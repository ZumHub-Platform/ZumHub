package com.reflection;

import com.google.common.reflect.ClassPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodMapper {

    private Class<?> clazz;

    private List<Class<? extends Annotation>> requiredAnnotations = new ArrayList<>();
    private Class<?> returnType;
    private List<Class<?>> requiredParameterTypes = new ArrayList<>();

    public MethodMapper() {

    }

    public MethodMapper(@NotNull Class<?> clazz) {
        this.clazz = clazz;
    }

    public @Nullable Class<?> getClazz() {
        return clazz;
    }

    public MethodMapper setClazz(@NotNull Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public @NotNull List<Class<? extends Annotation>> getRequiredAnnotations() {
        return requiredAnnotations;
    }

    public MethodMapper setRequiredAnnotations(@NotNull List<Class<? extends Annotation>> requiredAnnotations) {
        this.requiredAnnotations = requiredAnnotations;
        return this;
    }

    public MethodMapper addRequiredAnnotation(@NotNull Class<? extends Annotation> requiredAnnotation) {
        this.requiredAnnotations.add(requiredAnnotation);
        return this;
    }

    public @Nullable Class<?> getReturnType() {
        return returnType;
    }

    public MethodMapper setReturnType(@NotNull Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    public @NotNull List<Class<?>> getRequiredParameterTypes() {
        return requiredParameterTypes;
    }

    public MethodMapper setRequiredParameterTypes(@NotNull List<Class<?>> requiredParameterTypes) {
        this.requiredParameterTypes = requiredParameterTypes;
        return this;
    }

    public MethodMapper addRequiredParameterType(@NotNull Class<?> parameterType) {
        this.requiredParameterTypes.add(parameterType);
        return this;
    }

    public Method[] mapMethods() {
        if (getClazz() == null) {
            return new Method[0];
        }

        List<Method> methods = new ArrayList<>();

        for (final Method method : getClazz().getMethods()) {
            boolean isValid = returnType == null || method.getReturnType() == returnType;

            for (Class<?> requiredInterfaceClass : requiredParameterTypes) {
                if (Arrays.stream(method.getParameterTypes()).noneMatch(inter -> inter.equals(requiredInterfaceClass))) {
                    isValid = false;
                    break;
                }
            }

            for (Class<? extends Annotation> annotation : requiredAnnotations) {
                if (Arrays.stream(method.getDeclaredAnnotations()).noneMatch(anno -> anno.annotationType().equals(annotation))) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                methods.add(method);
            }
        }

        return methods.toArray(new Method[0]);
    }
}
