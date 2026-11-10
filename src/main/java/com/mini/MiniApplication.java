
package com.mini;

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

    public static void main(String[] args) throws Exception {

        // 1. Discover application classes
        ClassPathScanner scanner = new ClassPathScanner();

        Set<Class<?>> classes = scanner.scan("com.mini");

        // 2. Create IoC container
        ApplicationContext context = new ApplicationContext();

        // 3. Register component definitions
        ComponentRegistrar componentRegistrar = new ComponentRegistrar();

        componentRegistrar.registerComponents(classes, context);

        // 4. Register controller routes
        HandlerMapping handlerMapping = new HandlerMapping();

        ControllerRegistrar controllerRegistrar = new ControllerRegistrar();

        controllerRegistrar.registerControllers(
                classes,
                handlerMapping,
                context
        );

        // 5. Configure dispatcher
        Dispatcher dispatcher = new Dispatcher(
                handlerMapping,
                List.of(
                        new RequestHandlerAdapter(),
                        new ControllerMethodHandlerAdapter()
                )
        );

        // 6. Start HTTP server
        HttpServer server = new HttpServer(8088, dispatcher);

        server.start();
    }
}
