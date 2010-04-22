<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("zxgdPage");

String hf_date1 = (String)VS.findValue("hf_date1");
String hf_date2 = (String)VS.findValue("hf_date2");
 
String state = (String)VS.findValue("state");
String client_name = (String)VS.findValue("client_name");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>咨询工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewZxgd.html?id="+id;
		var fea ='width=750,height=530,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delZxfd.html?id="+id;
		}
	}
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.hf_date1.value = "";
		document.myform.hf_date2.value = "";
		document.myform.state.value = "";		
	}
	
		
	function edit(id){
		var destination ="editZxgd.html?id="+id;
		var fea ='width=750,height=530,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);			
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
		document.myform.action = "listZxgd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listZxgd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>咨询工单</b></td>
		<td class="csstitle" width="25%">
		 
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			联系人：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;
			时间：<input type="text" name="hf_date1" value="<%=hf_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()" >
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="hf_date2" value="<%=hf_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()" >
			&nbsp;&nbsp;
			
			状态：<select name="state">				
				<option value="处理中" <%if(state.equals("处理中")) out.print("selected"); %>>处理中</option>
				<option value="已完成" <%if(state.equals("已完成")) out.print("selected"); %>>已完成</option>
				<option value=""></option>
			</select>&nbsp;&nbsp;
			 
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">咨询工单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">往来单位<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('linkman');">联系人<%if(orderName.equals("linkman")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">联系电话<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('address');">地址<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map zxgd = (Map)it.next();		
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(zxgd.get("id"))%>')">
		<td><%=StringUtils.nullToStr(zxgd.get("id")) %></td>
		<td><%=StringUtils.nullToStr(StaticParamDo.getClientNameById((String)zxgd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(zxgd.get("linkman")) %></td>
		<td><%=StringUtils.nullToStr(zxgd.get("mobile")) %></td>
		<td><%=StringUtils.nullToStr(zxgd.get("address")) %></td>
		<td><%=StringUtils.nullToStr(zxgd.get("state")) %></td>			
		<td>
		<%
		if(StringUtils.nullToStr(zxgd.get("state")).equals("已完成")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(zxgd.get("sfd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看咨询工单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(zxgd.get("sfd_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改咨询工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(zxgd.get("sfd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看咨询工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			 
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

</body>
</html>
