<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Jfgz jfgz = (Jfgz)VS.findValue("jfgz");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>积分规则</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/validateform.js"></script>
<script type="text/javascript">
	
	function saveInfo(){
		if (doValidate(myform)){		
			document.myform.submit();
		}
	}	
</script>
</head>
<body >
<form name="myform" action="updateJfgz.html" method="post">
<input type="hidden" name="jfgz.id" id="id" value="<%=StringUtils.nullToStr(jfgz.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">积分规则设置</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="25%">积分方法</td>
		<td class="a2" width="75%"><input type="text" name="jfgz.jfff" id="jfff" value="<%=StringUtils.nullToStr(jfgz.getJfff()) %>" style="width:160px" maxlength="100" notNull='true' vdisp='积分方法'  vtype='string'><font color="red">*</font></td>
	</tr>
	
	<tr>
		<td class="a1">消费金额</td>
		<td class="a2">
			<input type="text" id="xfje" name="jfgz.xfje"  value="<%=JMath.round(jfgz.getXfje()) %>" style="width:160px" maxlength="10" notNull='true' vdisp='消费金额'  vtype='float'><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">对应积分</td>
		<td class="a2">
			<input type="text" id="dyjf" name="jfgz.dyjf"  value="<%=JMath.round(jfgz.getDyjf()) %>" style="width:160px" maxlength="10" notNull='true' vdisp='对应积分'  vtype='float'><font color="red">*</font>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button">&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
