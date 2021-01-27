import { param } from '@/utils'
import request from '@/utils/request'
export default {

    //通过用户id获取申请
    getRepairByUserId(id) {
        return request({
            url: '/ma/repair/getRepairByUserId',
            method: 'get',
            params: { id }
        })
    },

    //创建或更新申请
    saveOrUpdateRepair(repair) {
        return request({
            url: '/ma/repair/saveOrUpdateRepair',
            method: 'post',
            data: repair
        })
    },

    //获取所有数据
    getAllList() {
        return request({
            url: '/ma/repair/list',
            method: 'get'
        })
    },

    //获取所有数据,模糊查询
    getPageList(page, limit, repairQuery) {
        return request({
            url: `/ma/repair/list/${page}/${limit}`,
            method: 'post',
            data: repairQuery
        })
    },

    //获取维修类型列表
    getRepairTypeList() {
        return request({
            url: `/ma/repair-describe/list`,
            method: 'get'
        })
    },

    //接收维修
    receive(repairId, userId) {
        return request({
            url: `/ma/repair/receive/${repairId}/${userId}`,
            method: 'get'
        })
    },

    //批量接收维修
    receiveBatch(repairIds, userId) {
        return request({
            url: `/ma/repair/receivebatch/${userId}`,
            method: 'post',
            data: repairIds
        })
    },

    //处理维修
    repair(repairId) {
        return request({
            url: `/ma/repair/repair/${repairId}`,
            method: 'get'
        })
    },

    //批量处理维修
    repairBatch(repairIds) {
        return request({
            url: `/ma/repair/repairbatch`,
            method: 'post',
            data: repairIds
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
