<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykda hykda = (Hykda)VS.findValue("hykda");

%>
<html>
<head>
<title>发卡管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="js/selSqr.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
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
		
		
		if(document.getElementById("hykh").value == ""){
			alert("会员卡号不能为空，请选择！");
			return;
		}	
		
		document.myform.submit();
	}	
	
	function chgScTyle(vD){

		if(vD == "") vD = "单个";
		var obj_sl1 = document.getElementById("sl1");
		var obj_sl2 = document.getElementById("sl2");
		
		if(vD == "单个"){
		    obj_sl1.style.display = "none";
			obj_sl2.style.display = "none";
		}else if(vD == "批量"){
		    obj_sl1.style.display = "";
			obj_sl2.style.display = "";
	}
	}
	
	function chgFkTyle(vD){

		if(vD == "") vD = "到机构";
		var obj_jg1 = document.getElementById("jg1");
		var obj_jg2 = document.getElementById("jg2");
		var obj_ly1 = document.getElementById("ly1");
		var obj_ly2 = document.getElementById("ly2");
		
		
		if(vD == "到个人"){
		    obj_jg1.style.display = "none";
			obj_jg2.style.display = "none";
			obj_ly1.style.display = "";
			obj_ly2.style.display = "";
		}else if(vD == "到机构"){
		    obj_jg1.style.display = "";
			obj_jg2.style.display = "";
			obj_ly1.style.display = "none";
			obj_ly2.style.display = "none";
	}
	}
</script>
</head>
<body  onload="initFzrTip();initClientTip();onloadClientInfo();">
<form name="myform" action="saveHykfk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发卡管理</td>
	</tr>
	</thead>
	<tr>
	   
		<td class="a1" width="15%">发卡日期</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.fkrq" id="fkrq" value="<%=DateComFunc.getToday()%>"  class="Wdate" onFocus="WdatePicker()"  size="20"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">		   
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value=""/> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="hykda.fkjsr" id="fzr" value=""> 	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">发卡机构</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.ffjg" id="ffjg" value=""   size="45"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">发卡类型</td>
		<td class="a2" width="35%" >
		   <select name="fklx" id="fklx" onchange="chgFkTyle(this.value);" style="width:232px" >
			<option value="到机构" >到机构</option>
			<option value="到个人" >到个人</option>	
		</select>
		</td>
	</tr>
	<tr>	
		<td class="a1" width="15%" id="jg1">领用机构</td>
		<td class="a2" colspan="3" id="jg2">
		   <input type="text" name="hykda.hymc" onblur="setClientValue();" id="client_name"  size="60" value="">
		   <input type="hidden" name="hykda.hybh" id="client_id" value="">
		   <div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <font color="red">*</font>		  
		</td>
		
		<td class="a1" width="15%" id="ly1" style="display:none">领用人</td>
		<td class="a2" colspan="3" id="ly2" style="display:none">			  
		   <input type="text" name="hykda.lxrname" id="lxrname" value="" size="20" maxlength="30"><font color="red">*</font> 
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">生成方式</td>
	    <td class="a2"  colspan="3">
        <select name="scfs" id="scfs" onchange="chgScTyle(this.value);" style="width:232px" colspan="3">			
			<option value="批量">批量</option>	
			<option value="单个">单个</option>
		</select>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.hykh" id="hykh" value="" size="40" maxlength="50"><font color="red">*</font>
		</td>
		
		<td class="a1" width="15%" id="sl1">数量</td>
		<td class="a2" id="sl2"  width="35%">
		   <input type="text" name="num" id="num" value="1" maxlength="30" size="20">
		</td><font color="red">*</font>		
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.fkbz" id="fkbz" value=""   size="85">
		</td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
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
