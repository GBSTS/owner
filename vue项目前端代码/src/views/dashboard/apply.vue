<template>
    <div class="app-container">
        <el-form
        
        :model="apply"
        :rules="rules"
        ref="apply"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="申请角色" prop="role">
          <el-select v-model="apply.role" placeholder="请选择角色">
            <el-option label="教师超级管理员" value='admin_teacher'></el-option>
            <el-option label="校级管理员" value='school'></el-option>
            <el-option label="院级管理员" value='college'></el-option>
            <el-option label="系级管理员" value='department'></el-option>
            <el-option label="教师" value='teacher'></el-option>
            <el-option label="学生管理员" value='admin_student'></el-option>
            <el-option label="管理系统管理员" value='manager'></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="申请内容" >
            <el-input type="textarea" v-model="apply.content"></el-input>
        </el-form-item>

        <div class="el-form-item" v-if="apply.isVerified != null">
            <label class="el-form-item__label">审核状态:</label>
            <div class="el-form-item__content">
            <el-tag
                  type="info"
                  v-if="apply.isVerified == 0"
                  effect="dark"
                  >未审批</el-tag
                >
                <el-tag
                  type="success"
                  v-else-if="apply.isVerified == 1"
                  effect="dark"
                  >已审批</el-tag
                >
            <el-tag type="danger" effect="dark" v-else>驳回</el-tag>
            <!---->
            </div>
        </div>

        <div class="el-form-item" v-if="apply.isVerified == 2">
            <label class="el-form-item__label">驳回理由：</label>
            <div class="el-form-item__content">
            {{apply.rejectReason}}
            <!---->
            </div>
        </div>


        <div style="margin-top: 50px">

        <el-button
          style="float: right; margin-right: 60px"
          type="primary"
          @click="saveOrUpdateApply"
          >申 请</el-button
        >
      </div>

      </el-form>
    </div>
</template>

<script>
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import applyApi from '@/api/ma/user-apply'

export default {
    computed: {
    ...mapGetters([
      'id','roles'
    ])
  },

    data(){
        return{
            rules: {
                role: [
                    { required: true, message: "请选择角色", trigger: "change" },
                ],
      },

            apply:{
                // applyId: '',
                // teacherNumber: '',
                // role: '',
                // content: '',
                // isVerified: 2,
                // rejectReason: ''
            },

        }
    },
    created(){ 
		this.getApply()
    },

    methods:{ 
        
        getApply() {
            applyApi.getApplyByUserId(this.id)
                .then(res=>{
                    this.apply = res.data.apply
                })
        },

        saveOrUpdateApply() {
          if(this.roles[0] == 'admin') {
            this.$message({
                  type: "error",
                  message: "超级管理员admin不能申请!",
                });
            return false
          }
          if(this.roles[0] == this.apply.role) {
            this.$message({
                  type: "error",
                  message: "你当前已经是该角色!",
                });
            return false
          }
            this.apply.teacherNumber = this.id
            applyApi.saveOrUpdateApply(this.apply)
                .then(res=>{
                    this.$message({
                  type: "success",
                  message: "申请成功!",
                });
                this.getApply()
            })
        }



    }
}
</script>