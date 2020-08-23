<template>
  <div id="app" @click="isDo()">
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

      <el-container >
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>设置</el-dropdown-item>
              <el-dropdown-item @click.native="aboutUs">关于我们</el-dropdown-item>
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
import global from "./api/global";
// import {name} from './components/Info'
export default {
  name: "App",

  data() {
    return {
      lastTime: null, //最后一次点击的时间
      currentTime: null, //当前点击的时间
      timeOut: 1 * 60 * 1000, //设置超时时间： 1分钟,
      Cusname: global.name,
      path: "",
    };
  },

  methods: {
    aboutUs() {},
    back() {
      this.$router.push({ path: "/" });
    },

    isDo() {
      this.currentTime = new Date().getTime(); //记录这次点击的时间
      if (this.currentTime - this.lastTime > this.timeOut && this.path != '/' && this.path !='/OpenAcc') {
        //判断上次最后一次点击的时间和这次点击的时间间隔是否大于10分钟
        // 这里写状态已过期后执行的操作
        this.$alert("由于长时间未操作，已自动退出，请重新登录。", "长时间未操作", {
                  confirmButtonText: "重新登录",
                  callback: (action) => {
                    this.$options.methods.back.bind(this)();
                  },
                });

      } else {
        this.lastTime = new Date().getTime(); //如果在10分钟内点击，则把这次点击的时间记录覆盖掉之前存的最后一次点击的时间
      }
    },
  },
  created() {
    this.lastTime = new Date().getTime(); //网页第一次打开时，记录当前时间
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
