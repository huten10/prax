<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.prax.framework.base.search.Page" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
int current =  page.getPageNo();

long beginNo = Math.min((current - 1) * page.getPageSize() + 1, page.getTotalCount());
long endNo = Math.min(current * page.getPageSize(), page.getTotalCount());

int begin = Math.max(1, current - page.getPageSize()/2);
int end = Math.min(begin + (page.getPageSize() - 1), page.getTotalPages());

request.setAttribute("current", current);

request.setAttribute("beginNo", beginNo);
request.setAttribute("endNo", endNo);

request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>
<script src="${ctx}/media/js/table.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/media/css/DT_bootstrap.css" />
<div class="row-fluid">
	<div class="span6">
		<div class="dataTables_info">
		显示${beginNo} - ${endNo}条， 共${page.totalCount}条，
		每页<select size="1" name="pageSize" id="pageSize" class="m-wrap xsmall" onchange="$(this).closest('form').submit()">
			<option value="5" <c:if test="${page.pageSize==5}">selected</c:if>>5</option>
			<option value="10" <c:if test="${page.pageSize==10}">selected</c:if>>10</option>
			<option value="20" <c:if test="${page.pageSize==20}">selected</c:if>>20</option>
			<option value="100" <c:if test="${page.pageSize==100}">selected</c:if>>100</option>
			<option value="1000" <c:if test="${page.pageSize==1000}">selected</c:if>>1000</option>
		</select>条。
		</div>
	</div>
	<div class="span6">
		<div class="dataTables_paginate paging_bootstrap pagination">
		<ul>
			 <% if (page.isHasPre()){%>
	               	<li class="hidden-480"><a href="javascript:jumpPage(1)">&lt;&lt;</a></li>
	                <li class="hidden-480"><a href="javascript:jumpPage(${page.prePage})">&lt;</a></li>
	         <%}else{%>
	                <li class="disabled hidden-480"><a href="#">&lt;&lt;</a></li>
	                <li class="disabled hidden-480"><a href="#">&lt;</a></li>
	         <%} %>
	 
			<c:forEach var="i" begin="${begin}" end="${end}">
	            <c:choose>
	                <c:when test="${i == current}">
	                    <li class="active"><a href="javascript:jumpPage(${i})">${i}</a></li>
	                </c:when>
	                <c:otherwise>
	                    <li><a href="javascript:jumpPage(${i})">${i}</a></li>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
		  
		  	 <% if (page.isHasNext()){%>
	               	<li class="hidden-480"><a href="javascript:jumpPage(${page.nextPage})">&gt;</a></li>
	                <li class="hidden-480"><a href="javascript:jumpPage(${page.totalPages})">&gt;&gt;</a></li>
	         <%}else{%>
	                <li class="disabled hidden-480"><a href="#">&gt;</a></li>
	                <li class="disabled hidden-480"><a href="#">&gt;&gt;</a></li>
	         <%} %>
		</ul>
		</div>
	</div>
</div>