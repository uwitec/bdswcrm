<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List dspXsdList = (List)VS.findValue("dspXsdList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待审批销售单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function doSpXsd(id){
		var destination = "spXsd.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批销售订单',fea);
	}
	function refreshPage(){
		document.myform.action = "queryDspXsdList.html";
		document.myform.submit();
	}	
	function descMx(id){
		document.descForm.id.value = id;
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
<form name="myform" action="queryDspXsdList.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批销售单</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>销售单编号</td>
		<td>创建日期</td>
		<td>客户名称</td>
		<td>客户联系人</td>
		<td>联系人电话</td>
		<td>金额</td>
		<td>销售人员</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = dspXsdList.iterator();
	
	while(its.hasNext()){
		Map map = (Map)its.next();
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("creatdate")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("kh_lxr")) %></td>
		<td><%=StringUtils.nullToStr(map.get("kh_lxdh")) %></td>
		<td align="right"><%=JMath.round(xsdje,2) %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("czr"))) %></td>
		<td>
			<a href="javascript:doSpXsd('<%=StringUtils.nullToStr(map.get("id")) %>');" class="xxlb">审批</a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
<form name="descForm" action="descXsd.html" method="post" target="xsddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="xsddesc" name="xsddesc" width="100%" onload="dyniframesize('xsddesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>