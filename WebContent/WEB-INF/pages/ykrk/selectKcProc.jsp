<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page shkcPage = (Page)VS.findValue("shkcPage");
String product_name = StringUtils.nullToStr((String)VS.findValue("product_name"));
String openerId = ParameterUtility.getStringParameter(request, "openerId","");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>售后好件库列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.product_name.value = "";
	}
	
	

	function sel(product_id,product_xh,product_name,qz_serial_num)
	{
		var id = window.opener.document.getElementById("product_id_<%=openerId%>");
		var xh = window.opener.document.getElementById("product_xh_<%=openerId%>");
		var name = window.opener.document.getElementById("product_name_<%=openerId%>");		
		var qz_flag = window.opener.document.getElementById("qz_flag_<%=openerId%>");
		
		if(id != null) id.value = product_id;
		if(xh != null) xh.value = product_xh;
		if(name != null) name.value = product_name;		
		if(qz_flag != null) qz_flag.value = qz_flag;
		window.close();	
	}
	
</script>
</head>
<body>
<form name="myform" action="selShkcByhao.html" method="post">
<input type="hidden" name="chk_id" value="">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">
			&nbsp;&nbsp;&nbsp;&nbsp;商品：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">		
		</td>			
	</tr>
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td nowrap>商品名称</td>
		<td nowrap>规格</td>		
		<td nowrap>好件数量</td>		
		<td nowrap>强制序列号</td>
	</tr>
	</thead>
	<%
	List shkcKcs = shkcPage.getResults();
	if(shkcKcs != null && shkcKcs.size()>0){
		Iterator it = shkcKcs.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();			
						
			String vl = StringUtils.nullToStr(map.get("product_id")) + "|" + StringUtils.nullToStr(map.get("product_xh")) + "|" + StringUtils.nullToStr(map.get("product_name")) + "|" +StringUtils.nullToStr(map.get("qz_serial_num"))+ "|" +StringUtils.nullToStr(map.get("qz_flag")) ;
	%>
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键单击选择商品" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=StringUtils.nullToStr(map.get("qz_flag")) %>');">	
			<td align="left"><%=StringUtils.nullToStr(map.get("product_name")) %></td>
			<td align="left"><%=StringUtils.nullToStr(map.get("product_xh")) %></td>			
			<td nowrap><%=StringUtils.nullToStr(map.get("hj_nums")) %></td>
			<td><%=StringUtils.nullToStr(map.get("qz_serial_num")) %></td>
		</tr>
	
	<%
		}
	}
	%>	
		<tr>
			<td colspan="10" class="page"><%=shkcPage.getPageScript() %></td>
		</tr>	
</table>
</form>
</body>
</html>
