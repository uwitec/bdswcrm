<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Nbzz nbzz = (Nbzz)VS.findValue("nbzz");

List userList = (List)VS.findValue("userList");

String message = "";
List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
if(msg != null && msg.size() > 0){
	message = StringUtils.nullToStr(msg.get(0));
}

%>

<html>
<head>
<title>内部转账</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,50,"编号")){	 return; }
				
		if(document.getElementById("zczh").value == ""){
			alert("转出账户不能为空，请选择！");
			return;
		}
		if(document.getElementById("zrzh").value == ""){
			alert("转入账户不能为空，请选择！");
			return;
		}
		if(document.getElementById("zczh").value == document.getElementById("zrzh").value){
			alert("转入账户与转出账户相同，无法转账，请确认！");
			return;		
		}		
		if(!InputValid(document.getElementById("zzje"),1,"float",1,0,999999999,"转账金额")){	 return; }					
		if(document.getElementById("jsr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}			
		
		document.nbzzForm.submit();
	}

	
	function openZczh(){
		var destination = "selZczh.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function openZrzh(){
		var destination = "selZrzh.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}		
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="nbzzForm" action="updateNbzz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">内部转账</td>
	</tr>
	</thead>
	<%if(!message.equals("")){ %>
	<tr>
		<td class="a2" colspan="4"><font color="red"><%=message %></font></td>		
	</tr>	
	<%} %>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="nbzz.id" id="id" value="<%=StringUtils.nullToStr(nbzz.getId()) %>" readonly></td>
		<td class="a1" width="15%">转账日期</td>
		<td class="a2">
			<input type="text" name="nbzz.zz_date" id="zz_date" value="<%=StringUtils.nullToStr(nbzz.getZz_date()) %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('sr_date')); return false;">
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">转出账户</td>
		<td class="a2" width="35%"><input type="text" id="zczh_name"  name="zczh_name" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(nbzz.getZczh())) %>" readonly>
		<input type="hidden" id="zczh"  name="nbzz.zczh" value="<%=StringUtils.nullToStr(nbzz.getZczh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openZczh();" style="cursor:hand">
		</td>
		<td class="a1" width="15%">转入账户</td>
		<td class="a2" width="35%"><input type="text" id="zrzh_name"  name="zrzh_name" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(nbzz.getZrzh())) %>" readonly>
		<input type="hidden" id="zrzh"  name="nbzz.zrzh" value="<%=StringUtils.nullToStr(nbzz.getZrzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openZrzh();" style="cursor:hand">
		</td>
	</tr>	
	<tr>					
		<td class="a1" width="15%">转账金额</td>
		<td class="a2" width="35%"><input type="text" name="nbzz.zzje" id="zzje" value="<%=JMath.round(nbzz.getZzje()) %>"></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
			<select name="nbzz.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>" <%if(StringUtils.nullToStr(nbzz.getJsr()).equals(strid)) out.print("selected"); %>><%=name %></option>
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
			<select name="nbzz.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(nbzz.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(nbzz.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
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
			<textarea rows="3" cols="50" name="nbzz.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(nbzz.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
