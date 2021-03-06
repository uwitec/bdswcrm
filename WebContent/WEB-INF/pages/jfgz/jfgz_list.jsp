<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("jfgzPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>积分规则</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewJfgz.html?id="+id;
		var fea = 'width=400,height=300,left=' + (screen.availWidth-350)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delJfgz.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addJfgz.html";
		var fea = 'width=400,height=300,left=' + (screen.availWidth-350)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加积分规则',fea);	
	}
	
	function edit(id){
		var destination = "editJfgz.html?id=" + id;
		var fea = 'width=400,height=300,left=' + (screen.availWidth-350)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改积分规则',fea);		
	}		
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
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
		document.myform.action = "listJfgz.html";
		document.myform.submit();
	}
</script>
</head>
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listJfgz.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>积分规则</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
	    <td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jfff');">积分方法<%if(orderName.equals("jfff")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('xfje');">消费金额<%if(orderName.equals("xfje")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('dyjj');">对应积分<%if(orderName.equals("dyjj")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td onclick="doSort('cz_date');">创建日期<%if(orderName.equals("cz_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">创建人<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>			
		<td>操作</td>		
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Jfgz info = (Jfgz)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(info.getId()) %></td>
		<td class="a1"  align="left"><%=StringUtils.nullToStr(info.getJfff()) %></td>
		<td class="a1"  align="right"><%=JMath.round(info.getXfje()) %></td>
		<td class="a1"  align="right"><%=JMath.round(info.getDyjf()) %></td>
		<td class="a1"><%=DateComFunc.formatDate(info.getCz_date()) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td class="a1">
			<a href="javascript:void(0);" onclick="edit('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="del('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
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
