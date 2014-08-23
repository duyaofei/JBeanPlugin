package com.venus.helper;

import javax.swing.*;
import java.io.*;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午23:26
 * 文件描述:
 * 修改描述：
 */
public class FileHelper {


    public static String toFullPath(String path){
        if (!path.endsWith(File.separator)){
            return path+File.separator;
        }
        return path;
    }
    /**
     * 创建java文件
     */
    private static boolean buildPath(String path){
        File file = new File(path);
        if(!file.exists()){
            if(!file.mkdirs() ){
                throw new RuntimeException("创建目录：["+path+"]失败！");
            }
        }
        return true;
    }

    public static File buildFile(String filePath){
        File file = new File(filePath);
        if(!file.exists() && !file.isDirectory()){
            try {
                if(!file.createNewFile() ){
                    throw new RuntimeException("创建文件：["+filePath+"]失败！");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return file;
    }


    public static void writeString(String path,String fileName,String content){


        Writer writer =null;
        if(buildPath(path)){
            File file = buildFile(toFullPath(path)+fileName);
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                assert writer != null;
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void writeString(String path, String tableName, StringBuilder stringBuilder) {
        writeString(path,tableName,stringBuilder.toString());
    }
}
