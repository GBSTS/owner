import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllDepartment() {
        return request({
            url: '/te/department/getAllDepartment',
            method: 'get'
          })
    },  
    
    //获取所有系
    getList() {
        return request({
            url: '/te/department/getAllDepartment',
            method: 'get'
          })
    }, 
        //根据id获取系
        getDepartmentById(id) {
        return request({
            url: '/te/department/getDepartmentById',
            method: 'get',
            params: { id }
          })
    },

    //系分页
    pageList(page,limit,departmentQuery) {
        return request({
            url: `/te/department/${page}/${limit}`,
            method: 'post',
            data: departmentQuery
        })
    },

    //修改系
    updateDepartment(department) {
        return request({
            url: '/te/department/updateDepartment',
            method: 'put',
            data: department
        })
    },
    
    //删除系
    delDepartmentById(id) {
        return request({
            url: '/te/department/delDepartmentById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除系
    delDepartmentsByList(idList) {
        return request({
            url: '/te/department/delDepartmentByList',
            method: 'delete',
            data: idList
        })
    },

    //添加系
    saveDepartment(department) {
        return request({
            url: '/te/department/saveDepartment',
            method: 'post',
            data: department
        })
    },

}
