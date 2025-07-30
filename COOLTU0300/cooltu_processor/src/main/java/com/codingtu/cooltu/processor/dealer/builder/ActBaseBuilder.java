package com.codingtu.cooltu.processor.dealer.builder;

import com.codingtu.cooltu.constant.FullName;
import com.codingtu.cooltu.constant.Pkg;
import com.codingtu.cooltu.lib4j.convert.ToString;
import com.codingtu.cooltu.lib4j.data.kv.KV;
import com.codingtu.cooltu.lib4j.data.map.BaseEsValueMap;
import com.codingtu.cooltu.lib4j.data.value.IntValue;
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
import com.codingtu.cooltu.processor.annotation.ui.ClickView;
import com.codingtu.cooltu.processor.container.BuilderMap;
import com.codingtu.cooltu.processor.dealer.builder.base.BaseBuilder;
import com.codingtu.cooltu.processor.tools.BuilderTool;
import com.codingtu.cooltu.lib4j.tools.ElementTool;
import com.codingtu.cooltu.processor.tools.IdTool;
import com.codingtu.cooltu.processor.tools.LayoutTool;

import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class ActBaseBuilder extends BaseBuilder {

    public BaseClass baseClass;
    public IdTool.Id layoutId;
    public BaseEs<ExecutableElement> clickViewEs;
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

        //类头
        addLine("");
        addLine("public abstract class [ActivityBase][fanxings]", javaInfo.name, actBaseFanxingStrEs.toFanxings());
        addLine("        extends [baseClass][fanxings]", baseClassFullName.value, baseFanxingStrEs.toFanxings());
        addLine("        implements android.view.View.OnClickListener {");
        addLine("");

        //字段
        fieldLines.ls(new Es.EachEs<String>() {
            @Override
            public boolean each(int position, String fieldLine) {
                addLine(fieldLine);
                return false;
            }
        });

        //oncreate
        addLine("");
        addLine("    @Override");
        addLine("    protected void onCreate(android.os.Bundle savedInstanceState) {");
        addLine("        super.onCreate(savedInstanceState);");
        if (layoutId != null) {
            addLine("        setContentView(getLayout());");
        }

        //findView
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

        //getLayout
        if (layoutId != null) {
            addLine("");
            addLine("    private int getLayout() {");
            addLine("        return [layout];", layoutId.toString());
            addLine("    }");
        }

        //onclick

        boolean hasClickView = !clickViewEs.isNull();
        addLine("");
        addLine("    @Override");
        addLine("    public void onClick(android.view.View v) {");
        addLine("        super.onClick(v);");
        if (hasClickView) {
            addLine("        try {");
            addLine("            switch (v.getId()) {");

            clickViewEs.ls(new Es.EachEs<ExecutableElement>() {
                @Override
                public boolean each(int position, ExecutableElement ee) {
                    ClickView clickView = ee.getAnnotation(ClickView.class);
                    Map<Integer, IdTool.Id> idMap = IdTool.elementToIds(ee, ClickView.class, clickView.value());
                    Es.maps(idMap).ls(new Es.MapEach<Integer, IdTool.Id>() {
                        @Override
                        public boolean each(Integer integer, IdTool.Id id) {
                            addLine("                case [com.codingtu.cooltu.R.id.tv]:", id.toString());
                            return false;
                        }
                    });

                    addLine("                    [tvClick](", ElementTool.simpleName(ee));

                    StringEs strs = Es.strs();
                    IntValue indexValue = IntValue.obtain();
                    ElementTool.getParameters(ee).ls(new Es.EachEs<VariableElement>() {
                        @Override
                        public boolean each(int position, VariableElement ve) {
                            KV<String, String> kv = ElementTool.getFieldKv(ve);
                            if (kv.k.equals(FullName.VIEW)) {
                                addLine("                            [, ]v", position == 0 ? "" : ", ");
                            } else {
                                addLine("                            [, ]([User]) v.getTag(com.codingtu.cooltu.lib4a.R.id.tag_[0])"
                                        , position == 0 ? "" : ", ", kv.k, indexValue.value);
                                indexValue.value++;
                            }
                            return false;
                        }
                    });

                    addLine("                    );");
                    addLine("                    break;");
                    return false;
                }
            });

            addLine("            }");
            addLine("        } catch (Exception e) {");
            addLine("            com.codingtu.cooltu.lib4a.log.Logs.e(e);");
            addLine("            if (!(e instanceof com.codingtu.cooltu.lib4a.exception.NotToastException)) {");
            addLine("                toast(e.getMessage());");
            addLine("            }");
            addLine("        }");
        }
        addLine("    }");

        //clickmethod
        clickViewEs.ls(new Es.EachEs<ExecutableElement>() {
            @Override
            public boolean each(int position, ExecutableElement ee) {
                String paramStr = ToString.to(ElementTool.getParameters(ee));
                addLine("");
                addLine("    public void [tvClick]([View v, User user]) {", ElementTool.simpleName(ee), paramStr);
                addLine("    }");
                return false;
            }
        });

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
                    viewInfo.name = "rootViewGroup";
                    fieldLine = "    protected android.view.ViewGroup rootViewGroup;";
                    findViewLine = TagTool.dealLine("        rootViewGroup = [ViewTool].getRootViewGroup(this);", FullName.VIEW_TOOL);
                } else {
                    fieldLine = TagTool.dealLine("    protected [RelativeLayout] [rootRl];", LayoutTool.dealViewType(viewInfo.viewType), viewInfo.name);
                    findViewLine = TagTool.dealLine("        [rootRl] = findViewById([com.codingtu.cooltu].R.id.[rootRl]);", viewInfo.id, Pkg.R, viewInfo.id);
                }
                addField(viewInfo.name, fieldLine);
                findViewLines.add(findViewLine);
                dealViewInfo(viewInfo);
                dealViewInfoForLines(viewInfo);
            }
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
                    viewInfo.name = viewInfo.id;
                } else if (viewInfoEs.count() == 1) {
                    LayoutTool.ViewInfo viewInfo0 = viewInfoEs.getByIndex(0);
                    viewInfo0.name += "0";
                    viewInfo.name = viewInfo.id + "1";
                } else {
                    viewInfo.name = viewInfo.id + viewInfoEs.count();
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
                addField(child.name, TagTool.dealLine("    protected [RelativeLayout] [rootRl];", LayoutTool.dealViewType(child.viewType), child.name));
                findViewLines.add(TagTool.dealLine("        [ll0] = ([LinearLayout]) [rootViewGroup].getChildAt([0]);", child.name,
                        LayoutTool.dealViewType(child.viewType), viewInfo.name, position));
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
