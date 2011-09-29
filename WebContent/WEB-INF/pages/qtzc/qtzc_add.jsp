<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String id = (String)VS.findValue("id");

String[] zclx = (String[])VS.findValue("zclx");

List userList = (List)VS.findValue("userList");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,50,"编号")){	 return; }
		if(!InputValid(document.getElementById("zc_date"),1,"string",1,1,50,"支出日期")){	 return; }
		if(document.getElementById("type").value == ""){
			alert("支出类型不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("zcje"),1,"float",1,0,999999999,"支出金额")){	 return; }
		
		if(document.getElementById("skzh").value == ""){
			alert("支出账号不能为空，请选择！");
			return;
		}	
		if(document.getElementById("jsr").value == ""){
			alert("出纳不能为空，请选择！");
			return;
		}
		if(document.getElementById("ywy").value == ""){
			alert("业务员不能为空，请选择！");
			return;
		}					
		
		document.qtzcForm.submit();
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="qtzcForm" action="saveQtzc.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">一般费用</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.id" id="id" value="<%=id %>" readonly><font color="red">*</font></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2">
			<input type="text" name="qtzc.zc_date" id="zc_date" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="qtzc.type" id="type">
				<option value=""></option>
			<%
			if(zclx != null && zclx.length>0){
				for(int i=0;i<zclx.length;i++){
			%>
			<option value="<%=zclx[i] %>"><%=zclx[i] %></option>
			<%		
				}
			}
			%>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.zcje" id="zcje" value=""><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="" readonly>
		<input type="hidden" id="skzh"  name="qtzc.zczh" value=""><font color="red">*</font>
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1" width="15%">出纳</td>
		<td class="a2">
			<select name="qtzc.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>"><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">业务员</td>
		<td class="a2">
			<select name="qtzc.ywy" id="ywy">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>"><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%">客户或项目</td>
		<td class="a2" width="35%"><input type="text" id="zcxm"  name="qtzc.zcxm" value="" size="15" maxlength="100">				
	</tr>	
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="qtzc.state" id="state">
				<option value="已保存">已保存</option>
				<option value="已提交">已提交</option>
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
			<textarea rows="2" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500"></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="草 稿" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
