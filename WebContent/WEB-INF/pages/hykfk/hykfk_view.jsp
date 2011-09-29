<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykda hykda = (Hykda)VS.findValue("hykda");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发卡管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="js/selSqr.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	//客户对应联系人信息
	var arryLxrObj = new Array();
	var temp_client_id = "";
	//客户联系人对象
	function LinkMan(id,name,tel){
	    this.id = id;
	    this.name = name;
	    this.tel = tel;
	}	

	function onloadClientInfo(){
		if($F('client_id') == ""){
			return;
		}
		var url = 'queryClientsRegInfo.html';
		var params = "clients_id=" + $F('client_id');
		var myAjax = new Ajax.Request(
		url,
		{
			method:'post',
			parameters: params,
			onComplete: initJhzq,
			asynchronous:true
		});
	}
	
	//查询客户相关信息
	function setClientRegInfo(){		
		//填充值
		setClientValue(); 

		if(temp_client_id == $F('client_id')){
			return;
		}
		
		temp_client_id = $F('client_id');
		if($F('client_id') == ""){
			return;
		}
		
		//根据填充后的值选择客户地址联系人等信息
		var url = 'queryClientsRegInfo.html';
		var params = "clients_id=" + $F('client_id');
		var myAjax = new Ajax.Request(
		url,
		{
			method:'post',
			parameters: params,
			onComplete: fillClientRegInfo,
			asynchronous:true
		});
	}
	
	//处理客户地址联系人等信息
	function fillClientRegInfo(originalRequest){  

		var resText = originalRequest.responseText.trim(); 
		if(resText == ""){
			return false;
		}

		var arryText = resText.split("%");

		//客户地址填充
		if(arryText != null && arryText.length>0){
		
			var arryClientInfo = arryText[0].split("#");			
			document.getElementById("address").value = arryClientInfo[0];
			
		}
		
		if(arryText != null && arryText.length>1){
			var linkMantext = arryText[1];
			
			//联系人填充
			var objLxr = document.getElementById("lxrname"); 
			objLxr.options.length = 0;
			
			var arryLinkMan = linkMantext.split("$");
			if(arryLinkMan.length > 0){
				for(var i=0;i<arryLinkMan.length;i++){
					var arryInfo = arryLinkMan[i].split("#");		
					var manObj = new LinkMan(arryInfo[0],arryInfo[1],arryInfo[2]);
					arryLxrObj[i] = manObj;
					objLxr.add(new Option(arryInfo[1],arryInfo[1]));
				}
				if(arryLxrObj[0].tel != undefined)
					document.getElementById("lxdh").value = arryLxrObj[0].tel;
			}		
		}
	}
	
	//联系人与电话联动
	function chgLxr(vl){
		if(vl == ""){
			return;
		}
		if(arryLxrObj != null && arryLxrObj.length > 0){
			for(var i=0;i<arryLxrObj.length;i++){
				if(vl == arryLxrObj[i].name){
					document.getElementById("lxdh").value = arryLxrObj[i].tel;
					break;
				}
			}
		}
	}
	
	function saveInfo(){
		if(document.getElementById("fkjsr").value == ""){
			alert("经手人不能为空，请填写！");
			return;
		}
	   
	   		
		if(document.getElementById("ffjg").value == ""){
			alert("发卡机构不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("lxrname").value == ""){
			alert("领用人不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("hykh").value == ""){
			alert("会员卡号不能为空，请选择！");
			return;
		}
		
		
		document.myform.submit();
	}	
	
	function chgScTyle(vD){

		if(vD == "") vD = "单个增加";
		var obj_sl1 = document.getElementById("sl1");
		var obj_sl2 = document.getElementById("sl2");
		
		if(vD == "单个增加"){
		    obj_sl1.style.display = "none";
			obj_sl2.style.display = "none";
		}else if(vD == "批量增加"){
		    obj_sl1.style.display = "";
			obj_sl2.style.display = "";
	}
	}
	
	function chgFkTyle(vD){

		if(vD == "") vD = "到机构";
		var obj_jg1 = document.getElementById("jg1");
		var obj_jg2 = document.getElementById("jg2");
		var lxdh=document.getElementById("lxdh");
		var address=document.getElementById("address");
		
		if(vD == "到个人"){
		    obj_jg1.style.display = "none";
			obj_jg2.style.display = "none";
			lxdh.value="";
			address.value="";
		}else if(vD == "到机构"){
		    obj_jg1.style.display = "";
			obj_jg2.style.display = "";
	}
	}
</script>
</head>
<body  onload="initFzrTip();initClientTip();onloadClientInfo();">
<form name="myform" action="updateHykfk.html" method="post">
<input type="hidden" name="hykda.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(hykda.getLxdh()) %>">
<input type="hidden" name="hykda.address" id="address" value="<%=StringUtils.nullToStr(hykda.getAddress()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发卡管理</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">会员卡号</td>
	   <td class="a2" width="35%"><%=StringUtils.nullToStr(hykda.getHykh()) %>
	   </td>
	   <td class="a1" width="15%">发卡机构</td>
		<td class="a2" width="35%"></td>
	</tr>
	
	<tr>
	    <%
		String rq = StringUtils.nullToStr(hykda.getFkrq()) ;
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1" width="15%">发卡日期</td>
		<td class="a2" width="35%"><%=rq%>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">	<%=StringUtils.nullToStr(hykda.getFkjsr()) %>	
		</td>
	</tr>
	
	<tr>	
		<td class="a1" width="15%" id="jg1">领用机构</td>
		<td class="a2" width="35%" id="jg2"><%=StringUtils.nullToStr(hykda.getHymc()) %>
		</td>
		
		<td class="a1" width="15%" >领用人</td>
		<td class="a2" width="35%">	<%=StringUtils.nullToStr(hykda.getLxrname()) %>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykda.getFkbz()) %>
		</td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">			
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
