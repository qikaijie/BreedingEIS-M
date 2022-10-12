package net.cnbec.catering.bean;

import java.io.Serializable;

public class SeedlingRecordBean implements Serializable {

    /**
     * id : 604
     * seedlingParentId : null
     * germplasmId : 1
     * seedlingId : 1
     * seedlingType : 1
     * attributeValues : [{"name":"可溶性糖含量(%)","id":17,"value":"可"},{"name":"每花序花朵数(朵)","id":46,"value":"10"},{"name":"花蕾颜色","id":47,"value":"1:白色"},{"name":"花冠直径(cm)","id":48,"value":"23"},{"name":"花瓣相对位置","id":49,"value":"3:重叠"},{"name":"花瓣形状","id":50,"value":"3:椭圆形"},{"name":"花瓣数(枚)","id":51,"value":"12"},{"name":"柱头位置","id":52,"value":"2:与花药等高"},{"name":"花柱基部茸毛","id":53,"value":"1:有"},{"name":"花药颜色","id":54,"value":"2:淡粉色"},{"name":"雄蕊数目(枚)","id":55,"value":"2"},{"name":"花粉量","id":56,"value":"1:有"},{"name":"花芽萌动期","id":120,"value":"2021-05-14"},{"name":"初花期","id":121,"value":"2021-03-20"},{"name":"盛花期","id":122,"value":"2021-05-14"},{"name":"终花期","id":123,"value":"2021-05-14"},{"name":"果实成熟期","id":124,"value":"2021-05-14"},{"name":"落叶期","id":125,"value":"2021-05-14"},{"name":"果实发育期(d)","id":126,"value":"2021-05-14"},{"name":"营养生长天数(d)","id":127,"value":"23"}]
     * enterMethod : 1
     * createYear : 2021
     * createTime : 2021-05-17 22:38:47
     * createById : 102
     * createByName : testuser
     * germplasmName : 百子菜
     * seedlingCode : Z9-BM1W-1501-15@1602-433
     * seedlingCodeOne : Z9-BM1W-1501-15
     * seedlingCodeTwo : 1602-433
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
    private boolean isCollect;
    private int collectLevel;

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

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getCollectLevel() {
        return collectLevel;
    }

    public void setCollectLevel(int collectLevel) {
        this.collectLevel = collectLevel;
    }
}
