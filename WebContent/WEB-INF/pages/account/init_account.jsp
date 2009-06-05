<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List accountList = (List)VS.findValue("accountList");
String iscs_flag = (String)VS.findValue("iscs_flag");
%>

<html>
<head>
<title>账户初始</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "initAccountAdd.html?id="+id;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
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
<body>
<form name="myform" action="initAccount.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>账户资料</b></td>		
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>账户名称</td>
		<td>账户类型</td>
		<td>初始金额</td>
	</tr>
	</thead>
	<%
	Iterator its = accountList.iterator();
	
	while(its.hasNext()){
		Map map = (Map)its.next();
		double qcje = map.get("qcje")==null?0:((Double)map.get("qcje")).doubleValue();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" <%if(qcje == 0){ %> title="双击初始账号金额" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');" <%}else{ %>title="账号初始化完成"<%} %>>
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("type")) %></td>
		<td align="right"><%=JMath.round(qcje,2) %>&nbsp;&nbsp;</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
注：在系统正式启用前请首先初始各账号金额，没有初始化的账号金额的默认为0。双击每行数据，初始化相应账号金额，系统初始工作结束后不能再次初始化账号金额。
</body>
</html>
