<template>
	<div class="app-container">
		<h2 style="color: darkslateblue">专业列表</h2>
		<!-- 查询表单，表单用v-model双向绑定-->
		<el-form :inline="true" class="demo-form-inline">
			<el-button type="danger" icon="el-icon-delete" @click="deleteSeleted()" v-permission="['admin']" style="margin-right: 10px">批量删除</el-button>

			<el-form-item>
				<el-input v-model="dataQuery.name" placeholder="专业名" />
			</el-form-item>

			<el-form-item label="所属系科">
        <el-select v-model="dataQuery.departmentId" placeholder="请选择系科">
          <el-option v-for="item in departmentList" :label="item.name" :value="item.id"></el-option> 
        </el-select>
      </el-form-item>

			<el-button style="margin-bottom: 20px" type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
			<el-button style="margin-bottom: 20px" type="default" icon="el-icon-remove-outline" @click="resetData()">清空</el-button>

			<el-button style="margin-bottom: 20px" type="success" icon="el-icon-circle-plus-outline" @click="editForm(id)">添加</el-button>

			<el-button style="margin-bottom: 20px" :loading="downloadLoading" type="primary" icon="el-icon-document" @click="handleDownload">
				导出Excel
			</el-button>
		</el-form>

		<!-- 表格 -->
		<!-- 在table里定义:data=list后，后面的column的prop都直接写list里的属性，不用list. -->
		<el-table :data="list" border fit highlight-current-row @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"> </el-table-column>

			<el-table-column label="序号" width="70" align="center">
				<!-- 这里用插槽，因为序号要自定义值 -->
				<template slot-scope="scope">
					{{ (page - 1) * limit + scope.$index + 1 }}
				</template>
			</el-table-column>

			<el-table-column prop="name" label="专业名" />
			
			<el-table-column prop="departmentName" label="系科名" />

			<el-table-column prop="collegeName" label="所属学院" />

			<el-table-column prop="introduction" label="专业简介" />

			<el-table-column label="操作" width="300" align="center">
				<template slot-scope="scope">
					<el-button icon="el-icon-more" size="mini" @click="infoClicked(scope.row.id)">详情</el-button>

					<el-button type="primary" size="mini" icon="el-icon-edit" @click="editForm(scope.row.id)">修改</el-button>
					<!-- 这里绑定一个删除事件，将传该行id -->
					<el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!-- 分页 -->
		<el-pagination :current-page.sync="page" :page-sizes="[5, 10, 15, 20, 50, 100, 1000]" :page-size.sync="limit" :total="total"
		 style="padding: 30px 0; text-align: center" layout="total, sizes, prev, pager, next, jumper" @current-change="getList" @size-change="getList" />

		<el-dialog title="专业编辑" :visible.sync="dialogEditFormVisible">
			<el-card class="box-card" style="width: 100%">
				<el-form :model="major" :rules="rules" ref="major" label-position="right" label-width="100px" class="demo-ruleForm">
					<div class="el-divider el-divider--horizontal">
						<div class="el-divider__text is-center">专业简介</div>
					</div>
					<div class="el-row">
						<div prop="introduction" class="el-col">
							<el-input type="textarea" :rows="3" v-model="major.introduction" placeholder="请输入专业简介"></el-input>
						</div>
					</div>
					<div class="el-divider el-divider--horizontal">
						<div class="el-divider__text is-center">专业信息</div>
					</div>
					<div class="el-row">
						 <el-form-item label="所属系科" prop="departmentId" class="el-col el-col-10">
                <el-select style="width: " v-model="major.departmentId" placeholder="请选择系科">
                  <el-option v-for="item in departmentList" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
						<el-form-item label="专业" prop="major" class="el-col el-col-12">
							<el-input v-model="major.name" placeholder="请输入所属专业"></el-input>
						</el-form-item>
					</div>
				</el-form>
			</el-card>
			<div slot="footer" class="dialog-footer">
			  <el-button @click="dialogEditFormVisible = false">取 消</el-button>
			  <el-button type="primary" @click="saveOrUpdate('major')">确 定</el-button>
			</div>
		</el-dialog>

		<el-dialog title="系科详情" :visible.sync="dialogInfoFormVisible">
		  <el-card class="box-card" style="width: 100%" :body-style="{ padding: '0px' }">
		    <h2 style="text-align: center">{{ major.name }}</h2>
		    <div style="padding: 0px 20px 20px 20px">
		      <div class="el-divider el-divider--horizontal">
		        <div class="el-divider__text is-center">专业简介</div>
		      </div>
		      <div class="el-row">
		        <div class="el-col el-col-24" style="text-indent: 2em">
		          {{ major.introduction }}
		        </div>
		      </div>
		      <div class="el-divider el-divider--horizontal">
		        <div class="el-divider__text is-center">专业信息</div>
		      </div>
		      <div class="el-row">
				  <div class="el-col el-col-12">
              <div class="el-form-item">
                <label class="el-form-item__label">专业名称:</label>
                <div class="el-form-item__content">
                  <el-label>{{ major.name }}</el-label>
                </div>
              </div>
            </div>
		        <div class="el-col el-col-12">
		          <div class="el-form-item">
		            <label class="el-form-item__label">所属学院:</label>
		            <div class="el-form-item__content">
		              <el-label>{{ major.collegeName }}</el-label>
		            </div>
		          </div>
		        </div>
						<div class="el-col el-col-12">
						  <div class="el-form-item">
						    <label class="el-form-item__label">系科名:</label>
						    <div class="el-form-item__content">
						      <el-label>{{ major.departmentName }}</el-label>
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
 import collegeApi from '@/api/te/college'
  import departmentApi from '@/api/te/department'
  import majorApi from '@/api/te/major'
import { mapGetters } from 'vuex' //全局变量管理，用于得到用户信息
	import permission from "@/directive/permission/index.js"; // 导入权限判断指令，可用v-permisson
	//导出excal功能用的组件
	import {
		parseTime
	} from "@/utils";
	import FilenameOption from "./components/FilenameOption";
	import AutoWidthOption from "./components/AutoWidthOption";
	import BookTypeOption from "./components/BookTypeOption";

	export default {
		directives: {
			permission
		}, //导入组件
		components: {
			FilenameOption,
			AutoWidthOption,
			BookTypeOption
		},
		  //计算属性，methods中可以通过this.roles得到角色数组
  computed: {
    ...mapGetters([
      'roles'
    ])
  },

		data() {
			return {
				list: [{
						id: "11111",
						name: "软件工程",
						departmentId:"",
						collegeName: "计算机与工程学院",
						introduction: "666"
					}
				],

				rules: {
					departmentId: [{
							required: true,
							message: "请输入所属系",
							trigger: "blur"
						},
						{
							min: 1,
							max: 20,
							message: "长度在 1 到 10 个字符",
							trigger: "blur",
						}
					],
					mame: [{
							required: true,
							message: "请输入所属专业",
							trigger: "blur"
						},
						{
							min: 1,
							max: 10,
							message: "长度在 1 到 10 个字符",
							trigger: "blur",
						}
					],
					introduction: [{
						required: true,
						message: "请输入专业简介",
						trigger: "blur"
					}]
				},

				major: {
					id: "11111",
					collegeName: "计算机与工程学院",
					departmentName: "惠州学院",
				  name: "软件工程",
					introduction: "666"
				},
					   departmentList: [
      ],

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
				filename: "major", //文件名
				autoWidth: true,
				bookType: "xlsx",
			};
		},
  created() {
          this.getList()
		  this.getDepartmentList();
  },
		methods: {
			//下载xlsx
			handleDownload() {
				this.downloadLoading = true;
				import("@/vendor/Export2Excel").then((excel) => {
					const tHeader = ["专业名", "系科名", "所属学院", "专业简介"]; //表头名称
					const filterVal = ["name", "departmentName", "collegeName", "introduction"];
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
						return v[j];
					})
				);
			},

			    //详情按钮点击.
    infoClicked(id) {
      console.log(id)
      majorApi.getMajorById(id)
          .then(res=>{
            this.major = res.data.major
            this.dialogInfoFormVisible = true
          })
          //this.college=xxx 从后端根据id取对象
    },    removeDataById(id) {
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
          majorApi.delMajorById(id)
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
      if(role != 'admin' && role != 'admin_teacher' && role != 'school'&& role != 'college') {
        this.$message({
          type: "error",
          message: "您没有此权限！"
        });
        return false
      }
      
      if(id == null) {
        this.major = {}
      } else {
        //得到teacherVO
          majorApi.getMajorById(id)
            .then(res=>{
              this.major = res.data.major
            })
      }
      this.dialogEditFormVisible = true;
    },
			 getDepartmentList() {
    //得到所有院的list
      departmentApi.getList()
        .then(res=>{
          this.departmentList = res.data.list
        })
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
            majorApi.delMajorsByList(idList)
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

			//获取所有数据
    getList() {
      console.log(this.page)
      console.log(this.limit)
      console.log(this.dataQuery)
      majorApi.pageList(this.page,this.limit,this.dataQuery)
        .then(res=>{
          console.log(res)
          this.list = res.data.list
          this.total = res.data.total
        })
	},
          //模态框点击确定
    saveOrUpdate(major) {
	  const _this=this
      var is=0;
      var _is=0;
      this.$refs[major].validate((valid) => {
        if (valid) {
          //表单前端校验通过
          console.log(this.major)
          if(this.major.id == null || this.major.id == "") {
			              for(var i in  _this.list)
            {
              if(_this.list[i].name==this.major.name)
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
            majorApi.saveMajor(this.major)
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
            majorApi.updateMajor(this.major)
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
	}
</script>

<style>
	.select {
		width: 100%;
	}
</style>
