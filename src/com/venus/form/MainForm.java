package com.venus.form;

import com.venus.form.panel.DataSourcePanel;
import com.venus.form.panel.HomePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午3:05
 * 文件描述:
 * 修改描述:
 */
public class MainForm {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JLabel msgLabel;
    private HomePanel homePanel;
    private DataSourcePanel dataSourcePanel;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JLabel getMsgLabel() {
        return msgLabel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public DataSourcePanel getDataSourcePanel() {
        return dataSourcePanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaBean生成器");
        final MainForm mainForm = new MainForm();
        mainForm.getDataSourcePanel().attach(mainForm.getHomePanel());
        mainForm.homePanel.setMsg(new Msg() {
            @Override
            public void showMsg(String msg,int type) {
                switch (type){
                    case Msg.SUCCESS:
                        mainForm.msgLabel.setForeground(Color.blue);
                        break;
                    case Msg.FAIL:
                        mainForm.msgLabel.setForeground(Color.red);
                        break;
                }
                mainForm.msgLabel.setText(msg);

            }
        });
        frame.setContentPane(mainForm.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

}
