<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
try{
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");
HashMap kcMap = (HashMap)VS.findValue("kcMap");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>分仓明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>分仓明细</b></td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td class="a1" width="35%"><B>仓库名称</B></td>
		<td class="a1" width="65%"><B>库存数量</B></td>
	</tr>
	</thead>

<%
Iterator it = storeList.iterator();
while(it.hasNext()){
	StoreHouse info = (StoreHouse)it.next();
	String strNums = StringUtils.nullToStr(kcMap.get(info.getId()));
	if(strNums.equals("")){
		strNums =  "0";
	}
%>	
	<tr>	
		<td class="a1">
			<input type="hidden" name="store_id"  value="<%=StringUtils.nullToStr(info.getId()) %>" readonly>
			<%=StringUtils.nullToStr(info.getName()) %>
		</td>
		<td class="a2"><%=strNums %></td>			
	</tr>
<%
}
}catch(Exception e){
	e.printStackTrace();
}
%>	
</table>
</body>
</html>
