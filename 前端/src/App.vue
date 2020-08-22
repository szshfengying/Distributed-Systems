<template>
  <div id="app">
    <img src="./assets/logo.png" />
    <el-container
      style="height: 800px; border: 1px solid #eee"
      v-if="!(path==='/'||path==='/OpenAcc')"
    >
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']" router>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>主菜单
            </template>
            <el-menu-item-group>
              <template slot="title">账户详情</template>
              <el-menu-item index="/Info">账户信息</el-menu-item>
              <el-menu-item index="/Details">明细查询</el-menu-item>
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
              <el-dropdown-item @click.native="back">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>{{Cusname}}</span>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>

    <router-view v-if="(path==='/'||path==='/OpenAcc')" />

  </div>
</template>

<script>
import global from './api/global'
// import {name} from './components/Info'
export default {
  name: "App",

  data() {
    return {
      // globalname : s,
      Cusname: global.name,
      path: "",
    };
  },

  methods: {
    back() {
      this.$router.push({ path: "/" });
    },
  },

  mounted() {
    this.path = this.$route.path;
  },

  watch: {
    $route(to, from) {
      this.path = to.path;
    },

  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1 {
  font-weight: normal;
  color: brown;
}

.el-form {
  width: 500px;
  margin: 0px auto;
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
