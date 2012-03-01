<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("bwlPage");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>备忘录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewBwl.html?id="+id;
		var fea = 'width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function refreshPage(){
		document.myform.action = "listSyBwl.html";
		document.myform.submit();
	}

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
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listSyBwl.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>备忘录</b></td>		
		<td class="csstitle" width="25%" align="left">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>
	</tr>		
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0"  border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>序号</td>
		<td>标题</td>
		<td>创建时间</td>
		<td>创建人</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Bwl info = (Bwl)it.next();
		i++;
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=i %></td>
		<td align="left"><a href="javascript:openWin('<%=StringUtils.nullToStr(info.getId()) %>');" class="xxlb"><%=StringUtils.nullToStr(info.getTitle()) %></a></td>
		<td><%=StringUtils.nullToStr(info.getCz_date()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td>
			<a href="javascript:openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
