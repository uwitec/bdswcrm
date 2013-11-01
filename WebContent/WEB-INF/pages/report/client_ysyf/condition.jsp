<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String[] wldwlx = (String[])VS.findValue("wldwlx");
 
List clientsList=(List)VS.findValue("clientList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单位应收应付查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">
function doSubmit(){	
	document.reportForm.submit();
}
function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getDwYsyfResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">单位应收应付汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">单位名称</td>
		<td class="a4">
		<input type="text" name="cl" id="client_name" value="" onblur="setClientValue();"  style="width:232px" size="30"  maxlength="50">
		<input type="hidden" name="client_name" id="client_id" value="">
		<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">客户经理</td>
		<td class="a4">
		    <input  id="brand" type="text" length="20"  onblur="setValue()"   style="width:232px"/> 
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="khjl" id="fzr"  /> 
		</td>				
	</tr>
	<tr>
		<td class="a1">客户类型</td>
		<td class="a4">
		<select name="client_type" id="client_type" style="width:232px">
				<option value=""></option>
				<%
				if(wldwlx != null && wldwlx.length > 0){ 
					for(int i=0;i<wldwlx.length;i++){
				%>
				<option value="<%=wldwlx[i] %>"><%=wldwlx[i] %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">应付为0不显示</td>
		<td class="a4">	
			<input type="radio" name="flag" id="flag" value="是"/>是&nbsp;&nbsp;
			<input type="radio" name="flag" id="flag" value="否" checked/>否			
		</td>											
	</tr>
	<tr>
		<td class="a1">应收为0不显示</td>
		<td class="a4" colspan="3">
			<input type="radio" name="flag2" id="flag2" value="是" />是&nbsp;&nbsp;
			<input type="radio" name="flag2" id="flag2" value="否" checked/>否				
		</td>	
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="doSubmit();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>