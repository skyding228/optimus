<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String root = request.getSession().getServletContext()
			.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>返回结果</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<!--标准mui.a64da349.css-->
<link rel="stylesheet" href="<%=root%>/static/css/mui.min.d6cde107.css">
<link rel="stylesheet" href="<%=root%>/static/css/app.046c73e2.css">
</head>

<body>
	<header class="mui-bar mui-bar-nav header">
		<h1 class="mui-title">结果详情</h1>
		<a class="mui-pull-right" href="${url}"><span
			class="mui-icon mui-icon-checkmarkempty"></span>完成</a>
	</header>

	<div class="mui-content">
		<div style="background-color: #FFFFFF;" id="overviewContainer">
			<div class="mui-text-center i-normal-padding">
				<%
					if ((Boolean) request.getAttribute("result") == true) {
				%>
				<a class="i-large-icon i-success-border"><span
					class="mui-icon mui-icon-checkmarkempty"></span></a>
				<%
					} else {
				%>
				<a class="i-large-icon i-error-border"><span
					class="mui-icon mui-icon-closeempty"></span></a>
				<%
					}
				%>
				<br />
				<div style="display: inline-block;">
					<h3>${message}</h3>
					<h6>${detail}</h6>
				</div>
			</div>

		</div>

	</div>
</body>

</html>