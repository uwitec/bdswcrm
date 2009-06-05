<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page productPage = (Page)VS.findValue("productPage");

String product_xh = ParameterUtility.getStringParameter(request,"product_xh", "");
String product_name = ParameterUtility.getStringParameter(request,"product_name", "");
String prop = ParameterUtility.getStringParameter(request,"prop", "");


String openerId = ParameterUtility.getStringParameter(request, "openerId","");
%>

<html>
<head>
<title>商品列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
	}

	function openWin(id){
		var destination = "viewKcye.html?product_id="+id;
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'设置库存数量',fea);	
	}	
	
	function sel(product_id,product_xh,product_name,lsxj,price,qz_serial_num,kh_cbj,gf){
	
		var id = window.opener.document.getElementById("product_id_<%=openerId%>");
		var xh = window.opener.document.getElementById("product_xh_<%=openerId%>");
		var name = window.opener.document.getElementById("product_name_<%=openerId%>");
		var pc = window.opener.document.getElementById("price_<%=openerId%>");
		var cbj = window.opener.document.getElementById("cbj_<%=openerId%>");
		var qz_flag = window.opener.document.getElementById("qz_flag_<%=openerId%>");
		var khcbj =  window.opener.document.getElementById("kh_cbj_<%=openerId%>");
		var obj_gf =  window.opener.document.getElementById("gf_<%=openerId%>");
		
		if(id != null) id.value = product_id;
		if(xh != null) xh.value = product_xh;
		if(name != null) name.value = product_name;
		if(pc != null) pc.value = lsxj;	
		if(cbj != null) cbj.value = price;
		if(qz_flag != null) qz_flag.value = qz_serial_num;
		if(khcbj != null) khcbj.value = kh_cbj;
		if(obj_gf != null) obj_gf.value = gf;
		
		window.close();	
	}
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selLsProcCkd.html" method="post">
<input type="hidden" name="openerId" value="<%=openerId %>">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择库存产品</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			产品名称：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;
			规格：<input type="text" name="product_xh" value="<%=product_xh %>" size="20">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>库存数量</td>
		<td>考核成本</td>
		<td>零售报价</td>
		<td>零售限价</td>
		<td>工分</td>
		<td>强制序列号</td>
	</tr>
	</thead>
	<%
	List productKcs = productPage.getResults();
	if(productKcs != null && productKcs.size()>0){
		Iterator it = productKcs.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
			double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
			double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();	
			double khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();	
			double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();	
	%>
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键单击选择产品" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=JMath.round(lsbj) %>','<%=JMath.round(price) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>','<%=JMath.round(khcbj) %>','<%=JMath.round(gf) %>');">		
			<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
			<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
			<td><%=StringUtils.nullToStr(map.get("kc_nums")) %></td>
			<td><%=JMath.round(khcbj,2) %></td>
			<td><%=JMath.round(lsbj,2) %></td>
			<td><%=JMath.round(lsxj,2) %></td>
			<td><%=JMath.round(gf,2) %></td>
			<td><%=StringUtils.nullToStr(map.get("qz_serial_num")) %></td>
		</tr>
	
	<%
		}
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=productPage.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
