<!--  -->
<template>
  <div class="Details">
    <el-main>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="execOrganno" label="执行机构" width="250"></el-table-column>

        <el-table-column prop="execDate" label="交易日期" width="150"></el-table-column>

        <el-table-column prop="amount" label="发生额" width="100"></el-table-column>

        <el-table-column prop="balance" label="余额" width="100"></el-table-column>

        <el-table-column prop="currType" label="币种" width="100">
          <template
            slot-scope="scope"
          >{{ scope.row.currType == 0 ? '人民币' : scope.row.currType == 1 ?'其他币种': '' }}</template>
        </el-table-column>

        <el-table-column prop="txnId" label="交易订单号" width="200"></el-table-column>
      </el-table>
    </el-main>
  </div>
</template>

<script>
import global from "@/api/global";
export default {
  data() {
    return {
      time: "",
      name: global.name,
      tableData: [],
      accid: global.accid,
    };
  },
  created() {
    this.addDate();
    this.getinfo();
  },
  methods: {
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
        url: "http://127.0.0.1:25008/details/query/detail",
        headers: {
          "Content-Type": "application/json",
          token: global.jwt,
        },
        data: JSON.stringify({
          currType: "1",
          execOrganno: "123",
          execTellerno: "123",
          txnCode: "1",
          accId: global.accid,
          txnDate: this.time,
        }),
      })
        .then((response) => {
          if (
            global.accid == "" ||
            response.data.code == 500 ||
            response.data.code == "500"
          ) {
            this.$alert(response.data.msg, "当前网页环境不安全，请重新登录", {
              confirmButtonText: "确定",
              callback: (action) => {
                this.$router.push({ path: "/" });
              },
            });
          } else {
            this.tableData = JSON.parse(response.data.账号信息);
          }
        })
        .catch(function (error) {
          this.$alert(response.data.msg, "当前网页环境不安全，请重新登录", {
            confirmButtonText: "确定",
            callback: (action) => {
              this.$router.push({ path: "/" });
            },
          });
          console.log("error");
        });
    },
  },
};

// };
</script>
<style>
</style>