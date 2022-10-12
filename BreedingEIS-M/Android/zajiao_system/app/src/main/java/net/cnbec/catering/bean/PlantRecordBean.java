package net.cnbec.catering.bean;

import java.io.Serializable;

public class PlantRecordBean implements Serializable {
    /**
     * id : 1
     * hybridId : 1
     * plantId : 1
     * attributeValues : [{"name":"果实颜色","id":1,"value":"白色"},{"name":"果实硬度","id":2,"value":"中等(10.1-20kg/cm)"}]
     * enterMethod : 1
     * createYear : 2020
     * createTime : 2020-06-22 19:29:55
     * createById : 1
     * createByName : admin
     * hybridName : 美人酥x南水
     * plantCode : Z8-BMZ2-2601-1@1704-1
     * plantCodeOne : Z8-BMZ2-2601-1
     * plantCodeTwo : 1704-1
     */

    private Integer id;
    private Integer hybridId;
    private Integer plantId;
    private String attributeValues;
    private String enterMethod;
    private String createYear;
    private String createTime;
    private Integer createById;
    private String createByName;
    private String hybridName;
    private String plantCode;
    private String plantCodeOne;
    private String plantCodeTwo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHybridId() {
        return hybridId;
    }

    public void setHybridId(Integer hybridId) {
        this.hybridId = hybridId;
    }

    public Integer getPlantId() {
        return plantId;
    }

    public void setPlantId(Integer plantId) {
        this.plantId = plantId;
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

    public Integer getCreateById() {
        return createById;
    }

    public void setCreateById(Integer createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getHybridName() {
        return hybridName;
    }

    public void setHybridName(String hybridName) {
        this.hybridName = hybridName;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getPlantCodeOne() {
        return plantCodeOne;
    }

    public void setPlantCodeOne(String plantCodeOne) {
        this.plantCodeOne = plantCodeOne;
    }

    public String getPlantCodeTwo() {
        return plantCodeTwo;
    }

    public void setPlantCodeTwo(String plantCodeTwo) {
        this.plantCodeTwo = plantCodeTwo;
    }
}
