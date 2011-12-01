<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productKcPage");

String product_xh = (String)VS.findValue("product_xh");
String product_name = (String)VS.findValue("product_name");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
String iscs_flag = (String)VS.findValue("iscs_flag");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	//打开设置存初始数量窗口
	function openWin(id,qz_flag){
		var destination = "";
		var fea = "";
		
		if(qz_flag == "是"){
			destination = "addProductKcSerial.html?product_id="+id;
			fea ='width=400,height=350,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		}else{
			destination = "addProductKc.html?product_id="+id;
			fea ='width=500,height=400,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		}

		window.open(destination,'设置库存数量',fea);	
	}
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
	}
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listProductKc.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存初始</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb"> 导出EXCEL </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			商品名称：<input type="text" name="product_name" value="<%=product_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			规格：<input type="text" name="product_xh" value="<%=product_xh %>">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>序号</td>
		<td onclick="doSort('product_name');">商品名称<%if(orderName.equals("product_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('product_xh');">规格<%if(orderName.equals("product_xh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('dw');">计量单位<%if(orderName.equals("dw")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>初始日期</td>
		<td>初始库存数量</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		i = i + 1 ;
	%>
	<tr class="a1"  onmousedown="trSelectChangeCss()" <%if(iscs_flag.equals("0")){ %> title="双击设置商品库存" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>');" <%}else{ %> title="库存初始已完成"<%} %>>
		<td><%=i %></td>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>		
		<td><%=StringUtils.nullToStr(map.get("dw")) %></td>
		<td><%=StringUtils.nullToStr(map.get("init_date")) %></td>
		<td><%=StringUtils.nullToStr(map.get("init_nums")) %></td>
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
<BR>
商品库存初始分为两种情况，一、强制序列号商品，需要输入序列号来初始库存数量；二、不需要强制序列号商品，只需输入初始数量即可。<BR>
系统初始工作结束后商品库存初始数据只可查看，不能修改。
</form>
</div>
</body>
</html>
