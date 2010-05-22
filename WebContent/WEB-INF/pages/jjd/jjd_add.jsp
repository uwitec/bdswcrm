<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Jjd jjd = (Jjd)VS.findValue("jjd");
List jjdProducts = (List)VS.findValue("jjdProducts");
 

int counts = 2;
if(jjdProducts != null && jjdProducts.size()>0){
	counts = jjdProducts.size() - 1;
}

 
 List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>
<html>
<head>
<title>接件单管理</title>
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
	
	function saveInfo(vl){ 

		if(vl == '1'){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}		
	    
		if(document.getElementById("fzr").value == ""){
			alert("接件人不能为空，请选择！");
			return;
		}	
		 	
		if(document.getElementById("client_name").value == ""){
			alert("客户姓名不能为空，请填写！");
			return;
		}
		if(document.getElementById("linkman").value=="")
		{
		   alert("联系人不能为空,请填写!");
		   return;
		}
		 var counts=0;		 
		for(var i=0;i<=allCount;i++)
		{			 	
		   var product_name=document.getElementById("product_name_"+i);
		   if(product_name!=null)
		   {		     
		      if(product_name.value!="")
		      {		     
		        counts=counts+1;
		      }
		   }		   
		}
		if(counts==0)
		{
		  alert("接修商品不能为空，请填写！");
		  return;
		}
		//判断是否存在强制输入序列号的商品没有输入序列号
		for(var i=0;i<=allCount;i++){
		 
			var qzserialnum = document.getElementById("qz_serial_num_"+i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			
			 
				 if(pn!=null&&pn.value!="")
				 {
				     
					if(qzserialnum.value == "")
					{
						//如果没有输入序列号提示用户输入序列号
						alert("商品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}
					else
					{
						//校验输入数量与商品数是否相同
						var serial = document.getElementById("qz_serial_num_" + i).value;
						var arrySerial = serial.split(",");
						
						var nms = document.getElementById("nums_" + i).value;
						
						if(parseInt(nms) != arrySerial.length){
							alert("商品" + pn.value + "输入序列号数量与商品数量不符，请检查！");
							qzserialnum.focus();
							return;
						}
					}
			     }
			 
		}

		  
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交接修单吗，提交后将无法修改！")){
				 
				document.jjdForm.submit();		
			}
 
	     }
	     else
	     { 
	         document.jjdForm.submit();	
	     }
	 		
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
      	
    function addTr(){
        var otr = document.getElementById("lsdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        
        var otd0=document.createElement("td");
		otd0.className = "a2";
		otd0.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
       
        var otd1=document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_name_'+curId+'" name="jjdProducts['+curId+'].product_name" style="width:100%" readonly> <input type="hidden" id="product_id_'+curId+'" name="jjdProducts['+curId+'].product_id">';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="jjdProducts['+curId+'].product_xh" size="15" style="width:100%" readonly>';     
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="jjdProducts['+curId+'].nums" value="1" size="5"  onblur="setNum('+curId+')" readonly>';
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="hjk_'+curId+'" name="producthjk" value="坏件库" size="7">';
       
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="jjdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="jjdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
        
     
        var otd6  = document.createElement("td"); 
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" id="cpfj_'+curId+'" name="jjdProducts['+curId+'].cpfj"  size="15" style="width:100%">';                       
          
         var otd7  = document.createElement("td"); 
        otd7.className = "a2";
        otd7.innerHTML = '<input type="text" id="fxts_'+curId+'" name="jjdProducts['+curId+'].fxts"  size="15" style="width:100%">';          
	 		
        
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="remark_'+curId+'" name="jjdProducts['+curId+'].remark">';                       

		 
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
          
        otr.appendChild(otd2);
        otr.appendChild(otd3); 
        otr.appendChild(otd4);
        otr.appendChild(otd5);
        otr.appendChild(otd6);        
         otr.appendChild(otd7);
        otr.appendChild(otd8);           
     }
     
     
     
    
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			 
			
	}     
	
	
	function openWin(){
		var destination = "selJjProduct.html";
		var fea ='width=800,height=550,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
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
			alert("请选择商品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置商品数量，再输入序列号！");
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
	
	//处理返回商品对象
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
							alert("商品列表中已存在该序列号，请检查！");
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
	
	 
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.jjdForm.proc_id.length;i++){
			var o = document.jjdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.jjdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("qz_serial_num_" + sel).value = "";
		document.getElementById("cpfj_" + sel).value = "";
		document.getElementById("fxts_" + sel).value = "";
		document.getElementById("hjk_" + sel).value="";
		document.getElementById("remark_" + sel).value = "";
	}			 
 
	
	 
</script>

</head>
<body onload="initFzrTip();initClientTip();">
<form name="jjdForm" action="saveJjd.html" method="post">
<input type="hidden" name="jjd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">接件单信息</td>
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
		<td class="a2" width="35%"><input type="text" name="jjd.id" id="id" value="<%=StringUtils.nullToStr(jjd.getId()) %>" readonly></td>	
		 
		 <%
		String rq = StringUtils.nullToStr(jjd.getJj_date());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">接件日期</td>
		<td class="a2"><input type="text" name="jjd.jj_date" id="jjdate" value="<%= rq %>" readonly> 
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">接件人</td>
		<td class="a2">         
			<input id="brand" type="text" length="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(jjd.getJjr()) %>"/> 
			<div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<input type="hidden" name="jjd.jjr" id="fzr" value="<%=StringUtils.nullToStr(jjd.getJjr())%>"/><font color="red">*</font>	          
		</td>							
	</tr>
	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>
 
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
	    <td width="5%">选择</td>
		<td width="15%">商品名称</td>
		<td width="15%">规格</td>
		<td width="5%">数量</td>	
		<td width="10%">目标库</td>
		<td width="15%">强制序列号</td>
		<td width="15%">附件</td>
		<td width="6%">返还天数</td>
		<td width="15%">备注</td>
		 
	</tr>
	</thead>
<%
 if(jjdProducts!=null&&jjdProducts.size()>0)
 {
      for(int i=0;i<jjdProducts.size();i++)
      {	
		   String flag2 = "否";
           JjdProduct jjdProduct= (JjdProduct)jjdProducts.get(i);
             if(!StringUtils.nullToStr(jjdProduct.getQz_serial_num()).equals("")){
			 flag2 = "是";
		}
           
 %>
	      <tr>
	             <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
				<td class="a2">				   
					<input type="text" id="product_name_<%=i %>" name="jjdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(jjdProduct.getProduct_name()) %>" style="width:100%" readonly>			 
					<input type="hidden" id="product_id_<%=i %>" name="jjdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(jjdProduct.getProduct_id()) %>">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="jjdProducts[<%=i %>].product_xh" size="15" value="<%=StringUtils.nullToStr(jjdProduct.getProduct_xh()) %>" size="15" style="width:100%" readonly  ></td>	
				
				<td class="a2"><input type="text" id="nums_<%=i %>" name="jjdProducts[<%=i %>].nums" value="<%=StringUtils.nullToStr(jjdProduct.getNums()) %>" size="5" style="width:100%" onblur="setNum(<%=i %>)"  readonly></td>		 
				<td class="a2">
					<input type="text" id="hjk_<%=i %>" name="producthjk" value="坏件库" size="7">					 
				</td>
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="jjdProducts[<%=i %>].qz_serial_num" size="15" value="<%=StringUtils.nullToStr(jjdProduct.getQz_serial_num()) %>">
			         <input type="hidden" id="qz_flag_<%=i %>" name="jjdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
					</td>				
				<td class="a2"><input type="text" id="cpfj_<%=i %>" name="jjdProducts[<%=i %>].cpfj" style="width:100%" value="<%=StringUtils.nullToStr(jjdProduct.getCpfj())%>"></td>
				<td class="a2"><input type="text" id="fxts_<%=i %>" name="jjdProducts[<%=i %>].fxts" style="width:100%" value="<%=StringUtils.nullToStr(jjdProduct.getFxts())%>"></td>
				
				<td class="a2"><input type="text" id="remark_<%=i %>" name="jjdProducts[<%=i %>].remark" style="width:100%" value="<%=StringUtils.nullToStr(jjdProduct.getRemark())%>"></td>
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
			    <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
				 
				<td class="a2">
					<input type="text" id="product_name_<%=i %>" name="jjdProducts[<%=i %>].product_name"  style="width:100%" readonly>					 
					<input type="hidden" id="product_id_<%=i %>" name="jjdProducts[<%=i %>].product_id">
				</td>		 
				
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="jjdProducts[<%=i %>].product_xh" size="15" style="width:100%" readonly  ></td>	
 				
				<td class="a2"><input type="text" id="nums_<%=i %>" name="jjdProducts[<%=i %>].nums" value="1" size="5" style="width:100%" onblur="setNum(<%=i %>)" readonly></td>		 
				<td class="a2">
					<input type="text" id="hjk_<%=i %>" name="producthjk" value="坏件库" size="7">					 
				</td>
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="jjdProducts[<%=i %>].qz_serial_num" size="15" readonly>
				    <input type="hidden" id="qz_flag_<%=i %>" name="jjdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				
				<td class="a2"><input type="text" id="cpfj_<%=i %>" name="jjdProducts[<%=i %>].cpfj"  size="15" style="width:100%"></td>
				<td class="a2"><input type="text" id="fxts_<%=i %>" name="jjdProducts[<%=i %>].fxts"  size="15" style="width:100%"></td>				
				<td class="a2"><input type="text" id="remark_<%=i %>" name="jjdProducts[<%=i %>].remark" size="15" style="width:100%"></td>
				 
			</tr>
		<%
	}
}
%>	

</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	 
	<tr height="35">
		<td class="a2" colspan="4" width="100%">&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
	 
</table>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="jjd.client_id"   id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(jjd.getClient_name())) %>" size="30" maxlength="50" onblur="setClientValue();">
		<input type="hidden" name="jjd.client_name" id="client_id" value="<%=StringUtils.nullToStr(jjd.getClient_name()) %>"><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="jjd.linkman" id="linkman" size="30" value="<%=StringUtils.nullToStr(jjd.getLinkman()) %>"><font color="red">*</font></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="jjd.lxdh" id="lxdh" size="30" value="<%=StringUtils.nullToStr(jjd.getLxdh()) %>"></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2"><input type="text" name="jjd.mobile" id="mobile" size="30" maxlength="100" value="<%= StringUtils.nullToStr(jjd.getMobile())%>"></td>			
	</tr>
 	<tr>
		<td class="a1" width="15%">Email</td>
		<td class="a2" colspan="3"><input type="text" name="jjd.mail" id="mail" size="30" value="<%=StringUtils.nullToStr(jjd.getMail()) %>"></td>	
	 </tr>
</table>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="jjd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(jjd.getMs()) %> </textarea>
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
<font color="red">注：“草稿”指接件单暂存，可修改；“提交”后接件单不可修改。</font>
<BR><BR>
</form>
</BODY>
</HTML>


 
 