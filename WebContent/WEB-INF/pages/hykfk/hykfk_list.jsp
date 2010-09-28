<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("hykzzPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType"); 

String hykh = (String)VS.findValue("hykh");
String state = (String)VS.findValue("state");
%>

<html>
<head>
<title>发卡管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function doTh(id){
		if(confirm("确定要退卡吗？退卡原有的会员信息将被注销！")){
			location.href = "doThHykfk.html?id=" + id;
		}
	}
	
	function edit(id){
		var destination = "editHykfk.html?id=" + id;
		var fea = 'width=800,height=550,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'发卡',fea);		
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
		document.myform.action = "listHykfk.html";
		document.myform.submit();
	}
	
	function clearAll(){
		document.myform.hykh.value = "";	
		document.myform.state.value = "";	
	}
</script>
</head>
<body >
<form name="myform" action="listHykfk.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>发卡管理</b></td>
		<td class="csstitle" width="25%"><img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			会员卡号：<input type="text" name="hykh" value="<%=hykh %>" size="25" >	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			发卡状态：	
			<select name="state">
				<option value=""></option>
				<option value="已使用" <%if(state.equals("已使用")) out.print("selected"); %>>已使用</option>
				<option value="未使用" <%if(state.equals("未使用")) out.print("selected"); %>>未使用</option>				
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%" onclick="doSort('hykh');">会员卡号<%if(orderName.equals("hykh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('dept');">发卡机构<%if(orderName.equals("dept")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="15%" onclick="doSort('ssfl');">所属分类<%if(orderName.equals("ssfl")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td width="15%" onclick="doSort('card_type');">卡类型<%if(orderName.equals("card_type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
        <td width="10%" onclick="doSort('yxrq');">有效日期<%if(orderName.equals("yxrq")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
        <td width="10%" onclick="doSort('sxrq');">失效日期<%if(orderName.equals("sxrq")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
        <td width="10%" onclick="doSort('state');">发卡状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%">操作</td>		
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Hykzz info = (Hykzz)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(info.getHykh()) %></td>
		<td class="a1"  align="left"><%=StringUtils.nullToStr(info.getDept()) %></td>
		<td class="a1"  align="left"><%=StaticParamDo.getHykflNameById(info.getSsfl()) %></td>
		<td class="a1"  align="left"><%=StringUtils.nullToStr(info.getCard_type()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getYxrq()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getSxrq()) %> </td>
		<td class="a1"><%=StringUtils.nullToStr(info.getState()) %> </td>			
		<td class="a1">
		<%if(StringUtils.nullToStr(info.getState()).equals("未使用")){ %>
			<a href="javascript:edit('<%=StringUtils.nullToStr(info.getId()) %>');">发卡</a>
		<%}else{ %>
			<a href="javascript:doTh('<%=StringUtils.nullToStr(info.getId()) %>');">退卡</a>
		<%} %>
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
