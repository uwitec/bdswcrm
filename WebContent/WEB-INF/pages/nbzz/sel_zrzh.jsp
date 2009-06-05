
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("accountList");

%>

<html>
<head>
<title>选择账户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function sel(fkzh,zhmc){
	var id = window.opener.document.getElementById("zrzh");
	var name = window.opener.document.getElementById("zrzh_name");
	
	id.value = fkzh;
	name.value = zhmc;
	
	window.close();	
}
</script>

<body align="center">
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>名称</td>
		<td>类型</td>
		<td>当前余额</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double dqje = map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue();
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(map.get("id")) %>','<%=StringUtils.nullToStr(map.get("name")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("type")) %></td>
		<td><%=JMath.round(dqje,2) %></td>
	</tr>
	
	<%
	}
	%>			
</table>
</body>
</html>
