<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("dzdList");

String client_id = StringUtils.nullToStr(VS.findValue("client_id"));
String s_date = StringUtils.nullToStr(VS.findValue("s_date"));
String e_date = StringUtils.nullToStr(VS.findValue("e_date"));

%>

<html>
<head>
<title>对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.client_id.value = "";
		document.myform.client_name.value = "";
		document.myform.s_date.value = "";
		document.myform.e_date.value = "";
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}	
	
	function subForm(){
		if(document.myform.client_name.value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		document.myform.submit();
	}

</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listDzd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">查询条件</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="30%">客户名称</td>
		<td class="a2" width="70%">
		<input type="text" name="client_name" id="client_name" value="<%=StaticParamDo.getClientNameById(client_id) %>" size="30" maxlength="50" readonly>
		<input type="hidden" name="client_id" id="client_id" value="<%=client_id %>">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		</td>	
	</tr>
	<tr>
		<td class="a1" width="30%">对账单时间</td>
		<td class="a2">
		<input type="text" name="s_date" id="s_date" value="<%=s_date %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('s_date')); return false;">
		&nbsp;&nbsp;
		<input type="text" name="e_date" id="e_date" value="<%=e_date %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('e_date')); return false;">
		</td>
	</tr>
	<tr>
		<td class="a1" colspan="2">
			<input type="button" name="buttonCx" value=" 查询 " class="css_button2" onclick="subForm();";>&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">		
		</td>
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>交易日期</td>
		<td>客户名称</td>
		<td>类型</td>
		<td>产品型号</td>
		<td>单价</td>
		<td>数量</td>
		<td>贷方金额</td>
		<td>借方金额</td>
		<td>备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
		
		double dfje = map.get("dfje")==null?0:((Double)map.get("dfje")).doubleValue();
		double jfje = map.get("jfje")==null?0:((Double)map.get("jfje")).doubleValue();
	%>
	<tr>
		<td class="a3"><%=StringUtils.nullToStr(map.get("jyrq")) %></td>
		<td class="a3"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_id"))) %></td>
		<td class="a3"><%=StringUtils.nullToStr(map.get("kind")) %></td>
		<td class="a3"><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
		<td class="a3"><%=price==0?"":JMath.round(price,2) %></td>
		<td class="a3"><%=StringUtils.nullToStr(map.get("nums")).equals("0")?"":StringUtils.nullToStr(map.get("nums")) %></td>
		<td class="a3"><%=dfje==0?"":JMath.round(dfje,2) %></td>
		<td class="a3"><%=jfje==0?"":JMath.round(jfje,2) %></td>
		<td class="a3"><%=StringUtils.nullToStr(map.get("remark")) %></td>
	</tr>
	
	<%
	}
	%>
</table>

</form>
</body>
</html>
