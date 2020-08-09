<template>
  <div>
   <!-- <el-select v-model="value" placeholder="请选择" @change=selectOne >
    <el-option
      v-for="item in options"

      :key="item.value"
      :label="item.label"
      :value="{value:item.value,label:item.label,version:item.version}">
    </el-option>
  </el-select>
  <el-select v-model="value2" placeholder="请选择">
    <el-option
      v-for="item in tempList"
      :key="item.value"
      :label="item.value"
      :value="item.value">
    </el-option>
  </el-select> -->

  <div class="block">
          <span class="demonstration">现居住地：</span>
          <el-cascader
            v-model="valueBranches"
            :props="{value: 'value',label:'text'}"
            :options="branches">
          </el-cascader>
          <el-button type="primary" @click="submit()">确认开户</el-button>
        </div>
  </div>


  
</template>
 
<script>
import branches from "../api/branchs"


export default {
    data() {
return {
branches: branches,

valueBranches: branches.values, 
}
},

methods: {
    submit(){
         console.log(this.valueBranches);
         console.log(this.valueBranches_lable);
    }
},

}


//   data () {
//     return {
//       options: [{
//         value: '选项1',
//         label: '黄金糕',
//         version: [
//           {
//             key: 1,
//             value: 'A1'
//           },
//           {
//             key: 2,
//             value: 'A2'
//           }
//         ]
//       }, {
//         value: '选项2',
//         label: '双皮奶',
//         version: [
//           {
//             key: 1,
//             value: 'b1'
//           },
//           {
//             key: 2,
//             value: 'b2'
//           }
//         ]
//       }],
//       value: '',
//       value2: '',
//       tempList: []
//     }
//   },
//   methods: {
//     selectOne (params) {
//       // debugger
//       /* const { value, label, version} = params
//         console.log(value, label, version) */
//       this.value2 = ''
//       this.tempList = params.version
//     }
//   }
// }
</script>
 
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>