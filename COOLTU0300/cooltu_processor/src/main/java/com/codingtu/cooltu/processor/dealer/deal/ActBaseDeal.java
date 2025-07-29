package com.codingtu.cooltu.processor.dealer.deal;

import com.codingtu.cooltu.lib4j.data.data.JavaInfo;
import com.codingtu.cooltu.processor.annotation.activity.ActBase;
import com.codingtu.cooltu.processor.annotation.base.BaseClass;
import com.codingtu.cooltu.processor.annotation.ui.Layout;
import com.codingtu.cooltu.processor.dealer.builder.ActBaseBuilder;
import com.codingtu.cooltu.processor.dealer.deal.base.TypeBaseDeal;
import com.codingtu.cooltu.processor.tools.IdTool;

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
    }
}
