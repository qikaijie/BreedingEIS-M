<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="资源名称" prop="femaleParentSource">
        <el-input
          v-model="queryParams.femaleParentSource"
          placeholder="请输入资源名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          @click="handleAdd"
          v-hasPermi="['os:attribute:export']"
        >新增资源评价</el-button>
      </el-col>
    </el-row>

    <div style="padding-top:2px;box-sizing: border-box;">
      <el-table v-loading="loading" :data="collectList"  border fit highlight-current-row stripe element-loading-text="Loading">
        <el-table-column label="田间编码" align="center" prop="userId" />
        <el-table-column label="品种名称" align="center" prop="plantId" />
        <el-table-column label="其它名（俗名）" align="center" prop="plantId" />
        <el-table-column label="来源" align="center" prop="plantId" />
        <el-table-column label="是否获品种保护" align="center" prop="plantId" />
        <el-table-column label="保存类型" align="center" prop="plantId" />
        <el-table-column label="数量" align="center" prop="plantId" />
        <el-table-column label="保存方式" align="center" prop="plantId" />
        <el-table-column label="查看条码" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              style="color:#4DA964;font-size: 14px;"
              @click="handlePreView"
            >查看</el-button>
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
    <!--<el-image-viewer
      v-if="showViewer"
      :on-close="closeViewer"
      :url-list="url" />-->
    <viewer :images="[]">
    </viewer>

    <!-- 添加或修改属性池对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <div style="padding:0 54px 0 20px;box-sizing: border-box;">
        <el-form ref="form" :model="form" :rules="rules" label-width="140px">
          <el-form-item label="田间编码：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="品种名称：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="其它名（俗名）：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="来源：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="是否获品种保护：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="保存类型：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="数量：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="保存方式：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="保存圃库名称：" prop="fieldCode">
            <el-input v-model="form.fieldCode" placeholder="单行输入" />
          </el-form-item>
          <el-form-item label="品种特性描述：" prop="fieldCode">
            <el-input v-model="form.fieldCode" type="textarea" placeholder="100字以内" maxlength="100" show-word-limit/>
          </el-form-item>
          <el-form-item label="应用情况概述：" prop="fieldName">
            <el-input v-model="form.fieldName" type="textarea" placeholder="100字以内" maxlength="100" show-word-limit/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="text-align:center;">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listCollect, getCollect, delCollect, addCollect, updateCollect, exportCollect } from "@/api/os/collect";

export default {
  name: "Resources",
  data() {
    return {
      showViewer:false,
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
      url: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ]
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增资源评价";
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
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateAttribute(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAttribute(this.form).then(response => {
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
    handlePreView() {
      this.showViewer = true
      this.$viewerApi({
        images: this.url,
      })
    },
    // 关闭查看器
    closeViewer() {
      this.showViewer = false
    }
  }
};
</script>
