<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item :label="$t('menu.breeding.tiaomaku.label.tiaoma')" prop="plantCode">
        <el-autocomplete
          class="inline-input"
          v-model="queryParams.plantCode"
          :fetch-suggestions="querySearch"
          :placeholder="$t('menu.breeding.tiaomaku.label.tiaoma')"
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
        <el-table-column :label="$t('menu.breeding.tiaomaku.label.year')" align="center" prop="year" width="150" sortable/>
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
      // ?????????
      loading: false,
      // ????????????
      ids: [],
      // ???????????????
      single: true,
      // ???????????????
      multiple: true,
      // ?????????
      total: 0,
      // ??????????????????????????????
      statisticsPlantList: [],
      attributeList:[],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        plantCode:'',
        plantId:'',
      },
      // ????????????
      form: {},
      // ????????????
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
    /** ?????????????????????????????? */
    getList() {
      this.loading = true;
      listStatisticsPlant(this.queryParams).then(response => {
        this.statisticsPlantList = Array.isArray(response.data) ? response.data : [];
        this.attributeList = Array.isArray(response.attributeList) ? response.attributeList : [];
        this.total = this.statisticsPlantList.length;
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
      this.queryParams.plantId = '';
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
    /** ??????????????????????????? */
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
