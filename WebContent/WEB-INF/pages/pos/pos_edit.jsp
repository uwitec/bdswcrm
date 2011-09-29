<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>刷卡POS机设定</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">

	//保存刷卡POS机设定
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,50,"名称")){	 return; }
		
		var vl = document.getElementById("type").value;
		if(vl == "1"){
			//固定金额
			if(!InputValid(document.getElementById("mbfy"),1,"float",1,0,5000,"每笔费用")){	 return; }	
		}else if(vl == "2"){
			//固定扣款率
			if(!InputValid(document.getElementById("fl"),1,"float",1,0,1,"费率")){	 return; }	
		}else if(vl == "3"){
			//固定扣款率(封顶)
			if(!InputValid(document.getElementById("fl"),1,"float",1,0,1,"费率")){	 return; }	
			if(!InputValid(document.getElementById("fdfy"),1,"float",1,0,5000,"封顶费用")){	 return; }	
		}
		
		if(document.getElementById("fkzh").value == ""){
			alert("相关账户不能为空，请选择");
			return;
		}	
		document.posForm.submit();
	}	
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function selType(vl){
		if(vl == "1"){
			document.getElementById("pos_mbfy").style.display = "";
			document.getElementById("mbfy").value = "0.0";		
			document.getElementById("pos_fl").style.display = "none";
			document.getElementById("fl").value = "0.0";
			document.getElementById("pos_fdfy").style.display = "none";
			document.getElementById("fdfy").value = "0.0";			
		}else if(vl == "2"){
			document.getElementById("pos_mbfy").style.display = "none";
			document.getElementById("mbfy").value = "0.0";
			document.getElementById("pos_fl").style.display = "";
			document.getElementById("fl").value = "0.0";
			document.getElementById("pos_fdfy").style.display = "none";
			document.getElementById("fdfy").value = "0.0";	
		}else if(vl == "3"){
			document.getElementById("pos_mbfy").style.display = "none";
			document.getElementById("mbfy").value = "0.0";		
			document.getElementById("pos_fl").style.display = "";
			document.getElementById("fl").value = "0.0";
			document.getElementById("pos_fdfy").style.display = "";
			document.getElementById("fdfy").value = "0.0";		
		}
	}
	
	function init(){
		var vl = '<ww:property value="%{posType.type}" />';		
		if(vl == "1"){
			document.getElementById("pos_mbfy").style.display = "";	
			document.getElementById("pos_fl").style.display = "none";
			document.getElementById("pos_fdfy").style.display = "none";
		}else if(vl == "2"){
			document.getElementById("pos_mbfy").style.display = "none";
			document.getElementById("pos_fl").style.display = "";
			document.getElementById("pos_fdfy").style.display = "none";
		}else if(vl == "3"){
			document.getElementById("pos_mbfy").style.display = "none";
			document.getElementById("pos_fl").style.display = "";
			document.getElementById("pos_fdfy").style.display = "";
		}
	}
</script>
</head>
<body onload="init();">
<form name="posForm" action="updatePos.html" method="post">
<ww:hidden name="posType.id" id="id" value="%{posType.id}"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">刷卡POS机设定</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">名称</td>
		<td class="a2" width="75%">
			<ww:textfield name="posType.name" id="name" value="%{posType.name}" theme="simple"/><span style="color:red">*</span>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="25%">收费类型</td>
		<td class="a2" width="75%">
			<ww:select name="posType.type" id="type" theme="simple" list="#{'1':'固定金额','2':'固定扣款率','3':'固定扣款率(封顶)'}" onchange="selType(this.value);"  emptyOption="no"></ww:select>	
		</td>					
	</tr>
	<tr id="pos_mbfy">
		<td class="a1" width="25%">每笔费用</td>
		<td class="a2" width="75%">
			<ww:textfield name="posType.mbfy" id="mbfy" value="%{posType.mbfy}" theme="simple"/>元<span style="color:red">*</span>
		</td>					
	</tr>
	<tr id="pos_fl" style="display:none">
		<td class="a1" width="25%">费率</td>
		<td class="a2" width="75%">
			<ww:textfield name="posType.fl" id="fl" value="%{posType.fl}" theme="simple"/><span style="color:red">*</span>小数表示，例如：0.01即为1%
		</td>					
	</tr>
	<tr id="pos_fdfy" style="display:none">
		<td class="a1" width="25%">封顶费用</td>
		<td class="a2" width="75%">
			<ww:textfield name="posType.fdfy" id="fdfy" value="%{posType.fdfy}" theme="simple"/>元<span style="color:red">*</span>
		</td>					
	</tr>		
	<tr>
		<td class="a1" width="15%">相关账户</td>
		<td class="a2" width="35%">
			<ww:textfield id="zhname"  name="zhname" value="%{getAccountName(posType.account_id)}" theme="simple" size="30" readonly="true"/>
			<ww:hidden name="posType.account_id" id="fkzh" value="%{posType.account_id}" theme="simple"/>
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><span style="color:red">*</span>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2">
			<ww:textarea  name="posType.remark" id="remark" value="%{posType.remark}" cssStyle="width:70%" theme="simple" />
		</td>						
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">				
	<tr height="35">
		<td class="a1">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
