<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
String hykh = StringUtils.nullToStr(request.getParameter("hykh"));

%>

<html>
<head>
<title>请输入会员卡号</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	var sObj = null;
	//触发点击回车事件
	function f_enter(obj){
	    if (window.event.keyCode==13){
	        sendHykh(obj);
	        importSub();
	        event.returnValue = false;
	    }
	}	
	function importSub(){
	    var vl = "";
		var obj = document.getElementsByName("hyk");
		sObj = obj;
		if(obj != null){
		    
			for(var i=0;i<obj.length;i++){
				if(obj[i].value == ""){
					alert("序列号必须全部输入！");
					obj[i].focus();
					return;
				}
							
				if(obj[i].value != ""){
					if(vl == ""){
						vl = obj[i].value;					
					}
				}
			}
		}
		
		var hykh_1 = window.opener.document.getElementById("hykh");
		if(hykh_1 != null){
				hykh_1.value = vl;
		}
		window.close();
		
	}
	
	//发送会员卡号
	function sendHykh(obj){
		sObj = obj;
		dwrService.hykhIsExist(obj.value,checkHykda);		
	}
	
	
	//对返回的会员卡号进行校验
	function checkHykda(flag){
		if(flag == "false"){
			alert("会员卡号不存在或者会员卡号失效、未领用、已被停用，请检查!");
			sObj.focus();
			return;
		}
	}
</script>
</head>
<body >
<form name="myform" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡号信息</td>
	</tr>
	</thead>
<%
	String nm = "";
	nm = hykh;
%>
	<tr>
		<td class="a1" width="25%">会员卡号</td>
		<td class="a2" width="75%">
			<input type="text" name="hyk" id="hyk" value="<%=nm %>" size="30" onkeypress="javascript:f_enter(this);" onkeydown="javascript:f_enter(this);">
		</td>
	</tr>
</table>
<BR>
<center>
<input type="button" name="button1" value=" 确定 " class="css_button" onclick="importSub();">
<input type="button" name="button2" value=" 关闭 " class="css_button" onclick="window.close();">
</center>
</form>
</body>
</html>
