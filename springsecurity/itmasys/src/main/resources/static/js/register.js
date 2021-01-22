layui.config({
    base: '/js/'
}).use(['sliderVerify', 'jquery', 'form','element'], function() {
    var sliderVerify = layui.sliderVerify,
        form = layui.form,
        $=layui.$;

    //自定义验证规则
    form.verify({
        nikename: function(value) {
            var judge=true;
            var hrefStr = "/queryusernamunique"+"?username="+document.getElementById("username").value;
            $.ajax({
                url:hrefStr,
                success: function(str){
                    if(str=="false")
                        judge=false;
                },
                async:false
            });
            if(!judge){
                return "当前用户名已经被注册";
            }
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

    form.on('submit(formDemo)',function (data) {
        // /submitlogin
        layui.$.ajax({
            type: "POST",
            url: "/submitregister",
            data: {
                username:document.getElementById("username").value,
                password:md5(document.getElementById("L_pass").value),
                phone:document.getElementById("phone").value,
                email:document.getElementById("email").value,
                rolename:layui.$("input[type='radio']:checked").val()
            },
            dataType: "json",
            success: function(data){
                layui.layer.msg(data,{icon:3})
            },
            error:function (data) {
                layui.layer.msg("网络异常",{icon:7});
            }
        });
        return false;
    });

    var slider = sliderVerify.render({
        elem: '#slider'
    })
})