//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element;

});

layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#test'
        , url: '../../static/json/jj.json'
        ,cols: [[
            {type:'checkbox'}
            ,{field:'id', width:150, title: '学号', sort: true}
            ,{field:'username', width:150, title: '姓名'}
            ,{field:'sex', width:130, title: '性别', sort: true}
            ,{field:'city', width:160, title: '年级'}
            ,{field:'city', width:160, title: '专业'}
            ,{field:'experience', width:180, title: '班级'}
            ,{field:'sign', title: '二级学院', width: 350}
        ]]
        ,page: true
    });
});