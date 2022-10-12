<template>
	<div>
		<div id="icon">
			<img class="icon" src="../assets/image/iphone.png"/>
		</div>
		<div id="appName">{{ $t('login.name') }}</div>
		<div id="buttons">
			<a href="http://47.100.201.80:8081/yuzhong/common/download/android">{{ $t('login.download.android') }}</a>
			<a href="https://apps.apple.com/cn/app/云上后稷/id1570277391">{{ $t('login.download.ios') }}</a>
		</div>
	</div>
</template>
<script>
export default  {
  name:  'App',
  created() {
    this.onload();
  },
  methods: {
  	onload() {
	  	var u = navigator.userAgent;
		var app = navigator.appVersion;
	
		var isAndroid = u.match(/(Android);?[\s\/]+([\d.]+)?/); //android终端
		var isiOS = u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	
		var urls = {
			'android':'http://47.100.201.80:8081/yuzhong/common/download/android',
			'ios':'https://apps.apple.com/cn/app/云上后稷/id1570277391'
		};
	
		if(isAndroid) {
			//这个是安卓操作系统
			window.location.href = urls.android; //android 下载地址
			return;
		}
		if(isiOS) {
			//这个是ios操作系统
			window.location.href = urls.ios; //ios 下载地址
			return;
		}
  	}
  }
}
</script>
<style lang="scss" scoped>
* {
	margin: 0px;
	padding: 0px;
	font-size: 14px;
}

a {
	text-decoration: none;
}

#icon {
	text-align: center;
	/*让div内部文字居中*/
	background-color: #fff;
	width: 400px;
	height: 450px;
	margin: auto;
	position: relative;
	left: 0;
	right: 0;
	bottom: 0;
}

.icon{
	width: 400px;
	height: 450px;
}

#appName {
	margin: 5px auto;
	width: 100%;
	position: absolute;
	text-align: center;
}

#buttons {
	margin: 40px 20px;
	position: relative;
	height: auto;
}

#buttons a {
	background-color: #249cd3;
	box-shadow: 0 0 2px;
	padding: 16px;
	display: block;
	text-align: center;
	margin: 5px auto;
	border-radius: 16px;
	color: white;
}
</style>