package net.cnbec.catering.bean;

import java.io.Serializable;

public class SeedlingHistoryRecordBean implements Serializable {

    /**
     * id : 598
     * seedlingParentId : 1
     * germplasmId : 1
     * seedlingId : 2
     * seedlingType : 2
     * attributeValues : [{"name":"备注","id":85,"value":"未见花芽萌发，成花差"},{"name":"初花期","id":121,"value":"2021-03-20"}]
     * enterMethod : 1
     * createYear : 2021
     * createTime : 2021-03-18 12:33:39
     * createById : 102
     * createByName : qikaijie
     * germplasmName : 百子菜
     * seedlingCode : Z9-BM1W-1501-24@1602-442
     * seedlingCodeOne : Z9-BM1W-1501-24
     * seedlingCodeTwo : 1602-442
     * isCollect : null
     * collectLevel : null
     */

    private int id;
    private int seedlingParentId;
    private int germplasmId;
    private int seedlingId;
    private int seedlingType;
    private String attributeValues;
    private String enterMethod;
    private String createYear;
    private String createTime;
    private int createById;
    private String createByName;
    private String germplasmName;
    private String seedlingCode;
    private String seedlingCodeOne;
    private String seedlingCodeTwo;
    private Boolean isCollect;
    private Integer collectLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeedlingParentId() {
        return seedlingParentId;
    }

    public void setSeedlingParentId(int seedlingParentId) {
        this.seedlingParentId = seedlingParentId;
    }

    public int getGermplasmId() {
        return germplasmId;
    }

    public void setGermplasmId(int germplasmId) {
        this.germplasmId = germplasmId;
    }

    public int getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(int seedlingId) {
        this.seedlingId = seedlingId;
    }

    public int getSeedlingType() {
        return seedlingType;
    }

    public void setSeedlingType(int seedlingType) {
        this.seedlingType = seedlingType;
    }

    public String getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(String attributeValues) {
        this.attributeValues = attributeValues;
    }

    public String getEnterMethod() {
        return enterMethod;
    }

    public void setEnterMethod(String enterMethod) {
        this.enterMethod = enterMethod;
    }

    public String getCreateYear() {
        return createYear;
    }

    public void setCreateYear(String createYear) {
        this.createYear = createYear;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateById() {
        return createById;
    }

    public void setCreateById(int createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getGermplasmName() {
        return germplasmName;
    }

    public void setGermplasmName(String germplasmName) {
        this.germplasmName = germplasmName;
    }

    public String getSeedlingCode() {
        return seedlingCode;
    }

    public void setSeedlingCode(String seedlingCode) {
        this.seedlingCode = seedlingCode;
    }

    public String getSeedlingCodeOne() {
        return seedlingCodeOne;
    }

    public void setSeedlingCodeOne(String seedlingCodeOne) {
        this.seedlingCodeOne = seedlingCodeOne;
    }

    public String getSeedlingCodeTwo() {
        return seedlingCodeTwo;
    }

    public void setSeedlingCodeTwo(String seedlingCodeTwo) {
        this.seedlingCodeTwo = seedlingCodeTwo;
    }

    public Boolean getCollect() {
        return isCollect;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }

    public Integer getCollectLevel() {
        return collectLevel;
    }

    public void setCollectLevel(Integer collectLevel) {
        this.collectLevel = collectLevel;
    }
}
