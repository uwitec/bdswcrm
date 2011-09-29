<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

//角色列表
List roleList = (List)VS.findValue("roleList");

//具有审批权限的角色
String jgspRoles = (String)VS.findValue("jgspRoles");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>价格审批权限设定</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form name="myform" action="saveJgSpRightRoles.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>设定价格审批权限角色</b>
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
		<td>角色名称</td>
	</tr>
	</thead>
	<%
	Iterator it = roleList.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String flag = "";
		String role_id = StringUtils.nullToStr(map.get("role_id"));
		if(jgspRoles.indexOf(role_id) != -1){
			flag = "checked";
		}
	%>
	<tr>
		<td class="a1"><input type="checkbox" name="role_id" value="<%=StringUtils.nullToStr(map.get("role_id")) %>" <%=flag %>></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("role_name")) %></td>
	</tr>
	
	<%
	}
	%>
	<tr height="40">
		<td class="a1" colspan="3">
			<input type="submit" name="button1" value="保 存" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>	
</table>
</form>
说明：销售单商品价格低于商品分销限价，零售单商品低于零售限价，订单须经审核才能提交，审核权限与角色关联，在此进行设置。
</body>
</html>
