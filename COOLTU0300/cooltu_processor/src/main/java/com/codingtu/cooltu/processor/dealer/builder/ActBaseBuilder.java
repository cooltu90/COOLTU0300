package com.codingtu.cooltu.processor.dealer.builder;

import com.codingtu.cooltu.constant.FullName;
import com.codingtu.cooltu.constant.Pkg;
import com.codingtu.cooltu.lib4j.data.map.BaseEsValueMap;
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
import com.codingtu.cooltu.processor.tools.BuilderTool;
import com.codingtu.cooltu.processor.tools.IdTool;
import com.codingtu.cooltu.processor.tools.LayoutTool;

public class ActBaseBuilder extends BaseBuilder {

    public BaseClass baseClass;
    public IdTool.Id layoutId;
    private LayoutTool.ViewInfo viewInfo;

    public BaseMap<String, Boolean> fieldMap = Es.maps();
    public BaseEs<String> fieldLines = Es.es();
    public BaseEs<String> findViewLines = Es.es();
    public BaseEsValueMap<String, LayoutTool.ViewInfo> viewInfoMap = new BaseEsValueMap<>();

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
        dealViewInfo();
        addField("baseClassName", TagTool.dealLine("    public String baseClassName = \"[BaseWelcomeActivityBase]\";", javaInfo.name));

        addLine("");
        addLine("public abstract class [ActivityBase][fanxings]", javaInfo.name, actBaseFanxingStrEs.toFanxings());
        addLine("        extends [baseClass][fanxings] {", baseClassFullName.value, baseFanxingStrEs.toFanxings());
        addLine("");

        fieldLines.ls(new Es.EachEs<String>() {
            @Override
            public boolean each(int position, String fieldLine) {
                addLine(fieldLine);
                return false;
            }
        });

        addLine("");

        addLine("    @Override");
        addLine("    protected void onCreate(android.os.Bundle savedInstanceState) {");
        addLine("        super.onCreate(savedInstanceState);");
        if (layoutId != null) {
            addLine("        setContentView(getLayout());");
        }


        findViewLines.ls(new Es.EachEs<String>() {
            @Override
            public boolean each(int position, String s) {
                addLine(s);
                return false;
            }
        });

        addLine("        String nowBaseClassName = getClass().getSimpleName() + \"Base\";");
        addLine("        if (nowBaseClassName.equals(baseClassName)) {");
        addLine("            onCreateComplete();");
        addLine("        }");

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

    private void dealViewInfo() {
        if (layoutId != null) {
            viewInfo = LayoutTool.readLayout(layoutId.rName);
            if (viewInfo != null) {
                String fieldLine = null;
                String findViewLine = null;
                if (StringTool.isBlank(viewInfo.id)) {
                    viewInfo.id = "rootViewGroup";
                    fieldLine = "    protected android.view.ViewGroup rootViewGroup;";
                    findViewLine = TagTool.dealLine("        rootViewGroup = [ViewTool].getRootViewGroup(this);", FullName.VIEW_TOOL);
                } else {
                    fieldLine = TagTool.dealLine("    protected [RelativeLayout] [rootRl];", LayoutTool.dealViewType(viewInfo.viewType), viewInfo.id);
                    findViewLine = TagTool.dealLine("        [rootRl] = findViewById([com.codingtu.cooltu].R.id.[rootRl]);", viewInfo.id, Pkg.R, viewInfo.id);
                }
                addField(viewInfo.id, fieldLine);
                findViewLines.add(findViewLine);
                dealViewInfo(viewInfo);
            }
            dealViewInfoForLines(viewInfo);
        }
    }

    private void addField(String key, String line) {
        fieldMap.put(key, true);
        fieldLines.add(line);
    }

    private void dealViewInfo(LayoutTool.ViewInfo viewInfo) {
        viewInfo.childs.ls(new Es.EachEs<LayoutTool.ViewInfo>() {
            @Override
            public boolean each(int position, LayoutTool.ViewInfo viewInfo) {
                BaseEs<LayoutTool.ViewInfo> viewInfoEs = viewInfoMap.get(viewInfo.id);
                if (viewInfoEs.count() == 0) {

                } else if (viewInfoEs.count() == 1) {
                    LayoutTool.ViewInfo viewInfo0 = viewInfoEs.getByIndex(0);
                    viewInfo0.id += "0";
                    viewInfo.id += "1";
                } else {
                    viewInfo.id += viewInfoEs.count();
                }
                viewInfoEs.add(viewInfo);
                dealViewInfo(viewInfo);
                return false;
            }
        });
    }

    private void dealViewInfoForLines(LayoutTool.ViewInfo viewInfo) {
        viewInfo.childs.ls(new Es.EachEs<LayoutTool.ViewInfo>() {
            @Override
            public boolean each(int position, LayoutTool.ViewInfo child) {
                addField(child.id, TagTool.dealLine("    protected [RelativeLayout] [rootRl];", LayoutTool.dealViewType(child.viewType), child.id));
                findViewLines.add(TagTool.dealLine("        [ll0] = ([LinearLayout]) [rootViewGroup].getChildAt([0]);", child.id,
                        LayoutTool.dealViewType(child.viewType), viewInfo.id, position));
                dealViewInfoForLines(child);
                return false;
            }
        });
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
