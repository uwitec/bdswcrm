<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageQtzc");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");


String zc_date1 = (String)VS.findValue("zc_date1");
String zc_date2 = (String)VS.findValue("zc_date2");
String state = StringUtils.nullToStr(VS.findValue("state"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewQtzc.html?id="+id;
		var fea ='width=750,height=600,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要退回吗！")){
			location.href = "delQtzc.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.zc_date1.value = "";
		document.myform.zc_date2.value = "";
		document.myform.state.value = "";
	}	
	
	function add(){
		var destination = "addQtzc.html";
		var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他支出',fea);	
	}
	
	function edit(id){
		var destination = "editQtzc.html?id=" + id;
		var fea ='width=750,height=600,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他支出',fea);		
	}	
	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
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
	
	function refreshPage(){
		document.myform.action = "listQtzc.html";
		document.myform.submit();
	}	
	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listQtzc.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>一般费用</b></td>
		<td class="csstitle" width="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>	
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			时间：<input type="text" name="zc_date1" value="<%=zc_date1 %>" size="15" class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="zc_date2" value="<%=zc_date2 %>" size="15" class="Wdate" onFocus="WdatePicker()">&nbsp;&nbsp;&nbsp;&nbsp;
			状态：
			<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>待支付</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已支付</option>
			</select>			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>				
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ywy');">申请人<%if(orderName.equals("ywy")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('type');">费用类型<%if(orderName.equals("type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fysq_id');">费用申请单编号<%if(orderName.equals("fysq_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('zcje');">金额<%if(orderName.equals("zcje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>	
		<td onclick="doSort('jsr');">出纳<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('zc_date');">支出日期<%if(orderName.equals("zc_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('zczh');">支出账户<%if(orderName.equals("zczh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>				
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = (results.getResults()).iterator();
	
	while(its.hasNext()){
		Qtzc qtzc = (Qtzc)its.next();
		
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(qtzc.getId()) %>');">
		<td><%=StringUtils.nullToStr(qtzc.getId()) %></td>
		<td><%=StaticParamDo.getRealNameById(qtzc.getSqr()) %></td>
		<td><%=StaticParamDo.getFyTypeNameById(qtzc.getType()) %></td>
		<td><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
		<td align="right"><%=JMath.round(qtzc.getZcje(),2) %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(qtzc.getZc_date()) %></td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %></td>				
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getCzr())) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(qtzc.getState()).equals("已提交")){
		%>
		<a href="#" class="xxlb" onclick="openWin('<%=StringUtils.nullToStr(qtzc.getId()) %>');">查看</a>
		<%	
		}else{
		%>
			<a href="#" class="xxlb" onclick="edit('<%=StringUtils.nullToStr(qtzc.getId()) %>');" title="支出">支出</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="xxlb" onclick="del('<%=StringUtils.nullToStr(qtzc.getId()) %>');" title="退回">退回</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="xxlb" onclick="openWin('<%=StringUtils.nullToStr(qtzc.getId()) %>');" title="查看">查看</a>		
		<%	
		}
		%>

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
