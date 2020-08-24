<template>
  <div class="Balance">

        <el-main>
          <el-form ref="balform" :model="balform" label-width="80px">
            <el-form-item label="余额">
              <el-input :disabled="true" v-model="balform.balance"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="balform.currtype"></el-input>
            </el-form-item>
          </el-form>
        </el-main>

  </div>
</template>

<script>
import global from '@/api/global'
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    return {
      time:"",
      balform:{
      balance: "",
      currtype: "",
      }
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
    getbal(){
              axios({
        method: 'post',
        // url: 'http://127.0.0.1:25008/info/query/info',
        url: 'http://127.0.0.1:25008/accdetails/query/detail',
        headers: {
          "Content-Type": "application/json", 
        },
        data:JSON.stringify( 
        {
          'currType':"1",
          'execOrganno':"123",
          "execTellerno":"123",
          "txnCode":"1",
          'accId':global.accid,
          "txnDate":this.time
        }), 
      })
        .then((response) => {
          this.name=global.name
            this.balform.balance=(JSON.parse(response.data.账号信息)).balance
              if(global.currtype=='0'||global.currtype==0)
              this.balform.currtype="人民币";
            else
              this.balform.currtype="其他币种";
        })
           .catch(function (error) {
             this.$alert(response.data.msg, "当前网页环境不安全，请重新登录", {
                  confirmButtonText: "确定",
                  callback: (action) => {
                      this.$router.push({ path: "/" });
                  },
                });
          console.log(error);
        });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.addDate() 
    this.getbal()
  },
 
};
</script>

<style>

</style>