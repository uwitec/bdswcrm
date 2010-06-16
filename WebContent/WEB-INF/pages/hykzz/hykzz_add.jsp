<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykzz hykzz = (Hykzz)VS.findValue("hykzz");
List hykflList = (List)VS.findValue("hykflList");
%>
<html>
<head>
<title>会员卡制作</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" charset="UTF-8"  src="xhEditor/xheditor.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	
	function saveInfo(){
		if(document.getElementById("dept").value == ""){
			alert("制卡单位不能为空，请填写！");
			return;
		}
	   
	   		
		if(document.getElementById("ssfl").value == ""){
			alert("所属分类不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("hykh").value == ""){
			alert("会员卡号不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("yxrq").value == ""){
			alert("有效日期不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("sxrq").value == ""){
			alert("失效日期不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("zzfs").value == "批量增加"){
		if(!InputValid(document.getElementById("hykh"),1,"int",1,1,9999999999,"会员卡号")){	 return; }
		}	
		document.myform.submit();
	}	
	
	function chgZzTyle(vD){

		if(vD == "") vD = "单个增加";
		var obj_sl1 = document.getElementById("sl1");
		var obj_sl2 = document.getElementById("sl2");
		
		if(vD == "单个增加"){
		    obj_sl1.style.display = "none";
			obj_sl2.style.display = "none";
		}else if(vD == "批量增加"){
		    obj_sl1.style.display = "";
			obj_sl2.style.display = "";
	}
	}
</script>
</head>
<body >
<form name="myform" action="saveHykzz.html" method="post">

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">制卡单位</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykzz.dept" id="dept" value="" size="45"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">卡类型</td>
	    <td class="a2" colspan="3">
	        <input type="radio"  name="hykzz.card_type" value="条码卡" checked="checked">条码卡
	        <input type="radio"  name="hykzz.card_type" value="磁卡" >磁卡
	        <input type="radio"  name="hykzz.card_type" value="IC卡" >IC卡
	         <font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">所属分类</td>
		<td class="a2" colspan="3">
		  <select name="hykzz.ssfl" id="ssfl" style="width:256px">
				<option value=""></option>
				<%
				if(hykflList != null && hykflList.size()>0){
					for(int i=0;i<hykflList.size();i++){
						Hykfl hykfl = (Hykfl)hykflList.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(hykfl.getId()) %>"><%=StringUtils.nullToStr(hykfl.getName()) %></option>
				<%
					}
				}
				%>
			</select><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">制作方式</td>
	    <td class="a2">
        <select name="zzfs" id="zzfs" onchange="chgZzTyle(this.value);" style="width:232px" colspan="3">
			<option value="单个增加" >单个增加</option>
			<option value="批量增加" >批量增加</option>	
		</select>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykzz.hykh" id="hykh" value="" size="40" maxlength="50"><font color="red">*</font>
		</td>
	</tr>
	<tr>	
		<td class="a1" width="15%" id="sl1" style="display:none">数量</td>
		<td class="a2" id="sl2"  colspan="3" style="display:none">
		   <input type="text" name="num" id="num" value="1" maxlength="30" size="20">
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">有效日期</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykzz.yxrq" id="yxrq" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()" size="25"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">失效日期</td>
	    <td class="a2" colspan="3">
	       <input type="text" name="hykzz.sxrq" id="sxrq" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()" size="25"><font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">初始积分</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykzz.csjf" id="csjf" value="0"  size="25">
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">初始密码</td>
	    <td class="a2" colspan="3">
	       <input type="text" name="hykzz.csmm" id="csmm" value=""  size="25">
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
