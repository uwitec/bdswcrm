<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>生成摊销计划</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function chkTxfs(){
		if(!InputValid(document.getElementById("txyf"),1,"int",1,1,9999,"摊销月份数")){
			document.getElementById("txyf").focus();
			return; 
		}
		
		var counts = document.getElementById("txyf").value;		
		var total_je = document.getElementById("fkje").value;	
			
		document.getElementById("txje").value = (total_je / counts).toFixed(2);
	}

	function subPlan(){
		var counts = document.getElementById("txyf").value;
		
		var total_je = document.getElementById("fkje").value;
		
		var txje = (total_je / counts).toFixed(2);
		
		var tx_start_date = document.getElementById("start_date").value;
		if(tx_start_date == ""){
			alert("摊销起始日期不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("txyf"),1,"int",1,1,9999,"摊销月份数")){
			document.getElementById("txyf").focus();
			return; 
		}
		
		var strYear = parseInt(tx_start_date.substring(0,4));
		var strMonth = parseInt(tx_start_date.substring(5,7),10);
		var strDay = tx_start_date.substring(8,10);
		
		for(var i=0;i<counts;i++){
			var txrq = window.opener.document.getElementById("txrq_" + i);
			
			if(txrq == null){
				window.opener.addTr();
			}
			
			txrq = window.opener.document.getElementById("txrq_" + i);
			var je = window.opener.document.getElementById("je_" + i);
			var remark = window.opener.document.getElementById("remark_" + i);
			

			if(strMonth < 10){
				strMonth = "0" + strMonth;
			}
			
			var cdate = strYear + "-" + strMonth + "-" + strDay;
			txrq.value = cdate;
			je.value = txje;
			remark.value = "由摊销付款生成，摊销付款单编号[" + document.getElementById("id").value + "]";
			
			strMonth++;
			if(strMonth > 12){
				strMonth = 1;
				strYear++;
			}
		}
		
		window.close();
	}	
</script>
</head>
<body>
<form name="myform" action="updateTxfk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">生成摊销计划</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="35%">付款单编号</td>
		<td class="a2" width="65%">
			<ww:textfield name="id" id="id" value="%{id}" theme="simple" readonly="true"/>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="35%">付款金额</td>
		<td class="a2" width="65%">
			<ww:textfield name="fkje" id="fkje" value="%{fkje}" theme="simple" readonly="true"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="35%">起始摊销日期</td>
		<td class="a2" width="65%">
			<input type="text" name="start_date" id="start_date" value="" class="Wdate" onFocus="WdatePicker()"/>	
		</td>						
	</tr>
	<tr>
		<td class="a1" width="35%">摊销月份数</td>
		<td class="a2" width="65%">
			<ww:textfield name="txyf" id="txyf" value="" theme="simple" size="5" onblur="chkTxfs();"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="35%">每月摊销金额</td>
		<td class="a2" width="65%">
			<ww:textfield name="txje" id="txje" value="" theme="simple" readonly="true"/>
		</td>						
	</tr>					
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="确定" class="css_button" onclick="subPlan();">&nbsp;
			<input type="button" name="button1" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
