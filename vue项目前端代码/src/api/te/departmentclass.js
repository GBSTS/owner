import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllDepartmentclass() {
        return request({
            url: '/te/departmentclass/getAllDepartmentclass',
            method: 'get'
          })
    },   
    getAllDepartmentclassByMajorId(id) {
        return request({
            url: '/te/departmentclass/getAllDepartmentclassByMajorId',
            method: 'get',
            params: { id }
          })
    },

    //根据id获取行政班
    getDepartmentclassById(id) {
        return request({
            url: '/te/departmentclass/getDepartmentclassById',
            method: 'get',
            params: { id }
          })
    },

    //分页
    pageList(page,limit,departmentclassQuery) {
        return request({
            url: `/te/departmentclass/${page}/${limit}`,
            method: 'post',
            data: departmentclassQuery
        })
    },

    //修改行政班
    updateDepartmentclass(departmentclass) {
        return request({
            url: '/te/departmentclass/updateDepartmentclass',
            method: 'put',
            data: departmentclass
        })
    },
    
    //删除行政班
    delDepartmentclassById(id) {
        return request({
            url: '/te/departmentclass/delDepartmentclassById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除行政班
    delDepartmentclasssByList(idList) {
        return request({
            url: '/te/departmentclass/delDepartmentclasssByList',
            method: 'delete',
            data: idList
        })
    },

    //添加行政班
    saveDepartmentclass(departmentclass) {
        return request({
            url: '/te/departmentclass/saveDepartmentclass',
            method: 'post',
            data: departmentclass
        })
    },

}
