package com.codingtu.cooltu.processor.tools;

import javax.lang.model.element.Element;

public class ElementTool {
    public static String getType(Element e) {
        return e.asType().toString();
    }
}
