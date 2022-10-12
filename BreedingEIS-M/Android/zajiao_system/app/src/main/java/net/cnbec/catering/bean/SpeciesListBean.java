package net.cnbec.catering.bean;

import java.io.Serializable;

public class SpeciesListBean implements Serializable {

    /**
     * id : 1
     * name : 葡萄
     * sort : 0
     */

    private int id;
    private String name;
    private int sort;

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
