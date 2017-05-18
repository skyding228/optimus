<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String root = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getSession().getServletContext()
			.getContextPath();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>重定向页面</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
</head>
<body>
	<script type="text/javascript">
		window.location.href = '${url}';
	</script>
</body>
</html>