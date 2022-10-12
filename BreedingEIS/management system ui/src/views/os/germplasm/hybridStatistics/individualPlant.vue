<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item :label="$t('menu.germplasm.tiaomaku.label.tiaoma')" prop="plantCode">
        <el-autocomplete
          class="inline-input"
          v-model="queryParams.plantCode"
          :fetch-suggestions="querySearch"
          :placeholder="$t('menu.germplasm.tiaomaku.label.tiaoma')"
          @select="handleSelect"
        ></el-autocomplete>
      </el-form-item>
      <el-form-item>
        <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{$t('common.button.search')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{$t('common.button.reset')}}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="margin:10px 0;">
      <el-col :span="1.5">
        <el-button
          class="ui-default-add"
          style="width:126px;"
          @click="handleExport"
          v-hasPermi="['os:attribute:export']"
        >{{$t('common.button.export')}}</el-button>
      </el-col>
    </el-row>

    <div style="padding-top:2px;box-sizing: border-box;" class="ui-fixed-table">
      <el-table v-loading="loading" :data="statisticsPlantList"  border fit highlight-current-row stripe element-loading-text="Loading">
        <el-table-column :label="$t('menu.germplasm.tiaomaku.label.year')" align="center" prop="year" width="150" sortable/>
        <el-table-column v-for="(item,index) of attributeList" :prop="'value'+index" :key="index" align="center" :label="item.fieldName" width="150" sortable/>
      </el-table>

      <pagination
        v-show="total>0"
        v-if="false"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
  import { listStatisticsPlant } from "@/api/os/statistics";
  import { listPlant} from "@/api/os/plant";
export default {
  name: "IndividualPlant",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户收藏植物表格数据
      statisticsPlantList: [],
      attributeList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        plantCode:'',
        plantId:'',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      url: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ]
    };
  },
  created() {
    //this.getList();
  },
  methods: {
    /** 查询用户收藏植物列表 */
    getList() {
      this.loading = true;
      listStatisticsPlant(this.queryParams).then(response => {
        this.statisticsPlantList = Array.isArray(response.data) ? response.data : [];
        this.attributeList = Array.isArray(response.attributeList) ? response.attributeList : [];
        this.total = this.statisticsPlantList.length;
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
        userId: undefined,
        plantId: undefined,
        createTime: undefined
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
      this.queryParams.plantId = '';
      this.handleQuery();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除用户收藏植物编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCollect(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有属性池数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return getList(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
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
    },
  }
};
</script>

<style lang="scss">
/*  .ui-fixed-table .el-table__body{
    width:100% !important;
  }*/
</style>
