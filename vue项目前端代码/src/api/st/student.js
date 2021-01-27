import { param } from '@/utils'
import request from '@/utils/request'
export default {

    //根据id获取学生
    getStudentById(id) {
        return request({
            url: '/st/student/getStudentById',
            method: 'get',
            params: { id }
          })
    },

    //学生分页
    pageList(page,limit,studentQuery) {
        return request({
            url: `/st/student/${page}/${limit}`,
            method: 'post',
            data: studentQuery
        })
    },

    //修改学生
    updateStudent(student) {
        return request({
            url: '/st/student/updateStudent',
            method: 'put',
            data: student
        })
    },
    
    //删除学生
    delStudentById(id) {
        return request({
            url: '/st/student/delStudentById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除学生
    delStudentsByList(idList) {
        return request({
            url: '/st/student/delStudentsByList',
            method: 'delete',
            data: idList
        })
    },

    //添加学生
    saveStudent(student) {
        return request({
            url: '/st/student/saveStudent',
            method: 'post',
            data: student
        })
    },

}
