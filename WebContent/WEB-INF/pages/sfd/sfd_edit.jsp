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
String[] bxyy = (String[])VS.findValue("bxyy"); 
%>

<html>
<head>
<title>售后服务单编辑</title>
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
	var jhzq = 0;
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

	function initJhzq(originalRequest){
		var resText = originalRequest.responseText.trim(); 
		if(resText == ""){
			return false;
		}
		var arryText = resText.split("%");

		//客户地址填充
		if(arryText != null && arryText.length>0){
			var arryClientInfo = arryText[0].split("#");			
			jhzq = arryClientInfo[6];
		}
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
			var objLxr = document.getElementById("linkman"); 
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
					document.getElementById("mobile").value = arryLxrObj[0].tel;
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
					document.getElementById("mobile").value = arryLxrObj[i].tel;
					break;
				}
			}
		}
	}
	function saveInfo(vl){ 

		if(vl == '1'){
			document.getElementById("state").value = "已保存";
			document.getElementById("wx_state").value = "待处理";
		}	
	      
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
		
		if(document.getElementById("jx_date").value == ""){
			alert("接待时间不能为空，请选择！");
			return;
		}
		if(document.getElementById("khlx").value == "往来单位"){	
		  if(document.getElementById("client_id").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		  }	
		}
		else if(document.getElementById("khlx").value == "零售客户"){
		if(document.getElementById("client_name").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		  }
		
		}	
		///if(document.getElementById("linkman").value == ""){
		///	alert("联系人不能为空，请选择！");
		///	return;
		///}
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
		if(document.getElementById("bxyy").value == ""){
			alert("报修原因不能为空，请选择！");
			return;
		}	
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交售后服务单吗，提交后将无法修改！")){				
				document.myform.submit();	
			}
 	     }
	     else
	     { 
	         document.myform.submit();	
	     }
	      alert("操作成功！");
	}
    
    function setFlow(v2,id){ 
        if((document.getElementById("state").value == ""))
        {
          alert("先保存单据，才能指定流程！");
		  return;
        }
        
        document.getElementById("state").value="已提交";
        
        if((document.getElementById("flow").value != ""))
        {
          alert("已经为本单指定了流程！");
		  return;
        }
		if(v2 == '1')
		{
			document.getElementById("flow").value = "咨询";
		}
		else if(v2=='2')
		{
			document.getElementById("flow").value = "投诉";
		}
		else
		{
		    document.getElementById("flow").value = "维修";
		}
		if(v2=='1')
		{
		  var destination = "editZxgd.html?id="+id;
		  var fea ='width=950,height=550,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		  window.open(destination,'详细信息',fea);	
		}
		else if(v2=='2')
		{
		   var destination = "editTsgd.html?id="+id;
		   var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		   window.open(destination,'详细信息',fea);	
		}
		else
		{
		   var destination = "editPgd.html?id="+id;
		   var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		   window.open(destination,'详细信息',fea);	
		}
	}
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
	}     
	
	function openWin(id){  //与退货单使用一个商品选择
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
	
	//处理返回商品对象
	function setProductInfo(product){
		  
		 var  client_name=document.getElementById("client_name");
		 if(document.getElementById("khlx").value == "往来单位"){	
		   var  client_id=document.getElementById("client_id");
		    client_id.value="";
		 }
		 var linkman= document.getElementById("linkman");
		 var  mobile=document.getElementById("mobile");
		 var  address=document.getElementById("address");
		 var  ms=document.getElementById("ms");
		 client_name.value=""; 
		   
		 linkman.value="";
		 mobile.value=""
		address.value="";
		ms.value="";
		  var productArray=product.split("$");
		  var flog= productArray[0];
		  //维修记录
		     
		   if(flog==2)  {
		  	//零售记录
		     ms.value='序列号: '+productArray[4]+'\n'+'商品名称: '+productArray[2]+'\n'+'商品型号: '+productArray[3];
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
		    
		      ms.value='序列号: '+productArray[4]+'\n'+'商品名称: '+productArray[2]+'\n'+'商品型号: '+productArray[3];
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
	function chgBxyy(vD){

		var bxyy_ms1 = document.getElementById("bxyy_ms1");
		var bxyy_ms2 = document.getElementById("bxyy_ms2");
		
		if((vD == "其他") || (vD == "其他原因")){
			bxyy_ms1.style.display = "";
			bxyy_ms2.style.display = "";
				
		}else {
			bxyy_ms1.style.display = "none";
			bxyy_ms2.style.display = "none";
		}
	}
	
	function saveState()
	{
	  if(document.getElementById("flow").value!="")
	  { 
	     document.myform.btnSave.disabled = true;
	     document.myform.btnConsult.disabled = true;
	     document.myform.btnRepair.disabled = true;
	  }
	  else
	  {
	    document.myform.btnSave.disabled = false;
	    document.myform.btnConsult.disabled = false;
	    document.myform.btnRepair.disabled = false;
	  }
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();chgBxyy('<%=StringUtils.nullToStr(sfd.getBxyy()) %>');saveState();">
<form name="myform" action="updateSfd.html" method="post">
<input type="hidden" name="sfd.state" id="state" value="<%=StringUtils.nullToStr(sfd.getState())%>">
<input type="hidden" name="sfd.wx_state" id="wx_state" value="<%=StringUtils.nullToStr(sfd.getWx_state())%>">
<input type="hidden" name="sfd.flow" id="flow" value="<%=StringUtils.nullToStr(sfd.getFlow())%>">
<input type="hidden" name="sfd.jx_date" id="jx_date" value="<%=StringUtils.nullToStr(sfd.getJx_date())%>">
<input type="hidden" name="sfd.khlx" id="khlx" value="<%=StringUtils.nullToStr(sfd.getKhlx())%>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售后服务单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="sfd.id" id="id" value="<%=StringUtils.nullToStr(sfd.getId())%>"   size="45"  readonly>
		</td>
		<td class="a1" width="15%">商品序列号</td>
		<td class="a2" width="35%">
		<input type="text" id="s_nums" name="sfd.qz_serial_num" value="<%=StringUtils.nullToStr(sfd.getQz_serial_num())%>" size="45" onkeypress="javascript:f_enter()"></td>
					
	</tr>
	<%
	  if(StringUtils.nullToStr(sfd.getKhlx()).equals("往来单位"))
	  {
	 %>
	<tr>
	    <td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="sfd.client_id"   id="client_name" onblur="setClientRegInfo();" value="<%=StringUtils.nullToStr(StaticParamDo.getClientNameById(sfd.getClient_name())) %>" size="45" maxlength="50" >
		<input type="hidden" name="sfd.client_name" id="client_id" value="<%=StringUtils.nullToStr(sfd.getClient_name())%>" ><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>			
		
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%" >
			<select name="sfd.linkman" id="linkman" onchange="chgLxr(this.value);" style="width:76%">
				<%
				if(!StringUtils.nullToStr(sfd.getLinkman()).equals("")){
				%>
					<option value="<%=StringUtils.nullToStr(sfd.getLinkman()) %>" selected><%=StringUtils.nullToStr(sfd.getLinkman()) %></option>
				<%
				} 
				%>
			</select>
		</td>	
	</tr>
	<% 
	}
	else if(StringUtils.nullToStr(sfd.getKhlx()).equals("零售客户"))
	{
	%>
	<tr>
	    <td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%">
		<input type="text" name="sfd.client_name"   id="client_name"  value="<%=StringUtils.nullToStr(sfd.getClient_name()) %>" size="45" maxlength="50" >
		<font color="red">*</font>
		</td>			
		
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%" >
		<input type="text" name="sfd.linkman"   id="linkman"  value="<%=StringUtils.nullToStr(sfd.getLinkman()) %>" size="45" maxlength="50" >
		</td>	
	</tr>
	<% 
	}
	%>
	<tr>			
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%">
			<input type="text" name="sfd.mobile" id="mobile" value="<%=StringUtils.nullToStr(sfd.getMobile()) %>" size="45"></input>
		</td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%">
		    <input id="address" name="sfd.address" type="text" size="45" maxlength="100" value="<%=StringUtils.nullToStr(sfd.getAddress()) %>" />            
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">商品信息</td>
		<td class="a2" colspan="3">
			<textarea rows="6" name="sfd.ms" id="ms" style="width:92%" maxlength="500" ><%=StringUtils.nullToStr(sfd.getMs())%></textarea><span style="color:red">*</span>
		</td>
	</tr>	
	<tr>
		<td class="a1" width="15%">报修原因</td>
		<td class="a2" colspan="3">
			<select name="sfd.bxyy" id="bxyy"  onchange="chgBxyy(this.value);" style="width:80%">
				<option value=""></option>
				<%
				String bxyy1 = StringUtils.nullToStr(sfd.getBxyy());
				if(bxyy != null && bxyy.length>0){
					for(int i=0;i<bxyy.length;i++){
				%>
					<option value="<%=StringUtils.nullToStr(bxyy[i]) %>" <%if(bxyy1.equals(StringUtils.nullToStr(bxyy[i]))) out.print("selected"); %>><%=StringUtils.nullToStr(bxyy[i]) %></option>
				<%
					}
				}
				%>				
			</select><font color="red">*</font>	
		</td>	
	</tr>
	<% 
	if((!StringUtils.nullToStr(sfd.getBxyy()).equals("其他")) && (!StringUtils.nullToStr(sfd.getBxyy()).equals("其他原因")))
	  {
	%>
	<tr>	
		<td class="a1" width="15%" id="bxyy_ms1" style="display:none">报修原因说明</td>
		<td class="a2" id="bxyy_ms2" style="display:none" colspan="3">
		<textarea rows="6" name="sfd.bxyy_ms" id="bxyy_ms" style="width:92%"><%=StringUtils.nullToStr(sfd.getBxyy_ms()) %></textarea>
   </tr>
   <% 
   }
   else
   {
   %>
   <tr>	
		<td class="a1" width="15%" id="bxyy_ms1">报修原因说明</td>
		<td class="a2" id="bxyy_ms2" colspan="3">
		<textarea rows="6" name="sfd.bxyy_ms" id="bxyy_ms" style="width:92%"><%=StringUtils.nullToStr(sfd.getBxyy_ms()) %></textarea>
   </tr>
   <% 
   }
   %>
   
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		   <input  id="brand"  type="text"  size="43" length="20"  onblur="setValue()" value="<%=StringUtils.nullToStr(StaticParamDo.getRealNameById(sfd.getJxr())) %>"/> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="sfd.jxr" id="fzr" value="<%=StringUtils.nullToStr(sfd.getJxr())%>"/> 		      
		</td>	
	    <td class="a1" width="15%">客户报修方式</td>
		<td class="a2">
			<select name="sfd.qzfs" id="qzfs"  style="width:76%">
			    <option value=""></option>
				<option value="电话" <%if(StringUtils.nullToStr(sfd.getQzfs()).equals("电话"))out.print("selected"); %>>电话</option>
				<option value="带机" <%if(StringUtils.nullToStr(sfd.getQzfs()).equals("带机"))out.print("selected"); %>>带机</option>
			</select>	
			<font color="red">*</font> 	  		
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4" >
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSave" value="保 存" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
		</td>
	</tr>
</table>
<BR>
<font color="red">注：“保存”后才可以进行处理流程的指定。</font>
<BR><BR>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">选择本单处理流程</td>
	</tr>
	</thead> 
	<tr height="35">
		<td class="a1" colspan="4" width="100%">&nbsp;
			<input type="button" name="btnConsult" value="咨 询" class="css_button3" onclick="setFlow('1','<%=StringUtils.nullToStr(sfd.getId())%>');">
			<!--  <input type="button" name="btnComplain" value="投 诉" class="css_button3" onclick="setFlow('2','<%=StringUtils.nullToStr(sfd.getId())%>');">-->
			<input type="button" name="btnRepair" value="维 修" class="css_button3" onclick="setFlow('3','<%=StringUtils.nullToStr(sfd.getId())%>');">
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="reset" name="button2" value="关 闭" class="css_button3" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>