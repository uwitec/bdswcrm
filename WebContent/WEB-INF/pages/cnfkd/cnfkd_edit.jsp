<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>出纳付款单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
function saveInfo(vl){
	if(!InputValid(document.getElementById("fk_date"),1,"string",1,1,20,"单据日期")){return; }
	if(document.getElementById("fkfs").value == ""){
		alert("付款方式不能为空，请选择");
		return;
	}
	if(document.getElementById("fkzh").value == ""){
		alert("付款账户不能为空，请选择");
		return;
	}
	if(!InputValid(document.getElementById("fzr"),1,"string",1,1,20,"出纳")){return; }

	if(document.getElementById("has_fy是").checked){
		if(document.getElementById("fy_type").value == ""){
			alert("费用类型不能为空，请选择");
			return;
		}
		if(document.getElementById("fy_account").value == ""){
			alert("费用支付账号不能为空，请选择");
			return;
		}
		if(!InputValid(document.getElementById("fy_je"),1,"float",1,1,999999999,"费用金额")){	 return; }			
	}
	
	if(vl == "1"){
		document.getElementById("state").value = "待支付";
		document.cnfkdForm.submit();
	}else{
		document.getElementById("state").value = "已支付";
		if(window.confirm("确认要付款吗？")){
			document.cnfkdForm.submit();
		}else{
			return;
		}
	}
	document.cnfkdForm.btnSub.disabled = true;
	document.cnfkdForm.btnSave.disabled = true;
}

function openFyType(){
	var destination = "selFyType.html";
	var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'费用类别',fea);	
}

function chgHasFy(vl){
	if(vl == "否"){
		document.getElementById("fy_tdobj1").style.display = "none";
		document.getElementById("fy_tdobj2").style.display = "none";
		document.getElementById("fy_tdobj3").style.display = "none";

		document.getElementById("fy_type").value = "";
		document.getElementById("fy_type_show").value = "";
		document.getElementById("fy_je").value = "0.00";
		document.getElementById("fy_account").value = "";
	}else{
		document.getElementById("fy_tdobj1").style.display = "";
		document.getElementById("fy_tdobj2").style.display = "";
		document.getElementById("fy_tdobj3").style.display = "";
	}
}
</script>
</head>
<body onload="initFzrTip();chgHasFy('<ww:property value="%{cnfkd.has_fy}"/>');">
<form name="cnfkdForm" action="updateCnfkd.html" method="post">
<ww:hidden name="cnfkd.state" id="state" value="" theme="simple"></ww:hidden>
<ww:hidden name="cnfkd.cgfk_id" id="cgfk_id" value="%{cnfkd.cgfk_id}" theme="simple"></ww:hidden>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出纳付款单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="cnfkd.id" id="id" value="%{cnfkd.id}" theme="simple" readonly="true" cssStyle="width:85%"/>
		</td>
		<td class="a1" width="15%">日期</td>
		<td class="a2" width="35%">
			<input type="text" name="cnfkd.fk_date" id="fk_date" value="<ww:property value="%{cnfkd.fk_date}"/>" class="Wdate" onFocus="WdatePicker()" style="width:85%"/>&nbsp;	
			<span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1">往来单位</td>
		<td class="a2">
			<ww:textfield name="wldw" id="wldw" value="%{getClientName(cnfkd.client_name)}" theme="simple" cssStyle="width:85%" readonly="true"/>
			<ww:hidden name="cnfkd.client_name" id="client_name" value="%{cnfkd.client_name}" theme="simple"/>
		</td>
		<td class="a1">联系人</td>
		<td class="a2">
			<ww:textfield name="cnfkd.lxr" id="lxr" value="%{cnfkd.lxr}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>							
	</tr>
	<tr>
		<td class="a1">电话</td>
		<td class="a2">
			<ww:textfield name="cnfkd.tel" id="tel" value="%{cnfkd.tel}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>
		<td class="a1">传真</td>
		<td class="a2">
			<ww:textfield name="cnfkd.fax" id="fax" value="%{cnfkd.fax}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>							
	</tr>	
	<tr>
		<td class="a1">单位全称</td>
		<td class="a2">
			<ww:textfield name="cnfkd.client_all_name" id="client_all_name" value="%{cnfkd.client_all_name}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>						
		<td class="a1">开户行及账号</td>
		<td class="a2">
			<ww:textfield name="cnfkd.bank_no" id="bank_no" value="%{cnfkd.bank_no}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>					
	</tr>
	<tr>
		<td class="a1">付款类型</td>
		<td class="a2" colspan="3">
			<ww:textfield name="cnfkd.fklx" id="fklx" value="%{cnfkd.fklx}" theme="simple" readonly="true" size="35"/>
		</td>
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付款信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%">
			<ww:select name="cnfkd.fkfs" id="fkfs" list="%{arryFkfs}" emptyOption="true" theme="simple"  cssStyle="width:81%"></ww:select><span style="color:red">*</span>
		</td>	
		<td class="a1" width="15%">付款金额</td>
		<td class="a2" width="35%">
			<ww:textfield name="cnfkd.fkje" id="fkje" value="%{getText('global.format.double',{cnfkd.fkje})}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>								
	</tr>	
	<tr>
		<td class="a1">付款账户</td>
		<td class="a2">
			<ww:select name="cnfkd.fkzh" id="fkzh" list="%{accountList}" listKey="id" listValue="name" theme="simple"  emptyOption="true" cssStyle="width:81%"></ww:select><span style="color:red">*</span>
		</td>
		<td class="a1">出纳</td>
		<td class="a2">
			<ww:textfield name="brand" id="brand" onblur="setValue()" value="%{getUserRealName(cnfkd.jsr)}" theme="simple" cssStyle="width:85%"></ww:textfield>
            <div id="brandTip" style="height:12px;position:absolute;left:548px; top:85px;  width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <ww:hidden name="cnfkd.jsr" id="fzr" value="%{cnfkd.jsr}" theme="simple"></ww:hidden><font color="red">*</font>	
		</td>									
	</tr>		
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
			<ww:textfield name="cnfkd.remark"  id="remark" value="%{cnfkd.remark}" theme="simple" maxlength="250" cssStyle="width:95%"/>
		</td>	
	</tr>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">相关费用</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">是否添加费用</td>
		<td class="a2" width="35%">
			<ww:radio list="#{\"是\":'是',\"否\":'否'}" name="cnfkd.has_fy" id="has_fy" theme="simple" value="%{cnfkd.has_fy}" onclick="chgHasFy(this.value);"></ww:radio><span style="color:red">*</span>
		</td>	
		<td class="a1" width="15%" id="fy_tdobj1">费用类型</td>
		<td class="a2" width="35%" id="fy_tdobj2">
			<ww:textfield name="fy_type_show" id="fy_type_show" theme="simple" value="%{getFyTypeName(cnfkd.fy_type)}" onclick="openFyType();" readonly="true" cssStyle="width:85%"/><span style="color:red">*</span>
			<ww:hidden name="cnfkd.fy_type" id="fy_type" theme="simple" value="%{cnfkd.fy_type}"/>
			<img src="images/select.gif" align="absmiddle" title="选择费用类型" border="0" onclick="openFyType();" style="cursor:hand">			
		</td>
	</tr>	
	<tr id="fy_tdobj3">
		<td class="a1">费用支付账号</td>
		<td class="a2">
			<ww:select name="cnfkd.fy_account" id="fy_account" list="%{accountList}" listKey="id" listValue="name" theme="simple"  emptyOption="true" cssStyle="width:81%"></ww:select><span style="color:red">*</span>
		</td>
		<td class="a1">费用金额</td>
		<td class="a2">
			<ww:textfield name="cnfkd.fy_je" id="fy_je" onblur="setValue()" value="%{getText('global.format.double',{cnfkd.fy_je})}" theme="simple" cssStyle="width:85%"></ww:textfield>
            <font color="red">*</font>	
		</td>									
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value=" 草 稿 " class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;
			<input type="button" name="btnSub" value="确认付款" class="css_button3" onclick="saveInfo('2');">&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>