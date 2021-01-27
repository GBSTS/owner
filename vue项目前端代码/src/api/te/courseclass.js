import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getCourseclassById(id) {
        return request({
            url: '/te/courseclass/getCourseclassById',
            method: 'get',
            params: { id }
        })
    },

    //课程班分页
    pageList(page,limit,courseclassQuery) {
        return request({
            url: `/te/courseclass/${page}/${limit}`,
            method: 'post',
            data: courseclassQuery
        })
    },

    //课程班修改
    updateCourseclass(courseclass) {
        return request({
            url: '/te/courseclass/updateCourseclass',
            method: 'put',
            data: courseclass
        })
    },

    //课程班添加
    saveCourseclass(courseclass) {
        return request({
            url: '/te/courseclass/saveCourseclass',
            method: 'post',
            data: courseclass
        })
    },

    //课程班删除
    delCourseclassById(id) {
        return request({
            url: '/te/courseclass/delCourseclassById',
            method: 'delete',
            params: { id }
        })
    },

    //课程班批量删除
    delCourseclassByIdList(idList) {
        return request({
            url: '/te/courseclass/delCourseclassByIdList',
            method: 'delete',
            data: idList
        })
    },

}
