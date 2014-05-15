<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售发票开票</title>
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

	var strCount = '<ww:property value="%{fpmxDescs.size}"/>';
	if(strCount == '0') strCount = '1';
	var allCount = parseInt(strCount)-1;

	//保存发票信息
	function saveInfo(vl){
		hj();

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(!InputValid(document.getElementById("kprq"),1,"string",1,1,20,"开票日期")){	 return; }
		if(!InputValid(document.getElementById("khmc"),1,"string",1,1,100,"客户名称")){	 return; }
		if(document.getElementById("fplx").value == ""){
			alert("发票类型不能为空，请选择");
			return;
		}
		if(!InputValid(document.getElementById("fph"),1,"string",1,1,25,"发票号")){	 return; }
		if(!InputValid(document.getElementById("fzr"),1,"string",1,1,25,"开票人")){	 return; }
		
		if(parseFloat(document.getElementById("fpje").value) <= 0){
			alert("开票金额不能小于等于0，请检查！");
			return;
		}

		if(vl == "1"){
			document.xsfpForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				document.xsfpForm.submit();
			}else{
				return;
			}
		}
	}

		
    function addTr(){
        var otr = document.getElementById("fpmxTable").insertRow(-1);
        var curId = document.getElementById('fpmxTable').rows.length-3;

        allCount = allCount +1;
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = curId +2;
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" name="fpmxDescs[' + curId + '].khmc" id="khmc_' + curId + '" value="" theme="simple" readonly="true" style="width:90%"/>';
       
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" name="fpmxDescs[' + curId + '].yw_id" id="yw_id_' + curId + '" value="" readonly="true"  theme="simple" style="width:90%;"/>';
        
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text"  name="fpmxDescs[' + curId + '].cdate" id="cdate_' + curId + '" value="" readonly="true"  theme="simple" style="width:90%;"/>';        
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text"  name="fpmxDescs[' + curId + '].kpje_ying" id="kpje_ying_' + curId + '" value="" readonly="true"  theme="simple" style="width:90%;text-align:right"/>';        
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text"  name="fpmxDescs[' + curId + '].kpje_yi" id="kpje_yi_' + curId + '" value="" theme="simple" readonly="true"  style="width:90%;text-align:right"/>';        
        
        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text"  name="fpmxDescs[' + curId + '].kpje_bc" id="kpje_bc_' + curId + '" value="" theme="simple" onblur="hj();" style="width:90%;text-align:right"/>';        
        
        var otd7 = document.createElement("td");
        otd7.className = "a2";
        otd7.innerHTML = '<input type="text"  name="txfkDescs[' + curId + '].remark" id="remark_' + curId + '" value="" theme="simple" style="width:90%;"/>';        
        
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2);
        otr.appendChild(otd3);
        otr.appendChild(otd4);
        otr.appendChild(otd5);
        otr.appendChild(otd6);
        otr.appendChild(otd7);
    }	
    
    function hj(){
    	var length = (document.getElementById('fpmxTable').rows.length-3);
		if(!InputValid(document.getElementById("fpje_bdd"),1,"float",1,0,99999999,"本次开票金额")){
			document.getElementById("fpje_bdd").focus();
			return;
		}
    	
    	var hjz = document.getElementById("fpje_bdd").value;
    	document.getElementById("fpje_bdd").value = parseFloat(document.getElementById("fpje_bdd").value).toFixed(2);	
    	
    	for(var i=0;i<=length;i++){		
    		var kpje_bc_obj = document.getElementById("kpje_bc_" + i);
    		if(!InputValid(kpje_bc_obj,1,"float",1,0,99999999,"本次开票金额")){
    			kpje_bc_obj.focus();
    			return;
    		}
    		if(parseFloat(kpje_bc_obj.value) > parseFloat(document.getElementById("kpje_ying_" + i).value) - parseFloat(document.getElementById("kpje_yi_" + i).value)){
    			alert("本次开票金额大于可开票金额，请检查！");
    			kpje_bc_obj.focus();
    			return;
    		}
    		var kpje_bc = document.getElementById("kpje_bc_" + i).value;
    		document.getElementById("kpje_bc_" + i).value = parseFloat(kpje_bc).toFixed(2);	
    		hjz = (parseFloat(hjz) +parseFloat(kpje_bc)).toFixed(2);		
    	}
    	document.getElementById('fpje').value = parseFloat(hjz).toFixed(2);		
    }
	
	
	function addMx(){
	
		var destination = "listXsfpDkfp.html";
		var fea ='width=900,height=550,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		
		window.open(destination,'选择待开发票',fea);
	}	
</script>
</head>
<body onload="initFzrTip();">
<form name="xsfpForm" action="updateXsfpFpxx.html" method="post">
<input type="hidden" name="xsfpFpxx.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售发票开票</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="xsfpFpxx.id" id="id" value="%{xsfpFpxx.id}" theme="simple" readonly="true"  cssStyle="width:232px"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">开票日期</td>
		<td class="a2" width="35%">
			<input type="text" name="xsfpFpxx.kprq" id="kprq" value="<ww:property value="%{xsfpFpxx.kprq}"/>" class="Wdate"  style="width:232px" onFocus="WdatePicker()"/> <span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%">
			<ww:textfield name="xsfpFpxx.khmc" id="khmc" value="%{xsfpFpxx.khmc}" theme="simple"  cssStyle="width:232px" maxLength="100"/><span style="color:red">*</span>
		</td>	
		<td class="a1" width="15%">发票类型</td>
		<td class="a2" width="35%">
			<ww:select name="xsfpFpxx.fplx" id="fplx" theme="simple" list="#{'普通发票':'普通发票','增值发票':'增值发票'}"  emptyOption="true"  cssStyle="width:232px" ></ww:select><span style="color:red">*</span>
		</td>	
	</tr>	
	<tr>
		<td class="a1" width="15%">发票号</td>
		<td class="a2" width="35%">
			<ww:textfield name="xsfpFpxx.fph" id="fph" value="%{xsfpFpxx.fph}" theme="simple"  cssStyle="width:232px" maxLength="100"/><span style="color:red">*</span>
		</td>					
		<td class="a1" width="15%">开票人</td>
		<td class="a2" width="35%">
			<ww:textfield name="brand" id="brand" onblur="setValue()" value="%{getUserRealName(xsfpFpxx.getKpr())}"  cssStyle="width:232px" theme="simple"></ww:textfield>
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <ww:hidden name="xsfpFpxx.kpr" id="fzr" value="%{xsfpFpxx.kpr}" theme="simple"></ww:hidden><font color="red">*</font>		
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2"  colspan="3">
			<ww:textfield name="xsfpFpxx.remark" id="remark" value="%{xsfpFpxx.remark}" theme="simple"  cssStyle="width:532px" maxLength="100"/>
		</td>					
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发票明细内容</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="fpmxTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">序号</td>
		<td width="25%">客户名称</td>
		<td width="15%">销售单号</td>
		<td width="10%">销售日期</td>
		<td width="10%">应开票金额</td>
		<td width="10%">已开票金额</td>
		<td width="10%">本次开票金额</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	
	<ww:if test="counts>0">
	<tr>
		<td class="a2">1</td>
		<td class="a2">&nbsp;</td>
		<td class="a2">不对单</td>
		<td class="a2">&nbsp;</td>
		<td class="a2"><ww:textfield name='bdd_ykpje' id='bdd_ykpje' value="0.00" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='bdd_yikpje' id='bdd_yikpje' value="0.00" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.fpje_bdd' id='fpje_bdd' value="%{getText('global.format.double',{xsfpFpxx.fpje_bdd})}" theme="simple"  onblur="hj();" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.remark_bdd' id='remark_bdd' value="%{xsfpFpxx.remark_bdd}" theme="simple" cssStyle="width:90%"/></td>
	</tr>		
	<ww:iterator value="%{fpmxDescs}" status="li">
	<tr>
		<td class="a2"><ww:property value="#li.count+1"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].khmc" id="khmc_%{#li.count-1}" value="%{khmc}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].yw_id" id="yw_id_%{#li.count-1}" value="%{yw_id}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].cdate" id="cdate_%{#li.count-1}" value="%{cdate}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_ying" id="kpje_ying_%{#li.count-1}" value="%{getText('global.format.double',{kpje_ying})}" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_yi" id="kpje_yi_%{#li.count-1}" value="%{getText('global.format.double',{kpje_yi})}" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_bc" id="kpje_bc_%{#li.count-1}" value="%{getText('global.format.double',{kpje_bc})}" theme="simple"  onblur="hj();" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].remark" id="remark_%{#li.count-1}" value="%{remark}" theme="simple" cssStyle="width:90%"/></td>		
	</tr>	
	</ww:iterator>
	</ww:if>	
	<ww:else>
	<tr>
		<td class="a2">1</td>
		<td class="a2">&nbsp;</td>
		<td class="a2">不对单</td>
		<td class="a2">&nbsp;</td>
		<td class="a2"><ww:textfield name='bdd_ykpje' id='bdd_ykpje' value="0.00"  readonly="true" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='bdd_yikpje' id='bdd_yikpje' value="0.00"  readonly="true" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.fpje_bdd' id='fpje_bdd'  value="%{getText('global.format.double',{xsfpFpxx.fpje_bdd})}" theme="simple" cssStyle="width:90%;text-align:right" onblur="hj();"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.remark_bdd' id='remark_bdd' value="%{xsfpFpxx.remark_bdd}" theme="simple" cssStyle="width:90%"/></td>
	</tr>		
	<tr>
		<td class="a2">2</td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].khmc" id="khmc_0" value="" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].yw_id" id="yw_id_0" value="" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].cdate" id="cdate_0" value="" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].kpje_ying" id="kpje_ying_0" value="0.00" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].kpje_yi" id="kpje_yi_0" value="0.00" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].kpje_bc" id="kpje_bc_0" value="0.00" theme="simple" cssStyle="width:90%;text-align:right" onblur="hj();"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[0].remark" id="remark_0" value="" theme="simple" cssStyle="width:90%"/></td>
	</tr>	
	</ww:else>	
</table>
<table width="100%"  align="center" id="fpmxTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a2" width="5%"><b>合计</b></td>
		<td class="a2" width="25%">&nbsp;</td>
		<td class="a2" width="15%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%"><ww:textfield name='xsfpFpxx.fpje' id='fpje'  value="%{getText('global.format.double',{xsfpFpxx.fpje})}" theme="simple" readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2" width="15%">&nbsp;</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;
			<input type="button" name="button1" value="添加开票明细" class="css_button3" onclick="addMx();">
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