<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] lxrlx=(String[])VS.findValue("lxrlx");
Map maplinkman=(Map)VS.findValue("mapLinkman");

%>

<html>
<head>
<title>修改联系人</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
	
		
			if(document.getElementById("name").value == "")
			{
				alert("联系人姓名不能为空，请填写！");
				return;
			}
			if(document.getElementById("yddh").value == ""){
				alert("联系人手机不能为空，请填写！");
				return;
			}
		
		
		
		document.accountForm.submit();
	}

	
	 
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="accountForm" action="updatelinkman.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
	<input type="hidden" name="linkman.id" value="<%=maplinkman.get("id") %>"/>
		<td colspan="2">联系人资料</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">联系人姓名</td>
		<td class="a2" width="35%"><input type="text" name="linkman.name" id="name" value="<%=maplinkman.get("name") %>"></td>
		
	</tr>
	<tr>
		<td class="a1" width="15%">联系人电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.yddh" id="yddh" value="<%=maplinkman.get("yddh") %>" ></td>
				
	</tr>	
	<tr>
	    <td class="a1" width="15%">联系人类型</td>
	    <td class="a2" width="35%">
			<select name="linkman.lxid"  >
			<% 
			for(int i=0;i<lxrlx.length;i++)
			{
			%>
				<option value="<%=lxrlx[i] %>" <%if(lxrlx[i].equals(maplinkman.get("lxid"))) out.print("selected"); %>><%=lxrlx[i] %></option>
			
			<%} %>
			</select>
		</td>		
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	 
	
	<tr height="35">
		<td class="a1" >
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
