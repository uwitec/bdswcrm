<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sw.cms.util.*" %>
<html>
<head>
<title>备忘录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" charset="UTF-8"  src="xhEditor/xheditor.js"></script>
<script type="text/javascript">
	$(pageInit);
	function pageInit(){
		$('#content').xheditor(true,{tools:'full',upLinkUrl:"<%=request.getContextPath() %>/UploadFile",upLinkExt:"zip,rar,txt",upImgUrl:"<%=request.getContextPath() %>/UploadFile",upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"<%=request.getContextPath() %>/UploadFile",upFlashExt:"swf",upMediaUrl:"<%=request.getContextPath() %>/UploadFile",upMediaExt:"avi"});
	}
	function saveInfo(){
		if(!InputValid(document.getElementById("title"),1,"string",1,1,100,"标题")){	 return; }
		
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="saveBwl.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备忘录</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">标题</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="bwl.title" id="title" value="" size="45"><font color="red">*</font></td>
	</tr>
		
	<tr>
		<td class="a1" width="15%">备忘录内容</td>
		<td class="a2" colspan="3">
			<textarea id="content" name="bwl.content" rows="25" style="width:99%"></textarea>
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
