package net.cnbec.catering.network.requestBean;

public class CollectAddRequest1 {

    private Integer level;
    private Integer seedlingId;

    public CollectAddRequest1(Integer level, Integer seedlingId){
        this.level = level;
        this.seedlingId = seedlingId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(Integer seedlingId) {
        this.seedlingId = seedlingId;
    }
}
