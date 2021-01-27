<template>
    <div class="app-container">
        <el-form :model="passWord" status-icon :rules="rules" ref="passWord" label-width="100px" class="demo-passWord">
            <el-form-item label="当前密码" prop="curPwd" required>
                <el-input type="password" v-model="passWord.curPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPwd" required>
                <el-input type="password" v-model="passWord.newPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPwd" required>
                <el-input type="password" v-model="passWord.confirmPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('passWord')">提交</el-button>
                <el-button @click="resetForm('passWord')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import userApi from '@/api/te/user'

export default {

    computed: {
    ...mapGetters([
      'id','roles'
    ])
  },
    data() {
      
        var validateCurPwd = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else if (value.length < 6) {
                callback(new Error('密码长度必须大于等于6位！'))    
            } else {
                callback();
            }
      };

      var validateNewPwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (value.length < 6) {
            callback(new Error('密码长度必须大于等于6位！'))    
        } else {
            callback();
        }
      };
      var validateConfirmPwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.passWord.newPwd) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        passWord: {
          id: '',
          curPwd: '',
          newPwd: '',
          confirmPwd: ''
        },
        rules: {
          curPwd: [
            { validator: validateCurPwd, trigger: 'blur' }
          ],
          newPwd: [
            { validator: validateNewPwd, trigger: 'blur' }
          ],
          confirmPwd: [
            { validator: validateConfirmPwd, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        if(this.roles[0] == 'admin') {
          this.$message({
              type: 'error',
              message: 'admin无法修改密码！'
          })
          return false
        }

        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.passWord.id = this.id
            userApi.changePwd(this.passWord)
                .then(res=>{
                    this.$message({
                        type: 'success',
                        message: '修改成功！'
                    })
                })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
}
</script>