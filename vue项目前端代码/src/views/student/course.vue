<template>
  <div class="app-container">
    
    <div class="block" v-permission="['admin','admin_student']">
      <el-date-picker
        v-if="flag == 0 || flag == 3"
        v-model="selectTime"
        type="datetimerange"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :default-time="['12:00:00']">
      </el-date-picker>

      <el-button 
      type="success" 
      plain 
      v-if="flag == 0 || flag == 3"
      @click="startSelect()"
      >
      启动选课任务</el-button>
      <el-button 
      type="danger" 
      plain 
      v-if="flag == 1"
      @click="closeSelect()"
      >取消选课任务</el-button>
    </div>

    <el-alert
      style="height: 60px; font-size: 20px !important;"
      v-if="flag == 1"
      :title='curTimeDisplay'
      type="success"
    >
    </el-alert>

    <el-alert
      style="height: 60px; font-size: 20px !important;"
      v-else
      :title='curTimeDisplay'
      type="error"
    >
    </el-alert>

    <el-tabs 
    v-model="activeName" type="border-card" @tab-click="handleClick"
    v-permission="['student']"
    >
      <!-- 选课总览 -->
      <el-tab-pane label="选课总览" name="first">
        <!-- 查询表单-->
        <el-form :inline="true" class="demo-form-inline">
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

          <el-button type="primary" icon="el-icon-search" @click="getList()"
            >查询</el-button
          >

          <el-button type="default" icon="el-icon-remove-outline" @click="resetData()"
            >清空</el-button
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
          <el-table-column prop="courseName" label="课程" />

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

          <el-table-column prop="noneSelected" label="未选" width="80" align="center" />

          <el-table-column prop="classTime" label="上课时间" />

          <el-table-column prop="classPlace" label="上课地点" />

          <el-table-column label="状态" width="70" align="center">
            <template slot-scope="scope">
              <el-tag type="danger" v-if="scope.row.state === 0">未选</el-tag>
              <el-tag type="success" v-else-if="scope.row.state === 1">已选</el-tag>
              <el-tag type="info" v-else>退选</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="70" align="center">
            <template slot-scope="scope">
              <!-- 未选或退选-》选课   已选-》退选 -->
              <el-button
                v-if="scope.row.state === 0 || scope.row.state === 2"
                type="text"
                style="color: #67c23a"
                @click="selectClick(scope.row.id)"
                >选课</el-button
              >
              <el-button
                v-else
                type="text"
                style="color: #f56c6c"
                @click="removeDataById(scope.row.id)"
                >退选</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 任选结果 -->
      <el-tab-pane label="任选结果" name="second">
        <!-- 查询表单-->
        <el-form :inline="true" class="demo-form-inline" style="margin-bottom: 20px">
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
          :data="selectedList"
          border
          fit
          highlight-current-row
          @selection-change="handleSelectionChange"
        >
          <el-table-column prop="courseName" label="课程" />

          <el-table-column prop="credit" label="学分" width="80" align="center" />

          <el-table-column label="类别" width="100" align="center">
            <template slot-scope="scope">
              <p v-if="scope.row.courseType == 1">公共任选</p>
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

          <el-table-column prop="noneSelected" label="未选" width="80" align="center" />

          <el-table-column prop="classTime" label="上课时间" />

          <el-table-column prop="classPlace" label="上课地点" />
        </el-table>
      </el-tab-pane>

      <!-- 退选 -->
      <el-tab-pane label="退选" name="third">
        <el-form :inline="true" class="demo-form-inline" style="margin-bottom: 20px">
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
          :data="quitList"
          border
          fit
          highlight-current-row
          @selection-change="handleSelectionChange"
        >
          <el-table-column prop="courseName" label="课程" />

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

          <el-table-column prop="noneSelected" label="未选" width="80" align="center" />

          <el-table-column prop="classTime" label="上课时间" />

          <el-table-column prop="classPlace" label="上课地点" />
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!-- 选课对话框 -->
    <el-dialog title="课程" :visible.sync="dialogInfoFormVisible">
      <el-card class="box-card" style="width: 100%">
        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">课程信息</div>
        </div>
        <el-form label-width="100px" class="demo-ruleForm">
          <div class="el-row">
            <el-form-item label="课程名">
              <el-label>{{ courseclass.courseName }}</el-label>
            </el-form-item>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="学分">
                <el-label>{{ courseclass.credit }}</el-label>
              </el-form-item>
            </div>
            <div class="el-col el-col-12">
              <el-form-item label="类别">
                <el-label>{{
                  courseclass.courseType == 0 ? "公共任选" : "专业限选"
                }}</el-label>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="任课老师">
                <el-label>{{ courseclass.teacherName }}</el-label>
              </el-form-item>
            </div>
            <div class="el-col el-col-12">
              <el-form-item label="限选">
                <el-label>{{ courseclass.limitNumber }}</el-label>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="已选">
                <el-label>{{ courseclass.selected }}</el-label>
              </el-form-item>
            </div>
            <div class="el-col el-col-12">
              <el-form-item label="未选">
                <el-label>{{ courseclass.noneSelected }}</el-label>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-12">
              <el-form-item label="上课时间">
                <el-label>{{ courseclass.classTime }}</el-label>
              </el-form-item>
            </div>
            <div class="el-col el-col-12">
              <el-form-item label="上课地点">
                <el-label>{{ courseclass.classPlace }}</el-label>
              </el-form-item>
            </div>
          </div>
          <el-form-item label="状态">
            <el-label v-if="courseclass.state == 0">未选</el-label>
            <el-label v-else-if="courseclass.state == 1">已选</el-label>
            <el-label v-else>退选</el-label>
          </el-form-item>
          <el-alert
            style="height: 60px; font-size: 20px !important;"
            title="选课时请事先保证此课程时间和其他已选课程不冲突！"
            type="error"
          >
          </el-alert>
        </el-form>
      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogInfoFormVisible = false">取 消</el-button>
        <el-button type="success" @click="saveOrUpdate('courseclass')"
          >确认选课</el-button
        >
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="教师详情" :visible.sync="dialogTeacherInfoFormVisible">
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
                <!-- <el-label>{{ teacher.identityNumber }}</el-label> -->
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
import permission from "@/directive/permission/index.js"; // 导入权限判断指令，可用v-permisson
//导出excal功能用的组件
import { parseTime } from "@/utils";
import FilenameOption from "./components/FilenameOption";
import AutoWidthOption from "./components/AutoWidthOption";
import BookTypeOption from "./components/BookTypeOption";

import courseSelectApi from '@/api/st/courseSelect'
import courseclassApi from '@/api/te/courseclass'
import teacherApi from '@/api/te/teacher'

export default {
directives: { permission }, //导入组件
components: { FilenameOption, AutoWidthOption, BookTypeOption },

  //计算属性，methods中可以通过this.roles得到角色数组
  computed: {
    ...mapGetters([
      'id','roles'
    ])
  },

  data() {
    return {
      list: [
        // {
        //   id: "11111",
        //   courseName: "大师精讲：我是如何摆脱早泄的",
        //   credit: 30.0,
        //   courseType: 1,
        //   teacherName: "叶子豪",
        //   limit: 50,
        //   selected: 50,
        //   noneSelected: 0,
        //   classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        //   classPlace: "南苑一栋C402",
        //   state: 0
        // },
        // {
        //   id: "22222",
        //   courseName: "戒撸，从我开始学起",
        //   credit: 30.0,
        //   courseType: 2,
        //   teacherName: "叶子豪",
        //   limit: 50,
        //   selected: 0,
        //   noneSelected: 50,
        //   classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        //   classPlace: "南苑一栋C402",
        //   state: 1
        // },
        // {
        //   id: "33333",
        //   courseName: "经验访谈：男生的那点生理疾病，你了解过吗？",
        //   credit: 30.0,
        //   courseType: 2,
        //   teacherName: "叶子豪",
        //   limit: 50,
        //   selected: 0,
        //   noneSelected: 50,
        //   classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        //   classPlace: "南苑一栋C402",
        //   state: 2
        // },
      ],

      selectedList: [],

      quitList: [],
      
      courseclass: {
        // id: "11111",
        // courseName: "大师精讲：我是如何摆脱早泄的",
        // credit: 30.0,
        // courseType: 1,
        // teacherName: "叶子豪",
        // limit: 50,
        // selected: 50,
        // noneSelected: 0,
        // classTime: "[ 1-12周 ] - [ 1-2节 ] 单",
        // classPlace: "南苑一栋C402",
        // state: 0
      },

      teacher: {},

      dialogInfoFormVisible: false, //选课对话框状态
      dialogTeacherInfoFormVisible: false, //教师详情对话框状态
      dialogVisible: false, //图片预览对话框状态
      dialogImageUrl: '', //图片预览url

      dataQuery: {}, //筛选条件封装对象

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: "选课列表", //文件名
      autoWidth: true,
      bookType: "xlsx",

      //选项卡
      activeName: "first",
      //选课相关
      flag: 0, //0：未启动选课. 1：cur<begin，2：begin<cur<end，3：cur > end
      selectTime: [], //选课时间段，[0]为起始时间，[1]为结束时间
      curTimeDisplay: '',
      timer: '', //定时器

    };
  },

  created() {
    this.init()
  },

  beforeDesotry() {
    clearInterval(this.timer)
    this.timer = null
  },

  methods: {


    //初始化
    init() {
      this.selectTime[0] = new Date()
      this.selectTime[1] = new Date()
      //得到flag
      courseSelectApi.getFlag()
        .then(res=>{
          this.flag = res.data.flag
          if(this.flag == 0) this.curTimeDisplay = '选课暂未开放'
          else if(this.flag == 3) this.curTimeDisplay = '选课结束'
          //获取当前时间
          this.getSelectTime()
          this.getList()
        })
    },

    //获取所有选课数据
    getList() {
      if(this.flag == 2) {
        if(this.roles[0] == 'student') {
          this.dataQuery.studentId = this.id
          courseSelectApi.getAllCourseclassSelect(this.dataQuery)
            .then(res=>{
              this.list = res.data.list
            })
        }
      } else {
        this.list = []
      }
    },

    //获取选课时间
    getSelectTime() {
      courseSelectApi.getSelectTime()
        .then(res=>{
          if(!(res.data.begin == null || res.data.end == null)) {
            this.selectTime[0] = new Date(res.data.begin)
            this.selectTime[1] = new Date(res.data.end)
            if(this.flag === 0 || this.flag === 3) {
              return
            } 
            //定时器
            this.timer = setInterval(() => {
              this.getFlag()
              var cur = new Date()
              var endTime
              if(cur < this.selectTime[0]) endTime = this.selectTime[0]
              else if(cur < this.selectTime[1]) endTime = this.selectTime[1]
              else {
                this.curTimeDisplay = '选课结束！'
                this.list = []
                //所有未确定课程改为已确定
                courseSelectApi.updateConfirmToTrue()
                  .then(res=>{})
                clearInterval(this.timer)
                this.timer = null
                return false
              }
              // console.log(cur, endTime)
              let leftTime = parseInt((endTime.getTime()-cur.getTime())/1000)
              let d = parseInt(leftTime/(24*60*60))
              let h = parseInt(leftTime/(60*60)%24)
              let m = parseInt(leftTime/60%60)
              let s = parseInt(leftTime%60)
              if(cur < this.selectTime[0]) this.curTimeDisplay = `距离选课开始还剩：${d}天${h}小时${m}分${s}秒`
              else if(cur < this.selectTime[1]) {
                this.curTimeDisplay = `距离选课结束还剩：${d}天${h}小时${m}分${s}秒`
                if(this.flag != 2) {
                  this.flag = 2
                  this.getList()
                }
              }
            }, 1000);
          }
        })
    },

    //启动选课
    startSelect() {
      var begin = this.selectTime[0]
      var end = this.selectTime[1]
      var diff = (end.getTime()-begin.getTime())/1000
      let h = parseInt(diff/(60*60))
      if(h >= 4) {
        this.$message({
            type: "error",
            message: "选课时间不能超过四小时！"
          })
          return false
      }
      begin = this.$moment(begin).format('YYYY-MM-DD HH:mm:ss')
      end = this.$moment(end).format('YYYY-MM-DD HH:mm:ss')
      courseSelectApi.startSelect(begin,end)
        .then(res=>{
          this.$message({
            type: "success",
            message: "开启选课成功！"
          })
          this.init()
        })
    },

    //关闭选课
    closeSelect() {
      courseSelectApi.closeSelect()
        .then(res=>{
            this.$message({
            type: "success",
            message: "关闭选课成功！"
          })
          clearInterval(this.timer)
          this.timer = null
        })
    },

    getFlag() {
      courseSelectApi.getFlag()
        .then(res=>{
          this.flag = res.data.flag
        })
    },

    handleSelectionChange() {},

    //选课点击事件
    selectClick(courseclassId) {
      this.dialogInfoFormVisible = true
      courseSelectApi.getCourseclassSelectById(this.id, courseclassId)
        .then(res=>{
          this.courseclass = res.data.courseclass
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
            this.dialogTeacherInfoFormVisible = true
          })
    },

    //选项卡点击事件
    handleClick(tab, event) {
      // if (tab.index === "0")
      if(tab.index === "0") {
        this.getList()
      } else if(tab.index === "1") {
        courseSelectApi.getSelectedCourseclass(this.id)
          .then(res=>{
            this.selectedList = res.data.list
          })
      } else if(tab.index === "2") {
        courseSelectApi.getQuitCourseclass(this.id)
          .then(res=>{
            this.quitList = res.data.list
          })
      }

      console.log(tab, event);
    },

    //下载xlsx
    handleDownload() {
      this.downloadLoading = true
      import("@/vendor/Export2Excel").then((excel) => {
        const tHeader = ["课程", "学分", "类别", "任课老师", "限选", "已选", "未选", "上课时间", "上课地点"]; //表头名称
        const filterVal = ['courseName', 'credit', "courseType", "teacherName", "limit", "selected", "noneSelected","classTime","classPlace"];
        const list = this.list;
        const data = this.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType,
        })
        this.downloadLoading = false;
      })
    },
    //转换格式
    formatJson(filterVal, jsonData) {
      //j表示list里的属性名，v[j]表示该属性对应的值
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


    //清空按钮点击
    resetData() {
      this.dataQuery = {};
    },

    //退选按钮点击
    removeDataById(id) {
      this.$confirm("您确认退选吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //点击确定，执行then方法
        courseSelectApi.delCourseclassStudent(this.id,id)
          .then(res=>{
            this.$message({
              type: "success",
              message: "退选成功!",
            });
           this.getList()
          })
      });
    },

    //选课模态框点击确定
    saveOrUpdate(courseclass) {
      var courseclassId = this.courseclass.id
      //执行业务逻辑后
      courseSelectApi.saveCourseclassStudent(this.id, courseclassId)
        .then(res=>{
          this.$message({
                type: "success",
                message: "选课成功!",
          });
        this.dialogInfoFormVisible = false
        this.courseclass = {}
        this.getList()
        })
    },
  },
};
</script>

<style>

.el-alert__title {
  font-size: 16px;
}
</style>
