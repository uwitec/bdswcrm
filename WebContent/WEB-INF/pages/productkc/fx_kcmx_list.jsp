<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productKcPage");
String kc_con = (String)VS.findValue("kc_con");
%>

<html>
<head>
<title>库存查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
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
<form name="listForm" action="queryKcInfo.html" method="post">
<input type="hidden" name="kc_con" value="<%=kc_con %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存查询</b></td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>商品编号</td>
		<td>商品名称</td>
		<td>规格</td>	
		<td>库存情况</td>
		<td>分销限价</td>
	</tr>
	</thead>
	<%
	Iterator it = results.getResults().iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
		double fxbj = map.get("fxbj")==null?0:((Double)map.get("fxbj")).doubleValue();
		
		String num = StringUtils.nullToStr(map.get("nums"));
		if(num.equals("")){
			num = "0";
		}
		int nums = Integer.parseInt(num);
		
		String kcqk = "无";
		if(nums > 0){
			kcqk = "有";
		}
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(map.get("product_id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
		<td><%=kcqk %></td>
		<td align="right"><%=JMath.round(fxxj,2) %>&nbsp;</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript("listForm") %></td>
	</tr>
</table>
</form>
</body>
</html>
