package com.venus.form;

import com.venus.DBDriver;

import java.io.*;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/12 下午21:46
 * 文件描述:
 * 修改描述：
 */
public class MainFormBean implements Serializable {
    private String dirver;
    private String url;
    private String user;
    private String pwd;
    private String packAge;
    private String basePath;
    private String template;
    private String sqlText;

    private DBDriver dbDriver;
    //static 不会序列化
    private static MainFormBean me = new MainFormBean();

    private MainFormBean() {}

    public static MainFormBean me(){
        return me;
    }

    /**
     * 序列化
     * @throws java.io.IOException
     */
    public void serialize() throws IOException {
        //序列化对象
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objectFile.data"));
        out.writeObject(this);
        out.flush();
        out.close();
    }

    /**
     * 反序列化
     * @return
     * @throws Exception
     */
    public static MainFormBean deserialize() throws Exception{
        //反序列化对象　
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(("objectFile.data")));
        MainFormBean obj = (MainFormBean) in.readObject();
        in.close();
        return obj;
    }
//    ---------------------------------------------------------------------------------------------

    public String getDirver() {
        return dirver;
    }

    public void setDirver(final String dirver) {
        this.dirver = dirver;
    }

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

    public String getPackAge() {
        return packAge;
    }

    public void setPackAge(final String packAge) {
        this.packAge = packAge;
    }

    public DBDriver getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(DBDriver dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
