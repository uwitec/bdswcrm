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
%>

<html>
<head>
<title>添加采购付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	var allCount = <%=allCount %>;

	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("fk_date"),1,"string",1,1,20,"付款日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("fkzh"),1,"string",1,1,50,"付款账户")){	 return; }
		
		hj();
		
		if(isNaN(document.getElementById("fkje").value) || parseFloat(document.getElementById("fkje").value) == 0){
			alert("本次付款金额必须为数字并且不等于0，请检查！");
			return;
		}
		
		document.cgfkForm.btnSub.disabled = true;
		
		document.cgfkForm.submit();
	}
	
	function openWin(){
		var destination = "selectJhd.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function openProvider(){
		var destination = "selXsskClient.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
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
				if(!InputValid(fk,0,"float",0,1,99999999,"本次付款")){
					fk.focus();
					return;
				}
				
				if(parseFloat(fk.value) > parseFloat(yfk.value)){
					alert("本次付款金额大于应付金额，请检查！");
					fk.focus();
					return;
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
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购付款信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="cgfk.id" id="id" value="<%=StringUtils.nullToStr(cgfk.getId()) %>">
		</td>
		<td class="a1" width="15%">是否预付款</td>
		<td class="a2"><input type="checkbox" name="cgfk.is_yfk" id="is_yfk" value="是" onclick="chkYfk();">预付款</td>
	</tr>
	<tr>
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%"><input type="text" name="cgfk.fk_date" id="fk_date" value="<%=StringUtils.nullToStr(cgfk.getFk_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(cgfk.getFk_date()) %>"  class="Wdate" onFocus="WdatePicker()">
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
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" />
            <div   id="brandTip"  style="height:12px;position:absolute;left:132px; top:114px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="cgfk.jsr" id="fzr"  /> <font color="red">*</font>	
		</td>		
		<td class="a1" widht="20%">付款账户</td>
			<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgfk.getFkzh())) %>" readonly>
			<input type="hidden" id="fkzh"  name="cgfk.fkzh" value="<%=StringUtils.nullToStr(cgfk.getFkzh()) %>">
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
			<font color="red">*</font>
		</td>
	</tr>
	<tr>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" colspan="3">
			<select name="cgfk.state">
				<option value="已保存">已保存</option>
				<option value="已提交">已提交</option>
			</select>		
		</td>			
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
		<td>进货单编号</td>
		<td>发生日期</td>
		<td>发生金额</td>
		<td>应付金额</td>
		<td>本次付款</td>
		<td>备注</td>
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
		<td class="a2"><input type="text" id="jhd_id_<%=i %>" name="cgfkDescs[<%=i %>].jhd_id" value="<%=StringUtils.nullToStr(map.get("jhd_id")) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="cgfkDescs[<%=i %>].fsrq" value="<%=StringUtils.nullToStr(map.get("fsrq")) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="cgfkDescs[<%=i %>].fsje" value="<%=JMath.round(fsje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="yfje_<%=i %>" name="cgfkDescs[<%=i %>].yfje"  value="<%=JMath.round(yfje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcfk_<%=i %>" name="cgfkDescs[<%=i %>].bcfk" value="<%=JMath.round(bcfk) %>"  onblur="hj();"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgfkDescs[<%=i %>].remark" value="<%=StringUtils.nullToStr(map.get("remark")) %>"></td>	
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" id="jhd_id_<%=i %>" name="cgfkDescs[<%=i %>].jhd_id" value="" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="cgfkDescs[<%=i %>].fsrq" value="" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="cgfkDescs[<%=i %>].fsje" value="0.00" readonly></td>
		<td class="a2"><input type="text" size="10" id="yfje_<%=i %>" name="cgfkDescs[<%=i %>].yfje"  value="0.00" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcfk_<%=i %>" name="cgfkDescs[<%=i %>].bcfk" value="0.00" onblur="hj();"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgfkDescs[<%=i %>].remark" value=""></td>	
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"></td>
		<td class="a2"><input type="text" size="10" id="hj_fsje" name="hj_fsje" value="<%=JMath.round(hj_fsje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_yfje" name="hj_yfje"  value="<%=JMath.round(hj_yfje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_bcfk" name="hj_bcfk" value="0.00"></td>
		<td class="a2"></td>	
	</tr>
	<tr>
		<td class="a1">本次付款总金额</td>
		<td class="a4" colspan="5"><input type="text" size="10" id="fkje" name="cgfk.fkje" value="0.00" readonly></td>
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备  注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="cgfk.remark" id="remark" style="width:80%"><%=StringUtils.nullToStr(cgfk.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="btnReset" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnClose" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>