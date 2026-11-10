
package com.mini.scanner;

import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanner {

    public Set<Class<?>> scan(String packageName)
            throws IOException, ClassNotFoundException, URISyntaxException {

        Set<Class<?>> classes = new LinkedHashSet<>();

        String packagePath = packageName.replace('.', '/');

        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources =
                classLoader.getResources(packagePath);

        while (resources.hasMoreElements()) {

            URL resource = resources.nextElement();

            if (!resource.getProtocol().equals("file")) {
                continue;
            }

            // Convert URL to URI before converting to Path
            Path directory = Paths.get(resource.toURI());

            try (var paths = Files.walk(directory)) {

                var iterator = paths.iterator();

                while (iterator.hasNext()) {

                    Path path = iterator.next();

                    if (!path.toString().endsWith(".class")) {
                        continue;
                    }

                    if (path.getFileName().toString().contains("$")) {
                        continue;
                    }

                    String className = packageName + "."
                            + directory.relativize(path)
                            .toString()
                            .replace('/', '.')
                            .replace('\\', '.')
                            .replaceAll("\\.class$", "");

                    Class<?> clazz = Class.forName(
                            className,
                            false,
                            classLoader
                    );

                    classes.add(clazz);
                }
            }
        }

        return classes;
    }
}
