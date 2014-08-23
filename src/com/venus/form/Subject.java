package com.venus.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 14-8-23 上午9:58
 * 文件描述:
 * 修改描述:
 */
public abstract class Subject {

    private List<IObserver> observers = new ArrayList<IObserver>();

    /**
     * 增加观察者
     * @param observer 观察者
     */
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    /**
     * 移除观察者
     * @param observer 观察者
     */
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * 向观察者（们）发出通知
     */
    public void notifys() {
        for (IObserver o : observers) {
            o.update();
        }
    }
}
