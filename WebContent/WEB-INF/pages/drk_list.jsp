<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

//入库单权限及相关列表
List drkList = (List)VS.findValue("drkList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待入库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function editRkd(id){
		var destination = "editRkd.html?rkd_id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'入库单',fea);			
	}
	function refreshPage(){
		document.myform.action = "queryDrkList.html";
		document.myform.submit();
	}	
	function printRkd(id){
		var destination = "printRkd.html?rkd_id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'入库单打印',fea);				
	}
	function descMx(id){
		document.descForm.rkd_id.value = id;
		document.descForm.submit();		
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
<form name="myform" action="queryDrkList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待入出库单</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>入库单编号</td>
		<td>创建日期</td>
		<td>客户名称</td>
		<td>采购负责人</td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = drkList.iterator();
	
	while(its.hasNext()){
		Map rkd = (Map)its.next();
		
		String rkd_state = StringUtils.nullToStr(rkd.get("state"));
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');">
		<td><%=StringUtils.nullToStr(rkd.get("rkd_id")) %></td>
		<td><%=StringUtils.nullToStr(rkd.get("creatdate")) %></td>
		<td align="left"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(rkd.get("client_name"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(rkd.get("cgfzr"))) %></td>
		<td>
			<a href="javascript:editRkd('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');" class="xxlb">入库</a>&nbsp;&nbsp;
			<a href="javascript:printRkd('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');" class="xxlb">打印</a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
<form name="descForm" action="descRkd.html" method="post" target="rkddesc">
	<input type="hidden" name="rkd_id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="rkddesc" name="rkddesc" width="100%" onload="dyniframesize('rkddesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>