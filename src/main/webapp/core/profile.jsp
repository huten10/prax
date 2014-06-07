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
		<div class="span3 user"><img alt="" src="<c:out value="${user.profile.headImgUrl}" />" /></div>
		<div class="span9">
			<form action="wechat/user/save.do" id="profile_form" class="form-vertical" method="POST">
				<div class="control-group">
					<label class="control-label">昵称</label>
					<div class="controls">
						<input type="text" name="profile.nickName" value="<c:out value="${user.profile.nickName}"/>" class="span6 m-wrap" readonly >
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">姓名<span class="required">*</span></label>
					<div class="controls">
						<input type="text" name="profile.name" value="<c:out value="${user.profile.name}"/>" data-msg-required="请输入姓名。" class="span6 m-wrap required">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">手机号<span class="required">*</span></label>
					<div class="controls">
						<input type="text" name="profile.mobile" value="<c:out value="${user.profile.mobile}"/>" data-msg-required="请输入手机号。" class="span6 m-wrap required">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱</label>
					<div class="controls">
						<input type="text" name="profile.email" value="<c:out value="${user.profile.email}"/>" data-msg-email="请输入合法的邮箱地址。" class="span6 m-wrap email">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系地址</label>
					<div class="controls">
						<input type="text" name="profile.address" value="<c:out value="${user.profile.address}"/>" class="span6 m-wrap">
					</div>
				</div>
				<button type="submit" class="btn purple">保存</button>
			</form>
		</div>
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