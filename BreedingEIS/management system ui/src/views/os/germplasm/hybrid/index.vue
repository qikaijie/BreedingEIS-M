<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item :label="$t('menu.germplasm.resources.label.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('menu.germplasm.resources.label.name')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('menu.germplasm.resources.label.source')" prop="source">
        <el-select v-model="queryParams.source" placeholder="">
          <el-option label="引进" value="引进" />
          <el-option label="收集" value="收集" />
          <el-option label="自育" value="自育" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('menu.germplasm.resources.label.year')" prop="introductionYear">
        <el-date-picker clearable size="small" v-model="queryParams.introductionYear" value-format="yyyy" type="year">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{$t('common.button.search')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{$t('common.button.reset')}}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary" 
    	  icon="el-icon-plus" 
    	  size="mini" 
          @click="handleAdd"
          v-hasPermi="['os:germplasm:add']"
        >{{$t('menu.germplasm.resources.button.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['os:germplasm:import']"
        >{{$t('common.button.import')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          v-hasPermi="['os:germplasm:remove']"
        >{{ $t('common.button.delete') }}</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="hybridList" border fit highlight-current-row stripe element-loading-text="Loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('common.label.sequence')" align="center" width="120">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.germplasm.resources.label.name')" align="center" prop="name" />
      <el-table-column :label="$t('menu.germplasm.resources.label.alias')" align="center" prop="alias" />
      <el-table-column :label="$t('menu.germplasm.resources.label.source')" align="center" prop="source" width="80"/>
      <el-table-column :label="$t('menu.germplasm.resources.label.type')" align="center" prop="type" width="80"/>
      <!--<el-table-column :label="$t('menu.germplasm.resources.label.sourcearea')" align="center" prop="sourceArea" />-->
      <!--<el-table-column :label="$t('menu.germplasm.resources.label.yinjinfangshi')" align="center" prop="introductionMethod" />-->
      <el-table-column :label="$t('menu.germplasm.resources.label.year')" align="center" prop="introductionYear" width="100"/>
      <!--<el-table-column :label="$t('menu.germplasm.resources.label.pumingcheng')" align="center" prop="libName" />-->
      <!--<el-table-column :label="$t('menu.germplasm.resources.label.baocunfangshi')" align="center" prop="saveMethod" />-->
      <el-table-column :label="$t('menu.germplasm.resources.label.count')" align="center" prop="amount" width="80"/>
      <el-table-column :label="$t('menu.germplasm.resources.label.chakatiaoma')" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
          	size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewCode(scope.row)"
            v-hasPermi="['os:germplasm:view']"
          >{{$t('common.button.view')}}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:germplasm:remove']"
          >{{ $t('common.button.delete') }}</el-button>
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

    <!-- 添加或修改育种群体库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="170px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.name')" prop="name">
              <el-input v-model="form.name" :placeholder="$t('menu.germplasm.resources.label.name')" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.alias')" prop="alias">
              <el-input v-model="form.alias" :placeholder="$t('menu.germplasm.resources.label.alias')" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.source')" prop="source">
              <el-select v-model="form.source" placeholder="" style="width:100%;">
                <el-option label="引进" value="引进" />
                <el-option label="收集" value="收集" />
                <el-option label="自育" value="自育" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.type')" prop="type">
              <el-select v-model="form.type" placeholder="" style="width:100%;" filterable allow-create>
                <el-option label="选育品种" value="选育品种" />
                <el-option label="地方品种" value="地方品种" />
                <el-option label="野生资源" value="野生资源" />
                <el-option label="品系" value="品系" />
                <el-option label="遗传材" value="遗传材" />
                <!--<el-option label="其他" value="其他" />-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.sourcearea')" prop="sourceArea">
              <el-input v-model="form.sourceArea" :placeholder="$t('menu.germplasm.resources.label.sourcearea')" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.yuanlianxiren')" prop="firstIntroducerPhone">
              <div style="display: flex;">
                <el-input v-model="form.firstIntroducer" :placeholder="$t('menu.germplasm.resources.label.username')" style="flex:1;"/>
                <el-input v-model="form.firstIntroducerPhone" :placeholder="$t('menu.germplasm.resources.label.phone')" style="flex:1;margin-left:10px;" />
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.yinjinren')" prop="introducerPhone">
              <div style="display: flex;">
                <el-input v-model="form.introducer" :placeholder="$t('menu.germplasm.resources.label.username')" style="flex:1;" />
                <el-input v-model="form.introducerPhone" :placeholder="$t('menu.germplasm.resources.label.phone')" style="flex:1;margin-left:10px;" />
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.yinjinfangshi')" prop="introductionMethod">
              <el-select v-model="form.introductionMethod" placeholder="" style="width:100%;" filterable allow-create>
                <el-option label="接穗" value="接穗" />
                <el-option label="苗木" value="苗木" />
                <el-option label="组培苗" value="组培苗" />
                <el-option label="花粉" value="花粉" />
                <!--<el-option label="其他" value="其他" />-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.year')" prop="introductionYear">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.introductionYear"
                              value-format="yyyy"
                              type="year">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.pumingcheng')" prop="libName">
              <el-select v-model="form.libName" placeholder="" style="width:100%;" filterable allow-create>
                <el-option label="国家梨改良中心南京分中心" value="国家梨改良中心南京分中心" />
                <el-option label="南京农业大学湖熟梨基地" value="南京农业大学湖熟梨基地" />
                <!--<el-option label="其他" value="其他" />-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.baocunfangshi')" prop="saveMethod">
              <el-select v-model="form.saveMethod" placeholder="" style="width:100%;" filterable allow-create>
                <el-option label="高接" value="高接" />
                <el-option label="苗木定植" value="苗木定植" />
                <el-option label="组培繁殖" value="组培繁殖" />
                <!--<el-option label="其他" value="其他" />-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.count')" prop="amount">
              <div style="display: flex;">
                <el-input v-model.number="form.amount" type="number" :placeholder="$t('menu.germplasm.resources.label.count')" style="flex:1;"/>
                <span style="margin-left:10px;">{{$t('menu.germplasm.resources.label.unit')}}</span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.baohu')" prop="isProtect">
              <el-select v-model="form.isProtect" placeholder="" style="width:100%;">
                <el-option label="是" :value="1" />
                <el-option label="否" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.baocunnum')" prop="code">
              <el-input v-model="form.code" :placeholder="$t('menu.germplasm.resources.label.baocunnum')"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.characterization')" prop="describe">
              <el-input v-model="form.describe" type="textarea" :placeholder="$t('menu.germplasm.resources.label.characterization')"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item :label="$t('menu.germplasm.resources.label.application')" prop="application">
              <el-input v-model="form.application" type="textarea" :placeholder="$t('menu.germplasm.resources.label.application')"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item prop="germplasmImgList" :label="$t('menu.germplasm.resources.label.img')">
              <el-upload
                :action= "uploadImg.url"
                :headers="uploadImg.headers"
                :multiple = true
                list-type="picture-card"
                :with-credentials='true'
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-error="handleError"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible" :append-to-body="true">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>
        </el-row>
        <template v-if="form.type === '选育品种'">
          <div class="ui-form-title">{{$t('menu.germplasm.resources.title.xuanyu')}}</div>
          <el-row :gutter="10" class="mb8">
            <el-col :span="22">
              <el-form-item :label="$t('menu.germplasm.resources.label.xuanyudanwei')" prop="breedingCompany">
                <el-input v-model="typeRemark.breedingCompany" :placeholder="$t('menu.germplasm.resources.label.xuanyudanwei')"/>
              </el-form-item>
            </el-col>
            <el-col :span="22">
              <el-form-item :label="$t('menu.germplasm.resources.label.xuanyufangfa')" prop="breedingMethod">
                <el-select v-model="typeRemark.breedingMethod" placeholder="" style="width:100%;" filterable allow-create>
                  <el-option label="育种培育" value="育种培育" />
                  <el-option label="芽变选种" value="芽变选种" />
                  <el-option label="诱变育种" value="诱变育种" />
                  <el-option label="远缘育种" value="远缘育种" />
                  <el-option label="实生育种" value="实生育种" />
                  <!--<el-option label="其他" :value="3" />-->
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="22">
              <el-form-item :label="$t('menu.germplasm.resources.label.yuchengyear')" prop="breedingYear">
                <el-input v-model="typeRemark.breedingYear" :placeholder="$t('menu.germplasm.resources.label.yuchengyear')"/>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button type="primary" @click="submitForm">{{$t('common.button.ok')}}</el-button>
        <el-button @click="cancel">{{$t('common.button.cancle')}}</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
		{{ $t('common.tips.uploadoperate') }}
          <em>{{ $t('common.button.upload') }}</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />{{ $t('common.label.selectupdate') }}
          <el-link type="info" style="font-size:16px;color:red;" @click="importTemplate">{{ $t('common.button.downloadtemplate') }}</el-link>
        </div>
        <div class="el-upload__tip" style="color:green" slot="tip">{{ $t('common.tips.importfileext') }}</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">{{$t('common.button.ok')}}</el-button>
        <el-button @click="upload.open = false">{{$t('common.button.cancle')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGermplasm, getGermplasm, delGermplasm,  updateGermplasm, exportGermplasm, importTemplate, addGermplasm } from "@/api/os/germplasm/hybrid";
import { getToken } from "@/utils/auth";
export default {
  name: "Hybrid",
  data() {
    const  checkInt = (rule, value, callback) => {
      if(value) {
        if ((Number(value))&&(value)%1 === 0) {
          callback();
        }else {
          return callback(new Error(this.$t('common.tips.checkint')));
        }
      } else {
        callback();
      }
    }
    const  checkPhone = (rule, value, callback) => {
      if(value) {
        let flag = false;
        var reg = /^(\d{3,4}-)?\d{7,8}$/
        var reg2 = /^1[3456789]\d{9}$/
        if(!reg.test(value) && !reg2.test(value)) {
          flag = true
        }
        if (flag) {
          return callback(new Error(this.$t('common.tips.checkphone')));
        }else {
          callback();
        }
      } else {
        callback();
      }
    }

    return {
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
      // 杂交组合库表格数据
      hybridList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        source: '',
        introductionYear:''
      },
      fileList:[],
      // 表单参数
      form: {
        name:'',
        alias:'',
        source:'',
        type:'',
        typeRemark:'',
        sourceArea:'',
        firstIntroducer:'',
        firstIntroducerPhone:'',
        introducer:'',
        introducerPhone:'',
        introductionMethod:'',
        introductionYear:'',
        libName:'',
        saveMethod:'',
        amount:'',
        isProtect:'',
        describe:'',
        application:'',
        germplasmImgList:[],
      },
      typeRemark: {
        breedingCompany:'',
        breedingMethod:'',
        breedingYear:''
      },
      // 表单校验
      rules: {
        firstIntroducerPhone:[{ required: false, trigger: 'blur',validator: checkPhone}],
        introducerPhone:[{ required: false, trigger: 'blur',validator: checkPhone}],
        amount:[{ required: false, trigger: 'blur',validator: checkInt}],
        code:[{ required: true, trigger: 'blur',message:this.$t('menu.germplasm.resources.tips.checkbaocunnum')}],
        name:[{ required: true, trigger: 'blur',message:this.$t('menu.germplasm.resources.tips.checkname')}],
        introductionYear:[{ required: true, trigger: 'change',message:this.$t('menu.germplasm.resources.tips.checkyear')}],
      },
      dialogVisible:false,
      dialogImageUrl:'',
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/os/germplasm/importData"
      },
      uploadImg: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload"
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询育种群体库列表 */
    getList() {
      this.loading = true;
      listGermplasm(this.queryParams).then(response => {
        this.hybridList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        name:'',
        alias:'',
        source:'',
        type:'',
        typeRemark:'',
        sourceArea:'',
        firstIntroducer:'',
        firstIntroducerPhone:'',
        introducer:'',
        introducerPhone:'',
        introductionMethod:'',
        introductionYear:'',
        libName:'',
        saveMethod:'',
        amount:'',
        isProtect:'',
        describe:'',
        application:'',
        germplasmImgList:[],
      };
      this.typeRemark = {
         breedingCompany:'',
         breedingMethod:'',
         breedingYear:''
      }
      this.imgList = [];
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
      this.open = true;
      this.title = this.$t('menu.germplasm.resources.title.add');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGermplasm(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改育种群体库";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let form = JSON.parse(JSON.stringify(this.form));
          if(form.type === '选育品种') {
            let remark = JSON.stringify(this.typeRemark)
            Object.assign(form,{typeRemark: remark})
          } else {
            delete form.typeRemark
          }
          if(this.fileList.length > 0) {
            this.fileList.forEach(item => {
              form.germplasmImgList.push({imgUrl: item.response.fileName})
            })
          } else {
            delete form.germplasmImgList
          }
          if (this.form.id != undefined) {
            updateGermplasm(form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGermplasm(form).then(response => {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(this.$t('menu.germplasm.resources.tips.deleteconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return delGermplasm(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess(this.$t('common.tips.success'));
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.germplasm.resources.tips.exportconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return exportGermplasm(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.$t('menu.germplasm.resources.title.import');
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, this.$t('menu.germplasm.resources.title.importresult'), { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //跳转进入条码库
    handleViewCode(row) {
      this.$router.push({path: '/germplasm/code',query:{id:row.id,year: row.introductionYear}})
    },
    //多图片上传
    handleRemove(file, fileList) {//移除
      console.log(file, fileList);
      this.fileList = fileList;
    },
    handlePictureCardPreview(file) {//预览
      console.log(file)
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleError(err, file, fileList){//上传失败
      console.log(err)
    },
    beforeAvatarUpload(file) {//文件上传之前调用做一些拦截限制
      console.log(file);
      const isJPG = true;
      const isLt2M = file.size / 1024 / 1024 < 20;
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 20MB!');
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res, file,fileList) {//图片上传成功
      this.fileList = fileList;
    }
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
  .app-container >>> .el-dialog__body{
    padding:0 !important;
  }
</style>
