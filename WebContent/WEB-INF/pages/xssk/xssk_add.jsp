<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Xssk xssk = (Xssk)VS.findValue("xssk");

List userList = (List)VS.findValue("userList");

List xsskDescs = (List)VS.findValue("xsskDescs");

List clientsList=(List)VS.findValue("clientsList");

List posTypeList = (List)VS.findValue("posTypeList");
String[] ysfsArry = (String[])VS.findValue("ysfs");

int allCount = 3;
if(xsskDescs != null && xsskDescs.size()>0){
	allCount = xsskDescs.size();
}

String msg = StringUtils.nullToStr(VS.findValue("msg"));
%>

<html>
<head>
<title>销售收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=allCount %>;
	var msg = "<%=msg %>";

	function saveInfo(vl){
		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("sk_date"),1,"string",1,1,20,"收款日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(document.getElementById("skfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}
		if(!InputValid(document.getElementById("fkzh"),1,"string",1,1,50,"收款账户")){	 return; }
		
		hj();
		
		if(isNaN(document.getElementById("skje").value) || parseFloat(document.getElementById("skje").value) == 0){
			alert("本次收款金额必须为数字并且不等于0，请检查！");
			return;
		}

		if(!document.getElementById("is_ysk").checked){
			if((parseFloat(document.getElementById("skje").value)).toFixed(2) != (parseFloat(document.getElementById("hj_bcsk").value)).toFixed(2)){
				alert("本次收款金额必须与合计收款金额相同!");
				return;
			}
		}

		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("提交后将不能修改，确认提交吗？")){
				document.XsskForm.submit();
			}
		}else{
			document.XsskForm.submit();
		}
	}
	
	function openWin(){
		var destination = "selectJhd.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function hj(){
		if(document.getElementById("is_ysk").checked){
			return;
		}
		
		var hjz = 0;

		for(var i=0;i<allCount;i++){
				
			var sk = document.getElementById("bcsk_" + i);
			var ysk = document.getElementById("ysk_" + i);
			
			if(sk != null){
				if(!InputValid(sk,1,"float",1,-99999999,99999999,"本次收款")){
					sk.focus();
					return;
				}

				if(parseFloat(ysk.value)>0){
					if(parseFloat(sk.value) > parseFloat(ysk.value)){
						alert("本次收款金额应小于或等于应收金额，请检查！");
						sk.focus();
						return;
					}
				}else{
					if(parseFloat(sk.value) < parseFloat(ysk.value)){
						alert("应收金额为负值时，本次收款金额应大于应收金额，请检查！");
						sk.focus();
						return;
					}
				}
			}				
			
			hjz = parseFloat(hjz) + parseFloat(sk.value);
		}

		
		var hj_bcsk = document.getElementById("hj_bcsk");
		hj_bcsk.value = hjz.toFixed(2);
	}


	function autoFix(){
		if(document.getElementById("is_ysk").checked){
			return;
		}

		if(isNaN(document.getElementById("skje").value) || parseFloat(document.getElementById("skje").value) == 0){
			alert("本次收款金额必须为数字并且不等于0，请检查！");
			return;
		}
		
		var skje = parseFloat(document.getElementById("skje").value);  //本次收款金额

		if(skje <= 0){
			alert("本次收款金额小于或等于0时无法自动分配,请手动分配处理!");
			return;
		}
		
		var lastJe = skje;

		for(var i=0;i<allCount;i++){
			document.getElementById("bcsk_" + i).value = "0.00";
		}

		for(var i=0;i<allCount;i++){

			var sk = document.getElementById("bcsk_" + i);   //本行本次收款
			var ysk = document.getElementById("ysk_" + i);   //本行应收款

			if(parseFloat(lastJe) != 0){ //当前剩余金额不等于0
				if((parseFloat(lastJe)) >= (parseFloat(ysk.value))){
					sk.value = parseFloat(ysk.value).toFixed(2);
					lastJe = (parseFloat(lastJe)) - (parseFloat(ysk.value));
				}else{
					sk.value = lastJe.toFixed(2);
					break;
				}
			}

			if(lastJe == 0) break;
		}

		hj();
	}
	
	function chkYsk(){
		if(document.getElementById("is_ysk").checked){
			if(document.getElementById("client_id").value != ""){
				alert("先确认预收款，再选择客户！");
				document.getElementById("is_ysk").checked = false;
				return;
			}
			
			document.getElementById("hj_bcsk").readOnly = true;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcsk_" + i).readOnly = true;
			}

			document.getElementById("btnAutoFix").style.display = "none";
		}else{
			document.getElementById("hj_bcsk").readOnly = false;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcsk_" + i).readOnly = false;
			}

			document.getElementById("btnAutoFix").style.display = "";
		}
	}

	
	function queryYszd(){
		setClientValue();
		
		if(document.getElementById("client_id").value == ""){
			return;
		}
		
		if(document.getElementById("is_ysk").checked){
			return;
		}
		
		document.XsskForm.action = "addXssk.html";
		document.XsskForm.submit();
	}

	function onloadMsg(){
		if(msg == "") return;

		var tempMsg = "销售收款单对应收款款明细中：\n" + msg + "\n与其它未提交销售收款单或未提交预收冲应收存在冲突，无法保存，请检查！";

		alert(tempMsg);
	}

	function dwrGetAccount(){
		id = dwr.util.getValue("pos_id");
		if(id == ""){
			return;
		}

		dwrService.getAccountsById(id,setAccount);		
	}

	function setAccount(account){
		if(account != null && account.id != null){
			dwr.util.setValue("fkzh",account.id);
			dwr.util.setValue("zhname",account.name);
		}
	}	

	function selSkfs(vD){
		if(vD == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
	}	
</script>
</head>
<body onload="initFzrTip();initClientTip();onloadMsg();">
<form name="XsskForm" action="saveXssk.html" method="post">
<input type="hidden"  name="xssk.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售收款信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="xssk.id" id="id" value="<%=StringUtils.nullToStr(xssk.getId()) %>" style="width:190px" readonly>
		</td>
		<td class="a1" width="15%">收款日期</td>
		<td class="a2" width="35%">
			<input type="text" name="xssk.sk_date" id="sk_date" value="<%=StringUtils.nullToStr(xssk.getSk_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(xssk.getSk_date()) %>"  class="Wdate" onFocus="WdatePicker()" style="width:190px"> <font color="red">*</font>	
		</td>			
	</tr>
	<tr>	
		<td class="a1" width="15%">是否预收款</td>
		<td class="a2"><input type="checkbox" name="xssk.is_ysk" id="is_ysk" value="是" onclick="chkYsk();"  <%if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")) out.print("checked"); %>>预收款</td>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="clientName" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xssk.getClient_name())) %>" style="width:190px" onblur="queryYszd();"> <font color="red">*</font>
		<input type="hidden" name="xssk.client_name" id="client_id" value="<%=StringUtils.nullToStr(xssk.getClient_name()) %>">
		<div id="clientsTip" style="height:12px;position:absolute;left:132px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
			<input id="brand" style="width:190px" type="text" maxlength="20" onblur="setValue()" /> <font color="red">*</font>	
        	<div id="brandTip" style="height:12px;position:absolute;left:132px; top:113px;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="xssk.jsr" id="fzr"/> 
		</td>
		<td class="a1">收款方式</td>
		<td class="a2">
			<select name="xssk.skfs" id="skfs" onchange="selSkfs(this.value);">
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(StringUtils.nullToStr(xssk.getSkfs()).equals(ysfsArry[i])) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	
			
			<select name="xssk.pos_id" id="pos_id" style="display:none;" onchange="dwrGetAccount();">
				<option value=""></option>
			<%
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(StringUtils.nullToStr(xssk.getPos_id()).equals(posType.getId())) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>
				
			</select> <font color="red">*</font>
		</td>	
	</tr>
	<tr>					
		<td class="a1">收款账户</td>
		<td class="a2"><input type="text" id="zhname" style="width:190px" name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xssk.getSkzh())) %>" onclick="openAccount();" readonly> <font color="red">*</font>
		<input type="hidden" id="fkzh"  name="xssk.skzh" value="<%=StringUtils.nullToStr(xssk.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1">本次收款金额</td>
		<td class="a2"><input type="text" style="width:100px" name="xssk.skje" id="skje" value="<%=JMath.round(xssk.getSkje()) %>">
		<input type="button" name="btnAutoFix" id="btnAutoFix" value="自动分配" class="css_button2" onclick="autoFix();"> <font color="red">*</font>
		</td>		
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">收款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="20%">销售单编号</td>
		<td width="20%">发生日期</td>
		<td width="20%">发生金额</td>
		<td width="20%">应收金额</td>
		<td width="20%">本次收款</td>
	</tr>
	</thead>
<%
double hj_fsje = 0;
double hj_ysk = 0;
double hj_bcsk = 0;

if(xsskDescs != null && xsskDescs.size()>0){
	for(int i=0;i<xsskDescs.size();i++){
		Map map = (Map)xsskDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		hj_fsje += fsje;
		double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
		hj_ysk += ysk;
		double bcsk = map.get("bcsk")==null?0:((Double)map.get("bcsk")).doubleValue();
		hj_bcsk += bcsk;
%>
	<tr>
		<td class="a2"><input type="text" style="width:100%" id="xsd_id_<%=i %>" name="xsskDescs[<%=i %>].xsd_id" value="<%=StringUtils.nullToStr(map.get("xsd_id")) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="fsrq_<%=i %>" name="xsskDescs[<%=i %>].fsrq" value="<%=StringUtils.nullToStr(map.get("fsrq")) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="fsje_<%=i %>" name="xsskDescs[<%=i %>].fsje" value="<%=JMath.round(fsje) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="ysk_<%=i %>" name="xsskDescs[<%=i %>].ysk"  value="<%=JMath.round(ysk) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="bcsk_<%=i %>" name="xsskDescs[<%=i %>].bcsk" value="<%=JMath.round(bcsk) %>"  onblur="hj();"></td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" style="width:100%" id="xsd_id_<%=i %>" name="xsskDescs[<%=i %>].xsd_id" value="" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="fsrq_<%=i %>" name="xsskDescs[<%=i %>].fsrq" value="" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="fsje_<%=i %>" name="xsskDescs[<%=i %>].fsje" value="0.00" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="ysk_<%=i %>" name="xsskDescs[<%=i %>].yfje"  value="0.00" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="bcsk_<%=i %>" name="xsskDescs[<%=i %>].bcsk" value="0.00" readonly onblur="hj();"></td>
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"></td>
		<td class="a2"><input type="text" style="width:100%" id="hj_fsje" name="hj_fsje" value="<%=JMath.round(hj_fsje) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="hj_ysk" name="hj_ysk"  value="<%=JMath.round(hj_ysk) %>" readonly></td>
		<td class="a2"><input type="text" style="width:100%" id="hj_bcsk" name="hj_bcsk" value="<%=JMath.round(hj_bcsk) %>" readonly></td>
	</tr>
	 <%
	 String cssStyle = "readonly";
	 if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")){
		 cssStyle = "";
	 } 
	 %>	
	<tr>
		<td class="a1">备  注</td>
		<td class="a2" colspan="4"><input name="xssk.remark" id="remark" style="width:100%" value="<%=StringUtils.nullToStr(xssk.getRemark()) %>"></td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		
	<tr height="35">
		<td class="a1">
			<input type="button" name="btnSub" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>