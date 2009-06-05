<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List productKindList = (List)VS.findValue("productKindList");
%>
<html>
<head>
<title>库存数量汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">

	function subForm(){
		document.reportForm.submit();
	}
	
</script>
</head>
<body>
<form name="reportForm" action="getKcMxResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">工分限价汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">商品类别</td>
		<td class="a4">
			<select name="product_kind">
				<option value=""></option>
				<%
				if(productKindList != null &&  productKindList.size()>0){
					for(int i=0;i<productKindList.size();i++){
						Map map = (Map)productKindList.get(i);
						String id = StringUtils.nullToStr(map.get("id"));
						String name = StringUtils.nullToStr(map.get("name"));
						for(int k=0;k<id.length()-3;k++){
							name = "　" + name;
						}
				%>
				<option value="<%=id %>"><%=name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">商品名称</td>
		<td class="a4">
			<input type="text" name="product_name" id="product_name" value="">
		</td>					
	</tr>
	<tr>
		<td class="a1">商品规格</td>
		<td class="a4">
			<input type="text" name="product_xh" id="product_xh" value="">
		</td>				
	</tr>
	<tr height="35">
		<td class="a1" colspan="4" height="35">
			<input type="button" name="button1" value="提 交" onclick="subForm();" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
