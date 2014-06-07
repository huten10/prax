<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/core/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="/core/common/meta.jsp" %>
</head>
<body>
<%@ include file="/core/common/header.jsp" %>
<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading"><c:if test="${userData.uuid == null}">创建</c:if><c:if test="${userData.uuid != null}">修改</c:if>用户</div>
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
</body>
</html>
