
package com.mini;

import com.mini.annotation.MiniBootApplication;
import com.mini.context.ApplicationContext;
import com.mini.context.ComponentRegistrar;
import com.mini.dispatcher.ControllerMethodHandlerAdapter;
import com.mini.dispatcher.Dispatcher;
import com.mini.dispatcher.HandlerMapping;
import com.mini.dispatcher.RequestHandlerAdapter;
import com.mini.scanner.ClassPathScanner;
import com.mini.scanner.ControllerRegistrar;
import com.mini.server.HttpServer;

import java.util.List;
import java.util.Set;

public class MiniApplication {

    public static void run(Class<?> primarySource, String[] args)
            throws Exception {

        if (!primarySource.isAnnotationPresent(MiniBootApplication.class)) {
            throw new IllegalArgumentException(
                    "Application class must be annotated with @MiniBootApplication"
            );
        }

        // 1. Determine the application base package
        String basePackage = primarySource.getPackageName();

        System.out.println("Starting Mini Spring...");
        System.out.println("Base package: " + basePackage);

        // 2. Discover application classes
        ClassPathScanner scanner = new ClassPathScanner();

        Set<Class<?>> classes = scanner.scan(basePackage);

        // 3. Create the IoC container
        ApplicationContext context = new ApplicationContext();

        // 4. Register component definitions
        ComponentRegistrar componentRegistrar =
                new ComponentRegistrar();

        componentRegistrar.registerComponents(classes, context);

        // 5. Register controller routes
        HandlerMapping handlerMapping = new HandlerMapping();

        ControllerRegistrar controllerRegistrar =
                new ControllerRegistrar();

        controllerRegistrar.registerControllers(
                classes,
                handlerMapping,
                context
        );

        // 6. Configure Dispatcher
        Dispatcher dispatcher = new Dispatcher(
                handlerMapping,
                List.of(
                        new RequestHandlerAdapter(),
                        new ControllerMethodHandlerAdapter()
                )
        );

        // 7. Start HTTP server
        HttpServer server = new HttpServer(8088, dispatcher);

        System.out.println("Mini Spring started!");

        server.start();
    }
}
