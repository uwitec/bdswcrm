<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Lsysk lsysk = (Lsysk)VS.findValue("lsysk");
String[] fkfs = (String[])VS.findValue("fkfs");

List userList = (List)VS.findValue("userList");

List posTypeList = (List)VS.findValue("posTypeList");

%>

<html>
<head>
<title>零售预收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
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
		
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,50,"客户名称")){	 return; }
		if(!InputValid(document.getElementById("lxdh"),1,"string",1,1,20,"联系电话")){	 return; }
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}		
		if(!InputValid(document.getElementById("ysje"),1,"float",1,-999999999,999999999,"预收金额")){	 return; }
		
		if(document.getElementById("skzh").value == ""){
			alert("收款账户不能为空，请选择！");
			return;
		}	
			
		if(document.getElementById("fkfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}			
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("提交后将不能修改，确认提交吗？")){
				document.lsyskForm.submit();
			}else{
				return;
			}
		}else{
			document.lsyskForm.submit();
		}
		document.lsyskForm.btnSave.disabled = true;
		document.lsyskForm.btnSub.disabled = true;
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择客户',fea);		
	}	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	function selFkfs(vl){
		if(vl == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
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
			dwr.util.setValue("skzh",account.id);
			dwr.util.setValue("zhname",account.name);
		}
	}		
</script>
</head>
<body onload="initFzrTip();">
<form name="lsyskForm" action="updateLsysk.html" method="post">
<input type="hidden" name="lsysk.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">零售预收款</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.id" id="id" value="<%=StringUtils.nullToStr(lsysk.getId()) %>" readonly><font color="red">*</font></td>
		<td class="a1" width="15%">预收日期</td>
		<td class="a2">
			<input type="text" name="lsysk.ys_date" id="ys_date" value="<%=StringUtils.nullToStr(lsysk.getYs_date()) %>" class="Wdate" onFocus="WdatePicker()">
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.client_name" id="client_name" value="<%=StringUtils.nullToStr(lsysk.getClient_name()) %>"><font color="red">*</font></td>	
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.lxr" id="lxr" value="<%=StringUtils.nullToStr(lsysk.getLxr()) %>"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(lsysk.getLxdh()) %>"><font color="red">*</font></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.mobile" id="mobile" value="<%=StringUtils.nullToStr(lsysk.getMobile()) %>"></td>		
	</tr>
	<tr>						
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()"  value="<%=StaticParamDo.getRealNameById(lsysk.getJsr()) %>" /> 
          <div   id="brandTip"  style="height:12px;position:absolute;left:117px; top:140px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="lsysk.jsr" id="fzr"  value="<%=lsysk.getJsr() %>" /> <font color="red">*</font>		
		</td>	
		<td class="a1" width="15%">预收金额</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.ysje" id="ysje" value="<%=JMath.round(lsysk.getYsje()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" widht="20%">客户付款方式</td>
		<td class="a2">
			<select name="lsysk.fkfs" id="fkfs">
			<%
			if(fkfs != null && fkfs.length > 0){
				for(int i =0;i<fkfs.length;i++){
			%>
				<option value="<%=fkfs[i] %>" <%if(fkfs[i].equals(StringUtils.nullToStr(lsysk.getFkfs()))) out.print("selected"); %>><%=fkfs[i] %></option>
			<%
				}
			}
			
			String khfkfs = StringUtils.nullToStr(lsysk.getFkfs());
			String cssStyle = "display:none";
			if(khfkfs.equals("刷卡")){
				cssStyle = "";
			}
			%>
				
			</select>	
			<select name="lsysk.pos_id" id="pos_id" style="<%=cssStyle %>" onchange="dwrGetAccount();">
				<option value=""></option>
			<%
			String pos_id = StringUtils.nullToStr(lsysk.getPos_id());
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(pos_id.equals(posType.getId()+"")) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>
				
			</select>	<font color="red">*</font>					
		</td>	
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %>" readonly><font color="red">*</font>
		<input type="hidden" id="skzh"  name="lsysk.skzh" value="<%=StringUtils.nullToStr(lsysk.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><input name="lsysk.remark" id="remark" style="width:90%" maxlength="100" value="<%=StringUtils.nullToStr(lsysk.getRemark()) %>">
		</td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1">
			<input type="button" name="button1" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>
