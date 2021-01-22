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