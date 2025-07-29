package com.codingtu.cooltu.lib4j.tools;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FanxingTool {

    public static Type getFanxingType(Class clazz, int index) {
        return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public static Type getFanxingType(Object obj, int index) {
        return getFanxingType(obj.getClass(), index);
    }

    public static Class getFanxing(Object obj, int index) {
        Type type = getFanxingType(obj.getClass(), index);
        return type instanceof Class ? (Class) type : null;
    }

    public static Class getFanxing(Object obj) {
        return getFanxing(obj, 0);
    }

}
