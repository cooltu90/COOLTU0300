package com.codingtu.cooltu.lib.config;

import com.codingtu.cooltu.constant.Module;
import com.codingtu.cooltu.lib.BuildConfig;
import com.codingtu.cooltu.lib.ui.page.BaseActivity;
import com.codingtu.cooltu.lib4a.config.CoreConfigs;
import com.codingtu.cooltu.lib4j.config.LibConfigs;
import com.codingtu.cooltu.processor.annotation.ModuleInfo;

@ModuleInfo(
        module = Module.APP_LIB,
        rPkg = "com.codingtu.cooltu.lib",
        baseActivity = BaseActivity.class
)
public class BaseConfigs extends CoreConfigs {
    public static BaseConfigs configs() {
        return (BaseConfigs) LibConfigs.configs();
    }

    @Override
    public boolean isLog() {
        return BuildConfig.IS_LOG;
    }
}
