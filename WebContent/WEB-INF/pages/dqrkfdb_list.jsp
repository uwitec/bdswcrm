<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List confirmKfdbList = (List)VS.findValue("confirmKfdbList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待确认库房调拨</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function doConfirmKfdb(id){
		var destination = "toConfirmKfdb.html?id=" + id;
		var fea ='width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'确认库房调拨',fea);
	}	
	function refreshPage(){
		document.myform.action = "queryDqrDfdbList.html";
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
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();			
	}				
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="queryDqrDfdbList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待确认库房调拨</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>日期</td>
		<td>调出库房</td>
		<td>调入库房</td>
		<td>经手人</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = confirmKfdbList.iterator();
	
	while(its.hasNext()){
		Kfdb kfdb = (Kfdb)its.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(kfdb.getId()) %>');" >
		<td><%=StringUtils.nullToStr(kfdb.getId()) %></td>
		<td><%=StringUtils.nullToStr(kfdb.getCk_date()) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getCk_store_id())) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getRk_store_id())) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(kfdb.getJsr())) %></td>
		<td>
			<a href="javascript:doConfirmKfdb('<%=StringUtils.nullToStr(kfdb.getId()) %>');" class="xxlb">确认</a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
<form name="descForm" action="descKfdb.html" method="post" target="drqKfdbDesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="drqKfdbDesc" name="drqKfdbDesc" width="100%" onload="dyniframesize('drqKfdbDesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>