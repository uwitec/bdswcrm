<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bxfhd bxfhd = (Bxfhd)VS.findValue("bxfhd");
List bxfhdProducts = (List)VS.findValue("bxfhdProducts");

%>
<html>
<head>
<title>报修返还单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>

</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">报修返还单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bxfhd.getId()) %></td>	
		 
		<td class="a1">报修返还日期</td>
		<td class="a2"><%=StringUtils.nullToStr(bxfhd.getFh_date()) %></td>		
	</tr>
	<tr>		 
		<td class="a1" width="15%">报修单位</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxfhd.getBxcs())) %></td>		
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getJsr())) %></td>		 
	</tr>
	<tr>	
		<td class="a1" width="15%">合计金额</td>
		<td class="a2" width="35%"><%=JMath.round(bxfhd.getHjje()) %></td>
		<td class="a1" width="15%">本次实付金额</td>
		<td class="a2" width="35%"><%=JMath.round(bxfhd.getSsje()) %></td>		
	</tr>			
	<tr>
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(bxfhd.getFkzh())) %> </td>
		<td class="a1" width="15%">报修返还单状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bxfhd.getState()) %></td>							
	</tr>	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>	
</table>
<table width="100%"  align="center" id="bxfhdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
	    <td width="18%">商品名称</td>	
		<td width="15%">商品规格</td>
		<td width="5%">仓库</td>
		<td width="5%">单价</td>
		<td width="5%">数量</td>	
		<td width="5%">小计</td>
		<td width="12%">强制序列号</td>
		<td width="12%">商品附件</td>
		<td width="15%">备注</td>		
	</tr>
	</thead>
	<%
    if(bxfhdProducts!=null&&bxfhdProducts.size()>0)
    {
      for(int i=0;i<bxfhdProducts.size();i++)
      {
           BxfhdProduct bxfhdProduct= (BxfhdProduct)bxfhdProducts.get(i); 
    %>
	<tr>	            
				<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getProduct_name())%></td>
				<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getProduct_xh()) %></td>	
				<td class="a2"><%="好件库" %>	</td>
				<td class="a2"><%=JMath.round(bxfhdProduct.getPrice())%></td>
				<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getNums())%></td>		 
				<td class="a2"><%=JMath.round(bxfhdProduct.getTotalmoney())%></td>		
				<td class="a2"><%=bxfhdProduct.getQz_serial_num() %></td>						
				<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getCpfj()) %></td>	
				<td class="a2"><%=StringUtils.nullToStr(bxfhdProduct.getRemark()) %></td>				
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
			<textarea rows="4" name="bxfhd.remark" id="remark" style="width:75%"><%=StringUtils.nullToStr(bxfhd.getRemark()) %> </textarea>
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