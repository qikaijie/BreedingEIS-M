<template>
  <div class="app-container">
    <div>
      <el-table v-loading="loading" :data="collectList"  border fit highlight-current-row stripe element-loading-text="Loading" @sort-change="sortChange">
        <el-table-column :label="$t('common.label.sequence')" align="center" width="115">
          <template slot-scope="scope">
            {{scope.$index+1}}
          </template>
        </el-table-column>
        <el-table-column :label="$t('menu.germplasm.collect.label.tiaoma')" align="center" prop="codeTwo" sortable="custom">
          <template slot-scope="scope">
            <span style="cursor: pointer;color:#4DA964;" @click="viewRecordList(scope.row)">
	            <i class="el-icon-star-on" style="color:#FAC215;font-size:18px;margin-left:4px;" v-if="scope.row.level == 1"></i>
            	<i class="el-icon-star-on" style="color:red;font-size:18px;margin-left:4px;" v-if="scope.row.level == 2"></i>{{scope.row.codeTwo}}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('menu.germplasm.collect.label.name')" align="center" prop="germplasmName" sortable="custom" />
        <el-table-column :label="$t('menu.germplasm.collect.label.creater')" align="center" prop="userName" />
        <el-table-column :label="$t('menu.germplasm.collect.label.date')" align="center" prop="createTime" />
        <el-table-column :label="$t('common.label.operation')" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              style="color:#4DA964;font-size: 14px;"
              @click="handleUpdate(scope.row)"
            >{{$t('menu.germplasm.collect.button.cancel')}}</el-button>
            <!--v-hasPermi="['os:collect:edit']"-->
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
    </div>
  </div>
</template>

<script>
import { listCollect, getCollect, delCollect, addCollect, updateCollect, exportCollect } from "@/api/os/germplasm/collect";

export default {
  name: "Collect",
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
      collectList: [],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        plantId: undefined,
      },
      // ????????????
      form: {},
      // ????????????
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** ?????????????????????????????? */
    getList() {
      this.loading = true;
      listCollect(this.queryParams).then(response => {
        this.collectList = response.rows;
        this.total = response.total;
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
    /** ???????????????????????? */
    handleUpdate(row) {
      const ids = row.id ;
      this.$confirm(this.$t('menu.germplasm.recodelist.tips.cancel'), this.$t('common.title.warning'), {
        confirmButtonText: this.$t('common.button.ok'),
        cancelButtonText: this.$t('common.button.cancle'),
        type: "warning"
      }).then(function() {
        return delCollect(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess(this.$t('common.tips.success'));
      }).catch(function() {});
    },
    viewRecordList(row) {
      this.$router.push({ path: "/germplasm/recordList", query: { seedlingId: row.seedlingId,seedlingCode : row.seedlingCode } });
    },
    sortChange(column, prop, order) {
      Object.assign(this.queryParams,{prop:column.prop,order:column.order})
      this.getList()
    }
  }
};
</script>
