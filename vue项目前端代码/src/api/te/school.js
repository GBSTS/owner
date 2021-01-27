import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    //获取所有学院
    getList() {
        return request({
            url: '/te/school/getAllSchool',
            method: 'get'
          })
    },   

}
