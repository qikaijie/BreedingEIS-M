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
      // sgene????????????
      sgeneList: [],
      // ???????????????
      title: "",
      // ?????????????????????
      open: false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        speciesName: undefined,
        speciesName2: undefined,
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
    /** ??????sgene?????? */
    getList() {
      this.loading = true;
      listSgene(this.queryParams).then(response => {
        this.sgeneList = response.rows;
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
      this.title = "??????sgene";
    },
    /** ?????????????????? */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSgene(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "??????sgene";
      });
    },
    /** ???????????? */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSgene(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("????????????");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSgene(this.form).then(response => {
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
      this.$confirm('??????????????????sgene?????????"' + ids + '"?????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return delSgene(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("????????????");
        }).catch(function() {});
    },
    /** ?????????????????? */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('????????????????????????sgene??????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
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