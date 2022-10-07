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

    <div style="padding-top:2px;box-sizing: border-box;">
      <el-table v-loading="loading" :data="statisticsYearList"  border fit highlight-current-row stripe element-loading-text="Loading">
        <el-table-column :label="$t('menu.breeding.tiaomaku.label.tiaoma')" align="center" width="200" prop="code" sortable/>
        <el-table-column v-for="(item,index) of yearList" :key="index" align="center" :label="item" width="100" :prop="'value'+index" sortable/>
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
  import { listStatisticsYear } from "@/api/os/statistics";
  import { listHybridByYear } from "@/api/os/hybrid";
export default {
  name: "AnnualPlant",
  data() {
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
      // 用户收藏植物表格数据
      statisticsYearList:[],
      yearList:[],
      hybridList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hybridYear:'',
        hybridId:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleYearChange(year) {
      this.queryParams.hybridId = '';
      this.getHybridByYear(year)
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
    /** 查询用户收藏植物列表 */
    getList() {
      this.loading = true;
      listStatisticsYear(this.queryParams).then(response => {
        this.statisticsYearList = Array.isArray(response.data) ? response.data : [];
        this.yearList = Array.isArray(response.yearList) ? response.yearList : [];
        this.total = this.statisticsYearList.length;
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
  }
};
</script>
