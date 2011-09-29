<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");
HashMap kcMap = (HashMap)VS.findValue("kcMap");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
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
	
	String strNums = StringUtils.nullToStr(kcMap.get(StringUtils.nullToStr(info.getId())));
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
%>	
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
