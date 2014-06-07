<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ include file="./common/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh"> <!--<![endif]-->

<%@ include file="./common/head.jsp" %>

<link href="${ctx}/media/css/login.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN BODY -->
<body class="login">
	<div class="logo">
	</div>
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="form-vertical login-form" method="POST" action="${ctx}/j_spring_security_check">
			<h3 class="form-title">登录你的账户</h3>
			<div class="alert alert-error hide">
				<button class="close" data-dismiss="alert"></button>
				<span>请输入用户名和密码</span>
			</div>
			<%if (session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null) {%> 
			<div class="alert alert-error fade in">
            	 登录失败，请重试<button type="button" class="close" data-dismiss="alert"></button>
          	</div>
			<%}%>
			<div class="control-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-user"></i>
						<input class="m-wrap placeholder-no-fix required" type="text" placeholder="用户名" id="j_username" name="j_username" data-msg-required="请输入用户名。"/>
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-lock"></i>
						<input class="m-wrap placeholder-no-fix required" type="password" placeholder="密码" id="j_password" name="j_password" data-msg-required="请输入密码。" />
					</div>
				</div>
			</div>

			<div class="form-actions">
				<label class="checkbox">
				<input type="checkbox" id="remember" name="remember" /> 两周内记住我
				</label>
				<button type="submit" class="btn green pull-right">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</form>
		<!-- END LOGIN FORM -->
	</div>
<%@ include file="./common/footScripts.jsp" %>
</body>
<!-- END BODY -->
<script src="${ctx}/media/js/login.js" type="text/javascript"></script>
<script>
		jQuery(document).ready(function() {     
		  Login.init();
		});

</script>
</html>