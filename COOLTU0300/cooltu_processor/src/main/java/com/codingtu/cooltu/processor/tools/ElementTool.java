package com.codingtu.cooltu.processor.tools;

import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.lib4j.tools.CountTool;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ElementTool {
    public static String getType(Element e) {
        return e.asType().toString();
    }

    public static BaseEs<Element> getChildElementEs(TypeElement te) {
        BaseEs<Element> es = Es.es();
        List<? extends Element> ees = te.getEnclosedElements();
        int count = CountTool.count(ees);
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                es.add(ees.get(i));
            }
        }
        return es;
    }
}
