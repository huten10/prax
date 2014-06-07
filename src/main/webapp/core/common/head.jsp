<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="./taglibs.jsp" %>
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title><t:getAsString name="PAGE_TITLE"/></title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="${ctx}/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${ctx}/media/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	
	<!-- BEGIN GLOBAL MANDATORY SCRIPT -->
	<script src="${ctx}/media/js/lib/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/media/js/lib/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${ctx}/media/js/lib/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>      
	<script src="${ctx}/media/js/lib/bootstrap.min.js" type="text/javascript"></script>
	<!-- END GLOBAL MANDATORY SCRIPT -->

	<link rel="shortcut icon" href="${ctx}/media/image/favicon.ico" />
</head>
<!-- END HEAD -->