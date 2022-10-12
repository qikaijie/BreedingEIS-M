package net.cnbec.catering.bean;

import java.io.Serializable;

public class UserDetailsBean implements Serializable {

    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * dataScope : null
     * params : {}
     * userId : 1
     * deptId : null
     * userName : admin
     * nickName : 若依
     * email : null
     * phonenumber : null
     * sex : 1
     * avatar :
     * password : null
     * salt : null
     * status : null
     * delFlag : null
     * loginIp : null
     * loginDate : null
     * dept : null
     * roles : null
     * roleIds : null
     * postIds : null
     * admin : true
     */

    private Object searchValue;
    private Object createBy;
    private Object createTime;
    private Object updateBy;
    private Object updateTime;
    private Object remark;
    private Object dataScope;
    private ParamsBean params;
    private int userId;
    private Object deptId;
    private String userName;
    private String nickName;
    private Object email;
    private Object phonenumber;
    private String sex;
    private String avatar;
    private Object password;
    private Object salt;
    private Object status;
    private Object delFlag;
    private Object loginIp;
    private Object loginDate;
    private Object dept;
    private Object roles;
    private Object roleIds;
    private Object postIds;
    private boolean admin;

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getDataScope() {
        return dataScope;
    }

    public void setDataScope(Object dataScope) {
        this.dataScope = dataScope;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getDeptId() {
        return deptId;
    }

    public void setDeptId(Object deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Object phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getSalt() {
        return salt;
    }

    public void setSalt(Object salt) {
        this.salt = salt;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public Object getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(Object loginIp) {
        this.loginIp = loginIp;
    }

    public Object getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Object loginDate) {
        this.loginDate = loginDate;
    }

    public Object getDept() {
        return dept;
    }

    public void setDept(Object dept) {
        this.dept = dept;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

    public Object getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Object roleIds) {
        this.roleIds = roleIds;
    }

    public Object getPostIds() {
        return postIds;
    }

    public void setPostIds(Object postIds) {
        this.postIds = postIds;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static class ParamsBean implements Serializable{
    }
}
