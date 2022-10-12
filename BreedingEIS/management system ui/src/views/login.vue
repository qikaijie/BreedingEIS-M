<template>
  <div :class="this.$i18n.locale==='zh' ? 'body_zh' : 'body_en'">
	  <div>
	  	<div class="selectLang"><lang-select class="set-language" /></div>
	  </div>
	  <div class="login">
	    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
	      <h1 class="title" style="font-family: LiSu,STLiti, Microsoft YaHei, 微软雅黑, MicrosoftJhengHei,华文细黑,STHeiti,MingLiu;">{{ $t('login.title') }}</h1>
	      <h3 class="title">{{ $t('login.name') }}</h3>
	      <el-form-item prop="username">
	        <el-input v-model="loginForm.username" type="text" auto-complete="off" :placeholder="$t('login.username')">
	          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
	        </el-input>
	      </el-form-item>
	      <el-form-item prop="password">
	        <el-input
	          v-model="loginForm.password"
	          type="password"
	          auto-complete="off"
	          :placeholder="$t('login.password')"
	          @keyup.enter.native="handleLogin"
	        >
	          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
	        </el-input>
	      </el-form-item>
	      <el-form-item prop="code">
	        <el-input
	          v-model="loginForm.code"
	          auto-complete="off"
	          :placeholder="$t('login.code')"
	          style="width: 63%"
	          @keyup.enter.native="handleLogin"
	        >
	          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
	        </el-input>
	        <div class="login-code">
	          <img :src="codeUrl" @click="getCode" />
	        </div>
	      </el-form-item>
	      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">{{ $t('login.rememberMe') }}</el-checkbox>
	      <el-form-item style="width:100%;">
	        <el-button
	          :loading="loading"
	          size="medium"
	          type="primary"
	          style="width:100%;"
	          @click.native.prevent="handleLogin"
	        >
	          <span v-if="!loading">{{ $t('login.logIn') }}</span>
	          <span v-else>{{ $t('login.logIning') }}</span>
	        </el-button>
	      </el-form-item>
	      <el-form-item style="width:100%;text-align: center;color:#4DA964;">
	      	<router-link to="/introduction" target="_blank"><span>{{ $t('login.introduction') }}</span></router-link>
	      	<span style="margin:0 5px;">|</span>
	      	<router-link to="/download" target="_blank"><span>{{ $t('login.download.title') }}</span></router-link>
	      	<span style="margin:0 5px;">|</span>
	      	<router-link to="/sgeneSearch" target="_blank"><span>{{ $t('login.sgeneSearch.title') }}</span></router-link>
	      	<span style="margin:0 5px;">|</span>
	      	<router-link to="/pearSpeciesSearch" target="_blank"><span>{{ $t('login.pearSpeciesSearch.title') }}</span></router-link>
	      </el-form-item>
	    </el-form>
	    
	    <!--  底部  -->
	    <div class="el-login-footer">
	      <span>Copyright © 2020-2021 qikaijie  All Rights Reserved.</span>
	    </div>
	  </div>
	</div>
</template>

<script>
import LangSelect from '@/components/LangSelect';
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  components: { LangSelect },
  data() {
    return {
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: this.$t('login.tip.username') }
        ],
        password: [
          { required: true, trigger: "blur", message: this.$t('login.tip.password') }
        ],
        code: [{ required: true, trigger: "change", message: this.$t('login.tip.code') }]
      },
      loading: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" });
            })
            .catch(() => {
              this.loading = false;
              this.getCode();
            });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.body_zh {
  height: 100%;
  background-image: url("../assets/image/login-background.jpg");
  background-size: cover;
  display: grid;
}
.body_en {
  height: 100%;
  background-image: url("../assets/image/login-background_en.jpg");
  background-size: cover;
  display: grid;
}
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.selectLang {
  float: right;
  padding: 10px 20px;
  color: white;
  cursor: pointer;
}
.selectLang span {
  color: white;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 500px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
