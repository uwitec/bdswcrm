<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
String strNums = StringUtils.nullToStr(request.getParameter("nums"));
String serialNums = StringUtils.nullToStr(request.getParameter("serialNum"));
String openerId = StringUtils.nullToStr(request.getParameter("openerId"));
String product_id = StringUtils.nullToStr(request.getParameter("product_id"));

int nums = Integer.parseInt(strNums);

String[] arrySerial = null;
if(!serialNums.equals("")){
	arrySerial = serialNums.split(",");
}
%>

<html>
<head>
<title>输入序列号</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	var sObj = null;
	var curId = 0;
	
	//触发点击回车事件
	function f_enter(obj,id){
	    if (window.event.keyCode==13){
	        checkValueEcho(obj,id);
	        event.returnValue = false;
	    }
	}	
	
	function importSub(){
		var vl = "";
		
		//获取输入框组
		var obj = document.getElementsByName("serial_num");
		if(obj != null){
			for(var i=0;i<obj.length;i++){
				if(obj[i].value == ""){
					alert("序列号必须全部输入！");
					obj[i].focus();
					return;
				}else{
					if(vl == ""){
						vl = obj[i].value;					
					}else{
						vl = vl + "," + obj[i].value;
					}
				}
			}
		}
		
		if(window.confirm("确认所有序列号已进行校验，输入结果有效！")){
			var qz_num = window.opener.document.getElementById("qz_serial_num_<%=openerId%>");
			if(qz_num != null){
				qz_num.value = vl;
			}
			window.close();
		}
	}
	
	//检查输入值是否重复
	function checkValueEcho(obj,id){
		if(obj.value == ""){
			return;
		}
		var curObj = document.getElementsByName("serial_num");

		for(var i=0;i<curObj.length;i++){
			if(parseInt(id) != i){
				if(obj.value == curObj[i].value){
					alert("输入的序列号已经输入，不能重复，请检查！");
					obj.focus();
					return;
				}
			}
		}
		curId = id;
		sendSerialNum(obj);	
	}
	
	
	//发送序列号
	function sendSerialNum(obj){
		sObj = obj;
		dwrService.productIsExist(obj.value,'<%=product_id %>',checkProduct);		
	}
	
	//对返回的产品进行校验
	function checkProduct(flag){
		if(flag == "false"){
			alert("序列号不存在，请检查!");
			sObj.focus();
			return;
		}else{
			curId++;
			if(document.getElementsByName("serial_num")[curId] != null){
				document.getElementsByName("serial_num")[curId].focus();
			}
		}
	}

</script>
</head>
<body >
<form name="myform" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%">序号</td>
		<td>序列号</td>
	</tr>
	</thead>
<%
for(int i=0;i<nums;i++){
	String nm = "";
	if(arrySerial != null && arrySerial.length > 0){
		if(i<arrySerial.length)	nm = arrySerial[i];
	}
%>
	<tr class="a1">
		<td><%=i+1 %></td>
		<td class="a4"><input type="text" id="serial_num" name="serial_num" value="<%=nm %>" style="width:100%" onkeypress="javascript:f_enter(this,<%=i %>);"></td>
	</tr>
<%
}
%>
</table>
<BR>
说明：输入序列号回车，系统自动进行检验。
<center>
<input type="button" name="button1" value=" 确定 " class="css_button" onclick="importSub();">
<input type="button" name="button2" value=" 关闭 " class="css_button" onclick="window.close();">
</center>
</form>
</body>
</html>
