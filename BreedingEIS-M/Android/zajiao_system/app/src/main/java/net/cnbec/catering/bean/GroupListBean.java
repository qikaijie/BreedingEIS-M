package net.cnbec.catering.bean;

import net.cnbec.catering.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupListBean implements Serializable {

    /**
     * id : 1
     * name : 内在品质
     * sort : 1
     * createById : 1
     * createByName : admin
     * createTime : 2020-06-22T14:38:49.000+0800
     * attributeList : [{"id":1,"fieldType":"checkbox","fieldCode":null,"fieldName":"果实颜色","fieldContent":"白色|乳白色|淡黄色","sort":1,"createById":1,"createTime":"2020-06-22T14:31:15.000+0800","createByName":"admin","groupId":null},{"id":2,"fieldType":"radio","fieldCode":null,"fieldName":"果实硬度","fieldContent":"极硬(30kg/cm以上)|硬(20.1-30kg/cm)|中等(10.1-20kg/cm)|软(5.1-10kg/cm)|极软(5kg/cm以下)","sort":2,"createById":1,"createTime":"2020-06-22T14:35:47.000+0800","createByName":"admin","groupId":null},{"id":3,"fieldType":"input","fieldCode":null,"fieldName":"肉质综合评价","fieldContent":null,"sort":3,"createById":1,"createTime":"2020-06-22T14:37:08.000+0800","createByName":"admin","groupId":null},{"id":4,"fieldType":"text","fieldCode":null,"fieldName":"外观总评","fieldContent":null,"sort":4,"createById":1,"createTime":"2020-06-22T14:37:55.000+0800","createByName":"admin","groupId":null}]
     */

    private Integer id;
    private String name;
    private Integer sort;
    private Integer createById;
    private String createByName;
    private String createTime;
    private List<AttributeListBean> attributeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<AttributeListBean> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<AttributeListBean> attributeList) {
        this.attributeList = attributeList;
    }

    public static class AttributeListBean implements Serializable {
        /**
         * id : 1
         * fieldType : checkbox
         * fieldCode : null
         * fieldName : 果实颜色
         * fieldContent : 白色|乳白色|淡黄色
         * sort : 1
         * createById : 1
         * createTime : 2020-06-22T14:31:15.000+0800
         * createByName : admin
         * groupId : null
         */

        private Integer id;
        /**
         * checkbox
         * radio
         * input
         * text
         */
        private String fieldType;

        private Object fieldCode;
        private String fieldName;
        private String fieldContent;
        private Integer sort;
        private Integer createById;
        private String createTime;
        private String createByName;
        private Object groupId;

        /**
         * 是否选择
         */
        private Boolean isSelect;

        /**
         * 单选and多选  答案
         */
        private List<SelectResultBean> results = new ArrayList<>();
        /**
         * 文本and输入框  答案
         */
        private String resultStr = "";

        public List<SelectResultBean> getResults() {
            return results;
        }

        public void setResults(List<SelectResultBean> results) {
            this.results = results;
        }

        public String getResultStr() {
            return resultStr;
        }

        public void setResultStr(String resultStr) {
            this.resultStr = resultStr;
        }

        public Boolean getSelect() {
            return isSelect;
        }

        public void setSelect(Boolean select) {
            isSelect = select;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public Object getFieldCode() {
            return fieldCode;
        }

        public void setFieldCode(Object fieldCode) {
            this.fieldCode = fieldCode;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldContent() {
            return fieldContent;
        }

        public void setFieldContent(String fieldContent) {
            this.fieldContent = fieldContent;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Integer getCreateById() {
            return createById;
        }

        public void setCreateById(Integer createById) {
            this.createById = createById;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateByName() {
            return createByName;
        }

        public void setCreateByName(String createByName) {
            this.createByName = createByName;
        }

        public Object getGroupId() {
            return groupId;
        }

        public void setGroupId(Object groupId) {
            this.groupId = groupId;
        }

        public static class SelectResultBean implements Serializable {
            private String title;
            private Boolean isSelect;

            public Boolean getSelect() {
                return isSelect;
            }

            public void setSelect(Boolean select) {
                isSelect = select;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public SelectResultBean(String title, Boolean isSelect){
                this.title = title;
                this.isSelect = isSelect;
            }
        }
    }


}
