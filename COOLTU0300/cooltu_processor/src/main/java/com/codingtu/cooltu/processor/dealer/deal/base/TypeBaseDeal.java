package com.codingtu.cooltu.processor.dealer.deal.base;

import com.codingtu.cooltu.lib4j.tools.FanxingTool;
import com.codingtu.cooltu.processor.tools.ElementTool;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public abstract class TypeBaseDeal<T extends Annotation> extends BaseDeal {
    @Override
    public void dealElement(Element element) {
        TypeElement typeElement = (TypeElement) element;
        Class<T> annotationClass = FanxingTool.getFanxing(this);
        T annotation = typeElement.getAnnotation(annotationClass);
        dealTypeElement(ElementTool.getType(typeElement), typeElement, annotation);
    }

    protected abstract void dealTypeElement(String typeFullName, TypeElement te, T annotation);

}
