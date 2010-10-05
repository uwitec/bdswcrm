<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

SysUser user = (SysUser)VS.findValue("user");
List storeList = (List)VS.findValue("storeList");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");

%>

<html>
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src="js/selJsr.js"></script>
<style>
	.selectTip{
		background-color:#009;color:#fff;
	}
</style>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("user_name"),1,"string",1,1,20,"登录名")){	 return; }
		if(!InputValid(document.getElementById("real_name"),1,"string",1,1,20,"真实姓名")){	 return; }
		
		if(!InputValid(document.getElementById("password"),1,"string",1,1,20,"密码")){	 return; }
		if(!InputValid(document.getElementById("password2"),1,"string",1,1,20,"密码确认")){	 return; }
		
		if(document.getElementById("password").value != document.getElementById("password2").value){
			alert("密码与密码确认不同，请检查！");
			return;
		}
		
		if(document.getElementById("is_dls").value == "1"){
			//用户类型：代理商
			if(document.getElementById("client_id").value == ""){
				alert("代理商不能为空，请选择！");
				return;
			}			
		}else if(document.getElementById("is_dls").value == "2"){
			//用户类型：供应商
			if(document.getElementById("product_kind").value == ""){
				alert("供应商品类别不能为空，请选择！");
				return;
			}			
		}else{
			//用户类型：业务员
			if(document.getElementById("fzr").value == ""){
				alert("业务员不能为空，请选择！");
				return;
			}
		}

		//所在库房不能为空
		if(document.getElementById("szkf").value == ""){
			alert("所在库房不能为空，请选择！");
			return;
		}
		document.userForm.submit();
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openProductKindWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}
	
	function selType(vl){
		var obj = document.getElementById("selUserType");
		var obj2 = document.getElementById("selName");
		if(vl == "1"){
			obj.innerHTML = document.getElementById("div_dls").innerHTML;
			obj2.innerHTML = "代理商";
			document.getElementById("trSzkf").style.display = "none";	
		}else if(vl == "2"){
			obj.innerHTML = document.getElementById("div_gys").innerHTML;
			obj2.innerHTML = "供应商品类别";
			document.getElementById("trSzkf").style.display = "none";	
		}else{
			 obj.innerHTML = document.getElementById("div_ywy").innerHTML;
			 obj2.innerHTML = "业务员";
			 document.getElementById("trSzkf").style.display = "";	
			 initFzrTip();
		} 
	}		
</script>
</head>
<body onload="initFzrTip();">
<form name="userForm" action="saveUser.html" method="post">
<center>
<font color="red" style="font-size:16px;"><%=msg %></font>
</center>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">用户信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">登录名</td>
		<td class="a2" width="35%"><input type="text" name="user.user_name" style="width:150px" id="user_name" value="<%=StringUtils.nullToStr(user.getUser_name()) %>"><font color="red">*</font></td>
		<td class="a1" width="15%">真实姓名</td>
		<td class="a2" width="35%"><input type="text" name="user.real_name" style="width:150px" id="real_name" value="<%=StringUtils.nullToStr(user.getReal_name()) %>"><font color="red">*</font></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">密码</td>
		<td class="a2" width="35%"><input type="password" name="user.password" style="width:150px" id="password" value="<%=StringUtils.nullToStr(user.getPassword()) %>"><font color="red">*</font></td>
		<td class="a1" width="15%">密码确认</td>
		<td class="a2" width="35%"><input type="password" name="user.password2" style="width:150px" id="password2" value="<%=StringUtils.nullToStr(user.getPassword()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%"><input type="text" name="user.gs_phone" style="width:150px" id="gs_phone" value="<%=StringUtils.nullToStr(user.getGs_phone()) %>" maxlength="10"></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="user.mobile" style="width:150px" id="mobile" value="<%=StringUtils.nullToStr(user.getMobile()) %>" maxlength="20"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">用户类别</td>
		<td class="a2" width="35%">
			<select name="user.is_dls" id="is_dls" onchange="selType(this.value);" style="width:150px">
				<option value="0">业务员(普通)</option>
				<option value="3">业务员(零售)</option>
				<option value="4">业务员(渠道)</option>
				<option value="1">代理商</option>
				<option value="2">供应商</option>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%" id="selName">业务员</td>
		<td class="a2" width="35%" id="selUserType">
			<input id="brand" type="text" style="width:150px" onblur="setValue();" value=""/><font color="red">*</font>
			<div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<input type="hidden" name="user.client_name" id="fzr" value=""/>		
		</td>		
	</tr>
	<tr id="trSzkf">
		<td class="a1" width="15%">所在库房</td>
		<td class="a2" colspan="3">
			<select name="user.szkf" id="szkf" multiple="multiple" style="width:250px;height:140px;">
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(storeHouse.getId()) %>"><%=StringUtils.nullToStr(storeHouse.getName()) %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font><BR>注:选择多个、取消选择请按住CTRL键点击鼠标左键。
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
<div id="div_ywy" style="display:none">
	<input id="brand" type="text" style="width:150px" onblur="setValue();" value=""/><font color="red">*</font>
	<div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
	<input type="hidden" name="user.client_name" id="fzr" value=""/>		
</div>
<div id="div_dls" style="display:none">
	<input type="text" name="user.client_id" id="client_name" value="" onclick="openClientWin();" style="width:150px" maxlength="50" readonly>
	<input type="hidden" name="user.client_name" id="client_id" value="">
	<img src="images/select.gif" align="absmiddle" title="点击选择代理商" border="0" onclick="openClientWin();" style="cursor:hand"><font color="red">*</font>
</div>
<div id="div_gys" style="display:none">
	<input type="text" name="kind_name" id="kind_name" value="" style="width:150px" onclick="openProductKindWin();" readonly>
	<input type="hidden" name="user.client_name" id="product_kind" value="">
	<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openProductKindWin();" style="cursor:hand"><font color="red">*</font>
</div>