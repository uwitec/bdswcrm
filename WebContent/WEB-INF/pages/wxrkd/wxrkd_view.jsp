<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Wxrkd wxrkd=(Wxrkd)VS.findValue("wxrkd");
List wxrkdProducts=(List)VS.findValue("wxrkdProducts");
%>
<html>
<head>
<title>维修入库单查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
</head>
<body  >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4"> 维修入库单信息</td>
	</tr>
	</thead>
	 
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxrkd.getId())%></td>	
		 
		<td class="a1">维修时间</td>
		 
		<td class="a2"><%=StringUtils.nullToStr(wxrkd.getWxrk_date())%> 
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">维修人</td>
		<td class="a2">
		   <%=StaticParamDo.getRealNameById(wxrkd.getJsr()) %>		    
		</td>	
		<td class="a1" width="15%">维修单状态</td>
		<td class="a2" width="35%" colspan="3">
			<%=StringUtils.nullToStr(wxrkd.getState())%>	
		</td>						
	</tr>
	 
</table>
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">维修产品信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="wxrkdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td> 
		<td>目标库</td>
		<td>数量</td>
		<td>序列号</td>
		<td>备注</td>		 
	</tr>
	</thead>
 <%
    if(wxrkdProducts!=null&&wxrkdProducts.size()>0)
    {
      for(int i=0;i<wxrkdProducts.size();i++)
      {
           WxrkdProduct wxrkdProduct= (WxrkdProduct)wxrkdProducts.get(i); 
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(wxrkdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(wxrkdProduct.getProduct_xh()) %></td>
	    <td class="a2"><%="好件库" %>	</td>
	    <td class="a2"><%=StringUtils.nullToStr(wxrkdProduct.getNums()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(wxrkdProduct.getQz_serial_num()) %></td>	
		<td class="a2"><%=StringUtils.nullToStr(wxrkdProduct.getRemark()) %></td>
	</tr>
<%
}
} %>
</table>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">备注</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="2" name="wxrkd.remark" id="remark" style="width:75%" readonly="readonly"><%=StringUtils.nullToStr(wxrkd.getRemark()) %> </textarea>
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