package com.codingtu.cooltu.lib4j.convert;

import com.codingtu.cooltu.lib4j.tools.CharTool;

public class ToName {

    /**************************************************
     * 转换成静态变量的命名方式
     * 比如 myName 转换后为 MY_NAME
     **************************************************/
    public static String toStaticType(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (CharTool.isLower(c)) {
                sb.append(ToUpper.to(c));
            } else if (CharTool.isUpper(c) && i != 0) {
                sb.append("_" + c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**************************************************
     * 转换成小写加下划线类型
     * 比如 myName 转换后为 my_name
     **************************************************/
    public static String toLayoutType(String str) {
        return toStaticType(str).toLowerCase();
    }

    /**************************************************
     * 转换成类的命名方式
     **************************************************/
    public static String toClassType(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0 && CharTool.isLower(c)) {
                c = ToUpper.to(c);
            } else if (CharTool.isLowerLine(c)) {
                if (i == str.length() - 1) {
                    break;
                } else {
                    i++;
                    c = str.charAt(i);
                    if (CharTool.isLower(c)) {
                        c = ToUpper.to(c);
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**************************************************
     * 转换成函数的命名方式
     **************************************************/
    public static String toMethodType(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0 && CharTool.isUpper(c)) {
                c = ToLower.to(c);
            } else if (CharTool.isLowerLine(c)) {
                if (i == str.length() - 1) {
                    break;
                } else {
                    i++;
                    c = str.charAt(i);
                    if (CharTool.isLower(c)) {
                        c = ToUpper.to(c);
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
