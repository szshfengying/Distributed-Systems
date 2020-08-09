<template>
  <div class="openacc">
    <h1>欢迎使用中国工商银行网上银行</h1>

    <el-form :model="regForm" :rules="rules" label-width="180px" ref="regForm">
       <el-form-item label="请选择地区、网点" :required="true">
        <el-cascader
          placeholder="请选择执行地区、网点"
          style="width: 350px;"
          v-model="regForm.valueBranches"
          :props="{value: 'value',label:'text'}"
          :options="branches"
        ></el-cascader>
      </el-form-item>

      <el-form-item label="请选择执行柜员" :required="true">
        <el-select style="width: 350px;" v-model="regForm.ExecTellerno" placeholder="请选择执行柜员">
          <el-option
            v-for="item in execTellerno"
            :key="item.label"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="姓名" prop="name">
        <el-input placeholder="请输入姓名:" v-model="regForm.name" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input placeholder="请输入手机号:" v-model="regForm.phone" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="id">
        <el-input placeholder="请输入身份证号:" v-model="regForm.id" @input="onInput()"></el-input>
      </el-form-item>

      <el-form-item label="密码:" prop="password">
        <el-input
          placeholder="请输入密码:"
          v-model="regForm.password"
          type="password"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>

      <el-form-item label="确认密码:" prop="checkPassword">
        <el-input
          placeholder="请再次输入密码:"
          v-model="regForm.checkPassword"
          type="checkPassword"
          show-password
          @input="onInput()"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-checkbox v-model="checked" label="免责声明" @input="onInput()">本人已知晓开户...风险提示,继续开户。</el-checkbox>
        <br />
        <el-button type="primary" @click="submit('regForm')" v-if="checked">确认开户</el-button>
        <!-- <el-button type="primary" @click="submit2()" v-if="checked">确认开户</el-button> -->
        <el-button type="primary" v-else disabled>确认开户</el-button>
        <el-button @click="resetForm('regForm')">重置</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import branches from "../api/branchs";

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
        name: "",
        phone: "",
        id: "",
        password: "",
        checkPassword: "",
        ExecTellerno: "",
        valueBranches: "",
      },
      branches: branches,
      execTellerno: [
        {
          value: "ExecTellerno01",
          label: "柜员1",
        },
        {
          value: "ExecTellerno02",
          label: "柜员2",
        },
        {
          value: "ExecTellerno03",
          label: "柜员3",
        },
        {
          value: "ExecTellerno04",
          label: "柜员4",
        },
      ],

      checked: false,

      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        phone: [{ required: true, message: "请输入电话", trigger: "blur" }],
        id: [{ required: true, message: "请输入身份证号", trigger: "blur" }],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { validator: validatePass1, trigger: "blur" },
        ],
        checkPassword: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          { validator: validatePass2, trigger: "blur" },
        ],
      },

      // form123:{
      //   setAccTitle: this.regForm.name,
      //   setExecOrganno: 1,
      //   setExecTellerno: this.regForm.ExecTellerno,
      //   setBranchId: this.regForm.BranchId,
      //   setpassword: this.regForm.password,
      //   setRegion: this.regForm.Region
      // },
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
          console.log(this.regForm);
          this.$alert("您的账号是", "开户成功", {
            confirmButtonText: "前往登录",
            callback: (action) => {
              this.$options.methods.back.bind(this)();
            },
          });
          // //验证通过
          // api.userRegister(this.regForm).then(({ data }) => {
          //   if (data.success) {
          //   this.$alert("您的账号是", "开户成功", {
          //       confirmButtonText: "前往登录",
          //       callback: (action) => {
          //         this.$options.methods.back.bind(this)();
          //       },
          //     });

          // //     this.$message({
          // //         type: 'success',
          // //         message: '注册成功'
          // //     });
          //   } else {
          //     this.$message({
          //       type: "info",
          //       message: "用户名已经存在",
          //     });
          //   }
          // });
        } else {
          //验证不通过
          console.log("error submit");
          return false;
        }
      });
    },
    // submit2() {
    //   // var postObj = {
    //   //   form: this.regForm,
    //   //   // name: this.regForm.name,
    //   //   // phone: this.regForm.phone,
    //   //   // id: this.regForm.id,
    //   //   // // options1: this.regForm.options1,
    //   //   // // options2: this.regForm.options2,
    //   //   // password: this.regForm.password,
    //   // };
    //   console.log(this.regForm);

    //   this.$alert("您的账号是", "开户成功", {
    //     confirmButtonText: "前往登录",
    //     callback: (action) => {
    //       this.$options.methods.back.bind(this)();
    //     },
    //   });
    // },

    // openacc() {
    //   this.$alert("您的帐号是", "开户成功", {
    //     confirmButtonText: "确定",
    //     callback: (action) => {
    //       // this.$message({
    //       //   type: "info",
    //       //   message: `action: ${action}`,
    //       // });
    //       this.$router.push({ path: '/' })
    //     },
    //   });
    // },
  },
};
</script>



<style>
</style>