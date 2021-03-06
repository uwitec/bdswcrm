<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账号资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,50,"账户名称")){	 return; }
		
		if(document.getElementById("type").value == "银行"){
			if(document.getElementById("bank").value == ""){
				alert("开户行不能为空，请填写！");
				return;
			}
			if(document.getElementById("bank_count").value == ""){
				alert("账号不能为空，请填写！");
				return;
			}
		}
		
		
		document.accountForm.submit();
	}

	
	function selType(vl){
		if(vl == "银行"){
			document.getElementById("bank").readOnly = false;
			document.getElementById("bank_count").readOnly = false;
		}else{
			document.getElementById("bank").readOnly = false;
			document.getElementById("bank_count").readOnly = false;
		}
	}
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="accountForm" action="saveAccount.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">账户资料</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">账户名称</td>
		<td class="a2" width="35%"><input type="text" name="accounts.name" id="name" style="width:170px" value=""/></td>
		<td class="a1" width="15%">账户类型</td>
		<td class="a2" width="35%">
			<select name="accounts.type" id="type" onchange="selType(this.value);" style="width:170px">
				<option value="现金">现金</option>
				<option value="银行">银行</option>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">开户行</td>
		<td class="a2" width="35%"><input type="text" name="accounts.bank" id="bank" style="width:170px" value="" maxlength="50"  readonly="readonly"/></td>
		<td class="a1" width="15%">账号</td>
		<td class="a2" width="35%"><input type="text" name="accounts.bank_count" id="bank_count" style="width:170px" value=""  readonly="readonly" maxlength="50"/></td>		
	</tr>	
</table>
<br/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备 注</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="accounts.remark" id="remark" style="width:80%"></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();"/>
		</td>
	</tr>
</table>

</form>
</body>
</html>
