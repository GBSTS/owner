<template>
  <div class="app-container">
    <el-container>
      <!----------------侧边栏---------------->
      <el-aside width="340px">
        <!-- <el-form -->
        <!-- :inline="true" -->
        <!-- :model="selectTalk" -->
        <!-- @keyup.enter.native="getRecordList()" -->
        <!-- > -->
        <!-- 搜索框 -->
        <!-- <el-form-item> -->
        <!-- <el-input v-model="selectTalk.key"></el-input> -->
        <!-- </el-form-item> -->
        <!-- 查询 -->
        <!-- <el-form-item> -->
        <!-- <el-button type="success" @click="queryTalk(selectTalk.key)" -->
        <!-- >查询</el-button -->
        <!-- > -->
        <!-- </el-form-item> -->
        <!-- </el-form> -->

        <!-- 刷新列表 -->
        <el-button
          type="primary"
          class="refreshBtn"
          @click="refreshList"
          style="margin-top: 10px"
          >刷新列表</el-button
        >

        <!-- 消息列表 -->
        <div
          style="
            margin-top: 5px;
            max-height: calc(100vh - 180px);
            height: calc(100vh - 180px);
            overflow: auto;
          "
        >
          <div v-if="talkRecord.length > 0">
            <el-card
              class="box-card text item"
              v-for="talk in this.talkRecord"
              :key="talk.teacherNumber"
              :class="{ active: active == talk.teacherNumber }"
              @click.native="selectedChat(talk)"
            >
              <!-- 头像 -->
              <i class="el-icon-user-solid imghead"></i>

              <p class="first-p">
                <!-- 姓名 -->
                <span class="name">{{ talk.teacherName | ellipsis }}</span>
                <!-- 时间 时间格式 -->
                <span class="lastMsgTime">{{
                  $moment(talk.lastMsgTime).format("yyyy-MM-DD HH:mm")
                }}</span>
              </p>

              <p class="second-p">
                <!-- 消息数 -->
                <el-badge
                  class="newMsgCount"
                  :value="talk.newMsgCount"
                  :max="99"
                  v-show="talk.newMsgCount > 0"
                ></el-badge>
                <!-- 消息 -->
                <span class="lastMsgContent">
                  {{ talk.lastMsgContent | ellipsis }}
                </span>
              </p>
            </el-card>
          </div>
          <div v-else-if="talkRecord.length == 0">
            <div class="">
              <p class="notice">
                <i class="el-icon-warning"></i><font>当前没有会话</font>
              </p>
            </div>
          </div>
        </div>
      </el-aside>

      <transition name="el-fade-in-linear">
        <!----------------聊天窗口---------------->
        <!--  -->
        <el-container class="transition-box" v-if="active != ''">
          <el-header>
            <!-- 头部信息 -->
            <span class="teacherName">{{ chatInfoEn.teacherName }}</span>
          </el-header>

          <el-main>
            <!-- 咨询窗口 -->
            <div class="common_chat-wrapper">
              <div class="common_chat-inner">
                <!-- 聊天记录 -->
                <div
                  class="common_chat-main"
                  id="common_chat_main"
                  ref="common_chat_main"
                >
                  <div class="common_chat-main-content">
                    <div class="inner">
                      <div
                        v-for="(item, index) in chatInfoEn.msgList"
                        :key="index"
                      >
                        <!-- 客户、客服 -->
                        <div
                          class="item"
                          :class="{
                            sender: item.role == oprRoleName,
                            receiver: item.role != oprRoleName,
                          }"
                        >
                          <div class="info-wrapper">
                            <!-- 系统时间 -->
                            <div class="sys">
                              <p>{{$moment(item.gmtCreate).format("yyyy-MM-DD HH:mm:ss")}}</p>
                            </div>
                            <!-- 头像 -->
                            <div class="avatar-wrapper">
                              <!-- <img class="kf-img" :src="item.avatarUrl" /> -->
                              <img class="kf-img" :src="chatInfoEn.avatarUrl" />
                            </div>
                            <!-- 1)文本类型 -->
                            <div
                              v-if="item.contentType == 0"
                              class="item-content common_chat_emoji-wrapper-global"
                            >
                              <p class="text" v-html="item.content"></p>
                            </div>
                            <!-- 2)图片类型 -->
                            <div
                              v-else-if="item.contentType == 1"
                              class="item-content"
                            >
                              <img
                                class="img"
                                :src="item.fileUrl"
                                @click="imgViewDialog_show(item)"
                              />
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- 底部区域 -->
                <div class="common_chat-footer">
                  <!-- 聊天输入框 -->
                  <div class="input-wrapper">
                    <div
                      maxlength="500"
                      class="inputContent common_chat_emoji-wrapper-global"
                      id="common_chat_input"
                      contenteditable="true"
                      @paste.stop="inputContent_paste"
                      @drop="inputContent_drop"
                      @keydown="inputContent_keydown"
                      @mouseup="inputContent_mouseup"
                      @mouseleave="inputContent_mouseup"
                    ></div>
                  </div>

                  <!-- 轮询刷新 -->
                  <el-button
                    style="float: right; padding: 8px"
                    type="success"
                    icon="el-icon-refresh-right"
                    circle
                    @click="refreshData"
                  ></el-button>

                  <!-- 发送按钮 -->
                  <el-button
                    type="primary"
                    size="small"
                    class="send-btn"
                    :class="chatInfoEn.state"
                    @click="sendText()"
                    :disabled="chatInfoEn.inputContent.length == 0"
                    >发送</el-button
                  >
                </div>
              </div>

              <!-- 图片查看dialog -->
              <el-dialog
                title
                :visible.sync="imgViewDialogVisible"
                class="imgView-dialog"
                :modal="false"
              >
                <div class="header">
                  <i
                    class="iconfont fa fa-remove"
                    @click="imgViewDialog_close"
                  ></i>
                </div>
                <div class="main">
                  <img class="img" :src="imgViewDialog_imgSrc" />
                </div>
              </el-dialog>
            </div>
          </el-main>
        </el-container>
      </transition>
    </el-container>
  </div>
</template>

<script>
/* eslint-disable */
import talkApi from "@/api/ma/user-talk";

export default {

  filters: {
    ellipsis(val) {
      if (!val) return "";
      if (val.length > 10) {
        return val.slice(0, 10) + "...";
      }
      return val;
    },
  },

  props: {
    chatInfoEn: {
      // required: true,
      // type: [Object, Array],
      default: () => {
        return {
          inputContent: "",
          teacherName: "",
          avatarUrl:
            "https://pic1.zhimg.com/80/v2-5ec0fb527c389530021ec2c911875165_720w.jpg?source=1940ef5c",
          msgList: [
            // {
            //   role: 1,
            //   contentType: 0,
            //   content: "aaa这是内容这是内容这是内容这是内容这是内容",
            //   gmtCreate: "2020-12-05 22:00:00",
            // },
          ],
        };
      },
    },

    oprRoleName: {
      // required: true,
      // type: Integer,
      default: 0,
    }, // 操作者名称；e.g. 0:用户 1:管理员
  },

  data() {
    return {
      timer: "",

      selectTalk: {},
      talkRecord: [],
      talkChat: {
        teacherNumber: "",
        role: 0,
        contentType: 0,
        content: "",
        fileUrl: null,
        avatarUrl:
          "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607185469505&di=95f6a5b97cb4438cdf8c10113efeb092&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201809%2F01%2F20180901190625_wmpeq.thumb.700_0.jpeg",
      },

      talkInfo: {},
      active: "",

      inputContent_setTimeout: null, // 输入文字时在输入结束才修改具体内容
      selectionRange: null, // 输入框选中的区域
      imgViewDialogVisible: false, // 图片查看dialog的显示
      imgViewDialog_imgSrc: "", // 图片查看dialog的图片地址
    };
  },

  created() {
    this.getRecordList();
  },

  methods: {
    /** 刷新列表 */
    getRecordList() {
      talkApi.getRecordList(false).then((res) => {
        this.$data.talkRecord = res.data.talkRecord;
      });
      // console.log("刷新聊天列表 => =>");
    },
    /** 刷新咨询列表 */
    refreshList() {
      this.getRecordList();
      this.active = "";
    },

    /** 打开聊天窗口 */
    selectedChat(talk) {
      // 发送请求，查询聊天记录
      talkApi.getChat(talk.teacherNumber).then((res) => {
        this.chatInfoEn.msgList = res.data.msgList;
        talk.newMsgCount = 0;
        this.chatInfoEn.teacherName = talk.teacherName;
        this.active = talk.teacherNumber;
        this.scrollToBottom();
      });
    },

    /** 刷新聊天记录 */
    refreshData() {
      // 聊天记录
      this.chatInfoEn.msgList = [];
      // this.$message({ type: "success", message: `${this.active}` });
      talkApi.getChat(this.active).then((res) => {
        this.chatInfoEn.msgList = res.data.msgList;
        this.scrollToBottom();
        this.getRecordList();
      });
    },

    init() {
      // 初始化状态
      document.getElementById("common_chat_input").innerHTML = "";
    },

    /**
     * 发送文本
     */
    sendText: function () {
      if (this.chatInfoEn.inputContent.length == "") {
        return;
      }
      var msgContent = this.chatInfoEn.inputContent;
      // console.log("msgContent: ", msgContent); // 打印输入内容
      document.getElementById("common_chat_input").innerHTML = ""; // 聊天框置空
      this.setInputContentByDiv();

      this.sendMsg({
        contentType: 0,
        content: msgContent,
        role: 0, // 管理员-0 ，用户-1
        fileUrl: null,
      });
    },

    /**
     * 设置输入内容
     * 根据input输入框innerHTML转换为纯文本
     */
    setInputContentByDiv: function () {
      var htmlStr = document.getElementById("common_chat_input").innerHTML;

      // 1.转换表情为纯文本：<img textanme="[笑]"/> => [笑]
      var tmpInputContent = htmlStr
        .replace(/<img.+text=\"(.+?)\".+>/g, "[$1]")
        .replace(/<.+?>/g, "");

      // 设置最长长度
      if (tmpInputContent.length > 500) {
        document.getElementById("common_chat_input").innerHTML = "";
        var value = tmpInputContent.substr(0, 499);
        this.setInputDiv(value);
      }

      // 3.修改store
      this.chatInfoEn.inputContent = tmpInputContent;
    },

    /**
     * 设置input输入框内容
     * @param {String} vlaue 设置的值
     */
    setInputDiv: function (value) {
      if (this.$data.selectionRange == null) {
        document.getElementById("common_chat_input").focus();
        return;
      }
      // 1.设置selectionRange
      if (window.getSelection) {
        window.getSelection().removeAllRanges();
        window.getSelection().addRange(this.$data.selectionRange);
      } else {
        this.$data.selectionRange && this.$data.selectionRange.select();
      }

      // 3.聊天框中是否选中了文本，若选中文本将被替换成输入内容
      if (window.getSelection) {
        var sel, range;
        // IE9 and non-IE
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
          // 1)删除选中的文本(内容)
          range = sel.getRangeAt(0); // 获取鼠标选中的文本区域
          range.deleteContents(); // 删除选中的文本
        }
      } else if (document.selection && document.selection.type != "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(imgStr);
      }

      // 4.修改inputContent
      this.setInputContentByDiv();
    },

    /**
     * 设置input输入框的选择焦点
     */
    setInputContentSelectRange: function () {
      if (window.getSelection && window.getSelection().rangeCount > 0) {
        var selectRange = window.getSelection().getRangeAt(0);
        if (
          selectRange.commonAncestorContainer.nodeName == "#text" &&
          selectRange.commonAncestorContainer.parentElement &&
          selectRange.commonAncestorContainer.parentElement.id ==
            "common_chat_input"
        ) {
          // 选中了输入框内的文本
          this.$data.selectionRange = selectRange;
        } else if (
          selectRange.commonAncestorContainer.id == "common_chat_input"
        ) {
          // 选中了输入框
          this.$data.selectionRange = selectRange;
        }
      }
    },

    /**
     * 输入框的mouseup
     */
    inputContent_mouseup: function (e) {
      this.setInputContentSelectRange();
    },

    /**
     * 输入框的keydown
     */
    inputContent_keydown: function (e) {
      // keyup触发时，绑定的数据还没有被变更，需要进行延后访问
      this.setInputContentSelectRange();
      clearTimeout(this.$data.inputContent_setTimeout);
      this.$data.inputContent_setTimeout = setTimeout(() => {
        this.setInputContentByDiv();
        // 若按了回车，直接发送
        if (e.keyCode == 13) {
          this.sendText();
          this.chatInfoEn.inputContent = "";
        }
      }, 1);
    },

    /**
     * 输入框的粘贴
     */
    inputContent_paste: function (e) {
      var self = this;
      var isImage = false;
      if (e.clipboardData && e.clipboardData.items.length > 0) {
        // 2.纯文本
        if (!isImage) {
          var str = e.clipboardData.getData("text/plain");
          // 转化为纯文字
          var span = document.createElement("span");
          span.innerHTML = str;
          var txt = span.innerText;
          this.setInputDiv(
            txt
              .replace(/\n/g, "")
              .replace(/\r/g, "")
              .replace(/</g, "&lt;")
              .replace(/>/g, "&gt;")
          );
        }
      }
      e.stopPropagation();
      e.preventDefault();
    },

    /**
     * 图片查看dialog_显示
     */
    imgViewDialog_show: function (item) {
      this.$data.imgViewDialogVisible = true;
      this.$data.imgViewDialog_imgSrc = item.fileUrl;
    },

    /**
     * 图片查看dialog_显示
     */
    imgViewDialog_close: function () {
      this.$data.imgViewDialogVisible = false;
      var self = this;
      setTimeout(function () {
        self.$data.imgViewDialog_imgSrc = "";
      }, 100);
    },

    /**
     * 输入框的拖拽
     */
    inputContent_drop: function (e) {
      var self = this;
      setTimeout(function () {
        self.setInputContentByDiv();
      }, 100);
    },

    /**
     * 发送消息，e.g. 文本、图片、文件
     * @param {Object} msg 消息对象
     */
    sendMsg: function (msg) {
      // 请求后端发送信息保存数据库
      this.$data.talkChat.teacherNumber = this.active;
      this.$data.talkChat.role = msg.role;
      this.$data.talkChat.contentType = msg.contentType;
      this.$data.talkChat.content = msg.content;
      this.$data.talkChat.fileUrl = msg.fileUrl;
      // this.$data.talkChat.gmtCreate = "2020-12-05 22:00:00";
      talkApi.send(this.$data.talkChat).then((res) => {
        this.$data.talkChat.gmtCreate = res.data.gmtCreate;
        this.chatInfoEn.msgList.push(this.$data.talkChat);
        // console.log("msg: ",res);
        this.$data.talkChat = {};
        this.scrollToBottom();
        this.getRecordList();
      });
    },

    /**
     * 跳到最下
     */
    scrollToBottom: function () {
      this.$nextTick(() => {
        var container = this.$el.querySelector("#common_chat_main");
        container.scrollTop = container.scrollHeight;
      });
    },
  },

  mounted() {
    // this.$data.timer = setInterval(this.getRecordList, 5000); // 5s刷新列表
  },

  beforeDestroy() {
    clearInterval(this.$data.timer);
    this.$data.timer = "";
  },
};
</script>

<style lang="less">
.sys {
  margin: -65px 0;
  color: #b0b0b0;
  font-size: 12px;
  text-align: center;
}

.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: left;
  line-height: 60px;
}
.teacherName {
  margin-left: 40px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
}

.imghead {
  font-size: 45px;
  float: left;
  margin-top: 23px;
  margin-right: 4px;
}

.first-p .name {
  float: left;
  /* font-size: 14px; */
  color: #000000;
  width: 50%;
  /* overflow: hidden; */
  /* text-align: left; */
}

.first-p .lastMsgTime {
  float: right;
  font-size: 14px;
  color: #949494;
}

.second-p {
  /* margin-bottom: -1px; */
  /* text-align: left; */
  padding: 0;
}

.second-p .lastMsgContent {
  display: inline-block;
  /* line-height: 20px; */
  width: 70%;
  /* margin-top: 2px; */
  font-size: 12px;
  color: #8d8d8d;
  /* overflow: hidden; */
}

.second-p .newMsgCount {
  line-height: 45px;
  position: relative;
  float: right;
}

.second-p .newMsgCount .el-badge__content {
  border: 1px solid rgba(0, 0, 0, 0);
}

.refreshBtn {
  width: 96%;
}

.el-form {
  margin-top: 10px;
  margin-bottom: -16px;
}

.el-card {
  margin: 5px;
  text-align: left;
  background: #f8f8f8;
  cursor: pointer; /* 鼠标变小手 */
}

.el-card:hover {
  background: #f1f1f1;
}

.active {
  background: #cecece;
}

.notice {
  margin-top: 50px;
  color: rgb(119, 119, 119);
}

.el-card__body {
  padding: 5px;
}

::-webkit-scrollbar {
  width: 0 !important;
}

::-webkit-scrollbar {
  width: 0 !important;
  height: 0;
}

aside {
  padding: 0;
  margin-bottom: 0px;
}

.common_chat-wrapper {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
  font-size: 12px;
  float: left;
  border: 0px;
  .common_chat-inner {
    width: 100%;
    height: 100%;
    .common_chat-main {
      position: relative;
      height: calc(~"100% - 190px");
      overflow-y: auto;
      overflow-x: hidden;
      .common_chat-main-header {
        padding-top: 14px;
        text-align: center;
        .el-button {
          padding: 0px;
          font-size: 12px;
          color: #8d8d8d;
        }
      }
      .common_chat-main-content {
        position: absolute;
        width: 100%;
        height: 100%;
        & > .inner {
          padding-bottom: 20px;
          .item {
            clear: both;
            overflow: hidden;
          }
          .receiver,
          .sender {
            margin-top: 18px;
            font-size: 12px;
            .avatar-wrapper {
              float: left;
              .kf-img {
                width: 40px;
                height: 40px;
              }
            }
            .info-wrapper {
              position: relative;
              text-align: left;
              font-size: 12px;
              .item-content {
                position: relative;
                max-width: 600px;
                // max-width: 60%;
                color: #3e3e3e;
                font-size: 13px;
                border-radius: 3px;
                .text {
                  line-height: 1.8;
                  white-space: normal;
                  word-wrap: break-word;
                  word-break: break-all;
                  //   padding: 10px 12px;
                  padding: 0 14px;
                  font-size: 18px;
                }
                .img {
                  max-width: 320px;
                  max-height: 240px;
                  white-space: normal;
                  word-wrap: break-word;
                  word-break: break-all;
                  padding: 5px;
                  cursor: pointer;
                }
                .preInput {
                  position: relative;
                  color: #8d8d8d;
                  img {
                    height: 15px;
                    position: relative;
                    top: 3px;
                  }
                }
                .issueList {
                  width: 250px;
                  padding: 10px;
                  .title {
                    position: relative;
                    .content {
                      position: absolute;
                      margin-top: -1px;
                      margin-left: 6px;
                    }
                  }
                  .el-collapse-item__wrap {
                    background: transparent;
                  }
                  .el-collapse {
                    border: 0px;
                    margin-top: 8px;
                    margin-bottom: -8px;
                    .el-collapse-item__header {
                      font-size: 13px;
                      background: transparent;
                      color: #f7455d;
                      padding-left: 5px;
                    }
                    .el-collapse-item__wrap {
                      .el-collapse-item__content {
                        font-size: 12px;
                        color: #3e3e3e;
                        padding-left: 5px;
                      }
                    }
                  }
                }
                .issueExtend {
                  width: 250px;
                  padding: 10px 10px 0px;
                  .main {
                    border-top: 1px solid #eeeff0;
                    margin-top: 10px;
                    padding-top: 10px;
                    p {
                      margin-bottom: 5px;
                    }
                    .el-button {
                      font-size: 12px;
                      color: #f7455d;
                    }
                  }
                }
                .issueResult {
                  width: 250px;
                  .main {
                    padding: 10px;
                  }
                  .footer {
                    border-top: 1px solid #eeeff0;
                    height: 30px;
                    .btn {
                      width: 60px;
                      margin: 0px 30px;
                      padding: 6px 0px;
                      display: inline-block;
                      text-align: center;
                      font-size: 10px;
                      color: #8d8d8d;
                      cursor: pointer;
                      position: relative;
                      &:first-child::after {
                        top: 4px;
                        right: -30px;
                        width: 1px;
                        height: 80%;
                        content: "";
                        position: absolute;
                        background-color: #eeeff0;
                        z-index: 0;
                      }
                    }
                    .iconfont {
                      font-size: 10px;
                      margin-right: 5px;
                    }
                  }
                }
              }
            }
          }
          .item.receiver {
            margin-left: 5px;
            .avatar-wrapper {
              margin-right: 15px;
              line-height: 15px;
            }
            .info-wrapper {
              // float: left;
              .item-content {
                float: left;
                color: #000000;
                background-color: #f9fbfc;
                border: 1px solid #ccc;
                &::before {
                  position: absolute;
                  top: -1px;
                  left: -10px;
                  width: 0px;
                  height: 0px;
                  content: "";
                  border-top: 0px;
                  border-right: 10px solid #ccc;
                  border-bottom: 5px solid transparent;
                  border-left: 0px;
                }
              }
            }
          }
          .item.sender {
            margin-right: 5px;
            .avatar-wrapper {
              float: right;
              margin-left: 15px;
              line-height: 15px;
            }
            .info-wrapper {
              // float: right;
              .item-content {
                float: right;
                background: #0095ff;
                border: 1px solid #0095ff;
                color: #ffffff;
                &::before {
                  position: absolute;
                  top: -1px;
                  right: -10px;
                  width: 0px;
                  height: 0px;
                  content: "";
                  border-top: 0px;
                  border-right: 0px;
                  border-bottom: 5px solid transparent;
                  border-left: 10px solid #0095ff;
                }
              }
            }
          }
        }
      }
    }
    .common_chat-footer {
      position: relative;
      width: 100%;
      border-top: 1px solid #ccc;
      .opr-wrapper {
        height: 20px;
        padding: 10px;
        text-align: left;
        & > .item {
          margin-right: 12px;
          float: left;
          font-weight: normal;
          text-decoration: blink;
          & > .iconfont {
            color: #aaa;
            font-size: 20px;
          }
        }
      }
      .input-wrapper {
        position: relative;
        padding: 2px 0px 0px 10px;
        .inputContent {
          width: 99%;
          padding: 2px;
          height: 150px;
          resize: none;
          overflow: auto;
          line-height: 1.5;
          outline: 0px solid transparent;
          text-align: left;
          font-size: 20px;
        }
        .shortcutPopover-wrapper {
          position: absolute;
          top: 30px;
          left: 10px;
          width: 440px;
          max-height: 80px;
          padding: 4px;
          font-size: 12px;
          overflow-y: auto;
          border: 1px solid #9b9aab;
          border-radius: 3px;
          background-color: #fff;
          cursor: pointer;
          p {
            padding: 4px;
            &.selected {
              background-color: #ded1cc;
            }
            .key {
              display: inline-block;
              width: 50px;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
            }
            .content {
              display: inline-block;
              width: 350px;
              margin-left: 10px;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
            }
            .highlight {
              color: #00a8d7;
            }
          }
        }
        .tips {
          position: absolute;
          top: 7px;
          left: 20px;
          width: auto;
          color: #8d8d8d;
        }
      }
      .send-btn {
        float: right;
        margin-right: 16px;
        &.off,
        &.end {
          background-color: #ccc;
          border-color: #ccc;
        }
      }
      .off-wrapper {
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
        background-color: rgba(255, 255, 255, 0.6);
        font-size: 14px;
        .content {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
        }
      }
    }
  }
}
.imgView-dialog {
  background: #00000080;
  height: 100%;
  .el-dialog {
    max-width: 75%;
    position: relative;
    background: transparent;
    box-shadow: none;
    .el-dialog__header {
      display: none;
    }
    .el-dialog__body {
      padding: 0px;
      text-align: center;
      position: relative;
      .header {
        text-align: right;
        position: relative;
        height: 0px;
        .fa-remove {
          font-size: 32px;
          color: white;
          position: relative;
          right: -50px;
          top: -30px;
          cursor: pointer;
        }
      }
      .main {
        .img {
          max-width: 100%;
          max-height: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>