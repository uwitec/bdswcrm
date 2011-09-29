<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("cgfkPage");

String fk_date1 = (String)VS.findValue("fk_date1");
String fk_date2 = (String)VS.findValue("fk_date2");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待审批付款申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewCgfk.html?id="+id;
		var fea = 'width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}

	function doSpCgfk(id){
		var destination = "spCgfk.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批付款申请单',fea);
	}	
	
	function clearAll(){
		document.myform.fk_date1.value = "";
		document.myform.fk_date2.value = "";
	}
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}	
	
	function refreshPage(){
		document.myform.action = "listDspCgfk.html";
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="listDspCgfk.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批付款申请</b></td>
		<td class="csstitle" width="25%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			付款日期：<input type="text" name="fk_date1" value="<%=fk_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="fk_date2" value="<%=fk_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="10%" nowrap="nowrap">编号</td>
		<td width="26%" nowrap="nowrap">往来单位</td>
		<td width="8%" nowrap="nowrap">付款金额</td>		
		<td width="20%" nowrap="nowrap">付款账户</td>
		<td width="8%" nowrap="nowrap">申请人</td>
		<td width="7%" nowrap="nowrap">是否预付款</td>
		<td width="8%" nowrap="nowrap">操作员</td>
		<td width="10%" nowrap="nowrap">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		String fkje = JMath.round((map.get("fkje")==null?0:((Double)map.get("fkje")).doubleValue()),2);
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onDblClick="doSpCgfk('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td nowrap="nowrap"><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gysmc")) %></td>
		<td align="right" nowrap="nowrap"><%=fkje %>&nbsp;</td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(map.get("fkzh"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jsr"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("is_yfk")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("czr"))) %></td>
		<td><a href="#" onclick="doSpCgfk('<%=StringUtils.nullToStr(map.get("id")) %>');">审批</a></td>
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
</body>
</html>
