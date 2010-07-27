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
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>销售管理</b></td>
	 </tr>
	<%
	Iterator it = funcList.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("1"))
	  {
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
	}
	%>
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>采购管理</b></td>
	 </tr>
	
	<%
	Iterator it1 = funcList.iterator();
	
	while(it1.hasNext()){
		Map map = (Map)it1.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("2"))
	  {
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
	}
	%>
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>库存管理</b></td>
	 </tr>
	
	<%
	Iterator it2 = funcList.iterator();
	
	while(it2.hasNext()){
		Map map = (Map)it2.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("3"))
	  {
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
	}
	%>
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>出纳管理</b></td>
	 </tr>
	
	<%
	Iterator it3 = funcList.iterator();
	
	while(it3.hasNext()){
		Map map = (Map)it3.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("4"))
	  {
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
	}
	%>
	
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>账务管理</b></td>
	 </tr>
	
	<%
	Iterator it4 = funcList.iterator();
	
	while(it4.hasNext()){
		Map map = (Map)it4.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("5"))
	  {
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
	}
	%>
	  <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>售后管理</b></td>
	 </tr>
	 
	 
	 <%
	Iterator it6 = funcList.iterator();
	
	while(it6.hasNext()){
		Map map = (Map)it6.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("7"))
	  {
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
	}
	%>
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>OA办公</b></td>
	 </tr>
	
	<%
	Iterator it7 = funcList.iterator();
	
	while(it7.hasNext()){
		Map map = (Map)it7.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("8"))
	  {
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
	}
	%>
	<tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>会员管理</b></td>
	 </tr>
	
	<%
	Iterator it8 = funcList.iterator();
	
	while(it8.hasNext()){
		Map map = (Map)it8.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("11"))
	  {
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
	}
	%>	
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>基础资料</b></td>
	 </tr>
	 
	 <%
	Iterator it9 = funcList.iterator();
	
	while(it9.hasNext()){
		Map map = (Map)it9.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("9"))
	  {
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
	}
	%>
	 <tr>
	      <td class="a1" colspan="3" style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>系统管理</b></td>
	 </tr>
	
	<%
	Iterator it10 = funcList.iterator();
	
	while(it10.hasNext()){
		Map map = (Map)it10.next();
		String flag = "";
	  if(StringUtils.nullToStr(map.get("funcflag")).equals("10"))
	  {
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
