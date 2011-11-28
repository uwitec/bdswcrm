<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List accountList = (List)VS.findValue("accountList");
List clientsList=(List)VS.findValue("clientList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购付款统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
function submits()
{
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
<form name="reportForm" action="getCgfkHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">采购付款统计</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="client_name" id="client_name" value="" onblur="setClientValue();" style="width:232px"  size="30"  maxlength="50">
		<input type="hidden" name="cl " id="client_id" value="">
		<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">经手人</td>
		<td class="a4">
		    <input  id="brand" type="text"   length="20"  onblur="setValue()"  style="width:232px" />
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="jsr" id="fzr"  /> 
		</td>		
	</tr>
	<tr>
		<td class="a1">付款账户</td>
		<td class="a4">
			<select name="account_id" style="width:232px">
				<option value=""></option>
				<%
				if(accountList != null &&  accountList.size()>0){
					for(int i=0;i<accountList.size();i++){
						Map map = (Map)accountList.get(i);
						String name = StringUtils.nullToStr(map.get("name"));
						String id = StringUtils.nullToStr(map.get("id"));
				%>
				<option value="<%=id %>"><%=name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">包括预付</td>
		<td class="a4">
			<input type="radio" name="isYfk" id="isYfk" value="1" checked> 是
			<input type="radio" name="isYfk" id="isYfk" value="2"> 否
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" onclick="submits()" name="button1" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
