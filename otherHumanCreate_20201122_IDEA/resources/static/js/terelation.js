layui.use('table', function(){
    var table = layui.table;
    table.render({
        height:520
        ,elem: '#test'
        ,url:'../../static/json/jj.json'
        ,toolbar: true
        ,title: '教师数据表'
        ,totalRow: true
        ,cols: [[
            {field:'id', title:'教师编号', width:120, fixed: 'left', unresize: true, sort: true, totalRowText: '合计行'}
            ,{field:'tename', title:'教师名', width:120, edit: 'text'}
            ,{field:'sex', title:'性别', width:80, edit: 'text'}
            ,{field:'academy', title:'院别', width:180, totalRow: true, templet:function(){
                    return'<select name="logins" class="sel_xlk" lay-filter="stateSelect" lay-verify="required" data-state="' + d.logins + '" data-value="' + d.id + '" >' +
                        '        <option value="0">请选择</option>' +
                        '         <option value="1">计算机科学与工程学院</option>' +
                        '         <option value="2">生命科学学院</option>' +
                        '         <option value="3">体育学院</option>'
                    '    </select>'
                }, edit: 'text'}
            ,{field:'department', title:'系别', width:180, edit: 'text'}
            ,{field:'jointime', title:'加入时间',sort: true}
        ]]
        ,id:'testReload'
        ,page: true
        ,response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        }
        ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.status, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.total, //解析数据长度
                "data": res.rows.item //解析数据列表
            };
        }
    });
    //考勤状态改变
    form.on('select(stateSelect)', function (data) {//修改类型
        let id = data.elem.dataset.value; //当前数据的id
        let logins = data.elem.value; //当前字段变化的值
        //修改状态
        //....这里省略一个ajax请求...
        // 传值：表单变化后的值传递到后台数据库进行实时修改，例如，根据id修改这条数据的状态。
    });

    var $ = layui.$, active = {
        reload: function(){
            var demoReload = $('#demoReload');

            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    key: {
                        id: demoReload.val()
                    }
                }
            }, 'data');
        }
    };
    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});