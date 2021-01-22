layui.use(['table', 'laydate', 'layer'], function() {
	var table = layui.table,
		laydate = layui.laydate,
		layer = layui.layer;
	var $ = layui.$;

	table.render({
		elem: '#test',
		url: '/getData',
		method:"post",
		page: true,
		// limits: [3, 5, 10],
		// limit: 3, //每页默认显示的数量
		autoSort: false,
		where:{
			field:'productId',
			order:''
		},
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
						// return '<p id="' + d.productId +
						// 	'_productName" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" lay-event="productName">' +
						// 	d.productName + '</p>';
						return '<p id="' + d.productId +'_productName" ' +
							'style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" ' +
							'class="layui-productName">'+d.productName+'</p>';
					}
				},
				{
					field: 'imgUrl',
					title: '图片',
					align: 'center',
					templet: function(d) {
						return '<img src="' + d.imgUrl + '" class="layui-img" style="width:100%;" />';
						// return '<img src="' + d.imgUrl + '" style="width:100%;" lay-event="imgUrl" />';
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
						return '<p class="layui-date" style="text-align:center;">' + d.expectedMatureDate + '</p>';
						// return '<p id="' + d.productId +
						// 	'_expectedMatureDate" style="text-align:center;" lay-event="expectedMatureDate">' + d.expectedMatureDate +
						// 	'</p>';
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
						return '<p class="layui-introduce" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">' + d.productIntroduction + '</p>';
						// return '<p style="overflow: hidden;text-overflow: ellipsis;" lay-event="introduce">' + d.productIntroduction +
						// 	'</p>';
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
			//查看照片
			$(".layui-img").each(function(i) {
				this.onclick = function() {
					// layer.photos({ photos: {"data": [{"src": "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1578364952,3440871037&fm=26&gp=0.jpg"}]} ,anim:5});
					layer.open({
						type: 1,
						title: false,
						closeBtn: 0,
						skin: 'layui-layer-nobg', //没有背景色
						shadeClose: true,
						content: "<img src='" + this.getAttribute('src') + "' style='max-width:100%; max-height:100%' />"
					});
				}
			});
			//查看详细信息
			$(".layui-introduce").each(function(i) {
				this.onclick = function() {
					layer.open({
								content: this.innerText,
								title: '商品介绍'
							});
				}
			});
			//查看商品名称
			$(".layui-productName").each(function(i){
				this.onclick=function(){
					layer.tips(this.innerText, '#' + this.getAttribute("id"));
				};
			});
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
		if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				obj.del();
				layer.close(index);
			});
		}
	});

	//sort
	table.on('sort(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		// console.log(obj.field); //当前排序的字段名
		// console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		// console.log(this); //当前排序的 th 对象

		//尽管我们的 table 自带排序功能，但并没有请求服务端。
		//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
		table.reload('test', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段   在接口作为参数字段  field order
				,order: obj.type //排序方式   在接口作为参数字段  field order
			}
		});
	});
});
