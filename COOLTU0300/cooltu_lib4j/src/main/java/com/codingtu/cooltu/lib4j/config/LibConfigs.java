package com.codingtu.cooltu.lib4j.config;

import com.codingtu.cooltu.lib4j.json.base.JsonHolder;
import com.codingtu.cooltu.lib4j.json.fastjson.FastJsonHolder;

public abstract class LibConfigs {

    ///////////////////////////////////////////////////////
    //
    // 创建获取
    //
    ///////////////////////////////////////////////////////
    private static LibConfigs CONFIGS;

    public static void configs(LibConfigs configs) {
        CONFIGS = configs;
    }

    public static LibConfigs configs() {
        return CONFIGS;
    }

    ///////////////////////////////////////////////////////
    //
    // 配置方法
    //
    ///////////////////////////////////////////////////////

    /**************************************************
     * log相关
     **************************************************/
    public abstract boolean isLog();

    public abstract void baseLog(int level, String tag, String msg);

    public String getDefaultLogTag() {
        return "TestApp";
    }

    public boolean isLogJsonException() {
        return false;
    }

    /**************************************************
     * json相关
     **************************************************/
    public JsonHolder createJsonHolder() {
        return new FastJsonHolder();
    }


}
