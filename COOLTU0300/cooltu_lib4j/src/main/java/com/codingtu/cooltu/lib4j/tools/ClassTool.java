package com.codingtu.cooltu.lib4j.tools;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

public class ClassTool {

    ///////////////////////////////////////////////////////
    //
    // 判断是否为给定的类
    //
    ///////////////////////////////////////////////////////

    /**************************************************
     * 是否给定的类型
     **************************************************/
    private static boolean isGivenClass(Class cls, String name) {
        return cls.getCanonicalName().equals(name);
    }

    private static boolean isGivenClass(String cls, String name) {
        return cls.equals(name);
    }

    /**************************************************
     * 是否给定的类型们
     **************************************************/
    public static boolean isGivenClasses(String cls, Class... classes) {
        int count = CountTool.count(classes);
        if (count > 0) {
            for (int i = 0; i < CountTool.count(classes); i++) {
                if (isGivenClass(classes[i], cls)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGivenClasses(String cls, String... classes) {
        int count = CountTool.count(classes);
        if (count > 0) {
            for (int i = 0; i < CountTool.count(classes); i++) {
                if (isGivenClass(classes[i], cls)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**************************************************
     * 是否基础类型
     **************************************************/
    public static final Class[] BASE_CLASSES = new Class[]{
            String.class,
            Integer.class,
            int.class,
            Double.class,
            double.class,
            Float.class,
            float.class,
            Long.class,
            long.class,
            Character.class,
            char.class,
            Short.class,
            short.class,
            Byte.class,
            byte.class,
            Boolean.class,
            boolean.class
    };

    public static boolean isBaseClass(String classFullName) {
        return isGivenClasses(classFullName, BASE_CLASSES);
    }

    /**************************************************
     * 是否给定的类型
     **************************************************/
    public static boolean isObject(String name) {
        return isGivenClass(Object.class, name);
    }

    public static boolean isVoid(String name) {
        return isGivenClass(Void.class, name);
    }

    public static boolean isNotVoid(String name) {
        return StringTool.isNotBlank(name) && !ClassTool.isVoid(name);
    }

    public static boolean isString(String name) {
        return isGivenClass(String.class, name);
    }

    public static boolean isInt(String name) {
        return isGivenClass(int.class, name);
    }

    public static boolean isInteger(String name) {
        return isGivenClass(Integer.class, name);
    }

    public static boolean isChar(String name) {
        return isGivenClass(char.class, name);
    }

    public static boolean isCharacter(String name) {
        return isGivenClass(Character.class, name);
    }

    public static boolean isShort(String name) {
        return isGivenClass(short.class, name);
    }

    public static boolean isSHORT(String name) {
        return isGivenClass(Short.class, name);
    }

    public static boolean isByte(String name) {
        return isGivenClass(byte.class, name);
    }

    public static boolean isBYTE(String name) {
        return isGivenClass(Byte.class, name);
    }

    public static boolean isDouble(String name) {
        return isGivenClass(double.class, name);
    }

    public static boolean isDOUBLE(String name) {
        return isGivenClass(Double.class, name);
    }

    public static boolean isFloat(String name) {
        return isGivenClass(float.class, name);
    }

    public static boolean isFLOAT(String name) {
        return isGivenClass(Float.class, name);
    }

    public static boolean isLong(String name) {
        return isGivenClass(long.class, name);
    }

    public static boolean isLONG(String name) {
        return isGivenClass(Long.class, name);
    }

    public static boolean isBoolean(String name) {
        return isGivenClass(boolean.class, name);
    }

    public static boolean isBOOLEAN(String name) {
        return isGivenClass(Boolean.class, name);
    }

    public static boolean isList(String name) {
        return name.startsWith(List.class.getCanonicalName());
    }


    ///////////////////////////////////////////////////////
    //
    // 获取类名。用于apt中的注解反射
    //
    ///////////////////////////////////////////////////////
    public static interface AnnotationClassGetter {
        Object get();
    }

    public static String getAnnotationClass(AnnotationClassGetter getter) {
        String back = null;
        try {
            getter.get();
        } catch (MirroredTypeException mte) {
            back = mte.getTypeMirror().toString();
        }
        return back;
    }

    public static List<String> getAnnotationClasses(AnnotationClassGetter getter) {
        try {
            getter.get();
        } catch (MirroredTypesException mte) {
            List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();
            ArrayList<String> classNames = new ArrayList<String>();
            for (int i = 0; i < CountTool.count(typeMirrors); i++) {
                classNames.add(typeMirrors.get(i).toString());
            }
            return classNames;
        }
        return null;
    }

}
