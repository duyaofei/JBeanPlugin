package com.venus.entity;

import com.venus.helper.DBHelper;
import com.venus.helper.StringHelper;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-3 下午下午8:59
 * 文件描述:
 * 修改描述:
 */
public class ColMetaData {

    private String dataType;
    /*字段名称*/
    private String fieldName;

    /*注释*/
    private String comment;

    public ColMetaData() {
    }

    /**
     *
     * @param dataType 字段类型
     * @param fieldName 字段名称
     * @param comment 注释
     */
    public ColMetaData(String dataType, String fieldName, String comment) {
        this.dataType = dataType;
        this.fieldName = fieldName;
        this.comment = comment;
    }


    /**
     * 获取字段Java类型
     *
     * @return
     */
    public String getJavaType() {
        return DBHelper.getJavaType(dataType);
    }

    /**
     * 获取字段的简短Java类型
     *
     * @return
     */
    public String getSimpleJavaType() {
        String javaType = getJavaType();

        if (StringHelper.isEmpty(javaType)) {
            return null;
        }

        return javaType.substring(javaType.lastIndexOf(".")+1);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDataType() {
        return dataType;
    }
}
