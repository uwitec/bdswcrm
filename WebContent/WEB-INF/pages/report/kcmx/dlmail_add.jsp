<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

 
%>
<html>
<head>
<title>邮件添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="xhEditor/xheditor.js"></script>
<script type="text/javascript">
	//$(pageInit);
	//function pageInit(){
	//	$('#content').xheditor(true,{tools:'full',upLinkUrl:"<%=request.getContextPath() %>/UploadFile",upLinkExt:"zip,rar,txt",upImgUrl:"<%=request.getContextPath() %>/UploadFile",upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"<%=request.getContextPath() %>/UploadFile",upFlashExt:"swf",upMediaUrl:"<%=request.getContextPath() %>/UploadFile",upMediaExt:"avi"});
	//}
	function saveInfo(){
		//document.myform.content.value = eWebEditor1.eWebEditor.document.body.innerHTML;

		if(document.getElementById("to").value == ""){
		  alert("收件人不能为空，请填写！");
		  return ;
		}
		if(document.getElementById("subject").value ==""){
			alert("邮件主题不能为空，请填写！");
			return ;                  
		}
		
		document.myform.button1.disabled = true;
		document.myform.submit();
	}
	
	function addSuffix(){
		var otr = document.getElementById("objSuffix").insertRow(-1);
		
        var otd=document.createElement("td");
        otd.innerHTML = '<input name="suffix" type="file" value="" size="80"/>';		
		
		otr.appendChild(otd); 
	}
	
	function addTo(){
		var destination = "selMail.html";
		var fea ='width=750,height=420,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-420)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择发送人',fea);		
	}
	
	function openMailSet(){
		var destination = "editMailSet.html";
		var fea ='width=600,height=350,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'发件箱设置',fea);			
	}
	function setValue()
	{	   
	     var str= window.opener.document.getElementById("t1").outerHTML;
	     str+=window.opener.document.getElementById("t2").outerHTML;
	     str+=window.opener.document.getElementById("t3").outerHTML;
	     document.getElementById("content").value=str; 
	     document.getElementById("tsr").outerHTML=str; 
	}
	
</script>
</head>
<body onload="setValue();">

<form name="myform" action="sendMail.html" method="post" enctype="multipart/form-data">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>发送邮件</b></td>
		<td class="csstitle" align="center" width="25%"><a href="javascript:openMailSet();">发件箱设置</a></td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="15%">收件人</td>
		<td class="a2" width="90%" valign="middle">
			<table width="100%" border="0" style="font-size: 12px">
				<tr>
					<td width="80%"><textarea name="to" id="to" rows="3" style="width:95%" ></textarea><BR>&nbsp;<font color="red">多个邮件地址用“,”隔开</font></td>
					<td width="16"><img src="images/create.gif" border="0" style="cursor: hand"></td>
					<td><a href="javascript:addTo();" class="xxlb"> 添加收件人 </a></td>
				</tr>
			</table>
		</td> 
	</tr>
	<tr>
		<td class="a1">邮件主题</td>
		<td class="a2">
		    <input type="text" name="subject"  id="subject" size="90" maxlength="100">
		</td> 
	</tr>			
	<tr>
		<td class="a1">邮件内容</td>
		<td class="a2">
			<textarea name="tsr"  id="tsr" rows="20" style="width:90%"></textarea>
			<input type="hidden" id="content" name="content" value=""/>
		</td>
	</tr>
	<tr>	
	    <td class="a1">
	    附件<BR>
	   [<a href="javascript:addSuffix();">添加附件</a>]
	    </td> 
		<td class="a2">
			<table width="100%" border="0" id="objSuffix">
				<tr>
					<td><input name="suffix" id="fj" type="file"  size="80"/></td>
				</tr>
				<tr>
					<td><input name="suffix" type="file" value="" size="80"/></td>
				</tr>
				<tr>
					<td><input name="suffix" type="file" value="" size="80"/></td>
				</tr>								
			</table>
		</td> 
	</tr>	
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		    	</td>
	</tr>
</table>
</form>
</body>
</html>
