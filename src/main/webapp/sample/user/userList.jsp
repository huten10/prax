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
				<div class="caption"><i class="icon-user"></i>用户列表</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="clearfix">
					<div class="btn-group">
						<a href="add.do" class="btn green">新增用户 <i class="icon-plus"></i></a>
					</div>
				</div>
				<form id="mainForm" action="list.do" method="get">
					<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
					<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
					<input type="hidden" name="order" id="order" value="${page.order}"/>
					
					<table class="table table-striped table-bordered table-hover" id="user_table">
						<thead>
							<tr>
								<th><a href="javascript:sort('loginName','asc')">登录名</a></th>
								<th class="hidden-480"><a href="javascript:sort('name','asc')">姓名</a></th>
								<th class="hidden-480"><a href="javascript:sort('email','asc')">电邮</a></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="o" items="${page.result}">
							 <tr class="odd gradeX">
								<td>${o.loginName}&nbsp;</td>
								<td class="hidden-480">${o.name}&nbsp;</td>
								<td class="hidden-480">${o.email}&nbsp;</td>
								
								<td>&nbsp;
									<a href="<c:url value="/sample/user/edit.do"/>?uuid=${o.uuid}">修改</a>&nbsp;
									<a href="<c:url value="/sample/user/delete.do"/>?uuid=${o.uuid}">删除</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<tags:pagination page="${page}"/>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->
