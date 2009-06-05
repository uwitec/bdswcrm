<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List ysMxList = (List)VS.findValue("ysMxList");

%>

<html>
<head>
<title>应收统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
	function openWin(id){
		var destination = "viewXsd.html?id="+id;
		var fea ='width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'销售单信息',fea);	
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
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>应收明细</b></td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>销售单编号</td>
		<td>经手人</td>		
		<td>客户名称</td>
		<td>合计金额</td>
		<td>应收金额</td>
		<td>应收日期</td>
	</tr>
	</thead>
	<%
	Iterator it = ysMxList.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		double xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
	%>
	<tr class="a1"  title="双击查看原始单据" onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(map.get("xsd_id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("xsd_id")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jsr"))) %></td>		
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
		<td><%=JMath.round(xsdje,2) %></td>
		<td><%=JMath.round(ysk,2) %></td>
		<td><%=StringUtils.nullToStr(map.get("ysrq")) %></td>
	</tr>
	
	<%
	}
	%>	
</table>
<br>
<center>
<input type="button" name="button" value=" 关 闭 " class="css_button2" onclick="window.close();">
</center>
</body>
</html>
