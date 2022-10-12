<template>
  <div class="">
  <el-main v-loading="importLoading">
    <el-row>
      <el-form ref="form" :model="form" label-width="120px" :rules="rules">
        <el-row>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.plantbase')" prop="plantBase">
              <el-input :placeholder="$t('menu.breeding.tiaomaku.label.plantbase')" clearable v-model="form.plantBase"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.plantarea')" prop="plantArea">
              <el-input :placeholder="$t('menu.breeding.tiaomaku.label.plantarea')" clearable v-model="form.plantArea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.plantridge')" prop="plantRidge">
            <el-col :span="11"><el-input type="number" v-model="form.plantStartRidge"></el-input></el-col>
			<el-col class="line" style="text-align:center;" :span="2">-</el-col>
			<el-col :span="11"><el-input :placeholder="$t('menu.breeding.tiaomaku.label.plantridge')" type="number" v-model="form.plantRidge"></el-input></el-col>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.plantrow')" prop="plantRow">
              <el-input :placeholder="$t('menu.breeding.tiaomaku.label.plantrow')" type="number" v-model="form.plantRow"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.maxnum')" prop="maxNum">
              <el-input :placeholder="$t('menu.breeding.tiaomaku.label.maxnum')" type="number" v-model="form.maxNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.hybrid')" prop="germplasmId">
              <el-input v-model="form.germplasmName" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('menu.breeding.tiaomaku.label.fieldnumber')" prop="fieldNumber">
              <el-input :placeholder="$t('menu.breeding.tiaomaku.label.fieldnumber')" clearable v-model="form.fieldNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-button
              type="warning"
              size="medium"
              style="margin-left: 30px;"
              @click="handlePosition"
            >{{$t('menu.breeding.tiaomaku.button.generate')}}</el-button>
            <el-button
              type="primary"
              size="medium"
              style="margin-left: 30px;"
              @click="handleSubmit"
            >{{$t('common.button.submit')}}</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-row>

    <div class="ui-table" v-if="showTable">
      <table>
        <tr>
          <td :style="{width:calcWidth}"></td>
          <td :style="{width:calcWidth2}" v-for="(item,index) of Number(form.plantRidge)" v-if="(index + 1) >= Number(form.plantStartRidge)" :colspan="form.plantRow" :key="'Ridge' + index">
            第{{index + 1}}垄
          </td>
        </tr>
        <tr>
          <td :style="{width:calcWidth}"></td>
          <td :style="{width:calcWidth}" v-for="(item,index) of ((Number(form.plantRidge) - Number(form.plantStartRidge) + 1) * Number(form.plantRow))" >
            第
            <template v-if="(index + 1) % form.plantRow !== 0">
              {{(index + 1) % form.plantRow}}
            </template>
            <template v-else>
              {{form.plantRow}}
            </template>
            行
          </td>
        <tr>
          <td :style="{width:calcWidth}">株数：</td>
          <td :style="{width:calcWidth}" v-for="(item,index) of plantsTotal" >
            {{item}}
          </td>
        </tr>
        <tr v-for="(item,index) of form.tableData" :key="'row_'+index">
          <td :style="{width:calcWidth}">{{item.name}}</td>
          <td :style="{width:calcWidth}" style="cursor: pointer;" v-for="(item2,index) of item.values" :key="item2.plantRidge + '_' + item2.plantRow + '_' + item2.lineNum" @click="handleDbClick(item2)" @contextmenu.prevent="handleRightClick(item2)">
            {{item2.value}}
          </td>
        </tr>
      </table>
    </div>
  </el-main>
  </div>
</template>
<script>
import { addSeedling } from "@/api/os/germplasm/plant";
export default {
  props:["germplasmId", "germplasmCode", "germplasmName"],
  computed: {
      calcWidth:function () {
        if(this.form.plantRidge !== "" && this.form.plantRow !== "") {
            return 100/(this.form.plantRidge * this.form.plantRow + 1) + "%"
        } else {
            return 0
        }
      },
      calcWidth2:function () {
          if(this.form.plantRidge !== "" && this.form.plantRow !== "") {
              return (100/(this.form.plantRidge * this.form.plantRow + 1)) * this.form.plantRow + "%"
          } else {
              return 0
          }
      }
  },
  components: {
  },
  data() {
      const validateInteger = (rule,value,callback) => {
          if(!/^[0-9]\d*$/.test(value)) {
              return callback(new Error(this.$t('common.tips.checkint')))
          } else {
              callback()
          }
      };
    const validatePlantRidge = (rule,value,callback) => {
          if(Number(value) < Number(this.form.plantStartRidge) || Number(value) == 0) {
              return callback(new Error(this.$t('menu.breeding.tiaomaku.tips.plantridge')))
          } else {
              callback()
          }
    };
    return {
        showTable:false,
        importLoading: false,
        form: {
            plantBase: "",
            plantArea: "",
            plantStartRidge: 1,
            plantRidge: "",
            plantRow: "",
            maxNum: 100,
            germplasmId: this.germplasmId,
            sowingCode: this.germplasmCode,
            germplasmName: this.germplasmName,
            fieldNumber: this.germplasmCode,
            tableData: [],
        },
        // 表单校验
        rules: {
        	plantBase: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.plantbase'), trigger: "blur" }
            ],
            plantArea: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.plantarea'), trigger: "blur" }
            ],
            plantStartRidge: [
            	{ required: true, message: this.$t('menu.breeding.tiaomaku.tips.plantridge'), trigger: "blur" },
                { required: true, validator: validateInteger, trigger: "blur" }
            ],
            plantRidge: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.plantridge'), trigger: "blur" },
                { required: true, validator: validateInteger, trigger: "blur" },
                { required: true, validator: validatePlantRidge, trigger: "blur" }
            ],
            plantRow: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.plantrow'), trigger: "blur" },
                { required: true, validator: validateInteger, trigger: "blur" }
            ],
            maxNum: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.maxnum'), trigger: "blur" },
                { required: true, validator: validateInteger, trigger: "blur" }
            ],
            fieldNumber: [
                { required: true, message: this.$t('menu.breeding.tiaomaku.tips.fieldnumber'), trigger: "blur" }
            ]
        },
        plantsTotal:[]
    };
  },
  mounted() {
  },
  watch: {
      tableData: {
          handler(val,oldVal) {
            this.handleResetPlantTotal()
          },
          deep:true,
          immediate:true
      }
  },
  methods: {
      handlePosition() {
          this.$refs["form"].validate((valid) => {
              if (valid) {
                  this.form.tableData = [];
                  this.plantsTotal = (new Array((Number(this.form.plantRidge) - Number(this.form.plantStartRidge) + 1) * Number(this.form.plantRow))).fill(0);
                  for(let i = 0;i < this.form.maxNum;i++) {
                      let obj = {
                          name: "第" + (i + 1)+ "株"
                      };
                      let array = [];
                      for(let j = Number(this.form.plantStartRidge); j <= Number(this.form.plantRidge); j++) {
                          for(let k = 0;k < Number(this.form.plantRow);k++) {
                              let item = {
                                  plantRidge: j,//垄数
                                  plantRow: k + 1,//行数
                                  lineNum: i + 1,//第几株
                                  value: ""
                              }
                              array.push(item)
                          }
                      }
                      Object.assign(obj, {values: array})
                      this.form.tableData.push(obj)
                  }
                  this.showTable = true;
              }
          });
      },
      handleSubmit() {
      	  this.importLoading = true;
	      addSeedling(this.form).then(response => {
	      	  this.importLoading = false;
              if (response.code === 200) {
                this.$alert(
                //this.$createElement('div', { style: 'height: 300ps; overflow-y: auto;' }, response.msg ),
                response.msg,
                "导入结果", 
                { 
                	dangerouslyUseHTMLString: true,
                	center: true,
			      	confirmButtonText: '确定',
			        callback: action => {
			            this.$emit("closePage");
                		this.$emit("reload");
			        }
                });
                
              }
          });
      },
      handleDbClick(obj) {
      	  let that = this;
          let plantRidge = obj.plantRidge;
          let plantRow = obj.plantRow;
          let lineNum = obj.lineNum;
          let num = (plantRidge - this.form.plantStartRidge) * this.form.plantRow + plantRow - 1;
          let tableData = JSON.parse(JSON.stringify(this.form.tableData));
          tableData.forEach((item,index) => {
              if(index < lineNum) {
                  item.values[num].value = that.addzero(plantRidge) + that.addzero(plantRow) + "-" + (index + 1);
              } else {
                  item.values[num].value = "";
              }
          })
          this.form.tableData = tableData;
          this.handleResetPlantTotal();
      },
      handleRightClick(obj) {
          let plantRidge = obj.plantRidge;
          let plantRow = obj.plantRow;
          let lineNum = obj.lineNum;
          let num = (plantRidge - this.form.plantStartRidge) * this.form.plantRow + plantRow - 1;
          let tableData = JSON.parse(JSON.stringify(this.form.tableData));
          tableData[lineNum - 1].values[num].value = "";
          this.form.tableData = tableData;
          this.handleResetPlantTotal();
      },
      handleResetPlantTotal() {
          let plantsTotal = [...this.plantsTotal]
          plantsTotal.forEach((item,index) => {
              let total = 0;
              this.form.tableData.forEach((item2,index2) =>{
                  if(item2.values[index].value !== "" && item2.values[index].value !== null) {
                      total++;
                  }
              })
              plantsTotal[index] = total;
          })
          this.plantsTotal = plantsTotal;
      },
      addzero(obj)
      {
        if(obj<10) 
        {
        	return "0" + obj;
        }
        else 
        {
        	return obj;
        }
      }
  }
};
</script>
<style lang="scss" scoped>
  .ui-table table{width:100%;border-collapse: collapse;}
  .ui-table table thead tr{background: #EEEEEE;}
  .ui-table table thead tr td{height:35px !important;}
  .ui-table table tbody td{padding:4px 10px;box-sizing: border-box;}
  .ui-table table td{border:1px solid #ddd;text-align: center;font-size: 13px;color:#666;height:35px;}
  .ui-table table td:hover{border:2px solid #409EFF;}
  .ui-cell{display: inline-block;width:100%;height:100%;line-height:35px;cursor: pointer;}
</style>
