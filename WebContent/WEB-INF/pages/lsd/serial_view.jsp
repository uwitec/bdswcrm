<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("serialList");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序列号信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

</script>

<body align="center">
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>名称</td>
		<td>序列号</td>		
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" >
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("serial_num")) %></td>
		
	</tr>
	
	<%
	}
	%>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>		
</table>
</body>
</html>
