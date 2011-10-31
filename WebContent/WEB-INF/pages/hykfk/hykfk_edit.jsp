<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykzz hykzz = (Hykzz)VS.findValue("hykzz");

String msg = StringUtils.nullToStr(VS.findValue("msg"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发卡管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
function saveInfo(){
	if(document.getElementById("fzr").value == ""){
		alert("经手人不能为空，请填写！");
		return;
	}
	if(document.getElementById("ffjg").value == ""){
		alert("发卡机构不能为空，请选择！");
		return;
	}
	if(document.getElementById("client_id").value == ""){
		alert("会员名称不能为空，请选择！");
		return;
	}
	document.myform.submit();
}	

function selType(vl){
	var obj = document.getElementById("tdHymc");
	if(vl == "到机构"){
		obj.innerHTML = document.getElementById("div_jg").innerHTML;
		initClientTip();
	}else{
		 obj.innerHTML = document.getElementById("div_gr").innerHTML;
	} 
}	
</script>
</head>
<body  onload="initFzrTip();initClientTip();">
<form name="myform" action="updateHykfk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发卡管理</td>
	</tr>
	</thead>
	<%
	if(!msg.equals("")){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=msg %></font></td>
	</tr>
	<%
	}
	%>		
	<tr>
	   <td class="a1" width="15%">会员卡号</td>
	   <td class="a2" width="35%">
		   <input type="text" name="hykda.hykh" id="hykh" value="<%=StringUtils.nullToStr(hykzz.getHykh()) %>" maxlength="50" style="width:200px" readonly>
	   </td>
		<td class="a1" width="15%">发卡机构</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.ffjg" id="ffjg" value="<%=StringUtils.nullToStr(hykzz.getDept()) %>" maxlength="50" style="width:200px"> <font color="red">*</font>
		</td>	   
	</tr>
	<tr>
		<td class="a1">发卡日期</td>
		<td class="a2">
		   <input type="text" name="hykda.fkrq" id="fkrq" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()" maxlength="20" style="width:200px"> <font color="red">*</font>
		</td>
		<td class="a1">经手人</td>
		<td class="a2">		   
		   <input id="brand" type="text" maxlength="20" style="width:200px"onblur="setValue()" value=""> <font color="red">*</font>
           <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="hykda.fkjsr" id="fzr" value=""> 	
		</td>
	</tr>
	<tr>
		<td class="a1">发卡类型</td>
		<td class="a2">
		    <select name="fklx" id="fklx" style="width:200px" onchange="selType(this.value);">
				<option value="到机构">到机构</option>
				<option value="到个人">到个人</option>	
			</select>
		</td>
		<td class="a1">会员名称</td>
		<td class="a2" id="tdHymc">
		   <input type="text" name="hymc" onblur="setClientValue();" id="client_name" maxlength="50" style="width:200px" value=""> <font color="red">*</font>
		   <input type="hidden" name="hykda.hymc" id="client_id" value="">
		   <div id="clientsTip" style="position:absolute;width:250px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>		
	</tr>
	<tr>	
		<td class="a1">会员联系人</td>
		<td class="a2">			  
		   <input type="text" name="hykda.lxrname" id="lxrname" value="" maxlength="20" style="width:200px">
		</td>
		<td class="a1">证件号码</td>
		<td class="a2">
		   <input type="text" name="hykda.sfzh" id="sfzh" value="" maxlength="20" style="width:200px">
		</td>		
	</tr>
	<tr>
		<td class="a1">性别</td>
		<td class="a2">
		    <select name="hykda.sex" id="sex" style="width:200px">
				<option value="男">男</option>
				<option value="女">女</option>	
			</select>
		</td>					

		<td class="a1">出生日期</td>
		<td class="a2">
		   <input type="text" name="hykda.birth" id="birth" value="" class="Wdate" onFocus="WdatePicker()" maxlength="20" style="width:200px">
		</td>		
	</tr>
	<tr>
		<td class="a1">联系电话</td>
		<td class="a2">
		<input type="text" name="hykda.lxdh" id="lxdh" value="" maxlength="20" style="width:200px">
		</td>
		<td class="a1">手机</td>
		<td class="a2">
		   <input type="text" name="hykda.mobile" id="mobile" maxlength="20" style="width:200px">
		</td>
	</tr>
	<tr>
		<td class="a1">E-Mail</td>
		<td class="a2">
		   <input type="text" name="hykda.mail" id="mail" value="" maxlength="100" style="width:200px">
		</td>
		<td class="a1">单位名称</td>
		<td class="a2">
		   <input type="text" name="hykda.gzdw" id="gzdw" value="" maxlength="100" style="width:200px">
		</td>
	</tr>
	<tr>
		<td class="a1">地址</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.address" id="address" value="" maxlength="100" style="width:563px">
		</td>
	</tr>	
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.fkbz" id="fkbz" value="" maxlength="500" style="width:563px">
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="确认发卡" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
<div id="div_jg" style="display:none">
	<input type="text" name="hymc" onblur="setClientValue();" id="client_name" maxlength="50" style="width:200px" value=""> <font color="red">*</font>
	<input type="hidden" name="hykda.hymc" id="client_id" value="">
	<div id="clientsTip" style="position:absolute;width:250px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>	
</div>
<div id="div_gr" style="display:none">
	<input type="text" name="hykda.hymc" id="client_id" value="" maxlength="50" style="width:200px" > <font color="red">*</font>
</div>