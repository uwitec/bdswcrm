<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List funcList = (List)VS.findValue("funcList");
List roleFuncs = (List)VS.findValue("roleFuncs");

String role_id = (String)VS.findValue("role_id");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");

%>

<html>
<head>
<title>设定角色功能</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="saveRoleFuncs.html" method="post">
<input type="hidden" name="role_id" value="<%=role_id %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>设定角色功能</b>
		</td>		
	</tr>	
</table>
<center>
<font color="red"><%=msg %></font>
</center>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>选择</td>
		<td>功能名称</td>
		<td>功能描述</td>
	</tr>
	</thead>
	<%
	Iterator it = funcList.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String flag = "";
		if(roleFuncs.contains(StringUtils.nullToStr(map.get("func_id")))){
			flag = "checked";
		}
	%>
	<tr>
		<td class="a1"><input type="checkbox" name="func_id" value="<%=StringUtils.nullToStr(map.get("func_id")) %>" <%=flag %>></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("func_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("func_ms")) %></td>

	</tr>
	
	<%
	}
	%>
	<tr height="40">
		<td class="a1" colspan="3">
			<input type="submit" name="button1" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="返 回" class="css_button2" onclick="location.href='listRole.html';">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
