<template>
  <div class="app-container">
    <h2 style="color: darkslateblue">教师列表</h2>
    <!-- 查询表单，表单用v-model双向绑定-->
    <el-form :inline="true" class="demo-form-inline">
      <el-button
        type="danger"
        icon="el-icon-delete"
        @click="deleteSeleted()"
        style="margin-right: 10px"
        >批量删除</el-button
      >

      <el-form-item>
        <el-input v-model="dataQuery.name" placeholder="姓名" />
      </el-form-item>

      <el-form-item>
        <el-input v-model="dataQuery.title" placeholder="职称" />
      </el-form-item>

      <el-form-item label="所属学院">
        <el-select v-model="dataQuery.collegeId" placeholder="请选择学院">
          <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option> 
        </el-select>
      </el-form-item>

      <el-form-item label="政治面貌">
        <el-select v-model.number="dataQuery.political" placeholder="请选择政治面貌">
          <el-option label="中共党员" :value=1></el-option> 
          <el-option label="共青团员" :value=2></el-option>
          <el-option label="群众" :value=3></el-option>
        </el-select>
      </el-form-item>

      <el-button style="margin-bottom: 20px" type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button style="margin-bottom: 20px" type="default" icon="el-icon-remove-outline" @click="resetData()"
        >清空</el-button
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

      <el-table-column prop="name" label="名字" />

      <el-table-column label="性别">
        <!-- 这里用插槽，因为性别是数值，要转成对应字符串 -->
        <template slot-scope="scope">
          {{ scope.row.gender === false ? "男" : "女" }}
        </template>
      </el-table-column>

      <el-table-column label="级别">
        <!-- 这里用插槽，因为政治面貌是数值，要转成对应字符串 -->
        <template slot-scope="scope">
          <p v-if="scope.row.level === 1">校级领导</p>
          <p v-else-if="scope.row.level === 2">院级领导</p>
          <p v-else>教师</p>
        </template>
      </el-table-column>

      <el-table-column prop="title" label="职称" />

      <el-table-column prop="collegeName" label="所属学院" />

      <el-table-column prop="hiredate" label="入职时间" />

       <el-table-column label="用户状态" align="center">
        <!-- 这里用插槽，因为政治面貌是数值，要转成对应字符串 -->
        <template slot-scope="scope">
          <el-tag
                  type="info"
                  v-if="scope.row.isVerified === 0"
                  effect="dark"
                  >未审批</el-tag
                >
                <el-tag
                  type="success"
                  v-else
                  effect="dark"
                  >已审批</el-tag
                >
        </template>
      </el-table-column>

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
    <el-dialog title="教师编辑" :visible.sync="dialogEditFormVisible">
      <el-card class="box-card" style="width: 100%">
        <el-form
          :model="teacher"
          :rules="rules"
          ref="teacher"
          label-width="100px"
          class="demo-ruleForm"
        >
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">基本信息</div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <div>
                <!-- 上传照片 -->
                <!-- action: 发送post请求的路径 -->
                <el-form-item label="照片" >
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
                      style="width: 178px; height: 178px"
                      v-if="teacher.photo"
                      :src="teacher.photo"
                      class="avatar"
                    />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                </el-form-item>
              </div>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="名字" prop="name">
                <el-input v-model="teacher.name"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="teacher.gender">
                  <el-radio :label="false">男</el-radio>
                  <el-radio :label="true">女</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="教师级别" prop="level" required>
                <el-select style="width: " v-model.number="teacher.level" placeholder="请选择教师级别">
                  <el-option label="校级领导" :value=1></el-option>
                  <el-option label="院级领导" :value=2></el-option>
                  <el-option label="教师" :value=3></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <el-form-item label="身份证号" prop="identityNumber" required>
                <el-input v-model="teacher.identityNumber"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="出生日期" prop="birth">
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                  v-model="teacher.birth"
                  value-format="yyyy-MM-dd"
                  style="width: 100%"
                ></el-date-picker>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <el-form-item label="籍贯" prop="nativeplace">
                  <el-input v-model="teacher.nativeplace"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="政治面貌" prop="political">
                <el-select style="width: " v-model.number="teacher.political" placeholder="请选择政治面貌">
                  <el-option label="中共党员" :value=1></el-option>
                  <el-option label="共青团员" :value=2></el-option>
                  <el-option label="群众" :value=3></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <el-form-item label="联系电话" prop="phone">
                <el-input v-model.number="teacher.phone"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="teacher.email"></el-input>
              </el-form-item>
            </div>
          </div>
            <div class="el-divider el-divider--horizontal">
              <div class="el-divider__text is-center">教职信息</div>
            </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <el-form-item label="教职工号">
                自动生成
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="所属学院" prop="collegeId">
                <el-select style="width: " v-model="teacher.collegeId" placeholder="请选择学院">
                  <el-option v-for="item in collegeList" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <div class="el-row">
            <div class="el-col el-col-10">
              <el-form-item label="职称" prop="title" required>
                <el-input v-model="teacher.title"></el-input>
              </el-form-item>
            </div>
            <div class="el-col el-col-10">
              <el-form-item label="入职时间" prop="hiredate" required>
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                  v-model="teacher.hiredate"
                  value-format="yyyy-MM-dd"
                  style="width: 100%"
                ></el-date-picker>
              </el-form-item>
            </div>
          </div>
          <div class="el-divider el-divider--horizontal">
            <div class="el-divider__text is-center">执照</div>
          </div>
          <!-- 上传执照 -->
          <!-- action: 发送post请求的路径 -->
          <div class="el-form-item" prop="license" style="text-align: center">
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
          </div>
        </el-form>
      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="quitClick">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate('teacher')">确 定</el-button>
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
                <el-label>{{ teacher.birth }}</el-label>
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">籍贯:</label>
              <div class="el-form-item__content">
                <el-label>{{ teacher.nativeplace }}</el-label>
                <!---->
              </div>
            </div>
          </div>
          <div class="el-col el-col-8">
            <div class="el-form-item">
              <label class="el-form-item__label">政治面貌:</label>
              <div class="el-form-item__content">
                <el-label v-if="teacher.political == 1">中共党员</el-label>
                <el-label v-else-if="teacher.political == 2">共青团员</el-label>
                <el-label v-else>群众</el-label>
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
        <div class="el-divider el-divider--horizontal">
          <div class="el-divider__text is-center">执照</div>
        </div>
        <div class="demo-image__preview" style="text-align: center">
          <el-image
            style="width: 200px; height: 200px"
            :src="teacher.license"
            @click="picturePreview('license')"
          >
          </el-image>
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
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
import teacherApi from '@/api/te/teacher'
import collegeApi from '@/api/te/college'

//导出excal功能用的组件
import { parseTime } from '@/utils'
import FilenameOption from './components/FilenameOption'
import AutoWidthOption from './components/AutoWidthOption'
import BookTypeOption from './components/BookTypeOption'

export default {
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

    return {
      list: [
        // {
        //   id: '11111',
        //   name: "叶子豪",
        //   college_name: '计算机科学与工程学院',
        //   gender: 0,
        //   nativeplace: "广东东莞",
        //   political: 1,
        //   title: "校长",
        //   birth: "2000-12-03",
        //   hiredate: "2020-12-03",         
        //   phone: "1231234567",
        //   email: "yezihao@qq.com",
        // }
      ],
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
        level: [
          { required: true, message: "请选择教师级别", trigger: "change" },
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
          // id: '11111',
          // identityNumber: '440902200008200825',
          // collegeId: '',
          // name: "叶子豪",
          // gender: 0,
          // political: '1',
          // nativeplace: "广东东莞",
          // birth: "2000-12-03",
          // hiredate: "2020-12-03",
          // title: "校长",
          // phone: "1231234567",
          // email: "yezihao@qq.com",
          // photo: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2975996532,2425031589&fm=26&gp=0.jpg', //照片路径
          // license: 'https://bpic.588ku.com/element_origin_min_pic/19/04/22/0b24a9294dc1a4d1b478c32b99af4c2c.jpg', //执照路径
      },
      collegeList: [
        // {
        //   id: 'asuihd12d1n12ff1',
        //   name: '计算机与工程学院'
        // },
      ],

      //分页用的data
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数

      dialogEditFormVisible: false, //编辑对话框状态
      dialogInfoFormVisible: false, //详情对话框状态
      dialogVisible: false, //图片放大对话框状态
      dialogImageUrl: '', //图片放大对话框url
      avatarLoading: false, //头像加载
      licenseLoading: false, //执照加载

      dataQuery: {}, //筛选条件封装对象

      multipleSelection: [], //多选的对象数组

      //下载xlsx用的data
      downloadLoading: false, //读取进度条
      filename: 'teacher', //文件名
      autoWidth: true, 
      bookType: 'xlsx'
    };
  },

  created() {
    this.getList()
    this.getCollegeList()
  },

  methods: {

      //下载xlsx
      handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['名字', '所属院','性别', '职称', '政治面貌', '入职时间'] //表头名称
        const filterVal = ['name', 'collegeName','gender', 'title', 'political', 'hiredate']
        const list = this.list
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType
        })
        this.downloadLoading = false
      })
    },
    //转换格式
    formatJson(filterVal, jsonData) {
      //j表示list里的属性名，v[j]表示该属性对应的值。此处gender和political值是数字，要转换成对应字符串
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'gender') {
          if(v[j] == 0) v[j] = '男'
          else v[j] = '女'
          return v[j]
        }
        if(j === 'political') {
          if(v[j] == 1) v[j] = '中共党员'
          else if(v[j] == 2) v[j] = '共青团员'
          else v[j] = '群众'
          return v[j]
        }
        if (j === 'hiredate') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
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

      //详情按钮点击.
      infoClicked(id) {
        teacherApi.getTeacherById(id)
          .then(res=>{
            this.teacher = res.data.teacher
            this.teacher.hiredate = this.teacher.hiredate.substring(0,10)
            if(this.teacher.birth) this.teacher.birth = this.teacher.birth.substring(0,10)
            this.dialogInfoFormVisible = true
          })
          //this.teacher=xxx 从后端根据id取对象
      },

      //批量删除
      deleteSeleted() {
        var role = this.roles[0] //得到角色字符串
        //系的批量删除只有admin，教师系统超级管理员，系管理员有权限操作
        if(role == 'admin' || role == 'admin_teacher') {
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
            teacherApi.delTeachersByList(idList)
              .then(res=>{
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
                if(this.total%this.limit == idList.length && this.page > 1) this.page--
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

    //添加/修改按钮点击
    editForm(id) {
      var role = this.roles[0] //得到角色字符串
      if(role != 'admin' && role != 'admin_teacher') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      
      if(id == null) {
        this.teacher = {}
      } else {
        //得到teacherVO
          teacherApi.getTeacherById(id)
            .then(res=>{
              this.teacher = res.data.teacher
            })
      }
      this.dialogEditFormVisible = true;
    },

    //删除按钮点击
    removeDataById(id) {
      var role = this.roles[0]
      if(role == 'admin' || role == 'admin_teacher') {
        this.$confirm("此操作将永久删除记录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          //点击确定，执行then方法
          //提示信息
          teacherApi.delTeacherById(id)
            .then(res=>{
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              if(this.total%this.limit == 1 && this.page > 1) this.page--
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

    //取消按钮点击
    quitClick() {
      this.$confirm("是否取消编辑?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.dialogEditFormVisible = false
          this.teacher = {}
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

    //获取所有数据
    getList() {
      console.log(this.dataQuery)
      console.log(this.page,this.limit)
      teacherApi.pageList(this.page,this.limit,this.dataQuery)
        .then(res=>{
          console.log(res)
          this.total = res.data.total
          this.list = res.data.list
          for(var i in this.list) {
            this.list[i].hiredate = this.list[i].hiredate.substring(0,10)
          }
        })
    },

    //模态框点击确定
    saveOrUpdate(teacher) {
      this.$refs[teacher].validate((valid) => {
        if (valid) {
          //表单前端校验通过
          console.log(this.teacher)
          if(this.teacher.id == null || this.teacher.id == "") {
            teacherApi.saveTeacher(this.teacher)
              .then(res=>{
                  this.$message({
                  type: "success",
                  message: "添加成功!",
                });
              this.dialogEditFormVisible = false
              this.getList()
              })
          } else{
            teacherApi.updateTeacher(this.teacher)
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
  }
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
