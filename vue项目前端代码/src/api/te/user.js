import { param } from '@/utils'
import request from '@/utils/request'
export default {

    //修改密码
    changePwd(passwordForm) {
        return request({
            url: '/te/user/changePwd',
            method: 'post',
            data: passwordForm
          })
    },

}
