package com.codingtu.cooltu.config;

import com.codingtu.cooltu.constant.Module;
import com.codingtu.cooltu.lib.config.BaseConfigs;
import com.codingtu.cooltu.lib.ui.page.BaseActivity;
import com.codingtu.cooltu.lib4j.config.LibConfigs;
import com.codingtu.cooltu.processor.annotation.ModuleInfo;

@ModuleInfo(
        module = Module.APP,
        rPkg = "com.codingtu.cooltu",
        baseActivity = BaseActivity.class
)
public class Configs extends BaseConfigs {

    public static Configs configs() {
        return (Configs) LibConfigs.configs();
    }

}
