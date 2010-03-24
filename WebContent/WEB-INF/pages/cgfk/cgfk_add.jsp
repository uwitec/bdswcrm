<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List providers = (List)VS.findValue("providerList");
Cgfk cgfk = (Cgfk)VS.findValue("cgfk");

List userList = (List)VS.findValue("userList");
List clientsList=(List)VS.findValue("clientsList");
List cgfkDescs = (List)VS.findValue("cgfkDescs");

int allCount = 3;
if(cgfkDescs != null && cgfkDescs.size()>0){
	allCount = cgfkDescs.size();
}

Clients clients = (Clients)VS.findValue("clients");
%>

<html>
<head>
<title>付款申请单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=allCount %>;

	function saveInfo(vl){
		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("fk_date"),1,"string",1,1,20,"付款日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		hj();
		
		if(isNaN(document.getElementById("fkje").value) || parseFloat(document.getElementById("fkje").value) == 0){
			alert("本次付款金额必须为数字并且不等于0，请检查！");
			return;
		}

		if(vl == "1"){
			document.cgfkForm.submit();
		}else{
			if(window.confirm("确认提交吗，提交后将不可修改！")){
				document.cgfkForm.submit();
			}else{
				return;
			}
		}
		document.cgfkForm.btnSave.disabled = true;
		document.cgfkForm.btnSub.disabled = true;
	}
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	

	function openName(){
		if(document.getElementById("client_id").value == ""){
			alert("请先选择往来单位，再选择单位全称!");
			return;
		}
		var destination = "selClientAllName.html?id=" + document.getElementById("client_id").value;
		var fea ='width=500,height=200,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择单位信息',fea);		
	}	

	function openLxr(){
		if(document.getElementById("client_id").value == ""){
			alert("请先选择往来单位，再选择客户联系人!");
			return;
		}
		var destination = "selClientLxr.html?id=" + document.getElementById("client_id").value;
		var fea ='width=500,height=200,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择取系人',fea);		
	}	
	
	function hj(){
		if(document.getElementById("is_yfk").checked){
			return;
		}
			
		var hjz = 0;

		for(var i=0;i<allCount;i++){
				
			var fk = document.getElementById("bcfk_" + i);
			var yfk = document.getElementById("yfje_" + i);
			
			if(fk != null){
				if(!InputValid(fk,0,"float",-99999999,1,99999999,"本次付款")){
					fk.focus();
					return;
				}
				if(parseFloat(yfk.value) > 0){
					if(parseFloat(fk.value) > parseFloat(yfk.value)){
						alert("本次付款金额应小于或等于应付金额，请检查！");
						fk.focus();
						return;
					}
				}else{
					if(parseFloat(fk.value) < parseFloat(yfk.value)){
						alert("应付金额为负值时，本次付款金额应大于或等于应付金额，请检查！");
						fk.focus();
						return;
					}
				}
			}				
			
			hjz = parseFloat(hjz) + parseFloat(fk.value);
		}

			
		
		var fkje = document.getElementById("fkje");
		var hj_bcfk = document.getElementById("hj_bcfk");
		hj_bcfk.value = hjz.toFixed(2);
		fkje.value = hjz.toFixed(2);
		
	}
	
	function chkYfk(){
		if(document.getElementById("is_yfk").checked){
			if(document.getElementById("client_id").value != ""){
				alert("先确认预收款，再选择客户！");
				document.getElementById("is_yfk").checked = false;
				return;
			}
			
			document.getElementById("fkje").readOnly = false;
			document.getElementById("hj_bcfk").readOnly = true;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcfk_" + i).readOnly = true;
			}
			
		}else{
			document.getElementById("fkje").readOnly = true;
			document.getElementById("hj_bfk").readOnly = false;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcfk_" + i).readOnly = false;
			}
		}
	}	
	
	function queryYszd(){
		setClientValue();
		
		if(document.getElementById("client_id").value == ""){
			return;
		}
		
		if(document.getElementById("is_yfk").checked){
			return;
		}
		
		document.cgfkForm.action = "addCgfk.html";
		document.cgfkForm.submit();
	}					
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="cgfkForm" action="saveCgfk.html" method="post">
<input type="hidden" name="cgfk.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付款申请单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="cgfk.id" id="id" value="<%=StringUtils.nullToStr(cgfk.getId()) %>" readonly><font color="red">*</font>
		</td>
		<td class="a1" width="15%">是否预付款</td>
		<td class="a2"><input type="checkbox" name="cgfk.is_yfk" id="is_yfk" value="是" onclick="chkYfk();">预付款</td>
	</tr>
	<tr>
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%"><input type="text" name="cgfk.fk_date" id="fk_date" value="<%=StringUtils.nullToStr(cgfk.getFk_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(cgfk.getFk_date()) %>"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
			<input type="text" name="gysmc" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfk.getGysbh())) %>" size="35" onblur="queryYszd();">
			<input type="hidden" name="cgfk.gysbh" id="client_id" value="<%=StringUtils.nullToStr(cgfk.getGysbh()) %>">
			<div id="clientsTip" style="height:12px;position:absolute;width:270px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<font color="red">*</font>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">单位全称</td>
		<td class="a2" width="35%">
			<input type="text" name="cgfk.client_all_name"  id="client_all_name" value="<%=StringUtils.nullToStr(cgfk.getClient_all_name()) %>" size="35" maxlength="100">
			<img src="images/select.gif" align="absmiddle" title="点击选择" border="0" onclick="openName();" style="cursor:hand">		
		</td>
		<td class="a1" width="15%">开户行账号</td>
		<td class="a2">
			<input type="text" name="cgfk.bank_no" id="bank_no" value="<%=StringUtils.nullToStr(cgfk.getBank_no()) %>" size="35" maxlength="50">
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">客户联系人</td>
		<td class="a2" width="35%">
			<input type="text" name="cgfk.kh_lxr" id="kh_lxr" value="<%=StringUtils.nullToStr(cgfk.getKh_lxr()) %>" size="35" maxlength="20">
			<img src="images/select.gif" align="absmiddle" title="点击选择" border="0" onclick="openLxr();" style="cursor:hand">	
		</td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="cgfk.tel" id="tel" value="<%=StringUtils.nullToStr(cgfk.getTel()) %>" size="35" maxlength="20"></td>
	</tr>		
	<tr>
	<%
	String fax = "";
	if(StringUtils.nullToStr(cgfk.getFax()).equals("") && clients != null){
		fax = StringUtils.nullToStr(clients.getCz());
	}else{
		fax = StringUtils.nullToStr(cgfk.getFax());
	}
	%>
		<td class="a1" width="15%">传真</td>
		<td class="a2"><input type="text" name="cgfk.fax" id="fax" value="<%=fax %>" size="35" maxlength="20"></td>	
		<td class="a1" width="15%">申请人</td>
		<td class="a2" width="35%">
		    <input id="brand" type="text" size="35" onblur="setValue()" />
            <div   id="brandTip"  style="height:12px;position:absolute;left:132px; top:114px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="cgfk.jsr" id="fzr"  /> <font color="red">*</font>	
		</td>	
	</tr>
	<tr>	
		<td class="a1" widht="20%">付款账户</td>
		<td class="a2" colspan="3"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgfk.getFkzh())) %>" size="35" readonly>
			<input type="hidden" id="fkzh"  name="cgfk.fkzh" value="<%=StringUtils.nullToStr(cgfk.getFkzh()) %>">
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>
	<tr>
		<td class="a1">备  注</td>
		<td class="a2" colspan="3"><input type="text" name="cgfk.remark" id="remark" style="width:80%" value="<%=StringUtils.nullToStr(cgfk.getRemark()) %>" maxlength="500"></td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">付款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="20%">进货单编号</td>
		<td width="15%">发生日期</td>
		<td width="15%">发生金额</td>
		<td width="15%">应付金额</td>
		<td width="15%">本次付款</td>
		<td width="20%">备注</td>
	</tr>
	</thead>
<%
double hj_fsje = 0;
double hj_yfje = 0;

if(cgfkDescs != null && cgfkDescs.size()>0){
	for(int i=0;i<cgfkDescs.size();i++){
		Map map = (Map)cgfkDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		hj_fsje += fsje;
		double yfje = map.get("yfje")==null?0:((Double)map.get("yfje")).doubleValue();
		hj_yfje += yfje;
		double bcfk = map.get("bcfk")==null?0:((Double)map.get("bcfk")).doubleValue();
%>
	<tr>
		<td class="a2"><input type="text" id="jhd_id_<%=i %>" name="cgfkDescs[<%=i %>].jhd_id" value="<%=StringUtils.nullToStr(map.get("jhd_id")) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="cgfkDescs[<%=i %>].fsrq" value="<%=StringUtils.nullToStr(map.get("fsrq")) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="cgfkDescs[<%=i %>].fsje" value="<%=JMath.round(fsje) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="yfje_<%=i %>" name="cgfkDescs[<%=i %>].yfje"  value="<%=JMath.round(yfje) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcfk_<%=i %>" name="cgfkDescs[<%=i %>].bcfk" value="<%=JMath.round(bcfk) %>" style="width:100%"  onblur="hj();"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgfkDescs[<%=i %>].remark" value="<%=StringUtils.nullToStr(map.get("remark")) %>" style="width:100%"></td>	
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" id="jhd_id_<%=i %>" name="cgfkDescs[<%=i %>].jhd_id" value="" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="cgfkDescs[<%=i %>].fsrq" value="" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="cgfkDescs[<%=i %>].fsje" value="0.00" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="yfje_<%=i %>" name="cgfkDescs[<%=i %>].yfje"  value="0.00" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcfk_<%=i %>" name="cgfkDescs[<%=i %>].bcfk" value="0.00" style="width:100%" onblur="hj();"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgfkDescs[<%=i %>].remark" value="" style="width:100%"></td>	
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"></td>
		<td class="a2"><input type="text" size="10" id="hj_fsje" name="hj_fsje" value="<%=JMath.round(hj_fsje) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_yfje" name="hj_yfje"  value="<%=JMath.round(hj_yfje) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_bcfk" name="hj_bcfk" value="0.00" style="width:100%"></td>
		<td class="a2"></td>	
	</tr>
	<tr>
		<td class="a1">本次付款总金额</td>
		<td class="a4" colspan="5"><input type="text" size="10" id="fkje" name="cgfk.fkje" value="0.00" readonly></td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2')">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnClose" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>