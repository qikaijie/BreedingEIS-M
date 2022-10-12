<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item :label="$t('login.pearSpeciesSearch.speciesName')" prop="speciesName">
        <el-input
          v-model="queryParams.speciesName"
          :placeholder="$t('login.pearSpeciesSearch.speciesName')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('login.pearSpeciesSearch.maleParent')" prop="maleParent">
        <el-input
          v-model="queryParams.maleParent"
          :placeholder="$t('login.pearSpeciesSearch.maleParent')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('login.pearSpeciesSearch.femaleParent')" prop="femaleParent">
        <el-input
          v-model="queryParams.femaleParent"
          :placeholder="$t('login.pearSpeciesSearch.femaleParent')"
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

    <el-table v-loading="loading" :data="PearSpeciesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('login.pearSpeciesSearch.speciesName')" align="center" prop="speciesName" />
      <el-table-column :label="$t('login.pearSpeciesSearch.maleParent')" align="center" prop="maleParent" />
      <el-table-column :label="$t('login.pearSpeciesSearch.femaleParent')" align="center" prop="femaleParent" />
      <el-table-column :label="$t('login.pearSpeciesSearch.company')" align="center" prop="company" />
      <el-table-column :label="$t('login.pearSpeciesSearch.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('login.pearSpeciesSearch.reference')" align="center" prop="reference" />
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
import { listPearSpecies, getPearSpecies, delPearSpecies, addPearSpecies, updatePearSpecies, exportPearSpecies } from "@/api/os/pearSpecies";

export default {
  name: "PearSpecies",
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
      // 梨物种表格数据
      PearSpeciesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        speciesName: undefined,
        maleParent: undefined,
        femaleParent: undefined,
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
    /** 查询梨物种列表 */
    getList() {
      this.loading = true;
      listPearSpecies(this.queryParams).then(response => {
        this.PearSpeciesList = response.rows;
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
        maleParent: undefined,
        femaleParent: undefined,
        company: undefined,
        remark: undefined,
        reference: undefined
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
      this.title = "添加梨物种";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPearSpecies(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改梨物种";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePearSpecies(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPearSpecies(this.form).then(response => {
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
      this.$confirm('是否确认删除梨物种编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPearSpecies(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有梨物种数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPearSpecies(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>