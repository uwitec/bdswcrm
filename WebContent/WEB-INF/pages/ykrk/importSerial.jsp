<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<% 
String openerId = StringUtils.nullToStr(request.getParameter("openerId"));
%>

<html>
<head>
<title>输入序列号</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	 
	function importSub(){
		var vl = "";
		
		//获取输入框组
		var obj = document.getElementById("serial_num").value;		 
			var qz_num = window.opener.document.getElementById("nqz_serial_num_<%=openerId%>");
			if(qz_num != null){
				qz_num.value = obj;
			 }
			window.close();
		}

</script>
</head>
<body >
<form name="myform" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%">序号</td>
		<td>序列号</td>
	</tr>
	</thead>
 
 <tr class="a1">
		<td> </td>
		<td class="a4"><input type="text" id="serial_num" name="serial_num"   style="width:100%"  ></td>
	</tr>
	
 
</table>
<BR>
 
<center>
<input type="button" name="button1" value=" 确定 " class="css_button" onclick="importSub();">
<input type="button" name="button2" value=" 关闭 " class="css_button" onclick="window.close();">
</center>
</form>
</body>
</html>
