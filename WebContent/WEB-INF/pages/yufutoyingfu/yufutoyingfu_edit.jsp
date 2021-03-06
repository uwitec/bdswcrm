<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YufuToYingfu yufuToYingfu = (YufuToYingfu)VS.findValue("yufuToYingfu");

List userList = (List)VS.findValue("userList");
double clientHjYufuK = (Double)VS.findValue("clientHjYufuK");

List yufuToYingfuDescs = (List)VS.findValue("yufuToYingfuDescs");
 

int allCount = 3;
if(yufuToYingfuDescs != null && yufuToYingfuDescs.size()>0){
	allCount = yufuToYingfuDescs.size();
}
String msg = StringUtils.nullToStr(VS.findValue("msg"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>预付冲应付</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
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
	var msg = "<%=msg %>";
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("create_date"),1,"string",1,1,20,"日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		hj();

		if(vl == "1"){
			document.XsskForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				document.XsskForm.submit();
			}else{
				return;
			}
		}
		
		document.XsskForm.btnSub.disabled = true;
		document.XsskForm.btnSave.disabled = true;
	}
	
	
	function hj(){
		var bcjs_hj = 0;

		for(var i=0;i<allCount;i++){
				
			var bcjs = document.getElementById("bcjs_" + i);  //本次结算金额
			var xsdysje = document.getElementById("yingfuje_" + i);  //销售单应付金额
			
			if(!InputValid(bcjs,0,"float",0,-99999999,99999999,"本次结算")){
				bcjs.focus();
				return;
			}
			if(parseFloat(xsdysje.value) > 0){
				if(parseFloat(bcjs.value) > parseFloat(xsdysje.value)){
					alert("本次结算金额应小于或等于应付金额，请检查！");
					bcjs.focus();
					return;
				}
			}else{
				if(parseFloat(bcjs.value) < parseFloat(xsdysje.value)){
					alert("应付金额为负值时，本次结算金额应大于或等于应付金额，请检查！");
					bcjs.focus();
					return;
				}
			}
			
			bcjs_hj = parseFloat(bcjs_hj) + parseFloat(bcjs.value);
			
		}
		
		if(parseFloat(bcjs_hj) > parseFloat(document.getElementById("yufuzje").value)){
			alert("结算合计金额大于预付总金额，请检查！");
			bcjs.focus();
			return;
		}
		
		var hjjs = document.getElementById("total");
		hjjs.value = bcjs_hj.toFixed(2);
		
	}

	function onloadMsg(){
		if(msg == "") return;

		var tempMsg = "预付冲应付明细中：\n" + msg + "\n与其它付款申请单或预付冲应付存在冲突，无法保存，请检查！";

		alert(tempMsg);
	}	

	function autoFix(){
		
		var yfje = parseFloat(document.getElementById("yufuzje").value);  //预付总金额

		if(yfje <= 0){
			alert("预付总金额小于或等于0时无法自动分配,请手动分配处理!");
			return;
		}
		
		var lastJe = yfje;

		for(var i=0;i<allCount;i++){
			document.getElementById("bcjs_" + i).value = "0.00";
		}

		for(var i=0;i<allCount;i++){

			var fk = document.getElementById("bcjs_" + i);   //本行本次结算
			var yfje = document.getElementById("yingfuje_" + i);   //本行应付金额

			if(parseFloat(lastJe) != 0){ //当前剩余金额不等于0
				if((parseFloat(lastJe)) >= (parseFloat(yfje.value))){
					fk.value = parseFloat(yfje.value).toFixed(2);
					lastJe = (parseFloat(lastJe)) - (parseFloat(yfje.value));
				}else{
					fk.value = lastJe.toFixed(2);
					break;
				}
			}

			if(lastJe == 0) break;
		}

		hj();
	}		
</script>
</head>
<body  onload="initFzrTip();onloadMsg();">
<form name="XsskForm" action="updateYufuToYingfu.html" method="post">
<input type="hidden" name="yufuToYingfu.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">预付冲应付</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="yufuToYingfu.id" style="width:200px"  id="id" value="<%=StringUtils.nullToStr(yufuToYingfu.getId()) %>" readonly> <font color="red">*</font>
		</td>
		<td class="a1" width="15%">结算日期</td>
		<td class="a2" width="35%"><input type="text" name="yufuToYingfu.create_date" style="width:200px"  id="create_date" value="<%=StringUtils.nullToStr(yufuToYingfu.getCreate_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(yufuToYingfu.getCreate_date()) %>" class="Wdate" onFocus="WdatePicker()"> <font color="red">*</font>
		</td>		
	</tr>
	<tr>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="clientName" id="client_name" style="width:200px"  value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(yufuToYingfu.getClient_name())) %>" size="35" readonly> <font color="red">*</font>
		<input type="hidden" name="yufuToYingfu.client_name" id="client_id" value="<%=StringUtils.nullToStr(yufuToYingfu.getClient_name()) %>">
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text" style="width:200px"   onblur="setValue()" value="<%=StaticParamDo.getRealNameById(yufuToYingfu.getJsr())%>"/>  <font color="red">*</font>
            <div   id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="yufuToYingfu.jsr" id="fzr"  value="<%=yufuToYingfu.getJsr()%>"/> 
		</td>				
	</tr>
	<tr>		
		<td class="a1" width="15%">预付总金额</td>
		<td class="a2" colspan="3">
			<input type="text" name="yufuzje" id="yufuzje" value="<%=JMath.round(clientHjYufuK) %>" style="width:100px" readonly>
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
		<td width="25%">销售单编号</td>
		<td width="25%">应收金额</td>
		<td width="25%">本次结算</td>
		<td width="25%">备注</td>
	</tr>
	</thead>
<%
double hj_yingfuje = 0;
double hj_bcjs = 0;

if(yufuToYingfuDescs != null && yufuToYingfuDescs.size()>0){
	for(int i=0;i<yufuToYingfuDescs.size();i++){
		YufuToYingfuDesc info = (YufuToYingfuDesc)yufuToYingfuDescs.get(i);

		hj_yingfuje += info.getYingfuje();
		hj_bcjs += info.getBcjs();
%>
	<tr>
		<td class="a2"><input type="text" style="width:90%" id="jhd_id_<%=i %>" name="yufuToYingfuDescs[<%=i %>].jhd_id" value="<%=StringUtils.nullToStr(info.getJhd_id()) %>" readonly></td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="yingfuje_<%=i %>" name="yufuToYingfuDescs[<%=i %>].yingfuje" value="<%=JMath.round(info.getYingfuje()) %>" readonly></td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="bcjs_<%=i %>" name="yufuToYingfuDescs[<%=i %>].bcjs" value="<%=JMath.round(info.getBcjs()) %>" onblur="hj();"></td>
		<td class="a2"><input type="text" style="width:90%" id="remark_<%=i %>" name="yufuToYingfuDescs[<%=i %>].remark" value="<%=StringUtils.nullToStr(info.getRemark()) %>"></td>	
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" style="width:90%" id="xsd_id_<%=i %>" name="yufuToYingfuDescs[<%=i %>].jhd_id" value="" readonly></td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="yingfuje_<%=i %>" name="yufuToYingfuDescs[<%=i %>].yingfuje" value="0.00" readonly></td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="bcjs_<%=i %>" name="yufuToYingfuDescs[<%=i %>].bcjs" value="0.00" readonly onblur="hj();"></td>
		<td class="a2"><input type="text" style="width:90%" id="remark_<%=i %>" name="yufuToYingfuDescs[<%=i %>].remark"  value="" readonly></td>
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="hj_yingfuje" name="hj_yingfuje" value="<%=JMath.round(hj_yingfuje) %>" readonly></td>
		<td class="a2"><input type="text" style="width:90%;text-align:right" id="total" name="yufuToYingfu.total" value="<%=JMath.round(hj_bcjs) %>" readonly></td>
		<td class="a2"></td>	
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
			<textarea rows="3" cols="50" name="yufuToYingfu.remark" id="remark" style="width:80%"><%=StringUtils.nullToStr(yufuToYingfu.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
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