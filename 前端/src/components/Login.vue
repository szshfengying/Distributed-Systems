<template>
<div class="hello">
    <h1>欢迎使用中国工商银行网上银行</h1>
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      status-icon
    >
      <el-form-item prop="userName">
        <el-input v-model="dataForm.userName" placeholder="帐号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="login-btn-submit" type="primary" @click="login()">登录</el-button>
        <el-button class="login-btn-submit"  @click="openacc()">开户</el-button>
        <el-button @click="Test()">开户Test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import global from '@/api/global'
export default {
  data() {
let validateAcc = (rule, value, callback) => {
      let reg = /^[\d]{16}$/;
      if (!reg.test(value)) {
        callback(new Error("账号不正确"));
      } else {
        callback();
      }
    };

    return {
      dataForm: {
        userName: "",
        password: "",
      },
      dataRule: {
        userName: [
          { required: true, message: "帐号不能为空", trigger: "blur" },
          { validator: validateAcc,trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    openacc() {
      this.$router.push({ path: "/OpenAcc" });
    },
    login() {
      // 获取远端图片
      axios({
        method: 'post',
        url: 'http://localhost:8081/login',
        headers: {
   /*         'Content-type': 'application/x-www-form-urlencoded', */
          "Content-Type": "application/json", 
        },
        params: 
        {
          'username': this.dataForm.userName,
          'password': this.dataForm.password 
        }, 
      })
        .then((response) => {
            if (response.data.code == 0) { 
            global.accid=this.dataForm.userName;
           this.$router.push({ path: "/Info" });
           }
            else{
              this.$message(response.data.msg);
           }
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    onInput() {
      this.$forceUpdate();
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