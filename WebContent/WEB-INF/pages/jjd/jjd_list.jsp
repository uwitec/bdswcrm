<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("jjdPage");

String jj_date1 = (String)VS.findValue("jj_date1");
String jj_date2 = (String)VS.findValue("jj_date2");
String qz_serial_num = (String)VS.findValue("qz_serial_num");
String state = (String)VS.findValue("state");
String linkman = (String)VS.findValue("linkman");
String jjr=(String)VS.findString("jjr");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>接件单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewJjd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delJjd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.linkman.value = "";
		document.myform.qz_serial_num.value = "";
		document.myform.jj_date1.value = "";
		document.myform.jj_date2.value = "";
		document.myform.state.value = "";
		document.myform.jjr.value = "";
	}
	
	function add(){
		var destination = "addJjd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';	
		window.open(destination,'添加接件单',fea);	
	}
	
	function edit(id){
		var destination = "editJjd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改接件单',fea);		
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
	
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();		
	}
	
	function refreshPage(){
		document.myform.action = "listJjd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listJjd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>接件单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="add();"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			联系人：<input type="text" name="linkman" value="<%=linkman %>" size="10"> 
			序列号：<input type="text" name="qz_serial_num" value="<%=qz_serial_num %>" size="10"> 
			时间： <input type="text" name="jj_date1" id="creatdate" value="<%=jj_date1 %>" class="Wdate" onFocus="WdatePicker()"> 至 
				  <input type="text" name="jj_date2" id="creatdate2" value="<%=jj_date2 %>"  class="Wdate" onFocus="WdatePicker()">										 
			接件单状态：<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select> 
			接件人：<input type="text" name="jjr" value="<%=jjr %>" size="10">
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">接件单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">单位名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('linkman');">联系人<%if(orderName.equals("linkman")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">手机<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jj_date');">接件时间<%if(orderName.equals("jj_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('jjr');">接件人<%if(orderName.equals("jjr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('cjr');">操作员<%if(orderName.equals("cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map jjd = (Map)it.next();
		 
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(jjd.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(jjd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(jjd.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(jjd.get("client_name"))) %></td>		 
		<td><%=StringUtils.nullToStr(jjd.get("linkman"))  %></td>
		<td><%=StringUtils.nullToStr(jjd.get("lxdh")) %></td>
		<td><%=StringUtils.nullToStr(jjd.get("mobile")) %></td>
		<td><%=StringUtils.nullToStr(jjd.get("jj_date")) %></td>
		<td><%=StringUtils.nullToStr(jjd.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jjd.get("jjr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jjd.get("cjr"))) %></td> 
		<td>
		<%
		if(StringUtils.nullToStr(jjd.get("state")).equals("已提交") )
		{
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(jjd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看接件单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(jjd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改接件单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(jjd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看接件单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(jjd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该接件单" border="0" style="cursor:hand"></a>		
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

<form name="descForm" action="descJjd.html" method="post" target="jjddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="jjddesc" name="jjddesc" onload="dyniframesize('jjddesc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>
