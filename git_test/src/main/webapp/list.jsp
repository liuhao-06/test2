<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*,com.baidu.bpit.git_test.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%
String username = null;
if(session.getAttribute("username")!=null){
    username = (String)session.getAttribute("username");
}else{
%>
<script type="text/javascript">
alert("请先登录！");
window.location = "index.jsp";
</script>
<%} %>
<div>
欢迎回来,<%=username %>。<a href="logout.jsp">退出</a>
</div>
<div>
<%
List<Map> li = Worker.getUserList();
%>
<table border="1" cellpadding="1" cellspacing="0" width="600">
<tr height="30" valign="middle" align="center">
	<td colspan="5"><b>用户列表</b></td>
</tr>
<tr>
	<td>序号</td>
	<td>用户名</td>
	<td>密码</td>
	<td>用户类型</td>
	<td>最后修改时间</td>
</tr>
<%
for(int i=0;i<li.size();i++){
    Map map = li.get(i);
%>
<tr>
	<td><%=i+1 %></td>
	<td><%=map.get("username") %></td>
	<td><%=map.get("password") %></td>
	<td><%=map.get("usertype") %></td>
	<td><%=map.get("modify_time") %></td>
</tr>
<%} %>
</table>
</div>
</center>
</body>
</html>