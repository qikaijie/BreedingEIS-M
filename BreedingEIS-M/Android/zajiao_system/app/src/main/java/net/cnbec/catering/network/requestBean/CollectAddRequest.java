package net.cnbec.catering.network.requestBean;

public class CollectAddRequest {

    private Integer level;
    private Integer plantId;

    public CollectAddRequest(Integer level,Integer plantId){
        this.level = level;
        this.plantId = plantId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPlantId() {
        return plantId;
    }

    public void setPlantId(Integer plantId) {
        this.plantId = plantId;
    }
}
