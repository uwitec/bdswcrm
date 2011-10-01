<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>已读消息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function delMsg(id){
		if(window.confirm("确认要删除该消息吗？")){
			document.myform.action = "delReadedMsg.html?msg_id=" + id;
			document.myform.submit();		
		}
	}	
	function refreshPage(){
		document.myform.action = "listReadedMsg.html";
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="listReadedMsg.html" method="post">
<div class="rightContentDiv" id="divContent">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>已读消息</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a></td>			
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td width="10%">发 送 人</td>
		<td width="15%">发送时间</td>
		<td width="15%">读取时间</td>
		<td width="50%">内容</td>
		<td width="10%">操作</td>
	</tr>
	</thead>

	<ww:iterator value="%{msgPage.results}">
	<tr>
		<td class="a1" nowrap="nowrap"><ww:property value="%{getUserRealName(sender_id)}"/></td>
		<td class="a1" nowrap="nowrap"><ww:date name="%{send_time}" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td class="a1" nowrap="nowrap"><ww:date name="%{read_time}" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td class="a1"><ww:property value="%{msg_body}"/></td>
		<td class="a1" nowrap="nowrap">
			<a class="xxlb" href="javascript:delMsg('<ww:property value="%{msg_id}"/>');">删除</a>
		</td>
	</tr>
	</ww:iterator>	

</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><crm:page value="%{msgPage}" formname="myform"/></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
