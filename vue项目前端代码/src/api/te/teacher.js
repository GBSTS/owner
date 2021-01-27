import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllTeacher() {
        return request({
            url: '/te/teacher/getAllTeacher',
            method: 'get'
          })
    },

    //根据id获取教师
    getTeacherById(id) {
        return request({
            url: '/te/teacher/getTeacherById',
            method: 'get',
            params: { id }
          })
    },

    //教师分页
    pageList(page,limit,teacherQuery) {
        return request({
            url: `/te/teacher/${page}/${limit}`,
            method: 'post',
            data: teacherQuery
        })
    },

    //修改教师
    updateTeacher(teacher) {
        return request({
            url: '/te/teacher/updateTeacher',
            method: 'put',
            data: teacher
        })
    },
    
    //删除教师
    delTeacherById(id) {
        return request({
            url: '/te/teacher/delTeacherById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除教师
    delTeachersByList(idList) {
        return request({
            url: '/te/teacher/delTeachersByList',
            method: 'delete',
            data: idList
        })
    },

    //添加教师
    saveTeacher(teacher) {
        return request({
            url: '/te/teacher/saveTeacher',
            method: 'post',
            data: teacher
        })
    },

    getTeacherByName(name) {
        return request({
            url: '/te/teacher/getTeacherByName',
            method: 'get',
            params: { name }
        })
    },
    checkTeacherByName(name) {
        return request({
            url: '/te/teacher/checkTeacherByName',
            method: 'get',
            params: { name }
        })
    }

}
