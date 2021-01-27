import { param } from '@/utils'
import request from '@/utils/request'
export default {

    //获取聊天列表
    getRecordList(isTeacher,teacherNumber) {
        return request({
            url: '/ma/talk-record/list',
            method: 'post',
            params: {isTeacher,teacherNumber}
        })
    },

    //获取聊天记录
    getChat(teacherNumber) {
        return request({
            url: `/ma/talk-record/chat/${teacherNumber}`,
            method: 'post'
        })
    },

    //查询列表
    select(key) {
        return request({
            url: `/ma/talk-record/select/${key}`,
            method: 'post'
        })
    },

    //发送消息
    send(chat) {
        return request({
            url: `/ma/talk-record/send`,
            method: 'post',
            data: chat
        })
    },

}
