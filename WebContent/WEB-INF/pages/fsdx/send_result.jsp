<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%

   List loglist=(List)request.getAttribute("loglist"); %>

<html>
<head>
<title>联系人列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	

	
	
		
		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listlinkman.html" method="post">


<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td >联系人</td>
		<td >手机</td>
		<td >发送状态</td>
		 
	</tr>
	</thead>
	<%
	Iterator its = loglist.iterator();
	
	while(its.hasNext()){
		SendMsgLog log = (SendMsgLog)its.next();
	%>
	<tr class="a1">
		<td class="a1"><%=StringUtils.nullToStr(log.getUser_name()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(log.getMobile_num()) %></td>
		<td class="a1"><% if(log.getFlag()=="1"){out.print("成功");}else{out.print("失败");} %></td>
	</tr>
	
	<%
	}
	%>
</table>

</form>
</body>
</html>
