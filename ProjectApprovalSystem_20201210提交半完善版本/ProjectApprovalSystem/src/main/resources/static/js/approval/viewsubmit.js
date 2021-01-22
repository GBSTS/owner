layui.use(['table', 'element'], function() {
    var table = layui.table,
        element = layui.element;
    var $ = layui.$;

    table.render({
        elem: '#test',
        url: '/approval/getItemSubmit',
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
                    title: '项目编号'
                },
                {
                    field: 'itemName',
                    title: '项目名',
                    align: "center"
                },
                {
                    field: 'submitDate',
                    title: '提交时间',
                    align: 'center'
                },
                {
                    field: 'fileId',
                    title: '提交资料',
                    align: "center"
                },
                {
                    field: 'submitIntroduction',
                    title: '提交描述',
                    align: "center"
                }
            ]
        ]
    });
});
