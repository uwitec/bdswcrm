<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("nbggPage");

String q_con = StringUtils.nullToStr(VS.findValue("q_con"));
String start_date = StringUtils.nullToStr(VS.findValue("start_date"));
String end_date = StringUtils.nullToStr(VS.findValue("end_date"));
String czr = StringUtils.nullToStr(VS.findValue("czr"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内部公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function openWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';

		window.open(destination,'详细信息',fea);	
	}

	function clearAll(){
		document.myform.q_con.value = "";
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.czr.value = "";
	}

	function refreshPage(){
		document.myform.action = "listGdNbgg.html";
		document.myform.submit();
	}
</script>
</head>
<body >
<form name="myform" action="listGdNbgg.html" method="post">
<div class="rightContentDiv" id="divContent">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
		<td class="csstitle" width="25%" align="left">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a></td>
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			标题或内容：<input type="text" name="q_con" value="<%=q_con %>" size="20" maxlength="50">&nbsp;
			发布人：<input type="text" name="czr" value="<%=czr %>" size="15" maxlength="50">&nbsp;
			发布时间：<input type="text" name="start_date" value="<%=start_date %>" size="15" class="Wdate" onFocus="WdatePicker()">
			至
			<input type="text" name="end_date" value="<%=end_date %>" size="15" class="Wdate" onFocus="WdatePicker()">
			&nbsp;
			<input type="submit" name="buttonCx" value="查询" class="css_button"><input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>序号</td>
		<td>标题</td>
		<td>发布时间</td>
		<td>发布人</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		XxfbNbgg info = (XxfbNbgg)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=i %></td>
		<td class="a1"><a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');" class="xxlb"><%=StringUtils.nullToStr(info.getTitle()) %></a></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getPub_date()) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td class="a1">
			<a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
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
</table>
</form>
</div>
</body>
</html>
