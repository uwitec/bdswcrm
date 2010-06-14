<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map hykdas = (Map)VS.findValue("hykdas");

List hykflList = (List)VS.findValue("hykflList");
%>

<html>
<head>
<title>会员卡档案</title>
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
		if(document.getElementById("hymc").value == ""){
			alert("会员姓名不能为空，请填写！");
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
		document.myform.submit();
	}	
	
</script>
</head>
<body >
<form name="myform" action="updateHykda.html" method="post">
<input type="hidden" name="hykda.id" id="id" value="<%=StringUtils.nullToStr(hykdas.get("id")) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">卡信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykh" id="hykh" value="<%=StringUtils.nullToStr(hykdas.get("hykh")) %>" size="45" readonly>
		</td>
	
		<td class="a1" width="15%">卡类型</td>
	    <td class="a2" width="35%">
	    <input type="text" name="card_type" id="card_type" value="<%=StringUtils.nullToStr(hykdas.get("card_type")) %>" size="45" readonly>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">所属分类</td>
		<td class="a2" width="35%">
		<input type="text" name="ssfl" id="ssfl" value="<%=StaticParamDo.getHykflNameById(StringUtils.nullToStr(hykdas.get("ssfl"))) %>" size="45" readonly>
		</td>
	
		<td class="a1" width="15%">初始积分</td>
		<td class="a2" width="35%">
		   <input type="text" name="csjf" id="csjf" value="<%=StringUtils.nullToStr(hykdas.get("csjf")) %>"  size="25" readonly>
		</td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">有效日期</td>
		<td class="a2" width="35%">
		   <input type="text" name="yxrq" id="yxrq" value="<%=StringUtils.nullToStr(hykdas.get("yxrq")) %>"   size="25" readonly>
		</td>
	
		<td class="a1"  width="15%">失效日期</td>
	    <td class="a2" width="35%">
	       <input type="text" name="sxrq" id="sxrq" value="<%=StringUtils.nullToStr(hykdas.get("sxrq")) %>"  size="25" readonly>
        </td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">领用情况</td>
		<td class="a2" width="35%">
		<input type="text" name="state" id="state" value="<%=StringUtils.nullToStr(hykdas.get("state")) %>"  size="25" readonly>		   
		</td>
	
		<td class="a1"  width="15%">是否停用</td>
	    <td class="a2" width="35%">	       
	       <select name="hykda.sfty" id="sfty" style="width:76%">				
				<option value="否" <%if(StringUtils.nullToStr(hykdas.get("sfty")).equals("否")) out.print("selected"); %>>否</option>
				<option value="是" <%if(StringUtils.nullToStr(hykdas.get("sfty")).equals("是")) out.print("selected"); %>>是</option>
			</select><font color="red">*</font>
        </td>
	</tr> 
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">会员信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">会员姓名</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.hymc" id="hymc" value="<%=StringUtils.nullToStr(hykdas.get("hymc")) %>" size="45" >
		</td>
	
		<td class="a1" width="15%">联系人</td>
	    <td class="a2" width="35%">
	    <input type="text" name="hykda.lxrname" id="lxrname" value="<%=StringUtils.nullToStr(hykdas.get("lxrname")) %>" size="45" >
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%">
		<input type="text" name="hykda.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(hykdas.get("lxdh")) %>" size="45" >
		</td>
	
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.mobile" id="mobile" value="<%=StringUtils.nullToStr(hykdas.get("mobile")) %>"  size="25" >
		</td>
	</tr>
	 
	<tr>
		<td class="a1" width="15%">身份证号</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.sfzh" id="sfzh" value="<%=StringUtils.nullToStr(hykdas.get("sfzh")) %>"   size="25" >
		</td>
	
		<td class="a1"  width="15%">E-Mail</td>
	    <td class="a2" width="35%">
	       <input type="text" name="hykda.mail" id="mail" value="<%=StringUtils.nullToStr(hykdas.get("mail")) %>"  size="25" >
        </td>
	</tr>	
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.address" id="address" value="<%=StringUtils.nullToStr(hykdas.get("address")) %>"   size="50" maxlength="80">
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
