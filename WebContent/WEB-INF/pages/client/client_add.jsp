<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] wldwlx = (String[])VS.findValue("wldwlx");
List userList = (List)VS.findValue("userList");
%>
<html>
<head>
<title>客户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,50,"单位名称")){	 return; }
		
		if(document.getElementById("client_type").value == ""){
			alert("单位类型不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("khjl").value == ""){
			alert("客户经理不能为空，请选择！");
			return;
		}		
		
		if(!InputValid(document.getElementById("zq"),0,"int",0,1,999,"帐期")){	 return; }
		if(!InputValid(document.getElementById("xe"),0,"float",999999999,"限额")){	 return; }	
		
		
		document.clientForm.submit();
	}	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="clientForm" action="saveClient.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来单位</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">单位名称</td>
		<td class="a2" width="35%"><input type="text" name="client.name" id="name" value=""><font color="red">*</font></td>
		<td class="a1" width="15%">单位类型</td>
		<td class="a2" width="35%">
			<select name="client.client_type" id="client_type">
				<option value=""></option>
				<%
				if(wldwlx != null && wldwlx.length > 0){ 
					for(int i=0;i<wldwlx.length;i++){
				%>
				<option value="<%=wldwlx[i] %>"><%=wldwlx[i] %></option>
				<%
					}
				}
				%>
			</select>
			<font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="client.lxr" id="lxr" value="" maxlength="10"></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="client.lxdh" id="lxdh" value="" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="client.mobile" id="mobile" value="" maxlength="20"></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="client.address" id="address" value="" maxlength="50"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">客户经理</td>
		<td class="a2" width="35%">
			<select name="client.khjl" id="khjl">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(map.get("user_id")) %>"><%=StringUtils.nullToStr(map.get("real_name")) %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="client.mail" id="mail" value="" maxlength="50"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="client.msn" id="msn" value="" maxlength="20"></td>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="client.qq" id="qq" value="" maxlength="20"></td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">账期</td>
		<td class="a2" width="35%"><input type="text" name="client.zq" id="zq" value="0" size="5"> 天<font color="red">*</font>	</td>
		<td class="a1" width="15%">限额</td>
		<td class="a2" width="35%"><input type="text" name="client.xe" id="xe" value="0.00">元<font color="red">*</font>	</td>
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
			<textarea rows="3" cols="50" name="client.remark" id="remark" style="width:80%" maxlength="500"></textarea>
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
