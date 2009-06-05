<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List onlineUserList = (List)application.getAttribute("onlineUserList");
List userList = (List)VS.findValue("userList");
%>

<html>
<head>
<title>在线用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function refreshPage(){
		document.myform.action = "showOnlineUser.html";
		document.myform.submit();
	}
</script>
</head>
<body >
<form name="myform" action="showOnlineUser.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>在线用户</b></td>	
		<td class="csstitle" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>用户名</td>
		<td>姓名</td>
		<td>工作IP</td>
		<td>登陆时间</td>			
	</tr>
	</thead>
	<%
	List<String> onlineUserId = new ArrayList<String>();
	Iterator it = onlineUserList.iterator();
	
	while(it.hasNext()){
		LoginInfo loginInfo = (LoginInfo)it.next();
		onlineUserId.add(loginInfo.getUser_id());
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(loginInfo.getUser_name()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(loginInfo.getReal_name()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(loginInfo.getLast_login_ip()) %></td>
		<td class="a1"><%=DateComFunc.formatDate(loginInfo.getLast_login_time(),"yyyy-MM-dd HH:mm:ss") %></td>		
	</tr>
	
	<%
	}
	%>
</table>

<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>离线用户</b></td>		
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>用户名</td>
		<td>姓名</td>
		<td>末次登录时间</td>
		<td>末次退出时间</td>			
		<td>末次工作IP</td>
	</tr>
	</thead>
	<%
	Iterator it2 = userList.iterator();
	
	while(it2.hasNext()){
		Map map = (Map)it2.next();
		
		String user_id = StringUtils.nullToStr(map.get("user_id"));
		
		if(!onlineUserId.contains(user_id)){
	%>	
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(map.get("user_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td class="a1"><%=DateComFunc.formatDate((Date)map.get("last_login_time"),"yyyy-MM-dd HH:mm:ss") %></td>
		<td class="a1"><%=DateComFunc.formatDate((Date)map.get("last_logout_time"),"yyyy-MM-dd HH:mm:ss") %></td>	
		<td class="a1"><%=StringUtils.nullToStr(map.get("last_login_ip")) %></td>	
	</tr>
<%
		}
	}
%>
</table>
</form>
</body>
</html>
