<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item>
          <el-dropdown
            @command="handleBatchCommand"
            :disabled="dataListSelections.length <= 0"
          >
            <el-button type="danger">
              批量操作
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="pass">批量通过</el-dropdown-item>
              <el-dropdown-item command="reject">批量驳回</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>

        <!-- <el-form-item>
          <el-select
            style="width: 250px"
            v-model="dataForm.schoolId"
            placeholder="学校名称"
            filterable
            clearable
          >
            <el-option
              v-for="school in schoolList"
              :key="school.schoolId"
              :label="school.schoolName"
              :value="school.schoolId"
            >
            </el-option>
          </el-select>
        </el-form-item> -->

        <el-form-item>
          <el-select
            style="width: 120px"
            v-model="dataForm.isVerified"
            placeholder="审批状态"
            filterable
            clearable
          >
            <el-option label="未审批" :value="0"></el-option>
            <el-option label="已审批" :value="1"></el-option>
            <el-option label="驳回" :value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="dataForm.name"
            placeholder="请输入名字"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="getPageList()">查询</el-button>
          <el-button type="primary" @click="getDataList()">查询全部</el-button>
          <el-button native-type="reset">清空</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="dataList"
        border
        @selection-change="selectionChangeHandle"
        style="width: 100%"
      >
        <el-table-column
          type="selection"
          header-align="center"
          align="center"
          width="50"
          :selectable="checkSelectable"
        ></el-table-column>

        <el-table-column
          prop="applyId"
          header-align="center"
          align="center"
          label="申请编号"
          width="100px"
        ></el-table-column>

        <!-- <el-table-column
          prop="schoolName"
          header-align="center"
          align="center"
          label="学校名称"
          ><template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>学校编号: {{ scope.row.schoolId }}</p>
              <p>学校名称: {{ scope.row.schoolName }}</p>
              <p>学校详细地址: {{ scope.row.addr }}</p>
              <div slot="reference" class="name-wrapper">
                {{ scope.row.schoolName }}
              </div>
            </el-popover>
          </template>
        </el-table-column> -->

        <el-table-column
          prop="teacherName"
          header-align="center"
          align="center"
          label="教工姓名"
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>教工编号: {{ scope.row.teacherNumber }}</p>
              <p>教工姓名: {{ scope.row.teacherName }}</p>
              <p>联系方式: {{ scope.row.phone }}</p>
              <div slot="reference" class="name-wrapper">
                {{ scope.row.teacherName }}
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="content"
          header-align="center"
          align="center"
          label="申请内容"
        >
          <template slot-scope="scope">
            <el-popover trigger="click" placement="left">
              <p>内容:</p>
              <p>{{ scope.row.content }}</p>
              <div slot="reference" class="name-wrapper">
                <el-button slot="reference">点击查看</el-button>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="role"
          header-align="center"
          align="center"
          label="申请角色"
        >
        </el-table-column>

        <el-table-column
          prop="photo"
          header-align="center"
          align="center"
          label="申请图片"
        >
          <template slot-scope="scope">
            <el-popover trigger="click" placement="left">
              教工照片:
              <p>
                <el-image
                  style="width: 300px; height: 300px"
                  fit="contain"
                  :src="scope.row.photo"
                  :preview-src-list="photoList"
                ></el-image>
              </p>
              教工执照:
              <p>
                <el-image
                  style="width: 300px; height: 300px"
                  fit="contain"
                  :src="scope.row.license"
                  :preview-src-list="photoList"
                >
                </el-image>
              </p>
              <div slot="reference" class="name-wrapper">
                <el-button slot="reference">点击查看</el-button>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="isVerified"
          header-align="center"
          align="center"
          label="审批状态"
          width="120px"
          sortable
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>
                审核时间：
                {{ scope.row.isVerified == 0 ? "" : $moment(scope.row.gmtModified).format("yyyy-MM-DD HH:mm:ss") }}
              </p>
              <div slot="reference" class="name-wrapper">
                <el-tag
                  type="info"
                  v-if="scope.row.isVerified == 0"
                  effect="dark"
                  >未审批</el-tag
                >
                <el-tag
                  type="success"
                  v-else-if="scope.row.isVerified == 1"
                  effect="dark"
                  >已审批</el-tag
                >
                <el-tag type="danger" effect="dark" v-else>驳回</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          header-align="center"
          align="center"
          label="申请日期"
          sortable
        >
        <template slot-scope="scope">
           {{$moment(scope.row.gmtCreate).format("yyyy-MM-DD HH:mm:ss")}}
        </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="passHandle(scope.row)"
              :disabled="scope.row.isVerified == 0 ? btnDisable : !btnDisable"
              >审批</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="rejectHandle(scope.row)"
              :disabled="scope.row.isVerified == 0 ? btnDisable : !btnDisable"
              >驳回</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="limit"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
      ></el-pagination>

      <el-dialog title="驳回" :visible.sync="dialogVisible" width="30%">
        <el-form :model="reject">
          <el-form-item label="驳回原因:" required>
            <br />
            <el-select
              v-model="reject.rejectType"
              filterable
              clearable
              placeholder="请选择"
            >
              <el-option
                v-for="reason in rejectList"
                :key="reason.rejectType"
                :label="reason.rejectReason"
                :value="reason.rejectType"
              >
              </el-option>
              <!-- <el-option label="其他" value="-1">其他</el-option> -->
            </el-select>
          </el-form-item>
          <!-- 表单校验 -->
          <el-form-item v-if="this.reject.rejectType == `-1`" required>
            <el-input
              v-model="reject.rejectReason"
              type="textarea"
              :rows="5"
              placeholder="驳回原因"
            >
            </el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelReject">取 消</el-button>
          <el-button type="primary" @click="sumbitReject">确 定</el-button>
        </span>
      </el-dialog>
    </el-tabs>
  </div>
</template>

<script>
/* eslint-disable */
import applyApi from "@/api/ma/user-apply";

export default {
  data() {
    return {
      /** 查询表单数据 */
      dataForm: {},
      /** 后端请求学校列表 */
      // schoolList: [],
      /** 后端返回table数据 */
      dataList: [],
      /** 教师资料图片列表 */
      photoList: [],
      /** 数据库查出驳回类型+内容下拉框(长度小于10个汉字) */
      rejectList: [],

      btnDisable: false,
      reject: {}, // 驳回对象
      dataListSelections: [], // 选中数据
      applyIds: [], // 未审核id数组
      names: [], // 未审核姓名数组

      //分页用的data
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数

      dialogVisible: false, // 对话框是否显示
    };
  },
  components: {},

  created() {
    this.getDataList(); // 刷新页面
  },

  methods: {
    /** 获取所有数据+模糊查询 */
    getPageList() {
      this.photoList = [];
      // console.log(this.dataForm.schoolId, this.dataForm.isVerified, this.dataForm.key);
      applyApi.getPageList(this.page, this.limit, this.dataForm).then((res) => {
        // console.log(res);
        this.dataList = res.data.dataList;
        this.total = res.data.total;
        // 添加照片
        res.data.dataList.map((data) => {
          this.photoList.push(data.photo);
          this.photoList.push(data.license);
        });
      });
    },

    /** 获取所有数据 */
    getDataList() {
      this.dataForm = {};
      this.photoList = [];
      applyApi.getPageList(this.page, this.limit, this.dataForm).then((res) => {
        // console.log(res);
        this.dataList = res.data.dataList;
        this.total = res.data.total;
        // 添加照片
        res.data.dataList.map((data) => {
          this.photoList.push(data.photo);
          this.photoList.push(data.license);
        });
      });
      // applyApi.getApplyList().then((res) => {
      //   // console.log(res);
      //   this.dataList = res.data.dataList;
      // });

      // 查出学校
      // applyApi.getSchoolList().then((res) => {
      //   this.schoolList = res.data.schoolList;
      //   // console.log(res.data.schoolList);
      // });
    },

    /** 获得选中ids与未审核ids */
    getSelectAndUnverifiedIds() {
      // 判断(isVerified == 未审核), 未审核才能进行驳回或通过操作
      var selectApplyIds = this.dataListSelections.map((item) => {
        if (item.isVerified == 0) {
          this.applyIds.push(item.applyId); //未审核id数组
          this.names.push(item.teacherName); //未审核教工姓名数组
        }
        return item.applyId; //返回选中id数组
      });
      return selectApplyIds;
      // console.log("selectApplyIds: ", selectApplyIds);
      // console.log("unverifiedApplyIds: ", this.applyIds);
      // console.log("unverifiedNames: ", this.names);
    },

    /** 批量: 通过审核 */
    passBatchHandle() {
      var selectApplyIds = this.getSelectAndUnverifiedIds();
      // 判断选中数组与未审核数组是否相同，相同则顺利提交，不相同警告
      if (selectApplyIds.length == this.applyIds.length) {
        this.$confirm(`确定 【${this.names.join(",")}】 申请通过?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 调用 批量驳回数组，根据 selectApplyIds 将 is_verified 改成审批
            // console.log(selectApplyIds);
            applyApi
              .passApplys(selectApplyIds)
              .then((res) => {
                this.$message({
                  type: "success",
                  message: "审批成功!",
                });
                // 刷新页面
                this.getDataList();
              })
              .catch(() => {
                this.$message({
                  type: "error",
                  message: "审核失败!",
                });
              });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "你选中了已处理的申请!",
        });
      }
      this.names = [];
      this.applyIds = [];
    },

    /** 批量: 驳回审核 */
    rejectBatchHandle() {
      var selectApplyIds = this.getSelectAndUnverifiedIds();
      // 判断选中数组与未审核数组是否相同，相同则顺利提交，不相同警告
      if (selectApplyIds.length == this.applyIds.length) {
        this.$confirm(
          `确定对 【${this.names.join(",")}】 申请进行驳回?`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            // 调用 批量驳回数组，根据 this.applyIds 将 is_verified 改成驳回
            applyApi.rejectApplys(selectApplyIds).then((res) => {
              this.$message({
                type: "success",
                message: "批量驳回成功!",
              });
              // 刷新table
              this.getDataList();
            });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "你选中了已处理的申请!",
        });
      }
      this.names = [];
      this.applyIds = [];
    },

    /** 批量操作 */
    handleBatchCommand(cmd) {
      /** 批量 通过审核 */
      if (cmd == "pass") {
        if (this.dataListSelections.length != 0) {
          // 调用 批量审批方法
          this.passBatchHandle();
        } else {
          this.$message({
            type: "error",
            message: "尚未选择!",
          });
        }
      }
      /** 批量 驳回审核 */
      if (cmd == "reject") {
        // 选中数据
        if (this.dataListSelections.length != 0) {
          // 调用 批量驳回方法
          this.rejectBatchHandle();
        } else {
          // 未选中数据
          this.$message({
            type: "error",
            message: "尚未选择!",
          });
        }
      }
    },

    /** 操作栏: 通过审核 */
    passHandle(row) {
      // 判断 isVerified 审核情况, 未审批才能进行审批
      if (row.isVerified == 0) {
        this.$confirm(`确认【${row.teacherName}】审批通过?`, "审批", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 调用审批方法, 提交 row.applyId 给后端修改 isVerified
            // console.log("passHandle: ", row.applyId);
            applyApi
              .passApply(row.applyId)
              .then((res) => {
                this.$message({
                  type: "success",
                  message: "审核成功!",
                });
                // 刷新table
                this.getDataList();
              })
              .catch(() => {
                this.$message({
                  type: "error",
                  message: "审核失败!",
                });
              });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "申请已被处理!",
        });
      }
    },

    /** 操作栏: 驳回审核 */
    rejectHandle(row) {
      if (row.isVerified == 0) {
        // 打开弹窗
        this.dialogVisible = true;
        // 获取到申请id, 赋值给表单中的applyId
        this.reject.applyId = row.applyId;
        // console.log("reject: ", this.reject);
        // 获取驳回类型+原因
        applyApi
          .getRejectReasonList()
          .then((res) => {
            // console.log(res);
            this.rejectList = res.data.rejectList;
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "申请已被处理!",
        });
      }
    },

    /** 驳回提交 */
    sumbitReject() {
      if (this.reject.rejectReason != null || this.reject.rejectType != null) {
        this.$confirm(`确认驳回申请?`, "驳回", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 调用驳回方法, 提交 row.applyId 给后端修改 isVerified
            // console.log("sumbitReject:reject=> ", this.reject);
            applyApi.rejectApply(this.reject).then((res) => {
              this.$message({
                type: "success",
                message: "驳回成功!",
              });

              // 关闭对话框
              this.dialogVisible = false;
              this.reject = {};
              // 刷新table
              this.getDataList();
            });
          })
          .catch(() => {
            this.reject.rejectReason = null;
            this.reject.rejectType = null;
          });
      } else {
        this.$message({
          type: "error",
          message: "驳回原因!!",
        });
      }
    },

    /** 取消驳回按钮 */
    cancelReject() {
      this.dialogVisible = false;
      this.reject = {};
    },

    checkSelectable(row, index) {
      let flag = true;
      if (row.isVerified != "0") {
        flag = false;
      }
      return flag;
    },

    // 每页数
    sizeChangeHandle(val) {
      this.limit = val;
      this.page = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.page = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
      // console.log("多选=> ", val);
    },
  },
};
</script>
