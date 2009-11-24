<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Fhkhd fhkhd = (Fhkhd)VS.findValue("fhkhd");
List fhkhdProducts = (List)VS.findValue("fhkhdProducts");
 

int counts = 2;
if(fhkhdProducts != null && fhkhdProducts.size()>0){
	counts = fhkhdProducts.size() - 1;
}

 
 List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>
<html>
<head>
<title>返还客户单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	  
	var allCount = <%=counts %>;
		
	function saveInfo(){
	      
		if(document.getElementById("fzr").value == ""){
			alert("返还人不能为空，请选择！");
			return;
		}	
		 	
		if(document.getElementById("client_name").value == ""){
			alert("客户姓名不能为空，请填写！");
			return;
		}
		if(document.getElementById("lxr").value=="")
		{
		   alert("联系人不能为空,请填写!");
		   return;
		}
		 var counts=0;
		for(var i=0;i<=allCount;i++)
		{
		   
		   var product_name=document.getElementById("product_name_"+i).value;
		   if(product_name!="")
		   {
		     
		     counts=counts+1;
		   }
		}
		if(counts==0)
		{
		  alert("接修产品不能为空，请填写！");
		  return;
		}
		//判断是否存在强制输入序列号的产品没有输入序列号
		for(var i=0;i<=allCount;i++){
		  
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //产品名称
			
			 
				 if(pn.value!="")
				 {
					if(qzserialnum.value == "")
					{
						//如果没有输入序列号提示用户输入序列号
						alert("产品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}
					
			     }			 
		}

		  
		
	    document.fhkhdForm.submit();	
	     
	 		
	}
	
	function setNum(i)
	{
	  var nums = document.getElementById("nums_" + i);
			if(nums != null){
				if(!InputValid(nums,0,"int",0,1,99999999,"数量")){
					nums.focus();
					return;
				}
			}		
	} 
	
	function showfy(fyidvalue)
	{
	  if(fyidvalue=='否')
	  {
	    document.getElementById("fyid").style.display="none";
	  }
	  else
	  {
	    document.getElementById("fyid").style.display=""; 
	  }
	}		
      	
    function addTr(){
        var otr = document.getElementById("lsdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="jjdProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="jjdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="jjdProducts['+curId+'].product_xh" size="15" readonly>';
      
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="jjdProducts['+curId+'].qz_serial_num" size="15" readonly>&nbsp;';   
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="remark_'+curId+'" name="jjdProducts['+curId+'].remark">';                       

		var otd4 = document.createElement("td");
		otd4.className = "a2";
		otd4.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1);           
        otr.appendChild(otd2);
        otr.appendChild(otd3); 
        otr.appendChild(otd4);
                          
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selFhkhdProcKc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}

	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function openYushk(){
		var destination = "selLsysk.html";
		var fea ='width=850,height=500,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择零售预收款',fea);		
	}		
	
 
	
	function chgSel(vl){
		if(vl == "是"){
			document.getElementById("ysStyle").style.display = "";
		}else{
			document.getElementById("ysStyle").style.display = "none";
			document.getElementById("yushk_id").value = "";
			document.getElementById("yushkje").value = "0.00";
		}
		hj();
	}
	
	function openSerialWin(vl){
		var pn = document.getElementById("product_name_" + vl).value;
		var nm = document.getElementById("nums_" + vl).value;
		
		if(pn == ""){
			alert("请选择产品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置产品数量，再输入序列号！");
			return;
		}
		
		var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
		
		var url = "jjimportSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
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
		dwrService.getProductObjBySerialNum(serialNum,setProductInfo);		
	}
	
	//处理返回产品对象
	function setProductInfo(product){
		if(product != null && product.productId != null){
			var flag = false;
			for(var i=0;i<=allCount;i++){
				var obj = document.getElementById("product_id_" + i);
								
				if(obj != null){
					if(obj.value == "" || obj.value==product.productId){
					
						var vl = dwr.util.getValue("qz_serial_num_" + i); //已有的序列号
						var vl2 = dwr.util.getValue("s_nums");    //输入的序列号
						if(vl.indexOf(vl2) != -1){
							alert("产品列表中已存在该序列号，请检查！");
							break;
						}
						
						if(vl == ""){
							vl = vl2;
						}else{
							vl += "," + vl2;
						}
						dwr.util.setValue("qz_serial_num_" + i,vl);
											
						dwr.util.setValue("product_id_" + i,product.productId);
						dwr.util.setValue("product_name_" + i,product.productName);
						dwr.util.setValue("product_xh_" + i,product.productXh);
						dwr.util.setValue("cbj_" + i,product.price);
						dwr.util.setValue("kh_cbj_" + i,product.khcbj);
						dwr.util.setValue("gf_" + i,product.gf);
						dwr.util.setValue("price_" + i,product.lsbj);	
						
						var nums = dwr.util.getValue("nums_" + i);
						dwr.util.setValue("nums_" + i,parseInt(nums)+1);					
						
						dwr.util.setValue("qz_flag_" + i,product.qz_serial_num);
						
						dwr.util.setValue("s_nums","");
						break;
					}
					if(i==allCount){
						addTr();				
					}
				}
			}
			hj();
		}else{
			alert("该序列号不存在，请检查!");
		}
	}		 
</script>

</head>
<body onload="initFzrTip();initClientTip();">
<form name="fhkhdForm" action="saveFhkhd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">返还客户单信息</td>
	</tr>
	</thead>
	 
	 <%
	 
	if(msg != null && msg.size() > 0){
		for(int i=0;i<msg.size();i++){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=StringUtils.nullToStr(msg.get(i)) %></font></td>
	</tr>
	<%
		}
	}
	%>		
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><input type="text" name="fhkhd.id" id="id" value="<%=StringUtils.nullToStr(fhkhd.getId()) %>" readonly></td>	
		 
		 <%
		String rq = StringUtils.nullToStr(fhkhd.getFh_date());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">返还日期</td>
		<td class="a2"><input type="text" name="fhkhd.fh_date" id="fh_date" value="<%= rq %>" readonly><img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('fh_date')); return false;"><font color="red">*</font>
		</td>		
	</tr>
	<tr>
		
		 
		<td class="a1" width="15%">返还人</td>
		<td class="a2">         
			<input id="brand" type="text" length="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(fhkhd.getFhr()) %>"/> 
			<div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<input type="hidden" name="fhkhd.fhr" id="fzr" value="<%=StringUtils.nullToStr(fhkhd.getFhr())%>"/><font color="red">*</font>	          
		</td>
		<td class="a1" width="15%">返还单状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="fhkhd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(fhkhd.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(fhkhd.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select><font color="red">*</font>		
		</td>						
	</tr>
	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>
	 
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		 
		<td>强制序列号</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
 if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
 {
      for(int i=0;i<fhkhdProducts.size();i++)
      {
           FhkhdProduct fhkhdProduct= (FhkhdProduct)fhkhdProducts.get(i);
 %>
	<tr>
				<td class="a2">
					<input type="text" id="product_name_<%=i %>" name="fhkhdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(fhkhdProduct.getProduct_name()) %>">
					<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
					<input type="hidden" id="product_id_<%=i %>" name="fhkhdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(fhkhdProduct.getProduct_id()) %>">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="fhkhdProducts[<%=i %>].product_xh" size="15" value="<%=StringUtils.nullToStr(fhkhdProduct.getProduct_xh()) %>"></td>	
				
				 
				 
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="fhkhdProducts[<%=i %>].qz_serial_num" size="15" value="<%=StringUtils.nullToStr(fhkhdProduct.getQz_serial_num()) %>">
					<a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				<td class="a2"><input type="text" id="remark_<%=i %>" name="fhkhdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(fhkhdProduct.getRemark()) %>"></td>
				<%if (i>0){ %>		
				<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
				<%}else{ %>
				<td class="a2">&nbsp;</td>
				<%} %>
			</tr>
<%
}
 }
else
{ 
	for(int i=0;i<3;i++)
	{
		%>
			<tr>
				<td class="a2">
					<input type="text" id="product_name_<%=i %>" name="fhkhdProducts[<%=i %>].product_name"  >
					<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
					<input type="hidden" id="product_id_<%=i %>" name="fhkhdProducts[<%=i %>].product_id">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="fhkhdProducts[<%=i %>].product_xh" size="15"  ></td>					 
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="fhkhdProducts[<%=i %>].qz_serial_num" size="15" readonly>
					 
				</td>				
				<td class="a2"><input type="text" id="remark_<%=i %>" name="fhkhdProducts[<%=i %>].remark"></td>
				<%if (i>0){ %>		
				<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
				<%}else{ %>
				<td class="a2">&nbsp;</td>
				<%} %>
			</tr>
		<%
	}
}
%>	
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
    <tr>
		<td  align="left" class="a2" colspan="4" > 
			<input  type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
			 
		</td>	 
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
  <tr>	   
	      <td  class="a1" width="15%" >报修费用</td>
	      <td  class="a2"  colspan="3">
	       <select name="fhkhd.isfy" id="isfy" onchange="showfy(this.value)">
	         <option value="否" <%if(StringUtils.nullToStr(fhkhd.getIsfy()).equals("否"))out.print("selected"); %>>否</option>
	         <option value="是" <%if(StringUtils.nullToStr(fhkhd.getIsfy()).equals("是"))out.print("selected"); %>>是</option>
	       </select>
	      </td>
	</tr>
	 
	<tr id="fyid" style="display:none">
	    <td class="a1" width="15%"  >实收费用</td>
		<td class="a2" width="35%"  ><input type="text"  name="fhkhd.skje" id="skje" value=""/></td>
		<td class="a1" width="15%"  >收款账户</td>
		<td class="a2" width="35%"  ><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(fhkhd.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="fhkhd.skzh" value="<%=StringUtils.nullToStr(fhkhd.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><font color="red">*</font>
	</tr>
</table> 
 <BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="fhkhd.client_id"   id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(fhkhd.getClient_name())) %>" size="30" maxlength="50" onblur="setClientValue();">
		<input type="hidden" name="fhkhd.client_name" id="client_id" value="<%=StringUtils.nullToStr(fhkhd.getClient_name()) %>">
		<div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="fhkhd.lxr" id="lxr" size="30" value="<%=StringUtils.nullToStr(fhkhd.getLxr()) %>"><font color="red">*</font></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="fhkhd.lxdh" id="lxdh" size="30" value="<%=StringUtils.nullToStr(fhkhd.getLxdh()) %>"></td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2"><input type="text" name="fhkhd.address" id="address" size="30" maxlength="100" value="<%= StringUtils.nullToStr(fhkhd.getAddress())%>"></td>			
	</tr>
 	
</table>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="fhkhd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(fhkhd.getMs()) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;				 
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>

</form>
</BODY>
</HTML>