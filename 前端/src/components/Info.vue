<template>
  <div class="Info">
    <h1>{{name}}</h1>
    <el-main>
      <el-form ref="infoform" :model="infoform" label-width="80px">
        <el-form-item label="卡号">
          <el-input :disabled="true" v-model="accid"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input :disabled="true" v-model="infoform.addr"></el-input>
        </el-form-item>
        <el-form-item label="币种">
          <el-input :disabled="true" v-model="infoform.currtype"></el-input>
        </el-form-item>
        <el-form-item label="余额">
          <el-input :disabled="true" :type="tpen" v-model="infoform.balance"></el-input>
        </el-form-item>
        <el-button type="primary" @click="show()">{{showdata}}</el-button>
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
      infoform: {
        addr: "",
        currtype: "",
         balance:"",
      },
    };
  },
  created() {
    this.addDate();
    this.getinfo();
  },
  methods: {
    show()
    {
        if(this.n=="1"||this.n==1)
        {
        this.tpen="text";
        this.n="0";
        this.showdata="隐藏余额"
        }
        else
        {
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
      this.time = year + "-" + month + "-" + strDate;
    },
    getinfo() {
      axios({
        method: "post",
        url: "http://127.0.0.1:25008/info/query/info",
        headers: {
          "Content-Type": "application/json",
        },
        data: JSON.stringify({
           'currType':"1",
          'execOrganno':"123",
          "execTellerno":"123",
          "txnCode":"1",
          'accId':global.accid,
          "txnDate":this.time
        }),
      })
               .then((response) => {
                 consloe.log(response)
            this.accid=global.accid
            global.name=(JSON.parse(response.data.账号信息)).accTitle
            global.currtype=(JSON.parse(response.data.账号信息)).currType
            this.name=global.name
            this.infoform.addr=(JSON.parse(response.data.账号信息)).regionId
            this.infoform.balance=(JSON.parse(response.data.账号信息)).curBalance
            if(global.currtype==1)
              this.infoform.currtype="人民币";
            else
              this.infoform.currtype="其他币种";
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