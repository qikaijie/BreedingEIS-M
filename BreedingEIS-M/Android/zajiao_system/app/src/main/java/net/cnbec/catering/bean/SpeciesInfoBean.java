package net.cnbec.catering.bean;

import java.io.Serializable;

public class SpeciesInfoBean implements Serializable {

    /**
     * id : 7
     * name : 梨
     * sort : 30
     */

    private int id;
    private String name;
    private int sort;

    public SpeciesInfoBean() {}
    public SpeciesInfoBean(int id,String name,int sort) {
        this.id = id;
        this.name = name;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
