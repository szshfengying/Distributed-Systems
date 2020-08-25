<template>
  <div class="Info">
    <el-main>
          <el-form ref="form" :model="infoform" label-width="80px">
            <el-form-item label="卡号">
              <el-input :disabled="true"  v-model="accid"></el-input>
            </el-form-item>
            <el-form-item label="开户机构">
              <el-input :disabled="true" v-model="infoform.addr"></el-input>
            </el-form-item>
            <el-form-item label="币种">
              <el-input :disabled="true" v-model="infoform.currtype"></el-input>
            </el-form-item>
                <el-form-item label="余额">
              <el-input  :disabled="true" :type="tpen" v-model="infoform.balance"></el-input>
            </el-form-item>
            
             <el-button type="primary" @click="show()">{{showdata}}</el-button> 
             <el-button type="primary" @click="reset()">重置密码</el-button>
          </el-form>
        </el-main>
  </div>
</template>

<script>
import global from "@/api/global";

export default {
  data() {
    return {
      showdata: "显示余额",
      n: "1",
      tpen: "password",
      time: "",
      name: "",
      accid: global.accid,
      bb: "",
      infoform: {
        addr: "",
        currtype: "",
        balance: 99999,
      },
    };
  },
  created() {
    this.addDate();
    this.getinfo();
  },

  methods:{
    check()
    {
        if(global.accid=="")
          {
              console.log("aaa")
               this.$alert("请重新登录", {
                  confirmButtonText: "确定",
                  callback: (action) => {
                      this.$router.push({ path: "/" });
                  },
                });
          }
    },
    reset()
    {
      this.$router.push({ path: "/Reset" });
    },
   show()
    {
        if(this.n=="1"||this.n==1)
        {
        this.infoform.balance = this.bb
        this.tpen="text";
        this.n="0";
        this.showdata="隐藏余额"
        }
        else
        {
          this.infoform.balance = 999999
          this.tpen="password";
          this.n="1";
          this.showdata="显示余额"
        }
    }, 
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
      getinfo() {
      axios({
        method: 'post',
        // url: 'http://127.0.0.1:25008/info/query/info',
        url: 'http://127.0.0.1:25003/query/info',
        headers: {
   /*         'Content-type': 'application/x-www-form-urlencoded', */
          "Content-Type": "application/json", 
          "token":global.jwt,
        },
        data:JSON.stringify( 
        {
          'currType':"1",
          'execOrganno':"123",
          "execTellerno":"123",
          "txnCode":"1",
          'accId':global.accid,
          "txnDate":this.time
        }), 
      })
           .then((response) => {
            if(global.accid==""||response.data.code==500||response.data.code=="500")
            {
                  this.$alert(response.data.msg, "当前网页环境不安全，请重新登录", {
                  confirmButtonText: "确定",
                  callback: (action) => {
                      this.$router.push({ path: "/" });
                  },
                });
            }
            else
            {
            this.accid=global.accid
            global.name=(JSON.parse(response.data.账号信息)).accTitle
            global.currtype=(JSON.parse(response.data.账号信息)).currType
            this.name=global.name
            this.infoform.addr=(JSON.parse(response.data.账号信息)).openingInstitution
            // this.infoform.balance=(JSON.parse(response.data.账号信息)).curBalance.toFixed(2)
            this.bb=(JSON.parse(response.data.账号信息)).curBalance.toFixed(2)
            if(global.currtype==0)
              this.infoform.currtype="人民币";
            else
              this.infoform.currtype="其他币种";
              console.log(global)
            }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  }
};

</script>
<style>

</style>