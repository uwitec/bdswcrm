<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bwl bwl = (Bwl)VS.findValue("bwl");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>备忘录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="xhEditor/xheditor.js"></script>
<script type="text/javascript">
	$(pageInit);
	function pageInit(){
		$('#content').xheditor(true,{tools:'full',upLinkUrl:"<%=request.getContextPath() %>/UploadFile",upLinkExt:"zip,rar,txt",upImgUrl:"<%=request.getContextPath() %>/UploadFile",upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"<%=request.getContextPath() %>/UploadFile",upFlashExt:"swf",upMediaUrl:"<%=request.getContextPath() %>/UploadFile",upMediaExt:"avi"});
	}
	function saveInfo(){
		if(!InputValid(document.getElementById("title"),1,"string",1,1,100,"标题")){	 return; }		
		
		document.myform.submit();
	}	
	function addTo(){
		var destination = "selMan.html";
		var fea ='width=750,height=420,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-420)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择共享用户',fea);		
	}
</script>
</head>
<body >
<form name="myform" action="updateBwl.html" method="post">
<input type="hidden" name="bwl.id" id="id" value="<%=StringUtils.nullToStr(bwl.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备忘录</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">共享用户名</td>
		<td class="a2" width="90%" valign="middle">
			<table width="100%" border="0" style="font-size: 12px">
				<tr>
					<td width="80%"><textarea name="bwl.gxr" id="to" rows="3" style="width:95%" ><%=StringUtils.nullToStr(bwl.getGxr()) %></textarea><BR>&nbsp;<font color="red">多个人名用“,”隔开</font></td>
					<td width="16"><img src="images/create.gif" border="0" style="cursor: hand"></td>
					<td><a href="javascript:addTo();" class="xxlb"> 添加共享用户名 </a></td>
				</tr>
			</table>
		</td> 
	</tr>
	<tr>
		<td class="a1" width="15%">标题</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="bwl.title" id="title" value="<%=StringUtils.nullToStr(bwl.getTitle()) %>" size="45"><font color="red">*</font></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">备忘录内容</td>
		<td class="a2" colspan="3">
			<textarea id="content" name="bwl.content" rows="25" style="width:99%"><%=StringUtils.nullToStr(bwl.getContent()) %></textarea>
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
