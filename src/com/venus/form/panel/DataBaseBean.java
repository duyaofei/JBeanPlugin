package com.venus.form.panel;

import java.io.Serializable;

public class DataBaseBean implements Serializable {
    private String url;
    private String user;
    private String pwd;
    private String driver;
    private String aPackage;
    private String basePath;


    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    public String getPackage() {
        return aPackage;
    }

    public void setPackage(final String aPackage) {
        this.aPackage = aPackage;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(final String basePath) {
        this.basePath = basePath;
    }
}