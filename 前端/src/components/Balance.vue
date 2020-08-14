<!--  -->
<template>
  <div class="Info">
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']" router>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>主菜单
            </template>
            <el-menu-item-group>
              <template slot="title">账户详情</template>
              <el-menu-item index="/Info">账户信息</el-menu-item>
              <el-menu-item index="/Balance">余额查询</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="功能模块">
              <el-menu-item index="/Transfer">转账</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>设置</el-dropdown-item>
              <el-dropdown-item>关于我们</el-dropdown-item>
              <el-dropdown-item>联系我们</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>{{name}}</span>
        </el-header>

        <el-main>
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="余额">
              <el-input :disabled="true" v-model="balform.balance"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="balform.currtype"></el-input>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
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
      name:global.name,
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
          'accId':800000200000076,
          "txnDate":this.time
        }), 
      })
        .then((response) => {
          this.name=global.name
            this.balform.balance=(JSON.parse(response.data.账号信息)).curBalance
              if(global.currtype==1)
              this.balform.currtype="人民币";
            else
              this.balform.currtype="其他币种";
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.addDate() 
    this.getbal()
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