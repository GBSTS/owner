layui.use('table', function () {
    var table = layui.table;

    //温馨提示：默认由前端自动合计当前行数据。从 layui 2.5.6 开始： 若接口直接返回了合计行数据，则优先读取接口合计行数据。
    //详见：https://www.layui.com/doc/modules/table.html#totalRow
    table.render({
        elem: '#test'
        , url: '../../static/json/jj.json'
        , toolbar: '#toolbarDemo'
        , title: '用户数据表'
        , totalRow: true
        , cols: [[
            { type: 'checkbox', fixed: 'left' }
            , { field: 'id', title: '学号', width: 80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计' }
            , { field: 'username', title: '姓名', width: 120, edit: 'text' }
            , {
                field: 'sex', title: '性别', width: 150, edit: 'text', templet: function (res) {
                    return '<em>' + res.email + '</em>'
                }
            }
            , { field: 'ip', title: '宿舍号', width: 120 }
            , { field: 'city', title: '城市', width: 100 }
            , { field: 'phone', title: '电话', width: 80, sort: true, totalRow: true }
            , { field: 'joinTime', title: '加入时间', width: 120 }
            , { fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150 }
        ]]
        , page: true
    });

    //工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选')
                break;
        };
    });
});