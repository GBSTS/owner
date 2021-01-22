layui.use(['table', 'element', 'layer'], function() {
	var table = layui.table,
		element = layui.element,
		layer = layui.layer;
	var $ = layui.$;

	table.render({
		elem: '#test',
		url: '../../static/json/temagcourse.json',
		method:"post",
		page: true,
		// limits: [3, 5, 10],
		// limit: 3, //每页默认显示的数量
		autoSort: false,
		defaultToolbar:['filter'],
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
					field: 'courseId',
					title: '课程编号',
					align:'center'
				},
				{
					field: 'courseName',
					title: '课程名',
					align: "center"
				},
				{
					field: 'courseType',
					title: '课程类型',
					align: "center"
				},
				{
					field: 'courseBriefIntroduction',
					title: '课程简介',
					align: 'center',
					templet:function (d) {
						return '<p class="layui-layer-own1 alyui-showDetails" aa="'+d.courseBriefIntroduction+'">'+d.courseBriefIntroduction+'</p>';
					}
				},
				{
					field: 'teacher',
					title: '任课教师',
					align: "center"
				},
				{
					field: 'semester',
					title: '上课学期',
					align: "center"
				},
				{
					field: 'optionalClass',
					title: '可选班级',
					align:'center'
				},
				{
					field:"credit",
					title:"学分",
					align:"center"
				},
				{
					field:"classHours",
					title:"学时",
					align:"center"
				},
				{
					field:"optionalNumberOfPeople",
					title:"可选人数",
					align:"center"
				},
				{
					field:"numberOfPeopleSelected",
					title:"已选人数",
					align:"center"
				},
				{
					title: '操作',
					fixed: 'right',
					toolbar: '#barDemo',
					align: "center"
				}
			]
		],
		done: function(res, curr, count) {
			//查看详细信息
			$('.alyui-showDetails').each(function(i) {
				this.onclick = function() {
					imgArrStr = this.getAttribute("aa");
					layer.open({
						content: imgArrStr
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
	table.on('sort(test)', function(obj) {
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
});
