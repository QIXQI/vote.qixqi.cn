$(document).ready(function(){
	var form = document.getElementById('user_login').getElementsByTagName('form')[0];

	// 检测用户名或邮箱不能为空
	function checkIdentity(){       
	    if($('#login_field').val().trim() == ''){
	        return false;
	    }
	    return true;
	}

	// 检测密码
	function checkPassword(){
	    var regNumber = /\d+/;          // 验证 0-9 的任意数字最少出现一次
	    var regString = /[a-zA-Z]+/;    // 验证大小写字母任意字母最少出现一次

	    if($('#password').val().length >= 8 && regNumber.test($('#password').val()) && regString.test($('#password').val())){
	        return true;
	    }
	    return false;
	}

	// 关闭错误提示信息
	$('#error_message button:first').click(() => {
		$('error_message').css('display', 'none');
	});

	// ajax 提交表单信息
	$('#login').submit((event) => {
		if (checkIdentity() && checkPassword()){
			// 发送登录请求
			var data = {
				login_field: $('#login_field').val(),
				password: $('#password').val()
			}
			$.ajax({
				url: 'login.do',
				type: 'POST',
				dataType: 'json',
				data: data,
				async: true,
				success: function(data){
					var result = data.result;
					if (result === 'success'){
						alert('登录成功，即将跳转');
						alert(data.user);
						console.log(data.user);
						$(location).attr('href', 'index.html');
					} else{
						alert('登录失败');
					}
				},
				error: function(err){
					alert('登录失败');
					console.error(err.responseText);
				}
			});
		} else{
			$('#error_message').css('display', 'block');
		}
		event.preventDefault();
	});
});