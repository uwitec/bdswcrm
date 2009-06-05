<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
Rkd rkd = (Rkd)VS.findValue("rkd");
List rkdProducts = (List)VS.findValue("rkdProducts");
List userList = (List)VS.findValue("userList");
%>

<html>
<head>
<title>查看入库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<form name="rkdForm" action="updateRkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">入库单信息</td>
	</tr>
	</thead>
	<tr>
	<tr>
		<td class="a1" width="15%">入库单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(rkd.getRkd_id()) %></td>	
		<td class="a1">入库时间</td>
		<td class="a2"><%=StringUtils.nullToStr(rkd.getRk_date()) %></td>
	</tr>
	<tr>	
		<td class="a1" width="15%">仓库</td>
		<td class="a2" width="35%">
			<%
			String store_id = StringUtils.nullToStr(rkd.getStore_id());
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
					if(store_id.equals(id)) out.print(name); 
				}
			}
			%>	
		</td>
		<td class="a1" width="15%">仓管员</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(rkd.getFzr())) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">入库单状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(rkd.getState())%>
		</td>
		<td class="a1">创建时间</td>
		<td class="a2"><%=StringUtils.nullToStr(rkd.getCreatdate()) %></td>			
	</tr>	
	<tr>
		<td class="a1" width="15%">进货单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(rkd.getJhd_id()) %></td>	
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(rkd.getCgfzr())) %></td>			
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(rkd.getClient_name())) %></td>	
	</tr>			
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">描述信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="6" name="rkd.ms" id="ms" style="width:75%" readonly><%=StringUtils.nullToStr(rkd.getMs()) %></textarea>
		</td>
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
<table width="100%"  align="center" id="rktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td>
		<td>进货价</td>
		<td>数量</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(rkdProducts != null && rkdProducts.size()>0){
	for(int i=0;i<rkdProducts.size();i++){
		Map rkdProduct = (Map)rkdProducts.get(i);
		double price = rkdProduct.get("price")==null?0:((Double)rkdProduct.get("price")).doubleValue();
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("product_name")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("product_xh")) %></td>
		<td class="a2"><%=JMath.round(price,2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("nums")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("remark")) %></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
