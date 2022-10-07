<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="140px">
    <el-form-item :label="$t('menu.user.password.oldpass')" prop="oldPassword">
      <el-input v-model="user.oldPassword" :placeholder="$t('menu.user.password.oldpass')" type="password" />
    </el-form-item>
    <el-form-item :label="$t('menu.user.password.newpass')" prop="newPassword">
      <el-input v-model="user.newPassword" :placeholder="$t('menu.user.password.newpass')" type="password" />
    </el-form-item>
    <el-form-item :label="$t('menu.user.password.twopass')" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" :placeholder="$t('menu.user.password.twopass')" type="password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('common.button.submit') }}</el-button>
      <el-button type="danger" size="mini" @click="close">{{ $t('common.button.cancle') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    //校验密码
    let checkPassword = (rule, value, callback) => {
      if (value) {
        if(/^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{8,18}$/.test(value)) {
          callback();
        } else {
          callback(new Error("密码必须由字母、数字组成，区分大小写，长度8-18位"));
        }
      } else {
        callback(new Error("用户密码不能为空"));
      }
    };
    return {
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: this.$t('menu.user.password.tips.oldpass'), trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: this.$t('menu.user.password.tips.newpass'), trigger: "blur" },
          { validator: checkPassword, trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: this.$t('menu.user.password.tips.twopass'), trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" },
          { validator: checkPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess("Modification succeeded");
              }
            }
          );
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
