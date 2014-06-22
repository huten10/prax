<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
				<s:form styleId="inputForm" action="sample/user/save" method="post">
					<s:hidden property="uuid"/>
					<table class="table">
					<tr>
						<td>登录名:</td>
						<td><s:text styleClass="form-control" property="loginName"/></td>
					</tr>
					<tr>
						<td>用户名:</td>
						<td><s:text styleClass="form-control" property="name"/></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><s:text styleClass="form-control" property="password"/></td>
					</tr>
					<tr>
						<td>邮箱:</td>
						<td><s:text styleClass="form-control" property="email"/></td>
					</tr>
					<tr>
						<td>地址:</td>
						<td>
							<c:forEach var="o" items="${user.addresses}">
								${o.city}<br/>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input class="btn btn-primary" type="submit" value="提交"/>&nbsp;
							<input class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
						</td>
					</tr>
				</table>
				</s:form>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->
