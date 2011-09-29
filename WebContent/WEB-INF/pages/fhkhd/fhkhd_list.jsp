<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Page results = (Page)VS.findValue("fhkhdPage");

String fh_date1 = (String)VS.findValue("fh_date1");
String fh_date2 = (String)VS.findValue("fh_date2");

String state = (String)VS.findValue("state");
String lxr = (String)VS.findValue("lxr");
String fhkh=(String)VS.findString("client_name");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>返还客户单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewFhkhd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delFhkhd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.lxr.value = "";
		
		document.myform.fh_date1.value = "";
		document.myform.fh_date2.value = "";
		document.myform.state.value = "";
		document.myform.client_name.value = "";
	}
	
	function add(){
		var destination = "addFhkhd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';	
		window.open(destination,'添加返还客户单',fea);	
	}
	
	function edit(id){
		var destination = "editFhkhd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改返还客户单',fea);		
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
		document.myform.action = "listFhkhd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listFhkhd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>返还客户单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="add();"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
		    返还客户：<input type="text" name="client_name" value="<%=fhkh %>" size="30">&nbsp;&nbsp;
			联系人：<input type="text" name="lxr" value="<%=lxr %>" size="10">&nbsp;&nbsp;			
			返还时间：<input type="text" name="fh_date1" value="<%=fh_date1%> " size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;至&nbsp;
			<input type="text" name="fh_date2" value="<%=fh_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">&nbsp;&nbsp;
			状态：<select name="state">
				<option value=""></option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
			</select>&nbsp;&nbsp;			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">返还客户单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">返还客户<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr');">联系人<%if(orderName.equals("lxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('productNums');">返还数量<%if(orderName.equals("productNums")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('skje');">维修金额<%if(orderName.equals("skje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fh_date');">返还时间<%if(orderName.equals("fh_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('cjr');">操作员<%if(orderName.equals("cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map fhkhd = (Map)it.next();
		String totalmoney = JMath.round((fhkhd.get("skje")==null?0:((Double)fhkhd.get("skje")).doubleValue()),2); 
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(fhkhd.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(fhkhd.get("client_id"))) %></td>
		 
		<td><%=StringUtils.nullToStr(fhkhd.get("lxr"))  %></td>
		<td><%=StringUtils.nullToStr(fhkhd.get("productNums")) %></td>
		<td align="right" nowrap="nowrap"><%=totalmoney %>&nbsp;&nbsp;</td>
		<td><%=StringUtils.nullToStr(fhkhd.get("fh_date")) %></td>		
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(fhkhd.get("jsr"))) %></td>
		<td><%=StringUtils.nullToStr(fhkhd.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(fhkhd.get("cjr"))) %></td> 
		<td>
		<%
		if(StringUtils.nullToStr(fhkhd.get("state")).equals("已提交") )
		{
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看返还客户单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改返还客户单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看返还客户单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(fhkhd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该返还客户单" border="0" style="cursor:hand"></a>		
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

<form name="descForm" action="descFhkhd.html" method="post" target="fhkhddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="fhkhddesc" name="fhkhddesc" onload="dyniframesize('fhkhddesc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
