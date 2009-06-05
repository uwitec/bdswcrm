<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List yfMxList = (List)VS.findValue("yfMxList");

%>

<html>
<head>
<title>应付统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
	function openWin(id){
		var destination = "viewJhd.html?id="+id;
		var fea ='width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'进货单信息',fea);	
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
		<td>进货单编号</td>
		<td>经手人</td>		
		<td>供应商</td>
		<td>合计金额</td>
		<td>应付金额</td>
		<td>应付日期</td>
	</tr>
	</thead>
	<%
	Iterator it = yfMxList.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		double total = map.get("total")==null?0:((Double)map.get("total")).doubleValue();
		double yfk = map.get("yfk")==null?0:((Double)map.get("yfk")).doubleValue();
	%>
	<tr class="a1"  title="双击查看原始单据" onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("fzr"))) %></td>		
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("gysbh"))) %></td>
		<td><%=JMath.round(total,2) %></td>
		<td><%=JMath.round(yfk,2) %></td>
		<td><%=StringUtils.nullToStr(map.get("yfrq")) %></td>
	</tr>
	
	<%
	}
	%>	
</table>
<BR>
<center>
<input type="button" name="button" value=" 关 闭 " class="css_button2" onclick="window.close();">
</center>
</body>
</html>
