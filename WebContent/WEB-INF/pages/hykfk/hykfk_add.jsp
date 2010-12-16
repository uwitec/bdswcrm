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
		
		document.myform.submit();
	}	
	
	
	
	function chgFkTyle(vD){

		if(vD == "") vD = "到机构";
		var obj_jg1 = document.getElementById("jg1");
		var obj_jg2 = document.getElementById("jg2");
		var obj_ly1 = document.getElementById("ly1");
		var obj_ly2 = document.getElementById("ly2");
		var obj_lxdh1=document.getElementById("lxdh1");
		var obj_lxdh2=document.getElementById("lxdh2");
		var obj_m1 = document.getElementById("m1");
		var obj_m2 = document.getElementById("m2");
		var obj_sf1 = document.getElementById("sf1");
		var obj_sf2 = document.getElementById("sf2");
		var obj_dz1=document.getElementById("dz1");
		var obj_dz2=document.getElementById("dz2");
		if(vD == "到个人"){
		    obj_jg1.style.display = "none";
			obj_jg2.style.display = "none";
			obj_ly1.style.display = "";
			obj_ly2.style.display = "";
			obj_lxdh1.style.display = "";
			obj_lxdh2.style.display = "";
			obj_m1.style.display = "";
			obj_m2.style.display = "";
			obj_sf1.style.display = "";
			obj_sf2.style.display = "";
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
		}else if(vD == "到机构"){
		    obj_jg1.style.display = "";
			obj_jg2.style.display = "";
			obj_ly1.style.display = "none";
			obj_ly2.style.display = "none";
			obj_lxdh1.style.display = "none";
			obj_lxdh2.style.display = "none";
			obj_m1.style.display = "none";
			obj_m2.style.display = "none";
			obj_sf1.style.display = "none";
			obj_sf2.style.display = "none";
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
	}
	}
</script>

</head>
<body  onload="initFzrTip();onloadClientInfo();">
<form name="myform" action="saveHykfk.html" method="post">
<input type="hidden" name="hykda.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(hykda.getLxdh()) %>">
<input type="hidden" name="hykda.mobile" id="mobile" value="<%=StringUtils.nullToStr(hykda.getMobile()) %>">
<input type="hidden" name="hykda.address" id="address" value="<%=StringUtils.nullToStr(hykda.getAddress()) %>">
<input type="hidden" name="hykda.mail" id="mail" value="<%=StringUtils.nullToStr(hykda.getMail()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发卡管理</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">会员卡号</td>
	   <td class="a2" width="35%" colspan="3">
		   <input type="text" name="hykda.hykh" id="hykh" value="<%=StringUtils.nullToStr(hykda.getHykh()) %>" size="45" maxlength="50" readonly>
	   </td>
	</tr>
	
	<tr>
	    <%
		String rq = StringUtils.nullToStr(hykda.getFkrq()) ;
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1" width="15%">发卡日期</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.fkrq" id="fkrq" value="<%=rq%>"  class="Wdate" onFocus="WdatePicker()"  size="45"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">		   
		   <input  id="brand"  type="text"    size="45" length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(hykda.getFkjsr()) %>"> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="hykda.fkjsr" id="fzr" value="<%=StringUtils.nullToStr(hykda.getFkjsr()) %>"> 	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">发卡机构</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.ffjg" id="ffjg" value=""   size="45"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">发卡类型</td>
		<td class="a2" width="35%" >
		   <select name="fklx" id="fklx" onchange="chgFkTyle(this.value);" style="width:256px">
			<option value="到机构" >到机构</option>
			<option value="到个人" >到个人</option>	
		</select>
		</td>
	</tr>
	<tr>	
	  
		<td class="a1" width="15%" id="jg1">领用机构</td>
		<td class="a2" colspan="3" id="jg2">
		   <input type="text" name="hykda.hymc" onblur="setClientValue();" id="client_name"  size="45" value="<%=StringUtils.nullToStr(hykda.getHymc()) %>">
		   <input type="hidden" name="hykda.hybh" id="client_id" value="">
		   <div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <font color="red">*</font>		  
		</td>
		
		<td class="a1" width="15%" id="ly1" style="display:none">领用人</td>
		<td class="a2" colspan="3" id="ly2" style="display:none">			  
		   <input type="text" name="hykda.lxrname" id="lxrname" value="" size="45" maxlength="30"><font color="red">*</font> 
		</td>	
	  
	</tr>
	<tr>
		<td class="a1" width="15%" style="display:none" id="lxdh1">联系电话</td>
		<td class="a2" width="35%"  style="display:none" id="lxdh2">
		<input type="text" name="hykda.lxdh" id="lxdh" value="" size="45" >
		</td>
	
		<td class="a1" width="15%"  style="display:none" id="m1">手机</td>
		<td class="a2" width="35%"  style="display:none" id="m2">
		   <input type="text" name="hykda.mobile" id="mobile" value=""  size="45" >
		</td>
	</tr>
	 
	<tr>
		<td class="a1" width="15%"   style="display:none" id="sf1">身份证号</td>
		<td class="a2" width="35%"   style="display:none" id="sf2">
		   <input type="text" name="hykda.sfzh" id="sfzh" value=""   size="45" >
		</td>
	
		<td class="a1" width="15%"    style="display:none" id="dz1">地址</td>
		<td class="a2" width="15%"   style="display:none" id="dz2">
		   <input type="text" name="hykda.address" id="address" value=""   size="45" maxlength="80">
		</td>
	</tr>	
	

	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.fkbz" id="fkbz" value=""   size="90">
		</td>
	</tr>	
   <tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
