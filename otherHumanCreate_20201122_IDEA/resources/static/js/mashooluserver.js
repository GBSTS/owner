layui.use(['table', 'element', 'layer'], function() {
	var table = layui.table,
		element = layui.element,
		layer = layui.layer;
	var $ = layui.$;

	table.render({
		elem: '#test',
		url: '/getSchoolLevelApplicationData',
		method:"post",
		page: true,
		// limits: [3, 5, 10],
		// limit: 3, //每页默认显示的数量
		autoSort: false,
		where:{
			field:'applyId',
			order:''
		},
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
		cols: [
			[{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field: 'applyId',
					title: '申请编号',
					sort: true
				},
				{
					field: 'applicant',
					title: '申请人',
					align: "center"
				},
				{
					field: 'contact',
					title: '联系方式',
					align: 'center'
				},
				{
					field: 'schoolName',
					title: '学校名称',
					align: "center"
				},
				{
					field: 'schoolAddress',
					title: '学校详细地址',
					align: "center"
				},
				{
					field: 'supportingMaterials',
					title: '证明材料',
					width: 100,
					templet: function(d) {
						return '<button class="layui-btn layui-btn-sm layui-btn-warm layui-showMaterials" aa="' + d.supportingMaterials +
							'">证明材料</button>';
					}
				},
				{
					title: '操作',
					fixed: 'right',
					toolbar: '#barDemo',
					width: 250,
					align: "center"
				}
			]
		],
		done: function(res, curr, count) {
			//查看证明材料
			$('.layui-showMaterials').each(function(i) {
				this.onclick = function() {
					imgArrStr = this.getAttribute("aa");
					imgArr = imgArrStr.split(',');
					contentImgStr = '<div style="width: 100%; height: 100%;">';
					for (var i = 0; i < imgArr.length; i++)
						contentImgStr = contentImgStr + '<img src="' + imgArr[i] +
						'" class="layui-openImg" width="135px" height="135px" style="margin:3px;" />';
					contentImgStr += '</div>';
					layer.open({
						area: ['480px', '400px'],
						title: '证明材料',
						content: contentImgStr
					})
					$(".layui-openImg").each(function(i) {
						this.onclick = function() {
							layer.open({
								type: 1,
								title: false,
								content: '<img src="' + this.getAttribute("src") + '" width="100%" />'
							})
						}
					});
				}
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
			case 'rejectApply':
				var data = checkStatus.data;
				if(data.length==0){
					layer.alert('当前选中为空');
					break;
				}
				layer.prompt({
					formType: 2,
					title: '拒绝的原因',
					area: ['240px', '100px'],
					btn: ['确认驳回申请', '取消']
				}, function(value, index, elem) {
					alert(value); //得到value
					layer.close(index);
					/**
					 * 拒绝操作
					 */
				});
				break;
			case 'approved':
				var data = checkStatus.data;
				if(data.length==0){
					layer.alert('当前选中为空');
					break;
				}
				/**
				 * 通过操作
				 */
				break;
			case 'LAYTABLE_TIPS':
				layer.alert('Table for layui-v' + layui.v);
				break;
		};
	});

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		// if (obj.event === 'del') {
		// 	layer.confirm('真的删除行么', function(index) {
		// 		obj.del();
		// 		layer.close(index);
		// 	});
		// } else
		if (obj.event === 'details') {
			msg = '<div>' +
				'<div>' +
				'<label style="text-align: right; width: 100px; display:inline-block;">申请编号：</label>' +
				'<label style="text-align: left;">' + data.applyId + '</label>' +
				'</div>' +
				'<div>' +
				'<label style="text-align: right; width: 100px; display:inline-block;">申请人：</label>' +
				'<label style="text-align: left;">' + data.applicant + '</label>' +
				'</div>' +
				'<div>' +
				'<label style="text-align: right; width: 100px; display:inline-block;">联系方式：</label>' +
				'<label style="text-align: left;">' + data.contact + '</label>' +
				'</div>' +
				'<div>' +
				'<label style="text-align: right; width: 100px; display:inline-block;">学校名称：</label>' +
				'<label style="text-align: left;">' + data.schoolName + '</label>' +
				'</div>' +
				'<div>' +
				'<label style="text-align: right; width: 100px; display:inline-block;">详细地址：</label>' +
				'<label style="text-align: left;">' + data.schoolAddress + '</label>' +
				'</div>' +
				'</div>';
			layer.open({
				area: ['400', '300'],
				content: msg
			})
		} else if (obj.event === 'rejectApply') {
			layer.prompt({
				formType: 2,
				title: '拒绝的原因',
				area: ['240px', '100px'],
				btn: ['确认驳回申请', '取消']
			}, function(value, index, elem) {
				alert(value); //得到value
				layer.close(index);
				/**
				 * 拒绝操作
				 */
			});
		} else if (obj.event === 'approved') {
			/**
			 * 通过操作
			 */
		}
	});

	//sort
	table.on('sort(test)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		// console.log(obj.field); //当前排序的字段名
		// console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		// console.log(this); //当前排序的 th 对象

		//尽管我们的 table 自带排序功能，但并没有请求服务端。
		//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
		table.reload('test', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				,
			where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field, //排序字段   在接口作为参数字段  field order
				order: obj.type , //排序方式   在接口作为参数字段  field order
				page:1
			}
		});
	});

	table.render({
		elem: '#test2',
		url: '/getSchoolLevelApplicationHistoryData',
		method:"post",
		page: true,
		// limits: [3, 5, 10],
		// limit: 3, //每页默认显示的数量
		autoSort: false,
		where:{
			field:'applyId',
			order:''
		},
		cols: [
			[{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field: 'applyId',
					title: '申请编号',
					sort: true
				},
				{
					field: 'applicant',
					title: '申请人',
					align: "center"
				},
				{
					field: 'contact',
					title: '联系方式',
					align: 'center'
				},
				{
					field: 'schoolName',
					title: '学校名称',
					align: "center"
				},
				{
					field: 'schoolAddress',
					title: '学校详细地址',
					align: "center"
				},
				{
					field: 'supportingMaterials',
					title: '证明材料',
					width: 100,
					templet: function(d) {
						return '<button class="layui-btn layui-btn-sm layui-btn-warm layui-showMaterials" aa="' + d.supportingMaterials +
							'">证明材料</button>';
					}
				},
				{
					field: 'approvalTime',
					title: '审批时间',
					align: "center"
				},
				{
					field: 'approvalStatus',
					title: '审批状态',
					width: 100,
					templet: function(d) {
						if (d.approvalStatus)
							return '<span class="layui-badge layui-bg-green">审核通过</span>';
						else
							return '<span class="layui-badge">驳回申请</span>';
					}
				},
				{
					field: 'reason',
					title: '原因',
					width: 100,
					templet: function(d) {
						if (d.approvalStatus)
							return '满足要求';
						else
							return '<p>' + d.reason + '</p>';
					}
				}
			]
		],
		done: function(res, curr, count) {
			//查看证明材料
			$('.layui-showMaterials').each(function(i) {
				this.onclick = function() {
					imgArrStr = this.getAttribute("aa");
					imgArr = imgArrStr.split(',');
					contentImgStr = '<div style="width: 100%; height: 100%;">';
					for (var i = 0; i < imgArr.length; i++)
						contentImgStr = contentImgStr + '<img src="' + imgArr[i] +
						'" class="layui-openImg" width="135px" height="135px" style="margin:3px;" />';
					contentImgStr += '</div>';
					layer.open({
						area: ['480px', '400px'],
						title: '证明材料',
						content: contentImgStr
					})
					$(".layui-openImg").each(function(i) {
						this.onclick = function() {
							layer.open({
								type: 1,
								title: false,
								content: '<img src="' + this.getAttribute("src") + '" width="100%" />'
							})
						}
					});
				}
			});
		}
	});

	//sort
	table.on('sort(test2)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		// console.log(obj.field); //当前排序的字段名
		// console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		// console.log(this); //当前排序的 th 对象

		//尽管我们的 table 自带排序功能，但并没有请求服务端。
		//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
		table.reload('test2', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,
			where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段   在接口作为参数字段  field order
				,
				order: obj.type, //排序方式   在接口作为参数字段  field order
				page:1
			}
		});
	});
});
