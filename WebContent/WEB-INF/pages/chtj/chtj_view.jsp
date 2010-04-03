<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Chtj chtj = (Chtj)VS.findValue("chtj");
List chtjDescs = (List)VS.findValue("chtjDesc");

%>

<html>
<head>
<title>存货调价</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">存货调价</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(chtj.getId()) %></td>	
		<td class="a1">日期</td>
		<td class="a2"><%=StringUtils.nullToStr(chtj.getTj_date()) %></td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(chtj.getJsr())) %></td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(chtj.getState()) %></td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="kcpdDescTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>原价格</td>
		<td>调整后价格</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(chtjDescs!=null && chtjDescs.size()>0){
	for(int i=0;i<chtjDescs.size();i++){
		ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(chtjDesc.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(chtjDesc.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(chtjDesc.getYsjg(),2) %></td>
		<td class="a2"><%=JMath.round(chtjDesc.getTzjg(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(chtjDesc.getRemark()) %></td>
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
		<td colspan="2">其它</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="kcpd.remark" id="remark" style="width:75%" maxlength="500" readonly><%=StringUtils.nullToStr(chtj.getRemark()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
