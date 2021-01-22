function sendNumber(){
    var email = document.getElementById("yzm-header").value;
    if(email==""){
        layui.layer.msg("邮箱不能为空",{icon:5});
        return ;
    }
    var emreg=/^\w{3,}(\.\w+)*@[A-z0-9]+(\.[A-z]{2,5}){1,2}$/;
    if(!emreg.test(email)){
        layui.layer.msg("邮箱地址格式不正确",{icon:5});
        return ;
    }
    var formData = new FormData();
    formData.append("email",email);
    layui.$.ajax({
        url:"/getforgetpasswordnumber",
        type: "POST",
        data:formData,
        cache:false,         //不设置缓存
        processData: false,  // 不处理数据
        contentType: false,   // 不设置内容类型,
        success:function(str){
            if(str=="验证码已经发送，请注意查看。"){
                layui.layer.msg(str,{icon:1});
            }else{
                layui.layer.msg(str,{icon:5});
            }
        },
        error:function () {
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg("网络异常");
            });
        }
    });
}

function resetpassword(){
    var username = document.getElementById("username").value;
    var email = document.getElementById("yzm-header").value;
    var yzm = document.getElementById("yzm").value;
    var password = document.getElementById("L_pass").value;
    if(username==""||email==""||yzm==""||password=="")
        return ;
    var formData = new FormData();
    formData.append("username",username);
    formData.append("email",email);
    formData.append("yzm",yzm);
    formData.append("password",password);
    layui.$.ajax({
        url:"/submitforgetpassword",
        type: "POST",
        data:formData,
        cache:false,         //不设置缓存
        processData: false,  // 不处理数据
        contentType: false,   // 不设置内容类型,
        success:function(str){
            if(str=="重置密码成功"){
                layui.layer.msg(str,{icon:1});
                location.href="/login";
            }else{
                layui.layer.msg(str,{icon:5});
            }
        },
        error:function () {
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg("网络异常");
            });
        }
    });

}

layui.config({
    base: 'js/'
}).use(['sliderVerify', 'jquery', 'form','element'], function() {
    var sliderVerify = layui.sliderVerify,
        form = layui.form,
        $=layui.$;

    //自定义验证规则
    form.verify({
        nikename: function(value) {
            if (value.length < 5) {
                return '昵称至少得5个字符啊';
            }
        },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        repass: function(value) {
            if ($('#L_pass').val() != $('#L_repass').val()) {
                return '两次密码不一致';
            }
        }
    });



    var slider = sliderVerify.render({
        elem: '#slider'
    })
})