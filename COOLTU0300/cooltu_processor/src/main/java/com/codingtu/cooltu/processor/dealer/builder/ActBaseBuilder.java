package com.codingtu.cooltu.processor.dealer.builder;

import com.codingtu.cooltu.constant.FullName;
import com.codingtu.cooltu.lib4j.data.value.TValue;
import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.lib4j.es.impl.StringEs;
import com.codingtu.cooltu.lib4j.tools.ClassTool;
import com.codingtu.cooltu.processor.BuilderType;
import com.codingtu.cooltu.processor.annotation.base.BaseClass;
import com.codingtu.cooltu.processor.annotation.base.Fanxing;
import com.codingtu.cooltu.processor.container.BuilderMap;
import com.codingtu.cooltu.processor.dealer.builder.base.BaseBuilder;
import com.codingtu.cooltu.processor.tools.BuilderTool;

public class ActBaseBuilder extends BaseBuilder {

    public BaseClass baseClass;

    public ActBaseBuilder(String activityName) {
        super(BuilderTool.actBaseJavaInfo(activityName));
        BuilderMap.putActBaseBuilder(this);
    }

    @Override
    protected BuilderType builderType() {
        return BuilderType.ACTBASE;
    }

    @Override
    protected boolean isBuild() {
        return true;
    }

    @Override
    protected void beforeBuild() {
        super.beforeBuild();
        lineEs.log();
    }

    @Override
    protected void dealLines() {
        super.dealLines();
        StringEs actBaseFanxingStrEs = Es.strs();
        StringEs baseFanxingStrEs = Es.strs();
        TValue<String> baseClassFullName = TValue.obtain();
        getGenericities(baseClassFullName, actBaseFanxingStrEs, baseFanxingStrEs);

        addLine("");
        addLine("public abstract class [ActivityBase][fanxings]", javaInfo.name, actBaseFanxingStrEs.toFanxings());
        addLine("        extends [baseClass][fanxings] {", baseClassFullName.value, baseFanxingStrEs.toFanxings());
        addLine("");
        addLine("}");
    }

    private void getGenericities(TValue<String> baseClassFullName, StringEs actBaseFanxingStrEs, StringEs baseFanxingStrEs) {

        if (baseClass == null) {
            baseClassFullName.value = FullName.BASE_ACTIVITY;
            return;
        }
        Fanxing[] fanxings = baseClass.fanxings();
        BaseEs<Fanxing> fanxingEs = Es.es(fanxings);

        baseClassFullName.value = ClassTool.getAnnotationClass(new ClassTool.AnnotationClassGetter() {
            @Override
            public Object get() {
                return baseClass.value();
            }
        });

        fanxingEs.ls(new Es.EachEs<Fanxing>() {
            @Override
            public boolean each(int position, Fanxing fanxing) {

                String fanxingClassFullName = ClassTool.getAnnotationClass(new ClassTool.AnnotationClassGetter() {
                    @Override
                    public Object get() {
                        return fanxing.value();
                    }
                });
                if (ClassTool.isNotVoid(fanxingClassFullName)) {
                    baseFanxingStrEs.add(fanxingClassFullName);
                } else {
                    String name = fanxing.name();
                    String extendsClassFullName = ClassTool.getAnnotationClass(new ClassTool.AnnotationClassGetter() {
                        @Override
                        public Object get() {
                            return fanxing.extendsClass();
                        }
                    });
                    if (ClassTool.isNotVoid(extendsClassFullName)) {
                        actBaseFanxingStrEs.add(name + " extends " + extendsClassFullName);
                    } else {
                        actBaseFanxingStrEs.add(name);
                    }
                    baseFanxingStrEs.add(name);
                }
                return false;
            }
        });
    }
}
