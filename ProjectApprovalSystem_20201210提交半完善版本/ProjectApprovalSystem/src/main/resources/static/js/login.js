layui.use(['element', 'form'], function() {
	var element = layui.element,
		form = layui.form;
});

function submitLoginInfoFun() {
	var email=document.getElementById("email").value;
	var password=document.getElementById("password").value;
	var identity=document.getElementById("identity").value;
	if(email==""||password==""||identity==""){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.msg("输入不能够为空");
		});
		return ;
	}
	var formData = new FormData();
	formData.append("email",email);
	formData.append("password",password);
	formData.append("identity",identity);

	$.ajax({
		url:"/submitLoginInfo",
		type: "POST",
		data:formData,
		cache:false,         //不设置缓存
		processData: false,  // 不处理数据
		contentType: false,   // 不设置内容类型
		success:function(result){
			if(result=="redirect:/apply/mainshow"){
				location.href="/apply/mainshow";
				return ;
			}
			if(result=="redirect:/approval/mainshow"){
				location.href="/approval/mainshow";
				return ;
			}
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.msg(result);
				// layer.confirm(result);
			});
		},
		error:function () {
			layui.use('layer', function() {
				var layer = layui.layer;
				// layer.msg("网络异常");
				layer.confirm("网络异常");
			});
		}
	});
}