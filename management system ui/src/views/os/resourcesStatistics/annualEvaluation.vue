<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="资源名称" prop="userId">
        <el-select v-model="queryParams.type" placeholder="">
          <el-option label="已回复" :value="1" />
          <el-option label="未回复" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="田间编号" prop="femaleParentSource">
        <el-input
          v-model="queryParams.femaleParentSource"
          placeholder="请输入田间编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="margin:10px 0;">
      <el-col :span="1.5">
        <el-button
          class="ui-default-add"
          style="width:126px;"
          @click="handleExport"
          v-hasPermi="['os:attribute:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <div style="padding-top:2px;box-sizing: border-box;">
      <el-table v-loading="loading" :data="collectList"  border fit highlight-current-row stripe element-loading-text="Loading">
        <el-table-column label="条码信息" align="center" prop="userId" />
        <el-table-column label="2020" align="center" prop="userId" />
        <el-table-column label="2021" align="center" prop="userId" />
        <el-table-column label="2022" align="center" prop="userId" />
        <el-table-column label="总计" align="center" prop="userId" />
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
  import { listCollect, getCollect, delCollect, addCollect, updateCollect, exportCollect } from "@/api/os/collect";

export default {
  name: "AnnualEvaluation",
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
      },
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
        //this.collectList = response.rows;
        this.collectList = [{},{}]
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
    //导出的方法
    exportExcel() {
      require.ensure([], () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        let tHeader = ['序号', '昵称'];
        let array2 = []
        this.customList.forEach((item,index) => {
          tHeader.push(item.name)
          array2.push('custom'+index)
        })
        tHeader = tHeader.concat(["报名日期","状态"])
        // 上面设置Excel的表格第一行的标题
        let tableData = JSON.parse(JSON.stringify(this.tableData.data))
        let filterVal = ['index', 'userName'].concat(array2);
        filterVal = filterVal.concat(['addTime','status'])
        // 上面的index、nickName、name是tableData里对象的属性
        const data = this.formatJson(filterVal, tableData);
        export_json_to_excel(tHeader, data, '导出');
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]))
    },
    demoDownload() {
      exportMemberImporttpl({}).then(res => { // 请求接口
        let filename = res.filename.split("=")[1]
        delete res.filename
        let url = window.URL.createObjectURL(new Blob([res]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', filename)
        document.body.appendChild(link)
        link.click()
      }).catch(err => {
        alertError('导出模板失败',this)
        console.log('err', err)
      })
    },
  }
};
</script>
