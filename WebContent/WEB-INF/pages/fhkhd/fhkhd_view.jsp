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
		<td class="a2"><%=StringUtils.nullToStr(fhkhd.getFh_date()) %></td>		
	</tr>
	<tr>
			<td class="a1" width="15%">返还人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(fhkhd.getFhr())) %></td>
		<td class="a1" width="15%">返还单状态</td>
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
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td> 		 
		 
		<td>序列号</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(fhkhdProducts != null && fhkhdProducts.size()>0){
	for(int i=0;i<fhkhdProducts.size();i++){
		FhkhdProduct fhkhdProduct = (FhkhdProduct)fhkhdProducts.get(i);
		
		
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getProduct_xh()) %></td>		 
	 
			<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getQz_serial_num()) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(fhkhdProduct.getRemark()) %></td>
	</tr>
<%
	}
}
%>	
</table>
<%
if(StringUtils.nullToStr(fhkhd.getIsfy()).equals("是"))
{
 %>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
  <tr>	   
	      <td  class="a1" width="15%" >报修费用</td>
	      <td  class="a2"  colspan="3">
	       
	         <%if(StringUtils.nullToStr(fhkhd.getIsfy()).equals("否"))
	           {
	              out.print("否");  
	           }
	           if(StringUtils.nullToStr(fhkhd.getIsfy()).equals("是"))
	           {
	              out.print("是");
	           }	            
	          %>	      
	      </td>
	</tr>
	 
	<tr id="fyid" >
	    <td class="a1" width="15%"  >实收费用</td>
		<td class="a2" width="35%"  ><%=StringUtils.nullToStr(fhkhd.getSkje())%></td>
		<td class="a1" width="15%"  >收款账户</td>
		<td class="a2" width="35%"  ><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(fhkhd.getSkzh()))%> <td>		
	</tr>
</table> 
<%} %>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(fhkhd.getClient_name()) %></td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(fhkhd.getLxr()) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(fhkhd.getLxdh()) %></td>	
		 <td class="a1" width="15%">地址</td>
		<td class="a2"><%=StringUtils.nullToStr(fhkhd.getAddress()) %></td>			
	</tr>
 
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="jjd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(fhkhd.getMs()) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			 
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</body>
</html>
