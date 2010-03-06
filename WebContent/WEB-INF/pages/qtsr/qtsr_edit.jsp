<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtsr qtsr = (Qtsr)VS.findValue("qtsr");
String[] srlx = (String[])VS.findValue("srlx");
List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>其它收入</title>
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
	function saveInfo(vl){
		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		if(!InputValid(document.getElementById("id"),1,"string",1,1,50,"编号")){	 return; }
		if(!InputValid(document.getElementById("sr_date"),1,"string",1,1,50,"收入日期")){	 return; }
		if(document.getElementById("type").value == ""){
			alert("收入类型不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("skje"),1,"float",1,0,999999999,"收款金额")){	 return; }
		
		if(document.getElementById("skzh").value == ""){
			alert("收款账号不能为空，请选择！");
			return;
		}	
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}	
				
		if(vl == "1"){
			document.qtsrForm.submit();
		}else{
			if(window.confirm("提交后将不能修改，确认提交吗？")){
				document.qtsrForm.submit();
			}else{
				return;
			}
		}	
		document.qtsrForm.btnSub.disabled = true;
		document.qtsrForm.btnSave.disabled = true;	
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}		
	
</script>
</head>
<body onload="initFzrTip();">
<form name="qtsrForm" action="updateQtsr.html" method="post">
<input type="hidden" name="qtsr.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其它收入</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="qtsr.id" id="id" value="<%=StringUtils.nullToStr(qtsr.getId()) %>" readonly><font color="red">*</font></td>
		<td class="a1" width="15%">收入日期</td>
		<td class="a2">
			<input type="text" name="qtsr.sr_date" id="sr_date" value="<%=StringUtils.nullToStr(qtsr.getSr_date()) %>" class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="qtsr.type" id="type">
				<option value=""></option>
			<%
			if(srlx != null && srlx.length>0){
				for(int i=0;i<srlx.length;i++){
			%>
			<option value="<%=srlx[i] %>" <%if(StringUtils.nullToStr(qtsr.getType()).equals(srlx[i])) out.print("selected"); %>><%=srlx[i] %></option>
			<%		
				}
			}
			%>
			</select>		<font color="red">*</font>	
		</td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtsr.skje" id="skje" value="<%=JMath.round(qtsr.getSkje()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtsr.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="qtsr.skzh" value="<%=StringUtils.nullToStr(qtsr.getSkzh()) %>"><font color="red">*</font>
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(qtsr.getJsr()) %>"/> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:385px; top:110px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="qtsr.jsr" id="fzr" value="<%=qtsr.getJsr() %>"/> <font color="red">*</font>
		</td>	
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备 注</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" cols="50" name="qtsr.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(qtsr.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="保 存" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
<font color="red">注：保存后不结算可修改；提交后完成结算不可修改。</font>
</form>
</body>
</html>
