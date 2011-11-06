<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List dspCgfkList = (List)VS.findValue("dspCgfkList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待审批付款申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function doSpCgfk(id){
		var destination = "spCgfk.html?id=" + id;
		var fea ='width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批付款申请单',fea);
	}
	function refreshPage(){
		document.myform.action = "queryDspFksqList.html";
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
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="queryDspFksqList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批付款申请</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>创建日期</td>
		<td>供货商</td>
		<td>付款金额</td>
		<td>付款账户</td>
		<td>经手人</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = dspCgfkList.iterator();
	
	while(its.hasNext()){
		Cgfk cgfk = (Cgfk)its.next();
		String fkje = JMath.round(cgfk.getFkje(),2);
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(cgfk.getId()) %></td>
		<td><%=StringUtils.nullToStr(cgfk.getFk_date()) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfk.getGysbh())) %></td>
		<td><%=fkje %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgfk.getFkzh())) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(cgfk.getJsr())) %></td>
		<td>
			<a href="javascript:doSpCgfk('<%=StringUtils.nullToStr(cgfk.getId()) %>');" class="xxlb">审批</a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
</div>
</body>
</html>