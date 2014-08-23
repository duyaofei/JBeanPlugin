package com.venus.form.panel;

import com.venus.DBDriver;
import com.venus.entity.DBTable;
import com.venus.helper.DBHelper;
import com.venus.helper.SerializableHelper;
import com.venus.helper.StringHelper;
import com.venus.service.SqlExecutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午3:45
 * 文件描述:
 * 修改描述:
 */
public class sqlRadio {
    private JPanel homePanel;
    private JEditorPane sqlText;
    private JButton exeBtn;
    private JList tableList;
    private JTable describeTable;
    private JButton convertBtn;
    private JRadioButton regRadio;
    private JRadioButton sqlRadio;

    private DataBaseBean dataBaseBean;
    private DBDriver dbDriver;
    public sqlRadio() {

        //执行Sql
        exeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(dataBaseBean==null){
                    try {
                        dataBaseBean = SerializableHelper.unSerializable(DataBaseBean.class);
                        dbDriver = DBHelper.getDbDriver(dataBaseBean.getDriver());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "找不到数据库连接信息，是否还未设置，或没有保存？！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }

                if(StringHelper.isEmpty(sqlText.getText())){
                    viewTables();
                }
            }
        });
        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                viewTableInf(tableList.getSelectedValue().toString());
            }
        });
    }


    //展示表格
    private void viewTables(){
        try {
            Connection conn = DBHelper.buildConnection(dataBaseBean);
            SqlExecutor sqlExecutor = new SqlExecutor(conn,dbDriver);

            DefaultListModel listModel = new DefaultListModel();
            for(String tableName : sqlExecutor.queryTableNames()){
                listModel.addElement(tableName);
            }

            tableList.setModel(listModel);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
    //展示表信息
    private void viewTableInf(String tableName) {
        try {
            Connection conn = DBHelper.buildConnection(dataBaseBean);

            SqlExecutor sqlExecutor = new SqlExecutor(conn,dbDriver);

            DBTable dbTable = sqlExecutor.fetchDBTable(tableName);

            Vector<String> colNames = new Vector<String>();
            colNames.add("类型");
            colNames.add("字段");
            colNames.add("注释");

            Vector<Vector<java.io.Serializable>> datas = new Vector<Vector<java.io.Serializable>>();

            if (dbTable != null) {
                for (int i = 0; i < dbTable.getColumnCount(); i++) {
                    Vector<java.io.Serializable> data = new Vector<java.io.Serializable>();
                    data.add(dbTable.getFieldName(i));
                    data.add(dbTable.getDataType(i));
                    data.add(dbTable.getComments(i));
                    datas.add(data);
                }
            }
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setDataVector(datas, colNames);
            describeTable.setModel(tableModel);

        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示", JOptionPane.WARNING_MESSAGE);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("HomePanel");
        frame.setContentPane(new com.venus.form.panel.sqlRadio().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
