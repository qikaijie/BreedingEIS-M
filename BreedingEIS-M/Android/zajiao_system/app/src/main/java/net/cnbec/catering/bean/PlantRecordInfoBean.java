package net.cnbec.catering.bean;

import android.graphics.Rect;

import java.io.Serializable;
import java.util.List;

public class PlantRecordInfoBean implements Serializable {

    /**
     * id : 2
     * hybridId : 1
     * plantId : 1
     * attributeValues : [{"name":"肉质综合评价","id":3,"value":"还不错"},{"name":"外观总评","id":4,"value":"挺好的"},{"name":"肉质综合评价","id":3,"value":"挺棒的"},{"name":"外观总评","id":4,"value":"挺好看的"},{"name":"可溶固形物含量","id":10,"value":"80%"}]
     * enterMethod : 1
     * createYear : 2020
     * createTime : 2020-06-29 11:02:22
     * createById : 1
     * createByName : admin
     * logList : [{"id":3,"recordId":2,"hybridId":1,"plantId":1,"attributeId":3,"attributeName":"肉质综合评价","attributeValue":"还不错","createTime":"2020-06-29 11:02:22","createYear":"2020"},{"id":4,"recordId":2,"hybridId":1,"plantId":1,"attributeId":4,"attributeName":"外观总评","attributeValue":"挺好的","createTime":"2020-06-29 11:02:22","createYear":"2020"},{"id":5,"recordId":2,"hybridId":1,"plantId":1,"attributeId":3,"attributeName":"肉质综合评价","attributeValue":"挺棒的","createTime":"2020-06-29 11:02:22","createYear":"2020"},{"id":6,"recordId":2,"hybridId":1,"plantId":1,"attributeId":4,"attributeName":"外观总评","attributeValue":"挺好看的","createTime":"2020-06-29 11:02:22","createYear":"2020"},{"id":7,"recordId":2,"hybridId":1,"plantId":1,"attributeId":10,"attributeName":"可溶固形物含量","attributeValue":"80%","createTime":"2020-06-29 11:02:22","createYear":"2020"}]
     * imgList : []
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
    private List<LogListBean> logList;
    private List<ImgListBean> imgList;

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

    public List<LogListBean> getLogList() {
        return logList;
    }

    public void setLogList(List<LogListBean> logList) {
        this.logList = logList;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public static class ImgListBean {
        private Integer id;
        private Integer recordId;
        private Integer hybridId;
        private Integer plantId;
        private Integer type;
        private String path;
        private String remark;
        private String createTime;
        private Integer createById;
        private String createByName;

        private Rect mBounds; // 记录坐标
        public Rect getBounds() {//将你的图片显示坐标字段返回
            return mBounds;
        }
        public void setBounds(Rect bounds) {
            mBounds = bounds;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRecordId() {
            return recordId;
        }

        public void setRecordId(Integer recordId) {
            this.recordId = recordId;
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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
    }

    public static class LogListBean {
        /**
         * id : 3
         * recordId : 2
         * hybridId : 1
         * plantId : 1
         * attributeId : 3
         * attributeName : 肉质综合评价
         * attributeValue : 还不错
         * createTime : 2020-06-29 11:02:22
         * createYear : 2020
         */

        private Integer id;
        private Integer recordId;
        private Integer hybridId;
        private Integer plantId;
        private Integer attributeId;
        private String attributeName;
        private String attributeValue;
        private String createTime;
        private String createYear;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRecordId() {
            return recordId;
        }

        public void setRecordId(Integer recordId) {
            this.recordId = recordId;
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

        public Integer getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(Integer attributeId) {
            this.attributeId = attributeId;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeValue() {
            return attributeValue;
        }

        public void setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateYear() {
            return createYear;
        }

        public void setCreateYear(String createYear) {
            this.createYear = createYear;
        }
    }
}
