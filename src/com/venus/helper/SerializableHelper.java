package com.venus.helper;

import com.venus.form.panel.DataBaseBean;

import java.io.*;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午5:08
 * 文件描述:序列化帮助类
 * 修改描述:
 */
public class SerializableHelper {

    /**
     * 序列化到文件
     * @param obj 序列化对象
     * @throws java.io.IOException
     */
    public static void serializable(Serializable obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(obj.getClass().getSimpleName()));
        out.writeObject(obj);
        out.flush();
        out.close();
    }

    /**
     * 凡序列化
     */
    public static <T>  T unSerializable(Class clazz) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(clazz.getSimpleName()));
        Object object =  in.readObject();
        in.close();
        return (T) object;
    }

    public static void main(String[] args) {
        DataBaseBean dataBaseBean = new DataBaseBean();
        dataBaseBean.setDriver("2222222");
        System.out.println(dataBaseBean.getClass().getSimpleName());
        try {
            dataBaseBean = SerializableHelper.unSerializable(DataBaseBean.class);
            System.out.println("args = [" + dataBaseBean.getDriver() + "]");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
