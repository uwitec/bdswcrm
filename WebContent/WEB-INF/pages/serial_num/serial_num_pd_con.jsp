<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序列号盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function query(){
		if(document.getElementById("store_id").value == ""){
			alert("库房不能为空，请选择！");
			return;
		}
		if(document.getElementById("txtFile").value == ""){
			alert("序列号文件不能为空，请选择！");
			return;
		}
		if(document.getElementById("tab_flag").value == ""){
			alert("分隔符不能为空，请选择！");
			return;
		}		
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="pdSerialNumResult.html" method="post" enctype="multipart/form-data">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>序列号盘点</b></td>			
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="listSerialNumPd.html"> 查看历史盘点记录 </a> </td>
	</tr>
</table>
<table width="100%"  align="center" border="0" class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="20%">库房</td>
		<td class="a2" width="80%">
			<ww:select name="store_id" id="store_id" theme="simple" list="%{storeList}" listValue="name" listKey="id" emptyOption="true"></ww:select>	
		</td>
	</tr>
	<tr>
		<td class="a1">序列号文件</td>
		<td class="a2" colspan="3">
			<input type="file" id="txtFile" name="txtFile"> 注：文件格式为txt，大小不能超过10M，序列号用逗号或者是回车分隔，不能包括空行。
		</td>
	</tr>
	<tr>
		<td class="a1">分隔符</td>
		<td class="a2" colspan="3">
			<ww:select name="tab_flag" id="tab_flag" theme="simple" list="#{\"逗号\":'逗号',\"回车\":\"回车\"}" emptyOption="true"></ww:select>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="开始盘点" class="css_button2" onclick="query();">
			<input type="reset" name="button2" value="重置" class="css_button2">
		</td>
	</tr>		
</table>
</form>
</body>
</html>