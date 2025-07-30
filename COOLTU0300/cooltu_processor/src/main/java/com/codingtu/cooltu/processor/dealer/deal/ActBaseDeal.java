package com.codingtu.cooltu.processor.dealer.deal;

import com.codingtu.cooltu.lib4j.data.data.JavaInfo;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.processor.annotation.activity.ActBase;
import com.codingtu.cooltu.processor.annotation.base.BaseClass;
import com.codingtu.cooltu.processor.annotation.ui.ClickView;
import com.codingtu.cooltu.processor.annotation.ui.Layout;
import com.codingtu.cooltu.processor.dealer.builder.ActBaseBuilder;
import com.codingtu.cooltu.processor.dealer.deal.base.TypeBaseDeal;
import com.codingtu.cooltu.processor.tools.ElementTool;
import com.codingtu.cooltu.processor.tools.IdTool;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class ActBaseDeal extends TypeBaseDeal<ActBase> {
    @Override
    protected void dealTypeElement(String typeFullName, TypeElement te, ActBase actBase) {
        JavaInfo actJavaInfo = new JavaInfo(typeFullName);
        ActBaseBuilder actBaseBuilder = new ActBaseBuilder(actJavaInfo.name);
        actBaseBuilder.baseClass = te.getAnnotation(BaseClass.class);
        Layout layout = te.getAnnotation(Layout.class);
        if (layout != null) {
            IdTool.Id layoutId = IdTool.elementToId(te, Layout.class, layout.value());
            actBaseBuilder.layoutId = layoutId;
        }

        actBaseBuilder.clickViewEs = ElementTool.getChildElementEs(te).convert(new Es.Convert<Element, ExecutableElement>() {
            @Override
            public ExecutableElement convert(int index, Element element) {
                if (element instanceof ExecutableElement) {
                    ClickView clickView = element.getAnnotation(ClickView.class);
                    if (clickView != null) {
                        return (ExecutableElement) element;
                    }
                }
                return null;
            }
        });

    }
}
