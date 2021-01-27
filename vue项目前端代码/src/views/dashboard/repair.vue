<template>
    <div class="app-container">
        <el-form
        
        :model="repair"
        :rules="rules"
        ref="repair"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="维修类型" prop="repairType">
          <el-select v-model="repair.repairType" placeholder="请选择维修类型">
            <el-option label="数据显示错误" :value=1></el-option>
            <el-option label="添加错误" :value=2></el-option>
            <el-option label="修改错误" :value=3></el-option>
            <el-option label="删除错误" :value=4></el-option>
            <el-option label="账户错误" :value=5></el-option>
            <el-option label="功能性错误" :value=6></el-option>
            <el-option label="页面卡顿" :value=7></el-option>
            <el-option label="其他错误" :value=8></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="问题描述" >
            <el-input type="textarea" v-model="repair.content"></el-input>
        </el-form-item>

        <div class="el-form-item">
            <label class="el-form-item__label">接收状态:</label>
            <div class="el-form-item__content">
            <el-tag
                  type="info"
                  v-if="repair.userId == null"
                  effect="dark"
                  >未接收</el-tag
                >
                <el-tag
                  type="success"
                  v-else
                  effect="dark"
                  >已接收</el-tag
                >
            </div>
        </div>

        <div class="el-form-item" v-if="repair.userId != null">
            <label class="el-form-item__label">接收人：</label>
            <div class="el-form-item__content">
            {{repair.userName}}
            <!---->
            </div>
        </div>

        <div class="el-form-item" v-if="repair.userId != null">
            <label class="el-form-item__label">联系电话：</label>
            <div class="el-form-item__content">
            {{repair.userPhone}}
            <!---->
            </div>
        </div>

        <div class="el-form-item" v-if="repair.userId != null && repair.repairStatus != null">
            <label class="el-form-item__label">维修状态:</label>
            <div class="el-form-item__content">
            <el-tag
                  type="info"
                  v-if="repair.repairStatus == 0"
                  effect="dark"
                  >未维修</el-tag
                >
                <el-tag
                  type="success"
                  v-else
                  effect="dark"
                  >已维修</el-tag
                >
            </div>
        </div>

        <div style="margin-top: 50px">

        <el-button
          style="float: right; margin-right: 60px"
          type="primary"
          @click="saveOrUpdateRepair"
          >申 请</el-button
        >
      </div>

      </el-form>
    </div>
</template>

<script>
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import repairApi from '@/api/ma/user-maintenance'

export default {
    computed: {
    ...mapGetters([
      'id'
    ])
  },

    data(){
        return{
            rules: {
                repairType: [
                    { required: true, message: "请选择维修类型", trigger: "change" },
                ],
      },

            repair:{
                // repairId: '',
                // teacherNumber: '',
                // repairType: '',
                // content: '',
                // isVerified: 2,
                // rejectReason: ''
            },

        }
    },
    created(){ 
		this.getRepair()
    },

    methods:{ 
        
        getRepair() {
            repairApi.getRepairByUserId(this.id)
                .then(res=>{
                    this.repair = res.data.repair
                })
        },

        saveOrUpdateRepair() {
            this.repair.teacherNumber = this.id
            repairApi.saveOrUpdateRepair(this.repair)
                .then(res=>{
                    this.$message({
                  type: "success",
                  message: "申请成功!",
                });
                this.getRepair()
            })
        }



    }
}
</script>