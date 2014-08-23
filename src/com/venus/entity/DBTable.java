package com.venus.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午16:18
 * 文件描述: 描述一张表
 * 修改描述:
 */
public class DBTable {
    /*表名*/
    private String tableName;
    /*列描述信息*/
    private List<ColMetaData> colMetaDatas;


    public DBTable(String tableName) {
        this.tableName = tableName;
    }


    /**
     * 添加一个字段
     * @param colMetaData
     */
    public void addColMetaData(ColMetaData colMetaData) {
        if (colMetaDatas == null) {
            colMetaDatas = new ArrayList<ColMetaData>();
        }
        colMetaDatas.add(colMetaData);
    }

    /**
     * 获取表名
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 获取字段总数
     * @return
     */
    public int getColumnCount() {
        return colMetaDatas.size();
    }

    /**
     * 获取字段名称
     * @param i
     * @return
     */
    public String getFieldName(int i) {
        return colMetaDatas.get(i).getFieldName();
    }

    /**
     * 获取字段对性数据库类型
     * @param i
     * @return
     */
    public String getDataType(int i) {
        return colMetaDatas.get(i).getDataType();
    }

    /**
     * 获取字段注释
     * @param i
     * @return
     */
    public String getComments(int i) {
        return colMetaDatas.get(i).getComment();
    }

    /**
     * 获取字段对应java类型
     * @param i
     * @return
     */
    public String getJavaType(int i) {
        return colMetaDatas.get(i).getJavaType();
    }

    /**
     * 获取字段对应简短Java类型
      * @param i
     * @return
     */
    public String getSimpleJavaType(int i) {
        return colMetaDatas.get(i).getSimpleJavaType();
    }

}
