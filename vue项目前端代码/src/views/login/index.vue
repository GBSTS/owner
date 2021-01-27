<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">
          教务系统登录
        </h3>
      </div>

      <el-form-item prop="username" class="loginform-item">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          class="loginform-input"
          ref="username"
          v-model="loginForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-tooltip
        v-model="capsTooltip"
        content="Caps lock is On"
        placement="right"
        manual
      >
        <el-form-item prop="password" class="loginform-item">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            class="loginform-input"
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            :placeholder="$t('login.password')"
            name="password"
            tabindex="2"
            autocomplete="on"
            @keyup.native="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <svg-icon
              :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
            />
          </span>
        </el-form-item>
      </el-tooltip>

      <!-- 验证码TODO -->
      <el-form-item class="loginform-item" prop="verifycode">
        <span style="color: #889aa4; width: 30px; padding-left: 10px">
          <i class="el-icon-key" style="font-size: 20px; font-weight: bolder;"></i> 
        </span>
        <!-- 注意：prop与input绑定的值一定要一致，否则验证规则中的value会报undefined，因为value即为绑定的input输入值 -->
        <el-input
          class="loginform-input"
          v-model="loginForm.verifycode"
          placeholder="请输入验证码"
        ></el-input>
      </el-form-item>
      <el-form-item class="loginform-item" style="height: 51.6px">
          <div @click="refreshCode">
            <s-identify :identifyCode="identifyCode"></s-identify>
            <el-button @click="refreshCode" type="text" class="textbtn"
              >看不清，换一张</el-button
            >
          </div>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
      >
        {{ $t("login.logIn") }}
      </el-button>

      <div style="position: relative">
        <div class="tips">
          <span>账号 : admin</span>
          <span>密码 : admin123</span>
        </div>
        <div class="tips">
          <span style="margin-right: 18px">
            学生账号：
          </span>
          <span>通过导入学生excel获取</span>
        </div>

        <el-popover
          placement="top-start"
          title="忘记密码"
          width="200"
          trigger="hover"
          content="请联系账户管理员重置密码，重置后的密码为身份证后8位。">
            <el-button slot="reference" class="thirdparty-button" style="margin-right: 120px" type="text">
              忘记密码
            </el-button>
        </el-popover>


        <el-button class="thirdparty-button" type="primary" @click="toRegister">
          教师注册
        </el-button>
      </div>
    </el-form>

    <el-drawer
      title="教师注册信息填写"
      :visible.sync="dialog"
      direction="ltr"
      :with-header="false"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <p
        style="
          margin-left: 5px;
          font-size: 18px;
          height: 46px;
          border-bottom: 1px solid #ddd;
          width: 97%;
        "
      >
        教师注册信息
      </p>
      <el-form
        :inline="true"
        :model="teacher"
        :rules="rules"
        ref="teacher"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="名字" prop="name">
          <el-input v-model="teacher.name"></el-input>
        </el-form-item>
        <!-- 院系下拉列表，v-for实现 -->
        <el-form-item label="所属院系" prop="collegeId">
          <el-select v-model="teacher.collegeId" placeholder="请选择院系">
            <el-option
              v-for="item in collegeList"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="性别" prop="gender" required>
          <el-radio-group v-model="teacher.gender">
            <el-radio :label="false">男</el-radio>
            <el-radio :label="true">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityNumber" required>
          <el-input v-model="teacher.identityNumber"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="title" required>
          <el-input v-model="teacher.title"></el-input>
        </el-form-item>
        <el-form-item label="教师级别" prop="level" required>
          <el-select style="width: " v-model.number="teacher.level" placeholder="请选择教师级别">
            <el-option label="校级领导" :value=1></el-option>
            <el-option label="院级领导" :value=2></el-option>
            <el-option label="教师" :value=3></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="籍贯" prop="nativeplace">
          <el-input v-model="teacher.nativeplace"></el-input>
        </el-form-item>
        <el-form-item label="政治面貌" prop="political">
          <el-select v-model.number="teacher.political" placeholder="请选择政治面貌">
            <el-option label="中共党员" :value=1></el-option>
            <el-option label="共青团员" :value=2></el-option>
            <el-option label="群众" :value=3></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-col :span="11">
            <el-form-item prop="birth">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="teacher.birth"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="入职时间" required>
          <el-col :span="11">
            <el-form-item prop="hiredate">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="teacher.hiredate"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" required>
          <el-input v-model.number="teacher.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" required>
          <el-input v-model="teacher.email"></el-input>
        </el-form-item>

        <el-form-item label="申请角色" prop="role">
          <el-select v-model="teacher.role" placeholder="请选择角色">
            <el-option label="教师超级管理员" value='admin_teacher'></el-option>
            <el-option label="校级管理员" value='school'></el-option>
            <el-option label="院级管理员" value='college'></el-option>
            <el-option label="系级管理员" value='department'></el-option>
            <el-option label="教师" value='teacher'></el-option>
            <el-option label="学生管理员" value='admin_student'></el-option>
            <el-option label="管理系统管理员" value='manager'></el-option>
          </el-select>
        </el-form-item>

        <!-- 上传照片 -->
        <!-- action: 发送post请求的路径 -->
        <el-form-item label="照片" prop="photo" required>
          <el-upload
            v-loading="avatarLoading"
            class="avatar-uploader"
            action="http://8.135.93.69:8002/eduoss/fileoss"
            :on-progress="avatarProgress"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="teacher.photo" :src="teacher.photo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <!-- 上传执照 -->
        <!-- action: 发送post请求的路径 -->
        <el-form-item label="执照" prop="license" required>
          <el-upload
            v-loading="licenseLoading"
            class="avatar-uploader"
            action="http://8.135.93.69:8002/eduoss/fileoss"
            :show-file-list="false"
            :on-success="handleLicenseSuccess"
            :on-progress="licenseProgress"
            :before-upload="beforeLicenseUpload"
          >
            <img v-if="teacher.license" :src="teacher.license" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div style="margin-top: 55px">
        <el-button
          style="float: right; margin-right: 40px"
          @click="dialog = false"
          >取 消</el-button
        >
        <el-button
          style="float: right; margin-right: 20px"
          type="primary"
          @click="saveTeacher('teacher')"
          >立即创建</el-button
        >
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import LangSelect from '@/components/LangSelect'
import SocialSign from './components/SocialSignin'
import SIdentify from './components/SIdentify'

import collegeApi from '@/api/te/college'
import teacherApi from '@/api/te/teacher'

export default {
  name: 'Login',
  components: { LangSelect, SocialSign, SIdentify },
  data() {
    //校验身份证号格式
    var checkIdentityNumber = (rule, value, callback) => {
      if(!value) {
        callback(new Error('身份证号不能为空'));
      }
      const res = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value)
      if(!res) {
        callback(new Error('请输入正确的身份证号'));
      } else {
        callback();
      }
    }

    //校验电话号码是否为纯数字
    var checkPhone = (rule, value, callback) => {
      if(!value) callback();
        const result = /^\d+$/.test(value) //校验value字符串是否为纯数字
        if (!result) {
          callback(new Error('请输入数字值'));
        } else {
          callback();
        }
    };

    // 验证码自定义验证规则
    var validateVerifycode = (rule, value, callback) => {
      if (value === '') {
      callback(new Error('请输入验证码'))
      } else if (value !== this.identifyCode) {
          console.log('validateVerifycode:', value)
          callback(new Error('验证码不正确!'))
      } else {
        callback()
      };
    };

    // const validateUsername = (rule, value, callback) => {
    //   if (!validUsername(value)) {
    //     callback(new Error('Please enter the correct user name'))
    //   } else {
    //     callback()
    //   }
    // }
    // const validatePassword = (rule, value, callback) => {
    //   if (value.length < 6) {
    //     callback(new Error('The password can not be less than 6 digits'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      loginForm: {
        username: 'admin',
        password: 'admin123',
        verifycode: ''
      },
      loginRules: {
        // verifycode: [
        //   { required: true, trigger: 'blur', validator: validateVerifycode }
        // ]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      dialog: false, //注册框
      avatarLoading: false, //头像加载
      licenseLoading: false, //执照加载
      redirect: undefined,
      otherQuery: {},

      rules: {
        name: [
          { required: true, message: "请输入名字", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "长度在 1 到 10 个字符",
            trigger: "blur",
          },
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        collegeId: [
          { required: true, message: "请选择院系", trigger: "change" },
        ],
        nativeplace: [
          { required: true, message: "请输入籍贯", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
        title: [
          { required: true, message: "请输入职称", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
        hiredate: [
          {
            required: true,
            message: "请选择入职时间",
            trigger: "change",
          },
        ],
        political: [
          { required: true, message: "请选择政治面貌", trigger: "change" },
        ],
        role: [
          { required: true, message: "请选择角色", trigger: "change" },
        ],
        identityNumber: [
            {validator: checkIdentityNumber, trigger: 'blur'}
        ],
        phone: [
            {validator: checkPhone, trigger: 'blur'}
        ],
        email: [
            {type: "email", message: "邮箱格式不正确"}
        ]
      },
      teacher: {
          // identityNumber: '440902200008200825',
          // collegeId: '',
          // name: "叶子豪",
          // level: 1,
          // role: 'manager',
          // gender: false,
          // political: 1,
          // nativeplace: "广东东莞",
          // birth: "2000-12-03",
          // hiredate: "2020-12-03",
          // title: "校长",
          // phone: "1231234567",
          // email: "yezihao@qq.com",
          // photo: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2975996532,2425031589&fm=26&gp=0.jpg', //照片路径
          // license: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2975996532,2425031589&fm=26&gp=0.jpg', //执照路径
      },
      collegeList: [
        // {
        //   id: 'asuihd12d1n12ff1',
        //   name: '计算机与工程学院'
        // },
        // {
        //   id: 'asuihd12d1n12ff2',
        //   name: '美术学院'
        // },
      ],

      identifyCodes: '1234567890',
      identifyCode: '',


    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  created() {
    // window.addEventListener('storage', this.afterQRScan)
    this.getCollegeList()
  },
  mounted() {
    // 验证码初始化
    this.identifyCode = ''
    this.makeCode(this.identifyCodes, 4)

    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {

      //模态框点击确定
    saveTeacher(teacher) {
      this.$refs[teacher].validate((valid) => {
        if (valid) {
          //表单前端校验通过
          console.log(this.teacher)
          teacherApi.saveTeacher(this.teacher)
            .then(res=>{
                this.$message({
                type: "success",
                message: "注册成功!",
              });
              this.loginForm.username = res.data.teacherNumber
              this.$alert('用户名：'+res.data.teacherNumber+'<br>密码：身份证后8位<br>请牢记用户名和密码！', '账号信息', {
                confirmButtonText: '确定',
                dangerouslyUseHTMLString: true
              });
              this.dialog = false
          }) 
        } else {
          return false;
        }
      });
    },

      //得到所有院
      getCollegeList() {
      //得到所有院的list
        collegeApi.getAllCollege()
          .then(res=>{
            this.collegeList = res.data.list
          })
      },

      // 生成随机数
      randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min)
      },
      // 切换验证码
      refreshCode() {
        this.identifyCode = ''
        this.makeCode(this.identifyCodes, 4)
      },
      // 生成四位随机验证码
      makeCode(o, l) {
        for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
        this.randomNum(0, this.identifyCodes.length)
          ]
        }
        console.log(this.identifyCode)
       },
    

   //上传执照成功之后
      handleLicenseSuccess(res, file) {
        this.licenseLoading = false
        this.teacher.license = res.data.url
      },
      //上传执照时
      licenseProgress() {
        this.licenseLoading = true
      },

      //上传执照之前
      beforeLicenseUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 1;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式！');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 1MB！');
        }
        return isJPG && isLt2M;
      },

      //上传头像成功之后
      handleAvatarSuccess(res, file) {
        this.teacher.photo = res.data.url
        this.avatarLoading = false
      },
      //上传头像时
      avatarProgress() {
        this.avatarLoading = true
      },
      //上传头像之前
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },

    toRegister() {
      // this.teacher = {}
      this.dialog = true
    },

    checkCapslock(e) {
      const { key } = e
      this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm)
            .then(() => {
              this.$router.push({ path: '/', query: this.otherQuery })
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
    // afterQRScan() {
    //   if (e.key === 'x-admin-oauth-code') {
    //     const code = getQueryObject(e.newValue)
    //     const codeMap = {
    //       wechat: 'code',
    //       tencent: 'code'
    //     }
    //     const type = codeMap[this.auth_type]
    //     const codeName = code[type]
    //     if (codeName) {
    //       this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
    //         this.$router.push({ path: this.redirect || '/' })
    //       })
    //     } else {
    //       alert('第三方登录失败')
    //     }
    //   }
    // }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-drawer {
    width: 85% !important;
    background-color: $bg;
    color: $cursor !important;
  }

  .el-input {
    display: inline-block;
    // height: 47px;
    width: 85%;

    input {
      background: transparent;
      width: 100%;
      border-top: 0px;
      border-left: 0px;
      border-right: 0px;
      margin-left: 5px;
      // padding-left: 5px;
      // border-bottom: 1px;
      -webkit-appearance: none;
      border-radius: 0px;
      // padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      // caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    width: 220px;
    // margin-top: 50px;
    // background: rgba(0, 0, 0, 0.1);
    // border-radius: 5px;
    // color: #dddddd;
  }
  .el-form-item__content {
    display: block;
  }
  .el-input__suffix {
    margin-right: -36px;
  }
  .el-form-item__label {
    text-align: left;
    padding-left: 10px;
    color: #ddd;
    line-height: 45px;
  }
  .el-radio-group {
    margin-left: 15px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .loginform-item {
    width: 100%;
  }

  .loginform-input {
    .input {
      width: 100% !important;
    }

    // width: 85%;
    // width: 100%;
    border-buttom: 2px solid #ddd;
  }

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    .set-language {
      color: #fff;
      position: absolute;
      top: 3px;
      font-size: 18px;
      right: 0px;
      cursor: pointer;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  .s-canvas{
    display: inline-block;
    height: 51.6px;
  }

  .textbtn {
    float: right;
    margin-right: 30px;
    line-height: 30px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
