<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

StoreHouse storeHouse = (StoreHouse)VS.findValue("storeHouse");

%>

<html>
<head>
<title>仓库管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,25,"仓库名称")){	 return; }
		
		document.storeHouseForm.submit();
	}
</script>
</head>
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">仓库信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.name" id="name" value="<%=StringUtils.nullToStr(storeHouse.getName()) %>" readonly size="30" maxlength="35"></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.address" id="address" value="<%=StringUtils.nullToStr(storeHouse.getAddress()) %>" readonly size="40" maxlength="50"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxr" id="lxr" value="<%=StringUtils.nullToStr(storeHouse.getLxr()) %>" readonly size="25" maxlength="32"></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(storeHouse.getLxdh()) %>" readonly size="30" maxlength="50"></td>
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="6" cols="50" name="storeHouse.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(storeHouse.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
