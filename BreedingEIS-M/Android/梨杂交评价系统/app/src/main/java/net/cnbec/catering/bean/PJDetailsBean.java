package net.cnbec.catering.bean;

import java.util.List;

public class PJDetailsBean {
    private String titleStr;

    private List<PJKVBean> itemMaps;

    public PJDetailsBean(String titleStr, List<PJKVBean> maps){
        this.titleStr = titleStr;
        this.itemMaps = maps;

    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public List<PJKVBean> getItemMaps() {
        return itemMaps;
    }

    public void setItemMaps(List<PJKVBean> itemMaps) {
        this.itemMaps = itemMaps;
    }
}
