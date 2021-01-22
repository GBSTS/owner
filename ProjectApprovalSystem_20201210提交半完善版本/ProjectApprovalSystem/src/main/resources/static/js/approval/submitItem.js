var vm = new Vue({
    el: '#owner-form',
    data: {
        itemIdArr:[]
    }
})
$.ajax({
    url: "/apply/getOwnerItemInfo",
    type: "Post",
    data:{
        id: 1
    },
    datatype: "html",
    success: function(data, status) {
        eval(data);
        //运行JS代码，完成实验标题（coursename）和实验内容步骤简介（cei）的赋值
    }
});
layui.use(['element', 'form','layer'], function(){
    var element=layui.element,form=layui.form,layer=layui.layer;
    var $=layui.$;

    window.setInterval('$("#submitDate").val(new Date().toLocaleString())',1000);
})