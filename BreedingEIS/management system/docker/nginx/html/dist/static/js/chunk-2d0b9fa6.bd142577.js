(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0b9fa6"],{"34f5":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("el-form",{ref:"queryForm",attrs:{model:e.queryParams,inline:!0,"label-width":"100px"}},[n("el-form-item",{attrs:{label:e.$t("login.sgeneSearch.speciesName"),prop:"speciesName"}},[n("el-input",{attrs:{placeholder:e.$t("login.sgeneSearch.speciesName"),clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.speciesName,callback:function(t){e.$set(e.queryParams,"speciesName",t)},expression:"queryParams.speciesName"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("login.sgeneSearch.speciesEnName"),prop:"speciesName2"}},[n("el-input",{attrs:{placeholder:e.$t("login.sgeneSearch.speciesEnName"),clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.speciesName2,callback:function(t){e.$set(e.queryParams,"speciesName2",t)},expression:"queryParams.speciesName2"}})],1),e._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v(e._s(e.$t("common.button.search")))]),e._v(" "),n("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v(e._s(e.$t("common.button.reset")))])],1)],1),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.sgeneList},on:{"selection-change":e.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.speciesName"),align:"center",prop:"speciesName"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.speciesEnName"),align:"center",prop:"speciesName2"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.s1"),align:"center",prop:"s1"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.s2"),align:"center",prop:"s2"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.s3"),align:"center",prop:"s3"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.s4"),align:"center",prop:"s4"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.sGene"),align:"center",prop:"sGene"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.bloomStartDate"),align:"center",prop:"bloomStartDate"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.bloomEndDate"),align:"center",prop:"bloomEndDate"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.pollenAbortion"),align:"center",prop:"pollenAbortion"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.recommended"),align:"center",prop:"recommended"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.source"),align:"center",prop:"source"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.area"),align:"center",prop:"area"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("login.sgeneSearch.updateDate"),align:"center",prop:"updateDate"}})],1),e._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}})],1)},s=[],i=n("b775");function o(e){return Object(i["a"])({url:"/os/sgene/list",method:"get",params:e})}function r(e){return Object(i["a"])({url:"/os/sgene/"+e,method:"get"})}function l(e){return Object(i["a"])({url:"/os/sgene",method:"post",data:e})}function c(e){return Object(i["a"])({url:"/os/sgene",method:"put",data:e})}function u(e){return Object(i["a"])({url:"/os/sgene/"+e,method:"delete"})}function m(e){return Object(i["a"])({url:"/os/sgene/export",method:"get",params:e})}var p={name:"Sgene",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,total:0,sgeneList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:20,speciesName:void 0,speciesName2:void 0},form:{},rules:{}}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.loading=!0,o(this.queryParams).then((function(t){e.sgeneList=t.rows,e.total=t.total,e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:void 0,speciesName:void 0,speciesName2:void 0,s1:void 0,s2:void 0,s3:void 0,s4:void 0,sGene:void 0,bloomStartDate:void 0,bloomEndDate:void 0,pollenAbortion:void 0,recommended:void 0,source:void 0,area:void 0,updateDate:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!=e.length,this.multiple=!e.length},handleAdd:function(){this.reset(),this.open=!0,this.title="添加sgene"},handleUpdate:function(e){var t=this;this.reset();var n=e.id||this.ids;r(n).then((function(e){t.form=e.data,t.open=!0,t.title="修改sgene"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!=e.form.id?c(e.form).then((function(t){200===t.code&&(e.msgSuccess("修改成功"),e.open=!1,e.getList())})):l(e.form).then((function(t){200===t.code&&(e.msgSuccess("新增成功"),e.open=!1,e.getList())})))}))},handleDelete:function(e){var t=this,n=e.id||this.ids;this.$confirm('是否确认删除sgene编号为"'+n+'"的数据项?',"警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return u(n)})).then((function(){t.getList(),t.msgSuccess("删除成功")})).catch((function(){}))},handleExport:function(){var e=this,t=this.queryParams;this.$confirm("是否确认导出所有sgene数据项?","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return m(t)})).then((function(t){e.download(t.msg)})).catch((function(){}))}}},g=p,d=n("2877"),h=Object(d["a"])(g,a,s,!1,null,null,null);t["default"]=h.exports}}]);