import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    saveDepartmentclassStudent(departmentclassId,courseclassId) {
        return request({
            url: '/st/courseclass-student/saveDepartmentclassStudent',
            method: 'post',
            params: {departmentclassId, courseclassId}
          })
    },   
    delByCourseclassId(id) {
        return request({
            url: '/st/courseclass-student/delByCourseclassId',
            method: 'delete',
            params: {id}
          })
    },   
    delByCourseclassIdList(idList) {
        return request({
            url: '/st/courseclass-student/delByCourseclassIdList',
            method: 'delete',
            data: idList
          })
    },   

}
