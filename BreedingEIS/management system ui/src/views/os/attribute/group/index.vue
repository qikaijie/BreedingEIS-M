<template>
  <div class="app-container">
    <div class="ui-code">
      <div class="ui-code-left">
        <div class="ui-group-add">
          <!--<el-button class="ui-default-btn ui-group-add-btn" @click="handleAdd">添加分类</el-button>-->
        </div>
        <div class="ui-list" style="height:calc(100% - 62px);">
          <el-scrollbar style="height:100%;padding:0 !important;">
            <div class="ui-code-list">
              <el-tree
                class="filter-tree"
                :data="attributeGroupList"
                :props="defaultProps"
                :default-expand-all="true"
                node-key="id"
                :highlight-current="showTree"
                :expand-on-click-node="false"
                @node-click="handleNodeClick"
                ref="tree"
              >
                <span class="custom-tree-node show-hide" slot-scope="{ node ,data}" style="display: flex;align-items: center;">
                  <img src="@/assets/image/type.png" style="width:16px;margin-right:8px;" v-if="data.type === 'species'">
                  <span style="font-size:13px;">{{ data.name }}</span>
                </span>
              </el-tree>
              <!--<div class="ui-code-item" @click="handleChangeGroup(item.id)" :class="{'ui-code-active' : item.id == activeCode}" v-for="(item,index) of attributeGroupList" :key="index">
                <div class="ui-group-name">{{item.name}}</div>
                <el-button
                  type="text"
                  icon="el-icon-edit"
                  style="color:#BBBBBB;"
                  @click.stop="handleUpdate(item)"
                ></el-button>
                <el-button
                  type="text"
                  style="color:#4DA964;font-size:12px;"
                  @click="handleSwitch(item.id,0)"
                  v-if="item.isHidden == 1"
                >显示</el-button>
                <el-button
                  type="text"
                  style="color:#4DA964;font-size:12px;"
                  @click="handleSwitch(item.id,1)"
                  v-if="item.isHidden == 0"
                >隐藏</el-button>
              </div>-->
            </div>
          </el-scrollbar>
        </div>
      </div>
      <div class="ui-code-right">
        <div class="ui-code-title">{{$t('menu.properties.classification.title.list')}}</div>
        <div class="ui-list">
          <el-scrollbar style="height:100%;padding:0 !important;">
            <div class="ui-attribute-table">
              <div class="ui-thead">
                <div class="ui-tr">
                	<div class="ui-td" style="width:100px;">{{$t('common.label.sequence')}}</div>
                	<div class="ui-td" style="width:150px;">{{$t('menu.properties.classification.label.xingzhuangname')}}</div>
                	<div class="ui-td" style="width:130px;">{{$t('menu.properties.classification.label.type')}}</div>
                	<div class="ui-td" style="flex:1;">{{$t('menu.properties.classification.label.content')}}</div>
                	<div class="ui-td" style="width:50px;">{{$t('common.label.orderby')}}</div>
                </div>
              </div>
              <div class="ui-tbody">
                <draggable v-model="attributeExistentList" dragable="true" @start="startMove" :move="move" @update="datadragEnd">
                  <transition-group>
                    <div class="ui-tr" v-for="(item,index) of attributeExistentList" :key="index">
                      <div class="ui-td" style="width:100px;">{{item.id}}</div>
                      <div class="ui-td" style="width:150px;">{{item.fieldName}}</div>
                      <div class="ui-td" style="width:130px;">
                        <span v-if="item.fieldType == 'input'">{{$t('common.label.input')}}</span>
                        <span v-else-if="item.fieldType == 'text'">{{$t('common.label.text')}}</span>
                        <span v-else-if="item.fieldType == 'radio'">{{$t('common.label.radio')}}</span>
                        <span v-else-if="item.fieldType == 'checkbox'">{{$t('common.label.checkbox')}}</span>
                        <span v-else-if="item.fieldType == 'date'">{{$t('common.label.date')}}</span>
                      </div>
                      <div class="ui-td" style="flex:1;">{{item.fieldContent}}</div>
                      <div class="ui-td" style="width:50px;cursor:pointer;"><i class="el-icon-s-fold"></i></div>
                    </div>
                  </transition-group>
                </draggable>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
    </div>


    <!-- 添加或修改属性分组对话框 -->
    <div v-if="open">
      <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
        <div style="padding:0 120px 0 80px;box-sizing: border-box;">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="分类名称：" prop="name">
              <el-input v-model="form.name" placeholder="请输入分类名称" />
            </el-form-item>
          </el-form>
        </div>
        <div class="ui-table">
          <div class="ui-search">
            <div class="ui-search-name">选择性状：</div>
            <el-input
              placeholder=""
              style="width:230px;"
              v-if="false"
              suffix-icon="el-icon-search"
              v-model="searchAttr">
            </el-input>
          </div>
          <div style="padding-left:2px;box-sizing: border-box;">
            <el-table v-loading="loading" :data="attributeNonExistentList" @selection-change="handleSelectionChange" border fit highlight-current-row stripe element-loading-text="Loading" ref="existentTable">
              <el-table-column type="selection" width="60" align="center" />
              <el-table-column label="序号" align="center" width="55">
                <template slot-scope="scope">
                  {{scope.$index+1}}
                </template>
              </el-table-column>
              <el-table-column label="性状名称" align="center" prop="fieldName" />
              <el-table-column label="已加入分类" align="center" >
                <template slot-scope="scope">
                  <span v-if="scope.row.attributeGroupsList">
                    <span v-for="(item,index) of scope.row.attributeGroupsList" :key="index">{{item.name}}<span v-if="index != (scope.row.attributeGroupsList.length - 1)">,</span></span>
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <div slot="footer" class="dialog-footer" style="text-align: center;">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { listAttributeGroup, listSpecies, getAttributeGroup, delAttributeGroup, addAttributeGroup, updateAttributeGroup, exportAttributeGroup, listAttributeExistent, listAttributeAll, hiddenAttributeGroup, sortRelationAttribute, queryAttributeList } from "@/api/os/attribute/group";
import draggable from 'vuedraggable'
export default {
  name: "AttributeGroup",
  components: {
    draggable
  },
  data() {
    return {
      activeCode:'',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 属性分组表格数据
      attributeGroupList: [],
      attributeExistentList:[],
      attributeNonExistentList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: ''
      },
      searchAttr:'',
      // 表单参数
      form: {
        attributeList:[],
        name:''
      },
      // 表单校验
      rules: {
        name:[{ required: true, trigger: 'blur',message:'请输入分类名称'}],
      },
      showTree: true, //开启树选中高亮
      defaultProps: {
        children: "children",
        label: "name",
      },
      isDisabled:false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询属性分组列表 */
    getList() {
      this.loading = true;
      listSpecies().then(response => {
        this.attributeGroupList = response.data;
        if(this.attributeGroupList.length > 0) {
          if(this.attributeGroupList[0].type === "species") {
            this.isDisabled = true;
          } else {
            this.isDisabled = false;
          }
          this.getExistentList(this.attributeGroupList[0].businessId,this.attributeGroupList[0].type);
        }
      });
    },
    /** 查询分组下的属性集合 */
    getExistentList(id,type) {
      Object.assign(this.queryParams,{id:id,type:type });
      queryAttributeList(this.queryParams).then(response => {
        this.attributeExistentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分组下的属性集合 */
    getListAttributeAll() {
      listAttributeAll().then(response => {
        this.attributeNonExistentList = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        name: "",
        attributeList: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getListAttributeAll();
      this.ids = []
      this.open = true;
      this.title = "添加分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let that = this
      that.reset();
      Object.assign(that.form,{name: row.name,id : row.id})
      const id = row.id
      Object.assign(that.queryParams,{groupId:id});
      listAttributeExistent(that.queryParams).then(response => {
        that.ids = []
        let chooseExistentList = response.rows;
        chooseExistentList.forEach((item,index) => {
          that.ids.push(item.id);
        })
        listAttributeAll().then(res => {
          that.attributeNonExistentList = res.data;
          this.showChooseItem();
        });
        that.open = true;
        that.title = "修改分类";
      });
    },
    showChooseItem() {
      let that = this
      setTimeout(function(){
        let table = that.attributeNonExistentList // 从后台获取到的数据
        let currentIds = that.ids
        table.forEach(item => {
          if (currentIds.indexOf(item.id) > -1) {
            that.$refs.existentTable && that.$refs.existentTable.toggleRowSelection(item, true)
          } else {
            that.$refs.existentTable && that.$refs.existentTable.toggleRowSelection(item, false)
          }
        })
      }, 500);
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.ids.length > 0) {
            let attributeList = [];
            this.ids.forEach((item,index) => {
              attributeList.push({id:item})
            })
            this.form.attributeList = attributeList;
          } else {
            this.msgInfo("请选择属性")
            return
          }
          if (this.form.id != undefined) {
            updateAttributeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAttributeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除该属性?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAttributeGroup(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 删除分类按钮操作 */
    handleDeleteGroup(row) {
      const ids = row.id;
      this.$confirm('是否确认删除该属性分组?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delAttributeGroup(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有属性分组数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAttributeGroup(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    //显示隐藏
    handleSwitch(id,isHidden ) {
      let param = {
        groupId: id,
        isHidden: isHidden
      }
      hiddenAttributeGroup(param).then(response => {
        if (response.code === 200) {
          if(isHidden == 0) {
            this.msgSuccess("显示成功");
          } else {
            this.msgSuccess("隐藏成功");
          }
          this.getList();
        }
      });
    },
    startMove(evt) {
	  if(this.isDisabled)
      {
      	this.msgWarning("物种分类下的属性才可以排序，请先选择属性分类，再进行属性排序。");
      }
    },
    move(evt) {
    	return !this.isDisabled;
    },
    datadragEnd(evt) {
      if(this.isDisabled)
      {
      	return false;
      }
      let array = []
      this.attributeExistentList.forEach((item,index) => {
        array.push(item.attributeGroupRelationId)
      })
      sortRelationAttribute(array).then(res => { // 请求接口
        if (res.code === 200) {
          this.msgSuccess(this.$t('menu.properties.classification.tips.orderby'));
        }
      }).catch(err => {
        console.log('err', err)
      })
    },
    /** 切换分类*/
    handleChangeGroup(id) {
      this.activeCode = id;
      this.getExistentList(id);
    },
    //节点点击事件
    handleNodeClick(data, node) {
      //参数一，当前节点数据，参数二：当前节点node数据
      this.getExistentList(data.businessId,data.type);
      if(data.type === "species") {
        this.isDisabled = true;
      } else {
        this.isDisabled = false;
      }
    },
  }
};
</script>

<style scoped lang="scss">
  .ui-form-title{
    color:#101010;
    font-size:14px;
    font-weight: bold;
    padding:7px 20px;
    box-sizing: border-box;
    border-bottom:1px solid #EEE;
    margin-bottom:21px;
  }
  .app-container{
    position: relative;
  }
  .ui-code{
    width:100%;
    height: calc(100vh - 140px);
    display: flex;
    .ui-code-title{
      padding:0 12px;
      box-sizing: border-box;
      color:#101010;
      font-size:14px;
      height:48px;
      line-height: 48px;
    }
    .ui-list{
      width:100%;
      height:calc(100% - 48px);
    }
    .ui-code-left{
      width:200px;
      height:100%;
      background: #F8F8F8;
      .ui-code-list{
        width:100%;
        .ui-code-item{
          width:100%;
          height:49px;
          color:#9999A5;
          font-size:14px;
          padding:0 7px 0 15px;
          box-sizing: border-box;
          cursor: pointer;
          display: flex;
          align-items: center;
          .ui-group-name{
            color:#9999A5;
            flex:1;
            min-width: 0;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
        .ui-code-active{
          color:#101010;
          background: #EEEEEE;
          .ui-group-name{
            color:#101010;
          }
        }
      }
    }
    .ui-code-right{
      flex:1;
      min-width: 0;
      height:100%;
      margin-left:47px;
    }
    .ui-group-add{
      padding:17px 24px;
      box-sizing: border-box;
      .ui-group-add-btn{
        width:100%;
      }
    }
  }
  .ui-table{
    width:100%;
    padding:0 120px 0 100px;
    box-sizing: border-box;
    .ui-search{
      display: flex;
      align-items: center;
      margin-bottom:6px;
      .ui-search-name{
        flex:1;
        color:#101010;
        font-size:14px;
      }
    }
  }

  .ui-code-right .ui-attribute-table{width:100%;border-collapse: collapse;border-right:1px solid #dfe6ec;border-bottom:1px solid #dfe6ec;}
  .ui-code-right .ui-attribute-table .ui-thead .ui-tr{background: #f8f8f9;display: flex;}
  .ui-code-right .ui-attribute-table .ui-thead .ui-tr .ui-td{height:45px !important;line-height:45px !important;}
  .ui-code-right .ui-attribute-table .ui-tbody .ui-td{padding:10px 10px;box-sizing: border-box;display: flex;align-items: center;justify-content: center;}
  .ui-code-right .ui-attribute-table .ui-td{border:1px solid #dfe6ec;text-align: center;font-size: 14px;color:#515a6e;line-height: 23px;border-bottom:none;border-right: none;}
  .ui-code-right .ui-attribute-table .ui-tbody .ui-tr{display: flex;background: #FFF !important;}
  .ui-code-right .ui-attribute-table .ui-tbody .ui-tr:nth-of-type(even){background: #FAFAFA !important;}
</style>
