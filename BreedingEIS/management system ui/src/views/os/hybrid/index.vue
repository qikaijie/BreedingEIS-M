<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item :label="$t('menu.breeding.breeding.label.qinben1')" prop="maleParent">
        <el-input
          v-model="queryParams.maleParent"
          :placeholder="$t('menu.breeding.breeding.label.qinben1')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.breeding.label.qinben2')" prop="femaleParent">
        <el-input
          v-model="queryParams.femaleParent"
          :placeholder="$t('menu.breeding.breeding.label.qinben2')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.breeding.label.nianfen')" prop="hybridYear">
        <el-date-picker clearable size="small" v-model="queryParams.hybridYear" value-format="yyyy" type="year">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary" 
    	  icon="el-icon-plus" 
    	  size="mini" 
          @click="handleAdd"
          v-hasPermi="['os:hybrid:add']"
        >{{ $t('menu.breeding.breeding.button.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >{{ $t('common.button.import') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          v-hasPermi="['os:hybrid:remove']"
        >{{ $t('common.button.delete') }}</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="hybridList" border fit highlight-current-row stripe element-loading-text="Loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('common.label.sequence')" align="center" width="80">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.breeding.breeding.label.zajiaodaihao')" align="center" prop="sowingCode" />
      <el-table-column :label="$t('menu.breeding.breeding.label.muben')" align="center" prop="femaleParent" />
      <el-table-column :label="$t('menu.breeding.breeding.label.fuben')" align="center" prop="maleParent" />
      <el-table-column :label="$t('menu.breeding.breeding.label.zajiaoriqi')" align="center" prop="hybridDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hybridDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.breeding.breeding.label.zhongzishuliang')" align="center" prop="seedNums" width="80"/>
      <el-table-column :label="$t('menu.breeding.breeding.label.chakantiaoma')" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewCode(scope.row)"
            v-hasPermi="['os:hybrid:view']"
          >{{ $t('common.button.view') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:hybrid:remove']"
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
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <div class="ui-form-title">{{ $t('menu.breeding.breeding.title.zajiaoinfo') }}</div>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.muben')" prop="femaleParent">
              <el-input v-model="form.femaleParent" :placeholder="$t('menu.breeding.breeding.label.muben')"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.mubenlaiyuan')" prop="femaleParentSource">
              <el-input v-model="form.femaleParentSource" :placeholder="$t('menu.breeding.breeding.label.mubenlaiyuan')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.fuben')" prop="maleParent">
              <el-input v-model="form.maleParent" :placeholder="$t('menu.breeding.breeding.label.fuben')"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.fubenlaiyuan')" prop="maleParentSource">
              <el-input v-model="form.maleParentSource" :placeholder="$t('menu.breeding.breeding.label.fubenlaiyuan')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.operator')" prop="operator">
              <el-input v-model="form.operator" :placeholder="$t('menu.breeding.breeding.label.operator')"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.zajiaoriqi')" prop="hybridDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.hybridDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.zajiaoriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="ui-form-title">{{ $t('menu.breeding.breeding.title.quzhonginfo') }}</div>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.caiguoriqi')" prop="caiguoDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.caiguoDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.caiguoriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.quzhongriqi')" prop="quzhongDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.quzhongDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.quzhongriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="ui-form-title">{{ $t('menu.breeding.breeding.title.bozhonginfo') }}</div>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.zhongzishuliang')" prop="seedNums">
              <el-input v-model="form.seedNums" :placeholder="$t('menu.breeding.breeding.label.zhongzishuliang')" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.bozhongriqi')" prop="sowingDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.sowingDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.bozhongriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.zajiaodaihao')" prop="sowingCode">
              <el-input v-model="form.sowingCode" :placeholder="$t('menu.breeding.breeding.label.zajiaodaihao')" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.bozhongmethod')" prop="sowingMethod">
              <el-input v-model="form.sowingMethod" :placeholder="$t('menu.breeding.breeding.label.bozhongmethod')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.cengjiriqi')" prop="seedStratificationDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.seedStratificationDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.cengjiriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="11">
          </el-col>
        </el-row>
        <div class="ui-form-title">{{ $t('menu.breeding.breeding.title.dingzhiinfo') }}</div>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.dingzhimethod')" prop="colonizationMode">
              <el-input v-model="form.colonizationMode" :placeholder="$t('menu.breeding.breeding.label.dingzhimethod')" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.dingzhisite')" prop="colonizationPlace">
              <el-input v-model="form.colonizationPlace" :placeholder="$t('menu.breeding.breeding.label.dingzhisite')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.dingzhicount')" prop="colonizationTotal">
              <el-input v-model="form.colonizationTotal" :placeholder="$t('menu.breeding.breeding.label.dingzhicount')" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.dingzhiriqi')" prop="colonizationDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.colonizationDate"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :placeholder="$t('menu.breeding.breeding.label.dingzhiriqi')">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.leader')" prop="colonizationHead">
              <el-input v-model="form.colonizationHead" :placeholder="$t('menu.breeding.breeding.label.leader')" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item :label="$t('menu.breeding.breeding.label.contact')" prop="colonizationPhone">
              <el-input v-model="form.colonizationPhone" :placeholder="$t('menu.breeding.breeding.label.contact')" />
            </el-form-item>
          </el-col>
        </el-row>
        <div class="ui-form-title"></div>
        <el-col :span="22">
          <el-form-item :label="$t('common.label.remarks')" prop="remark">
            <el-input v-model="form.remark" type="textarea" :placeholder="$t('common.label.remarks')" style="height:82px;"/>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button type="primary" @click="submitForm">{{ $t('common.button.ok') }}</el-button>
        <el-button @click="cancel">{{ $t('common.button.cancle') }}</el-button>
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
        <div class="el-upload__tip" style="color:green;" slot="tip">{{ $t('common.tips.importfileext') }}</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">{{ $t('common.button.ok') }}</el-button>
        <el-button @click="upload.open = false">{{ $t('common.button.cancle') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHybrid, getHybrid, delHybrid, addHybrid, updateHybrid, exportHybrid, importTemplate } from "@/api/os/hybrid";
import { getToken } from "@/utils/auth";
export default {
  name: "Hybrid",
  data() {
    const  checkInt = (rule, value, callback) => {
       if ((Number(value))&&(value)%1 === 0) {
          callback();
        }else {
          return callback(new Error(this.$t('common.tips.checkint')));
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
        maleParent: '',
        femaleParent: '',
        hybridYear:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        femaleParent:[{ required: true, trigger: 'blur',message:this.$t('menu.breeding.breeding.tips.checkmuben')}],
        maleParent:[{ required: true, trigger: 'blur',message:this.$t('menu.breeding.breeding.tips.checkfuben')}],
        hybridDate:[{ required: true, trigger: 'blur',message:this.$t('menu.breeding.breeding.tips.checkzajiaoriqi')}],
        sowingCode:[{ required: true, trigger: 'blur',message:this.$t('menu.breeding.breeding.tips.checkzajiaodaihao')}],
        //seedNums:[{ required: false, trigger: 'blur',validator: checkInt}],
        colonizationTotal:[{ required: true, trigger: 'blur',validator: checkInt}],
        colonizationPhone:[{ required: false, trigger: 'blur',validator: checkPhone}],
      },
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
        url: process.env.VUE_APP_BASE_API + "/os/hybrid/importData"
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
      listHybrid(this.queryParams).then(response => {
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
        id: undefined,
        maleParent: undefined,
        maleParentSource: undefined,
        femaleParent: undefined,
        femaleParentSource: undefined,
        caiguoDate:undefined,
        quzhongDate:undefined,
        hybridYear: undefined,
        hybridDate: undefined,
        inflorescenceNums: undefined,
        place: undefined,
        operator: undefined,
        seedNums: undefined,
        sowingDate: undefined,
        sowingCode: undefined,
        sowingMethod: undefined,
        colonizationMode: undefined,
        colonizationPlace: undefined,
        colonizationDate: undefined,
        colonizationTotal: undefined,
        colonizationHead: undefined,
        colonizationPhone: undefined,
        superiorPlantNums: undefined,
        seedStratificationDate:undefined,
        remark: undefined,
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
      this.open = true;
      this.title = this.$t('menu.breeding.breeding.title.xinzeng');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHybrid(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('menu.breeding.breeding.title.xiugai');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateHybrid(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          } else {
            addHybrid(this.form).then(response => {
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
      this.$confirm(this.$t('menu.breeding.breeding.tips.deleteconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return delHybrid(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess(this.$t('common.tips.success'));
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.breeding.tips.exportconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return exportHybrid(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.$t('menu.breeding.breeding.title.import');
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
      this.$alert(response.msg, this.$t('menu.breeding.breeding.title.importresult'), 
      { 
      	dangerouslyUseHTMLString: true, 
      	center: true,
      	confirmButtonText: this.$t('common.button.ok'),
        callback: action => {
            this.getList();
        }
      });
      
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //跳转进入条码库
    handleViewCode(row) {
      this.$router.push({path: '/hybrid/code',query:{id:row.id,year: row.hybridYear}})
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
