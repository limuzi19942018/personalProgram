package com.example.demo_activity.test1.comenum;

/**
 * @Author: yongl
 * @DATE: 2020/4/30 17:25
 */

public enum TemplatePathEnum {
    /**
     * 报告框架模板
     */
    reportFrameTemplate(1,"report/frameTemplate/"),

    /**
     * 说明框架模板
     */
    descFrameTemplate(2,"desc/frameTemplate/"),

    /**
     * 报告的内容模板
     */
    reportContentTemplate(3,"report/contentTemplate/"),

    /**
     * 说明的内容模板
     */
    descContentTemplate(4,"report/contentTemplate/"),

    /**
     * 资产基础法的作价表模板
     */
    basicTemplate(5,"basic/template/"),

    /**
     * 收益法的作价表模板
     */
    incomeTemplate(6,"income/template/"),

    /**
     * 市场法的作价表模板
     */
    marketTemplate(7,"market/template/");

    int type;
    String exAlypath;

    /**
     * 构造方法不能少
     * @param type
     * @param exAlypath
     */
    TemplatePathEnum(int type, String exAlypath) {
        this.type = type;
        this.exAlypath = exAlypath;
    }
    /**
     * get set方法
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExAlypath() {
        return exAlypath;
    }

    public void setExAlypath(String exAlypath) {
        this.exAlypath = exAlypath;
    }


    public static String getExPath(Integer type) {
        if (type == null) {
            return null;
        } else {
            for (TemplatePathEnum s : TemplatePathEnum.values()) {
                if (s.getType() == type) {
                    return s.getExAlypath();
                }
            }
            return null;
        }
    }

}
