package com.codingtu.cooltu.processor.tools;

import com.codingtu.cooltu.lib4j.data.value.TValue;
import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;
import com.codingtu.cooltu.lib4j.tools.StringTool;
import com.codingtu.cooltu.processor.log.Logs;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class LayoutTool {

    public static class ViewInfo {
        public ViewInfo parent;
        public BaseEs<ViewInfo> childs = Es.es();
        public String viewType;
        public String id;
        public int index;
        public String name;
    }

    public static ViewInfo readLayout(String layoutName) {
        try {
            String layoutPath = BuilderTool.layoutPath(layoutName);

            TValue<ViewInfo> parentViewInfo = TValue.obtain();
            TValue<ViewInfo> currentViewInfo = TValue.obtain();

            parser().parse(layoutPath, new DefaultHandler() {
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if ("include".equals(qName)) {
                        String layoutName = getLayout(attributes);
                        ViewInfo viewInfo = readLayout(layoutName);
                        if ("merge".equals(viewInfo.viewType)) {
                            viewInfo.childs.ls(new Es.EachEs<ViewInfo>() {
                                @Override
                                public boolean each(int position, ViewInfo viewInfo) {
                                    viewInfo.parent = parentViewInfo.value;
                                    addToChilds(parentViewInfo.value.childs, viewInfo);
                                    return false;
                                }
                            });
                        } else {
                            String id = getId(attributes);
                            if (StringTool.isNotBlank(id)) {
                                viewInfo.id = id;
                            }
                            viewInfo.parent = parentViewInfo.value;
                            addToChilds(parentViewInfo.value.childs, viewInfo);
                        }
                    } else {
                        String id = getId(attributes);
                        currentViewInfo.value = new ViewInfo();
                        currentViewInfo.value.viewType = qName;
                        currentViewInfo.value.id = id;
                        if (parentViewInfo.value != null) {
                            currentViewInfo.value.parent = parentViewInfo.value;
                            addToChilds(parentViewInfo.value.childs, currentViewInfo.value);
                        }
                        parentViewInfo.value = currentViewInfo.value;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement(uri, localName, qName);
                    if (qName.equals(currentViewInfo.value.viewType)) {
                        if (currentViewInfo.value.parent != null) {
                            parentViewInfo.value = currentViewInfo.value.parent;
                            currentViewInfo.value = parentViewInfo.value;
                        }
                    }
                }
            });
            return parentViewInfo.value;
        } catch (Exception e) {
            Logs.i(e);
            return null;
        }
    }

    private static void addToChilds(BaseEs<ViewInfo> childs, ViewInfo viewInfo) {
        viewInfo.index = childs.count();
        childs.add(viewInfo);
    }

    private static SAXParser parser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            return factory.newSAXParser();
        } catch (Exception e) {
            return null;
        }
    }

    public static void logViewInfo(ViewInfo parentViewInfo, int level) {
        Logs.i(StringTool.repeatString(level, " ") + parentViewInfo.viewType + " " + parentViewInfo.id + " " + parentViewInfo.index);
        Es.es(parentViewInfo.childs).ls(new Es.EachEs<ViewInfo>() {
            @Override
            public boolean each(int position, ViewInfo viewInfo) {
                logViewInfo(viewInfo, level + 1);
                return false;
            }
        });
    }

    private static String getId(Attributes attributes) {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String qName = attributes.getQName(i);
            if (qName.equals("android:id")) {
                String value = attributes.getValue(i);
                int index = value.indexOf("/");
                return value.substring(index + 1);
            }
        }
        return null;
    }

    private static String getLayout(Attributes attributes) {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String qName = attributes.getQName(i);
            if (qName.equals("layout")) {
                String value = attributes.getValue(i);
                int index = value.indexOf("/");
                return value.substring(index + 1);
            }
        }
        return null;
    }

    public static String dealViewType(String viewType) {
        if (viewType.contains(".")) {
            return viewType;
        } else {
            if ("View".equals(viewType)) {
                return "android.view.View";
            } else {
                return "android.widget." + viewType;
            }
        }
    }

}
