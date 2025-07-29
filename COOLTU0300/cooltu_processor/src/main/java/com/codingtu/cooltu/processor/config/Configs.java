package com.codingtu.cooltu.processor.config;

import com.codingtu.cooltu.lib4j.config.LibConfigs;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

public class Configs extends LibConfigs {

    ///////////////////////////////////////////////////////
    //
    // 创建获取
    //
    ///////////////////////////////////////////////////////
    private ProcessingEnvironment processingEnv;
    private Messager messager;

    public static Configs configs() {
        return (Configs) LibConfigs.configs();
    }

    public Configs(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.messager = processingEnv.getMessager();
    }

    public static void init(ProcessingEnvironment processingEnv) {
        configs(new Configs(processingEnv));
    }

    ///////////////////////////////////////////////////////
    //
    // 配置
    //
    ///////////////////////////////////////////////////////

    /**************************************************
     * log相关
     **************************************************/
    @Override
    public boolean isLog() {
        return true;
    }

    @Override
    public void baseLog(int level, String tag, String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
