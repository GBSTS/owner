<template>
  <div class="app-container">
    <h2 style="color: darkslateblue">课程班列表</h2>
    <!-- 查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteSeleted()"
            style="margin-right: 10px"
            >批量删除</el-button>

      <el-button
            type="primary"
            icon="el-icon-edit"
            @click="editSelected()"
            style="margin-right: 10px"
            >批量修改</el-button>

      <el-form-item>
        <el-select v-model="dataQuery.courseType" placeholder="课程类型选择">
          <el-option label="公共任选" value="1"></el-option>
          <el-option label="专业限选" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="dataQuery.name"
          suffix-icon="el-icon-search"
          placeholder="课程名"
        />
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataQuery.isConfirm" placeholder="课程班状态">
          <el-option label="未确定" value="false"></el-option>
          <el-option label="已确定" value="true"></el-option>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >

      <el-button
        type="default"
        icon="el-icon-remove-outline"
        @click="resetData()"
        >清空</el-button
      >

      <el-button
        type="success"
        icon="el-icon-circle-plus-outline"
        @click="editForm(null)"
        >添加</el-button
      >



      <el-button
        :loading="downloadLoading"
        type="success"
        icon="el-icon-document"
        @click="handleDownload"
      >
        导出Excel
      </el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>

      <el-table-column prop="name" label="课程" />

      <el-table-column prop="credit" label="学分" width="80" align="center" />

      <el-table-column label="类别" width="100" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.courseType === 1">公共任选</p>
          <p v-else>专业限选</p>
        </template>
      </el-table-column>

      <el-table-column
        label="任课教师"
        width="100"
        align="center"
      >
      <template slot-scope="scope">
          <el-button
            type="text"
            @click="getTeacherInfo(scope.row.teacherId)"
            >{{scope.row.teacherName}}</el-button>
        </template>
      </el-table-column>

      <el-table-column prop="limitNumber" label="限选" width="80" align="center" />

      <el-table-column prop="selected" label="已选" width="80" align="center" />

      <el-table-column
        prop="noneSelected"
        label="未选"
        width="80"
        align="center"
      />

      <el-table-column prop="classTime" label="上课时间" />

      <el-table-column prop="classPlace" label="上课地点" />

      <el-table-column label="状态" width="100" align="center">
        <template slot="header" slot-scope="scope">
          状态
          <el-tooltip placement="top">
            <div slot="content" style="font-size: 14px; color: #ddd">
              <p style="color: deepskyblue; font-size: 16px">[已确定]</p>表示该课程班已分配好学生
              <p style="color: deepskyblue; font-size: 16px">[未确定]</p>表示该课程班未分配好学生，可供学生选课
              <el-divider></el-divider>
              <p>点击以下按钮表示切换状态</p>
            </div>
            <i class="el-icon-warning"></i>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            plain
            v-if="scope.row.isConfirm === false"
            @click="notConfirmClick(scope.row.id)"
            >未确定</el-button
          >

          <el-button
            type="success"
            size="mini"
            plain
            v-else
            @click="confirmClick(scope.row.id)"
            >已确定</el-button
          >
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="editForm(scope.row.id)"
            >修改</el-button
          >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id, scope.row.isConfirm)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-sizes="[5, 10, 15, 20, 50, 100, 1000]"
      :page-size="limitNumber"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="getList"
    />

    <!-- 表单对话框 -->
    <el-dialog title="课程班编辑" :visible.sync="dialogEditFormVisible">
      <el-form
        :model="courseclass"
        :rules="rules"
        ref="courseclass"
        label-width="100px"
        class="demo-ruleForm"
      >
        <!-- 课程远程搜索框 -->
        <el-form-item label="*所属课程" prop="courseId" >
            <el-autocomplete
            v-model="autoCourseName"
            :fetch-suggestions="querySearchCourseAsync"
            :trigger-on-focus="false" 
            placeholder="请输入内容"
          ></el-autocomplete>
        </el-form-item>

        <!-- 任课教师远程搜索框 -->
        <el-form-item label="所属教师" prop="teacherId" >
            <el-autocomplete
            v-model="autoTeacherName"
            :fetch-suggestions="querySearchTeacherAsync"
            :trigger-on-focus="false" 
            placeholder="请输入内容"
          ></el-autocomplete>
        </el-form-item>

        <el-form-item label="限选" prop="limitNumber" required>
          <el-input-number v-if="courseclass.isConfirm==null || courseclass.isConfirm==0" v-model="courseclass.limitNumber" :min="0"></el-input-number>
          <el-input-number v-else v-model="courseclass.limitNumber" :min="0" :disabled="true"></el-input-number>
          <el-tooltip placement="top">
            <div slot="content" style="font-size: 14px; color: #ddd;">
              只有状态为 [未确定] 的课程班才能修改限选人数
            </div>
            <i class="el-icon-warning" style="margin-left: 10px"></i>
          </el-tooltip>
        </el-form-item>

        <el-form-item label="起始周" prop="weekBegin" required>
          <el-input-number v-model.number="courseclass.weekBegin" :min="0" ></el-input-number>
        </el-form-item>  
        <el-form-item label="结束周" prop="weekEnd" required>
          <el-input-number v-model.number="courseclass.weekEnd" :min="0"></el-input-number>
          </el-form-item>  
          <el-form-item label="起始节" prop="dayBegin" required>
          <el-input-number v-model.number="courseclass.dayBegin" :min="0"></el-input-number>
            </el-form-item>  
            <el-form-item label="结束节" prop="dayEnd" required>
          <el-input-number v-model.number="courseclass.dayEnd" :min="0"></el-input-number>
              </el-form-item>

        <el-form-item prop="state" required>      
          <el-radio-group v-model="courseclass.state">
            <el-radio :label="true">单周</el-radio>
            <el-radio :label="false">双周</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上课地点" prop="classPlace">
          <el-input v-model="courseclass.classPlace"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate('courseclass')"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- "未确定"对话框 -->
    <el-dialog title="确定课程班" :visible.sync="dialogConfirmFormVisible">
      <el-form
        :model="confirmData"
        :rules="rules"
        ref="confirmData"
        label-width="100px"
        class="demo-ruleForm"
      >
        <!-- 系下拉列表，v-for实现 -->
        <el-form-item label="系" prop="departmentId" required>
          <el-select v-model="confirmData.departmentId" @change="departmentSelected" placeholder="请选择系">
            <el-option
              v-for="item in departmentList"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 专业下拉列表，v-for实现 -->
        <el-form-item label="专业" prop="majorId" required>
          <el-select v-model="confirmData.majorId" @change="majorSelected" placeholder="请选择专业">
            <el-option
              v-for="item in majorList"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 行政班下拉列表，v-for实现 -->
        <el-form-item label="行政班" prop="departmentclassId" required>
          <el-select
            v-model="confirmData.departmentclassId"
            placeholder="请选择行政班"
          >
            <el-option
              v-for="item in departmentclassList"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogConfirmFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="notConfirmChange('confirmData')"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="教师详情" :visible.sync="dialogInfoFormVisible">
      <el-card class="box-card" style="width: 100%">
        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">基本信息</div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-8">
            <div>
              <div class="el-form-item">
                <label class="el-form-item__label">照片:</label>
                <div class="el-form-item__content">
                  <div class="demo-image">
                    <div class="block">
                      <div class="demo-image__preview">
                        <el-image
                          style="width: 150px; height: 150px;     border: 3px solid #ddd;"
                          :src="teacher.photo"
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
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">姓名:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.name }}</el-label>
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">性别:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.gender == 0 ? "男" : "女" }}</el-label>
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">身份证号:</label>
              <div class="el-form-item__content" style="width: 220px">
                <el-label>{{ teacher.identityNumber }}</el-label>
                <!---->
              </div>
            </div>
          </div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">出生日期:</label>
              <div class="el-form-item__content">
                <!-- <el-label>{{ teacher.birth }}</el-label> -->
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">籍贯:</label>
              <div class="el-form-item__content">
                <!-- <el-label>{{ teacher.nativeplace }}</el-label> -->
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">政治面貌:</label>
              <div class="el-form-item__content">
                <!-- <el-label v-if="teacher.political == 1">中共党员</el-label>
                <el-label v-else-if="teacher.political == 2">共青团员</el-label>
                <el-label v-else>群众</el-label> -->
                <!---->
              </div>
            </div>
          </div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">联系电话:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.phone }}</el-label>
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">邮箱:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.email }}</el-label>
                <!---->
              </div>
            </div>
          </div>
        </div>
        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">教职信息</div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">教职工号:</label>
              <div class="el-form-item__content">
                <el-label>{{teacher.teacherNumber}}</el-label>
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">所属学院:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.collegeName }}</el-label>
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">职称:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.title }}</el-label>
              </div>
            </div>
          </div>
        </div>
        <div class="el-row">
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">入职时间:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.hiredate }}</el-label>
              </div>
            </div>
          </div>
        </div>
        <!-- <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">执照</div>
        </div>
        <div class="demo-image__preview" style="text-align: center">
          <el-image
            style="width: 200px; height: 200px"
            :src="teacher.license"
            @click="picturePreview('license')"
          >
          </el-image>
        </div> -->
      </el-card>
    </el-dialog>
    <!-- 图片放大对话框 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

 <script>
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import courseclassStudentApi from '@/api/st/courseclassStudent'
import courseclassApi from '@/api/te/courseclass'
import courseApi from '@/api/te/course'
import teacherApi from '@/api/te/teacher'
import departmentApi from '@/api/te/department'
import departmentclassApi from '@/api/te/departmentclass'
import majorApi from '@/api/te/major'

//导出excal功能用的组件
import { parseTime } from "@/utils";
import FilenameOption from "./components/FilenameOption";
import AutoWidthOption from "./components/AutoWidthOption";
import BookTypeOption from "./components/BookTypeOption";

export default {
  components: { FilenameOption, AutoWidthOption, BookTypeOption },

  //计算属性，methods中可以通过this.roles得到角色数组
  computed: {
    ...mapGetters([
      'roles'
    ])
  },

  data() {
    var checkCourse = (rule, value, callback) => {
      if(!this.autoCourseName) {
        callback(new Error("请输入所属课程"));
      }
      courseApi.checkCourseByName(this.autoCourseName)
        .then(res=>{
          if(res.data.flag === false) {
            callback(new Error("该课程不存在！"));
          } else {
            this.courseclass.courseId = res.data.id
            callback();
          }
        })
    }

    var checkTeacher = (rule, value, callback) => {
      if(!this.autoTeacherName) {
        callback(new Error("请输入任课教师"));
      }
      teacherApi.checkTeacherByName(this.autoTeacherName)
        .then(res=>{
          if(res.data.flag === false) {
            callback(new Error("该任课教师不存在！"));
          } else {
            this.courseclass.teacherId = res.data.id
            callback();
          }
        })
    }

    //校验结束周>起始周
    var checkWeekEnd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入结束周"));
      }
      if (this.courseclass.weekEnd <= this.courseclass.weekBegin) {
        callback(new Error("结束周不能小于等于起始周"));
      } else {
        callback();
      }
    };

    //校验结束节>起始节
    var checkDayEnd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入结束节"));
      }
      if (this.courseclass.dayEnd <= this.courseclass.dayBegin) {
        callback(new Error("结束节不能小于等于起始节"));
      } else {
        callback();
      }
    };

    return {
      list: [
        // {
        //   id: "11111",
        //   courseName: "大师精讲：我是如何摆脱早泄的",
        //   credit: 30.0,
        //   courseType: 1,
        //   teacherName: "叶子豪",
        //   limitNumber: 50,
        //   selected: 50,
        //   noneSelected: 0,
        //   classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        //   classPlace: "南苑一栋C402",
        //   isConfirm: 1,
        // },
        // {
        //   id: "22222",
        //   courseName: "戒撸，从我开始学起",
        //   credit: 30.0,
        //   courseType: 2,
        //   teacherName: "叶子豪",
        //   limitNumber: 50,
        //   selected: 0,
        //   noneSelected: 50,
        //   classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        //   classPlace: "南苑一栋C402",
        //   isConfirm: 0,
        // },
      ],
      rules: {
        courseId: [
          { validator: checkCourse, trigger: "blur" },
        ],
        teacherId: [
          { validator: checkTeacher, trigger: "blur" },
        ],
        departmentId: [
          { required: true, message: "请选择系", trigger: "change" },
        ],
        limitNumber: [
          { required: true, message: "请选择限选人数", trigger: "change" },
        ],
        majorId: [{ required: true, message: "请选择专业", trigger: "change" }],
        departmentclassId: [
          { required: true, message: "请选择行政班", trigger: "change" },
        ],
        weekBegin: [
          { required: true, message: "请选择起始周", trigger: "change" },
        ],
        weekEnd: [
          {validator: checkWeekEnd, trigger: 'change'}
        ],
        dayBegin: [
          { required: true, message: "请选择起始节", trigger: "change" },
        ],
        dayEnd: [
          {validator: checkDayEnd, trigger: 'change'}
        ],
        state: [
          { required: true, message: "请选择单/双周", trigger: "change" },
        ],
      },
      courseclass: {
        // id: "11111",
        // courseId: '',
        // teacherId: '',
        // limitNumber: 50,
        // weekBegin: 1,
        // weekEnd: 16,
        // dayBegin: 1,
        // dayEnd: 2,
        // state: 0,
        // classPlace: "南苑一栋C402",
      },
      teacher: {},
      dialogImageUrl: '',
      dialogVisible: false,

      //分页用的data
      page: 1, //当前页
      limitNumber: 10, //每页记录数
      total: 0, //总记录数

      dialogEditFormVisible: false, //修改对话框状态
      dialogConfirmFormVisible: false, //"未确定"对话框状态
      dialogInfoFormVisible: false,

      dataQuery: {}, //筛选条件封装对象

      //"未确定"对话框用的data
      confirmData: {}, //未确定对话框封装对象
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
      ],

      //修改对话框用的data
      teacherList: [
        // {
        //   id: '11111',
        //   name: '叶子豪'
        // },
        // {
        //   id: '22222',
        //   name: '小叶子'
        // }
      ],

      courseList: [
        // {
        //   id: '11111',
        //   name: '大师精讲：我是如何摆脱早泄的'
        // },
        // {
        //   id: '22222',
        //   name: '戒撸，从我开始学起'
        // }
      ],
      autoCourseName: '',
      autoTeacherName: '',

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: "课程班列表", //文件名
      autoWidth: true,
      bookType: "xlsx",
    };
  },

  created() {
    this.getList()
    this.getInit()
  },

  methods: {
    //初始化
    getInit() {
      teacherApi.getAllTeacher()
        .then(res=>{
          this.teacherList = res.data.list
        })
      departmentApi.getAllDepartment()
      .then(res=>{
        this.departmentList = res.data.list
      })
      courseApi.getAllCourse()
        .then(res=>{
          this.courseList = res.data.list
        })
    },

    //系选择后获取专业
    departmentSelected(value) {
      majorApi.getAllMajorByDepartmentId(value)
        .then(res=>{
          this.majorList = res.data.list
        })
    },

    //专业选择后获取行政班
    majorSelected(value) {
      departmentclassApi.getAllDepartmentclassByMajorId(value)
        .then(res=>{
          this.departmentclassList = res.data.list
        })
    },

    //图像放大
    picturePreview(name) {
      if(name == 'photo') {
        this.dialogImageUrl = this.teacher.photo
      } else if(name == 'license') {
        this.dialogImageUrl = this.teacher.license
      }
      this.dialogVisible = true
    },

    //获取教师详细信息
    getTeacherInfo(id) {
      teacherApi.getTeacherById(id)
          .then(res=>{
            this.teacher = res.data.teacher
            if(this.teacher.hiredate) this.teacher.hiredate = this.$moment(this.teacher.hiredate).format('YYYY-MM-DD')
            if(this.teacher.birth) this.teacher.birth = this.$moment(this.teacher.birth).format('YYYY-MM-DD')
            this.dialogInfoFormVisible = true
          })
    },

    //搜索框动态生成教师
    querySearchTeacherAsync(queryString, cb) {
      teacherApi.getTeacherByName(queryString)
        .then(res=>{
          var list = res.data.list;
          var nameList = [];
          for(let item of list) {
            nameList.push({ value: item.name});
          }
              cb(nameList);
        })
    },

    //搜索框动态生成课程
    querySearchCourseAsync(queryString, cb) {
      courseApi.getCourseByName(queryString)
        .then(res=>{
          var list = res.data.list;
          var nameList = [];
          for(let item of list) {
            nameList.push({ value: item.name});
          }
              cb(nameList);
        })
    },

    //"未确定"改变状态
    notConfirmChange(confirmData) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      this.$refs[confirmData].validate((valid) => {
        console.log(this.confirmData);
        if (valid) {
          //表单前端校验通过
          var id = this.confirmData.courseclassId;
          var departmentclassId = this.confirmData.departmentclassId;
          //根据这两个id给中间表添加字段，再修改状态为已确定
          courseclassStudentApi.saveDepartmentclassStudent(departmentclassId,id)
            .then(res=>{
              this.$message({
                type: "success",
                message: "修改成功",
              });
              this.getList()
              this.dialogConfirmFormVisible = false;
              this.confirmData = {};
            })
        } else {
          return false;
        }
      });
    },

    //未确定点击
    notConfirmClick(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      this.confirmData = {};
      this.majorList = {};
      this.departmentclassList = {};
      this.confirmData.courseclassId = id;
      this.dialogConfirmFormVisible = true;
    },

    //确定点击
    confirmClick(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      this.$confirm(
        "是否将该课程班状态更改为“未确定”？若执行此操作，将会清除所有该课程班与学生之间的信息！",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        //根据id在中间表删除所有记录
        courseclassStudentApi.delByCourseclassId(id)
          .then(res=>{
            this.$message({
              type: "success",
              message: "修改成功!",
            });
            this.getList()
          })
      });
    },

    //下载xlsx
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = [
          "课程",
          "学分",
          "类别",
          "任课教师",
          "限选",
          "已选",
          "未选",
          "上课时间",
          "上课地点",
        ]; //表头名称
        const filterVal = [
          "courseName",
          "credit",
          "courseType",
          "teacherName",
          "limitNumber",
          "selected",
          "noneSelected",
          "classTime",
          "classPlace"
        ];
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
      //j表示list里的属性名，v[j]表示该属性对应的值
      console.log(filterVal)
      console.log(jsonData)
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === "courseType") {
            if (v[j] == 1) v[j] = "公共任选";
            else v[j] = "专业限选";
            return v[j];
          }
          return v[j]
        }))
    },

    //批量删除
    deleteSeleted() {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      var idList = new Array();
      for (var i in this.multipleSelection) {
        idList.push(this.multipleSelection[i].id);
        if(this.multipleSelection[i].isConfirm == 1) {
          this.$message({
            type: "error",
            message: "所选课程班中包含 [已确定] 状态！"
          })
          return false
        }
      }
      courseclassApi.delCourseclassByIdList(idList)
        .then(res=>{
          this.$message({
            type: "success",
            message: "删除成功！"
          })
          this.getList()
        })
    },

    //批量修改
    editSelected() {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      var idList = new Array();
      for (var i in this.multipleSelection) {
        idList.push(this.multipleSelection[i].id);
        if(this.multipleSelection[i].isConfirm == false) {
          this.$message({
            type: "error",
            message: "所选课程班中包含 [未确定] 状态！"
          })
          return false
        }
      }
      // alert("修改了" + idList);
      courseclassStudentApi.delByCourseclassIdList(idList)
        .then(res=>{
          this.$message({
              type: "success",
              message: "修改成功",
            });
            this.getList()
        })

    },

    //多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    //清空按钮点击
    resetData() {
      this.dataQuery = {};
    },

    //添加/修改按钮点击
    editForm(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      this.autoCourseName = '';
      this.autoTeacherName = '';
      if (id == null) {
        this.courseclass = {};
        this.courseclass.courseId = ''
        this.courseclass.teacherId = ''
      } else {
        courseclassApi.getCourseclassById(id)
          .then(res=>{
            this.courseclass = res.data.courseclass
          })
      }
      this.dialogEditFormVisible = true;
    },

    //删除按钮点击
    removeDataById(id, isConfirm) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      if(isConfirm == 1) {
        this.$message({
          type: "error",
          message: "无法删除状态为 [已确定] 课程班！"
        })
        return false
      }

      this.$confirm("此操作将永久删除记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //点击确定，执行then方法
        //提示信息
        courseclassApi.delCourseclassById(id)
          .then(res=>{
            this.$message({
              type: "success",
              message: "删除成功!"
            });
          })
          this.getList()
      });
    },

    //获取所有数据
    getList() {
      console.log(this.dataQuery)
      courseclassApi.pageList(this.page,this.limitNumber,this.dataQuery)
        .then(res=>{
          console.log(res)
          this.list = res.data.list
          this.total = res.data.total
        })
    },

    //模态框点击确定
    saveOrUpdate(courseclass) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher' && role != 'department') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      this.$refs[courseclass].validate((valid) => {
        console.log(this.courseclass);
        //表单前端校验通过
        if (valid) {
          //添加
          if(this.courseclass.id == null || this.courseclass.id == "") {
            courseclassApi.saveCourseclass(this.courseclass)
              .then(res=>{
                this.$message({
                  type: "success",
                  message: "添加成功!",
                });
                this.dialogEditFormVisible = false;
                this.getList()
              })
          } 
          //修改
          else {
            courseclassApi.updateCourseclass(this.courseclass)
              .then(res=>{
                this.$message({
                  type: "success",
                  message: "编辑成功!",
                });
                this.dialogEditFormVisible = false;
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
</style>
