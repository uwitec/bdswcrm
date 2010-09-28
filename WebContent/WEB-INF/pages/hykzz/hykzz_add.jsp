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
<script language="JavaScript" src="js/validateform.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if (doValidate(myform)){	
			if(document.getElementById("zzfs").value == "单个增加"){
				if(document.getElementById("hykh_a").value == "") alert("会员卡号不能为空!");
				document.getElementById("hykh").value = document.getElementById("hykh_a").value;
			}else{
				if(document.getElementById("hykh_f").value == "" || document.getElementById("hykh_b").value == "") alert("会员卡号不能为空!");
				if(document.getElementById("nums").value == "") alert("数量不能为空!");
				document.getElementById("hykh").value = document.getElementById("hykh_f").value + "%" +document.getElementById("hykh_b").value;
			}	
			document.myform.submit();
		}
	}	
	
	function chgZzTyle(vD){
		if(vD == "") vD = "单个增加";
		var obj_sl1 = document.getElementById("pl1");
		var obj_sl2 = document.getElementById("pl2");
		var obj_dg = document.getElementById("dg");
		
		if(vD == "单个增加"){
		    obj_sl1.style.display = "none";
			obj_sl2.style.display = "none";
			obj_dg.style.display = "";
		}else if(vD == "批量增加"){
		    obj_sl1.style.display = "";
			obj_sl2.style.display = "";
			obj_dg.style.display = "none";
		}
	}
</script>
</head>
<body onload="chgZzTyle('');">
<form name="myform" action="saveHykzz.html" method="post">
<input type="hidden" name="hykzz.hykh" id="hykh" value=""/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">制卡单位</td>
		<td class="a2" width="75%">
		   <input type="text" name="hykzz.dept" id="dept" value="" style="width:200px" maxlength="25" notNull='true' vdisp='制卡单位'  vtype='string'><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">卡类型</td>
	    <td class="a2">
	        <input type="radio"  name="hykzz.card_type" value="条码卡" checked="checked">条码卡
	        <input type="radio"  name="hykzz.card_type" value="磁卡" >磁卡
	        <input type="radio"  name="hykzz.card_type" value="IC卡" >IC卡
	         <font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1">所属分类</td>
		<td class="a2" >
		  <select name="hykzz.ssfl" id="ssfl" style="width:200px" notNull='true' vdisp='所属分类'  vtype='string'>
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
		<td class="a1">制作方式</td>
	    <td class="a2">
        <select name="zzfs" id="zzfs" onchange="chgZzTyle(this.value);" style="width:200px">
			<option value="单个增加" >单个增加</option>
			<option value="批量增加" >批量增加</option>	
		</select>
		</td>
	</tr>
	<tr id="dg">
		<td class="a1">会员卡号</td>
		<td class="a2">
		   <input type="text" name="hykh_a" id="hykh_a" value="" style="width:200px" notNull='false' vdisp='会员卡号'  vtype='notsstring' maxlength="50"><font color="red">*</font>
		</td>
	</tr>
	<tr id="pl1">
		<td class="a1">会员卡号</td>
		<td class="a2">
		   <input type="text" name="hykh_f" id="hykh_f" value="" style="width:100px" notNull='false' title='会员卡号--前缀' vdisp='会员卡号--前缀'  vtype='notsstring' maxlength="40">
		   <input type="text" name="hykh_b" id="hykh_b" value="" style="width:85px" notNull='false' title='会员卡号--序列' vdisp='会员卡号--序列'  vtype='NumStr' maxlength="10">
		   <font color="red">*</font>
		</td>
	</tr>	
	<tr id="pl2">	
		<td class="a1">数量</td>
		<td class="a2">
		   <input type="text" name="nums" id="nums" value="1" style="width:200px" notNull='false' vdisp='会员卡号'  vtype='int' maxlength="9">
		</td>		
	</tr>
	<tr>
		<td class="a1">有效日期</td>
		<td class="a2">
		   <input type="text" name="hykzz.yxrq" id="yxrq" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()" style="width:200px"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1" >失效日期</td>
	    <td class="a2">
	       <input type="text" name="hykzz.sxrq" id="sxrq" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()" style="width:200px"><font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1">初始积分</td>
		<td class="a2">
		   <input type="text" name="hykzz.csjf" id="csjf" value="0" style="width:200px" notNull='false' vdisp='初始积分'  vtype='int' maxlength="9">
		</td>
	</tr>
	<tr>
		<td class="a1">初始密码</td>
	    <td class="a2">
	       <input type="text" name="hykzz.csmm" id="csmm" value="" style="width:200px" notNull='false' vdisp='初始密码'  vtype='string' maxlength="20">
        </td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重置" class="css_button">&nbsp;
			<input type="button" name="button3" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
注：批量制卡时，前缀不变，序列自动增加。
</body>
</html>
