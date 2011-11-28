<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List store_list = (List)VS.findValue("store_list");
List productKindList = (List)VS.findValue("productKindList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存金额汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">

	function subForm(){
		var objs = document.getElementsByName("tj_type");
		if(objs[0].checked){
			document.reportForm.action = "getKcJeResult.html";
			document.reportForm.submit();
		}else{
			if(document.getElementById("cdate").value == ""){
				alert("库存日期不能为空,请选择!");
				return;
			}
			document.reportForm.action = "getHisKcJeResult.html";
			document.reportForm.submit();
		}
	}
	
	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}	

	function chkHis(vl){
		if(vl == "0"){
			document.getElementById("td_cdate_lable").style.display = "none";
			document.getElementById("td_cdate_value").style.display = "none";
			document.getElementById("tr_flag_lable").style.display = "";
			document.getElementById("cdate").value = "";
		}else{
			document.getElementById("td_cdate_lable").style.display = "";
			document.getElementById("td_cdate_value").style.display = "";
			document.getElementById("tr_flag_lable").style.display = "none";
		}
	}	
	
</script>
</head>
<body>
<form name="reportForm" action="getKcJeResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">库存金额汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1" width="15%">汇总类型</td>
		<td class="a4" colspan="3">
			<input type="radio" name="tj_type" id="tj_type" value="0" checked onclick="chkHis(this.value);">当前库存
			<input type="radio" name="tj_type" id="tj_type" value="1" onclick="chkHis(this.value);">历史库存
		</td>	
	</tr>
	<tr>
		<td class="a1" id="td_cdate_lable"  style="display:none">库存日期</td>
		<td class="a4" colspan="3" id="td_cdate_value" style="display:none">
			<input type="text" name="cdate" id="cdate" value="" style="width:232px" class="Wdate" onFocus="WdatePicker()">
		</td>					
	</tr>
	<tr>
		<td class="a1" width="15%">商品类别</td>
		<td class="a4" width="35%">
			<input type="text" name="kind_name" id="kind_name" value="" style="width:232px" onclick="openWin();" readonly>
			<input type="hidden" name="product_kind" id="product_kind" value="">
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openWin();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">商品名称</td>
		<td class="a4" width="35%">
			<input type="text" name="product_name" style="width:232px" id="product_name" value="">
		</td>					
	</tr>
	<tr id="tr_flag_lable">
		<td class="a1">是否显示0库存商品</td>
		<td class="a4">
			<select name="flag" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>
		<td class="a1">是否显示停售商品</td>
		<td class="a4" colspan="3">
			<select name="state" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>					
	</tr>
	<tr>
		<td class="a1">仓库名称</td>
		<td class="a4" colspan="3">
			<select name="store_id" id="store_id" style="width:232px">
				<option value="">全部</option>
				<%
				if(store_list != null && store_list.size()>0){
					for(int i=0;i<store_list.size();i++){
						StoreHouse store = (StoreHouse)store_list.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(store.getId()) %>"><%=StringUtils.nullToStr(store.getName()) %></option>
				<%
					}
				}
				%>
			</select>
		</td>				
	</tr>
	<tr height="35">
		<td class="a1" colspan="4" height="35">
			<input type="button" name="button1" value="提 交" onclick="subForm();" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
注:历史库存汇总统计值为当天的期初值。
</form>
</body>
</html>

