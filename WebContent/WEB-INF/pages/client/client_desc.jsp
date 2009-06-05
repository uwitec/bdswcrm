<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("descClientLinkman");

%>

<html>
<head>
<title>联系人</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">联系人</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%">联系人</td>
		<td width="7%">类型</td>
		<td width="15%">手机</td>
		<td width="15%">座机</td>
		<td width="15%">邮箱</td>
		<td width="15%">其他联系方式</td>
		<td width="18%">备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		ClientsLinkman linkman = (ClientsLinkman)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(linkman.getName()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getLx()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getYddh()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getGzdh()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getMail()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getQtlx()) %></td>
		<td><%=StringUtils.nullToStr(linkman.getRemark()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
