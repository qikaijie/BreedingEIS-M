<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item :label="$t('menu.feedback.label.content')" prop="content">
        <el-input
          v-model="queryParams.content"
          :placeholder="$t('menu.feedback.label.content')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('menu.feedback.label.type')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('menu.feedback.label.type')" clearable size="small">
          <el-option :label="$t('common.label.pending')" value="0" />
          <el-option :label="$t('common.label.processed')" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{$t('common.button.search')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{$t('common.button.reset')}}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['os:feedback:add']"
        >{{$t('common.button.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['os:feedback:edit']"
        >{{$t('common.button.edit')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['os:feedback:remove']"
        >{{$t('common.button.delete')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:feedback:export']"
        >{{$t('common.button.export')}}</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="feedbackList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('menu.feedback.label.creater')" min-width="10%" align="center" prop="username" />
      <el-table-column :label="$t('menu.feedback.label.channel')" min-width="10%" align="center" prop="channel" :formatter="channelFormat" />
      <el-table-column :label="$t('menu.feedback.label.content')" min-width="30%" align="center" prop="content" />
      <el-table-column :label="$t('menu.feedback.label.reply')" min-width="20%" align="center" prop="reply" />
      <el-table-column :label="$t('menu.feedback.label.type')" min-width="10%" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column :label="$t('common.label.operation')" min-width="20%" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:feedback:edit']"
            v-if="scope.row.status == 0"
          >{{$t('menu.feedback.button.handle')}}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:feedback:remove']"
          >{{$t('common.button.delete')}}</el-button>
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

    <!-- ???????????????????????????????????? -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('menu.feedback.label.creater')" prop="username">
          <el-input :disabled="feedback_username" v-model="form.username" :placeholder="$t('menu.feedback.label.creater')" />
        </el-form-item>
        <el-form-item :label="$t('menu.feedback.label.content')" prop="content">
          <el-input :disabled="feedback_content" v-model="form.content" type="textarea" :placeholder="$t('menu.feedback.label.content')" />
        </el-form-item>
        <el-form-item v-show="feedback_reply" :label="$t('menu.feedback.label.reply')" prop="reply">
          <el-input v-model="form.reply" type="textarea" :placeholder="$t('menu.feedback.label.reply')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{$t('common.button.ok')}}</el-button>
        <el-button @click="cancel">{{$t('common.button.cancle')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFeedback, getFeedback, delFeedback, addFeedback, updateFeedback, exportFeedback } from "@/api/os/feedback";

export default {
  name: "Feedback",
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
      // ????????????????????????
      feedbackList: [],
      // ???????????????
      title: "??????",
      // ?????????????????????
      open: false,
      feedback_content: false,
      feedback_username: false,
      feedback_reply:false,
      // ????????????
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        content: undefined,
        status: undefined,
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
    /** ???????????????????????? */
    getList() {
      this.loading = true;
      listFeedback(this.queryParams).then(response => {
        this.feedbackList = response.rows;
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
        content: undefined,
        reply: undefined,
        status: "0"
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
      this.title = this.$t('menu.feedback.title.add');
      this.form.username = this.$store.state.user.name;
      this.feedback_username = false;
      this.feedback_content = false;
      this.feedback_reply = false;
    },
    /** ?????????????????? */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFeedback(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('menu.feedback.title.feedback');
        this.feedback_username = true;
      	this.feedback_content = true;
      	this.feedback_reply = true;
      });
    },
    /** ???????????? */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateFeedback(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
                this.open = false;
                this.getList();
              }
            });
          } else {
            addFeedback(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess(this.$t('common.tips.success'));
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
      this.$confirm('???????????????????????????????????????"' + ids + '"?????????????', "??????", {
          confirmButtonText: "??????",
          cancelButtonText: "??????",
          type: "warning"
        }).then(function() {
          return delFeedback(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("????????????");
        }).catch(function() {});
    },
    /** ?????????????????? */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm(this.$t('menu.feedback.tips.export'), this.$t('common.title.warning'), {
          confirmButtonText: this.$t('common.button.ok'),
          cancelButtonText: this.$t('common.button.cancle'),
          type: "warning"
        }).then(function() {
          return exportFeedback(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    statusFormat(row, column) {
	  	if(row.status == 0)
	  	{
	  		return this.$t('common.label.pending');
	  	}
		else
		{
			return this.$t('common.label.processed');
		}
	},
	channelFormat(row, column) {
	  	if(row.channel == 0)
	  	{
	  		return "web";
	  	}
		else if(row.channel == 1)
		{
			return "Android";
		}
		else if(row.channel == 2)
		{
			return "ios";
		}
	}
  }
};
</script>