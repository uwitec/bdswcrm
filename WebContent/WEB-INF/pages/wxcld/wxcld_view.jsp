<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map wxclds=(Map)VS.findValue("wxclds");
List wxcldProducts=(List)VS.findValue("wxcldProducts");
String[] wxlx=(String[])VS.findValue("wxlx");

 %>
<html>
<head>
<title>维修处理单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
</script>
</head>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">维修信息</td>
	</tr>
	</thead>	 	
	<tr>
		<td class="a1" width="15%">维修处理单编号</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(wxclds.get("w_id")) %> </td>			 
		<td class="a1">维修日期</td>
		<%
		  String bxdate= StringUtils.nullToStr(wxclds.get("w_jx_date"));
		  String rq="";
		  if(!bxdate.equals(""))
		  {
		    rq=bxdate;
		  } 
		  else
		  {
		    rq=DateComFunc.getToday();
		  }
		 %>
		<td class="a2"> <%=rq %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">维修人</td>
    	<td class="a2"><%=StaticParamDo.getRealNameById((String)wxclds.get("w_wxr"))%></td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("w_state"))%></td>					
	</tr>
	<tr>
		<td class="a1" width="15%">维修状态</td>
    	<td class="a2"><%=StringUtils.nullToStr(wxclds.get("w_wx_state"))%></td>	
		<td class="a1" width="15%">结单日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("w_jd_date"))%></td>					
	</tr>	
	<tr>
		<td class="a1" width="15%">解决方法</td>		   	
    	<td class="a2" width="85%"  colspan="3"><%=StringUtils.nullToStr(wxclds.get("w_jjff")) %></td>				
	</tr>	 	 
</table> 
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售服信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">售服单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("id"))%></td>	
		 	
		<td class="a1">接待日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("jx_date"))%></td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(wxclds.get("client_name")))%></td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(wxclds.get("jxr")))%></td>
	</tr>
	<tr>
	   <td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("linkman"))%></td>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("mobile"))%></td>
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("address"))%></td>
		<td class="a1" width="15%">求助方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("qzfs"))%></td>	
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%"  colspan="3"><%=StringUtils.nullToStr(wxclds.get("ms"))%></td> 
	</tr>			
</table>
 <br>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		  
	<thead>
	<tr>
		<td colspan="4">派工信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">派工单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("w_pgd_id"))%></td> 
		<td class="a1">派工日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("p_yy_date"))%></td> 
	</tr>
	 <tr>	
	   <td class="a1" width="15%">故障类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("p_gzlx"))%></td>
		<td class="a1" width="15%">客户性质</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("p_khxz"))%></td>			
	</tr>
	<tr>	   
		<td class="a1" width="15%">维修类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("p_wx_type"))%></td>
		<td class="a1" width="15%">维修凭证</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("p_wxpz"))%></td>		
	</tr>
	<tr>
	   <td class="a1" width="15%">派工人</td>
	   <td class="a2" colspan="3"><%=StaticParamDo.getRealNameById((String)wxclds.get("p_pgr"))%></td>		
	</tr>	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" width="85%"  colspan="3"><%=StringUtils.nullToStr(wxclds.get("p_gzfx"))%></td>	
	</tr>		 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">维修商品信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>商品规格</td> 
		<td>故障类型</td>
		<td>购买天数</td>
		<td>处理方式</td>	
		<td>序列号</td>
		<td>新序列号</td>	
		<td>备注</td>		 
	</tr>
	</thead>
 <%
 if(wxcldProducts!=null&&wxcldProducts.size()>0)
 {
      for(int i=0;i<wxcldProducts.size();i++)
      {	
		 WxcldProduct wxcldProduct= (WxcldProduct)wxcldProducts.get(i);
 %> 
     <tr>
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_name())%></td>
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_xh())%></td>
	    <td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_wxlx())%></td>
	    <td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_gmts())%></td>
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_clfs())%></td>		
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_serial_num())%></td>	
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getN_product_serial_num())%></td>		
		<td class="a2"><%=StringUtils.nullToStr(wxcldProduct.getProduct_remark())%></td>
	</tr>
<%
	}
}
%>		
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
    <tr>	   
	      <td  class="a1" width="15%" >维修费用</td>
	      <td  class="a2" colspan="3"><%=StringUtils.nullToStr(wxclds.get("w_isfy"))%></td>
	</tr>
	<tr>
	    <td class="a1" width="15%">实收费用</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(wxclds.get("w_skje"))%></td>
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(wxclds.get("w_skzh"))) %></td>			 
	</tr>
	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(wxclds.get("w_ms"))%></td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</HTML>