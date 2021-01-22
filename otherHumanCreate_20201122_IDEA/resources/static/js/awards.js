
//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element;

});


//Demo
layui.use('form', function(){
    var form = layui.form;

    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
})


layui.use('laydate', function() {
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#test2' //指定元素
    });


    layui.use('upload', function () {
        var upload = layui.upload;

        //拖拽上传
        upload.render({
            elem: '#test10'
            , url: 'https://httpbin.org/post' //改成您自己的上传接口
            , done: function (res) {
                layer.msg('上传成功');
                layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.files.file);
                console.log(res)
            }
        });


        upload.render({
            elem: '#test20'
            , url: '127.0.0.1 '//改成您自己的上传接口
            , done: function (res) {
                layer.msg('上传成功');
                console.log(res)
            }
        });
    });
})