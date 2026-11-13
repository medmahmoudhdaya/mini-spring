
package com.mini.scanner;

import com.mini.annotation.RequestMapping;
import com.mini.annotation.RestController;
import com.mini.context.ApplicationContext;
import com.mini.dispatcher.HandlerMapping;
import com.mini.handler.ControllerMethodHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ControllerRegistrar {

    public void registerControllers(
            Set<Class<?>> classes,
            HandlerMapping handlerMapping,
            ApplicationContext context
    ) throws Exception {

        for (Class<?> clazz : classes) {

            if (!clazz.isAnnotationPresent(RestController.class)) {
                continue;
            }

            Object controller = context.getBean(clazz);

            for (Method method : clazz.getDeclaredMethods()) {

                RequestMapping mapping = resolveMapping(method);

                if (mapping == null) {
                    continue;
                }

                if (method.getParameterCount() != 0) {
                    throw new IllegalStateException(
                            "handler methods cannot have parameters yet: "
                                    + method.getName()
                    );
                }

                String path = resolvePath(method, mapping);

                handlerMapping.register(
                        mapping.method().name(),
                        path,
                        new ControllerMethodHandler(controller, method)
                );
            }
        }
    }

    private RequestMapping resolveMapping(Method method) {

        // Case 1: Direct @RequestMapping
        RequestMapping mapping =
                method.getAnnotation(RequestMapping.class);

        if (mapping != null) {
            return mapping;
        }

        // Case 2: Composed annotations (@GetMapping, @PostMapping...)
        for (Annotation annotation : method.getAnnotations()) {

            mapping = annotation.annotationType()
                    .getAnnotation(RequestMapping.class);

            if (mapping != null) {
                return mapping;
            }
        }

        return null;
    }

    private String resolvePath(
            Method method,
            RequestMapping mapping
    ) throws Exception {

        // Direct @RequestMapping uses its path attribute.
        if (method.isAnnotationPresent(RequestMapping.class)) {
            return mapping.path();
        }

        // Composed annotations can expose a value() or path() attribute.
        for (Annotation annotation : method.getAnnotations()) {

            if (annotation.annotationType()
                    .getAnnotation(RequestMapping.class) == null) {
                continue;
            }

            Class<? extends Annotation> annotationType =
                    annotation.annotationType();

            try {
                Method valueMethod =
                        annotationType.getDeclaredMethod("value");

                return (String) valueMethod.invoke(annotation);

            } catch (NoSuchMethodException ignored) {
                // Try path() instead.
            }

            try {
                Method pathMethod =
                        annotationType.getDeclaredMethod("path");

                return (String) pathMethod.invoke(annotation);

            } catch (NoSuchMethodException ignored) {
                // Fall back to the base annotation's path.
            }
        }

        return mapping.path();
    }
}
