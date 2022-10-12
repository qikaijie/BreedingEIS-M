package net.cnbec.catering.bean;

import java.io.Serializable;

public class PlantHistoryRecordBean implements Serializable {

    /**
     * id : 599
     * hybridId : 110
     * plantParentId : null
     * plantId : 5148
     * plantType : 1
     * attributeValues : [{"name":"汁液","id":11,"value":"7:多"},{"name":"内质综合评价","id":15,"value":"6:中上"},{"name":"涩味","id":14,"value":"1:有"},{"name":"香气","id":13,"value":"1:无或几乎无"},{"name":"风味","id":12,"value":"3:淡甜"},{"name":"石细胞数量","id":10,"value":"3:少"},{"name":"果肉类型","id":9,"value":"5:脆"},{"name":"果肉质地","id":8,"value":"7:细"},{"name":"果心大小","id":5,"value":"5:中"},{"name":"田间评价分级","id":149,"value":"高接观察"},{"name":"果实成熟期-预","id":148,"value":"8月中旬"},{"name":"果个大小","id":147,"value":"中"},{"name":"树势","id":98,"value":"5:中"},{"name":"备注","id":85,"value":"果个中等，阳面着色果面变黄明显，风味太淡，下周再评。"},{"name":"外观综合评价","id":45,"value":"7:好"},{"name":"果面光滑度","id":44,"value":"7:平滑"},{"name":"萼片状态","id":39,"value":"1:脱落"},{"name":"果点明显程度","id":31,"value":"5:中等"},{"name":"果锈数量","id":29,"value":"1:无或极少"},{"name":"果心大小","id":5,"value":"5:中"},{"name":"果实底色","id":25,"value":"2:绿黄"},{"name":"果实形状","id":24,"value":"2:圆形"},{"name":"内质综合评价","id":15,"value":"6:中上"},{"name":"涩味","id":14,"value":"1:有"},{"name":"香气","id":13,"value":"1:无或几乎无"},{"name":"风味","id":12,"value":"3:淡甜"},{"name":"汁液","id":11,"value":"7:多"},{"name":"石细胞数量","id":10,"value":"3:少"},{"name":"果肉类型","id":9,"value":"5:脆"},{"name":"果肉质地","id":8,"value":"7:细"},{"name":"果实形状","id":24,"value":"2:圆形"},{"name":"果实底色","id":25,"value":"2:绿黄"},{"name":"果锈数量","id":29,"value":"1:无或极少"},{"name":"果点明显程度","id":31,"value":"5:中等"},{"name":"萼片状态","id":39,"value":"1:脱落"},{"name":"果面光滑度","id":44,"value":"7:平滑"},{"name":"外观综合评价","id":45,"value":"7:好"},{"name":"树势","id":98,"value":"5:中"}]
     * enterMethod : 1
     * createYear : 2021
     * createTime : 2021-05-15 13:11:54
     * createById : 102
     * createByName : testuser
     * hybridName : 玉露香x早冠
     * plantCode : Z1-BMB1-1701-213@ZY-89
     * plantCodeOne : Z1-BMB1-1701-213
     * plantCodeTwo : ZY-89
     * isCollect : null
     * collectLevel : null
     */

    private Integer id;
    private Integer hybridId;
    private Integer plantParentId;
    private Integer plantId;
    private Integer plantType;
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
    private Boolean isCollect;
    private Integer collectLevel;

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

    public Integer getPlantParentId() {
        return plantParentId;
    }

    public void setPlantParentId(Integer plantParentId) {
        this.plantParentId = plantParentId;
    }

    public Integer getPlantId() {
        return plantId;
    }

    public void setPlantId(Integer plantId) {
        this.plantId = plantId;
    }

    public Integer getPlantType() {
        return plantType;
    }

    public void setPlantType(Integer plantType) {
        this.plantType = plantType;
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
