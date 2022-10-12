package net.cnbec.catering.bean;

import java.io.Serializable;

public class SeedlingCollectListBean implements Serializable {

    /**
     * id : 66
     * userId : 102
     * seedlingId : 3
     * level : 2
     * createTime : 2021-05-13 01:20:54
     * userName : testuser
     * seedlingCode : Z9-BM1W-1501-7@1602-425
     * germplasmName : 百子菜
     */

    private int id;
    private int userId;
    private int seedlingId;
    private int level;
    private String createTime;
    private String userName;
    private String seedlingCode;
    private String germplasmName;

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

    public int getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(int seedlingId) {
        this.seedlingId = seedlingId;
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

    public String getSeedlingCode() {
        return seedlingCode;
    }

    public void setSeedlingCode(String seedlingCode) {
        this.seedlingCode = seedlingCode;
    }

    public String getGermplasmName() {
        return germplasmName;
    }

    public void setGermplasmName(String germplasmName) {
        this.germplasmName = germplasmName;
    }
}
