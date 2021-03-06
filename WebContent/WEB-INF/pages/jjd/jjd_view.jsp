<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");


Jjd jjd = (Jjd)VS.findValue("jjd");
List jjdProducts = (List)VS.findValue("jjdProducts");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>接件单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	 
</script>
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">接件单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jjd.getId()) %></td>	
		<td class="a1">接件日期</td>
		<td class="a2"><%=StringUtils.nullToStr(jjd.getJj_date()) %></td>		
	</tr>
	<tr>
			<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jjd.getJjr())) %></td>
		<td class="a1" width="15%">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(jjd.getState()) %></td>				
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
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td  >商品名称</td>
		<td  >规格</td>
		<td  >数量</td>	
		<td  >目标库</td>
		<td  >强制序列号</td>
		<td  >附件</td>
		<td  >返还天数</td>
		<td  >备注</td>	
	</tr>
	</thead>
<%
if(jjdProducts != null && jjdProducts.size()>0){
	for(int i=0;i<jjdProducts.size();i++){
		JjdProduct jjdProduct = (JjdProduct)jjdProducts.get(i);
		
		
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getProduct_xh()) %></td>		 
		<td class="a2"><%=jjdProduct.getNums() %></td>
		<td class="a2">坏件库</td>	
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getQz_serial_num()) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getCpfj()) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getFxts()) %></td>		
		<td class="a2"><%=StringUtils.nullToStr(jjdProduct.getRemark()) %></td>
	</tr>
<%
	}
}
%>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(jjd.getClient_name()) %></td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jjd.getLinkman()) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(jjd.getLxdh()) %></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2"><%= StringUtils.nullToStr(jjd.getMobile())%></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">Email</td>
		<td class="a2"><%=StringUtils.nullToStr(jjd.getMail()) %></td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jjd.getAddress()) %></td>		
	</tr> 
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="jjd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(jjd.getMs()) %> </textarea>
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
