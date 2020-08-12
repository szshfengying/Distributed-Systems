<template>
  <div class="Details">
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
            <el-form-item label="卡号">
              <el-input :disabled="true" v-model="infoform.accid"></el-input>
            </el-form-item>
            <el-form-item label="地址">
              <el-input :disabled="true" v-model="infoform.addr"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="infoform.currtype"></el-input>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "张炜杰",
      infoform:{
      accid: "",
      addr: "",
      currtype: "",
      }
    };
  },
  created(){
    this.getinfo()
  },
  methods:{
      getinfo() {
          this.$http({
            url: this.$http.adornUrl("/sys/info"),
            method: "get",
          }).then(({ data }) => {
            this.name=data.name
            this.infoform.accid=data.accid
            this.infoform.addr=data.addr
            this.infoform.currtype=data.currtype
          });
    },
  }
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
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