package net.cnbec.catering.bean;

import java.io.Serializable;

public class SeedlingInfoBean implements Serializable {

    /**
     * prevCode : Z9-BM1W-1501-14@1602-432
     * code : Z9-BM1W-1501-15@1602-433
     * collectId : 64
     * germplasmId : 1
     * isCollect : true
     * germplasmName : 百子菜
     * codeOne : Z9-BM1W-1501-15
     * id : 1
     * codeTwo : 1602-433
     * collectLevel : 1
     * nextCode : Z9-BM1W-1501-16@1602-434
     */

    private String prevCode;
    private String code;
    private int collectId;
    private int germplasmId;
    private boolean isCollect;
    private String germplasmName;
    private String codeOne;
    private int id;
    private String codeTwo;
    private int collectLevel;
    private String nextCode;

    public String getPrevCode() {
        return prevCode;
    }

    public void setPrevCode(String prevCode) {
        this.prevCode = prevCode;
    }

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

    public int getGermplasmId() {
        return germplasmId;
    }

    public void setGermplasmId(int germplasmId) {
        this.germplasmId = germplasmId;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getGermplasmName() {
        return germplasmName;
    }

    public void setGermplasmName(String germplasmName) {
        this.germplasmName = germplasmName;
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

    public int getCollectLevel() {
        return collectLevel;
    }

    public void setCollectLevel(int collectLevel) {
        this.collectLevel = collectLevel;
    }

    public String getNextCode() {
        return nextCode;
    }

    public void setNextCode(String nextCode) {
        this.nextCode = nextCode;
    }
}
