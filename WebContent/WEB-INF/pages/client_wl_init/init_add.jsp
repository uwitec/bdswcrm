<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>

<%
List msgs = (List)session.getAttribute("messages");

session.removeAttribute("messages");
String msg = "";

if(msgs != null && msgs.size() > 0){
	for(int i=0;i<msgs.size();i++){
		msg += StringUtils.nullToStr(msgs.get(i));
	}
}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>往来初始</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	<%if(!msg.equals("")){ %>
		alert("<%=msg %>");
		window.close();;
	<%}%>
	
	function saveInfo(){
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(!InputValid(document.getElementById("ysqc"),0,"float",9999999999,"应收期初")){	 return; }	
		if(!InputValid(document.getElementById("yfqc"),0,"float",9999999999,"应付期初")){	 return; }	
		
		if(window.confirm("提交后将不能修改，确认提交吗？")){
			document.clientForm.submit();
		}
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="clientForm" action="saveClientWlInit.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来初始</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
			<input type="text" name="clientWlInit.client_id" id="client_name" value="" readonly size="40" maxlength="50">
			<input type="hidden" name="clientWlInit.client_name" id="client_id" value="">
			<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">应收款</td>
		<td class="a2" width="35%"><input type="text" name="clientWlInit.ysqc" id="ysqc" value="0" maxlength="10"></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">应付款</td>
		<td class="a2" width="35%"><input type="text" name="clientWlInit.yfqc" id="yfqc" value="0" maxlength="20"></td>		
	</tr>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="2" cols="50" name="clientWlInit.remark" id="remark" style="width:80%" maxlength="200"></textarea>
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
说明：应收款、应付款输入当前应收、应付款。
</body>
</html>
