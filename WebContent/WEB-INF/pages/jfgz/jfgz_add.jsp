<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Jfgz jfgz = (Jfgz)VS.findValue("jfgz");
%>
<html>
<head>
<title>积分规则设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" charset="UTF-8"  src="xhEditor/xheditor.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	
	function saveInfo(){
		if(document.getElementById("jfff").value == ""){
			alert("积分方法不能为空，请填写！");
			return;
		}
	   
	    if(document.getElementById("xfje").value == ""){
			alert("消费金额不能为空，请选择！");
			return;
		}	
		
		if(document.getElementById("dyjf").value == ""){
			alert("对应积分不能为空，请选择！");
			return;
		}
		
		document.myform.submit();
	}	
	
</script>
</head>
<body >
<form name="myform" action="saveJfgz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">积分规则设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">积分方法</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="jfgz.jfff" id="jfff" value="" size="45"><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1" width="15%">消费金额</td>
		<td class="a2" colspan="3">
			<input type="text" id="xfje" name="jfgz.xfje"  value="" size="45"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">对应积分</td>
		<td class="a2" colspan="3">
			<input type="text" id="dyjf" name="jfgz.dyjf"  value="" size="45"><font color="red">*</font>
		</td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
