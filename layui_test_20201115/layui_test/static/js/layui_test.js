layui.use(['table', 'laydate', 'layer'], function() {
	var table = layui.table,
		laydate = layui.laydate,
		layer = layui.layer;
	var $ = layui.$;

	table.render({
		elem: '#test',
		url: '../static/json/productList.json',
		contentType: 'application/json;charset=utf-8',
		page: true,
		sort: true, //排序向服务器申请
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
		cols: [
			[{
					type: 'checkbox',
					fixed: 'left',
					width: 50
				},
				{
					field: 'productId',
					title: '商品编号',
					sort: true,
					fixed: 'left',
					width: 200
				},
				{
					field: 'productName',
					title: '商品名',
					align: "center",
					minWidth: 130,
					templet: function(d) {
						return '<p id="' + d.productId +
							'_productName" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" lay-event="productName">' +
							d.productName + '</p>';
					}
				},
				{
					field: 'imgUrl',
					title: '图片',
					align: 'center',
					templet: function(d) {
						// return '<img src="' + d.imgUrl + '" class="layui-img" style="width:100%;" />';
						return '<img src="' + d.imgUrl + '" style="width:100%;" lay-event="imgUrl" />';
					},
					Width: 95
				},
				{
					field: 'productLocal',
					title: '商品产地',
					align: "center",
					minWidth: 95
				},
				{
					field: 'productValue',
					title: '商品价格',
					align: "center",
					sort: true,
					minWidth: 95
				},
				{
					field: 'productUnit',
					title: '价格单位',
					minWidth: 95
				},
				{
					field: 'expectedMatureDate',
					title: '预计成熟时间',
					sort: true,
					minWidth: 120,
					templet: function(d) {
						// return '<p class="layui-date" style="text-align:center;">' + d.expectedMatureDate + '</p>';
						return '<p id="' + d.productId +
							'_expectedMatureDate" style="text-align:center;" lay-event="expectedMatureDate">' + d.expectedMatureDate +
							'</p>';
					}
				},
				{
					field: 'shelvesDate',
					title: '上架时间',
					sort: true,
					minWidth: 100,
					templet: function(d) {
						return '<p class="layui-date">' + d.shelvesDate + '</p>';
					}
				},
				{
					field: 'productIntroduction',
					title: '商品介绍',
					minWidth: 100,
					templet: function(d) {
						// return '<p class="layui-introduce" style="width:100%;">' + d.productIntroduction + '</p>';
						return '<p style="overflow: hidden;text-overflow: ellipsis;" lay-event="introduce">' + d.productIntroduction +
							'</p>';
					}
				},
				{
					title: '操作',
					fixed: 'right',
					toolbar: '#barDemo',
					width: 100
				}
			]
		],
		done: function(res, curr, count) {
			//日期控件
			$(".layui-date").each(function(i) {
				laydate.render({
					elem: this,
					format: "yyyy-MM-dd"
				});
			});
			// //查看照片
			// $(".layui-img").each(function(i) {
			// 	this.onclick = function() {
			// 		// layer.photos({ photos: {"data": [{"src": "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1578364952,3440871037&fm=26&gp=0.jpg"}]} ,anim:5});
			// 		layer.open({
			// 			type: 1,
			// 			title: false,
			// 			closeBtn: 0,
			// 			skin: 'layui-layer-nobg', //没有背景色
			// 			shadeClose: true,
			// 			content: "<img src='" + this.getAttribute('src') + "' style='max-width:100%; max-height:100%' />"
			// 		});
			// 	}
			// });
			// //查看详细信息
			// $(".layui-introduce").each(function(i) {
			// 	this.onclick = function() {
			// 		layer.msg('商品介绍', {
			// 		  time: 1000 //2秒关闭（如果不配置，默认是3秒）
			// 		}, function(){
			// 		  //do something
			// 		}); 
			// 	}
			// });
		}
	});
	//头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'getCheckLength':
				var data = checkStatus.data;
				layer.msg('选中了：' + data.length + ' 个');
				break;
			case 'isAll':
				layer.msg(checkStatus.isAll ? '全选' : '未全选')
				break;
			case 'LAYTABLE_TIPS':
				layer.alert('Table for layui-v' + layui.v);
				break;
		};
	});

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		//console.log(obj)
		if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				obj.del();
				layer.close(index);
			});
		} else if (obj.event === 'introduce') {
			layer.open({
				content: data.productIntroduction,
				title: '商品介绍'
			});
		} else if (obj.event === 'productName') {
			layer.tips(data.productName, '#' + data.productId + "_productName");
		} else if (obj.event === 'imgUrl') {
			// layer.photos({ photos: {"data": [{"src": "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1578364952,3440871037&fm=26&gp=0.jpg"}]} ,anim:5});
			layer.open({
				type: 1,
				title: false,
				closeBtn: 0,
				skin: 'layui-layer-nobg', //没有背景色
				shadeClose: true,
				content: "<img src='" + data.imgUrl + "' style='max-width:100%; max-height:100%' />"
			});
		} else if (obj.event === 'expectedMatureDate') {
			laydate.render({
				elem: '#' + data.productId + '_expectedMatureDate',
				format: "yyyy-MM-dd"
			});
		}
		laydate.render({
			elem: '#' + data.productId + '_expectedMatureDate',
			format: "yyyy-MM-dd"
		});
	});
});
