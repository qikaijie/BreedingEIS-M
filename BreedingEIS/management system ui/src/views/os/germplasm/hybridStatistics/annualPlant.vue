<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item :label="$t('menu.germplasm.statistical.label.year')" prop="hybridYear">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.hybridYear"
                        @change="handleYearChange"
                        value-format="yyyy"
                        type="year">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('menu.germplasm.statistical.label.zajiaozuhe')" prop="hybridId">
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
        <el-table-column :label="$t('menu.germplasm.statistical.label.tiaoma')" align="center" width="200" prop="code" sortable/>
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
      // ??????????????????????????????
      statisticsYearList:[],
      yearList:[],
      hybridList:[],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hybridYear:'',
        hybridId:''
      },
      // ????????????
      form: {},
      // ????????????
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
    /** ??????????????????????????????????????? */
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
    /** ?????????????????????????????? */
    getList() {
      this.loading = true;
      listStatisticsYear(this.queryParams).then(response => {
        this.statisticsYearList = Array.isArray(response.data) ? response.data : [];
        this.yearList = Array.isArray(response.yearList) ? response.yearList : [];
        this.total = this.statisticsYearList.length;
        this.loading = false;
      });
    },
    // ????????????
    cancel() {
      this.open = false;
      this.reset();
    },
    // ????????????
    reset() {
      this.form = {
        id: undefined,
        userId: undefined,
        plantId: undefined,
        createTime: undefined
      };
      this.resetForm("form");
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
    /** ?????????????????? */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('?????????????????????????????????????????????"' + ids + '"?????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return delCollect(ids);
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
        return getList(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
    },
  }
};
</script>
