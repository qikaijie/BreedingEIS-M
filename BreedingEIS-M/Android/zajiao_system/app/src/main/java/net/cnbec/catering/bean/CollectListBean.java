package net.cnbec.catering.bean;

import java.io.Serializable;

public class CollectListBean implements Serializable {
    /**
     * id : 98
     * userId : 102
     * plantId : 1
     * level : 1
     * createTime : 2021-05-14 00:46:28
     * userName : testuser
     * plantCode : Z9-BM1W-1501-15@1602-433
     * hybridYear : 2016
     * hybridName : 雪花x玉露香
     */

    private int id;
    private int userId;
    private int plantId;
    private int level;
    private String createTime;
    private String userName;
    private String plantCode;
    private String hybridYear;
    private String hybridName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getHybridYear() {
        return hybridYear;
    }

    public void setHybridYear(String hybridYear) {
        this.hybridYear = hybridYear;
    }

    public String getHybridName() {
        return hybridName;
    }

    public void setHybridName(String hybridName) {
        this.hybridName = hybridName;
    }
}
