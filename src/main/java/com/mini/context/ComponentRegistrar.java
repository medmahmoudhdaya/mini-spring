
package com.mini.context;

import com.mini.annotation.RestController;
import com.mini.annotation.Service;

import java.util.Set;

public class ComponentRegistrar {

    public void registerComponents(
            Set<Class<?>> classes,
            ApplicationContext context
    ) {

        for (Class<?> clazz : classes) {

            if (clazz.isAnnotationPresent(RestController.class)
                    || clazz.isAnnotationPresent(Service.class)) {

                context.registerBeanDefinition(clazz);
            }
        }
    }
}
