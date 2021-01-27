import { param } from '@/utils'
import request from '@/utils/request'
export default {


    //通过用户id获取申请
    getApplyByUserId(id) {
        return request({
            url: '/ma/user-apply/getApplyByUserId',
            method: 'get',
            params: { id }
        })
    },

    //创建或更新申请
    saveOrUpdateApply(apply) {
        return request({
            url: '/ma/user-apply/saveOrUpdateApply',
            method: 'post',
            data: apply
        })
    },

    //获取所有数据
    getApplyList() {
        return request({
            url: '/ma/user-apply/list',
            method: 'get'
        })
    },

    //通过申请
    passApply(applyId) {
        return request({
            url: '/ma/user-apply/pass',
            method: 'get',
            params: { applyId }
        })
    },

    //批量通过申请
    passApplys(applyIds) {
        return request({
            url: '/ma/user-apply/passbatch',
            method: 'post',
            data: applyIds
        })
    },


    //获取所有数据,模糊查询
    getPageList(page, limit, userApplyQuery) {
        return request({
            url: `/ma/user-apply/list/${page}/${limit}`,
            method: 'post',
            data: userApplyQuery
        })
    },

    //查询学校
    getSchoolList() {
        return request({
            url: `/te/school/list`,
            method: 'get'
        })
    },

    //查询驳回类型原因
    getRejectReasonList() {
        return request({
            url: `/ma/user-reject-reason/list`,
            method: 'get'
        })
    },

    //驳回申请
    rejectApply(reject) {
        return request({
            url: '/ma/user-reject/reject',
            method: 'post',
            data: reject
        })
    },

    //批量驳回申请
    rejectApplys(rejectIds) {
        return request({
            url: '/ma/user-reject/rejectbatch',
            method: 'post',
            data: rejectIds
        })
    },

}
