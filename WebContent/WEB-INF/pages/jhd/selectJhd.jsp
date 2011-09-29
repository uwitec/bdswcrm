<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("jhdPage");
List providers = (List)VS.findValue("providers");


String gysbh = ParameterUtility.getStringParameter(request, "gysbh","");
String state = ParameterUtility.getStringParameter(request, "state","");
String cg_date = ParameterUtility.getStringParameter(request, "cg_date","");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>进货单列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language="JavaScript">

function sel(jhd_id,gysbh,yfje){
	var id = window.opener.document.getElementById("jhd_id");
	var bh = window.opener.document.getElementById("gysbh");
	var je = window.opener.document.getElementById("yfje");
	
	id.value = jhd_id;
	bh.value = gysbh;
	je.value = yfje;
	
	window.close();	
}
</script> 
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listJhd.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择进货单</b></td>		
	</tr>
	<tr>
		<td class="search" align="left">&nbsp;
			供应商：
			<select name="gysbh">
				<option value=""></option>
			<%
			if(providers != null){
				Iterator it = providers.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String id = StringUtils.nullToStr(map.get("id"));
					String name = StringUtils.nullToStr(map.get("name"));
			%>
				<option value="<%=id %>" <%if(gysbh.equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>
			&nbsp;&nbsp;
			进货单状态：
			<select name="state">
				<option value=""></option>
				<option value="已创建" <%if(state.equals("已创建")) out.print("selected"); %>>已创建</option>
				<option value="已批准" <%if(state.equals("已批准")) out.print("selected"); %>>已批准</option>
				<option value="已发送" <%if(state.equals("已发送")) out.print("selected"); %>>已发送</option>
				<option value="已到货" <%if(state.equals("已到货")) out.print("selected"); %>>已到货</option>
				<option value="已入库" <%if(state.equals("已入库")) out.print("selected"); %>>已入库</option>
				<option value="已取消" <%if(state.equals("已取消")) out.print("selected"); %>>已取消</option>	
			</select>&nbsp;&nbsp;
			采购日期：<input type="text" name="cg_date" value="<%=cg_date %>" size="10"  class="Wdate" onFocus="WdatePicker()">	
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>供应商</td>
		<td>状态</td>
		<td>负责人</td>
		<td>金额</td>
		<td>采购时间</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Jhd jhd = (Jhd)it.next();
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(jhd.getId()) %>','<%=StringUtils.nullToStr(jhd.getGysbh()) %>','<%=jhd.getTotal() %>');">
		<td><%=StringUtils.nullToStr(jhd.getId()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getGysmc()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getState()) %></td>
		<td><%=StaticParamDo.getRealNameById(jhd.getFzr()) %></td>
		<td><%=JMath.round(jhd.getTotal(),2) %></td>
		<td><%=StringUtils.nullToStr(jhd.getCg_date()) %></td>
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
