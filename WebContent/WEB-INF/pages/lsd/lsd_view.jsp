<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");


Lsd lsd = (Lsd)VS.findValue("lsd");
List lsdProducts = (List)VS.findValue("lsdProducts");

%>
<html>
<head>
<title>零售单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function chgKpTyle(vl){
		var obj_mc1 = document.getElementById("mc1");
		var obj_mc2 = document.getElementById("mc2");
		
		var obj_dz1 = document.getElementById("dz1");
		var obj_dz2 = document.getElementById("dz2");
		
		var obj_dh1 = document.getElementById("dh1");
		var obj_dh2 = document.getElementById("dh2");
		
		var obj_zh1 = document.getElementById("zh1");
		var obj_zh2 = document.getElementById("zh2");
		
		var obj_sh1 = document.getElementById("sh1");
		var obj_sh2 = document.getElementById("sh2");
		
		if(vl == "出库单"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
			
			obj_dh1.style.display = "none";
			obj_dh2.style.display = "none";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vl == "普通发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vl == "增值发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "";
			obj_zh2.style.display = "";
			
			obj_sh1.style.display = "";
			obj_sh2.style.display = "";			
		}
	}	
</script>
</head>
<body onload="chgKpTyle('<%=StringUtils.nullToStr(lsd.getFplx()) %>');">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">零售单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsd.getId()) %></td>	
		<td class="a1">销售日期</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getCreatdate()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsd.getXsry())) %></td>
		<td class="a1" width="15%">出货库房</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(lsd.getStore_id())) %></td>					
	</tr>
	<tr height="35">
		<td class="a1">是否存在预收款</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getHas_yushk()) %></td>	
		<td class="a1">预收款编号</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getYushk_id()) %></td>
	</tr>
	<tr height="35">
		<td class="a1">预收金额</td>
		<td class="a2"><%=JMath.round(lsd.getYushkje()) %></td>	
		<td class="a1">本次实收金额</td>
		<td class="a2"><%=JMath.round(lsd.getSkje()) %></td>
	</tr>	
	<tr>
		<td class="a1" widht="20%">客户付款方式</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getFkfs()) %>&nbsp;<%=StaticParamDo.getPosNameById(lsd.getPos_id()) %></td>		
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsd.getSkzh())) %></td>
	</tr>
	<tr height="35">
		<td class="a1">零售单合计金额</td>
		<td class="a2"><%=JMath.round(lsd.getLsdje(),2) %></td>
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
		<td>商品名称</td>
		<td>规格</td>
		<td>销售价格</td>
		<td>数量</td>
		<td>工分</td>
		<td>小计（元）</td>
	</tr>
	</thead>
<%
if(lsdProducts != null && lsdProducts.size()>0){
	for(int i=0;i<lsdProducts.size();i++){
		LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
		
		
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(lsdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(lsdProduct.getProduct_xh()) %></td>	
		<td class="a2"><%=JMath.round(lsdProduct.getPrice(),2) %></td>
		<td class="a2"><%=lsdProduct.getNums() %></td>
		<td class="a2"><%=JMath.round((lsdProduct.getGf() * lsdProduct.getNums()),2) %></td>
		<td class="a2"><%=JMath.round(lsdProduct.getXj(),2) %></td>
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
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsd.getClient_name()) %></td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsd.getLxr()) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getLxdh()) %></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getMobile()) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getAddress()) %></td>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"><%=StringUtils.nullToStr(lsd.getMail()) %></td>		
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">发票类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsd.getFplx()) %></td>		
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" width="35%" id="mc2"><%=StringUtils.nullToStr(lsd.getKp_mc()) %></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><%=StringUtils.nullToStr(lsd.getKp_address()) %></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><%=StringUtils.nullToStr(lsd.getKp_dh()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><%=StringUtils.nullToStr(lsd.getKhhzh()) %></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><%=StringUtils.nullToStr(lsd.getSh()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">发票信息摘要</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(lsd.getFpxx()) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(lsd.getMs()) %></td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</body>
</html>
