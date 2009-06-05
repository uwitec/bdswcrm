<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productPage");
String curId = StringUtils.nullToStr(VS.findValue("curId"));
String product_name = StringUtils.nullToStr(VS.findValue("product_name"));
String product_xh = StringUtils.nullToStr(VS.findValue("product_xh"));
%>

<html>
<head>
<title>产品列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function add(){
		var id = "<%=curId %>";
		if(id == ""){
			alert("请首先选择产品类别，再添加产品！");
			return false;
		}
		var destination = "product_add.html?curId="+id;
		var fea ='width=600,height=500,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
				
		window.open(destination,'添加产品',fea);
	}
	
	function clearAll(){
		document.myform.product_name.value = "";
		document.myform.product_xh.value = "";
	}	
	
	function openWin(id){
		var destination = "viewProduct.html?productId="+id;
		var fea ='width=600,height=500,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function delProduct(id){
		if(confirm("确定要删除该条记录吗！")){
			document.myform.action = "delProduct.html";
			document.myform.productId.value = id;
			document.myform.submit();
		}
	}
	function editProduct(id){

		var destination = "editProduct.html?productId="+id;
		var fea ='width=600,height=500,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
				
		window.open(destination,'添加产品',fea);
	}
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<productTable.rows.length;i++){
				productTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function refreshPage(){
		document.myform.action = "product_list.html";
		document.myform.submit();
	}		
</script>
</head>
<body>
<form name="myform" action="product_list.html" method="post">
<input type="hidden" name="curId" id="curId" value="<%=curId %>">
<input type="hidden" name="productId">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>产品维护</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			产品名称：<input type="text" name="product_name" value="<%=product_name %>">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			产品规格：<input type="text" name="product_xh" value="<%=product_xh %>">
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>	
</table>

<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0" id="productTable">
	<thead>
	<tr>
		<td>产品编号</td>
		<td>产品名称</td>
		<td>规格</td>
		<td>成本价</td>
		<td>产品属性</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
	%>
	<tr class="a1"  onmousedown="trSelectChangeCss()" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("product_id")) %>');">		
		<td><%=StringUtils.nullToStr(map.get("product_id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
		<td><%=JMath.round(price,2) %></td>
		<td><%=StringUtils.nullToStr(map.get("prop")) %></td>
		<td><%=StringUtils.nullToStr(map.get("state")) %></td>
		<td>
			<a onclick="editProduct('<%=StringUtils.nullToStr(map.get("product_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改产品信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a onclick="openWin('<%=StringUtils.nullToStr(map.get("product_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看产品信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a onclick="delProduct('<%=StringUtils.nullToStr(map.get("product_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该产品信息" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>

<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
