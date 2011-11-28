<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List productKindList = (List)VS.findValue("productKindList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品序列号销售汇总</title>
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
	function sbForm(){
		document.reportForm.submit();
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getSerialXshzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品序列号销售汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" style="width:232px"  class="Wdate" onFocus="WdatePicker()"  size="35" maxlength="50"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" style="width:232px"  class="Wdate" onFocus="WdatePicker()"  size="35" maxlength="50"></td>
	</tr>
	<tr>
		<td class="a1">商品类别</td>
		<td class="a4">
			<select name="product_kind" style="width:232px">
				<option value=""></option>
				<%
				if(productKindList != null &&  productKindList.size()>0){
					for(int i=0;i<productKindList.size();i++){
						Map map = (Map)productKindList.get(i);
						String id = StringUtils.nullToStr(map.get("id"));
						String name = StringUtils.nullToStr(map.get("name"));
						for(int k=0;k<id.length()-3;k++){
							name = "&nbsp;&nbsp;" + name;
						}
				%>
				<option value="<%=id %>"><%=name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">商品名称</td>
		<td class="a4">
			<input type="text" name="product_name" id="product_name" style="width:232px"  size="35" maxlength="50" value="">
		</td>					
	</tr>	
	<tr>	
		<td class="a1">销售人员</td>
		<td class="a4">
		 <input  id="brand" type="text"    size="35" maxlength="50"  style="width:232px" onblur="setValue()"  /> 
            <div   id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="xsry_name" id="fzr"  /> 
		</td>
		<td class="a1">客户名称</td>
		<td class="a4">
		    <input type="text" name="clientId" id="client_name" value=""  onblur="setClientValue();"  style="width:232px" size="35" maxlength="50">
		    <input type="hidden" name="clientName" id="client_id" value="">
		    <div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
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