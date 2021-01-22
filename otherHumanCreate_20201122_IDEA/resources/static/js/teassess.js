layui.use('table', function(){
    var table = layui.table;

    table.render({
        height:350
        ,elem: '#test'
        ,url:'/test/table/demo1.json'
        ,cols: [[
            {field:'id', title:'ID', width:120, fixed: 'left', unresize: true, sort: true}
            ,{field:'user', title:'用户ID', width:120}
            ,{field:'time', title:'申请时间', width:120, sort: true}
            ,{field:'context', title:'维护内容', width:300}
            ,{field:'condition', title:'维护情况'}
        ]]
        ,page: true
    });
    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(test)', function(obj){
        var context = obj.context;

        layer.alert(JSON.stringify(context), {
            title: '当前需维护内容：'
        });

        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

});

//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element;

});