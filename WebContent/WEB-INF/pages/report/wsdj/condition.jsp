<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List deptList = (List)VS.findValue("deptList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售未收单据汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009; color:#fff;}
</style>
<script type="text/javascript">
	function sbForm(){
		if(document.reportForm.flag.value == "否"){
			document.reportForm.action = "getWsdjResult.html";
		}else{
			document.reportForm.action = "getWsdjMxResult.html";
		}
		document.reportForm.submit();
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openywyWin(){
		var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">销售未收单据汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" class="Wdate" style="width:232px" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" class="Wdate" style="width:232px" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">部门</td>
		<td class="a4">
			<select name="dept_id" style="width:232px">
				<option value=""></option>
				<%
				if(deptList != null &&  deptList.size()>0){
					for(int i=0;i<deptList.size();i++){
						Dept dept = (Dept)deptList.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "&nbsp;&nbsp;" + dept_name;
						}
				%>
				<option value="<%=dept_id %>"><%=dept_name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">销售人员</td>
		<td class="a4">
		    <input  id="brand" type="text" style="width:232px" onblur="setValue()"  > 
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="xsry_id" id="fzr"  > 
		</td>					
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
			<input type="text" name="client_name" id="client_name" value="" style="width:232px" onblur="setClientValue();"  >
			<input type="hidden" name="client_id" id="client_id" value=""> 
			<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">客户经理</td>
		<td class="a4">
		    <input type="text" name="khjl" id="khjl"  value="" style="width:232px">
		</td>
	</tr>
	<tr>
		<td class="a1">是否显示明细</td>
		<td class="a4">
			<select name="flag" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>
	
		<td class="a1">只显示超期单据</td>
		<td class="a4">
			<select name="cq_flag" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>	
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="sbForm();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>