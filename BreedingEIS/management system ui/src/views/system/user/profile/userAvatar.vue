<template>
  <div>
    <img v-bind:src="options.img" @click="editCropper()" :title="$t('menu.user.avatar.tips')" class="img-circle img-lg" />
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              {{ $t('menu.user.avatar.upload') }}
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">{{ $t('common.button.submit') }}</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import store from "@/store";
import { VueCropper } from "vue-cropper";
import { uploadAvatar } from "@/api/system/user";

export default {
  components: { VueCropper },
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // ?????????????????????
      open: false,
      // ???????????????
      title: this.$t('menu.user.avatar.title'),
      options: {
        img: store.getters.avatar, //?????????????????????
        autoCrop: true, // ???????????????????????????
        autoCropWidth: 200, // ???????????????????????????
        autoCropHeight: 200, // ???????????????????????????
        fixedBox: true // ????????????????????? ???????????????
      },
      previews: {}
    };
  },
  methods: {
    // ????????????
    editCropper() {
      this.open = true;
    },
    // ???????????????????????????
    requestUpload() {
    },
    // ????????????
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // ????????????
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // ????????????
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // ???????????????
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("??????????????????????????????????????????,??????JPG???PNG??????????????????");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    // ????????????
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        let formData = new FormData();
        formData.append("avatarfile", data);
        uploadAvatar(formData).then(response => {
          if (response.code === 200) {
            this.open = false;
            this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
            this.msgSuccess("????????????");
          }
          this.$refs.cropper.clearCrop();
        });
      });
    },
    // ????????????
    realTime(data) {
      this.previews = data;
    }
  }
};
</script>
