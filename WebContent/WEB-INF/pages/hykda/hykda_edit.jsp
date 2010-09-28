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
<title>会员卡档案</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function saveInfo(){
	document.myform.submit();
}	

</script>
</head>
<body>
<form name="myform" action="updateHykda.html" method="post">
<input type="hidden" name="hykda.fkjsr" value="<%=StringUtils.nullToStr(hykda.getFkjsr()) %>">
<input type="hidden" name="hykda.id" value="<%=StringUtils.nullToStr(hykda.getId()) %>"> 	 	
<input type="hidden" name="hykda.hymc" value="<%=StringUtils.nullToStr(hykda.getHymc()) %>">
<input type="hidden" name="hykda.state" value="<%=StringUtils.nullToStr(hykda.getState()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡档案</td>
	</tr>
	</thead>
	<tr>
	    <td class="a1" width="15%">会员卡号</td>
	    <td class="a2" width="35%">
		   <input type="text" name="hykda.hykh" id="hykh" value="<%=StringUtils.nullToStr(hykda.getHykh()) %>" maxlength="50" style="width:200px" readonly>
	    </td>
		<td class="a1" width="15%">发卡日期</td>
		<td class="a2" width="35%">
		   <input type="text" name="hykda.fkrq" id="fkrq" value="<%=StringUtils.nullToStr(hykda.getFkrq()) %>" maxlength="20" style="width:200px" readonly>
		</td>
	</tr>
	<tr>	
		<td class="a1">会员联系人</td>
		<td class="a2">			  
		   <input type="text" name="hykda.lxrname" id="lxrname" value="<%=StringUtils.nullToStr(hykda.getLxrname()) %>" maxlength="20" style="width:200px">
		</td>
		<td class="a1">证件号码</td>
		<td class="a2">
		   <input type="text" name="hykda.sfzh" id="sfzh" value="<%=StringUtils.nullToStr(hykda.getSfzh()) %>" maxlength="20" style="width:200px">
		</td>		
	</tr>
	<tr>
		<td class="a1">性别</td>
		<td class="a2">
		    <select name="hykda.sex" id="sex" style="width:200px">
				<option value="男" <%if("男".equals(StringUtils.nullToStr(hykda.getSex()))) out.print("selected"); %>>男</option>
				<option value="女" <%if("女".equals(StringUtils.nullToStr(hykda.getSex()))) out.print("selected"); %>>女</option>	
			</select>
		</td>					

		<td class="a1">出生日期</td>
		<td class="a2">
		   <input type="text" name="hykda.birth" id="birth" value="<%=StringUtils.nullToStr(hykda.getBirth()) %>" class="Wdate" onFocus="WdatePicker()" maxlength="20" style="width:200px">
		</td>		
	</tr>
	<tr>
		<td class="a1">联系电话</td>
		<td class="a2">
		<input type="text" name="hykda.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(hykda.getLxdh()) %>" maxlength="20" style="width:200px">
		</td>
		<td class="a1">手机</td>
		<td class="a2">
		   <input type="text" name="hykda.mobile" id="mobile" maxlength="20" value="<%=StringUtils.nullToStr(hykda.getMobile()) %>" style="width:200px">
		</td>
	</tr>
	<tr>
		<td class="a1">E-Mail</td>
		<td class="a2">
		   <input type="text" name="hykda.mail" id="mail" value="<%=StringUtils.nullToStr(hykda.getMail()) %>" maxlength="100" style="width:200px">
		</td>
		<td class="a1">单位名称</td>
		<td class="a2">
		   <input type="text" name="hykda.gzdw" id="gzdw" value="<%=StringUtils.nullToStr(hykda.getGzdw()) %>" maxlength="100" style="width:200px">
		</td>
	</tr>
	<tr>
		<td class="a1">地址</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.address" id="address" value="<%=StringUtils.nullToStr(hykda.getAddress()) %>" maxlength="100" style="width:563px">
		</td>
	</tr>	
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
		   <input type="text" name="hykda.fkbz" id="fkbz" value="<%=StringUtils.nullToStr(hykda.getFkbz()) %>" maxlength="500" style="width:563px">
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="保存" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
