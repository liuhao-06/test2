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
String name = request.getParameter("name");
String pwd = request.getParameter("pwd");
String message = "&nbsp;";
String ip = request.getHeader("x-forwarded-for");
if(ip==null){
    ip = request.getRemoteAddr();
}
//System.out.println("remote ip:"+ip);
if(name!=null&&pwd!=null){
    boolean login = Worker.login(name, pwd,ip);
    //System.out.println(name+","+pwd+","+login);
    if(login){
        session.setAttribute("username", name);
        request.getRequestDispatcher("list.jsp").forward(request, response);        
    }else{
        message = "用户名或密码错误";
    }
}

%>
<div align="center" style="vertical-align: middle;height: 400px;">
<form action="index.jsp" method="post">
now:<%=TimeUtil.getNowTime() %>
<br><br>
<table>
<tr>
	<td colspan="2" align="center"><div style="color: red;font-size: 11px;"><%=message %></div></td>
</tr>
<tr>
	<td>user:</td>
	<td><input type="text" name="name"></td>
</tr>
<tr>
	<td>pwd :</td>
	<td><input type="password" name="pwd"></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="登录"></td>
</tr>	
</table>
</form>
</div>
</body>
</html>