package com.venus.form.panel;

import com.venus.DBDriver;
import com.venus.entity.DBTable;
import com.venus.form.IObserver;
import com.venus.form.Msg;
import com.venus.helper.DBHelper;
import com.venus.helper.FileHelper;
import com.venus.helper.SerializableHelper;
import com.venus.helper.StringHelper;
import com.venus.service.DBTableToJavaBean;
import com.venus.service.SqlExecutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Vector;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午3:45
 * 文件描述:
 * 修改描述:
 */
public class HomePanel implements IObserver {
    private JPanel homePanel;
    private JEditorPane sqlText;
    private JButton exeBtn;
    private JList<String> tableList;
    private JTable describeTable;
    private JButton convertBtn;
    private JRadioButton regRadio;
    private JRadioButton sqlRadio;

    private DataBaseBean dataBaseBean;
    private TemplateBean templateBean;

    private DBTable dbTable;

    /**
     * Sql执行器
     */
    private SqlExecutor sqlExecutor;
    /**
     * 消息通知
     */
    private Msg msg;

    public HomePanel() {

        msg = new Msg() {
            @Override
            public void showMsg(String msg, int type) {

            }
        };

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行Sql
        exeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (regRadio.isSelected()) {
                    String reg = StringHelper.toUpper(sqlText.getText());
                    viewTables("".equals(reg)? ".*":reg);
                } else if (sqlRadio.isSelected()) {
                    String[] strs = sqlText.getText().split("#");
                    if (strs.length != 2) {
                        msg.showMsg("标准格式：类名#SQL语句\n样例：User#select * from user", Msg.FAIL);
                    } else {
                        dbTable = sqlExecutor.fetchDBTable(strs[0], strs[1]);
                        updateDescribeTable();
                    }

                }
            }

        });
        //展现信息
        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Object obj = tableList.getSelectedValue();
                if (obj != null) {
                    msg.showMsg(obj.toString(), Msg.SUCCESS);
                    dbTable = sqlExecutor.fetchDBTable(obj.toString());
                    updateDescribeTable();
                }
            }
        });
        //转换
        convertBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                msg.showMsg("玩命转换中.....", Msg.SUCCESS);
                try {
                    templateBean = SerializableHelper.unSerializable(TemplateBean.class);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                DBTableToJavaBean dBTableToJavaBean = new DBTableToJavaBean(dbTable,
                        dataBaseBean.getPackage(),
                        templateBean == null ? "" : templateBean.getTemplate());

                try {
                    FileHelper.writeString(dataBaseBean.getBasePath() + File.separator +
                                    dataBaseBean.getPackage().replace(".", File.separator),
                            StringHelper.toHumpFirstUpper(dbTable.getTableName()) + ".java",
                            dBTableToJavaBean.doIt());
                    msg.showMsg("转换成功", Msg.SUCCESS);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    msg.showMsg(e1.getMessage(), Msg.FAIL);
                }
            }
        });
        regRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (regRadio.isSelected()) {
                    sqlText.setText(".*");
                } else {
                    sqlText.setText("表名#SQL语句");
                }
            }
        });
    }


    /**
     * 展示表格
     *
     * @param regStr
     */
    private void viewTables(String regStr) {
        msg.showMsg("玩命加载中.....", Msg.SUCCESS);
        try {

            DefaultListModel<String> listModel = new DefaultListModel<String>();
            for (String tableName : sqlExecutor.queryTableNames()) {
                if (regStr == null || tableName.matches(regStr)) {
                    listModel.addElement(tableName);
                }
            }

            tableList.setModel(listModel);
            msg.showMsg("加载完成，共【" + listModel.getSize() + "】个表格", Msg.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            msg.showMsg(e.getMessage(), Msg.FAIL);
        }
    }


    /**
     * 更新
     */
    private void updateDescribeTable() {
        try {
            Vector<String> colNames = new Vector<String>();
            colNames.add("类型");
            colNames.add("字段");
            colNames.add("注释");

            Vector<Vector<Serializable>> datas = new Vector<Vector<Serializable>>();

            if (dbTable != null) {
                for (int i = 0; i < dbTable.getColumnCount(); i++) {
                    Vector<Serializable> data = new Vector<Serializable>();
                    data.add(dbTable.getDataType(i));
                    data.add(dbTable.getFieldName(i));
                    data.add(dbTable.getComments(i));
                    datas.add(data);
                }
            }
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setDataVector(datas, colNames);
            describeTable.setModel(tableModel);

        } catch (Exception e1) {
            e1.printStackTrace();
            msg.showMsg(e1.getMessage(), Msg.FAIL);
        }
    }


    /**
     * 初始化
     */
    private void init() throws Exception {
        dataBaseBean = SerializableHelper.unSerializable(DataBaseBean.class);
        templateBean = SerializableHelper.unSerializable(TemplateBean.class);
        DBDriver dbDriver = DBHelper.getDbDriver(dataBaseBean.getDriver());
        Connection conn = DBHelper.buildConnection(dataBaseBean);
        sqlExecutor = new SqlExecutor(conn, dbDriver);
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("HomePanel");
        frame.setContentPane(new HomePanel().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update() {
        try {
            init();
            msg.showMsg("配置信息已更新", Msg.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            msg.showMsg(e.getMessage(), Msg.FAIL);
        }
    }
}
