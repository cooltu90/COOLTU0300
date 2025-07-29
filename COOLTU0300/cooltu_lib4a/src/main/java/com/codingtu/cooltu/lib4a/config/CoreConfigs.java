package com.codingtu.cooltu.lib4a.config;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codingtu.cooltu.lib4a.R;
import com.codingtu.cooltu.lib4j.config.LibConfigs;
import com.codingtu.cooltu.lib4j.log.LibLogs;

public abstract class CoreConfigs extends LibConfigs {

    ///////////////////////////////////////////////////////
    //
    // 创建获取
    //
    ///////////////////////////////////////////////////////
    public static CoreConfigs configs() {
        return (CoreConfigs) LibConfigs.configs();
    }

    ///////////////////////////////////////////////////////
    //
    // 配置方法
    //
    ///////////////////////////////////////////////////////

    /**************************************************
     * log相关
     **************************************************/
    @Override
    public void baseLog(int level, String tag, String msg) {
        switch (level) {
            case LibLogs.LEVEL_INFO:
                Log.i(tag, msg);
                break;
            case LibLogs.LEVEL_WARNING:
                Log.w(tag, msg);
                break;
            case LibLogs.LEVEL_ERROR:
                Log.e(tag, msg);
                break;
        }
    }

}
