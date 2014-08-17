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
				<div class="caption"><i class="icon-user"></i>用户列表</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<form id="mainForm" action="list.do" method="post">
					<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
					<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
					<input type="hidden" name="order" id="order" value="${page.order}"/>
					
					<div class="row-fluid">
						<div class="span12">
							<div class="dataTables_filter">
								<div style="float: right;">
									<input type="text" placeholder="查询..." class="m-wrap" 
										name="FILTER_LIKES_login_OR_name_OR_profile.name_OR_profile.mobile_OR_profile.address" 
										value="${param['FILTER_LIKES_login_OR_name_OR_profile.name_OR_profile.mobile_OR_profile.address']}">
									<button type="submit" class="btn green">查询 &nbsp; <i class="m-icon-swapright m-icon-white"></i></button>
								</div>
							</div>
						</div>
					</div>
					
					<table class="table table-striped table-bordered table-hover" id="user_table">
						<thead>
							<tr>
								<th><a href="javascript:sort('login','asc')">登录名</a></th>
								<th><a href="javascript:sort('name','asc')">昵称</a></th>
								<th class="hidden-480"><a href="javascript:sort('profile.name','asc')">姓名</a></th>
								<th class="hidden-480"><a href="javascript:sort('profile.sex','asc')">性别</a></th>
								<th class="hidden-480"><a href="javascript:sort('profile.mobile','asc')">手机</a></th>
								<th class="hidden-480"><a href="javascript:sort('profile.address','asc')">地址</a></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="o" items="${page.result}">
							 <tr class="odd gradeX">
								<td>${o.login}&nbsp;</td>
								<td>${o.name}&nbsp;</td>
								<td class="hidden-480">${o.profile.name}&nbsp;</td>
								<td class="hidden-480">${o.profile.sex == 1 ? "男" : "女"}</td>
								<td class="hidden-480">${o.profile.mobile}&nbsp;</td>
								<td class="hidden-480 hidden-767">${o.profile.address}</td>
								
								<td>
									<a href="<c:url value="/user/edit.do"/>?uuid=${o.uuid}" class="btn mini purple"><i class="icon-edit"></i> 修改</a>
									<sec:authorize access="hasRole('SUPER_ADMIN')">
									<a href="#modal_${o.uuid}" role="button" class="btn mini purple" data-toggle="modal"><i class="icon-cog"></i> 重置密码</a>
									<div id="modal_${o.uuid}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel3" aria-hidden="true">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h3 id="modalLabel3">重置密码</h3>
										</div>
										<div class="modal-body">
											<p>确定要重置密码吗？</p>
										</div>
										<div class="modal-footer">
											<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
											<a href="<c:url value="/user/resetPassword.do"/>?uuid=${o.uuid}" class="btn blue"> 确定</a>
										</div>
									</div>
									</sec:authorize>
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
