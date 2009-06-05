<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String sendlinkman = (String)VS.findValue("sendLinkman");
  
%>
<html>
<head>
<title>短信内容</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo()
	{
	    if(document.getElementById("context").value=="")
	    {
	      alert("短信内容不能为空");
	      return;
	    }
		document.myform.submit();
	}
	
     function check(fieldName,UseName,RemName,len){      
       if(fieldName.value.length>len)
       {
        fieldName.value=(fieldName.value).substring(0,len);
        alert("最多可只允许输入 "+len+" 个字符！");
        return false;
    }
    else{
        UseName.value=eval(fieldName.value.length);
        RemName.value=len-UseName.value;
        return true;
    }
    }	
</script>
</head>
<body >
<form name="myform" action="sendSMS.html" method="post">
<input type="hidden" name="sendLinkman" value="<%=sendlinkman %>"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">短信内容</td>
	</tr>
	</thead>
	<tr>
         <td   valign="bottom"  colspan="2">
            	<font color="#7F7F7F">最多允许输入 <b>180</b> 个字符！</font>
         </td>
         <td   valign="bottom" colspan="2">
            	<font color="#7F7F7F">
                	已用：<input type="text" name="ContentUse" id="ContentUse" value="0" size="4" disabled style="text-align:center;border:0;background-color:#F9F9F9"> 个&nbsp;&nbsp;
                	剩余：<input type="text" name="ContentRem" id="ContentRem" value="180" size="4" disabled style="text-align:center;border:0;background-color:#F9F9F9"> 个&nbsp; 
            	</font>
         </td>
     </tr>    
	<tr>
		<td class="a1" width="15%">短信内容</td>
		<td class="a2" colspan="3">
			<textarea name="context" id="context"  rows="30" style="width:100%"  onkeydown="check(context,ContentUse,ContentRem,180)" onkeyup="check(context,ContentUse,ContentRem,180)" onchange="check(context,ContentUse,ContentRem,180)"></textarea> 
		</td>
	</tr>
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="发 送" class="css_button2" onclick="saveInfo()">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
