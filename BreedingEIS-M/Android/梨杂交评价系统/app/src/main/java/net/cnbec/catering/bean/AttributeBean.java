package net.cnbec.catering.bean;

import java.io.Serializable;

/**
 * @Describe: //TODO
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class AttributeBean implements Serializable {

    /**
     * 0    标题
     * 1    题目
     */
    private int type;

    private String name;

    private GroupListBean.AttributeListBean attributeListBean;

    public AttributeBean(){}
    public AttributeBean(String name){
        this.type = 0;
        this.name = name;
    }
    public AttributeBean(String name,GroupListBean.AttributeListBean attributeListBean){
        this.type = 1;
        this.name = name;
        this.attributeListBean = attributeListBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupListBean.AttributeListBean getAttributeListBean() {
        return attributeListBean;
    }

    public void setAttributeListBean(GroupListBean.AttributeListBean attributeListBean) {
        this.attributeListBean = attributeListBean;
    }
}
