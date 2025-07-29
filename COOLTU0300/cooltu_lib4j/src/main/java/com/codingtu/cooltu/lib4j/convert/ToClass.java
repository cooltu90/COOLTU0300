package com.codingtu.cooltu.lib4j.convert;

public class ToClass {

    public static Class to(String classFullName) {
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {

        }
        return null;
    }
}
