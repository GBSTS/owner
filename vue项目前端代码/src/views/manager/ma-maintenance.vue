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
              <el-dropdown-item command="receive">批量接收</el-dropdown-item>
              <el-dropdown-item command="repair">批量处理</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>

        <el-form-item>
          <el-select
            style="width: 250px"
            v-model="dataForm.repairType"
            placeholder="维修类型"
            filterable
            clearable
          >
            <el-option
              v-for="repair in repairTypeList"
              :key="repair.repairType"
              :label="repair.repairDescribe"
              :value="repair.repairType"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select
            style="width: 120px"
            v-model="dataForm.repairUser"
            placeholder="接收状态"
            filterable
            clearable
          >
            <el-option label="未接收" :value="0"></el-option>
            <el-option label="已接收" :value="1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select
            style="width: 120px"
            v-model="dataForm.repairStatus"
            placeholder="维修状态"
            filterable
            clearable
          >
            <el-option label="未处理" :value="0"></el-option>
            <el-option label="已处理" :value="1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="dataForm.key"
            placeholder="请输入"
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
        v-loading="dataListLoading"
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
          prop="repairId"
          header-align="center"
          align="center"
          label="维修编号"
          width="100px"
        ></el-table-column>

        <el-table-column
          prop="teacherName"
          header-align="center"
          align="center"
          label="申请人"
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>教工编号: {{ scope.row.teacherNumber }}</p>
              <p>联系方式: {{ scope.row.phone }}</p>
              <p>教工姓名: {{ scope.row.teacherName }}</p>
              <div slot="reference" class="name-wrapper">
                {{ scope.row.teacherName }}
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="repairDescribe"
          header-align="center"
          align="center"
          label="维修类型"
          width="100px"
        ></el-table-column>

        <el-table-column
          prop="photo"
          header-align="center"
          align="center"
          label="问题描述"
        >
          <template slot-scope="scope">
            <el-popover trigger="click" placement="left">
              <p>
                {{ scope.row.content }}
              </p>
              <div slot="reference" class="name-wrapper">
                <el-button slot="reference">点击查看</el-button>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="userName"
          header-align="center"
          align="center"
          label="接收状态"
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>管理员编号: {{ scope.row.userId }}</p>
              <p>管理员: {{ scope.row.userName }}</p>
              <p>联系方式: {{ scope.row.userPhone }}</p>
              <div slot="reference" class="name-wrapper">
                <!-- 已接收 -->
                <el-tag type="" v-if="scope.row.userId !== null" effect="dark"
                  ><i class="el-icon-check"></i
                ></el-tag>
                <!-- 未接收 -->
                <el-tag
                  type="info"
                  v-else-if="scope.row.userId === null"
                  effect="dark"
                  ><i class="el-icon-minus"></i
                ></el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          prop="repairStatus"
          header-align="center"
          align="center"
          label="维修状态"
          width="100px"
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="left">
              <p>维修时间： {{ scope.row.repairTime }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag
                  type="info"
                  v-if="scope.row.repairStatus == 0"
                  effect="dark"
                  >未处理</el-tag
                >
                <el-tag
                  type="success"
                  v-else-if="scope.row.repairStatus == 1"
                  effect="dark"
                  >已处理</el-tag
                >
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column
          header-align="center"
          align="center"
          label="申请日期"
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
              @click="receiveHandle(scope.row)"
              :disabled="
                scope.row.userId == null && scope.row.repairStatus == 0
                  ? btnDisable
                  : !btnDisable
              "
              >接收</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="repairHandle(scope.row)"
              :disabled="
                scope.row.repairStatus == 0 && scope.row.userId != null
                  ? btnDisable
                  : !btnDisable
              "
              >处理</el-button
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
    </el-tabs>
  </div>
</template>

<script>
/* eslint-disable */
import { mapGetters } from "vuex";
import repairApi from "@/api/ma/user-maintenance";

export default {
  computed: {
    ...mapGetters(["id"]),
  },
  data() {
    return {
      /** 查询表单数据 */
      dataForm: {},
      /** 后端请求学校列表 */
      repairTypeList: [],

      /** 后端返回table数据 */
      dataList: [],

      repair: {},

      btnDisable: false,
      dataListSelections: [], // 选中数据

      repairIds: [], // 未处理id数组
      repairNames: [], // 未处理姓名数组
      receiveIds: [], // 未接收姓名数组
      receiveNames: [], // 未接收姓名数组

      page: 1,
      limit: 10,
      total: 0,
      dialogVisible: false, // 对话框是否显示
      dataListLoading: false,
    };
  },
  components: {},

  created() {
    this.getDataList(); // 刷新页面
  },

  methods: {
    /** 获取所有数据 */
    getDataList() {
      this.dataForm = {};
      repairApi
        .getPageList(this.page, this.limit, this.dataForm)
        .then((res) => {
          // console.log(res,this.id);
          this.dataList = res.data.dataList;
          this.total = res.data.total;
        });

      // 查出维修类型
      repairApi.getRepairTypeList().then((res) => {
        this.repairTypeList = res.data.repairTypeList;
        // console.log(res.data.repairTypeList);
      });
    },

    /** 获取所有数据+模糊查询 */
    getPageList() {
      // console.log(
      //   this.dataForm.repairType,
      //   this.dataForm.repairUser,
      //   this.dataForm.repairStatus,
      //   this.dataForm.key
      // );
      repairApi
        .getPageList(this.page, this.limit, this.dataForm)
        .then((res) => {
          // console.log(res);
          this.dataList = res.data.dataList;
          this.total = res.data.total;
        });
    },

    /** 获得选中ids 与 未接收ids 与 接收且未处理ids */
    getSelectAndUnverifiedIds() {
      // 判断(repairStatus == 未接收), 未接收才能进行驳回或通过操作
      var selectRepairIds = this.dataListSelections.map((item) => {
        // 未接收
        if (item.userId == null) {
          this.receiveIds.push(item.repairId); //未接收id数组
          this.receiveNames.push(item.teacherName); //未接收教工姓名数组
        }
        // 已接收 且 未处理
        if (item.repairStatus == 0 && item.userId != null) {
          this.repairIds.push(item.repairId); //未处理id数组
          this.repairNames.push(item.teacherName); //未处理教工姓名数组
        }
        return item.repairId; //返回所有选中id数组
      });
      return selectRepairIds;
    },

    /** 批量: 接收维修 */
    receiveBatchHandle() {
      var selectReceiveIds = this.getSelectAndUnverifiedIds();
      // 判断选中数组与未接收数组是否相同，相同则顺利提交，不相同警告
      if (selectReceiveIds.length == this.receiveIds.length) {
        this.$confirm(
          `确定接收 【${this.receiveNames.join(",")}】 维修?`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            // 调用 批量接收数组，根据 this.receiveIds 将 正在处理的管理员id添加到维修表中
            // console.log("this.receiveIds: ", this.receiveIds);
            repairApi.receiveBatch(selectReceiveIds, this.id).then((res) => {
              this.$message({
                type: "success",
                message: "接收成功!",
              });
              // 刷新页面
              this.getDataList();
            });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "你选中了已处理的维修!",
        });
      }
      this.repairIds = [];
      this.repairNames = [];
      this.receiveIds = [];
      this.receiveNames = [];
    },

    /** 批量: 处理维修 */
    repairBatchHandle() {
      var selectRepairIds = this.getSelectAndUnverifiedIds();
      // 判断选中数组与未处理数组是否相同，相同则顺利提交，不相同警告
      if (selectRepairIds.length == this.repairIds.length) {
        this.$confirm(
          `确定已处理 【${this.repairNames.join(",")}】 维修?`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            // 调用 批量处理数组，根据 this.repairIds 将 repair_status 改成1
            // console.log("this.receiveIds: ", this.repairIds);
            repairApi.repairBatch(selectRepairIds).then(() => {
              this.$message({
                type: "success",
                message: "处理成功!",
              });
              // 刷新页面
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
      this.repairIds = [];
      this.repairNames = [];
      this.receiveIds = [];
      this.receiveNames = [];
    },

    /** 批量操作 */
    handleBatchCommand(cmd) {
      /** 批量 接收 */
      if (cmd == "receive") {
        if (this.dataListSelections.length != 0) {
          // 调用 批量接收方法
          this.receiveBatchHandle();
        } else {
          this.$message({
            type: "error",
            message: "尚未选择!",
          });
        }
      }
      /** 批量 处理 */
      if (cmd == "repair") {
        // 选中数据
        if (this.dataListSelections.length != 0) {
          // 调用 批量处理方法
          this.repairBatchHandle();
        } else {
          // 未选中数据
          this.$message({
            type: "error",
            message: "尚未选择!",
          });
        }
      }
    },

    /** 操作栏: 接收维修 */
    receiveHandle(row) {
      // 判断 repairStatus 接收情况, 未接收才能进行接收
      if (row.repairStatus == 0) {
        this.$confirm(`确认接收【${row.teacherName}】维修?`, "审批", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 调用接收方法, 提交 row.repairId 给后端修改 将 正在处理的管理员id添加到维修表中
            // console.log("receiveHandle: ", row.repairId);
            repairApi.receive(row.repairId, this.id).then((res) => {
              this.$message({
                type: "success",
                message: "接收成功!",
              });
              // 刷新table
              this.getDataList();
            });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "维修已被处理!",
        });
      }
    },

    /** 操作栏: 处理维修 */
    repairHandle(row) {
      if (row.repairStatus == 0) {
        // 打开弹窗
        this.$confirm(`确认已处理【${row.teacherName}】维修完成?`, "审批", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            // 调用处理方法, 提交 row.repairId 给后端修改 repairStatus改成1
            // console.log("repairHandle: ", row.repairId);
            repairApi.repair(row.repairId).then((res) => {
              this.$message({
                type: "success",
                message: "处理成功!",
              });
              // 刷新table
              this.getDataList();
            });
          })
          .catch(() => {});
      } else {
        this.$message({
          type: "error",
          message: "维修已被处理!",
        });
      }
    },

    checkSelectable(row, index) {
      let flag = true;
      if (row.repairStatus != 0) {
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
