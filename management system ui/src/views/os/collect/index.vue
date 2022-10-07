<template>
  <div class="app-container">
    <div>
      <el-table v-loading="loading" :data="collectList"  border fit highlight-current-row stripe element-loading-text="Loading" @sort-change="sortChange">
        <el-table-column :label="$t('common.label.sequence')" align="center" width="115">
          <template slot-scope="scope">
            {{scope.$index+1}}
          </template>
        </el-table-column>
        <el-table-column :label="$t('menu.breeding.collect.label.tiaoma')" align="center" prop="codeTwo" sortable="custom">
          <template slot-scope="scope">
            <span style="cursor: pointer;color:#4DA964;" @click="viewRecordList(scope.row)">
            <i class="el-icon-star-on" style="color:#FAC215;font-size:18px;margin-left:4px;" v-if="scope.row.level == 1"></i>
            <i class="el-icon-star-on" style="color:red;font-size:18px;margin-left:4px;" v-if="scope.row.level == 2"></i>{{scope.row.codeTwo}}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('menu.breeding.collect.label.zajiaozuhe')" align="center" prop="hybridName" sortable="custom"/>
        <el-table-column :label="$t('menu.breeding.collect.label.creater')" align="center" prop="userName" />
        <el-table-column :label="$t('menu.breeding.collect.label.date')" align="center" prop="createTime" />
        <el-table-column :label="$t('common.label.operation')" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              style="color:#4DA964;font-size: 14px;"
              @click="handleUpdate(scope.row)"
            >{{$t('menu.breeding.collect.button.cancel')}}</el-button>
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
import { listCollect, getCollect, delCollect, addCollect, updateCollect, exportCollect ,delPlantCollect} from "@/api/os/collect";

export default {
  name: "Collect",
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
      collectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        plantId: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户收藏植物列表 */
    getList() {
      this.loading = true;
      listCollect(this.queryParams).then(response => {
        this.collectList = response.rows;
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
    /** 取消收藏按钮操作 */
    handleUpdate(row) {
      let id = row.id;
      this.$confirm(this.$t('menu.breeding.recodelist.tips.cancel'), this.$t('common.title.warning'), {
        confirmButtonText: this.$t('common.button.ok'),
        cancelButtonText: this.$t('common.button.cancle'),
        type: "warning"
      }).then(function() {
        let param = {
          id: id
        }
        return delPlantCollect(param);
      }).then(() => {
        this.getList();
        this.msgSuccess("取消收藏成功");
      }).catch(function() {});
    },
    viewRecordList(row) {
      this.$router.push({ path: "/hybrid/recordList", query: { plantId: row.plantId, plantCode:row.plantCode } });
    },
    sortChange(column, prop, order) {
      Object.assign(this.queryParams,{prop:column.prop,order:column.order})
      this.getList()
    }
  }
};
</script>
