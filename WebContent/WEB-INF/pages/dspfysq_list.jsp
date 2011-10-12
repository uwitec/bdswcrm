<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List dspFysqList = (List)VS.findValue("dspFysqList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待审批费用申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function doSpFysq(id){
		var destination = "spFysq.html?id=" + id;
		var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'审批费用申请',fea);
	}	
	function refreshPage(){
		document.myform.action = "queryDspFysqList.html";
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
<form name="myform" action="queryDspFysqList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批费用申请</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>申请日期</td>
		<td>申请人</td>
		<td>使用部门</td>
		<td>费用类型</td>
		<td>金额</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = dspFysqList.iterator();
	
	while(its.hasNext()){
		Fysq fysq = (Fysq)its.next();
		String je = JMath.round(((Double)fysq.getJe()).doubleValue(),2);
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(fysq.getId()) %></td>
		<td><%=StringUtils.nullToStr(fysq.getCreatdate()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(fysq.getSqr())) %></td>
		<td><%=StaticParamDo.getDeptNameById(StringUtils.nullToStr(fysq.getYwy_dept())) %></td>
		<td><%=StringUtils.nullToStr(fysq.getFy_type()) %></td>
		<td><%=je %>&nbsp;&nbsp;</td>
		<td>
			<a href="javascript:doSpFysq('<%=StringUtils.nullToStr(fysq.getId()) %>');" class="xxlb">审批</a>
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