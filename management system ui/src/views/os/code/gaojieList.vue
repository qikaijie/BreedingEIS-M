<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['os:plant:add']"
        >{{ $t('common.button.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExportQRcode"
          v-hasPermi="['os:plant:export']"
        >{{ $t('menu.breeding.tiaomaku.button.exporterweima') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:plant:export']"
        >{{ $t('common.button.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDeleteList"
          v-hasPermi="['os:plant:remove']"
        >{{ $t('common.button.delete') }}</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="plantList" border fit highlight-current-row stripe element-loading-text="Loading">
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.tiaoma')" align="center" prop="code"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.changma')" align="center" prop="codeOne" width="150"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.duanma')" align="center" prop="codeTwo" width="150"/>
      <el-table-column :label="$t('menu.breeding.tiaomaku.label.createtime')" align="center" prop="createTime" width="150"/>
      <el-table-column :label="$t('common.label.operation')" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getQRCode(scope.row)"
            v-hasPermi="['os:plant:export']"
          >{{ $t('menu.breeding.tiaomaku.button.createerweima') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:plant:remove']"
          >{{ $t('common.button.delete') }}</el-button>
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

    <!-- 添加或修改植物对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父植物" prop="code">
          <el-input v-model="form.code" placeholder="" :disabled="true"/>
        </el-form-item>
        <el-form-item label="高接数量" prop="number">
          <el-input v-model="form.number" placeholder="请输入添加的高接数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    
    <el-dialog :title="imgTitle" :visible.sync="imgOpen" width="500px" append-to-body>
      <div style="text-align: center;">
      	<img :src="qrCode" placeholder="图片加载中" class="ui-plant-img" v-loading="imgLoading" @load="loadSuccess" />
      	<div>
      		<a :href="qrCode" :download="qrCodeName" class="qrcode" style="color:#4DA964;cursor: pointer;">{{ $t('menu.breeding.tiaomaku.button.download') }}</a>
      	</div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPlant, getPlant, getQRCode, delListPlant, delPlant, addPlant, updatePlant, exportPlant } from "@/api/os/plant";
import { addGaojiePlant } from "@/api/os/gaojiePlant";
import { downLoadZip } from "@/utils/zipdownload";
export default {
  name: "Plant",
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
      // 植物表格数据
      plantList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId:  this.$route.params && this.$route.params.codeId,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      imgTitle: "",
      imgOpen: false,
      imgLoading: true,
      qrCode: "",
      qrCodeName: "",
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询植物列表 */
    getList() {
      this.loading = true;
      listPlant(this.queryParams).then(response => {
        this.plantList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取图片列表
    getQRCode(row) {
      getQRCode(row.id).then(response => {
        if(response.code == 200) {
          this.qrCode = response.data;
          this.qrCodeName = row.code + ".png"
          this.imgOpen = true;
          this.imgTitle = this.$t('menu.breeding.tiaomaku.title.viewerweima');
        } else {
          this.msgError(response.msg)
        }
      });
    },
    loadSuccess() {
      this.imgLoading = false
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        parentId: undefined,
        code: undefined,
        number: undefined,
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
      getPlant(this.queryParams.parentId).then(response => {
        this.form.parentId = response.data.id;
        this.form.code = response.data.code;
        this.open = true;
        this.title = "添加高接植物";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            addGaojiePlant(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
        }
      });
    },
       /** 删除按钮操作 */
    handleDeleteList(row) {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.deleteconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return delListPlant(queryParams);
        }).then(response => {
          this.getList();
          this.msgSuccess(this.$t('common.tips.success'));
        }).catch(function() {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除植物编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPlant(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有植物数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPlant(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导出二维码按钮操作 */
    handleExportQRcode() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.breeding.tiaomaku.tips.exporterweimaconfirm'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          downLoadZip("/os/plant/exportQRcode?parentId=" + queryParams.parentId, "liuxn");
        }).catch(function() {});
    }
  }
};
</script>
<style scoped lang="scss">
	.line {
		text-align:center;
	}
</style>