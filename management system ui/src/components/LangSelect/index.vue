<template>  
  <el-dropdown trigger="click" class="international" @command="handleSetLanguage">
    <div>
      <span class="el-dropdown-link">
        	{{ languageName }}&nbsp;<svg-icon class-name="international-icon" icon-class="language" /><i class="el-icon-arrow-down el-icon--right"></i>
      </span>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :disabled="language==='zh'" command="zh">
                      中文
      </el-dropdown-item>
      <el-dropdown-item :disabled="language==='en'" command="en">
        English
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  data() {
  	return {
  		languageName: "中文"
  	}
  },
  created() {
  	if(this.$i18n.locale==='en')
    {
      	this.languageName = "English";
    }
    else
    {
        this.languageName = "中文";
    }
  },
  computed: {
    language() {
      return this.$store.getters.language
    }
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang;
      this.$store.dispatch('app/setLanguage', lang);
      if(lang==='en')
      {
      	this.languageName = "English";
      }
      else
      {
        this.languageName = "中文";
      }
      this.$message({
        message: '设置语言成功',
        type: 'success'
      })
    }
  }
}
</script>