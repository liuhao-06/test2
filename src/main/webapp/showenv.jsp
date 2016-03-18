<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>显示环境变量</title>
</head>
<body>
<%
Map<String,String> map = System.getenv();
if(map!=null){
    String[] keys = map.keySet().toArray(new String[0]);
    for(int i=0;i<keys.length;i++){
        out.println(i+":"+keys[i]+"="+map.get(keys[i])+"<br/>");
    }
}
%>

</body>
</html>