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
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,50,"编号")){	 return; }
		if(document.getElementById("type").value == ""){
			alert("收入类型不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("skje"),1,"float",1,0,999999999,"收款金额")){	 return; }
		
		if(document.getElementById("skzh").value == ""){
			alert("收款账号不能为空，请选择！");
			return;
		}	
		if(document.getElementById("jsr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}			
		
		document.qtsrForm.submit();
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="qtsrForm" action="updateQtsr.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其它收入</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="qtsr.id" id="id" value="<%=StringUtils.nullToStr(qtsr.getId()) %>" readonly></td>
		<td class="a1" width="15%">收入日期</td>
		<td class="a2">
			<input type="text" name="qtsr.sr_date" id="sr_date" value="<%=StringUtils.nullToStr(qtsr.getSr_date()) %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('sr_date')); return false;">
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
			</select>			
		</td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtsr.skje" id="skje" value="<%=JMath.round(qtsr.getSkje()) %>"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtsr.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="qtsr.skzh" value="<%=StringUtils.nullToStr(qtsr.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
			<select name="qtsr.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>" <%if(StringUtils.nullToStr(qtsr.getJsr()).equals(strid)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>		
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="qtsr.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(qtsr.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(qtsr.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
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
			<textarea rows="3" cols="50" name="qtsr.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(qtsr.getRemark()) %></textarea>
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
