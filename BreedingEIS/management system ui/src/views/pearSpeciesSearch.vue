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
      // ?????????????????????
      PearSpeciesList: [],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        speciesName: undefined,
        maleParent: undefined,
        femaleParent: undefined,
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
    /** ????????????????????? */
    getList() {
      this.loading = true;
      listPearSpecies(this.queryParams).then(response => {
        this.PearSpeciesList = response.rows;
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
        speciesName: undefined,
        maleParent: undefined,
        femaleParent: undefined,
        company: undefined,
        remark: undefined,
        reference: undefined
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
    // ?????????????????????
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** ?????????????????? */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "???????????????";
    },
    /** ?????????????????? */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPearSpecies(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "???????????????";
      });
    },
    /** ???????????? */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePearSpecies(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("????????????");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPearSpecies(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("????????????");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** ?????????????????? */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('????????????????????????????????????"' + ids + '"?????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return delPearSpecies(ids);
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
          return exportPearSpecies(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>