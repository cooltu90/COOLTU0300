package com.codingtu.cooltu.lib4j.convert;

public class ToString {

    public static String to(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            return String.valueOf(obj);
        }
    }

}
