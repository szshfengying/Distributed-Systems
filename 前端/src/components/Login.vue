<template>
<div class="login">
    <h1>欢迎使用中国工商银行网上银行</h1>
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      status-icon
    >
      <el-form-item prop="accid">
        <el-input v-model="dataForm.accid" placeholder="帐号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="login-btn-submit" type="primary" @click="login('dataForm')">登录</el-button>
        <el-button class="login-btn-submit"  @click="openacc()">开户</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import global from '@/api/global'
import { Loading } from 'element-ui'
export default {
  data() {
let validateAcc = (rule, value, callback) => {
      if (!checkCard(value)) {
        callback(new Error("账号不正确"));
      } else {
        callback();
      }
    };
    // luhn算法校验位
  function checkCard(cardNo){
    if(isNaN(cardNo))
        return false;
    if(cardNo.length != 16){  //判断长度  
        return false;
    }
    var nums = cardNo.split("");
    var sum = 0;
    var index = 1;
    for(var i = 0 ; i < nums.length; i++){
        if((i+1)%2==0){
            var tmp = Number(nums[nums.length-index])*2;
            if(tmp >= 10){
                var t = tmp+"".split("");
                tmp = Number(t[0]) + Number(t[1]);
            }
            sum+=tmp;
        }else{
            sum+=Number(nums[nums.length-index]);
        }
        index ++;
    }
    if(sum%10 != 0){
        return false;
    }
    return true;
}

    return {
      dataForm: {
        accid: "",
        password: "",
      },
      dataRule: {
        accid: [
          { required: true, message: "帐号不能为空", trigger: "blur" },
          // { validator: validateAcc,trigger: "blur" }
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
    login(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

      // 添加请求拦截器
    axios.interceptors.request.use(function (config) {
    Loading.service({
    lock: true,
    text: '加载中……',
    background: 'rgba(0, 0, 0, 0.7)'
  })
      return config;
     }, function (error) {
       console.log("req error")
        // 对请求错误做些什么
        return Promise.reject(error);
    });

 // 添加响应拦截器
axios.interceptors.response.use(function (response) {
   Loading.service().close();
    // 对响应数据做点什么
    return response;
  }, function (error) {
    Loading.service().close();
    // 对响应错误做点什么
    return Promise.reject(error);
  });

      axios({
        method: 'post',
        url:'http://127.0.0.1:25008/login/AccLogin/login',
        headers: {
          "Content-Type": "application/json", 
        },
        transformRequest: [
              function (data) {
                data = JSON.stringify(data);
                return data;
              },
            ],
     
        data: {
          accid: this.dataForm.accid,
          password: this.dataForm.password 
        }, 
      })
        .then((response) => {
            // if (response.data.code == 0) { 
              if (response.data.msg == "登录成功") { 
            global.accid=this.dataForm.accid;
           this.$router.push({ path: "/Info" });
           }
            else{
              this.$message(response.data.msg);
           }
        })
        .catch(function (error) {
          console.log(error);
        });


        };
        })

    },

    onInput() {
      this.$forceUpdate();
    },

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