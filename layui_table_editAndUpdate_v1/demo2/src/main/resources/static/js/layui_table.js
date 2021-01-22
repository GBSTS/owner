layui.use(['table','element'], function(){
    var table = layui.table,element = layui.element,$ = layui.$;
    var tabledata;

    var ta = table.render({
        elem: '#demo',
        height:'full-20',
        cellMinWidth: 80,
        limit:100000,//在当前项目的功能为限制能够显示或修改的上限
        url: '/getdata', //数据接口
        method: 'post', //默认：get请求
        toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
        cols: [[
            {type: 'checkbox'},
            {field: 'col1', title: 'col1', edit:'text'},
            {field: 'col2', title: 'col2', edit:'text'},
            {field: 'col3', title: 'col3', edit:'text'}
        ]],
        done:function(res,curr,count){
            tabledata = res.data;
        }
    });

    //头工具栏事件
    table.on('toolbar(test)', function(obj){
        var emptyObj={"col1":"","col2":"","col3":""};
        switch(obj.event){
            case 'delRow':
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if(data.length==0){
                    layer.msg("选中为空，不能够删除",{icon:7});
                    return ;
                }
                layer.confirm('确认删除行么', function(index){
                    var formData = new FormData();
                    for(var i=0;i<data.length;i++){//添加上传数据
                        formData.append("adtos["+i+"].col1",data[i].col1.toString());
                        formData.append("adtos["+i+"].col2",data[i].col2.toString());
                        formData.append("adtos["+i+"].col3",data[i].col3.toString());
                    }
                    $.ajax({
                        url: "/deletedata",
                        type: "POST",
                        data:formData,
                        cache:false,         //不设置缓存
                        processData: false,  // 不处理数据
                        contentType: false,   // 不设置内容类型
                        success:function(data){layer.msg("删除成功",{icon:1});},
                        error:function(){layer.msg('网络异常',{icon:2});}
                    });
                    table.reload("demo");
                    layer.close(index);
                });
                break;
            case 'update':
                var formData = new FormData();
                for(var i=0;i<tabledata.length;i++){//添加上传数据
                    formData.append("adtos["+i+"].col1",tabledata[i].col1.toString());
                    formData.append("adtos["+i+"].col2",tabledata[i].col2.toString());
                    formData.append("adtos["+i+"].col3",tabledata[i].col3.toString());
                }
                $.ajax({
                    url: "/updatedata",
                    type: "POST",
                    data:formData,
                    cache:false,         //不设置缓存
                    processData: false,  // 不处理数据
                    contentType: false,   // 不设置内容类型
                    success:function(data){layer.msg("修改成功",{icon:1});},
                    error:function(){layer.msg('网络异常',{icon:2});}
                });
                break;
            case 'addRow':
                tabledata.push(emptyObj);
                table.reload("demo", {data: tabledata,url:''});
                break;
            case 'addMoreRow':
                layer.prompt({
                    value: 5,
                    title: '请输入新建行数'
                }, function(value, index, elem){
                    var leng = parseInt(value);
                    if(leng<=0){
                        layer.msg("输入值必须大于0", {icon:7});
                        layer.close(index);
                        return ;
                    }
                    for(var i=0;i<leng;i++)
                        tabledata.push(emptyObj);
                    table.reload("demo", {data: tabledata,url:''});
                    layer.close(index);
                });
                break;
        };
    });

    //用于及时的更新数据实体
    table.on('edit(test)', function(obj){
        var data =  obj.data;
        var value = obj.value;
        var ziduan = obj.field;
        switch (ziduan) {
            case "col1":
                obj.update({col1 : value});
                break;
            case "col2":
                obj.update({col2 : value});
                break;
            case "col3":
                obj.update({col3 : value});
                break;
        }
    });
});