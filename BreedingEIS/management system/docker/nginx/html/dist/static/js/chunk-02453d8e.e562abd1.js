(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-02453d8e"],{3177:function(t,e,n){"use strict";var o=n("e09d"),a=n.n(o);a.a},"4e80":function(t,e,n){"use strict";n.d(e,"h",(function(){return a})),n.d(e,"g",(function(){return i})),n.d(e,"e",(function(){return r})),n.d(e,"f",(function(){return s})),n.d(e,"a",(function(){return l})),n.d(e,"i",(function(){return c})),n.d(e,"b",(function(){return u})),n.d(e,"c",(function(){return d})),n.d(e,"d",(function(){return m}));var o=n("b775");function a(t){return Object(o["a"])({url:"/os/plant/list",method:"get",params:t})}function i(t){return Object(o["a"])({url:"/os/plant/listByHybridId",method:"get",params:t})}function r(t){return Object(o["a"])({url:"/os/plant/"+t,method:"get"})}function s(t,e){return Object(o["a"])({url:"/os/plant/getQRCode/"+t+"?lang="+e,method:"get"})}function l(t){return Object(o["a"])({url:"/os/plant/autoGenerate",method:"post",data:t})}function c(t){return Object(o["a"])({url:"/os/plant/update",method:"put",data:t})}function u(t){return Object(o["a"])({url:"/os/plant/delList",method:"get",params:t})}function d(t){return Object(o["a"])({url:"/os/plant/del/"+t,method:"get"})}function m(t){return Object(o["a"])({url:"/os/plant/export",method:"get",params:t})}},"5da2":function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("el-row",{staticClass:"mb8",attrs:{gutter:10}},[n("el-col",{attrs:{span:1.5}},[n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:add"],expression:"['os:plant:add']"}],attrs:{type:"primary",icon:"el-icon-plus",size:"mini"},on:{click:t.handleAdd}},[t._v(t._s(t.$t("common.button.add")))])],1),t._v(" "),n("el-col",{attrs:{span:1.5}},[n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:export"],expression:"['os:plant:export']"}],attrs:{type:"warning",icon:"el-icon-download",size:"mini"},on:{click:t.handleExportQRcode}},[t._v(t._s(t.$t("menu.breeding.tiaomaku.button.exporterweima")))])],1),t._v(" "),n("el-col",{attrs:{span:1.5}},[n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:export"],expression:"['os:plant:export']"}],attrs:{type:"warning",icon:"el-icon-download",size:"mini"},on:{click:t.handleExport}},[t._v(t._s(t.$t("common.button.export")))])],1),t._v(" "),n("el-col",{attrs:{span:1.5}},[n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:remove"],expression:"['os:plant:remove']"}],attrs:{type:"danger",icon:"el-icon-delete",size:"mini"},on:{click:t.handleDeleteList}},[t._v(t._s(t.$t("common.button.delete")))])],1)],1),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],attrs:{data:t.plantList,border:"",fit:"","highlight-current-row":"",stripe:"","element-loading-text":"Loading"}},[n("el-table-column",{attrs:{label:t.$t("menu.breeding.tiaomaku.label.tiaoma"),align:"center",prop:"code"}}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("menu.breeding.tiaomaku.label.changma"),align:"center",prop:"codeOne",width:"150"}}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("menu.breeding.tiaomaku.label.duanma"),align:"center",prop:"codeTwo",width:"150"}}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("menu.breeding.tiaomaku.label.createtime"),align:"center",prop:"createTime",width:"150"}}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("common.label.operation"),align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:export"],expression:"['os:plant:export']"}],attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(n){return t.getQRCode(e.row)}}},[t._v(t._s(t.$t("menu.breeding.tiaomaku.button.createerweima")))]),t._v(" "),n("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["os:plant:remove"],expression:"['os:plant:remove']"}],attrs:{size:"mini",type:"text",icon:"el-icon-delete"},on:{click:function(n){return t.handleDelete(e.row)}}},[t._v(t._s(t.$t("common.button.delete")))])]}}])})],1),t._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.queryParams.pageNum,limit:t.queryParams.pageSize},on:{"update:page":function(e){return t.$set(t.queryParams,"pageNum",e)},"update:limit":function(e){return t.$set(t.queryParams,"pageSize",e)},pagination:t.getList}}),t._v(" "),n("el-dialog",{attrs:{title:t.title,visible:t.open,width:"500px","append-to-body":""},on:{"update:visible":function(e){t.open=e}}},[n("el-form",{ref:"form",attrs:{model:t.form,rules:t.rules,"label-width":"80px"}},[n("el-form-item",{attrs:{label:"父植物",prop:"code"}},[n("el-input",{attrs:{placeholder:"",disabled:!0},model:{value:t.form.code,callback:function(e){t.$set(t.form,"code",e)},expression:"form.code"}})],1),t._v(" "),n("el-form-item",{attrs:{label:"高接数量",prop:"number"}},[n("el-input",{attrs:{placeholder:"请输入添加的高接数量"},model:{value:t.form.number,callback:function(e){t.$set(t.form,"number",e)},expression:"form.number"}})],1)],1),t._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:t.submitForm}},[t._v("确 定")]),t._v(" "),n("el-button",{on:{click:t.cancel}},[t._v("取 消")])],1)],1),t._v(" "),n("el-dialog",{attrs:{title:t.imgTitle,visible:t.imgOpen,width:"500px","append-to-body":""},on:{"update:visible":function(e){t.imgOpen=e}}},[n("div",{staticStyle:{"text-align":"center"}},[n("img",{directives:[{name:"loading",rawName:"v-loading",value:t.imgLoading,expression:"imgLoading"}],staticClass:"ui-plant-img",attrs:{src:t.qrCode,placeholder:"图片加载中"},on:{load:t.loadSuccess}}),t._v(" "),n("div",[n("a",{staticClass:"qrcode",staticStyle:{color:"#4DA964",cursor:"pointer"},attrs:{href:t.qrCode,download:t.qrCodeName}},[t._v(t._s(t.$t("menu.breeding.tiaomaku.button.download")))])])])])],1)},a=[],i=n("4e80"),r=n("b775");function s(t){return Object(r["a"])({url:"/os/gaojiePlant/add",method:"post",data:t})}var l=n("ca3f"),c={name:"Plant",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,total:0,plantList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:10,parentId:this.$route.params&&this.$route.params.codeId},form:{},rules:{},imgTitle:"",imgOpen:!1,imgLoading:!0,qrCode:"",qrCodeName:""}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.loading=!0,Object(i["h"])(this.queryParams).then((function(e){t.plantList=e.rows,t.total=e.total,t.loading=!1}))},getQRCode:function(t){var e=this;Object(i["f"])(t.id).then((function(n){200==n.code?(e.qrCode=n.data,e.qrCodeName=t.code+".png",e.imgOpen=!0,e.imgTitle=e.$t("menu.breeding.tiaomaku.title.viewerweima")):e.msgError(n.msg)}))},loadSuccess:function(){this.imgLoading=!1},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={parentId:void 0,code:void 0,number:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(t){this.ids=t.map((function(t){return t.id})),this.single=1!=t.length,this.multiple=!t.length},handleAdd:function(){var t=this;this.reset(),Object(i["e"])(this.queryParams.parentId).then((function(e){t.form.parentId=e.data.id,t.form.code=e.data.code,t.open=!0,t.title="添加高接植物"}))},submitForm:function(){var t=this;this.$refs["form"].validate((function(e){e&&s(t.form).then((function(e){200===e.code&&(t.msgSuccess("新增成功"),t.open=!1,t.getList())}))}))},handleDeleteList:function(t){var e=this,n=this.queryParams;this.$confirm(this.$t("menu.breeding.tiaomaku.tips.deleteconfirm"),this.$t("common.title.warning"),{confirmButtonText:this.$t("common.button.ok"),cancelButtonText:this.$t("common.button.cancle"),type:"warning"}).then((function(){return Object(i["b"])(n)})).then((function(t){e.getList(),e.msgSuccess(e.$t("common.tips.success"))})).catch((function(){}))},handleDelete:function(t){var e=this,n=t.id||this.ids;this.$confirm('是否确认删除植物编号为"'+n+'"的数据项?',"警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return Object(i["c"])(n)})).then((function(){e.getList(),e.msgSuccess("删除成功")})).catch((function(){}))},handleExport:function(){var t=this,e=this.queryParams;this.$confirm("是否确认导出所有植物数据项?","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return Object(i["d"])(e)})).then((function(e){t.download(e.msg)})).catch((function(){}))},handleExportQRcode:function(){var t=this.queryParams;this.$confirm(this.$t("menu.breeding.tiaomaku.tips.exporterweimaconfirm"),this.$t("common.title.warning"),{confirmButtonText:this.$t("common.button.ok"),cancelButtonText:this.$t("common.button.cancle"),type:"warning"}).then((function(){Object(l["a"])("/os/plant/exportQRcode?parentId="+t.parentId,"liuxn")})).catch((function(){}))}}},u=c,d=(n("3177"),n("2877")),m=Object(d["a"])(u,o,a,!1,null,"38b4c388",null);e["default"]=m.exports},ca3f:function(t,e,n){"use strict";n.d(e,"a",(function(){return l}));n("a481"),n("3b2b");var o=n("bc3a"),a=n.n(o),i=n("5f87"),r={xlsx:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",zip:"application/zip"},s="http://47.100.201.80:8081/yuzhong";function l(t,e){var n=s+t;a()({method:"get",url:n,responseType:"blob",headers:{Authorization:"Bearer "+Object(i["a"])()}}).then((function(t){c(t,r.zip)}))}function c(t,e){var n=document.createElement("a"),o=new Blob([t.data],{type:e}),a=new RegExp("filename=([^;]+\\.[^\\.;]+);*"),i=decodeURI(t.headers["content-disposition"]),r=a.exec(i),s=r[1];s=s.replace(/\"/g,""),n.href=URL.createObjectURL(o),n.setAttribute("download",s),document.body.appendChild(n),n.click(),document.body.appendChild(n)}},e09d:function(t,e,n){}}]);