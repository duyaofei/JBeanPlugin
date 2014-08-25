package com.venus.form.panel;

import com.venus.form.Msg;
import com.venus.form.Subject;
import com.venus.helper.SerializableHelper;
import org.junit.Assert;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-14 下午10:44
 * 文件描述:
 * 修改描述:
 */
public class TemplatePanel extends Subject {
    private JPanel templatePanel;
    private JTextArea templateText;
    private JButton saveBtn;

    private TemplateBean templateBean;

    private Msg msg;

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public TemplatePanel() {

        /*实例化一个空的 msg 实现，防止用户没有设置 msg 时 出现nullpoint异常*/
        msg = new Msg() {
            @Override
            public void showMsg(String msg, int type) {

            }
        };

        if (templateBean == null) {
            try {
                templateBean = SerializableHelper.unSerializable(TemplateBean.class);
            } catch (IOException e) {
                e.printStackTrace();
                templateBean = new TemplateBean();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        /*初始化Form*/
        setData(templateBean);

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isModified(templateBean)) {
                    getData(templateBean);
                    try {
                        SerializableHelper.serializable(templateBean);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        msg.showMsg(e1.getMessage(), Msg.FAIL);
                    }
                    msg.showMsg("保存成功", Msg.SUCCESS);
                }


            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TemplatePanel");
        frame.setContentPane(new TemplatePanel().templatePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public void setData(TemplateBean data) {
        templateText.setText(data.getTemplate());
    }

    public void getData(TemplateBean data) {
        data.setTemplate(templateText.getText());
    }

    public boolean isModified(TemplateBean data) {
        if (templateText.getText() != null ? !templateText.getText().equals(data.getTemplate()) : data.getTemplate() != null)
            return true;
        return false;
    }

}
