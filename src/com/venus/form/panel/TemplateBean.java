package com.venus.form.panel;

import java.io.Serializable;

public class TemplateBean implements Serializable {
    private String template;

    public TemplateBean() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }
}