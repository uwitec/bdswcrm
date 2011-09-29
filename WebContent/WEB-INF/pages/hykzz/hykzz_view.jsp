<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykzz hykzz = (Hykzz)VS.findValue("hykzz");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员卡制作</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">制卡单位</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(hykzz.getDept()) %></td>
	</tr>
	<tr>
		<td class="a1">卡类型</td>
	    <td class="a2"> <%=StringUtils.nullToStr(hykzz.getCard_type())%></td>
	</tr>
	<tr>
		<td class="a1">所属分类</td>
		<td class="a2"><%=StaticParamDo.getHykflNameById(StringUtils.nullToStr(hykzz.getSsfl())) %></td>
	</tr>
	<tr>
		<td class="a1">会员卡号</td>
		<td class="a2"><%=StringUtils.nullToStr(hykzz.getHykh())%></td>
	</tr>
	 
	<tr>
		<td class="a1">有效日期</td>
		<td class="a2"><%=StringUtils.nullToStr(hykzz.getYxrq()) %></td>
	</tr>
	<tr>
		<td class="a1">失效日期</td>
	    <td class="a2"><%=StringUtils.nullToStr(hykzz.getSxrq()) %></td>
	</tr>
	<tr>
		<td class="a1">初始积分</td>
		<td class="a2"><%=StringUtils.nullToStr(hykzz.getCsjf()) %></td>
	</tr>
	<tr>
		<td class="a1">初始密码</td>
	    <td class="a2"><%=StringUtils.nullToStr(hykzz.getCsmm()) %></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">			
			<input type="button" name="button3" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
