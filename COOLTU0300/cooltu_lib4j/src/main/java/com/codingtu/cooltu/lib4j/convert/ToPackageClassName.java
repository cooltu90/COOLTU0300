package com.codingtu.cooltu.lib4j.convert;

import com.codingtu.cooltu.lib4j.tools.ClassTool;

public class ToPackageClassName {
    public static String to(String classFullName) {
        if (ClassTool.isInt(classFullName)) {
            return Integer.class.getCanonicalName();
        }
        if (ClassTool.isBoolean(classFullName)) {
            return Boolean.class.getCanonicalName();
        }
        if (ClassTool.isLong(classFullName)) {
            return Long.class.getCanonicalName();
        }
        if (ClassTool.isFloat(classFullName)) {
            return Float.class.getCanonicalName();
        }
        if (ClassTool.isDouble(classFullName)) {
            return Double.class.getCanonicalName();
        }
        if (ClassTool.isChar(classFullName)) {
            return Character.class.getCanonicalName();
        }
        if (ClassTool.isShort(classFullName)) {
            return Short.class.getCanonicalName();
        }
        if (ClassTool.isByte(classFullName)) {
            return Byte.class.getCanonicalName();
        }
        return classFullName;
    }
}
