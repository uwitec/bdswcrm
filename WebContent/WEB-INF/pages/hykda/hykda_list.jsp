<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("hykdaPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType"); 

String hykh = StringUtils.nullToStr(VS.findValue("hykh"));
String tel = StringUtils.nullToStr(VS.findValue("tel"));
String sfzh = StringUtils.nullToStr(VS.findValue("sfzh"));
String state = StringUtils.nullToStr(VS.findValue("state"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员卡档案</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function openWin(id){
		var destination = "viewHykda.html?id="+id;
		var fea = 'width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'会员卡档案',fea);	
	}
	
	function logout(id){
		if(confirm("确定要注销该会员卡吗？确认请点击确定按钮。\n如果注销的同时回收会员卡请点击取消按钮，进入发卡管理中做退卡操作。")){
			location.href = "logoutHykda.html?id=" + id;
		}
	}
	
	function edit(id){
		var destination = "editHykda.html?id=" + id;
		var fea = 'width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'会员卡档案',fea);		
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
		document.myform.action = "listHykda.html";
		document.myform.submit();
	}
	
	function clearAll(){
		document.myform.hykh.value = "";
		document.myform.tel.value = "";	
		document.myform.sfzh.value = "";	
		document.myform.state.value = "";		
	}
</script>
</head>
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listHykda.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>会员卡档案</b></td>
		<td class="csstitle" width="25%">
			<!--  <img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			会员卡号：<input type="text" name="hykh" value="<%=hykh %>" maxlength="50" size="15">	
			&nbsp;&nbsp;
			电话: <input type="text" name="tel" value="<%=tel %>" maxlength="50" size="15">	
			&nbsp;&nbsp;
			证件号码: <input type="text" name="sfzh" value="<%=sfzh %>" maxlength="50" size="15">	
			&nbsp;&nbsp;	
			状态:<select name="state">
							<option value=""></option>
							<option value="正常" <%if(state.equals("正常")) out.print("selected"); %>>正常</option>
							<option value="已注销" <%if(state.equals("已注销")) out.print("selected"); %>>已注销</option>
						</select>	
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%" onclick="doSort('hykh');">会员卡号<%if(orderName.equals("hykh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('hymc');">会员名称<%if(orderName.equals("hymc")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="10%" onclick="doSort('lxrname');">会员联系人<%if(orderName.equals("lxrname")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="12%" onclick="doSort('sfzh');">证件号码<%if(orderName.equals("sfzh")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="10%" onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td width="10%" onclick="doSort('mobile');">手机<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="8%" onclick="doSort('fkrq');">发卡日期<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="8%" onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="12%">操作</td>		
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Hykda info = (Hykda)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(info.getHykh()) %></td>
		<td class="a1"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(info.getHymc())) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getLxrname())%> </td>
		<td class="a1"><%=StringUtils.nullToStr(info.getSfzh())%> </td>
		<td class="a1"><%=StringUtils.nullToStr(info.getLxdh()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getMobile()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getFkrq()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getState()) %></td>
		<td class="a1">
		<%if(StringUtils.nullToStr(info.getState()).equals("正常")){ %>
			<a href="javascript:edit('<%=StringUtils.nullToStr(info.getId()) %>');">修改</a>&nbsp;&nbsp;
			<a href="javascript:openWin('<%=StringUtils.nullToStr(info.getId()) %>');">查看</a>&nbsp;&nbsp;
			<a href="javascript:logout('<%=StringUtils.nullToStr(info.getId()) %>');">注销</a>
		<%}else{ %>
			<a href="javascript:openWin('<%=StringUtils.nullToStr(info.getId()) %>');">查看</a>
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
</div>
</body>
</html>
