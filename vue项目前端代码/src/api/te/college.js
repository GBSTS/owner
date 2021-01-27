import { param } from '@/utils'
import request from '@/utils/request'
export default {
    
    getAllCollege() {
        return request({
            url: '/te/college/getAllCollege',
            method: 'get'
          })
    },   

    //获取所有学院
    getList() {
        return request({
            url: '/te/college/getAllCollege',
            method: 'get'
          })
    },   
    //根据id获取学院
    getCollegeById(id) {
        return request({
            url: '/te/college/getCollegeById',
            method: 'get',
            params: { id }
          })
    },

    //学院分页
    pageList(page,limit,collegeQuery) {
        return request({
            url: `/te/college/${page}/${limit}`,
            method: 'post',
            data: collegeQuery
        })
    },

    //修改学院
    updateCollege(college) {
        return request({
            url: '/te/college/updateCollege',
            method: 'put',
            data: college
        })
    },
    
    //删除学院
    delCollegeById(id) {
        return request({
            url: '/te/college/delCollegeById',
            method: 'delete',
            params: { id }
        })
    },

    //批量删除学院
    delCollegesByList(idList) {
        return request({
            url: '/te/college/delCollegeByList',
            method: 'delete',
            data: idList
        })
    },

    //添加学院
    saveCollege(college) {
        return request({
            url: '/te/college/saveCollege',
            method: 'post',
            data: college
        })
    },

}
