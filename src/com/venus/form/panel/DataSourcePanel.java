package com.venus.form.panel;

import com.venus.DBDriver;
import com.venus.form.Msg;
import com.venus.form.Subject;
import com.venus.helper.DBHelper;
import com.venus.helper.SerializableHelper;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午4:04
 * 文件描述:
 * 修改描述:
 */
public class DataSourcePanel extends Subject {
    private JPanel dataSourcePanel;
    private JTextField urlText;
    private JTextField userText;
    private JTextField pwdText;
    private JComboBox<DBDriver> dbComboBox;
    private JButton testConnBtn;
    private JTextArea dbInfText;
    private JTextField driverText;
    private JButton saveBtn;
    private JTextField packageText;
    private JTextField basePathTest;

    private DBDriver dbDriver;
    private DataBaseBean dataBaseBean;

    private Msg msg;

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public DataSourcePanel() {

        init();

        //切换数据库
        dbComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                dbDriver = (DBDriver) e.getItem();
                driverText.setText(dbDriver.getDriver());
                urlText.setText(dbDriver.getUrlTemplate());
            }
        });
        //测试数据库连接
        testConnBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                dbInfText.setText("");
                dbInfText.setText("正在建立连接...\n");
                msg.showMsg("正在建立连接...",Msg.SUCCESS);
                try {
                    Connection connection = DBHelper
                            .buildConnection(dbDriver, urlText.getText(), userText.getText(), pwdText.getText());

                    if (connection != null) {
                        dbInfText.append("Connection:[" + connection + "]\n");
                        dbInfText.append("连接成功\n");
                        msg.showMsg("连接成功",Msg.SUCCESS);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                    dbInfText.append(e1.getMessage() + "\n");
                    dbInfText.append("连接失败！！");
                    msg.showMsg("连接失败",Msg.FAIL);
                }

            }
        });

        //保存配置信息
        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isModified(dataBaseBean)) {
                    getData(dataBaseBean);
                }
                try {
                    SerializableHelper.serializable(dataBaseBean);
                    msg.showMsg("保存成功", Msg.SUCCESS);
                    notifys();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    msg.showMsg(e1.getMessage(), Msg.FAIL);
                }
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        /*msg 空实现，防止nullpointException*/
        msg = new Msg() {
            @Override
            public void showMsg(String msg, int type) {

            }
        };

        if (dataBaseBean == null) {
            try {
                /*反序列化*/
                dataBaseBean = SerializableHelper.unSerializable(DataBaseBean.class);
            } catch (IOException e) {
                dataBaseBean = new DataBaseBean();
                dataBaseBean.setUrl(DBDriver.MYSQL.getUrlTemplate());
                dataBaseBean.setDriver(DBDriver.MYSQL.getDriver());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        dbDriver = DBHelper.getDbDriver(dataBaseBean.getDriver());
        //初始化数据库选择下拉控件
        DefaultComboBoxModel<DBDriver> comboBoxModel = new DefaultComboBoxModel<DBDriver>();
        comboBoxModel.addElement(DBDriver.MYSQL);
        comboBoxModel.addElement(DBDriver.ORACLE);
        this.dbComboBox.setModel(comboBoxModel);
        /*初始化Form数据*/
        setData(dataBaseBean);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DataSourcePanel");
        frame.setContentPane(new DataSourcePanel().dataSourcePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setData(DataBaseBean data) {
        urlText.setText(data.getUrl());
        userText.setText(data.getUser());
        pwdText.setText(data.getPwd());
        driverText.setText(data.getDriver());
        packageText.setText(data.getPackage());
        basePathTest.setText(data.getBasePath());
        dbComboBox.setSelectedItem(dbDriver);
    }

    public void getData(DataBaseBean data) {
        data.setUrl(urlText.getText());
        data.setUser(userText.getText());
        data.setPwd(pwdText.getText());
        data.setDriver(driverText.getText());
        data.setPackage(packageText.getText());
        data.setBasePath(basePathTest.getText());
    }

    public boolean isModified(DataBaseBean data) {
        if (urlText.getText() != null ? !urlText.getText().equals(data.getUrl()) : data.getUrl() != null) return true;
        if (userText.getText() != null ? !userText.getText().equals(data.getUser()) : data.getUser() != null)
            return true;
        if (pwdText.getText() != null ? !pwdText.getText().equals(data.getPwd()) : data.getPwd() != null) return true;
        if (driverText.getText() != null ? !driverText.getText().equals(data.getDriver()) : data.getDriver() != null)
            return true;
        if (packageText.getText() != null ? !packageText.getText().equals(data.getPackage()) : data.getPackage() != null)
            return true;
        if (basePathTest.getText() != null ? !basePathTest.getText().equals(data.getBasePath()) : data.getBasePath() != null)
            return true;
        return false;
    }

}
