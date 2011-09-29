<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List ysList = (List)VS.findValue("ysList");
String ysrq1 = (String)VS.findValue("ysrq1");
String ysrq2 = (String)VS.findValue("ysrq2");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应收统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openMx(client_name){
		var destination = "listYstjMx.html?client_name="+client_name;
		var fea ='width=700,height=500,left=' + (screen.availWidth-700)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'应收统计明细',fea);	
	}		
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function clearAll(){
		document.myform.ysrq1.value = "";
		document.myform.ysrq2.value = "";
	}	
		
</script>
</head>
<body >
<form name="myform" action="listYstj.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>应收统计</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb"> 导出EXCEL </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			应收日期：<input type="text" name="ysrq1" value="<%=ysrq1 %>" size="10" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.ysrq1); return false;">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="ysrq2" value="<%=ysrq2 %>" size="10" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.ysrq2); return false;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>客户名称</td>
		<td>应收金额</td>
	</tr>
	</thead>
	<%
	Iterator it = ysList.iterator();
	
	double hj = 0;
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		double yshj = map.get("yshj")==null?0:((Double)map.get("yshj")).doubleValue();
		hj = Amount.add(hj,yshj);
	%>
	<tr class="a1"  title="双击查看详情"  onDblClick="openMx('<%=StringUtils.nullToStr(map.get("client_name")) %>');">
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
		<td><%=JMath.round(yshj,2) %></td>
	</tr>
	
	<%
	}
	%>
	<tr class="a1">
		<td><b>合 计</b></td>
		<td><B><%=JMath.round(hj,2) %></B></td>
	</tr>	
</table>
</form>
</body>
</html>
