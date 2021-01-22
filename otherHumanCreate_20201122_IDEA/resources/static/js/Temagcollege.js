layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#test'
        ,url:'../../static/json/lay.json'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,title: '数据表'
        ,height:600
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', title: '学院ID', width:80,  fixed: 'left'}
            ,{field: 'username', title: '学院名', width:80}
            ,{field: 'sex', title: '专业', width:80}
            ,{field: 'city', title: '院长', width:80}
            ,{field: 'sign', title: '副院长', width: 177}
            ,{field: 'experience', title: '负责人', width: 80}
            ,{field: 'score', title: '待定', width: 80}
            ,{field: 'classify', title: '待定', width: 80}
            ,{field: 'wealth', title: '待定', width: 135}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,page: true
    });

    //头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选': '未全选');
                break;

            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.prompt({
                formType: 2
                ,value: data.email
            }, function(value, index){
                obj.update({
                    email: value
                });
                layer.close(index);
            });
        }
    });
});