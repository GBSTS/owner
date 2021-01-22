layui.use(['table', 'element'], function() {
    var table = layui.table,
        layer = layui.layer;
    var $ = layui.$;

    table.render({
        elem: '#test',
        url: '/apply/getSubmitHistory',
        method:"post",
        cols: [
            [
                {
                    field: 'submitId',
                    title: '提交编号',
                    fixed: 'left'
                },
                {
                    field: 'itemId',
                    title: '项目编号',
                    align: "center"
                },
                {
                    field: 'itemName',
                    title: '项目名称',
                    align: 'center'
                },
                {
                    field: 'submitDate',
                    title: '提交时间',
                    align: "center"
                },
                {
                    field: 'submitIntroduction',
                    title: '提交说明',
                    align: "center"
                }
            ]
        ]
    });
});
