<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>付摊销付款</title>
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

	//保存付摊销付款
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(!InputValid(document.getElementById("fk_date"),1,"string",1,1,20,"付款日期")){	 return; }
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,100,"相关客户")){	 return; }
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择");
			return;
		}
		if(document.getElementById("fklx").value == ""){
			alert("支付类型不能为空，请选择");
			return;
		}
		if(!InputValid(document.getElementById("fkje"),1,"float",1,-9999999999,999999999,"付款金额")){	 return; }	
		if(document.getElementById("fkzh").value == ""){
			alert("付款账户不能为空，请选择");
			return;
		}

		if(vl == "1"){
			document.txfkForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				var myAjax = new Ajax.Request("checkAccountJe.html",
						{
							method:'post',
							parameters: 'account_id=' + document.getElementById("fkzh").value + "&zcje=" + document.getElementById("fkje").value,
							onComplete: subForm,
							asynchronous:true
						});
			}else{
				return;
			}
		}
	}

	function subForm(originalRequest){
		if(originalRequest.responseText.trim() == "false"){
			alert("支付账户金额不足，请检查！");
			return;
		}else{
			document.txfkForm.submit();
		}
	}
		
    function addTr(){
        var otr = document.getElementById("txfkTable").insertRow(-1);
        var curId = document.getElementById('txfkTable').rows.length-2;


        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = curId +1;
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" name="txfkDescs[' + curId + '].txrq" id="txrq_' + curId + '" value="" theme="simple" readonly="true" style="width:90%"/>';
       
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" name="txfkDescs[' + curId + '].je" id="je_' + curId + '" value="" theme="simple" style="width:90%;text-align:right"/>';
        
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text"  name="txfkDescs[' + curId + '].remark" id="remark_' + curId + '" value="" theme="simple" style="width:90%;text-align:right"/>';        
        
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2);
        otr.appendChild(otd3);
    }		
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function opePlan(){
		if(!InputValid(document.getElementById("fkje"),1,"float",1,-9999999999,999999999,"付款金额")){	
			document.getElementById("fkje").focus(); 
			return; 
		}
	
		var destination = "planTxfk.html?id=" + document.getElementById("id").value + "&fkje=" + document.getElementById("fkje").value;
		var fea ='width=500,height=400,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		
		window.open(destination,'选择账户',fea);
	}	
</script>
</head>
<body onload="initFzrTip();">
<form name="txfkForm" action="updateTxfk.html" method="post">
<input type="hidden" name="txfk.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付摊销付款</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="txfk.id" id="id" value="%{txfk.id}" theme="simple" readonly="true"  cssStyle="width:232px"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%">
			<input type="text" name="txfk.fk_date" id="fk_date" value="<ww:property value="%{txfk.fk_date}"/>" class="Wdate"  style="width:232px" onFocus="WdatePicker()"/>&nbsp;	
			<span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%">
			<ww:textfield name="txfk.client_name" id="client_name" value="%{txfk.client_name}" theme="simple"  cssStyle="width:232px" maxLength="100"/><span style="color:red">*</span>
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
			<ww:textfield name="brand" id="brand" onblur="setValue()" value="%{getUserRealName(txfk.getJsr())}"  cssStyle="width:232px" theme="simple"></ww:textfield>
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <ww:hidden name="txfk.jsr" id="fzr" value="%{txfk.jsr}" theme="simple"></ww:hidden><font color="red">*</font>		
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">支付类型</td>
		<td class="a2" width="35%">
			<ww:select name="txfk.fklx" id="fklx" theme="simple" list="%{zclxArray}"  emptyOption="true"  cssStyle="width:232px"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">付款金额</td>
		<td class="a2" width="35%">
			<ww:textfield name="txfk.fkje" id="fkje" value="%{getText('global.format.double',{txfk.fkje})}" theme="simple" cssStyle="width:232px"/><span style="color:red">*</span>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">支付账户</td>
		<td class="a2" colspan="3">
			<ww:textfield id="zhname"  name="zhname" value="%{getAccountName(txfk.account_id)}" onclick="openAccount();" theme="simple"  cssStyle="width:232px" readonly="true"/>
			<ww:hidden name="txfk.account_id" id="fkzh" value="%{txfk.account_id}" theme="simple"/>
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><span style="color:red">*</span>
		</td>
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">摊销计划</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="txfkTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="15%">序号</td>
		<td width="20%">摊销日期</td>
		<td width="20%">摊销金额</td>
		<td width="45%">备注</td>
	</tr>
	</thead>
	
	<ww:if test="counts>0">
	<ww:iterator value="%{txfkDescs}" status="li">
	<tr>
		<td class="a2"><ww:property value="#li.count"/></td>
		<td class="a2"><ww:textfield name='txfkDescs[%{#li.count-1}].txrq' id='txrq_%{#li.count-1}' value="%{txrq}" theme="simple" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name='txfkDescs[%{#li.count-1}].je' id='je_%{#li.count-1}' value="%{getText('global.format.double',{je})}" theme="simple"  cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='txfkDescs[%{#li.count-1}].remark' id='remark_%{#li.count-1}' value="%{remark}" theme="simple"  cssStyle="width:90%"/></td>
	</tr>	
	</ww:iterator>
	</ww:if>	
	<ww:else>
	<tr>
		<td class="a2">1</td>
		<td class="a2"><ww:textfield name="txfkDescs[0].txrq" id="txrq_0" value="" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="txfkDescs[0].je" id="je_0" value="" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="txfkDescs[0].remark" id="remark_0" value="" theme="simple" cssStyle="width:90%"/></td>
	</tr>	
	</ww:else>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;
			<input type="button" name="button1" value="生成摊销计划" class="css_button3" onclick="opePlan();">
		</td>
	</tr>	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2">
			<ww:textarea name="txfk.remark" id="remark"  theme="simple" cssStyle="width:70%"/>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>