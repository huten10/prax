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
			首页
		</h3>
		<ul class="breadcrumb">
			<li>
				<i class="icon-home"></i>
				<a href="${ctx}">首页</a> 
				<i class="icon-angle-right"></i>
			</li>
		</ul>
		<!-- END PAGE TITLE & BREADCRUMB-->
	</div>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row-fluid">
	<div class="span12">
		 <h4>欢迎你 <%=user.getProfile()!=null ? user.getProfile().getNickName():user.getName()%></h4>
	</div>
</div>
<!-- END PAGE CONTENT-->
