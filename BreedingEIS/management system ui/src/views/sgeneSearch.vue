<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item :label="$t('login.sgeneSearch.speciesName')" prop="speciesName">
        <el-input
          v-model="queryParams.speciesName"
          :placeholder="$t('login.sgeneSearch.speciesName')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('login.sgeneSearch.speciesEnName')" prop="speciesName2">
        <el-input
          v-model="queryParams.speciesName2"
          :placeholder="$t('login.sgeneSearch.speciesEnName')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.button.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="sgeneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('login.sgeneSearch.speciesName')" align="center" prop="speciesName" />
      <el-table-column :label="$t('login.sgeneSearch.speciesEnName')" align="center" prop="speciesName2" />
      <el-table-column :label="$t('login.sgeneSearch.s1')" align="center" prop="s1" />
      <el-table-column :label="$t('login.sgeneSearch.s2')" align="center" prop="s2" />
      <el-table-column :label="$t('login.sgeneSearch.s3')" align="center" prop="s3" />
      <el-table-column :label="$t('login.sgeneSearch.s4')" align="center" prop="s4" />
      <el-table-column :label="$t('login.sgeneSearch.sGene')" align="center" prop="sGene" />
      <el-table-column :label="$t('login.sgeneSearch.bloomStartDate')" align="center" prop="bloomStartDate" />
      <el-table-column :label="$t('login.sgeneSearch.bloomEndDate')" align="center" prop="bloomEndDate" />
      <el-table-column :label="$t('login.sgeneSearch.pollenAbortion')" align="center" prop="pollenAbortion" />
      <el-table-column :label="$t('login.sgeneSearch.recommended')" align="center" prop="recommended" />
      <el-table-column :label="$t('login.sgeneSearch.source')" align="center" prop="source" />
      <el-table-column :label="$t('login.sgeneSearch.area')" align="center" prop="area" />
      <el-table-column :label="$t('login.sgeneSearch.updateDate')" align="center" prop="updateDate" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import { listSgene, getSgene, delSgene, addSgene, updateSgene, exportSgene } from "@/api/os/sgene";

export default {
  name: "Sgene",
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
      // sgene表格数据
      sgeneList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        speciesName: undefined,
        speciesName2: undefined,
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
    /** 查询sgene列表 */
    getList() {
      this.loading = true;
      listSgene(this.queryParams).then(response => {
        this.sgeneList = response.rows;
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
        speciesName: undefined,
        speciesName2: undefined,
        s1: undefined,
        s2: undefined,
        s3: undefined,
        s4: undefined,
        sGene: undefined,
        bloomStartDate: undefined,
        bloomEndDate: undefined,
        pollenAbortion: undefined,
        recommended: undefined,
        source: undefined,
        area: undefined,
        updateDate: undefined
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
      this.title = "添加sgene";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSgene(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改sgene";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSgene(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSgene(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
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
      this.$confirm('是否确认删除sgene编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSgene(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有sgene数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSgene(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>