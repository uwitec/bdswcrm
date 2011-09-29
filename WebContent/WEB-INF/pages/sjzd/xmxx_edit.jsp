<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据字典维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("xm_name"),1,"string",1,1,100,"子项名称")){	 return; }
		if(!InputValid(document.getElementById("xm_ms"),0,"string",0,1,100,"子项描述")){	 return; }
		if(!InputValid(document.getElementById("xh"),0,"int",0,0,999,"序号")){	 return; }
		
		document.sjzdForm.submit();
	}
</script>
</head>
<body>
<form name="sjzdForm" action="updateZdxx.html" method="post">
<ww:hidden name="sjzdXmxx.zd_id" id="zd_id" value="%{sjzdXmxx.zd_id}"/>
<ww:hidden name="sjzdXmxx.xm_id" id="xm_id" value="%{sjzdXmxx.xm_id}"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">子项信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">子项名称</td>
		<td class="a2" width="75%"><ww:textfield name="sjzdXmxx.xm_name" id="xm_name" value="%{sjzdXmxx.xm_name}" theme="simple" cssStyle="width:75%"/></td>		
	</tr>
		<td class="a1" width="25%">子项描述</td>
		<td class="a2" width="75%"><ww:textfield  name="sjzdXmxx.xm_ms" id="xm_ms" value="%{sjzdXmxx.xm_ms}" theme="simple" cssStyle="width:75%"/></td>	
	<tr>
		<td class="a1" width="25%">排序号</td>
		<td class="a2" width="75%"><ww:textfield  name="sjzdXmxx.xh" id="xh" value="%{sjzdXmxx.xh}"  theme="simple"  cssStyle="width:75%"/></td>
	</tr>	
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button">&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
