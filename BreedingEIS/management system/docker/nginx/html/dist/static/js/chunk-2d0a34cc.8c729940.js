(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0a34cc"],{"021e":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{ref:"queryForm",attrs:{model:e.queryParams,inline:!0,"label-width":"100px"}},[a("el-form-item",{attrs:{label:e.$t("login.pearSpeciesSearch.speciesName"),prop:"speciesName"}},[a("el-input",{attrs:{placeholder:e.$t("login.pearSpeciesSearch.speciesName"),clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.speciesName,callback:function(t){e.$set(e.queryParams,"speciesName",t)},expression:"queryParams.speciesName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:e.$t("login.pearSpeciesSearch.maleParent"),prop:"maleParent"}},[a("el-input",{attrs:{placeholder:e.$t("login.pearSpeciesSearch.maleParent"),clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.maleParent,callback:function(t){e.$set(e.queryParams,"maleParent",t)},expression:"queryParams.maleParent"}})],1),e._v(" "),a("el-form-item",{attrs:{label:e.$t("login.pearSpeciesSearch.femaleParent"),prop:"femaleParent"}},[a("el-input",{attrs:{placeholder:e.$t("login.pearSpeciesSearch.femaleParent"),clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.femaleParent,callback:function(t){e.$set(e.queryParams,"femaleParent",t)},expression:"queryParams.femaleParent"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v(e._s(e.$t("common.button.search")))]),e._v(" "),a("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v(e._s(e.$t("common.button.reset")))])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.PearSpeciesList},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.speciesName"),align:"center",prop:"speciesName"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.maleParent"),align:"center",prop:"maleParent"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.femaleParent"),align:"center",prop:"femaleParent"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.company"),align:"center",prop:"company"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.remark"),align:"center",prop:"remark"}}),e._v(" "),a("el-table-column",{attrs:{label:e.$t("login.pearSpeciesSearch.reference"),align:"center",prop:"reference"}})],1),e._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}})],1)},r=[],i=a("b775");function l(e){return Object(i["a"])({url:"/os/pearSpecies/list",method:"get",params:e})}function s(e){return Object(i["a"])({url:"/os/pearSpecies/"+e,method:"get"})}function o(e){return Object(i["a"])({url:"/os/pearSpecies",method:"post",data:e})}function c(e){return Object(i["a"])({url:"/os/pearSpecies",method:"put",data:e})}function u(e){return Object(i["a"])({url:"/os/pearSpecies/"+e,method:"delete"})}function p(e){return Object(i["a"])({url:"/os/pearSpecies/export",method:"get",params:e})}var m={name:"PearSpecies",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,total:0,PearSpeciesList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:20,speciesName:void 0,maleParent:void 0,femaleParent:void 0},form:{},rules:{}}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.loading=!0,l(this.queryParams).then((function(t){e.PearSpeciesList=t.rows,e.total=t.total,e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:void 0,speciesName:void 0,maleParent:void 0,femaleParent:void 0,company:void 0,remark:void 0,reference:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!=e.length,this.multiple=!e.length},handleAdd:function(){this.reset(),this.open=!0,this.title="添加梨物种"},handleUpdate:function(e){var t=this;this.reset();var a=e.id||this.ids;s(a).then((function(e){t.form=e.data,t.open=!0,t.title="修改梨物种"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!=e.form.id?c(e.form).then((function(t){200===t.code&&(e.msgSuccess("修改成功"),e.open=!1,e.getList())})):o(e.form).then((function(t){200===t.code&&(e.msgSuccess("新增成功"),e.open=!1,e.getList())})))}))},handleDelete:function(e){var t=this,a=e.id||this.ids;this.$confirm('是否确认删除梨物种编号为"'+a+'"的数据项?',"警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return u(a)})).then((function(){t.getList(),t.msgSuccess("删除成功")})).catch((function(){}))},handleExport:function(){var e=this,t=this.queryParams;this.$confirm("是否确认导出所有梨物种数据项?","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return p(t)})).then((function(t){e.download(t.msg)})).catch((function(){}))}}},h=m,d=a("2877"),f=Object(d["a"])(h,n,r,!1,null,null,null);t["default"]=f.exports}}]);