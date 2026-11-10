
package com.mini.context;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private final Map<Class<?>, BeanDefinition> beanDefinitions =
            new HashMap<>();

    private final Map<Class<?>, Object> singletonObjects =
            new HashMap<>();

    private final Set<Class<?>> beansInCreation = new HashSet<>();

    public void registerBeanDefinition(Class<?> beanClass) {
        beanDefinitions.put(beanClass, new BeanDefinition(beanClass));
    }

    public <T> T getBean(Class<T> beanClass)
            throws ReflectiveOperationException {

        // Return existing singleton
        if (singletonObjects.containsKey(beanClass)) {
            return beanClass.cast(singletonObjects.get(beanClass));
        }

        BeanDefinition definition = beanDefinitions.get(beanClass);

        if (definition == null) {
            throw new IllegalArgumentException(
                    "No bean registered for: " + beanClass.getName()
            );
        }

        // Detect circular dependencies
        if (!beansInCreation.add(beanClass)) {
            throw new IllegalStateException(
                    "Circular dependency detected: " + beanClass.getName()
            );
        }

        try {
            Class<?> clazz = definition.getBeanClass();

            Constructor<?>[] constructors = clazz.getConstructors();

            if (constructors.length != 1) {
                throw new IllegalStateException(
                        "Expected exactly one public constructor for: "
                                + clazz.getName()
                );
            }

            Constructor<?> constructor = constructors[0];

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[parameterTypes.length];

            // Resolve constructor dependencies recursively
            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getBean(parameterTypes[i]);
            }

            // Create bean with injected dependencies
            Object bean = constructor.newInstance(dependencies);

            singletonObjects.put(beanClass, bean);

            return beanClass.cast(bean);

        } finally {
            beansInCreation.remove(beanClass);
        }
    }
}
