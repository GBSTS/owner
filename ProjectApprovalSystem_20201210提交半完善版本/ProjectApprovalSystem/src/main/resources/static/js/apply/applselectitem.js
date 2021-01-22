layui.use(['table', 'layer','element','form'], function() {
    var table = layui.table,
        layer = layui.layer,
        element = layui.element,
        form = layui.form;
    var $ = layui.$;

    table.render({
        elem: '#test',
        url: '/apply/getItemSelectionList',
        method:"post",
        page: true,
        where:{
            itemId:-1,
            companyId:-1
        },
        toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar:["filter"],
        cols: [
            [{
                type: 'checkbox',
                fixed: 'left',
                width: 50
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
                    field: 'companyId',
                    title: '公司编号',
                    align: 'center'
                },
                {
                    field: 'companyName',
                    title: '公司名',
                    align: "center"
                },
                {
                    field: 'currentHumanNumber',
                    title: '当前人数',
                    align: "center"
                },
                {
                    field: 'itemDeadline',
                    title: '项目截止时间',
                    align:"center"
                },
                {
                    field: 'itemDetailed',
                    title: '项目详细',
                    align:'center',
                    templet: function(d) {
                        return '<button id="search" type="button" class="layui-btn layui-btn-xs layui-btn-normal item-detailed" msg="'+d.itemDetailed+'" lay-event="search">项目详细</button>';
                    }
                },
                {
                    title: '操作',
                    align:'center',
                    fixed: 'right',
                    toolbar: '#barDemo',
                    width: 100
                }
            ]
        ],
        done: function(res, curr, count) {
            //项目详细
            $(".item-detailed").each(function (i) {
                this.onclick=function(){
                    layer.open({
                        title: '项目详细',
                        content: '<div>'+this.getAttribute("msg")+'</div>'
                    });
                };
            });
        }
    });
    //头工具栏事件
    table.on('toolbar(test)', function(obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;//当前被选中行的数据集合
        switch (obj.event) {
            case 'search':
                var itemId=$("#itemId").val()=="" ? -1 : parseInt($("#itemId").val());
                var companyId=$("#companyId").val()=="" ? -1 : parseInt($("#companyId").val());
                table.reload('test', {
                    where: {
                        itemId: itemId,
                        companyId: companyId
                    }
                });
                break;
            case "reload":
                table.reload('test', {
                    where: {
                        itemId: -1,
                        companyId: -1
                    }
                });
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj) {
        var data = obj.data;
        if (obj.event === 'apply') {
            var formData = new FormData();
            formData.append("itemId",data.itemId);
            $.ajax({//需要服务器的支持
            	url: "/apply/applyItem",
                type: "POST",
                async:false,
                cache:false,
                contentType: false,
                processData: false,
                data:formData,
            	success: function(result) {//result中包含vm_left,vm_right的json数据
            		layer.msg(result)
            	},
                error:function () {
                    layer.msg("网络异常");
                }
            });
        }
    });
});
