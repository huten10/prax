<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page import="com.prax.core.user.entity.User"%>
<%
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
User user = (User)auth.getPrincipal();
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/core/common/taglibs.jsp" %>
<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<div class="span12">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<h3 class="page-title">
			用户资料
		</h3>
		<ul class="breadcrumb">
			<li>
				<i class="icon-home"></i>
				<a href="${ctx}">首页</a> 
				<i class="icon-angle-right"></i>
			</li>
			<li><a href="#">用户资料</a></li>
		</ul>
		<!-- END PAGE TITLE & BREADCRUMB-->
	</div>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row-fluid profile">
	<div class="span12">
		<c:if test="${!empty MESSAGE}">
		<div class="alert alert-error fade in">
           	<c:out value="${MESSAGE}"/><button type="button" class="close" data-dismiss="alert"></button>
        </div>
        </c:if>
		<form action="changePassword.do" id="profile_form" class="form-vertical" method="POST">
			<div class="control-group">
				<label class="control-label">原密码<span class="required">*</span></label>
				<div class="controls">
					<input type="password" name="oldPassword" data-msg-required="请输入原密码。" class="span6 m-wrap required"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">新密码<span class="required">*</span></label>
				<div class="controls">
					<input type="password" id="form_password" name="password" data-rule-minlength="6" data-msg-minlength="请输入6数以上的新密码。" data-msg-required="请输入6数以上的新密码。" class="span6 m-wrap required"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">确认新密码<span class="required">*</span></label>
				<div class="controls">
					<input type="password" name="newPassword" data-msg-equalTo="两次密码不一致" data-rule-equalTo="#form_password"  class="span6 m-wrap" />
				</div>
			</div>
			<button type="submit" class="btn purple">保存</button>
		</form>
	</div>
</div>
<!-- END PAGE CONTENT-->
<style>
@media (max-width:400px) { 
	.profile .user img{
		width: 60px;
  		height: 60px;
	}
	.profile div.user{
		padding-bottom: 2px;
		border-bottom: 1px solid #eee;
	}
}
</style>
<script type="text/javascript">
jQuery(document).ready(function() { 
	jQuery('#profile_form').validate({
		errorElement: 'label', //default input error message container
        errorClass: 'help-inline', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "",
		highlight: function (element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok'); // display OK icon
			$(element).closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
		},
		unhighlight: function (element) { // revert the change dony by hightlight
			$(element).closest('.control-group').removeClass('error'); // set error class to the control group
		},
		success: function (label) {
			label.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
			.closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
		}
	});
});
</script>