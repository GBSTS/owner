import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllMajor() {
        return request({
            url: '/te/major/getAllMajor',
            method: 'get'
          })
    },   
    getAllMajorByDepartmentId(id) {
        return request({
            url: '/te/major/getAllMajorByDepartmentId',
            method: 'get',
            params: { id }
          })
    },   

    getMajorById(id) {
        return request({
            url: '/te/major/getMajorById',
            method: 'get',
            params: { id }
          })
    },

    //专业分页
    pageList(page,limit,majorQuery) {
        return request({
            url: `/te/major/${page}/${limit}`,
            method: 'post',
            data: majorQuery
        })
    },

    //修改专业
    updateMajor(major) {
        return request({
            url: '/te/major/updateMajor',
            method: 'put',
            data: major
        })
    },
    
    //删除专业
    delMajorById(id) {
        return request({
            url: '/te/major/delMajorById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除专业
    delMajorsByList(idList) {
        return request({
            url: '/te/major/delMajorByList',
            method: 'delete',
            data: idList
        })
    },

    //添加专业
    saveMajor(major) {
        return request({
            url: '/te/major/saveMajor',
            method: 'post',
            data: major
        })
    },

}
