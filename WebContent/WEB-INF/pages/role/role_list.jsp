<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("rolePage");

String role_name = ParameterUtility.getStringParameter(request,"role_name", "");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delRole.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.role_name.value = "";
	}

	function sel(){
		var k = 0;
		var obj = document.getElementsByName("role_id");
		for(var i=0;i<obj.length;i++){
			var o = obj[i];
			if(o.checked){
				k = k + 1;
			}
		}
		if(k != 1){
			alert("请选择角色，且只能选择一个角色！");
			return;
		}
		
		document.myform.action = "openFuns.html";
		document.myform.submit();
	}
	
	function refreshPage(){
		document.myform.action = "listRole.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listRole.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>角色管理</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="addRole.html" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			角色名称：<input type="text" name="role_name" value="<%=role_name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>选择</td>
		<td>角色编号</td>
		<td>角色名称</td>
		<td>序号</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr>
		<td class="a1"><input type="checkbox" name="role_id" value="<%=StringUtils.nullToStr(map.get("role_id")) %>"></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("role_id")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("role_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("xh")) %></td>
		<td class="a1">
			<a href="editRole.html?id=<%=StringUtils.nullToStr(map.get("role_id")) %>"><img src="images/modify.gif" align="absmiddle" title="修改角色信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("role_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除角色信息" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a2"><input type="button" class="css_button3" name="buttonx" value="设定角色功能" onclick="sel();"></td>
	</tr>
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
