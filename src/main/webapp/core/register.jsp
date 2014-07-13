<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/core/common/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh"> <!--<![endif]-->

<%@ include file="/core/common/head.jsp" %>
<link href="${ctx}/media/css/login.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN BODY -->
<body class="login">
	<div class="logo">
	</div>
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="form-vertical login-form" action="wechat/user/saveProfile" method="post">
			<h3 class="form-title">注册</h3>
			<div class="control-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">姓名</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-user"></i>
						<input class="m-wrap placeholder-no-fix required" type="text" placeholder="姓名" id='name' name='profile.name' data-msg-required="请输入姓名。"/>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">手机号</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-mobile-phone"></i>
						<input class="m-wrap placeholder-no-fix required" type="text" placeholder="手机号" id='mobile' name='profile.mobile' data-msg-required="请输入手机号。" />
					</div>
				</div>
			</div>

			<div class="form-actions">
				<button type="submit" class="btn green pull-right ">
				注册 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</form>
		<!-- END LOGIN FORM -->
	</div>
<%@ include file="/core/common/footScripts.jsp" %>
</body>
<!-- END BODY -->
<script type="text/javascript">
$('.login-form').validate({
    errorElement: 'label', //default input error message container
    errorClass: 'help-inline', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    highlight: function (element) { // hightlight error inputs
        $(element)
            .closest('.control-group').addClass('error'); // set error class to the control group
    },
    success: function (label) {
        label.closest('.control-group').removeClass('error');
        label.remove();
    },
    errorPlacement: function (error, element) {
        error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
    }
});
</script>
</html>


