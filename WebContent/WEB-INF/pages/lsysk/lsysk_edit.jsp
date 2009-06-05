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
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,50,"客户名称")){	 return; }
		if(!InputValid(document.getElementById("lxdh"),1,"string",1,1,20,"联系电话")){	 return; }
		if(document.getElementById("jsr").value == ""){
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
		
		document.lsyskForm.submit();
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
	
	function selFkfs(vl){
		if(vl == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
	}	
	
</script>
</head>
<body>
<form name="lsyskForm" action="updateLsysk.html" method="post">
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
			<input type="text" name="lsysk.ys_date" id="ys_date" value="<%=StringUtils.nullToStr(lsysk.getYs_date()) %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('ys_date')); return false;">
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
			<select name="lsysk.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>" <%if(StringUtils.nullToStr(lsysk.getJsr()).equals(strid)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">预收金额</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.ysje" id="ysje" value="<%=JMath.round(lsysk.getYsje()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %>" readonly><font color="red">*</font>
		<input type="hidden" id="skzh"  name="lsysk.skzh" value="<%=StringUtils.nullToStr(lsysk.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
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
			<select name="lsysk.pos_id" id="pos_id" style="<%=cssStyle %>">
				<option value="">选择刷卡POS机</option>
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
	</tr>
	<tr>				
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="lsysk.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(lsysk.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(lsysk.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
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
			<textarea rows="3" cols="50" name="lsysk.remark" id="remark" style="width:90%" maxlength="500"><%=StringUtils.nullToStr(lsysk.getRemark()) %></textarea>
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
