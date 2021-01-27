<template>
  <div class="app-container">
    <div class="block">
      <el-date-picker
        v-model="value1"
        type="datetimerange"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :default-time="['12:00:00']">
      </el-date-picker>
    </div>

    <el-button  @click="test">默认按钮</el-button>

    
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

  export default {
    data() {
      return {
        value1: '',
      };
    },
    computed: {
    ...mapGetters([
      'roles','avatar'
    ])
  },

    created() {
        this.getRole()
        // this.setTimeOut()
    },

    methods: {
      // setTimeOut() {
      //   var id =setInterval(function () {
      //     if (new Date().getSeconds()==0){   //myDate.getHours();    //获取当前小时数(0-23) 
      //         console.log('当前是',new Date().getMinutes())
      //         clearInterval(id)
      //     } else{
      //         console.log('时间未到')
      //     }
      //   },1000,id)
      // },

      getRole() {
        console.log(this.roles)
        console.log(this.avatar)
      },

      test() {
        console.log(this.value1)
      }

  }
}
</script>
