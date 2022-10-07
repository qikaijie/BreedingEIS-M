<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="140px">
    <el-form-item :label="$t('menu.user.nickname')" prop="nickName">
      <el-input v-model="user.nickName" />
    </el-form-item>
    <el-form-item :label="$t('menu.user.phone')" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item :label="$t('menu.user.email')" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item :label="$t('menu.user.sex')">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">Male</el-radio>
        <el-radio label="1">Female</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item :label="$t('menu.user.spaces')">
      <el-select v-model="user.speciesId" placeholder="">
        <el-option
          v-for="item in speciesList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('common.button.submit') }}</el-button>
      <el-button type="danger" size="mini" @click="close">{{ $t('common.button.cancle') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";
import { listSpeciesTreeselect } from "@/api/os/attribute/group";
export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      //物种
      speciesList: [],
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱地址不能为空", trigger: "blur" },
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getSpeciesList();
  },
  methods: {
    /** 查询物种列表 */
    getSpeciesList() {
      listSpeciesTreeselect().then(response => {
        this.speciesList = response.data;
      });
    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功");
            }
          });
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>
