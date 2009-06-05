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

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_type = info.getIs_dls();   //用户类别
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
	
	function desc(id){
		document.myform.product_id.value = id;
		document.myform.submit();
	}			
</script>
</head>
<body >
<form name="listForm" action="queryKcMx.html" method="post">
<input type="hidden" name="kc_con" value="<%=kc_con %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存查询</b></td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>产品编号</td>
		<td>产品名称</td>
		<td>规格</td>	
		<td>库存数量</td>
	<%
	if(user_type.equals("0")){
	%>
		<td>考核成本</td>
	<%
	}
	%>
		<td>零售报价</td>
		<td>零售限价</td>
	<%
	if(user_type.equals("0")){
	%>		
		<td>代理价</td>
		<td>分销限价</td>
	<%
	}
	%>		
		<td>工分</td>
		<td>点杀</td>
	</tr>
	</thead>
	<%
	Iterator it = results.getResults().iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();
		double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
		double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
		double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
		double fxbj = map.get("fxbj")==null?0:((Double)map.get("fxbj")).doubleValue();
		double dss = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();
		
		double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
		
		String num = StringUtils.nullToStr(map.get("nums"));
		if(num.equals("")){
			num = "0";
		}
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" title="点击查看分仓库存" onclick="desc('<%=StringUtils.nullToStr(map.get("product_id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("product_id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
		<td><%=num %></td>
	<%
	if(user_type.equals("0")){
	%>		
		<td align="right"><%=JMath.round(khcbj,2) %>&nbsp;</td>
	<%
	}
	%>		
		<td align="right"><%=JMath.round(lsbj,2) %>&nbsp;</td>
		<td align="right"><%=JMath.round(lsxj,2) %>&nbsp;</td>
	<%
	if(user_type.equals("0")){
	%>			
		<td align="right"><%=JMath.round(fxbj,2) %>&nbsp;</td>
		<td align="right"><%=JMath.round(fxxj,2) %>&nbsp;</td>
	<%
	}
	%>		
		<td><%=JMath.round(gf) %></td>
		<td><%=JMath.round(dss) %></td>
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

<form name="myform" action="viewFcmx.html" method="post" target="mx">
	<input type="hidden" name="product_id" value="">
</form>
<iframe id="mx" name="mx" width="100%" height="260" onload="dyniframesize('mx');" border="0" frameborder="0" SCROLLING="no"></iframe>
</body>
</html>
