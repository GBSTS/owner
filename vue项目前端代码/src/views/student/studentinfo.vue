<template>
  <div class="app-container">
    <!-- 编辑信息卡 -->
    <el-card class="box-card" style="width: 100%" v-if="editShow">
      <div slot="header" class="clearfix">
        <span>学生基本信息</span>
      </div>
      <el-form
        :model="student"
        :rules="rules"
        ref="student"
        label-width="100px"
        class="demo-ruleForm"
      >
        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">个人基本信息</div>
        </div>

        <div class="el-row">
          <div class="el-col el-col-4">
            <!-- 上传照片 -->
            <!-- action: 发送post请求的路径 -->
            <el-form-item label="照片" prop="photo">
              <el-upload
                v-loading="avatarLoading"
                class="avatar-uploader"
                action="http://8.135.93.69:8002/eduoss/fileoss"
                :on-progress="avatarProgress"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img
                  style="width: 100px; height: 100px"
                  v-if="student.photo"
                  :src="student.photo"
                  class="avatar"
                />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="学号" prop="studentNumber" required>
              {{student.studentNumber}}
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="名字" prop="name" required>
              <el-input v-model="student.name"></el-input>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="性别" prop="gender" required>
              <el-radio-group v-model="student.gender">
                <el-radio :label="false">男</el-radio>
                <el-radio :label="true">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <span></span>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="身份证号" prop="identityNumber" required>
              <el-input v-model="student.identityNumber"></el-input>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="出生日期">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="student.birth"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="籍贯" prop="nativeplace" required>
              <el-input v-model="student.nativeplace"></el-input>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="政治面貌" prop="political" required>
              <el-select
                style="width: 100%"
                v-model.number="student.political"
                placeholder="请选择政治面貌"
              >
                <el-option label="中共党员" :value=1></el-option>
                <el-option label="共青团员" :value=2></el-option>
                <el-option label="群众" :value=3></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="联系电话" prop="phone" >
              <el-input v-model.number="student.phone"></el-input>
            </el-form-item>
          </div>
          <div class="el-col el-col-6">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="student.email"></el-input>
            </el-form-item>
          </div>
        </div>

        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">学籍信息</div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-12">
            <el-form-item label="所属系" prop="departmentId" required>
              <el-select style="width: " v-model="student.departmentId" placeholder="请选择系">
                <el-option v-for="item in departmentList" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="el-col el-col-12">
            <el-form-item label="所属专业" prop="majorId" required>
              <el-select style="width: " v-model="student.majorId" placeholder="请选择专业">
                <el-option v-for="item in majorList" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-12">
            <el-form-item label="所属行政班" prop="departmentclassId" required>
              <el-select style="width: " v-model="student.departmentclassId" placeholder="请选择行政班">
                <el-option v-for="item in departmentclassList" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="el-col el-col-12">
            <el-form-item label="入学时间" prop="hiredate" required>
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="student.hiredate"
                style="width: 90%"
              ></el-date-picker>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <div class="bottom clearfix" style="text-align: center; padding-top: 30px">
        <el-button @click="cancelEdit">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate('student')">确 定</el-button>
      </div>
    </el-card>


    <!-- 学生信息卡 -->
    <el-card class="box-card" style="width: 100%" v-if="informationShow">
      <div slot="header" class="clearfix">
        <span>学生基本信息</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="changeEdit">编辑</el-button>
      </div>
      <div class="el-divider el-divider--horizontal">
        <div class="el-divider__text is-center">基本信息</div>
      </div>
      <div class="el-row">
        <div class="el-col el-col-4">
          <div>
            <div class="el-form-item">
              <label class="el-form-item__label">照片:</label>
              <div class="el-form-item__content">
                <div class="demo-image">
                  <div class="block">
                    <div class="demo-image__preview">
                      <el-image
                        style="width: 100px; height: 100px"
                        :src="student.photo"
                        @click="picturePreview('photo')"
                      >
                      </el-image>
                    </div>
                  </div>
                </div>
                <!---->
              </div>
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">学号:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.studentNumber }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">姓名:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.name }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">性别:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.gender == 0 ? "男" : "女" }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">身份证号:</label>
            <div class="el-form-item__content" style="width: 220px">
              <el-label>{{ student.identityNumber }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">出生日期:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.birth }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">籍贯:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.nativeplace }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">政治面貌:</label>
            <div class="el-form-item__content">
              <el-label v-if="student.political == 1">中共党员</el-label>
              <el-label v-else-if="student.political == 2">共青团员</el-label>
              <el-label v-else>群众</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">联系电话:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.phone }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-6">
          <div class="el-form-item">
            <label class="el-form-item__label">邮箱:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.email }}</el-label>
              <!---->
            </div>
          </div>
        </div>
      </div>
      <div class="el-divider el-divider--horizontal">
        <div class="el-divider__text is-center">学籍信息</div>
      </div>
      <div class="el-row">
        <div class="el-col el-col-12">
          <div class="el-form-item">
            <label class="el-form-item__label">所属系:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.departmentName }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-12">
          <div class="el-form-item">
            <label class="el-form-item__label">所属专业:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.majorName }}</el-label>
              <!---->
            </div>
          </div>
        </div>
      </div>
      <div class="el-row">
        <div class="el-col el-col-12">
          <div class="el-form-item">
            <label class="el-form-item__label">所属行政班:</label>
            <div class="el-form-item__content">
              <el-label>{{ student.departmentclassName }}</el-label>
              <!---->
            </div>
          </div>
        </div>
        <div class="el-col el-col-12">
          <div class="el-form-item">
            <label class="el-form-item__label">入职时间:</label>
            <div class="el-form-item__content">
              <el-label>{{ $moment(student.hiredate).format("yyyy-MM-DD") }}</el-label>
              <!---->
            </div>
          </div>
        </div>
      </div>
    </el-card>
    <!-- 图片放大对话框 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import permission from "@/directive/permission/index.js"; // 导入权限判断指令，可用v-permisson
//导出excal功能用的组件
import { parseTime } from "@/utils";
import FilenameOption from "./components/FilenameOption";
import AutoWidthOption from "./components/AutoWidthOption";
import BookTypeOption from "./components/BookTypeOption";

import { mapGetters } from 'vuex'

import studentApi from '@/api/st/student'
import departmentApi from '@/api/te/department'
import departmentclassApi from '@/api/te/departmentclass'
import majorApi from '@/api/te/major'

export default {
  directives: { permission }, //导入组件
  components: { FilenameOption, AutoWidthOption, BookTypeOption },

  computed: {
    ...mapGetters([
      'id','roles','avatar'
    ])
  },

  data() {
    

    //校验身份证号格式
    var checkIdentityNumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error("身份证号不能为空"));
      }
      const res = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(
        value
      );
      if (!res) {
        callback(new Error("请输入正确的身份证号"));
      } else {
        callback();
      }
    };

    //校验电话号码是否为纯数字
    var checkPhone = (rule, value, callback) => {
      if (!value) callback();
      const result = /^\d+$/.test(value); //校验value字符串是否为纯数字
      if (!result) {
        callback(new Error("请输入数字值"));
      } else {
        callback();
      }
    };

    return {


      //////////////////编辑卡、信息卡状态值////////////////////////
      editShow: false,
      informationShow: true,

      student: {
        // id: "11111",
        // student_number: "12345",
        // identity_number: "440902200008200825",
        // name: "叶子豪",
        // gender: 0,
        // political: "1",
        // nativeplace: "广东东莞",
        // birth: "2000-12-03",
        // hiredate: "2020-12-03",
        // title: "校长",
        // phone: "1231234567",
        // email: "yezihao@qq.com",
        // photo:
        //   "https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2975996532,2425031589&fm=26&gp=0.jpg", //照片路径
      },        
      departmentList: [
        // {
        //   id: "11111",
        //   name: "计算机系",
        // },
        // {
        //   id: "22222",
        //   name: "美术系",
        // },
      ],
      majorList: [
        // {
        //   id: "11111",
        //   name: "计算机",
        // },
        // {
        //   id: "22222",
        //   name: "软件工程",
        // },
      ],
      departmentclassList: [
        // {
        //   id: "11111",
        //   name: "18软件工程1班",
        // },
        // {
        //   id: "22222",
        //   name: "18软件工程2班",
        // },
      ],


      dialogEditFormVisible: false, //编辑对话框状态
      dialogInfoFormVisible: false, //详情对话框状态
      dialogVisible: false, //图片放大对话框状态
      dialogImageUrl: "", //图片放大对话框url

      dataQuery: {}, //筛选条件封装对象

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: "student", //文件名
      autoWidth: true,
      bookType: "xlsx",
    };
  },

  created() {
    this.getStudent()
    this.getInit()
  },

  methods: {

    //初始化
    getInit() {
      departmentApi.getAllDepartment()
        .then(res=>{
          this.departmentList = res.data.list
        })
      departmentclassApi.getAllDepartmentclass()
        .then(res=>{
          this.departmentclassList = res.data.list
        })
      majorApi.getAllMajor()
        .then(res=>{
          this.majorList = res.data.list
        })
    },

    //图像放大
    picturePreview(name) {
      if (name == "photo") {
        this.dialogImageUrl = this.student.photo;
      } else if (name == "lecense") {
        this.dialogImageUrl = this.student.lecense;
      }
      this.dialogVisible = true;
    },

    //上传头像成功之后
    handleAvatarSuccess(res, file) {
      this.student.photo = res.data.url
      this.avatarLoading = false
    },
    //上传头像时
      avatarProgress() {
        this.avatarLoading = true
      },
    //上传头像之前
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },



    ////////////编辑按钮切换到编辑卡方法/////////////
    changeEdit(){
        this.editShow = !this.editShow;
        this.informationShow = !this.informationShow;
    },

    ////////////取消按钮切换回信息卡方法/////////////
    cancelEdit(){
        this.$confirm('确认放弃编辑？')
          .then(_ => {
            this.editShow = !this.editShow;
            this.informationShow = !this.informationShow;
          })
          .catch(_ => {
          });
    },

    //获取学生
    getStudent() {
      studentApi.getStudentById(this.id)
        .then(res=>{
          this.student = res.data.student
          this.student.birth = this.student.birth.substring(0,10)
          this.student.hiredate = this.student.hiredate.substring(0,10)
        })

    },

    //模态框点击确定
    saveOrUpdate(student) {
      this.$refs[student].validate((valid) => {
        console.log(student);
        if (valid) {
          //表单前端校验通过
          studentApi.updateStudent(this.student)
            .then(res=>{
              this.$message({
                type: "success",
                message: "编辑成功!",
              });
              this.editShow = !this.editShow;
              this.informationShow = !this.informationShow;
              this.dialogEditFormVisible = false
              this.getStudent()
            })
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style>
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

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}
</style>
