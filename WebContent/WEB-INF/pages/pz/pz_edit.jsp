<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Pz pz = (Pz)VS.findValue("pz");
String[] pzlx = (String[])VS.findValue("pzlx");
List clientsList=(List)VS.findValue("clientsList");
List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>往来调账</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	//提交往来调账信息
	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,50,"编号")){	 return; }
		if(!InputValid(document.getElementById("pz_date"),1,"string",1,1,20,"日期")){	 return; }
		
		if(document.getElementById("type").value == ""){
			alert("类型不能为空，请选择！");
			return;
		}
		if(document.getElementById("pzxm").value == ""){
			alert("调账项目不能为空，请选择！");
			return;
		}		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,100,"往来单位")){	 return; }	
		if(!InputValid(document.getElementById("pzje"),1,"float",1,-999999999,999999999,"调账金额")){	 return; }			
		
		document.pzForm.submit();
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
	
</script>
</head>
<body  onload="initFzrTip();initClientTip();">
<form name="pzForm" action="updatePz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来调账</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="pz.id" id="id" value="<%=StringUtils.nullToStr(pz.getId()) %>" readonly></td>
		<td class="a1" width="15%">日期</td>
		<td class="a2">
			<input type="text" name="pz.pz_date" id="pz_date" value="<%=StringUtils.nullToStr(pz.getPz_date()) %>" class="Wdate" onFocus="WdatePicker()">
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="pz.type" id="type">
				<option value=""></option>
				<option value="应收" <%if(StringUtils.nullToStr(pz.getType()).equals("应收")) out.print("selected"); %>>应收</option>
				<option value="应付" <%if(StringUtils.nullToStr(pz.getType()).equals("应付")) out.print("selected"); %>>应付</option>
			</select>			
		</td>
		<td class="a1" width="15%">调账项目</td>
		<td class="a2" width="35%">
			<select name="pz.pzxm" id="pzxm">
				<option value=""></option>
			<%
			if(pzlx != null && pzlx.length > 0){
				for(int i=0;i<pzlx.length;i++){
			%>
				<option value="<%=pzlx[i] %>" <%if(pzlx[i].equals(StringUtils.nullToStr(pz.getPzxm()))) out.print("selected"); %>><%=pzlx[i] %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>	
		</td>			
	</tr>	
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(pz.getJsr()) %>"/>  
          <div   id="brandTip"  style="height:12px;position:absolute;left:96px; top:112px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="pz.jsr" id="fzr"  value="<%=pz.getJsr()%>"/> <font color="red">*</font>
		</td>	
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="clientName" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(pz.getClient_name())) %>" size="20" onblur="setClientValue();">
		<input type="hidden" name="pz.client_name" id="client_id" value="<%=StringUtils.nullToStr(pz.getClient_name()) %>">
		<div id="clientsTip" style="height:12px;position:absolute;left:385px; top:113px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="pz.pzje" id="pzje" value="<%=JMath.round(pz.getPzje()) %>"></td>		
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="pz.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(pz.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(pz.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
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
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="pz.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(pz.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>