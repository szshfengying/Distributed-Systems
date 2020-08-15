<!--  -->
<template>
 
  <div class="Info">

        <el-main>
          <el-form ref="form" :model="infoform" label-width="80px">
            <el-form-item label="卡号">
              <el-input :disabled="true" v-model="accid"></el-input>
            </el-form-item>
            <el-form-item label="地址">
              <el-input :disabled="true" v-model="infoform.addr"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="infoform.currtype"></el-input>
            </el-form-item>
          </el-form>
        </el-main>

  </div>
</template>

<script>
import global from '@/api/global'
export default {
  data() {
    return {
      time:"",
      name: "",
      accid:global.accid,
      infoform:{
      addr:"" ,
      currtype: "",
      }
    };
  },
  created(){
    this.addDate()
    this.getinfo()
  },
  methods:{
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
      getinfo() {
      axios({
        method: 'post',
        url: 'http://127.0.0.1:25008/info/query/info',
        headers: {
   /*         'Content-type': 'application/x-www-form-urlencoded', */
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
            this.accid=global.accid
            global.name=(JSON.parse(response.data.账号信息)).accTitle
            global.currtype=(JSON.parse(response.data.账号信息)).currType
            this.name=global.name
            this.infoform.addr=(JSON.parse(response.data.账号信息)).regionId
            if(global.currtype==1)
              this.infoform.currtype="人民币";
            else
              this.infoform.currtype="其他币种";
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  }
};
// //这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// //例如：import 《组件名称》 from '《组件路径》';

// export default {
//   //import引入的组件需要注入到对象中才能使用
//   components: {},
//   data() {
//     return {
//       form: { name: "", accid: "", addr: "", currtype: "人民币" },
//     };
//   },
//   //监听属性 类似于data概念
//   computed: {},
//   //监控data中的数据变化
//   watch: {},
//   //方法集合
//   methods: {},
//   //生命周期 - 创建完成（可以访问当前this实例）
//   created() {},
//   //生命周期 - 挂载完成（可以访问DOM元素）
//   mounted() {},
//   beforeCreate() {}, //生命周期 - 创建之前
//   beforeMount() {}, //生命周期 - 挂载之前
//   beforeUpdate() {}, //生命周期 - 更新之前
//   updated() {}, //生命周期 - 更新之后
//   beforeDestroy() {}, //生命周期 - 销毁之前
//   destroyed() {}, //生命周期 - 销毁完成
//   activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
// };
</script>
<style scoped>
h1 {
  font-weight: normal;
  color: brown;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>