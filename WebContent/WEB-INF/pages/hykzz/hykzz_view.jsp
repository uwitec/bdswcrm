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
		document.myform.submit();
	}	
	
</script>
</head>
<body >
<form name="myform" action="updateHykzz.html" method="post">
<input type="hidden" name="hykzz.id" id="id" value="<%=StringUtils.nullToStr(hykzz.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">制卡单位</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getDept()) %></td>
	</tr>
	<tr>
		<td class="a1"  width="15%">卡类型</td>
	    <td class="a2" colspan="3"> <%=StringUtils.nullToStr(hykzz.getCard_type())%></td>
	</tr>
	<tr>
		<td class="a1" width="15%">所属分类</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getHykflNameById(StringUtils.nullToStr(hykzz.getSsfl())) %></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getHykh())%></td>
	</tr>
	 
	<tr>
		<td class="a1" width="15%">有效日期</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getYxrq()) %></td>
	</tr>
	<tr>
		<td class="a1"  width="15%">失效日期</td>
	    <td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getSxrq()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">初始积分</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getCsjf()) %></td>
	</tr>
	<tr>
		<td class="a1"  width="15%">初始密码</td>
	    <td class="a2" colspan="3"><%=StringUtils.nullToStr(hykzz.getCsmm()) %></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">			
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
