<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Xssk xssk = (Xssk)VS.findValue("xssk");

List xsskDescs = (List)VS.findValue("xsskDescs");
%>

<html>
<head>
<title>销售收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售收款信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xssk.getId()) %></td>
		<td class="a1">是否预收款</td>
		<td class="a2"><%=StringUtils.nullToStr(xssk.getIs_ysk()) %></td>			
	</tr>
	<tr>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xssk.getClient_name())) %></td>
		<td class="a1" width="15%">收款日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(xssk.getSk_date()) %></td>			
	</tr>
	
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xssk.getJsr())) %></td>		
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xssk.getSkzh())) %></td>		
	</tr>
	<tr>
		<td class="a1">本次收款总金额</td>
		<td class="a2"><%=JMath.round(xssk.getSkje(),2) %></td>
		<td class="a1" width="15%">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(xssk.getState()) %></td>
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
		<td>发生日期</td>
		<td>发生金额</td>
		<td>本次收款</td>
	</tr>
	</thead>
<%
double hj_fsje = 0;
double hj_ysk = 0;
double hj_bcsk = 0;

if(xsskDescs != null && xsskDescs.size()>0){
	for(int i=0;i<xsskDescs.size();i++){
		Map map = (Map)xsskDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		hj_fsje += fsje;
		double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
		hj_ysk += ysk;
		
		double bcsk = map.get("bcsk")==null?0:((Double)map.get("bcsk")).doubleValue();
		hj_bcsk += bcsk;
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(map.get("xsd_id")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(map.get("fsrq")) %></td>
		<td class="a2"><%=JMath.round(fsje,2) %></td>
		<td class="a2"><%=JMath.round(bcsk,2) %></td>
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"></td>
		<td class="a2"><%=JMath.round(hj_fsje,2) %></td>
		<td class="a2"><%=JMath.round(hj_bcsk,2) %></td>
	</tr>
	<tr height="50">
		<td class="a1">备  注</td>
		<td class="a2" colspan="3" style="text-align: left"><%=StringUtils.nullToStr(xssk.getRemark()) %></td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1">
			<input type="button" name="button1" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
