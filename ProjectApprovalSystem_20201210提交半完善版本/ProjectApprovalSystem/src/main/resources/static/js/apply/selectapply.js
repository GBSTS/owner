layui.use(['table', 'element'], function() {
    var table = layui.table,
        element = layui.element;
    var $ = layui.$;

    table.render({
        elem: '#test',
        url: '/apply/getItemApplyHistory',
        method:"post",
        page: true,
        toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar:["filter"],
        cols: [
                [{
                    type: 'checkbox',
                    fixed: 'left',
                    width: 50
                },{
                    field: 'applyId',
                    title: '申请编号',
                    fixed: 'left'
                },
                {
                    field: 'itemId',
                    title: '项目编号',
                    fixed: 'left'
                },
                {
                    field: 'itemName',
                    title: '项目名',
                    align: "center"
                },
                {
                    field: 'itemMoney',
                    title: '项目资金',
                    align: "center"
                },
                {
                    field: 'itemDetailed',
                    title: '项目简介',
                    align: "center"
                },
                {
                    field: 'state',
                    title: '审批状态',
                    align: "center",
                    templet:function (d) {
                        if(d.state=="等待审批")
                            return '<button id="search" type="button" class="layui-btn layui-btn-xs layui-btn-normal" lay-event="search">等待审批</button>';
                        else if(d.state=="审批通过")
                            return '<button id="search" type="button" class="layui-btn layui-btn-xs layui-btn-warm" lay-event="search">审批通过</button>';
                        else
                            return '<button id="search" type="button" class="layui-btn layui-btn-xs layui-btn-danger" lay-event="search">拒绝申请</button>';
                    }
                },
                {
                    field: 'reason',
                    title: '原因',
                    align: "center"
                },
                    {
                        title: '操作',
                        align:'center',
                        fixed: 'right',
                        toolbar: '#barDemo',
                        width: 100
                    }
            ]
        ]
    });

    //头工具栏事件
    table.on('toolbar(test)', function(obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;//当前被选中行的数据集合
        switch (obj.event) {
            case "reload":
                table.reload('test', {});
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj) {
        var data = obj.data;
        if (obj.event === 'withdraw') {
            if(data.state=="等待审批"){

                var formData = new FormData();
                formData.append("applyId",data.applyId);
                $.ajax({//需要服务器的支持
                    url: "/apply/withdrawApply",
                    type: "POST",
                    async:false,
                    cache:false,
                    contentType: false,
                    processData: false,
                    data:formData,
                    success: function(result) {//result中包含vm_left,vm_right的json数据
                        layer.msg(result);
                        if(result=="撤回申请成功")
                            obj.del();
                    },
                    error:function () {
                        layer.msg("网络异常");
                    }
                });
            }else{
                layer.msg("当前状态不同撤回");
            }
        }
    });
});
