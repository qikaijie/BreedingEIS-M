<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8" style="margin:10px 0;">
      <el-col :span="1.5">
        <el-button
          type="primary" 
    	  icon="el-icon-plus" 
    	  size="mini" 
          @click="handleAdd"
          v-hasPermi="['os:attribute:add']"
        >{{$t('menu.properties.additive.button.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-upload
          style="text-align: left;width:130px;"
          :auto-upload="false"
          action="http://localhost:8080"
          ref="upload"
          :on-change="handleFileChange"
          :file-list="fileList">
          <el-button
            type="info"
            icon="el-icon-upload2"
            size="mini"
            v-hasPermi="['os:attribute:import']"
          >{{$t('menu.properties.additive.button.import')}}</el-button>
        </el-upload>

      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="attributeList" border fit highlight-current-row stripe element-loading-text="Loading">
      <el-table-column :label="$t('common.label.sequence')" align="center">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.properties.additive.label.xingzhuangname')" align="center" prop="fieldName" />
      <el-table-column :label="$t('menu.properties.additive.label.type')" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.fieldType == 'input'">{{$t('common.label.input')}}</span>
          <span v-if="scope.row.fieldType == 'text'">{{$t('common.label.text')}}</span>
          <span v-else-if="scope.row.fieldType == 'radio'">{{$t('common.label.radio')}}</span>
          <span v-else-if="scope.row.fieldType == 'checkbox'">{{$t('common.label.checkbox')}}</span>
          <span v-else-if="scope.row.fieldType == 'date'">{{$t('common.label.date')}}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.properties.additive.label.content')" align="center" prop="fieldContent" />
      <el-table-column :label="$t('menu.properties.additive.label.createtime')" align="center" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.label.sequence')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            style="color:#4DA964;font-size: 14px;"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:attribute:edit']"
          >{{$t('common.button.edit')}}</el-button>
          <!--<el-button
            type="text"
            style="color:#4DA964;font-size: 14px;"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:attribute:remove']"
          >{{$t('common.button.delete')}}</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- ????????????????????????????????? -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <div style="padding:30px 130px 30px 30px;box-sizing: border-box;">
        <el-form ref="form" :model="form" :rules="rules" label-width="130px">
          <el-form-item :label="$t('menu.properties.additive.label.xingzhuangname')" prop="fieldName">
            <el-input v-model="form.fieldName" :placeholder="$t('menu.properties.additive.label.xingzhuangname')" />
          </el-form-item>
          <el-form-item :label="$t('menu.properties.additive.label.speciesname')" prop="speciesId">
            <el-select v-model="form.speciesId" :placeholder="$t('menu.properties.additive.label.speciesname')" style="width:100%;">
              <el-option v-for="(item,index) of speciesList" :key="index" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('menu.properties.additive.label.type')">
            <el-select v-model="form.fieldType" :placeholder="$t('menu.properties.additive.label.type')" style="width:100%;">
              <el-option :label="$t('common.label.radio')" value="radio" />
              <el-option :label="$t('common.label.checkbox')" value="checkbox" />
              <el-option :label="$t('common.label.date')" value="date" />
              <el-option :label="$t('common.label.input')" value="input" />
              <el-option :label="$t('common.label.text')" value="text" />
            </el-select>
            <div class="ui-field-list" v-if="form.fieldType == 'radio'">
              <div class="ui-field-item" v-for="(item,index) of list" :key="index">
                <el-input v-model="item.name" :placeholder="$t('menu.properties.additive.tips.checkoption')" />
                <img src="../../../assets/image/cart_3.png" class="ui-field-add" @click="handleAddRadioField" v-if="index == (list.length - 1)">
                <img src="../../../assets/image/closeModel.png" class="ui-field-remove"  @click="handleRemoveRadioField(index)" v-if="index != 0 || list.length > 1">
              </div>
            </div>
            <div class="ui-field-list" v-if="form.fieldType == 'checkbox'">
              <div class="ui-field-item" v-for="(item,index) of list" :key="index">
                <el-input v-model="item.name" :placeholder="$t('menu.properties.additive.tips.checkoption')" />
                <img src="../../../assets/image/cart_3.png" class="ui-field-add" @click="handleAddCheckBoxField" v-if="index == (list.length - 1)">
                <img src="../../../assets/image/closeModel.png" class="ui-field-remove"  @click="handleRemoveCheckBoxField(index)" v-if="index != 0 || list.length > 1">
              </div>
            </div>
          </el-form-item>
          <!--<el-form-item label="????????????:" prop="min" v-if="form.fieldType == 'input'">
            <div style="display: flex;align-items: center;justify-content: center;">
              <el-input v-model="form.min" placeholder="?????????" @change="handleMaxChange()"/>
              <span style="margin:0 10px;">~</span>
              <el-input v-model="form.max" placeholder="?????????"  @change="handleMaxChange()" />
            </div>
          </el-form-item>-->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button type="primary" @click="submitForm">{{$t('common.button.ok')}}</el-button>
        <el-button @click="cancel">{{$t('common.button.cancle')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAttribute, getAttribute, delAttribute, addAttribute, updateAttribute, exportAttribute, importAttribute } from "@/api/os/attribute";
import { listAttributeGroup, listSpeciesTreeselect } from "@/api/os/attribute/group";
const MIN_NUMBER = 1;
const MAX_NUMBER = 100000;
export default {
  name: "Attribute",
  data() {
    return {
      fileList:[],
      // ?????????
      loading: true,
      // ????????????
      ids: [],
      // ???????????????
      single: true,
      // ???????????????
      multiple: true,
      // ?????????
      total: 0,
      // ?????????????????????
      attributeList: [],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fieldType: '',
        fieldCode: undefined,
        fieldName: undefined,
        fieldContent: undefined,
        sort: undefined,
        createById: undefined,
        createByName: undefined
      },
      // ????????????
      form: {
        fieldName:'',//????????????
        fieldType:'',
        fieldContent:'',
        speciesId:''
        /*min:'',
        max:''*/
      },
      list: [{name:''}],
      speciesList:[],
      importForm: {
        file:null,
        fileName:''
      },
      // ????????????
      rules: {
        /*min: [
          { validator: this.validateCom, trigger: 'blur' },
          { validator: this.validateMin, trigger: 'blur' },
        ],
        max: [
          { validator: this.validateCom, trigger: 'blur' },
          { validator: this.validateMax, trigger: 'blur' },
        ],*/
      },
      attributeGroupList:[]
    };
  },
  created() {
    this.getList();
    //this.getGroupList();
  },
  methods: {
    /** ?????????????????? */
    getSpeciesList() {
      listSpeciesTreeselect().then(response => {
        this.speciesList = response.data;
      });
    },
    /** ????????????????????? */
    getList() {
      this.loading = true;
      listAttribute(this.queryParams).then(response => {
        this.attributeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /*getGroupList() {
      listAttributeGroup().then(response => {
        this.attributeGroupList = response.rows;
      });
    },*/
    // ????????????
    cancel() {
      this.open = false;
      this.reset();
    },
    // ????????????
    reset() {
      /*this.form = {
        id: '',
        fieldName:'',//????????????
        fieldType:'',
        fieldContent:'',
        list: [{name:''}],
      };*/
      Object.assign(this.form,{id: '',
        fieldName:'',//????????????
        fieldType:'',
        fieldContent:'',
        speciesId:''
      })
      this.list = [{name:''}]
    },
    /** ?????????????????? */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** ?????????????????? */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // ?????????????????????
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** ?????????????????? */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('menu.properties.additive.title.add');
      this.getSpeciesList();
    },
    /** ?????????????????? */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.getSpeciesList();
      getAttribute(id).then(response => {
        this.form = response.data;
        let content = this.form.fieldContent
        if(content != '' && content != null) {
          let array = content.split('|')
          let list = []
          array.forEach((item) => {
            list.push({name: item})
          })
          this.list = list
        }
        this.open = true;
        this.title = this.$t('menu.properties.additive.title.edit');
      });
    },
    /** ???????????? */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.fieldType == 'radio' || this.form.fieldType == 'checkbox') {
            let fieldContent = '';
            this.list.forEach((item,index) => {
              fieldContent += item.name
              if(index != (this.list.length - 1)) {
                fieldContent += '|'
              }
            })
            Object.assign(this.form,{fieldContent : fieldContent})
          }
          if (this.form.id != undefined && this.form.id != '') {
            updateAttribute(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAttribute(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** ?????????????????? */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('????????????????????????????????????"' + ids + '"?????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return delAttribute(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("????????????");
        }).catch(function() {});
    },
    /** ?????????????????? */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('???????????????????????????????????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return exportAttribute(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    //??????
    handleAddRadioField() {
      this.list.push({name: ''})
    },
    //??????
    handleRemoveRadioField(index) {
      this.list.splice(index,1)
    },
    //??????
    handleAddCheckBoxField() {
      this.list.push({name: ''})
    },
    //??????
    handleRemoveCheckBoxField(index) {
      this.list.splice(index,1)
    },
    //????????????
    handleFileChange(file, fileList) {
      this.importForm.file = file.raw
      this.fileName = file.raw.name
      this.submitMember()
    },
    //??????????????????
    submitMember() {
      let data = this.importForm
      importAttribute(data).then(res => { // ????????????
        if (res.success) {
          this.msgSuccess("??????????????????");
          this.getList()
        } else {
          this.msgError(res.msg)
        }
      }).catch(err => {
        this.msgError(err.msg)
        console.log('err', err)
      })
    },
    handleMinChange() {
      this.$refs.form.validateField('max');
    },
    handleMaxChange() {
      this.$refs.form.validateField('min');
    },
    validateCom(rule, value, callback) {
      const one = Number(value);
      if (Number.isInteger(one)) {
        if (one < MIN_NUMBER) {
          return callback(new Error('?????????????????????0'));
        } else if (one > MAX_NUMBER) {
          return callback(new Error('?????????????????????100000'));
        }
        return callback();
      }
      return callback(new Error('???????????????????????????'));
    },
    validateMin(rule, value, callback) {
      const one = Number(value);
      const max = Number(this.form.max);
      if (!max || one < max) {
        return callback();
      }
      return callback(new Error('??????????????????????????????'));
    },
    validateMax(rule, value, callback) {
      const one = Number(value);
      const min = Number(this.form.min);
      if (!min || one > min) {
        return callback();
      }
      return callback(new Error('??????????????????????????????'));
    },
  }
};
</script>

<style scoped lang="scss">
  .ui-field-list{
    width:100%;
    margin-top:17px;
    .ui-field-item{
      width:100%;
      position: relative;
      margin-bottom: 15px;
      .ui-field-add{
        position: absolute;
        right:-40px;
        width:20px;
        height:20px;
        padding: 2px;
        top:6px;
        cursor: pointer;
      }
      .ui-field-remove{
        position: absolute;
        right:-70px;
        width:20px;
        height:20px;
        padding: 3px;
        top:6px;
        cursor: pointer;
      }
    }
  }
</style>
