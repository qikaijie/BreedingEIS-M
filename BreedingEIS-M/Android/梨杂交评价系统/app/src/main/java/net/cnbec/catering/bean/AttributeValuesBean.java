package net.cnbec.catering.bean;

import java.io.Serializable;

public class AttributeValuesBean implements Serializable {

    /**
     * name : 汁液
     * id : 11
     * value : 7:多
     */

    private String name;
    private int id;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
