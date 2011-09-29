<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YufuToYingfu yufuToYingfu = (YufuToYingfu)VS.findValue("yufuToYingfu");
double clientHjYufuK = (Double)VS.findValue("clientHjYufuK");

List yufuToYingfuDescs = (List)VS.findValue("yufuToYingfuDescs");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>预付冲应付</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">预付冲应付</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(yufuToYingfu.getId()) %></td>
		<td class="a1" width="15%">结算日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(yufuToYingfu.getCreate_date()) %></td>		
	</tr>
	<tr>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(yufuToYingfu.getClient_name())) %></td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(yufuToYingfu.getJsr())) %></td>				
	</tr>
	<tr>		
		<td class="a1" width="15%">预付总金额</td>
		<td class="a2" colspan="3"><%=clientHjYufuK %></td>					
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">收款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>销售单编号</td>
		<td>应收金额</td>
		<td>本次结算</td>
		<td>备注</td>
	</tr>
	</thead>
<%
double hj_yingfuje = 0;
double hj_bcjs = 0;

if(yufuToYingfuDescs != null && yufuToYingfuDescs.size()>0){
	for(int i=0;i<yufuToYingfuDescs.size();i++){
		YufuToYingfuDesc info = (YufuToYingfuDesc)yufuToYingfuDescs.get(i);

		hj_yingfuje += info.getYingfuje();
		hj_bcjs += info.getBcjs();
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(info.getJhd_id()) %></td>
		<td class="a2"><%=JMath.round(info.getYingfuje()) %></td>
		<td class="a2"><%=JMath.round(info.getBcjs()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(info.getRemark()) %></td>	
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"><%=JMath.round(hj_yingfuje) %></td>
		<td class="a2"><%=JMath.round(hj_bcjs) %></td>
		<td class="a2"></td>	
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备  注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="yufuToYingfu.remark" id="remark" style="width:80%" readonly><%=StringUtils.nullToStr(yufuToYingfu.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
