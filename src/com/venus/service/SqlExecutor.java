package com.venus.service;


import com.venus.DBDriver;
import com.venus.entity.ColMetaData;
import com.venus.entity.DBTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午23:03
 * 文件描述:
 * 修改描述：
 */
public class SqlExecutor {
    private Connection conn;
    private DBDriver dbDriver;

    public SqlExecutor(Connection conn) {
        this(conn, DBDriver.MYSQL);
    }

    public SqlExecutor(Connection conn, DBDriver dbDriver) {
        this.conn = conn;
        this.dbDriver = dbDriver;
    }

    /**
     * 检索所有表名
     *
     * @return
     */
    public ArrayList<String> queryTableNames() {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery(dbDriver.getShowTablesSql());
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    /**
     * 获取表实体
     *
     * @param tableName
     * @return
     */
    public DBTable fetchDBTable(String tableName) {

        String descTable = dbDriver.getDescTable(tableName);

        DBTable dbTable = new DBTable(tableName);
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(descTable);
            while (rs.next()) {
                dbTable.addColMetaData(new ColMetaData(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }

        return dbTable;
    }

    /**
     * 获取表实体
     * @param tableName
     * @param sql
     * @return
     */
    public DBTable fetchDBTable(String tableName, String sql) {
        DBTable dbTable = new DBTable(tableName);
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
            rsmd = rs.getMetaData();

            for(int i=1 ; i <= rsmd.getColumnCount(); i++){
                dbTable.addColMetaData(
                        new ColMetaData(rsmd.getColumnTypeName(i)
                                ,rsmd.getColumnLabel(i)
                                ,rsmd.getSchemaName(i)
                        ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }

        return dbTable;
    }
}
