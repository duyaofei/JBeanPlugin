package com.venus;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午14:48
 * 文件描述:数据库枚举
 * 修改描述：
 */
public enum DBDriver {

    MYSQL("com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306",
            "SHOW TABLES",
            "SELECT DATA_TYPE,COLUMN_NAME,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='${TABLE_NAME}'"),
    ORACLE("oracle.jdbc.OracleDriver",
            "jdbc:oracle:thin:@//localhost:1521",
            "SELECT TABLE_NAME FROM USER_TABLES",
            "SELECT B.DATA_TYPE, A.COLUMN_NAME, A.COMMENTS FROM USER_COL_COMMENTS A,USER_TAB_COLUMNS B WHERE A.TABLE_NAME = '${TABLE_NAME}' AND B.TABLE_NAME = '${TABLE_NAME}' AND A.COLUMN_NAME = B.COLUMN_NAME"),;

    /**
     *
     * @param driver
     * @param urlTemplate
     * @param showTablesSql
     * @param showColCommSql
     */
    DBDriver(String driver, String urlTemplate, String showTablesSql, String showColCommSql) {
        this.driver = driver;
        this.urlTemplate = urlTemplate;
        this.showTablesSql = showTablesSql;
        this.descTable = showColCommSql;
    }

    /*驱动*/
    private String driver;
    /*url模板*/
    private String urlTemplate;
    /*查询库中所有表SQL*/
    private String showTablesSql;

    private String descTable;


    public String getDriver() {
        return driver;
    }

    public String getShowTablesSql() {
        return showTablesSql;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public String getDescTable(String tableName) {
        return descTable.replaceAll("\\$\\{TABLE_NAME\\}",tableName);
    }

    public static void main(String[] args) {
        System.out.println(DBDriver.ORACLE.getDriver());
    }
}
