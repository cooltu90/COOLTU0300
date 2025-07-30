package com.codingtu.cooltu.lib4j.convert;

import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.lib4j.tools.ElementTool;

import javax.lang.model.element.VariableElement;

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

    public static String to(VariableElement ve) {
        return ElementTool.getType(ve) + " " + ElementTool.simpleName(ve);
    }

    public static String to(BaseEs<VariableElement> es) {
        StringBuilder sb = new StringBuilder();
        es.ls(new Es.EachEs<VariableElement>() {
            @Override
            public boolean each(int position, VariableElement ve) {
                if (position != 0) {
                    sb.append(", ");
                }
                sb.append(to(ve));
                return false;
            }
        });
        return sb.toString();
    }

}
