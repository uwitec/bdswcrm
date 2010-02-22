<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");


Fhkhd fhkhd = (Fhkhd)VS.findValue("fhkhd");
List fhkhdProducts = (List)VS.findValue("fhkhdProducts");

%>
<html>
<head>
<title>返还客户单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">	 
</script>
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">返还客户单信息</td>
	</tr>
	</thead>	 
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fhkhd.getId()) %></td>	
		<td class="a1">返还日期</td>
		<td class="a2"><%= StringUtils.nullToStr(fhkhd.getFh_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(fhkhd.getClient_id())) %></td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fhkhd.getLxr()) %></td>	
	</tr>
	
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(fhkhd.getJsr()) %></td>
		<td class="a1" width="15%">返还客户单状态</td>
		<td class="a2"><%=StringUtils.nullToStr(fhkhd.getState()) %></td>				
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
<table width="100%"  align="center" id="fhkhdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
	    <td width="19%">产品名称</td>	
		<td width="15%">产品规格</td>
		<td width="7%">仓库</td>
		<td width="5%">单价</td>
		<td width="6%">数量</td>	
		<td width="8%">小计</td>
		<td width="14%">强制序列号</td>
		<td width="12%">产品附件</td>
		<td width="13%">备注</td>		
	</tr>
	</thead>
	<%
    if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
    {
      for(int i=0;i<fhkhdProducts.size();i++)
      {
           FhkhdProduct fhkhdProduct= (FhkhdProduct)fhkhdProducts.get(i); 
    %>
	<tr>
	           	<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getProduct_name()) %></td>
				<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getProduct_xh()) %></td>	
				<td class="a2"><%="好件库" %></td>				
				<td class="a2"><%=JMath.round(fhkhdProduct.getPrice()) %></td>
				<td class="a2"><%=fhkhdProduct.getNums() %></td>		 
				<td class="a2"><%=JMath.round(fhkhdProduct.getTotalmoney()) %></td>
				<td class="a2"><%=fhkhdProduct.getQz_serial_num() %></td>					
				<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getCpfj()) %></td>	
				<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getRemark()) %></td>				
			</tr>
   <%
    }
  }
   %>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
   	<tr height="35">	
		<td class="a1">收款金额</td>
		<td class="a2"><%=JMath.round(fhkhd.getSkje()) %></td>
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2" ><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(fhkhd.getSkzh())) %></td>
	</tr>
</table> 
 <BR>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="fhkhd.remark" id="remark" style="width:75%"><%=StringUtils.nullToStr(fhkhd.getRemark()) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</BODY>
</HTML>
