<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("userPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
String real_name = (String)VS.findValue("real_name");
%>

<html>
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewUser.html?user_id="+id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delUser.html?user_id=" + id;
		}
	}
	
	function add(){
		var destination = "addUser.html";
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加系统用户',fea);	
	}
	
	function edit(userId){
		var destination = "editUser.html?user_id=" + userId;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加系统用户',fea);	
	}		
	
	function clearAll(){
		document.myform.real_name.value = "";
	}
	
	function sel(){
		var k = 0;
		var objs = document.getElementsByName("user_id");
		for(var i=0;i<objs.length;i++){
			var o = objs[i];
			if(o.checked){
				k = k + 1;
			}
		}
		if(k != 1){
			alert("请选择一个用户！");
			return;
		}
		
		document.myform.action = "openRoles.html";
		document.myform.submit();
	}	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}
	function refreshPage(){
		document.myform.action = "listUser.html";
		document.myform.submit();
	}		
</script>
</head>
<body >
<form name="myform" action="listUser.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>用户管理</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			真实姓名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>选择</td>
		<td onclick="doSort('user_id');">用户编号<%if(orderName.equals("user_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('user_name');">登录名<%if(orderName.equals("user_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('real_name');">真实姓名<%if(orderName.equals("real_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gs_phone');">电话<%if(orderName.equals("gs_phone")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">手机<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
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
		<td class="a1"><input type="checkbox" name="user_id" value="<%=StringUtils.nullToStr(map.get("user_id")) %>"></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("user_id")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("user_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td class="a1">
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改用户信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看用户信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该用户" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
	<tr>
		<td class="a2"><input type="button" class="css_button3" name="buttonx" value="设定用户角色" onclick="sel();"></td>
	</tr>	
</table>
</form>
</body>
</html>
