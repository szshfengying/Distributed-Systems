<!--  -->
<template>
  <div class="Transfer">
        <el-main>
          <el-form ref="getform" :model="getform" label-width="80px">
            <el-form-item label="付款户名">
              <el-input :disabled="true" v-model="pay_name"></el-input>
            </el-form-item>
            <el-form-item label="付款帐号">
              <el-input :disabled="true" v-model="pay_id"></el-input>
            </el-form-item>
            <el-form-item label="收款户名">
              <el-input v-model="getform.get_name"></el-input>
            </el-form-item>
            <el-form-item label="收款帐号">
              <el-input v-model="getform.get_id"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="getform.currtype"></el-input>
            </el-form-item>
            <el-form-item label="转账金额">
              <el-input v-model="getform.num"></el-input>
            </el-form-item>
            <el-button type="primary" @click="Transfer()">转账</el-button>
          </el-form>
        </el-main>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import global from '@/api/global'
export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    return {
      name: global.name,
      time:"",
      pay_name: global.name,
      pay_id: global.accid,
      getform: {
        get_name: "",
        get_id: "",
        currtype:"",
        num: "",
      },
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    addDate() {
      var date = new Date();
		  var seperator1 = "-";
		  var year = date.getFullYear();
		  var month = date.getMonth() + 1;
		  var strDate = date.getDate();
		  if (month >= 1 && month <= 9) {
		    	month = "0" + month;
		  }
		  if (strDate >= 0 && strDate <= 9) {
		      strDate = "0" + strDate;
		  }
		  this.time= year + "-" + month + "-" + strDate;
    },
    Transfer(){
       axios({
        method: "post",
        url: "http://127.0.0.1:25008/transfer/transfer/transfer",
        headers: {
   /*         'Content-type': 'application/x-www-form-urlencoded', */
          "Content-Type": "application/json", 
        },
        data:JSON.stringify( 
        {
          "currType":global.currtype,
          "execOrganno":"123",
          "execTellerno":"123",
           "accIdFrom":this.pay_id,
           "accTitleFrom":this.pay_name,
           "accIdTo":this.getform.get_id,
           "accTitleTo":this.getform.get_name,
           "amount":this.getform.num,
           "execDate":this.time
        }), 
      })
      .then((response) => {
        if (response.data.msg === "success") {
          this.$alert(response.data.转账情况, "转账成功", {
            confirmButtonText: "确定",
            callback: (action) => {
              this.$router.replace({ name: "Transfer" });
            },
          });
        } else {
          this.$message.error(data.msg);
        }
      }); 
    },
    getcurr() {
      if(global.currtype==1)
        this.getform.currtype="人民币";
      else
        this.getform.currtype="其他币种";
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getcurr(),
    this.addDate() 
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style>
</style>