package com.venus.form;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-22 下午10:51
 * 文件描述:消息通知
 * 修改描述:
 */
public interface Msg {
    public final static int SUCCESS=1;//成功视图
    public final static int FAIL=2;//失败试图
    public void showMsg(String msg, int type);
}
