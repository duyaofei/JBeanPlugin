package com.venus.service;

import com.venus.entity.DBTable;
import com.venus.helper.StringHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午20:35
 * 文件描述:
 * 修改描述：
 */
public class DBTableToJavaBean {

    private final DBTable entity;


    private String pack;

    private StringBuilder import_sb;
    private String template;
    private StringBuilder field_sb;
    private StringBuilder get_set_sb;

    private StringBuilder context;


    public DBTableToJavaBean(DBTable dbTable, String pack, String template) {
        this.entity = dbTable;
        this.pack = pack;
        this.template = template;
        this.import_sb = new StringBuilder();
        this.field_sb = new StringBuilder();
        this.get_set_sb = new StringBuilder();
        this.context = new StringBuilder();
    }


    private void init() {

        if (template != null) {
            String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
            String time = (new SimpleDateFormat("HH:mm:ss")).format(new Date());
            template = template.replaceAll("\\$\\{(?i)DATE\\}", date).replaceAll("\\$\\{(?i)TIME\\}", time);
        }

        Map<String, String> importMap = new HashMap<String, String>();
        for (int i = 0; i < entity.getColumnCount(); i++) {
            //字段类型
            String javaType = entity.getSimpleJavaType(i);
            //首字母小写驼峰格式
            String fn = StringHelper.toHumpFirstLower(entity.getFieldName(i));
            //首字母大写驼峰格式
            String Fn = StringHelper.toHumpFirstUpper(entity.getFieldName(i));

            String comment = entity.getComments(i);


            if (!importMap.containsKey(javaType)) {
                import_sb.append("import ").append(entity.getJavaType(i)).append(";\n");
                importMap.put(javaType, "");
            }


            field_sb.append("\t@Column(\"" + entity.getFieldName(i) + "\")\n");
            field_sb.append("\tprivate ").append(javaType).append(" ").append(fn).append(";//").append(comment).append("\n");

            get_set_sb.append("\n\tpublic ").append(javaType).append(" get").append(Fn).append("(){\n")
                    .append("\t\treturn this.").append(fn).append(";\n")
                    .append("\t}\n")

                    .append("\n\tpublic void set").append(Fn).append("(").append(javaType).append(" ").append(fn).append("){\n")
                    .append("\t\tthis.").append(fn).append(" = ").append(fn).append(";\n")
                    .append("\t}\n");


        }
        context.append(pack == null ? "" : "package " + pack + ";\n")
                .append(template == null ? "" : template + "\n")
                .append(import_sb).append("\n")
                .append("@Table(\"" + entity.getTableName() + "\")")//添加表名注解
                .append("\npublic class ").append(StringHelper.toHumpFirstUpper(entity.getTableName())).append(" {\n\n")
                .append(field_sb).append("\n")
                .append(get_set_sb).append("\n")
                .append("}");
    }

    public String doIt() {
        init();
        return toString();
    }

    @Override
    public String toString() {
        return context.toString();
    }
}
