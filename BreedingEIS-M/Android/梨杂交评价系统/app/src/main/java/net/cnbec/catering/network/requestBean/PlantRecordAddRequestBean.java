package net.cnbec.catering.network.requestBean;

import java.io.Serializable;
import java.util.List;

public class PlantRecordAddRequestBean implements Serializable {

    /**
     * hybridId : 0
     * imgList : [{"path":"string","type":0}]
     * logList : [{"attributeId":0,"attributeName":"string","attributeValue":"string"}]
     * plantId : 0
     */

    private Integer hybridId;
    private Integer plantId;
    private List<ImgListBean> imgList;
    private List<LogListBean> logList;

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

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public List<LogListBean> getLogList() {
        return logList;
    }

    public void setLogList(List<LogListBean> logList) {
        this.logList = logList;
    }

    public static class ImgListBean {
        /**
         * path : string
         * type : 0
         */

        private String path;
        private Integer type;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    public static class LogListBean {
        /**
         * attributeId : 0
         * attributeName : string
         * attributeValue : string
         */

        private Integer attributeId;
        private String attributeName;
        private String attributeValue;

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
    }
}
