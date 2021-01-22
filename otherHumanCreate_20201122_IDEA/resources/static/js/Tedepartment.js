layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#test'
        ,toolbar:'#toolbarDemo'
        ,url:'../../static/json/lay.json'
        ,height:600
        ,cols: [[
            {type:'checkbox'}
            ,{field:'id', width:80, title: '系ID', sort: true}
            ,{field:'username', width:80, title: '专业'}
            ,{field:'sex', width:80, title: '系主任', sort: true}
            ,{field:'city', width:80, title: '待定'}
            ,{field:'sign', title: '待定', minWidth: 100}
            ,{field:'experience', width:80, title: '待定', sort: true}
            ,{field:'score', width:80, title: '待定', sort: true}
            ,{field:'classify', width:80, title: '待定'}
            ,{field:'wealth', width:135, title: '待定', sort: true}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,page: true
    });




    //头工具栏事件
    table.on('toolbar(test)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'delete'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        }
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
        }
    });

});