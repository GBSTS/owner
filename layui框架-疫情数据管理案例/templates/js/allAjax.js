var timeoutlogin="/login";

$.ajaxSetup({
    contentType : "application/x-www-form-urlencoded;charset=utf-8",
    cache : false,
    complete : function(data, TS) {
        //对返回的数据data做判断
       // debugger;
      //  console.log(data.status);
        //403是后台拦截器那儿定义的，
        if (data.status == 403) {
            alert("登陆已经失效，请重新登录")
            //session过期的话，就location到一个页面
            location.href=timeoutlogin;
            return;
        }
    }
})