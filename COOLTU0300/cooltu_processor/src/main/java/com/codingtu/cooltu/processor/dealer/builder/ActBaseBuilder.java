package com.codingtu.cooltu.processor.dealer.builder;

import com.codingtu.cooltu.constant.FullName;
import com.codingtu.cooltu.constant.Pkg;
import com.codingtu.cooltu.lib4j.data.map.ListValueMap;
import com.codingtu.cooltu.lib4j.data.value.TValue;
import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.lib4j.es.impl.StringEs;
import com.codingtu.cooltu.lib4j.es.map.BaseMap;
import com.codingtu.cooltu.lib4j.tools.ClassTool;
import com.codingtu.cooltu.lib4j.tools.StringTool;
import com.codingtu.cooltu.lib4j.tools.TagTool;
import com.codingtu.cooltu.processor.BuilderType;
import com.codingtu.cooltu.processor.annotation.base.BaseClass;
import com.codingtu.cooltu.processor.annotation.base.Fanxing;
import com.codingtu.cooltu.processor.container.BuilderMap;
import com.codingtu.cooltu.processor.dealer.builder.base.BaseBuilder;
import com.codingtu.cooltu.processor.log.Logs;
import com.codingtu.cooltu.processor.tools.BuilderTool;
import com.codingtu.cooltu.processor.tools.IdTool;
import com.codingtu.cooltu.processor.tools.LayoutTool;

public class ActBaseBuilder extends BaseBuilder {

    public BaseClass baseClass;
    public IdTool.Id layoutId;
    private LayoutTool.ViewInfo viewInfo;
    public BaseMap<String, String> fieldMap = Es.maps();
    public BaseEs<String> findViewLines = Es.es();
    public ListValueMap<String, LayoutTool.ViewInfo> viewInfoMap = new ListValueMap<>();

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

    private void dealViewInfo(LayoutTool.ViewInfo viewInfo) {
        viewInfo.childs.ls(new Es.EachEs<LayoutTool.ViewInfo>() {
            @Override
            public boolean each(int position, LayoutTool.ViewInfo viewInfo) {
                if (StringTool.isNotBlank(viewInfo.id)) {
                    viewInfoMap.get(viewInfo.id).add(viewInfo);
                }
                dealViewInfo(viewInfo);
                return false;
            }
        });
    }

    @Override
    protected void dealLines() {
        super.dealLines();
        StringEs actBaseFanxingStrEs = Es.strs();
        StringEs baseFanxingStrEs = Es.strs();
        TValue<String> baseClassFullName = TValue.obtain();
        getGenericities(baseClassFullName, actBaseFanxingStrEs, baseFanxingStrEs);

        if (layoutId != null) {
            viewInfo = LayoutTool.readLayout(layoutId.rName);
            if (viewInfo != null) {
                if (StringTool.isBlank(viewInfo.id)) {
                    fieldMap.put("rootViewGroup", "    protected android.view.ViewGroup rootViewGroup;");
                    findViewLines.add(TagTool.dealLine("        rootViewGroup = [ViewTool].getRootViewGroup(this);", FullName.VIEW_TOOL));
                } else {
                    fieldMap.put(viewInfo.id,
                            TagTool.dealLine("    protected [RelativeLayout] [rootRl];", LayoutTool.dealViewType(viewInfo.viewType), viewInfo.id));
                    findViewLines.add(TagTool.dealLine("        [rootRl] = findViewById([com.codingtu.cooltu].R.id.[rootRl]);", viewInfo.id, Pkg.R, viewInfo.id));
                }
                dealViewInfo(viewInfo);
            }
        }

//        fieldMap.ls(new Es.MapEach<String, String>() {
//            @Override
//            public boolean each(String fieldName, String fieldLine) {
//                addLine(fieldLine);
//                return false;
//            }
//        });
//
//        findViewLines.ls(new Es.EachEs<String>() {
//            @Override
//            public boolean each(int position, String s) {
//                Logs.i(s);
//                return false;
//            }
//        });

        addLine("");
        addLine("public abstract class [ActivityBase][fanxings]", javaInfo.name, actBaseFanxingStrEs.toFanxings());
        addLine("        extends [baseClass][fanxings] {", baseClassFullName.value, baseFanxingStrEs.toFanxings());
        addLine("");
        addLine("    @Override");
        addLine("    protected void onCreate(android.os.Bundle savedInstanceState) {");
        addLine("        super.onCreate(savedInstanceState);");
        if (layoutId != null) {
            addLine("        setContentView(getLayout());");
        }
        addLine("    }");
        if (layoutId != null) {
            addLine("");
            addLine("    private int getLayout() {");
            addLine("        return [layout];", layoutId.toString());
            addLine("    }");
        }
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
