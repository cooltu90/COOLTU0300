package com.codingtu.cooltu.lib4j.tools;

import com.codingtu.cooltu.lib4j.convert.ToString;
import com.codingtu.cooltu.lib4j.data.kv.KV;
import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class ElementTool {
    public static String getType(Element e) {
        return e.asType().toString();
    }

    public static String simpleName(Element e) {
        return e.getSimpleName().toString();
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

    public static BaseEs<VariableElement> getParameters(ExecutableElement ee) {
        BaseEs<VariableElement> es = Es.es();
        List<? extends VariableElement> parameters = ee.getParameters();
        int count = CountTool.count(parameters);
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                es.add(parameters.get(i));
            }
        }
        return es;
    }

    public static KV<String, String> getFieldKv(VariableElement ve) {
        return new KV<>(getType(ve), simpleName(ve));
    }
}
