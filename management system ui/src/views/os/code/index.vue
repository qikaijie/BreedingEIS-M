<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item :label="$t('menu.breeding.tiaomaku.label.year')" prop="hybridYear">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.hybridYear"
                        @change="handleYearChange"
                        value-format="yyyy"
                        type="year">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.tiaomaku.label.zajiaozuhe')" prop="hybridId">
        <el-select v-model="queryParams.hybridId" placeholder="">
          <el-option v-for="(item,index) of hybridList" :key="index" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.tiaomaku.label.type')" prop="hybridId">
        <el-select v-model="queryParams.type" placeholder="">
          <el-option label="All" value="" />
          <el-option label="primary" value="1" />
          <el-option label="gaojie" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('menu.breeding.tiaomaku.label.code')" prop="code">
        <el-input :placeholder="$t('menu.breeding.tiaomaku.label.code')" clearable v-model="queryParams.code"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery" class="filter-item" type="primary" icon="el-icon-search" size="mini">{{ $t('common.button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.button.reset') }}</el-button>
      </el-form-item>
    </el-form>
    
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
    	<el-button 
    	  type="primary" 
    	  icon="el-icon-plus" 
    	  size="mini" 
    	  @click="createBarcode" 
    	  v-hasPermi="['os:plant:add']"
    	>{{ $t('menu.breeding.tiaomaku.button.createtiaoma') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExportQRcode"
          v-hasPermi="['os:plant:export']"
        >{{ $t('menu.breeding.tiaomaku.button.exporterweima') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:plant:export']"
        >{{ $t('menu.breeding.tiaomaku.button.exporttiaoma') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDeleteList"
          v-hasPermi="['os:plant:remove']"
        >{{ $t('common.button.delete') }}</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="plantList" border fit highlight-current-row stripe element-loading-text="Loading">
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.type')" align="center" prop="type" width="80">
      	<template slot-scope="scope">
           <span v-if="scope.row.type == 1">primary</span>
           <span v-if="scope.row.type == 2">gaojie</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.tiaoma')" align="center" prop="code"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.changma')" align="center" prop="codeOne" width="150"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.duanma')" align="center" prop="codeTwo" width="150"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.createtime')" align="center" prop="createTime" width="150"/>
      <el-table-column :label="$t('common.label.operation')" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getQRCode(scope.row)"
            v-hasPermi="['os:plant:export']"
          >{{ $t('menu.breeding.tiaomaku.button.createerweima') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="gaojieList(scope.row)"
            v-hasPermi="['os:plant:add']"
            v-if="scope.row.type === 1"
          >{{ $t('menu.breeding.tiaomaku.button.creategaojiema') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:plant:remove']"
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

    <el-dialog
      :title="$t('menu.breeding.tiaomaku.title.createtiaoma')"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      append-to-body
      width="80%"
    >
      <add v-if="dialogVisible" @closePage="dialogVisible = false" @reload="getList" :hybridId="hybridId" :sowingCode="sowingCode" :hybridName="hybridName" :plantList="allPlantList"></add>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openImg" width="500px" append-to-body>
      <div style="text-align: center;">
      	<img :src="qrCode" placeholder="图片加载中" class="ui-plant-img" v-loading="imgLoading" @load="loadSuccess" />
      	<div>
      		<a :href="qrCode" :download="qrCodeName" class="qrcode" style="color:#4DA964;cursor: pointer;">{{ $t('menu.breeding.tiaomaku.button.download') }}</a>
      	</div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listPlant, getPlant, getQRCode, delListPlant, delPlant, addPlant, updatePlant, exportPlant, listByHybridId } from "@/api/os/plant";
  import { listPlantRecord, getPlantRecord, delPlantRecord, addPlantRecord, updatePlantRecord, exportPlantRecord } from "@/api/os/plant/record";
  import { listHybridByYear } from "@/api/os/hybrid";
  import { listPlantImage } from "@/api/os/plant/image";
  import { downLoadZip } from "@/utils/zipdownload";
  import add from "./add";
export default {
  name: "Code",
  components: {
      add,
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    const  checkInt = (rule, value, callback) => {
      if ((Number(value))&&(value)%1 === 0) {
        callback();
      }else {
        return callback(new Error(this.$t('common.tips.checkint')));
      }
    }
    return {
      hybrid:'',
      activeCode:'',
      // 遮罩层
      loading: false,
      imgLoading:true,
      qrCode: "",
      qrCodeName: "",
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
      //批量添加植物传参
      dialogVisible:false,
      hybridId:'',
      sowingCode:'',
      hybridName:'',
      allPlantList:'',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hybridYear:'',
        hybridId:'',
        type:'',
        code:''
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
    codeDetail:function(){
      let code = (this.form.sowingCode != undefined ? this.form.sowingCode : '') + '-' + (this.form.plantBase != undefined ? this.form.plantBase : '') +''+ (this.form.plantArea != undefined ? this.form.plantArea : '') +'-'+ (this.form.plantRidge != undefined ? this.form.plantRidge : '') +""+ (this.form.plantRow != undefined ? this.form.plantRow : '') +'-'+ '(' + (this.form.plantNumber != undefined ? this.form.plantNumber : 0) +'~'+ ((this.form.plantNumber != undefined ? Number(this.form.plantNumber) : 0) + (this.form.count != undefined ? Number(this.form.count) : 0)) + ')'
      return code
    },
    codeNum:function () {
      let code = (this.form.fieldNumber != undefined ? this.form.fieldNumber : '') +'-'+ '(' + (this.form.number != undefined ? this.form.number : 0) + '~' +((this.form.number != undefined ? Number(this.form.number) : 0) + (this.form.count != undefined ? Number(this.form.count) : 0)) +')';
      return code
    }
  },
  mounted() {
    this.hybrid = this.$route.query.id
    if(this.hybrid) {
      this.queryParams.hybridId = Number(this.hybrid)
      this.queryParams.hybridYear = this.$route.query.year
      this.getHybridByYear(this.queryParams.hybridYear)
      this.getList()
    }
  },
  watch: {
    $route: {
      handler() {
        this.hybrid = this.$route.query.id
        if(this.hybrid) {
          this.hybridList = [];
          this.queryParams.hybridId = '';
          this.queryParams.hybridYear = '';
          this.plantList = [];
          this.plantRecordList = [];
          this.queryParams.hybridId = Number(this.hybrid)
          this.queryParams.hybridYear = this.$route.query.year
          this.getHybridByYear(this.queryParams.hybridYear)
          this.getList()
        }
        //深度监听，同时也可监听到param参数变化
      },
      deep: true,
    }
  },
  methods: {
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
    getList() {
      this.loading = true;
      listPlant(this.queryParams).then(response => {
        this.plantList = response.rows;
        this.plantList.forEach((item,index) => {
          if(item.attributeValues) {
            let attributeValues = JSON.parse(item.attributeValues);
            Object.assign(item,{attributeValuesList : attributeValues})
          }
        })
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分组下的属性集合 */
    getPlantRecordList(id) {
      let param = {plantId:id}
      listPlantRecord(param).then(response => {
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
    getQRCode(row) {
      getQRCode(row.id, this.$i18n.locale).then(response => {
        if(response.code == 200) {
          this.qrCode = response.data;
          this.qrCodeName = row.codeOne + ".png"
          this.openImg = true;
          this.title = this.$t('menu.breeding.tiaomaku.title.viewerweima');
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
    handleYearChange(year) {
      this.hybridList = [];
      this.queryParams.hybridId = '';
      this.plantList = [];
      this.plantRecordList = [];
      this.getHybridByYear(year)
    },
    handleHybridChange(id) {
      this.getList()
    },
    /** 新增按钮操作 */
    createBarcode() {
      if(this.queryParams.hybridYear && this.queryParams.hybridId) {   
      this.loading = true;
      listByHybridId(this.queryParams).then(response => {
        this.allPlantList = response.rows;
  
  		let sowingCode = "";
  		let hybridName = "";
        this.hybridList.forEach((item) => {
          if(item.id == this.queryParams.hybridId) {
            sowingCode = item.sowingCode;
            hybridName = item.name;
          }
        })
        
        this.hybridId = this.queryParams.hybridId;
        this.sowingCode = sowingCode;
        this.hybridName = hybridName;
        this.dialogVisible = true;
        
        this.loading = false;
      });     
        
      } else {
        this.msgInfo(this.$t('menu.breeding.tiaomaku.tips.checkzajiaozuhe'));
      }

    },
    gaojieList(row) {
    	this.$router.push({name:'gaojie', params:{codeId : row.id}});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPlant(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改育种群体库";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined && this.form.id != '') {
            updatePlant(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPlant(this.form).then(response => {
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
    handleDeleteList(row) {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.deleteconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return delListPlant(queryParams);
        }).then(response => {
          this.getList();
          this.msgSuccess(this.$t('common.tips.success'));
        }).catch(function() {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.deleteconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return delPlant(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess(this.$t('common.tips.success'));
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.exporttiaomaconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return exportPlant(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导出二维码按钮操作 */
    handleExportQRcode() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.exporterweimaconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          downLoadZip("/os/plant/exportQRcode?hybridId=" + queryParams.hybridId, "liuxn");
        }).catch(function() {});
    }
  }
};
</script>

<style scoped lang="scss">
  .app-container{
    position: relative;
  }
  .ui-code{
    width:100%;
    height: calc(100vh - 170px);
    display: flex;
    .ui-code-title{
      padding:0 12px;
      box-sizing: border-box;
      color:red;
      font-size:14px;
      height:48px;
      line-height: 48px;
    }
    .ui-list{
      width:100%;
      height:calc(100% - 48px);
    }
    .ui-code-left{
      width:300px;
      height:100%;
      background: #F8F8F8;
      text-align: center;
    }
    .ui-code-right{
      flex:1;
      min-width: 0;
      height:100%;
    }
  }
  .ui-barcode{
    width:100%;
    background: #F5F5F5;
    box-sizing: border-box;
    justify-content: center;
    align-items: center;
    .ui-barcode-detail{
        color:#101010;
        font-size:18px;
        line-height: 28px;
        margin-top:6px;
        letter-spacing: 5px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width:100%;
    }
    .ui-barcode-qr-code {
      width:200px;
      height:200px;
    }
    .ui-barcode-num1{
      box-sizing: border-box;
      line-height: 60px;
      font-size:24px;
      color:#101010;
      margin-top:10px;
      flex:1;
      min-width: 0;
      font-weight:bold;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .ui-barcode-num2{
      box-sizing: border-box;
      line-height: 42px;
      font-size:18px;
      color:#101010;
      margin-top:10px;
      flex:1;
      min-width: 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
  .ui-no-data{
    color:#BBBBBB;
    font-size:14px;
    width:100%;
    padding:150px 22px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    letter-spacing: 1px;
    text-align: center;
    line-height: 22px;
  }
  .ui-plant-img{
    width:260px;
    height:400px;
    cursor: pointer;
    text-align: center;
  }
</style>
