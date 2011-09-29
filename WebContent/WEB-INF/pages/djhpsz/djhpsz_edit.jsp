<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Djhpsz djhpsz = (Djhpsz)VS.findValue("djhpsz");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>兑奖货品设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	function saveInfo()
	{
		document.djhpszForm.submit();
	}

	function checkDwjf()
	{		
		for(var i=0;i<=allCount;i++)
		{			
						
			var dwjf = document.getElementById("dwjf_" + i);
			if(dwjf != null)
			{
				if(!InputValid(dwjf,0,"string",0,1,99999999,"单位积分"))
				{
					dwjf.focus();
					return;
				}
			}
		}
	}					
</script>
</head>
<body>
<form name="djhpszForm" action="updateDjhpsz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">兑奖货品设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">产品编号</td>
		<td class="a2" width="35%">
		<input type="text" id="product_id" name="djhpsz.product_id"  size="50" value="<%=StringUtils.nullToStr(djhpsz.getProduct_id()) %>" readonly>
		</td>
	</tr>
	<tr>	
		<td class="a1">产品名称</td>
		<td class="a2">
		<input type="text" id="product_name" name="djhpsz.product_name"  size="50" value="<%=StringUtils.nullToStr(djhpsz.getProduct_name()) %>" readonly>
		</td>
	</tr>
	<tr>	
		<td class="a1" width="15%">产品规格</td>
		<td class="a2">
		<input type="text" id="product_xh" name="djhpsz.product_xh"  size="50" value="<%=StringUtils.nullToStr(djhpsz.getProduct_xh()) %>" readonly>
		</td>	
	</tr>
	<tr>	
		<td class="a1" width="15%">对应积分</td>
		<td class="a2"><input type="text" id="dwjf" name="djhpsz.dwjf" size="50" value="<%=StringUtils.nullToStr(djhpsz.getDwjf()) %>" ></td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>