<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

//出库单权限及相关列表
String isCkdRight = (String)VS.findValue("isCkdRight");
List dckList = (List)VS.findValue("dckList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待出库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function editCkd(id){
		var destination = "editCkd.html?ckd_id=" + id;
		var fea ='width=900,height=650,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'出库单',fea);		
	}
	
	function refreshPage(){
		document.myform.action = "queryDckList.html";
		document.myform.submit();
	}	
	function printCkd(id){
		var destination = "printCkd.html?ckd_id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'出库单打印',fea);				
	}	
	function descMx(id){
		document.descForm.ckd_id.value = id;
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
<form name="myform" action="queryDckList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待出库单</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>出库单编号</td>
		<td>创建日期</td>
		<td>客户名称</td>
		<td>客户联系人</td>
		<td>联系人电话</td>
		<td>销售负责人</td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = dckList.iterator();
	
	while(its.hasNext()){
		Map ckd = (Map)its.next();
		
		String ckd_state = StringUtils.nullToStr(ckd.get("state"));
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');">
		<td><%=StringUtils.nullToStr(ckd.get("ckd_id")) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("creatdate")) %></td>
		<td align="left"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("client_lxr")) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("client_lxr_tel")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.get("xsry"))) %></td>
		<td>
			<a href="javascript:editCkd('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');" class="xxlb">出库</a>&nbsp;&nbsp;
			<a href="javascript:printCkd('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');" class="xxlb">打印</a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
<form name="descForm" action="descCkdProduct.html" method="post" target="ckddesc">
	<input type="hidden" name="ckd_id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="ckddesc" name="ckddesc" width="100%" onload="dyniframesize('ckddesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>