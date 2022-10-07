<template>
  <div class="app-container">
    <div class="ui-code">
      <div class="ui-code-left">
        <div class="ui-group-add">
          <el-button class="ui-default-btn ui-group-add-btn" v-hasPermi="['os:species:add']" @click="handleAddSpecies">{{$t('menu.properties.species.button.add')}}</el-button>
        </div>
        <div class="ui-list" style="height:calc(100% - 62px);">
          <el-scrollbar style="height:100%;padding:0 !important;">
            <div class="ui-code-list">
              <div class="ui-code-item" @click="handleChangeGroup(item.id)" :class="{'ui-code-active' : item.id == activeCode}" v-for="(item,index) of speciesList" :key="index">
                <div class="ui-group-name">{{item.name}}</div>
                <el-button
                  type="text"
                  icon="el-icon-edit"
                  style="color:#BBBBBB;"
                  v-hasPermi="['os:species:edit']"
                  @click.stop="handleUpdateSpecies(item)"
                ></el-button>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
      <div class="ui-code-right">
        <div class="ui-code-title">
		      {{$t('menu.properties.species.title.typelist')}}
          <el-button class="ui-default-btn ui-group-add-btn" v-hasPermi="['os:attribute/group:add']" @click="handleAdd" style="width:152px;float:right;">{{$t('menu.properties.species.button.addtype')}}</el-button>
        </div>
        <div class="ui-list">
          <el-scrollbar style="height:100%;padding:0 !important;">
            <div class="ui-attribute-table">
              <div class="ui-thead">
                <div class="ui-tr">
                	<div class="ui-td" style="width:100px;">{{$t('common.label.sequence')}}</div>
                	<div class="ui-td" style="flex:1;">{{$t('menu.properties.species.label.typename')}}</div>
                	<div class="ui-td" style="width:200px;">{{$t('common.label.sequence')}}</div>
                	<div class="ui-td" style="width:80px;">{{$t('common.label.orderby')}}</div>
                </div>
              </div>
              <div class="ui-tbody">
                <draggable v-model="attributeGroupList" dragable="true" @update="datadragEnd">
                  <transition-group>
                    <div class="ui-tr" v-for="(item,index) of attributeGroupList" :key="index">
                      <div class="ui-td" style="width:100px;">{{item.id}}</div>
                      <div class="ui-td" style="flex:1;">{{item.name}}</div>
                      <div class="ui-td" style="width:200px;">
                       	  <el-button
		                    type="text"
		                    style="color:#4DA964;font-size:14px;"
		                    v-hasPermi="['os:attribute/group:edit']"
		                    @click.stop="handleUpdate(item)">{{$t('common.button.edit')}}</el-button>
		                  <el-button
		                    type="text"
		                    style="color:#4DA964;font-size:14px;"
		                    v-hasPermi="['os:attribute/group:hidden']"
		                    @click="handleSwitch(item.id,0)"
		                    v-if="item.isHidden == 1">{{$t('common.button.show')}}</el-button>
		                  <el-button
		                    type="text"
		                    style="color:#4DA964;font-size:14px;"
		                    v-hasPermi="['os:attribute/group:hidden']"
		                    @click="handleSwitch(item.id,1)"
		                    v-if="item.isHidden == 0">{{$t('common.button.hidden')}}</el-button>
		                  <el-button
				            type="text"
				            style="color:#4DA964;font-size: 14px;"
				            v-hasPermi="['os:attribute/group:remove']"
				            @click="handleDelete(item)">{{$t('common.button.delete')}}</el-button>
                      </div>
                      <div class="ui-td" style="width:80px;cursor:pointer;"><i class="el-icon-s-fold"></i></div>
                    </div>
                  </transition-group>
                </draggable>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
    </div>

    <!-- 添加物种对话框 -->
    <div v-if="openSpecies">
      <el-dialog :title="title" :visible.sync="openSpecies" width="750px" append-to-body>
        <div style="padding:0 40px 0 40px;box-sizing: border-box;">
          <el-form ref="formSpecies" :model="formSpecies" :rules="rulesSpecies" label-width="120px">
            <el-form-item :label="$t('menu.properties.species.label.speciesname')" prop="name">
              <el-input v-model="formSpecies.name" :placeholder="$t('menu.properties.species.label.speciesname')" />
            </el-form-item>
            <el-form-item :label="$t('common.label.orderby')" prop="sort">
              <el-input v-model="formSpecies.sort" :placeholder="$t('common.label.orderby')" />
            </el-form-item>
          </el-form>
        </div>

        <div slot="footer" class="dialog-footer" style="text-align: center;">
          <el-button type="primary" @click="submitSpeciesForm">{{$t('common.button.ok')}}</el-button>
          <el-button @click="cancel">{{$t('common.button.cancle')}}</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 添加或修改属性分组对话框 -->
    <div v-if="open">
      <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
        <div style="padding:0 40px 0 40px;box-sizing: border-box;">
          <el-form ref="form" :model="form" :rules="rules" label-width="150px">
            <el-form-item :label="$t('menu.properties.species.label.typename')" prop="name">
              <el-input v-model="form.name" :placeholder="$t('menu.properties.species.label.typename')" />
            </el-form-item>
            <el-form-item :label="$t('menu.properties.species.label.speciesname')" prop="speciesId">
              <el-select v-model="form.speciesId" placeholder="" style="width:100%;" @change="changeSpecies" :disabled="isDisabled">
                <el-option v-for="(item,index) of speciesList" :key="index" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div class="ui-table">
          <div class="ui-search">
            <div class="ui-search-name">{{$t('menu.properties.species.label.xingzhuang')}}</div>
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
              <el-table-column :label="$t('common.label.sequence')" align="center" width="120">
                <template slot-scope="scope">
                  {{scope.$index+1}}
                </template>
              </el-table-column>
              <el-table-column :label="$t('menu.properties.species.label.xingzhuangname')" align="center" prop="fieldName" />
              <el-table-column :label="$t('menu.properties.species.label.xingzhuangtype')" align="center" >
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
          <el-button type="primary" @click="submitForm">{{$t('common.button.ok')}}</el-button>
          <el-button @click="cancel">{{$t('common.button.cancle')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { listAttributeGroup, getAttributeGroup, delAttributeGroup, addAttributeGroup, updateAttributeGroup, exportAttributeGroup, listAttributeExistent, listAttributeAll, hiddenAttributeGroup, sortAttributeGroup, addSpecies, updateSpecies, listSpeciesTreeselect } from "@/api/os/attribute/group";
import draggable from 'vuedraggable'
export default {
  name: "AttributeGroup",
  components: {
    draggable
  },
  data() {
    return {
      isDisabled:false,
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
      openSpecies:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: ''
      },
      speciesList:[],
      searchAttr:'',
      // 表单参数
      form: {
        attributeList:[],
        name:'',
        speciesId:''
      },
      formSpecies:{
        name: '',
        sort: ''
      },
      // 表单校验
      rules: {
        name:[{ required: true, trigger: 'blur',message:this.$t('menu.properties.species.label.checktypename')}],
        speciesId:[{ required: true, trigger: 'blur',message:this.$t('menu.properties.species.label.checkspeciesname')}],
      },
      rulesSpecies: {
        name:[{ required: true, trigger: 'blur',message:this.$t('menu.properties.species.label.checkspeciesname')}],
      },
    };
  },
  created() {
    this.getSpeciesList();
  },
  methods: {
    /** 查询物种列表 */
    getSpeciesList() {
      listSpeciesTreeselect().then(response => {
        this.speciesList = response.data;
        if(this.speciesList.length > 0) {
          this.getList(this.speciesList[0].id);
          this.activeCode = this.speciesList[0].id
        }
      });
    },
    /** 查询属性分组列表 */
    getList(id) {
      let param = {
        speciesId: id
      }
      this.loading = true;
      listAttributeGroup(param).then(response => {
        this.attributeGroupList = response.data;
        this.loading = false;
        /*if(this.attributeGroupList.length > 0) {
          this.getExistentList(this.attributeGroupList[0].id);
          this.activeCode = this.attributeGroupList[0].id
        }*/
      });
    },
    /** 查询分组下的属性集合 */
    getExistentList(id) {
      Object.assign(this.queryParams,{groupId:id});
      listAttributeExistent(this.queryParams).then(response => {
        this.attributeExistentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分组下的属性集合 */
    getListAttributeAll(id) {
      this.loading = true;
      let param = {
        speciesId: id
      }
      listAttributeAll(param).then(response => {
        this.attributeNonExistentList = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openSpecies = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        name: "",
        attributeList: [],
        speciesId:''
      }
      this.resetForm("form");
      this.form.speciesId = this.activeCode
    },
    resetSpecies() {
      this.formSpecies = {
        name: "",
        sort: ""
      };
      this.resetForm("formSpecies");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList(this.activeCode);
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
    /** 新增物种按钮操作 */
    handleAddSpecies() {
      this.openSpecies = true;
      this.title = this.$t('menu.properties.species.title.add')
    },
    /** 新增分类按钮操作 */
    handleAdd() {
      this.reset();
      this.getListAttributeAll(this.activeCode);
      this.ids = []
      this.open = true;
      this.title = this.$t('menu.properties.species.title.typeadd')
      this.isDisabled = false;
    },
    /** 修改物种按钮操作 */
    handleUpdateSpecies(row) {
      let that = this
      that.resetSpecies();
      Object.assign(that.formSpecies,row)
      that.openSpecies = true;
      that.title = this.$t('menu.properties.species.title.edit')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let that = this
      that.reset();
      this.isDisabled = true;
      Object.assign(that.form,{name: row.name,id : row.id,speciesId: row.speciesId})
      const id = row.id
      Object.assign(that.queryParams,{groupId:id});
      listAttributeExistent(that.queryParams).then(response => {
        that.ids = []
        let chooseExistentList = response.rows;
        chooseExistentList.forEach((item,index) => {
          that.ids.push(item.id);
        })
        let param = {
          speciesId:  row.speciesId
        }
        listAttributeAll(param).then(res => {
          that.attributeNonExistentList = res.data;
          this.showChooseItem();
        });
        that.open = true;
        that.title = this.$t('menu.properties.species.title.typeedit');
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
    changeSpecies(val) {
      this.getListAttributeAll(val);
    },
    /** 提交物种按钮 */
    submitSpeciesForm() {
      this.$refs["formSpecies"].validate(valid => {
        if (valid) {
          if (this.formSpecies.id != undefined) {
            updateSpecies(this.formSpecies).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.openSpecies = false;
                this.getSpeciesList();
              }
            });
          } else {
            addSpecies(this.formSpecies).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.openSpecies = false;
                this.getSpeciesList();
              }
            });
          }
        }
      });
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
            this.msgInfo(this.$t('menu.properties.species.tips.checkadditive'))
            return
          }
          if (this.form.id != undefined) {
            updateAttributeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList(this.activeCode);
              }
            });
          } else {
            addAttributeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList(this.activeCode);
              }
            });
          }
        }
      });
    },
    /** 删除分类按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(this.$t('menu.properties.species.tips.checkdelete'), this.$t('common.title.warning'), {
        confirmButtonText: this.$t('common.button.ok'),
        cancelButtonText: this.$t('common.button.cancle'),
        type: "warning"
      }).then(function() {
        return delAttributeGroup(ids);
      }).then(() => {
        this.getList(this.activeCode);
        this.msgSuccess(this.$t('common.tips.success'));
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
            this.msgSuccess(this.$t('menu.properties.species.tips.show'));
          } else {
            this.msgSuccess(this.$t('menu.properties.species.tips.hidden'));
          }
          this.getList(this.activeCode);
        }
      });
    },
    datadragEnd(evt) {
      let array = []
      this.attributeGroupList.forEach((item,index) => {
        array.push(item.id)
      })
      sortAttributeGroup(array).then(res => { // 请求接口
        if (res.code === 200) {
          this.msgSuccess(this.$t('common.tips.success'));
        }
      }).catch(err => {
        console.log('err', err)
      })
    },
    /** 切换分类*/
    handleChangeGroup(id) {
      this.activeCode = id;
      this.getList(id);
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
    padding:0 40px 0 40px;
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
