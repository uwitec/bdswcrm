<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<style>
.rightContent{
	width:100%;
	text-align:left;
	display:inline ;
	float:left;
	overflow-x: none; 
	overflow-y: none;	
}
</style>
<script>
var leftWinHeight;
$(document).ready(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
			document.getElementById("deptFrame").height = parent.leftWinHeight;
			document.getElementById("right").height = parent.leftWinHeight;
			leftWinHeight = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
			document.getElementById("deptFrame").height = $(window).height() + "px";
			document.getElementById("right").height = $(window).height() + "px";
			leftWinHeight = $(window).height() + "px";
		}
	}
});
$(window).resize(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
			document.getElementById("deptFrame").height = parent.leftWinHeight;
			document.getElementById("right").height = parent.leftWinHeight;
			leftWinHeight = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
			document.getElementById("deptFrame").height = $(window).height() + "px";
			document.getElementById("right").height = $(window).height() + "px";
			leftWinHeight = $(window).height() + "px";
		}
	}
});
</script>
</head>
<body>
<div class="rightContent" id="divContent">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0"  style="border-spacing: 0px;">
	<tr>
		<td width="25%">
			<iframe width="100%" name="deptFrame" id="deptFrame" src="listDept.html" frameborder="0" SCROLLING="auto" style="margin-top:0px;margin-left: 0px;"></iframe>
		</td>
		<td width="75%">
			<iframe width="100%" name="right" id="right" src="listEmployee.html" frameborder="0" SCROLLING="auto" style="margin-top:0px;margin-left: 0px;"></iframe>
		</td>
	</tr>
</table>
</div>
</body>
</html>
