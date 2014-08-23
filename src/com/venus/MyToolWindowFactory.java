package com.venus;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.venus.form.Msg;
import com.venus.form.MainForm;

import java.awt.*;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/12 下午19:16
 * 文件描述:
 * 修改描述：
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private MainForm mainForm;

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {

        mainForm = new MainForm();
        mainForm.getHomePanel().setMsg(new DefMsg());
        mainForm.getDataSourcePanel().attach(mainForm.getHomePanel());
/*        mainForm.project = project;
        try {
            mainForm.setData(MainFormBean.deserialize());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
        }*/
        //获取上下文工厂
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取窗体上下文
        Content content = contentFactory.createContent(mainForm.getRootPanel(), "", false);
        //将窗体上下文加入到toolWindow
        toolWindow.getContentManager().addContent(content);
    }

    class DefMsg implements Msg {

        @Override
        public void showMsg(String msg, int type) {
            switch (type){
                case Msg.SUCCESS:
                    mainForm.getMsgLabel().setForeground(Color.blue);
                    break;
                case Msg.FAIL:
                    mainForm.getMsgLabel().setForeground(Color.red);
                    break;
            }
            mainForm.getMsgLabel().setText(msg);
        }
    }
}
