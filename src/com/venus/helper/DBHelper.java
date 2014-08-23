package com.venus.helper;

import com.venus.DBDriver;
import com.venus.form.panel.DataBaseBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午14:46
 * 文件描述:
 * 修改描述：
 */
public class DBHelper {

    private static final DBDriver dbDriver = DBDriver.MYSQL;

    private static Map<String, String> map = new HashMap<String, String>();

    /**Mysql Oracle Java 類型影射**/
    static {
        map.put("BIGINT", "java.math.BigInteger");
        map.put("BIT", "java.lang.Boolean");
        map.put("CHAR", "java.lang.String");
        map.put("DATE", "java.sql.Date");
        map.put("DATETIME", "java.sql.Timestamp");
        map.put("DECIMAL", "java.math.BigDecimal");
        map.put("DOUBLE", "java.lang.Double");
        map.put("ENUM", "java.lang.String");
        map.put("FLOAT", "java.lang.Float");
        map.put("INT", "java.lang.Integer");
        map.put("NUMBER", "java.lang.Long");
        map.put("INT UNSIGNED", "java.lang.Integer");
        map.put("INTEGER", "java.lang.Long");
        map.put("LONGBLOB", "byte[]");
        map.put("RAW", "byte[]");
        map.put("LONGTEXT", "java.lang.String");
        map.put("MEDIUMBLOB", " byte[]");
        map.put("MEDIUMINT", "java.lang.Integer");
        map.put("MEDIUMTEXT", "java.lang.String");
        map.put("SET", "java.lang.String");
        map.put("SMALLINT", "java.lang.Integer");
        map.put("TEXT", "java.lang.String");
        map.put("TIME", "java.sql.Time");
        map.put("TIMESTAMP", "java.sql.Timestamp");
        map.put("TINYBLOB", "byte[]");
        map.put("VARCHAR", "java.lang.String");
        map.put("VARCHAR2", "java.lang.String");
        map.put("BLOB", "byte[]");
        map.put("TINYINT", "java.lang.Integer");
        map.put("TINYINT UNSIGNED", "java.lang.Integer");
        map.put("FLOAT", "java.lang.Float");
        map.put("DECIMAL", "java.math.BigDecimal");
        map.put("BOOLEAN", "java.lang.Boolean");
        map.put("YEAR", "java.sql.Date");
        map.put("DATE", "java.sql.Timestamp");
    }

    /**
     * 获取Java类型
     *
     * @param dataType
     * @return
     */
    public static String getJavaType(String dataType) {
        if (StringHelper.isEmpty(dataType))
            return null;
        return map.get(dataType.toUpperCase());
    }

    /**
     * 创建连接
     *
     * @param dbDriver
     * @param url
     * @param userName
     * @param passWord
     * @return
     * @throws Exception
     */
    public static Connection buildConnection(DBDriver dbDriver, String url, String userName, String passWord) throws Exception {
        if (dbDriver == null) {
            throw new IllegalArgumentException("数据库驱动：dbDriver 不能为null");
        }
        Connection connection = null;
        Class.forName(dbDriver.getDriver()).newInstance();
        connection = DriverManager.getConnection(url, userName, passWord);
        return connection;
    }
    public static Connection buildConnection(DataBaseBean db) throws Exception {
        Connection connection = null;
        Class.forName(db.getDriver()).newInstance();
        connection = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPwd());
        return connection;
    }

    public static DBDriver getDbDriver(String driver){
        if (DBDriver.MYSQL.getDriver().equals(driver))
            return DBDriver.MYSQL;
        if (DBDriver.ORACLE.getDriver().equals(driver))
            return DBDriver.ORACLE;
        return null;
    }

}
