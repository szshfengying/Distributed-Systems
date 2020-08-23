<template>
  <div class="openacc">
    <h1>欢迎使用中国工商银行网上银行</h1>

    <el-form :model="regForm" :rules="rules" label-width="180px" ref="regForm">
      <el-form-item label="请选择地区、网点" prop="valueBranches">
        <el-cascader
          placeholder="请选择执行地区、网点"
          style="width: 350px;"
          v-model="regForm.valueBranches"
          :props="{value: 'text',label:'text'}"
          :options="branches"
        ></el-cascader>
      </el-form-item>

      <!-- <el-form-item label="请选择执行柜员" :required="true" prop="ExecTellerno">
        <el-select style="width: 350px;" v-model="regForm.ExecTellerno" placeholder="请选择执行柜员">
          <el-option
            v-for="item in execTellerno"
            :key="item.label"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item> -->

      <el-form-item label="姓名" prop="name">
        <el-input placeholder="请输入姓名:" v-model="regForm.name" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input placeholder="请输入手机号:" v-model="regForm.phone" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="id">
        <el-input placeholder="请输入身份证号:" v-model="regForm.id" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="登录密码:" prop="password">
        <el-input
          placeholder="请输入登录密码:"
          v-model="regForm.password"
          type="password"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>
      <el-form-item label="确认登录密码:" prop="checkPassword">
        <el-input
          placeholder="请再次输入登录密码:"
          v-model="regForm.checkPassword"
          type="checkPassword"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>
   
       <el-form-item label="交易密码:" prop="pay_password">
         
        <el-input
          placeholder="请输入交易密码:"
          v-model="regForm.pay_password"
          type="pay_password"
          show-password
          @input="onInput()"
        ></el-input>
         <font size="2" color="brown"> ⚠️交易密码用于交易操作，请注意与登录密码区分。</font>
      </el-form-item>

      <el-form-item label="确认交易密码:" prop="checkPay_password">
        <el-input
          placeholder="请再次输入交易密码:"
          v-model="regForm.checkPay_password"
          type="checkPay_password"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>


      <el-form-item>
        <el-checkbox v-model="checked" @input="onInput()">本人同意开通中国工商银行银行网上账户。</el-checkbox>
        <br />
        <el-button type="primary" @click="submit('regForm')" v-if="checked">确认开户</el-button>
        <el-button type="primary" v-else disabled>确认开户</el-button>
        <el-button @click="resetForm('regForm')">重置</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import branches from "../api/branchs";
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

    let validateIDnumber = (rule, value, callback) => {
      let reg = /^\d{18}$/;
      if (!reg.test(value)) {
        callback(new Error("身份证格式错误"));
      } else {
        callback();
      }
    };
 let validatePhonenumber = (rule, value, callback) => {
      let reg = /^\d{11}$/;
      if (!reg.test(value)) {
        callback(new Error("手机号不正确"));
      } else {
        callback();
      }
    };
    let validatePayPass1 = (rule, value, callback) => {
      // 6位数字
      let reg = /^\d{6}$/;
      if (!reg.test(value)) {
        callback(new Error("密码长度需6位数字"));
      } else {
        callback();
      }
    };


    let validatePayPass2 = (rule, value, callback) => {
      if (value !== this.regForm.pay_password) {
        callback(new Error("两次密码输入不一致"));
      } else {
        callback();
      }
    };
    return {
      regForm: {
        name: "",
        phone: "",
        id: "",
        password: "",
        checkPassword: "",
        ExecTellerno: "",
        valueBranches: "",
        pay_password:"",
        checkPay_password:""


      },
      branches: branches,
      // execTellerno: [
      //   {
      //     value: 1,
      //     label: "柜员1",
      //   },
      //   {
      //     value: 2,
      //     label: "柜员2",
      //   },
      //   {
      //     value: 3,
      //     label: "柜员3",
      //   },
      //   {
      //     value: 4,
      //     label: "柜员4",
      //   },
      // ],

      checked: false,

      rules: {
        valueBranches: [
          { required: true, message: "请选择网点", trigger: "blur" },
        ],
        ExecTellerno: [
          { required: true, message: "请选择执行柜员", trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        phone: [{ required: true, message: "请输入电话号码", trigger: "blur" },
        { validator: validatePhonenumber, trigger: "blur" }],
        id: [{ required: true, message: "请输入身份证号", trigger: "blur" },
         { validator: validateIDnumber, trigger: "blur" }],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" },
          { validator: validatePass1, trigger: "blur" },
        ],
        checkPassword: [
          { required: true, message: "请再次输入登录密码", trigger: "blur" },
          { validator: validatePass2, trigger: "blur" },
        ],
        pay_password:[
          { required: true, message: "请输入与登录密码不同的交易密码", trigger: "blur" },
          { validator: validatePayPass1, trigger: "blur" },
        ],

        checkPay_password:[
          { required: true, message: "请再次输入与登录密码不同的交易密码", trigger: "blur" },
          { validator: validatePayPass2, trigger: "blur" },
        ],


      },
    };
  },

  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    onInput() {
      this.$forceUpdate();
    },
    back() {
      this.$router.push({ path: "/" });
    },

    submit(formName) {
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
            method: "post",
            url:"http://127.0.0.1:25008/open/accOpen/register",
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
              execOrganno:
                this.regForm.valueBranches[0] +
                this.regForm.valueBranches[1] +
                this.regForm.valueBranches[2],
              // execTellerno: this.regForm.ExecTellerno,
              execTellerno: 1,
              region: this.regForm.valueBranches[0],
              branchId: this.regForm.valueBranches[2],
              accTitle: this.regForm.name,
              password: this.regForm.password,
              phone: this.regForm.phone,
              number: this.regForm.id,
              payPassword: this.regForm.pay_password
            },
          })
            .then((response) => {
              if (response.data.code == "0") {
                var msg = "您的账号是: " + response.data.accid ;
                this.$alert(msg, "开户成功", {
                  confirmButtonText: "前往登录",
                  callback: (action) => {
                    this.$options.methods.back.bind(this)();
                  },
                });
              } else {
                this.$alert(response.data.msg, "开户失败", {
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
  },
};
</script>



<style>
</style>