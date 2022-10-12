package net.cnbec.catering.bean;

import java.io.Serializable;

public class PlantInfoBean implements Serializable {
    /**
     * code : Z8-BMZ2-2601-1@1704-1
     * collectId : 10
     * isCollect : true
     * codeOne : Z8-BMZ2-2601-1
     * id : 1
     * codeTwo : 1704-1
     * hybridName : 美人酥x南水
     * nextCode : Z8-BMZ2-2601-2@1704-2
     *
     * {"msg":"操作成功","code":200,"data":{"code":"Z8-BMZ2-2601-1@1704-1","collectId":10,"isCollect":true,"hybridId":1,"codeOne":"Z8-BMZ2-2601-1","id":1,"codeTwo":"1704-1","hybridName":"美人酥x南水","nextCode":"Z8-BMZ2-2601-2@1704-2"}}
     */

    private String code;
    private Integer collectId;
    private boolean isCollect;
    private Integer collectLevel;
    private String codeOne;
    private Integer id;
    private String codeTwo;
    private Integer hybridId;
    private String hybridName;
    private String nextCode;
    private String prevCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public Integer getCollectLevel() {
        return collectLevel;
    }

    public void setCollectLevel(Integer collectLevel) {
        this.collectLevel = collectLevel;
    }

    public boolean isIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public String getCodeOne() {
        return codeOne;
    }

    public void setCodeOne(String codeOne) {
        this.codeOne = codeOne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeTwo() {
        return codeTwo;
    }

    public void setCodeTwo(String codeTwo) {
        this.codeTwo = codeTwo;
    }

    public Integer getHybridId() {
        return hybridId;
    }

    public void setHybridId(Integer hybridId) {
        this.hybridId = hybridId;
    }

    public String getHybridName() {
        return hybridName;
    }

    public void setHybridName(String hybridName) {
        this.hybridName = hybridName;
    }

    public String getNextCode() {
        return nextCode;
    }

    public void setNextCode(String nextCode) {
        this.nextCode = nextCode;
    }

    public String getPrevCode() {
        return prevCode;
    }

    public void setPrevCode(String prevCode) {
        this.prevCode = prevCode;
    }
}
