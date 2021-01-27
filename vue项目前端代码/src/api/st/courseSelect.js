import { param } from '@/utils'
import request from '@/utils/request'
export default {

    //开启选课
    startSelect(begin,end) {
        return request({
            url: '/st/courseSelect/startSelect',
            method: 'get',
            params: { begin, end }
          })
    },

    //关闭选课
    closeSelect() {
        return request({
            url: '/st/courseSelect/closeSelect',
            method: 'get'
          })
    },

    //获取选课时间
    getSelectTime() {
        return request({
            url: '/st/courseSelect/getSelectTime',
            method: 'get'
          })
    },

    //获取flag
    getFlag() {
        return request({
            url: '/st/courseSelect/getFlag',
            method: 'get'
          })
    },

    //根据id获取选课
    getCourseclassSelectById(studentId, courseclassId) {
        return request({
            url: '/st/courseSelect/getCourseclassSelectById',
            method: 'get',
            params: { studentId, courseclassId }
          })
    },

    //获取所有未确定选课
    getAllCourseclassSelect(selectQuery) {
        return request({
            url: '/st/courseSelect/getAllCourseclassSelect',
            method: 'post',
            data: selectQuery 
          })
    },

    //不确定课程班更新为已确定
    updateConfirmToTrue() {
        return request({
            url: '/st/courseSelect/updateConfirmToTrue',
            method: 'put'
        })
    },

    //学生选课添加记录
    saveCourseclassStudent(studentId, courseclassId) {
        return request({
            url: '/st/courseSelect/saveCourseclassStudent',
            method: 'post',
            params: { studentId, courseclassId }
        })
    },

    //获取学生任选结果
    getSelectedCourseclass(studentId) {
        return request({
            url: '/st/courseSelect/getSelectedCourseclass',
            method: 'get',
            params: { studentId }
          })
    },

    //获取学生退选结果
    getQuitCourseclass(studentId) {
        return request({
            url: '/st/courseSelect/getQuitCourseclass',
            method: 'get',
            params: { studentId }
          })
    },

    //学生选课添加记录
    delCourseclassStudent(studentId, courseclassId) {
        return request({
            url: '/st/courseSelect/delCourseclassStudent',
            method: 'delete',
            params: { studentId, courseclassId }
        })
    },



}
