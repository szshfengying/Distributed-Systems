<template>
  <div class="Login">
    <h1>欢迎使用中国工商银行网上银行</h1>

    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="帐号">
        <el-input placeholder="请输入帐号" v-model="form.name" @input="onInput()"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input placeholder="请输入密码" v-model="form.password" show-password @input="onInput()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
        <el-button @click="openacc">开户</el-button>
        <el-button @click="Test">开户Test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: "",
        password: "",
      },
    };
  },
  methods: {

    onInput() {
      this.$forceUpdate();
    },
    login() {
      // 登录
      this.$router.push({ path: "/Details" });
    },
    openacc() {
      this.$router.push({ path: "/OpenAcc" });
    },
    Test(){
      this.$router.push({ path: "/Test" });
    }
  },
};

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: normal;
  color: brown;
}
</style>