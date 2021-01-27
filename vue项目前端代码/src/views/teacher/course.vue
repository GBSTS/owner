<template>
  <div class="app-container">
    <h2 style="color: darkslateblue">课程列表</h2>
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
        <el-input v-model="dataQuery.name" placeholder="课程名" />
      </el-form-item>

       <el-form-item label="所属学院">
        <el-select v-model="dataQuery.collegeId" placeholder="请选择学院">
          <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option> 
        </el-select>
      </el-form-item>
  <el-form-item>

      <el-select v-model="dataQuery.courseType" placeholder="课程类型选择">
          <el-option label="公共任选" value="1"></el-option>
          <el-option label="专业限选" value="2"></el-option>
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

      <el-table-column prop="name" label="课程名" />

      <el-table-column prop="collegeName" label="所属学院" />

     <el-table-column label="课程类别"  align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.courseType === 1">公共任选</p>
          <p v-else>专业限选</p>
        </template>
      </el-table-column>

      <el-table-column prop="credit" label="学分"></el-table-column>

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

    <el-dialog title="课程编辑" :visible.sync="dialogEditFormVisible">
      <el-card class="box-card" style="width: 100%">
        <el-form
          :model="course"
          :rules="rules"
          ref="course"
          label-position="right"
          label-width="100px"
          class="demo-ruleForm"
        >
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">课程简介</div>
          </div>

          <div class="el-row">
            <div class="el-col">
              <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入内容"
                v-model="course.introduction"
              >
              </el-input>
            </div>
          </div>
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">课程信息</div>
          </div>
          <el-form-item label="课程名" prop="name" class="el-col el-col-12">
            <el-input v-model="course.name" placeholder="请输入课程名"></el-input>
          </el-form-item>
          <el-form-item label="所属学院" prop="collegeId" class="el-col el-col-12">
                <el-select style="width: " v-model="course.collegeId" placeholder="请选择学院">
                  <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
          <el-form-item label="课程学分" prop="credit" class="el-col el-col-12">
            <el-input
              v-model.number="course.credit"
              placeholder="请输入课程学分"
            ></el-input>
          </el-form-item>
                   <el-form-item label="课程类型" prop="courseType" class="el-col el-col-12">
                <el-select style="width: " v-model="course.courseType" placeholder="请选择课程类型">
                  <el-option label="公共任选" :value="1"></el-option>
          <el-option label="专业限选" :value="2"></el-option>
                </el-select>
              </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate('course')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="课程详情" :visible.sync="dialogInfoFormVisible">
      <el-card class="box-card" style="width: 100%" :body-style="{ padding: '0px' }">
        <h2 style="text-align: center">{{ course.name }}</h2>
        <div style="padding: 0px 20px 20px 20px">
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">课程简介</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-24" style="text-indent: 2em">
              {{ course.introduction }}
            </div>
          </div>
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">课程信息</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">课程名:</label>
                <div class="el-form-item__content">
                  <el-label>{{ course.name }}</el-label>
                </div>
              </div>
            </div>
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">所属学院:</label>
                <div class="el-form-item__content">
                  <el-label>{{ course.collegeName }}</el-label>
                </div>
              </div>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">课程类型:</label>
                <div class="el-form-item__content">
                  <el-label>{{course.courseType === 1 ? "公共任选" : "专业限选"  }}</el-label>
                </div>
              </div>
            </div>
            <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">课程学分:</label>
                <div class="el-form-item__content">
                  <el-label>{{ course.credit }}</el-label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
 import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
 import courseApi from '@/api/te/course'
import collegeApi from '@/api/te/college'
import permission from "@/directive/permission/index.js"; // 导入权限判断指令，可用v-permisson
//导出excal功能用的组件
import { parseTime } from "@/utils";
import FilenameOption from "./components/FilenameOption";
import AutoWidthOption from "./components/AutoWidthOption";
import BookTypeOption from "./components/BookTypeOption";

export default {
  directives: {
    permission,
  }, //导入组件
  components: {
    FilenameOption,
    AutoWidthOption,
    BookTypeOption,
  },
  //计算属性，methods中可以通过this.roles得到角色数组
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  data() {
    //学分判断
    var checkCreate = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("学分不能为空"));
      }
      if (!Number.isInteger(value)) {
        callback(new Error("请输入数字"));
      } else {
        if (value > 0 && value <= 12) {
          callback();
        } else {
          callback(new Error("学分必须大于0小于等于12"));
        }
      }
    };

    return {
      list: [],

      ruleForm: {
        name: "",
        type: "",
        college: "",
        create: "",
      },

      course: {
      },

      rules: {
        name: [
          {
            required: true,
            message: "请输入课程名",
          },
          {
            min: 1,
            max: 20,
            message: "长度在1到20个字符",
            trigger: "blur",
          },
        ],
        type: [
          {
            required: true,
            message: "请选择课程类型",
            trigger: "blur",
          },
        ],
        college: [
          {
            required: true,
            message: "请选择所属学院",
            trigger: "blur",
          },
        ],
        create: [
          {
            validator: checkCreate,
            trigger: "blur",
          },
        ],
      },

      collegeList: [],

      //分页用的data
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数

      dialogEditFormVisible: false, //编辑对话框状态
      dialogInfoFormVisible: false, //详情对话框状态

      dataQuery: {}, //筛选条件封装对象

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: "course", //文件名
      autoWidth: true,
      bookType: "xlsx",
    };
  },
  created() {
    this.getList()
    this.getCollegeList()
  },
  methods: {
    //下载xlsx
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = ["课程名", "所属学院", "课程类型", "课程学分"]; //表头名称
        const filterVal = ["name", "collegeName", "courseType", "credit"];
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
      return jsonData.map((v) => filterVal.map((j) => {
        return v[j];
      }));
    },

   //详情按钮点击.
    infoClicked(id) {
      console.log(id)
      courseApi.geCourseById(id)
          .then(res=>{
            this.course = res.data.course
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
            courseApi.delCoursesByList(idList)
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
    			        //得到所有院
    getCollegeList() {
    //得到所有院的list
      collegeApi.getList()
        .then(res=>{
          this.collegeList = res.data.list
        })
    },

    //清空按钮点击
    resetData() {
      this.dataQuery = {};
    },

    //添加/修改按钮点击
    editForm(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'school'&& role != 'college') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      
      if(id == null) {
        this.course = {}
      } else {
        //得到teacherVO
          courseApi.geCourseById(id)
            .then(res=>{
              this.course = res.data.course
            })
      }
      this.dialogEditFormVisible = true;
    },

    //删除按钮点击
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
          courseApi.delCourseById(id)
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
      console.log(this)
      courseApi.pageList(this.page,this.limit,this.dataQuery)
        .then(res=>{
          this.list = res.data.list
          this.total = res.data.total
        })
    },

          //模态框点击确定
    saveOrUpdate(course) {
      const _this=this
      var is=0;
      var _is=0;
      this.$refs[course].validate((valid) => {
        if (valid) {
          //表单前端校验通过
          console.log(this.course)
          if(this.course.id == null || this.course.id == "") {
            for(var i in  _this.list)
            {
              if(_this.list[i].name==this.course.name)
              {
                is=1
              }
            }
        if(is)
            {
          this.$message({
          message: '该学院已存在',
          type: 'warning'
        });
            }
            
            if(!is)
            {
             courseApi.saveCourse(this.course)
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
            courseApi.updateCourse(this.course)
            .then(res=>{
              this.$message({
                type: "success",
                message: "编辑成功!",
              });
              this.dialogEditFormVisible = false
              this.getList()
            })  
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
.select {
  width: 100%;
}
</style>
