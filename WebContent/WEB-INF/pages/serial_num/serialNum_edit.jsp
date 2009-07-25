<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>序列号管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">
	//保存序列号
	function saveInfo(){
		if(!InputValid(document.getElementById("serial_num"),1,"string",1,1,50,"序列号")){	 return; }
		if(!InputValid(document.getElementById("product_name"),0,"string",0,1,100,"产品名称")){	 return; }
		if(document.getElementById("state").value == ""){
			alert("序列号状态不能为空，请选择");
			return;
		}
		document.serialNumForm.submit();
	}
	
	//选择产品列表
	function openWin(){
		var destination = "getProducts.html";
		var fea ='width=800,height=450,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-450)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'选择产品',fea);	
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = document.getElementById("serial_num").value;
		if(serialNum == ""){
			return;
		}
		dwrService.chkSerialNumIsExist(serialNum,setMsg);		
	}
	
	//处理返回值
	function setMsg(data){
		if(data != null){
			if(data == true){
				document.getElementById("msg").innerText = "序列号已存在";
			}
		}
	}
</script>
</head>
<body>
<form name="serialNumForm" action="updateSerialNum.html" method="post">
<ww:hidden name="serialNumMng.product_id" id="product_id" value="%{serialNumMng.product_id}" theme="simple"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">序列号管理</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">序列号</td>
		<td class="a2" width="75%">
			<ww:textfield name="serialNumMng.serial_num" id="serial_num" value="%{serialNumMng.serial_num}" theme="simple" onblur="sendSerialNum();"/>
			<span id="msg" style="color:red"></span>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="25%">产品名称</td>
		<td class="a2" width="75%">
			<ww:textfield  name="serialNumMng.product_name" id="product_name" value="%{serialNumMng.product_name}" theme="simple" cssStyle="width:75%" readonly="true"/>
			<img src="images/select.gif" align="absmiddle" title="选择产品" border="0" onclick="openWin();" style="cursor:hand">
		</td>
	</tr>
	<tr>
		<td class="a1" width="25%">产品规格</td>
		<td class="a2" width="75%"><ww:textfield  name="serialNumMng.product_xh" id="product_xh" value="%{serialNumMng.product_xh}"  theme="simple"  readonly="true"  cssStyle="width:75%"/></td>
	</tr>
	<tr>
		<td class="a1" width="25%">状态</td>
		<td class="a2" width="75%">
			<ww:select name="serialNumMng.state" id="state" theme="simple" list="#{'在库':'在库','已售':'已售','已退货':'已退货'}"></ww:select>		
		</td>		
	</tr>
	<tr>
		<td class="a1" width="25%">所在库房</td>
		<td class="a2" width="75%">
			<ww:select name="serialNumMng.store_id" id="store_id" theme="simple" list="%{storeList}" listValue="name" listKey="id" emptyOption="true"></ww:select>		
		</td>		
	</tr>
	<ww:set name="set_yj_flag" value="serialNumMng.yj_flag"/>
	<tr>
		<td class="a1" width="25%">是否样机</td>
		<td class="a2" width="75%">
			<ww:select name="serialNumMng.yj_flag" id="yj_flag" theme="simple" list="#{'0':'否','1':'是'}"></ww:select> 
		</td>		
	</tr>			
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重置" class="css_button">&nbsp;&nbsp;
			<input type="button" name="button1" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
注：<br>
&nbsp;&nbsp;1、如果序列号不存在，则添加。<br>
&nbsp;&nbsp;2、如果序列号存在，也可提交数据，系统将自动更新序列相关属必。
</body>
</html>
