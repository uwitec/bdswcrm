<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单据打印设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(document.getElementById("title_name").value == ""){
			alert("公司名称不能为空，请填写！");
			return;
		}
		if(document.getElementById("foot_name").value == ""){
			alert("页脚内容不能为空，请选择！");
			return;
		}
		
		if(window.confirm("确认要保存设置吗，原有设置将失效！")){
			document.myform.submit();
		}
	}
</script>
</head>
<body>
<form name="myform" action="saveReportSet.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>单据打印设置</b></td>			
	</tr>
</table>
<FONT color="red"><ww:property value="%{msg}"/></FONT>
<table width="100%"  align="center" border="1" class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1">公司名称</td>
		<td class="a2">
			<ww:textfield name="title_name" id="title_name" theme="simple" size="50" value="%{title_name}" maxlength="25"></ww:textfield>
		</td>
	</tr>
	<tr>
		<td class="a1" widht="20%">页脚内容</td>
		<td class="a2" colspan="3">
			<ww:textfield name="foot_name" id="foot_name" theme="simple" size="80" value="%{foot_name}" maxlength="100"></ww:textfield>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="保存" class="css_button2" onclick="saveInfo();">
		</td>
	</tr>		
</table>
</form>
</body>
</html>