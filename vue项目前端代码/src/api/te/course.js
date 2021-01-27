import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllCourse() {
        return request({
            url: '/te/course/getAllCourse',
            method: 'get'
          })
    },   
    getCourseByName(name) {
        return request({
            url: '/te/course/getCourseByName',
            method: 'get',
            params: { name }
        })
    },
    checkCourseByName(name) {
        return request({
            url: '/te/course/checkCourseByName',
            method: 'get',
            params: { name }
        })
    },

    //根据id获取课程
    geCourseById(id) {
        return request({
            url: '/te/course/getCourseById',
            method: 'get',
            params: { id }
          })
    },

    //课程分页
    pageList(page,limit,courserQuery) {
        return request({
            url: `/te/course/${page}/${limit}`,
            method: 'post',
            data: courserQuery
        })
    },

    //修改课程
    updateCourse(course) {
        return request({
            url: '/te/course/updateCourse',
            method: 'put',
            data: course
        })
    },
    
    //删除课程
    delCourseById(id) {
        return request({
            url: '/te/course/delCourseById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除课程
    delCoursesByList(idList) {
        return request({
            url: '/te/course/delCoursesByList',
            method: 'delete',
            data: idList
        })
    },

    //添加课程
    saveCourse(course) {
        return request({
            url: '/te/course/saveCourse',
            method: 'post',
            data: course
        })
    },

}
