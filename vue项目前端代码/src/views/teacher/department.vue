<template>
  <div class="app-container">
    <h2 style="color: darkslateblue">系科列表</h2>
    <!-- 查询表单，表单用v-model双向绑定-->
    <el-form :inline="true" class="demo-form-inline">
      <el-button
        type="danger"
        icon="el-icon-delete"
        @click="deleteSeleted()"
        v-permission="['admin']"
        style="margin-right: 10px"
        >批量删除</el-button
      >

      <el-form-item>
        <el-input v-model="dataQuery.name" placeholder="系科名" />
      </el-form-item>

      <el-form-item label="所属学院">
        <el-select v-model="dataQuery.collegeId" placeholder="请选择学院">
          <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option> 
        </el-select>
      </el-form-item>

      <el-button
        style="margin-bottom: 20px"
        type="primary"
        icon="el-icon-search"
        @click="getList()"
        >查询</el-button
      >
      <el-button
        style="margin-bottom: 20px"
        type="default"
        icon="el-icon-remove-outline"
        @click="resetData()"
        >清空</el-button
      >

      <el-button
        style="margin-bottom: 20px"
        type="success"
        icon="el-icon-circle-plus-outline"
        @click="editForm(id)"
        >添加</el-button
      >

      <el-button
        style="margin-bottom: 20px"
        :loading="downloadLoading"
        type="primary"
        icon="el-icon-document"
        @click="handleDownload"
      >
        导出Excel
      </el-button>
    </el-form>

    <!-- 表格 -->
    <!-- 在table里定义:data=list后，后面的column的prop都直接写list里的属性，不用list. -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>

      <el-table-column label="序号" width="70" align="center">
        <!-- 这里用插槽，因为序号要自定义值 -->
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="系科名" />

      <el-table-column prop="collegeName" label="所属学院" />

      <el-table-column prop="gmtCreate" label="创建时间" />

      <el-table-column prop="gmtModified" label="更新时间" />

      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button icon="el-icon-more" size="mini" @click="infoClicked(scope.row.id)"
            >详情</el-button
          >

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="editForm(scope.row.id)"
            >修改</el-button
          >
          <!-- 这里绑定一个删除事件，将传该行id -->
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page.sync="page"
      :page-sizes="[5, 10, 15, 20, 50, 100, 1000]"
      :page-size.sync="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="getList"
      @size-change="getList"
    />

    <!-- 表单对话框 -->
    <el-dialog title="系科编辑" :visible.sync="dialogEditFormVisible">
      <el-card class="box-card" style="width: 100%">
        <el-form
          :model="department"
          :rules="rules"
          ref="department"
          label-width="100px"
          class="demo-ruleForm"
        >
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">系科简介</div>
          </div>
          <div class="el-row">
            <div class="el-col">
              <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入内容"
                v-model="department.introduction"
              >
              </el-input>
            </div>
          </div>
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">系科信息</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="系科名称" prop="name">
                <el-input v-model.name="department.name" style="width: 80%"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="所属学院" prop="collegeId">
                <el-select style="width: " v-model="department.collegeId" placeholder="请选择学院">
                  <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="建立时间" prop="gmtCreate">
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                  v-model="department.gmtCreate"
                  style="width: 80%"
                ></el-date-picker>
              </el-form-item>
            </div>
            <div class="el-col el-col-12">
              <el-form-item label="更新时间" prop="gmtModified">
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                  v-model="department.gmtModified"
                  style="width: 80%"
                ></el-date-picker>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate('department')">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="系科详情" :visible.sync="dialogInfoFormVisible">
      <el-card class="box-card" style="width: 100%" :body-style="{ padding: '0px' }">
        <h2 style="text-align: center">{{ department.name }}</h2>
        <div style="padding: 0px 20px 20px 20px">
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">系科简介</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-24" style="text-indent: 2em">
              {{ department.introduction }}
            </div>
          </div>
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">系科信息</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">系科名称:</label>
                <div class="el-form-item__content">
                  <el-label>{{ department.name }}</el-label>
                </div>
              </div>
            </div>
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">所属学院:</label>
                <div class="el-form-item__content">
                  <el-label>{{ department.collegeName }}</el-label>
                </div>
              </div>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">建立时间:</label>
                <div class="el-form-item__content">
                  <el-label>{{ department.gmtCreate }}</el-label>
                </div>
              </div>
            </div>
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">更新时间:</label>
                <div class="el-form-item__content">
                  <el-label>{{ department.gmtModified }}</el-label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </el-dialog>
    <!-- 图片放大对话框 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
 import collegeApi from '@/api/te/college'
  import departmentApi from '@/api/te/department'
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import permission from "@/directive/permission/index.js"; // 导入权限判断指令，可用v-permisson
//导出excal功能用的组件
import { parseTime } from "@/utils";
import FilenameOption from "./components/FilenameOption";
import AutoWidthOption from "./components/AutoWidthOption";
import BookTypeOption from "./components/BookTypeOption";

export default {
  directives: { permission }, //导入组件
  components: { FilenameOption, AutoWidthOption, BookTypeOption },
  //计算属性，methods中可以通过this.roles得到角色数组
  computed: {
    ...mapGetters([
      'roles'
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
      list: [
        {
          id: "11111",
          name: "赛博朋克系科",
          collegeName: "赛博朋克学院",
          gmtCreate: "2077-12-10",
          gmtModified: "2020-12-13",
        }
      ],

      rules: {
        name: [
          { required: true, message: "请输入系科名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20个字符",
            trigger: "blur",
          },
        ],
        collegeId: [{ required: true, message: "请选择所属学院", trigger: "change" }],
        gmtCreate: [
          {
            
            required: true,
            message: "请选择创建时间",
            trigger: "change",
          },
        ],
        gmtModified: [
          {
            
            required: true,
            message: "请选择更新时间",
            trigger: "change",
          },
        ],
      },

      department: {
      },
            collegeList: [
      ],
      //分页用的data
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数

      dialogEditFormVisible: false, //编辑对话框状态
      dialogInfoFormVisible: false, //详情对话框状态
      dialogVisible: false, //图片放大对话框状态
      dialogImageUrl: "", //图片放大对话框url

      dataQuery: {}, //筛选条件封装对象

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: "department", //文件名
      autoWidth: true,
      bookType: "xlsx",
    };
  },

  created() {
          this.getList()
          this.getCollegeList();
  },

  methods: {
    //下载xlsx
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = ["系科名", "所属学院", "创建时间", "更新时间"]; //表头名称
        const filterVal = ["name", "collegeName", "gmtCreate", "gmtModified"];
        const list = this.list;
        const data = this.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType,
        });
        this.downloadLoading = false;
      });
    },

    //转换格式
    formatJson(filterVal, jsonData) {
      //j表示list里的属性名，v[j]表示该属性对应的值。此处gender和political值是数字，要转换成对应字符串
      return jsonData.map((v) =>
        filterVal.map((j) => {
          if (j === "hiredate") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },

    //图像放大
    picturePreview(name) {
      if (name == "photo") {
        this.dialogImageUrl = this.department.photo;
      } else if (name == "lecense") {
        this.dialogImageUrl = this.department.lecense;
      }
      this.dialogVisible = true;
    },

    //上传头像成功之后
    handleAvatarSuccess(res, file) {
      this.department.photo = URL.createObjectURL(file.raw);
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

    //详情按钮点击.
    infoClicked(id) {
      console.log(id)
      departmentApi.getDepartmentById(id)
          .then(res=>{
            this.department = res.data.department
            this.dialogInfoFormVisible = true
          })
          //this.college=xxx 从后端根据id取对象
    },

          //批量删除
      deleteSeleted() {
        var role = this.roles[0] //得到角色字符串
        //系的批量删除只有admin，教师系统超级管理员，系管理员有权限操作
        if(role == 'admin' || role == 'admin_teacher' || role == 'school'|| role == 'college') {
          this.$confirm("此操作将永久删除记录, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            //点击确定，执行then方法
            //提示信息
            var idList = new Array()
            for(var i in this.multipleSelection){
                  idList.push(this.multipleSelection[i].id)
            }
            departmentApi.delDepartmentsByList(idList)
              .then(res=>{
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
                if(this.total%this.limit == idList.length && this.page > 1) this.page--
                this.getList()
              })
          });
          
        } else {
          this.$message({
            type: "error",
            message: "您没有此权限！"
          });
        }
      },

    //多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    //清空按钮点击
    resetData() {
      this.dataQuery = {};
    },
     //得到所有院
    getCollegeList() {
    //得到所有院的list
      collegeApi.getList()
        .then(res=>{
          this.collegeList = res.data.list
        })
    },


    //添加/修改按钮点击
    editForm(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'school' && role != 'college') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      
      if(id == null) {
        this.department = {}
      } else {
        //得到teacherVO
          departmentApi.getDepartmentById(id)
            .then(res=>{
              this.department = res.data.department
            })
      }
      this.dialogEditFormVisible = true;
    },
    removeDataById(id) {
      console.log(id)
      var role = this.roles[0]
      if(role == 'admin' || role == 'admin_teacher' || role == 'school'|| role == 'college') {
        this.$confirm("此操作将永久删除记录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          //点击确定，执行then方法
          //提示信息
          departmentApi.delDepartmentById(id)
            .then(res=>{
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              if(this.total%this.limit == 1 && this.page > 1) this.page--
              this.getList()
            })
        });
      } else {
        this.$message({
            type: "error",
            message: "您没有此权限！",
          });
      }
    },

   //获取所有数据
    getList() {
      console.log(this.page)
      console.log(this.limit)
      console.log(this.dataQuery)
      departmentApi.pageList(this.page,this.limit,this.dataQuery)
        .then(res=>{
          console.log(res)
          this.list = res.data.list
          this.total = res.data.total
        })
    },

          //模态框点击确定
    saveOrUpdate(department) {
      const _this=this
      var is=0;
      var _is=0;
      this.$refs[department].validate((valid) => {
        if (valid) {
          //表单前端校验通过
          console.log(this.department)
          if(this.department.id == null || this.department.id == "") {
            for(var i in  _this.list)
            {
              if(_this.list[i].name==this.department.name)
              {
                is=1
              }
              if(this.department.gmtCreate>this.department.gmtModified)
              {
                _is=1
              }
            }
        if(_is)
        {
          this.$message({
          message: '建立时间必须小于或等于更新时间',
          type: 'warning'
        });
        }
        if(is)
            {
          this.$message({
          message: '该学院已存在',
          type: 'warning'
        });
            }
            
            if(!is&&!_is)
            {
             departmentApi.saveDepartment(this.department)
              .then(res=>{
                  this.$message({
                  type: "success",
                  message: "添加成功!",
                });
              this.dialogEditFormVisible = false
              this.getList()
              })
            }
          } else{
                        for(var i in  _this.list)
            {

              if(this.department.gmtCreate>this.department.gmtModified)
              {
                _is=1
              }
            }
        if(_is)
        {
          this.$message({
          message: '建立时间必须小于或等于更新时间',
          type: 'warning'
        });
        }
            
            if(!_is)
            {
            departmentApi.updateDepartment(this.department)
            .then(res=>{
              this.$message({
                type: "success",
                message: "编辑成功!",
              });
              this.dialogEditFormVisible = false
              this.getList()
            }) 
            } 
          }
          
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style>
.el-upload--text {
  width: 100%;
  height: 100px;
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
  width: 100%;
  height: 100px;
  line-height: 100px;
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
