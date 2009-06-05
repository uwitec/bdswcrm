<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Cgfk cgfk = (Cgfk)VS.findValue("cgfk");

List cgfkDescs = (List)VS.findValue("cgfkDescs");
%>

<html>
<head>
<title>修改采购付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购付款信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getId()) %></td>
		<td class="a1" widht="20%">是否预付款</td>
		<td class="a2"><%=StringUtils.nullToStr(cgfk.getIs_yfk()) %></td>			
	</tr>
	<tr>	
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getFk_date()) %></td>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfk.getGysbh())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(cgfk.getJsr())) %></td>		
		<td class="a1" widht="20%">本次付款总额</td>
		<td class="a2"><%=JMath.round(cgfk.getFkje(),2) %></td>	
	</tr>	
	<tr>
		<td class="a1" widht="20%">付款账户</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgfk.getFkzh())) %></td>
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">付款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>进货单编号</td>
		<td>发生日期</td>
		<td>发生金额</td>
		<td>本次付款</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(cgfkDescs != null && cgfkDescs.size()>0){
	for(int i=0;i<cgfkDescs.size();i++){
		Map map = (Map)cgfkDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		double yifje = map.get("yifje")==null?0:((Double)map.get("yifje")).doubleValue();
		double bcfk = map.get("bcfk")==null?0:((Double)map.get("bcfk")).doubleValue();
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(map.get("jhd_id")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(map.get("fsrq")) %></td>
		<td class="a2"><%=JMath.round(fsje,2) %></td>
		<td class="a2"><%=JMath.round(bcfk,2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(map.get("remark")) %></td>	
	</tr>
<%
	}
}
%>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">描述</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="cgyfk.ms" id="ms" style="width:80%"><%=StringUtils.nullToStr(cgfk.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
