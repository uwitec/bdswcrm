<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>邮件添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function saveInfo(){
		document.myform.content.value = eWebEditor1.eWebEditor.document.body.innerHTML;

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
		var fea ='width=850,height=450,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-450)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择发送人',fea);		
	}
	
	function openMailSet(){
		var destination = "editMailSet.html";
		var fea ='width=600,height=350,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'发件箱设置',fea);			
	}
</script>
</head>
<body >
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
			<textarea name="content" id="content" rows="1" style="width:90%" style="display:none"></textarea>
            <iframe ID="eWebEditor1" src="<%=request.getContextPath() %>/xxfb/eWebEditor.jsp?id=content&style=standard&color=Office" width="100%" height="350px" frameborder="0" scrolling="no"> 
            </iframe>
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
					<td><input name="suffix" type="file" value="" size="80"/></td>
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
