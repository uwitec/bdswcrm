<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品维护</title>
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
			document.getElementById("kindFrame").height = parent.leftWinHeight;
			document.getElementById("productRight").height = parent.leftWinHeight;
			leftWinHeight = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
			document.getElementById("kindFrame").height = $(window).height() + "px";
			document.getElementById("productRight").height = $(window).height() + "px";
			leftWinHeight = $(window).height() + "px";
		}
	}
});
$(window).resize(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
			document.getElementById("kindFrame").height = parent.leftWinHeight;
			document.getElementById("productRight").height = parent.leftWinHeight;
			leftWinHeight = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
			document.getElementById("kindFrame").height = $(window).height() + "px";
			document.getElementById("productRight").height = $(window).height() + "px";
			leftWinHeight = $(window).height() + "px";
		}
	}
});
</script>
</head>
<body align="center">
<div class="rightContent" id="divContent">
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
			<table width="100%" border="0"cellpadding="0" cellspacing="0" class="chart_list">
			  <tr>
			    <td width="100%" class="a2">
					<iframe width="100%" name="kindFrame" id="kindFrame" src="kind_list.html" border="0" frameborder="0" SCROLLING="yes"></iframe>
				</td>
			  </tr>
			</table>
		</td>
		<td width="75%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="100%">
					<iframe width="100%" name="productRight" id="productRight" src="product_list.html" border="0" frameborder="0" SCROLLING="auto"></iframe>
				</td>
			  </tr>
			</table>			
		</td>
	</tr>
</table>
</div>
</body>
</html>
