<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
XxfbNbgg xxfbNbgg = (XxfbNbgg)VS.findValue("xxfbNbgg");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内部公告</title>
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
		if(!InputValid(document.getElementById("pub_date"),1,"string",1,1,20,"发布时间")){	 return; }
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="updateNbgg.html" method="post">
<input type="hidden" name="xxfbNbgg.id" id="id" value="<%=StringUtils.nullToStr(xxfbNbgg.getId()) %>">
<input type="hidden" name="xxfbNbgg.parent_id" id="parent_id" value="<%=StringUtils.nullToStr(xxfbNbgg.getParent_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">内部公告</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">标题</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="xxfbNbgg.title" id="title" value="<%=StringUtils.nullToStr(xxfbNbgg.getTitle()) %>" size="45"><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1" width="15%">发布时间</td>
		<td class="a2" width="30%"><input type="text" name="xxfbNbgg.pub_date" id="pub_date" value="<%=StringUtils.nullToStr(xxfbNbgg.getPub_date()) %>" class="Wdate" onFocus="WdatePicker()">
		</td>
		<td class="a1" width="15%">分销商是否可见</td>
		<td class="a2" width="30%">
			<select name="xxfbNbgg.type" id="type">
				<option value="0" <%if(xxfbNbgg.getType().equals("0")) out.print("selected"); %>>否</option>
				<option value="1" <%if(xxfbNbgg.getType().equals("1")) out.print("selected"); %>>是</option>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">公告内容</td>
		<td class="a2" colspan="3">
			<textarea id="content" name="xxfbNbgg.content" rows="25" style="width:90%"><%=StringUtils.nullToStr(xxfbNbgg.getContent()) %></textarea>
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
