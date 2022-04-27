package com.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class FileUtilities {

    /**
     * Compute the absolute file path to the jar file.
     * The framework is based on
     * <a href="http://stackoverflow.com/a/12733172/1614775">http://stackoverflow.com/a/12733172/1614775</a>
     * But that gets it right for only one of the four cases.
     *
     * @param clazz A class residing in the required jar.
     * @return A File object for the directory in which the jar file resides.
     * During testing with NetBeans, the result is ./build/classes/,
     * which is the directory containing what will be in the jar.
     */
    public static File getJarDirectory(Class<?> clazz) {
        URL url;
        String extURL;

        try {
            url = clazz.getProtectionDomain().getCodeSource().getLocation();
        } catch (SecurityException ex) {
            url = clazz.getResource(clazz.getSimpleName() + ".class");
        }

        extURL = Objects.requireNonNull(url).toExternalForm();

        if (extURL.endsWith(".jar"))
            extURL = extURL.substring(0, extURL.lastIndexOf("/"));
        else {
            String suffix = "/" + (clazz.getName()).replace(".", "/") + ".class";
            extURL = extURL.replace(suffix, "");
            if (extURL.startsWith("jar:") && extURL.endsWith(".jar!"))
                extURL = extURL.substring(4, extURL.lastIndexOf("/"));
        }

        try {
            url = new URL(extURL);
        } catch (MalformedURLException ignored) {

        }

        try {
            return new File(url.toURI());
        } catch (URISyntaxException ex) {
            return new File(url.getPath());
        }
    }
}
