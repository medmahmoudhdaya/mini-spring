
package com.mini.scanner;

import com.mini.annotation.GetMapping;
import com.mini.annotation.RestController;
import com.mini.context.ApplicationContext;
import com.mini.dispatcher.HandlerMapping;
import com.mini.handler.ControllerMethodHandler;

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

            // Get the controller instance from the IoC container
            Object controller = context.getBean(clazz);

            for (Method method : clazz.getDeclaredMethods()) {

                if (!method.isAnnotationPresent(GetMapping.class)) {
                    continue;
                }

                if (method.getParameterCount() != 0) {
                    throw new IllegalStateException(
                            "GET handler methods cannot have parameters yet: "
                                    + method.getName()
                    );
                }

                GetMapping mapping =
                        method.getAnnotation(GetMapping.class);

                handlerMapping.register(
                        "GET",
                        mapping.value(),
                        new ControllerMethodHandler(controller, method)
                );
            }
        }
    }
}
