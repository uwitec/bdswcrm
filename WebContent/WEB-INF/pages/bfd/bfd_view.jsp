<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Bfd bfd = (Bfd)VS.findValue("bfd");
List bfdProducts = (List)VS.findValue("bfdProducts");
 %>
<html>
<head>
<title>报废单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">	
</script>

</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">报废单信息</td>
	</tr>
	</thead>

	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bfd.getId()) %> </td>	
		 
		<td class="a1">报废日期</td>
		<td class="a2"><%=StringUtils.nullToStr(bfd.getBf_date()) %></td>		
	</tr>
	<tr>		
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bfd.getJsr())) %></td>
		<td class="a1" width="15%">报废单状态</td>
		<td class="a2"><%=StringUtils.nullToStr(bfd.getState()) %></td>					
	</tr>
	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>		
</table>
<table width="100%"  align="center" id="bxdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
	    <td width="20%">产品名称</td>	
		<td width="15%">规格</td>
		<td width="3%">数量</td>	
		<td width="5%">仓库</td>			
		<td width="15%">备注</td>		
	</tr>
	</thead>
<%
 if(bfdProducts!=null&&bfdProducts.size()>0)
 {
      for(int i=0;i<bfdProducts.size();i++)
      {
           BfdProduct bfdProduct= (BfdProduct)bfdProducts.get(i); 
 %>
	<tr>
	            <td class="a2"><%=StringUtils.nullToStr(bfdProduct.getProduct_name()) %></td>
				<td class="a2"><%=StringUtils.nullToStr(bfdProduct.getProduct_xh()) %></td>	
				<td class="a2"><%=StringUtils.nullToStr(bfdProduct.getNums()) %></td>		 
				<td class="a2"><%="坏件库" %>	</td>			
				<td class="a2"><%=StringUtils.nullToStr(bfdProduct.getProduct_remark()) %></td>				
	</tr>
<%
}
 }
%>
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="bfd.remark" id="remark" style="width:75%" readonly><%=StringUtils.nullToStr(bfd.getRemark()) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</BODY>
</HTML>