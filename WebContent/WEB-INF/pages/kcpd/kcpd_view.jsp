<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Kcpd kcpd = (Kcpd)VS.findValue("kcpd");
List kcpdDescs = (List)VS.findValue("kcpdDesc");

%>

<html>
<head>
<title>库存盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">库存盘点</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(kcpd.getId()) %></td>	
		<td class="a1">盘点日期</td>
		<td class="a2"><%=StringUtils.nullToStr(kcpd.getPdrq()) %></td>			
	</tr>	
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(kcpd.getStore_id()) %></td>		
		<td class="a1" width="15%">盘点人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(kcpd.getPdr()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%" colspan="3"><%=StringUtils.nullToStr(kcpd.getState()) %></td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">盘点详情</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="kcpdDescTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>账面数量</td>
		<td>实际数量</td>
		<td>盈亏</td>
	</tr>
	</thead>
<%
if(kcpdDescs!=null && kcpdDescs.size()>0){
	for(int i=0;i<kcpdDescs.size();i++){
		KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(kcpdDesc.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kcpdDesc.getProduct_xh()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kcpdDesc.getKc_nums()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kcpdDesc.getSj_nums()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(kcpdDesc.getYk()) %></td>
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
			<textarea rows="4" name="kcpd.remark" id="remark" style="width:75%" readonly><%=StringUtils.nullToStr(kcpd.getRemark()) %></textarea>
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
