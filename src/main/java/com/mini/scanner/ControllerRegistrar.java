package com.mini.scanner;

import com.mini.annotation.GetMapping;
import com.mini.annotation.RestController;
import com.mini.dispatcher.HandlerMapping;
import com.mini.handler.ControllerMethodHandler;

import java.lang.reflect.Method;
import java.util.Set;

public class ControllerRegistrar {

    private final ClassPathScanner scanner = new ClassPathScanner();

    public void registerControllers(
            String basePackage,
            HandlerMapping handlerMapping
    ) throws Exception {

        Set<Class<?>> classes = scanner.scan(basePackage);

        for (Class<?> clazz : classes) {

            // 1. Check whether this class is a REST controller
            if (!clazz.isAnnotationPresent(RestController.class)) {
                continue;
            }

            // 2. Create the controller instance
            Object controller = clazz.getDeclaredConstructor().newInstance();

            // 3. Inspect its methods
            for (Method method : clazz.getDeclaredMethods()) {

                if (method.isAnnotationPresent(GetMapping.class)) {

                    if (method.getParameterCount() != 0) {
                        throw new IllegalStateException(
                                "GET handler methods cannot have parameters yet: "
                                        + method.getName()
                        );
                    }

                    GetMapping mapping = method.getAnnotation(GetMapping.class);

                    // 4. Register the endpoint
                    handlerMapping.register(
                            "GET",
                            mapping.value(),
                            new ControllerMethodHandler(controller, method)
                    );
                }
            }
        }
    }
}