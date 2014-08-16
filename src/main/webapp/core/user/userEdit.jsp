<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/core/common/taglibs.jsp" %>
<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<div class="span12">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<h3 class="page-title">
			首页
		</h3>
		<ul class="breadcrumb">
			<li>
				<i class="icon-home"></i>
				<a href="${ctx}">首页</a> 
				<i class="icon-angle-right"></i>
			</li>
			<li><a href="#">用户管理</a></li>
		</ul>
		<!-- END PAGE TITLE & BREADCRUMB-->
	</div>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box green">
			<div class="portlet-title">
				<div class="caption"><i class="icon-user"></i><c:if test="${user.uuid == null}">创建</c:if><c:if test="${user.uuid != null}">修改</c:if>用户</div>
			</div>
			<div class="portlet-body">
				<form action="save.do" id="profile_form" class="form-horizontal" method="POST">
					<h3 class="form-section">用户信息</h3>
					<input type="hidden" name="uuid" value="${user.uuid}">
					<sec:authorize access="hasRole('SUPER_ADMIN')">
					<div class="control-group">
						<label class="control-label">登录名</label>
						<div class="controls">
							<input type="text" name="login" value="<c:out value="${user.login}"/>" data-msg-required="请输入姓名。" class="span6 m-wrap required">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">管理员</label>
						<div class="controls chzn-controls">
							<select data-with-diselect="1" name="admin" tabindex="1"  class="chosen span6 m-wrap ">
								<option value="0" <c:if test="${!user.admin}">selected</c:if>>否</option>
								<option value="1" <c:if test="${user.admin}">selected</c:if>>是</option>
							</select>
						</div>
					</div>
					</sec:authorize>
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
						<label class="control-label">二维码</label>
						<div class="controls">
							<textarea name="profile.qrcode" class="span6 m-wrap" rows="2"><c:out value="${user.profile.qrcode}"/></textarea>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">邮箱</label>
						<div class="controls">
							<input type="text" name="profile.email" value="<c:out value="${user.profile.email}"/>" data-msg-email="请输入合法的邮箱地址。" class="span6 m-wrap email">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">单位</label>
						<div class="controls">
							<input type="text" name="profile.company" value="<c:out value="${user.profile.company}"/>" class="span6 m-wrap">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">联系地址</label>
						<div class="controls">
							<input type="text" name="profile.address" value="<c:out value="${user.profile.address}"/>" class="span6 m-wrap">
						</div>
					</div>
					<input class="btn btn-primary" type="submit" value="提交"/>&nbsp;
					<input class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->
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