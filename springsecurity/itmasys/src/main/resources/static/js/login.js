// console.log(document.getElementById("password").value);
// console.log(md5(document.getElementById("password").value));
layui.config({
    base: '/js/'
}).use(['sliderVerify', 'jquery', 'form','element'], function() {
    var sliderVerify = layui.sliderVerify,
        form = layui.form;

    //自定义验证规则
    form.verify({
        // nikename: function(value) {
        //     if (value.length < 5) {
        //         return '昵称至少得5个字符啊';
        //     }
        // },
        // pass: [/(.+){6,12}$/, '密码必须6到12位']
        nikename: function(value) {
            if (value.length < 0) {
                return '昵称至少得5个字符啊';
            }
        },
        pass: [/(.+){0,12}$/, '密码必须6到12位']
    });
    // form.on('submit(formDemo)',function (data) {
    //     layui.$.ajax({
    //         type: "POST",
    //         url: "/submitlogin",
    //         data: {
    //             username:document.getElementById("username").value,
    //             // password:md5(document.getElementById("password").value)
    //             password:document.getElementById("password").value
    //         },
    //         dataType: "json",
    //         success: function(data){
    //             layui.layer.msg(data,{icon:3})
    //         },
    //         error:function (data) {
    //             if(data.status==302)
    //                 location.href='/';
    //             console.log(data);
    //         }
    //     });
    //     return false;
    // });

    var slider = sliderVerify.render({
        elem: '#slider'
    })
});