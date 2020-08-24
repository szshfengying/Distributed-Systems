<template>
  <div class="openacc">

    <el-form :model="regForm" :rules="rules" label-width="180px" ref="regForm">

        <el-form-item label="账号">
          <el-input :disabled="true" v-model="regForm.accid"></el-input>
        </el-form-item>

    <el-form-item label="旧密码:" prop="oldpassword">
        <el-input
          placeholder="请输入旧密码:"
          v-model="regForm.oldpassword"
          type="password"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item> 

      <el-form-item label="新密码:" prop="password">
        <el-input
          placeholder="请输入新密码:"
          v-model="regForm.password"
          type="password"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>

      <el-form-item label="确认密码:" prop="checkPassword">
        <el-input
          placeholder="请再次输入新密码:"
          v-model="regForm.checkPassword"
          type="checkPassword"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <br />
        <el-button type="primary" @click="resetForm('regForm')" >确认重置</el-button>
        <el-button @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import global from '@/api/global';
import { Loading } from 'element-ui'
export default {
  data() {
    //自定义验证规则
    let validatePass1 = (rule, value, callback) => {
      // 6-16位, 数字, 字母, 字符至少包含两种, 同时不能包含中文和空格
      let reg = /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^[^\s\u4e00-\u9fa5]{6,16}$/;
      if (!reg.test(value)) {
        callback(new Error("密码长度需6-16位，数字，字母，字符至少包含两种"));
      } else {
        callback();
      }
    };
    //验证密码是否重复
    let validatePass2 = (rule, value, callback) => {
      if (value !== this.regForm.password) {
        callback(new Error("两次密码输入不一致"));
      } else {
        callback();
      }
    };
    return {
      regForm: {
        accid: global.accid,
        oldpassword:"",
        password:"",
        checkPassword: "",
      },
      checked: false,

      rules: {
    
        oldpassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
        password: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { validator: validatePass1, trigger: "blur" },
        ],
        checkPassword: [
          { required: true, message: "请再次输入新密码", trigger: "blur" },
          { validator: validatePass2, trigger: "blur" },
        ],
      },
    };
  },

  methods: {
   resetForm(formName) {
    this.$refs[formName].validate((valid) => {
        if (valid) {
           axios({
            method: "post",
            url:"http://127.0.0.1:25008/rest/ResetPassword/reset",
            // url: "http://10.23.14.167:25008/open/accOpen/register",
            headers: {
              /*         'Content-type': 'application/x-www-form-urlencoded', */
              "Content-Type": "application/json",
            },
            transformRequest: [
              function (data) {
                data = JSON.stringify(data);
                return data;
              },
            ],
            data: {
              accId: this.regForm.accid,
              password: this.regForm.oldpassword,
              newpassword: this.regForm.password,
            },
          })
            .then((response) => {
              if (response.data.code == "0") {
                var msg = "重置密码成功" ;
                this.$alert(msg, "重置密码成功", {
                  confirmButtonText: "返回主界面",
                  callback: (action) => {
                    this.$options.methods.back.bind(this)();
                  },
                });
              } else {
                this.$alert(response.data.msg, "重置密码失败", {
                  confirmButtonText: "确定",
                  callback: (action) => {
                    this.$options.methods.back.bind(this)();
                  },
                });
              }
            })
            .catch(function (error) {
              console.log(error);
            }); 
        }
      });
    },

    onInput() {
      this.$forceUpdate();
    },
    back() {
      this.$router.push({ path: "/Info" });
    },
  },
};
</script>



<style>
</style>