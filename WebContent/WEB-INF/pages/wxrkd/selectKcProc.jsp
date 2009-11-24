<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page shkcPage = (Page)VS.findValue("shkcPage");
String product_serial_num = ParameterUtility.getStringParameter(request,"product_serial_num", "");
 

 
%>

<html>
<head>
<title>售后坏件库列表</title>
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
	
	function sel(product_id,product_xh,product_name,serial_num,client_name,linkman,lxdh,address,remark){
	
		var id = window.opener.document.getElementById("product_id");
		var product_serial_num = window.opener.document.getElementById("product_serial_num");
		var name = window.opener.document.getElementById("product_name");
		var xh = window.opener.document.getElementById("product_xh");
		var kf = window.opener.document.getElementById("kf");
		var product_remark = window.opener.document.getElementById("product_remark");
		 
		 
		if(id!=null)
		{
		   id.value=product_id;
		}
		if(xh!=null)
		{
		   xh.value=product_xh;
		}
		if(name!=null)
		{ 
		   name.value=product_name;
		  
		}
		if(product_serial_num!=null)
		{ 
		   product_serial_num.value=serial_num;
		  
		}
		if(kf!=null)
		{ 
		   kf.value='在外库';
		  
		}
		if(product_remark!=null)
		{ 
		   product_remark.value=remark;
		  
		}
		window.close();	
	}
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selBxProcCkd.html" method="post">

<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择坏件库商品列表</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			序列号：<input type="text" name="product_serial_num" value="<%=product_serial_num %>" size="20">&nbsp;&nbsp;
		 
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
		<td>序列号</td>
		<td>报修天数</td>
		<td>客户名称</td>
		<td>联系人</td>
		<td>联系电话</td>
		 
	</tr>
	</thead>
	<%
	List shkcProduct = shkcPage.getResults();
	if(shkcProduct != null && shkcProduct.size()>0){
		Iterator it = shkcProduct.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			 
	%>
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键单击选择产品" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>','<%=StringUtils.nullToStr(map.get("client_name")) %>','<%=StringUtils.nullToStr(map.get("linkman")) %>','<%=StringUtils.nullToStr(map.get("mobile")) %>','<%=StringUtils.nullToStr(map.get("address ")) %>','<%=StringUtils.nullToStr(map.get("remark")) %>');">		
			<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
			<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
			<td><%=StringUtils.nullToStr(map.get("qz_serial_num")) %></td>
			<td><%=StringUtils.nullToStr(map.get("day_num")) %></td>
			<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
			<td><%=StringUtils.nullToStr(map.get("linkman")) %></td>		 
			<td><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		</tr>
	
	<%
		}
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=shkcPage.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
