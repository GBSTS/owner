layui.use(['element', 'form'], function() {
	var element = layui.element,
		form = layui.form;
});
function submitRegisterInfoFun() {
	var account=document.getElementById("account").value;
	var password=document.getElementById("password").value;
	var ackPassword=document.getElementById("ackPassword").value;
	var email=document.getElementById("email").value;
	var phone=document.getElementById("phone").value;
	var identity=document.getElementById("identity").value;
	if(account==""||password==""||ackPassword==""||email==""||phone==""||identity==""){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.msg("输入不能够为空");
		});
		return ;
	}
	if(password!=ackPassword){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.msg("两次的密码必须一致");
		});
		document.getElementById("password").value="";
		document.getElementById("ackPassword").value="";
		return ;
	}
	if(email.indexOf("@")==-1){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.msg("邮箱地址必须正确\n例如:1234567890@qq.com");
		});
		document.getElementById("email").value="";
		return ;
	}
	var formData = new FormData();
	formData.append("account",account);
	formData.append("password",password);
	formData.append("email",email);
	formData.append("phone",phone);
	formData.append("identity",identity);
	formData.append("reRegister","False");

	$.ajax({
		url:"/submitRegisterInfo",
		type: "POST",
		data:formData,
		cache:false,         //不设置缓存
		processData: false,  // 不处理数据
		contentType: false,   // 不设置内容类型,
		success:function(str){
			layui.use('layer', function() {
				var layer = layui.layer;
				if(str=="redirect:/login"){
					location.href='/login';
					return ;
				}
				if(str=="该用户已经注册并且激活，如果您需要重新注册请联系开发人员") {
					layer.msg(str);
					return;
				}
				if(str=="该用户已经注册但没有激活，请问是否重新注册"){
					layer.confirm(str, {
						btn: ['重新注册','取消'] //按钮
					}, function(){
						formData.append("reRegister","True");
						$.ajax({
							url:"/submitRegisterInfo",
							type: "POST",
							data:formData,
							cache:false,         //不设置缓存
							processData: false,  // 不处理数据
							contentType: false,   // 不设置内容类型,
							success:function(str){},
							error:function () {
								layui.use('layer', function() {
									var layer = layui.layer;
									layer.msg("网络异常");
								});
							}
						});
					}, function(){});
					return ;
				}
			});
		},
		error:function () {
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.msg("网络异常");
			});
		}
	});
}