<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="com.baidu.bpit.git_test.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
String[] configs = Worker.getServerConfig();
%>
<table border="1" cellspacing="1">
<tr>
	<td>server id</td>
	<td><%=configs[4] %>
</tr>
</table>
<br>
<table border="1" cellspacing="1">
<tr>
	<td colspan="2" align="center">db config</td>
</tr>
<tr>
	<td>name</td>
	<td><%=configs[0] %>
</tr>
<tr>
	<td>pwd</td>
	<td><%=configs[1] %></td>
</tr>
<tr>
	<td>driver</td>
	<td><%=configs[2] %></td>
</tr>
<tr>
	<td>url</td>
	<td><%=configs[3] %></td>
</tr>
</table>
</body>
</html>