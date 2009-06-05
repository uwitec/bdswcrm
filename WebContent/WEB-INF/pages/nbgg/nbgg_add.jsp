<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
<title>内部公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		document.myform.elements["content"].value = eWebEditor1.eWebEditor.document.body.innerHTML;
		if(!InputValid(document.getElementById("title"),1,"string",1,1,100,"标题")){	 return; }
		if(!InputValid(document.getElementById("pub_date"),1,"string",1,1,20,"发布时间")){	 return; }		
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="saveNbgg.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">内部公告</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="20%">标题</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="xxfbNbgg.title" id="title" value="" size="45"><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1" width="20%">发布时间</td>
		<td class="a2" width="30%"><input type="text" name="xxfbNbgg.pub_date" id="pub_date" value="<%=DateComFunc.getToday() %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('pub_date')); return false;">
		</td>
		<td class="a1" width="20%">分销商是否可见</td>
		<td class="a2" width="30%">
			<select name="xxfbNbgg.type" id="type">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">公告内容</td>
		<td class="a2" colspan="3">
			<textarea name="xxfbNbgg.content" id="content" rows="1" style="width:90%" style="display:none"></textarea>
            <iframe ID="eWebEditor1" src="<%=request.getContextPath() %>/xxfb/eWebEditor.jsp?id=content&style=standard&color=Office" width="100%" height="450px" frameborder="0" scrolling="no"> 
            </iframe>
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
