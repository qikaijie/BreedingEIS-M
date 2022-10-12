<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="80px">
      <el-form-item :label="$t('menu.breeding.recodelist.label.nianfen')" prop="hybridYear">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.hybridYear"
                        @change="handleYearChange"
                        value-format="yyyy"
                        type="year">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.recodelist.label.zajiaozuhe')" prop="hybridId">
        <el-select v-model="queryParams.hybridId" placeholder="">
          <el-option v-for="(item,index) of hybridList" :key="index" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.recodelist.label.tiaoma')" prop="plantCode">
        <el-autocomplete
          class="inline-input"
          v-model="queryParams.plantCode"
          :fetch-suggestions="querySearch"
          :placeholder="$t('menu.breeding.recodelist.label.tiaoma')"
          @select="handleSelect"
        ></el-autocomplete>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.recodelist.label.timesection')" prop="checkInTime">
        <el-date-picker
          v-model="queryParams.checkInTime"
          type="daterange"
          :range-separator="$t('menu.breeding.recodelist.label.timeto')"
          value-format="yyyy-MM-dd"
          :start-placeholder="$t('menu.breeding.recodelist.label.starttime')"
          :end-placeholder="$t('menu.breeding.recodelist.label.endtime')">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery" class="filter-item" type="primary" icon="el-icon-search" size="mini">{{$t('common.button.submit')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{$t('common.button.reset')}}</el-button>
      </el-form-item>
    </el-form>

	<el-row :gutter="10" class="mb8">
	  <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >{{$t('common.button.import')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:plant/record:export']"
        >{{$t('common.button.export')}}</el-button>
      </el-col>
    </el-row>

	<el-table v-loading="loading" :data="plantRecordList"  border fit highlight-current-row stripe element-loading-text="Loading">
	    <el-table-column :label="$t('menu.breeding.recodelist.label.time')" align="center" prop="createTime" width="100"/>
	    <el-table-column :label="$t('menu.breeding.recodelist.label.tiaoma')" align="center" prop="plantCode" width="100">
	      <template slot-scope="scope">
	        <span  style="color:red;" v-if="scope.row.isCollect == 1">
	          {{scope.row.plantCode}}
	          <i class="el-icon-star-on" style="color:#FAC215;font-size:18px;margin-left:4px;" v-if="scope.row.collectLevel == 1"></i>
	          <i class="el-icon-star-on" style="color:red;font-size:18px;margin-left:4px;" v-if="scope.row.collectLevel == 2"></i>
	        </span>
	        <span v-else>
	          {{scope.row.plantCode}}
	        </span>
	      </template>
	    </el-table-column>
	    <el-table-column :label="$t('menu.breeding.recodelist.label.zajiaozuhe')" align="center" prop="hybridName" width="100"/>
	    <el-table-column :label="$t('menu.breeding.recodelist.label.creater')" align="center" prop="createByName" width="100"/>
	    <el-table-column :label="$t('menu.breeding.recodelist.label.evaluationcontent')" align="left">
	      <template slot-scope="scope">
	        <span v-if="scope.row.attributeValuesList">
	          <span v-for="(item,index) of scope.row.attributeValuesList" :key="index">{{item.name}}：{{item.value}}<span v-if="index != (scope.row.attributeValuesList.length - 1)" style="color:red;font-weight: bold;">&nbsp;&nbsp;|&nbsp;&nbsp;</span></span>
	        </span>
	      </template>
	    </el-table-column>
	    <el-table-column :label="$t('menu.breeding.recodelist.label.image')" align="center" width="80">
	      <template slot-scope="scope">
	        <el-button
	            size="mini"
	            type="text"
	            icon="el-icon-view"
	            @click="getImageList(scope.row.plantId,scope.row.id)"
	          >{{ $t('common.button.view') }}</el-button>
	      </template>
	    </el-table-column>
	  </el-table>

	  <pagination
	    v-show="total>0"
	    :total="total"
	    :page.sync="queryParams.pageNum"
	    :limit.sync="queryParams.pageSize"
	    @pagination="getPlantRecordList"
	  />
    <!--<el-dialog :title="title" :visible.sync="openImg" width="750px" append-to-body>
      <el-image v-for="(item,index) of plantImgList" :src="item.path" :key="index" :z-index="index" placeholder="图片加载中" class="ui-plant-img" :preview-src-list="prevImgList" v-loading="imgLoading" @load="loadSuccess"></el-image>
    </el-dialog>-->

    <viewer :images="[]" >
    </viewer>
    
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
          {{ $t('common.tips.importfileext') }}
          <el-link type="info" style="font-size:16px;color:red;" @click="importTemplate">{{ $t('common.button.downloadtemplate') }}</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">{{$t('common.button.ok')}}</el-button>
        <el-button @click="upload.open = false">{{$t('common.button.cancle')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listPlant } from "@/api/os/plant";
  import { listPlantRecord, getPlantRecord, delPlantRecord, addPlantRecord, updatePlantRecord, exportPlantRecord, importTemplate } from "@/api/os/plant/record";
  import { listHybridByYear } from "@/api/os/hybrid";
  import { getToken } from "@/utils/auth";
  import { listPlantImage } from "@/api/os/plant/image";

  export default {
    name: "Code",
    data() {
      const  checkInt = (rule, value, callback) => {
        if ((Number(value))&&(value)%1 === 0) {
          callback();
        }else {
          return callback(new Error('请输入整数'));
        }
      }
      return {
        hybrid:'',
        activeCode:'',
        // 遮罩层
        loading: false,
        imgLoading:true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 育种群体库表格数据
        plantList: [],
        plantRecordList: [],
        plantImgList:[],
        prevImgList:[],
        hybridList:[],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        openImg:false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          hybridYear:'',
          hybridId:'',
          plantCode:'',
          plantId:'',
          endTime:'',
          startTime:'',
          checkInTime:[]
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
		  url: process.env.VUE_APP_BASE_API + "/os/plant/record/importData"
		},
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          plantRidge:[{ required: false, trigger: 'blur',validator: checkInt}],
          plantRow:[{ required: false, trigger: 'blur',validator: checkInt}],
          count:[{ required: false, trigger: 'blur',validator: checkInt}]
        }
      };
    },
    computed: {
    },
    mounted() {
      let plantId = this.$route.query.plantId
      if(plantId) {
        this.queryParams.plantId = plantId
        this.queryParams.plantCode = this.$route.query.plantCode
      }
      this.getPlantRecordList();
    },
    methods: {
      // *********************************** 公共方法 *****************************************
      filterNullFields (options) { // 删除空字段
        let param = Object.assign(options)
        if (Object.keys.length > 0) {
          for (let key in param) {
            if (!param[key] && param[key] !== 0) {
              delete param[key]
            }
          }
        }
        return param
      },
      getQueryParam (value) { // 获取查询字段参数 ٩(๑❛ᴗ❛๑)۶
        let model = this.queryParams
        let options = {}
        for (const key in model) {
          if (key !== 'checkInTime') {
            Object.assign(options, {[key]: model[key]})
          }
          delete model.currentPage
        }
        let time = {}
        if (Array.isArray(model.checkInTime) && model.checkInTime.length) {
          time = {
            startTime: model.checkInTime[0],
            endTime: model.checkInTime[1]
          }
        }
        let param = Object.assign({}, options, value, time)
        return this.filterNullFields(param)
      },
      /** 通过年份查询杂交组合库列表 */
      getHybridByYear(year) {
        let param = {
          hybridYear : year
        }
        listHybridByYear(param).then(response => {
          if(response.data) {
            this.hybridList = response.data;
          }
        });
      },
      /** 查询育种群体库列表 */
      querySearch(queryString, cb) {
        let param = {
          code: queryString
        }
        listPlant(param).then(response => {
          let plantList = Array.isArray(response.rows) ? response.rows : [];
          if(plantList.length > 0){
            let plantArray = []
            plantList.forEach((item) => {
              plantArray.push({
                value:item.code,
                plantId:item.id
              })
            })
            cb(plantArray);
          } else {
            cb([]);
          }
        });
      },
      handleSelect(item) {
        this.queryParams.plantId = item.plantId
        console.log(item);
      },
      /** 查询分组下的属性集合 */
      getPlantRecordList() {
        this.loading = true;
        listPlantRecord(this.getQueryParam()).then(response => {
          this.plantRecordList = response.rows;
          this.plantRecordList.forEach((item,index) => {
            if(item.attributeValues) {
              let attributeValues = JSON.parse(item.attributeValues);
              Object.assign(item,{attributeValuesList : attributeValues})
            }
          })
          this.total = response.total;
          this.loading = false;
        });
      },
      //获取图片列表
      getImageList(plantId,recordId ) {
        let param = {
          plantId: plantId,
          recordId: recordId
        }
        listPlantImage(param).then(response => {
          if(response.code == 200) {
            this.plantImgList = Array.isArray(response.data) ? response.data : [];
            let prevImgList = [];
            this.plantImgList.forEach((item) => {
              prevImgList.push(item.path)
            })
            this.prevImgList = prevImgList;
            this.$viewerApi({
              images: prevImgList,
            })
            this.openImg = true;
            this.title = this.$t('menu.breeding.recodelist.title.viewimg');
          } else {
            this.msgError(response.msg)
          }
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
          hybridId: undefined,
          sowingCode: undefined,
          plantBase: undefined,
          plantArea: undefined,
          plantRidge: undefined,
          plantRow: undefined,
          fieldNumber: undefined,
          number: undefined,
          plantNumber: undefined,
          count: undefined
        };
      },
      loadSuccess() {
        this.imgLoading = false
      },
      /** 切换条码信息*/
      handleChangeCode(id) {
        this.activeCode = id
        this.getPlantRecordList(id);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getPlantRecordList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.plantId = ''
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      handleYearChange(year) {
        this.hybridList = [];
        this.queryParams.hybridId = '';
        this.getHybridByYear(year)
      },
      handleHybridChange(id) {
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getPlantRecord(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改育种群体库";
        });
      },
	  /** 导入按钮操作 */
	  handleImport() {
		this.upload.title = this.$t('menu.breeding.recodelist.title.importrecode');
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
		  this.$alert(response.msg, this.$t('menu.breeding.recodelist.title.importresult'), { dangerouslyUseHTMLString: true });
		  this.getList();
	  },
	  // 提交上传文件
	  submitFileForm() {
		  this.$refs.upload.submit();
	  },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm(this.$t('menu.breeding.recodelist.tips.exportconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return exportPlantRecord(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>
