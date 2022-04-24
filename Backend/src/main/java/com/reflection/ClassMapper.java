package com.reflection;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMapper {

    private String packageName;
    private String className;

    private List<Class<? extends Annotation>> requiredAnnotations = new ArrayList<>();
    private Class<?> requiredSuperClass;
    private List<Class<?>> requiredInterfaceClasses = new ArrayList<>();

    public ClassMapper(String packageName) {
        this.packageName = packageName;
    }

    public ClassMapper(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public ClassMapper setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public ClassMapper setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<Class<? extends Annotation>> getRequiredAnnotations() {
        return requiredAnnotations;
    }

    public ClassMapper setRequiredAnnotations(List<Class<? extends Annotation>> requiredAnnotations) {
        this.requiredAnnotations = requiredAnnotations;
        return this;
    }

    public ClassMapper addRequiredAnnotation(Class<? extends Annotation> requiredAnnotation) {
        this.requiredAnnotations.add(requiredAnnotation);
        return this;
    }

    public List<Class<?>> getRequiredInterfaceClasses() {
        return requiredInterfaceClasses;
    }

    public Class<?> getRequiredSuperClass() {
        return requiredSuperClass;
    }

    public ClassMapper setRequiredSuperClass(Class<?> requiredSuperClass) {
        this.requiredSuperClass = requiredSuperClass;
        return this;
    }

    public ClassMapper setRequiredInterfaceClasses(List<Class<?>> requiredInterfaceClasses) {
        this.requiredInterfaceClasses = requiredInterfaceClasses;
        return this;
    }

    public ClassMapper addRequiredParentClass(Class<?> requiredParentClass) {
        this.requiredInterfaceClasses.add(requiredParentClass);
        return this;
    }

    public Class<?>[] mapClasses() {
        List<Class<?>> classes = new ArrayList<>();

        try {
            for (final ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(packageName + ".")) {
                    Class<?> clazz = info.load();
                    boolean isValid = requiredSuperClass == null || clazz.getSuperclass() == requiredSuperClass;

                    for (Class<?> requiredInterfaceClass : requiredInterfaceClasses) {
                        if (Arrays.stream(clazz.getInterfaces()).noneMatch(inter -> inter.equals(requiredInterfaceClass))) {
                            isValid = false;
                            break;
                        }
                    }

                    for (Class<? extends Annotation> annotation : requiredAnnotations) {
                        if (Arrays.stream(clazz.getDeclaredAnnotations()).noneMatch(anno -> anno.annotationType().equals(annotation))) {
                            isValid = false;
                            break;
                        }
                    }

                    if (isValid) {
                        classes.add(clazz);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classes.toArray(new Class<?>[0]);
    }
}
