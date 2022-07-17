package com.alibaba.csp.sentinel.dashboard.nacos.utils;

import java.lang.reflect.Array;

/**
 * Object Utilities
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.2
 */
@SuppressWarnings("unchecked")
public abstract class ObjectUtils {

    /**
     * Empty String array
     *
     * @since 1.0.10
     */
    public static final String[] EMPTY_STRING_ARRAY = {};


    /**
     * Convert from variable arguments to array
     *
     * @param values variable arguments
     * @param <T>    The class
     * @return array
     */
    public static <T> T[] of(T... values) {
        return values;
    }

    /**
     * Create a new empty array from the specified component type
     *
     * @param componentType the specified component type
     * @param <T>           the specified component type
     * @return new empty array
     */
    public static <T> T[] emptyArray(Class<T> componentType) {
        return (T[]) Array.newInstance(componentType, 0);
    }
}
