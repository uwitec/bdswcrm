<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Sfd sfd = (Sfd)VS.findValue("sfd");
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();
%>

<html>
<head>
<title>售后服务单添加</title>
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

	var allCount = 2;
	
	function saveInfo(vl){ 

		if(vl == '1'){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}	
	      
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
		
		if(document.getElementById("jx_date").value == ""){
			alert("接待时间不能为空，请选择！");
			return;
		}
			
		if(document.getElementById("client_id").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}		
		if(document.getElementById("linkman").value == ""){
			alert("联系人不能为空，请选择！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(document.getElementById("qzfs").value == ""){
			alert("求助方式不能为空，请选择！");
			return;
		}
			
		if(document.getElementById("wx_state").value == ""){
			alert("维修状态不能为空，请选择！");
			return;
		}
		if(document.getElementById("ms").value == ""){
			alert("说明不能为空，请选择！");
			return;
		}	
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交售后服务单吗，提交后将无法修改！")){				
				document.sfdForm.submit();		
			}
 	     }
	     else
	     { 
	         document.sfdForm.submit();	
	     }
	     document.sfdForm.btnSave.disabled = true;
		 document.sfdForm.btnSub.disabled = true;
	}
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
	}     
	
	function openWin(id){  //与退货单使用一个产品选择
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
    function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		
    function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("s_nums");
		if(serialNum == ""){
		   
			return;
		}
		dwrService.getBxdRecordorBuyRecord(serialNum,setProductInfo);		
	}
	
	//处理返回产品对象
	function setProductInfo(product){
		  
		 var  client_name=document.getElementById("client_name");
		 var  client_id=document.getElementById("client_id");
		 var linkman= document.getElementById("linkman");
		 var  mobile=document.getElementById("mobile");
		 var  address=document.getElementById("address");
		 var  ms=document.getElementById("ms");
		 client_name.value=""; 
		 client_id.value="";   
		 linkman.value="";
		 mobile.value=""
		address.value="";
		ms.value="";
		  var productArray=product.split("$");
		  var flog= productArray[0];
		  //维修记录
		     
		   if(flog==2)  {
		  	//零售记录
		     ms.value='序列号: '+productArray[4]+'\n'+'产品名称: '+productArray[2]+'\n'+'产品型号: '+productArray[3];
			 linkman.value=productArray[5]; 
			 mobile.value=productArray[6];			 
			 address.value=productArray[8];
			 client_name.value="零售客户";
			 client_id.value="CL00000097";	
			 
			 if(productArray[10]!="")
			 {
		       var destination = "viewLsd.html?id="+productArray[10];
		       var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		       window.open(destination,'详细信息',fea);	
	 	     }
		  }
		  else if(flog==4)
		  {//销售记录
		    
		      ms.value='序列号: '+productArray[4]+'\n'+'产品名称: '+productArray[2]+'\n'+'产品型号: '+productArray[3];
			  client_id.value=productArray[5];
			 linkman.value=productArray[6];
			 address.value=productArray[7];
			 mobile.value=productArray[8];
			  client_name.value=productArray[9];
			  
			 if(productArray[10]!="")
			 {
			  var destination = "viewXsd.html?id="+productArray[10];
		      var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		      window.open(destination,'详细信息',fea);  
		     }
		  }
		  //没有记录
		  else if(flog==3)
		  {
		      alert("维修记录和销售记录没有该序列号!");
		  }
		  else
		  {
		      alert("维修记录和销售记录没有该序列号!");
		  }
	}	
	
</script>
</head>
<body onload="initFzrTip();initClientTip()">
<form name="sfdForm" action="saveSfd.html" method="post">
<input type="hidden" name="sfd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售后服务单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="sfd.id" id="id" value="<%=StringUtils.nullToStr(sfd.getId())%>"  maxlength="50" readonly><font color="red">*</font>
		</td>
		 <%
		String rq = StringUtils.nullToStr(sfd.getJx_date()) ;
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%">
		 <input type="text" name="sfd.jx_date" id="jx_date" value="<%=rq%>" size="15"  class="Wdate" onFocus="WdatePicker()" >		
		<font color="red">*</font>
		</td>				
	</tr>
	<tr>			
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="sfd.client_id"   id="client_name" value="" size="30" maxlength="50" onblur="setClientValue();" >
		<input type="hidden" name="sfd.client_name" id="client_id" value="" ><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%">
		    <input id="linkman" name="sfd.linkman" type="text" length="20" value="" /> 
            <font color="red">*</font>	
		</td>
	</tr>
	<tr>			
		<td class="a1" width="15%">联系电话</td>
		<td class="a2">
			<input type="text" name="sfd.mobile" id="mobile" value=""></input>
		</td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%">
		    <input id="address" name="sfd.address" type="text" length="20" value="" />            
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value=""/> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="sfd.jxr" id="fzr" value=""/> 		    
		</td>	
	    <td class="a1" width="15%">求助方式</td>
		<td class="a2">
			<select name="sfd.qzfs" id="qzfs">
			    <option value=""></option>
				<option value="电话" >电话</option>
				<option value="带机" >带机</option>
			</select>
			<font color="red">*</font> 		  		
		</td>
	</tr>
	<tr>
		<td class="a1">维修状态</td>
		<td class="a2">
			<select name="sfd.wx_state" id="wx_state">			     
				<option value="待处理">待处理</option>
				<option value="已处理">已处理</option>				 
			</select>	<font color="red">*</font>		
		</td>					
	</tr>
	 <tr >
	    <td class="a1" width="15%">产品序列号</td>
		<td class="a2" colspan="4">
		<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			注：输入产品序列号回车，可自动提取客户信息
		</td>
	</tr>		
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="6" name="sfd.ms" id="ms" style="width:75%" maxlength="500"></textarea><font color="red">*</font>	
		</td>
	</tr>
			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="btnSub" value="提交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
<BR>
<font color="red">注：“草稿”指售后服务单暂存，可修改；“提交”后售后服务单不可修改，如需审批则直接提交审批。</font>
<BR><BR>
</form>
</body>
</html>