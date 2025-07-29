package com.codingtu.cooltu.lib4j.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileTool {

    public static final String SEPARATOR = File.separator;

    public static String addPrexSeparator(String dir) {
        return SEPARATOR + dir;
    }

    /**************************************************
     *
     * 获取项目目录
     *
     **************************************************/
    public static String getProjectDir() {
        return System.getProperty("user.dir");
    }


    public static File getDefaultFile() {
        return new File(getProjectDir(), "files\\files");
    }

    public static File getFileInProject(String path) {
        return new File(getProjectDir(), path);
    }

    /**************************************************
     *
     * 读取
     *
     **************************************************/
    public static BufferedReader getBufferedReader(File file) throws Exception {
        return getBufferedReader(file, "UTF-8");
    }

    public static BufferedWriter getBufferedWriter(File file) throws Exception {
        return getBufferedWriter(file, "UTF-8");
    }

    public static BufferedReader getBufferedReader(File file, String charsetName) throws Exception {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
    }

    public static BufferedWriter getBufferedWriter(File file, String charsetName) throws Exception {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charsetName));
    }

    /**************************************************
     *
     * 创建目录
     *
     **************************************************/
    public static void createParentDir(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static void createParentDir(String path) {
        createParentDir(new File(path));
    }

    public static void createDir(String path) {
        createDir(new File(path));
    }

    public static void createDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


}
