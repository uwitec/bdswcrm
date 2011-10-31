<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bxfhd bxfhd = (Bxfhd)VS.findValue("bxfhd");
List bxfhdProducts = (List)VS.findValue("bxfhdProducts"); 

int counts = 2;
if(bxfhdProducts != null && bxfhdProducts.size()>0){
	counts = bxfhdProducts.size() - 1;
}

 List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报修返还单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src='js/Check.js'></script>
<script language='JavaScript' src='js/date.js'></script>
<script language='JavaScript' src='js/nums.js'></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='js/prototype-1.4.0.js'></script>
<script type='text/javascript' src='js/selJsr.js'></script>
<script language='JavaScript' src='js/selClient.js'></script>

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
	    
		if(document.getElementById("client_name").value == ""){
			alert("报修单位不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
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
		  alert("报修返还商品不能为空，请填写！");
		  return;
		}
		if(!(document.getElementById("ssje").value=="0.00"))
		{
		  if(!InputValid(document.getElementById("skzh"),1,"string",0,1,100,"付款账户")){return;}
		}
		//判断是否存在强制输入序列号的商品没有输入序列号
		for(var i=0;i<=allCount;i++){
		 
			var qzserialnum = document.getElementById("qz_serial_num_"+i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			 
			if(qzflag != null){
				if(qzflag.value == "是"){					     
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
		}
	  
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交报修返还单吗，提交后将无法修改！")){
				
				document.bxfhdForm.submit();		
			}
 	     }
	     else
	     { 	         
	         document.bxfhdForm.submit();	
	     }
	     document.bxfhdForm.btnSub.disabled = true;
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
        var otr = document.getElementById("bxfhdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
		otd.className = "a2";
		otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
		
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="bxfhdProducts['+curId+'].product_name" style="width:90%" readonly><input type="hidden" id="product_id_'+curId+'" name="bxfhdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="bxfhdProducts['+curId+'].product_xh" size="15" style="width:90%" readonly>';

        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="store_id_'+curId+'" name="bxfhdProducts['+curId+'].store_id" value="好件库" size="7" style="width:90%"  readonly>';
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="price_'+curId+'" name="bxfhdProducts['+curId+'].price" value="0.00" size="5" style="width:90%" onblur="hj()">';
        
        var otd7 = document.createElement("td");
        otd7.className = "a2";
        otd7.innerHTML = '<input type="text" id="nums_'+curId+'" name="bxfhdProducts['+curId+'].nums" value="1" size="5" style="width:90%"  onblur="hj()">';
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="bxfhdProducts['+curId+'].totalmoney" value="0.00" size="5" style="width:90%" readonly>';
               
        var otd9 = document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="bxfhdProducts['+curId+'].qz_serial_num" size="11" readonly><input type="hidden" id="qz_flag_'+curId+'" name="bxfhdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
        var otd11 = document.createElement("td");
        otd11.className = "a2";
        otd11.innerHTML = '<input type="text" id="cpfj_'+curId+'"  name="bxfhdProducts['+curId+'].cpfj" size="15"  style="width:90%" >';

        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" id="remark_'+curId+'" name="bxfhdProducts['+curId+'].remark" style="width:90%"  >';                       
	
		otr.appendChild(otd); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd4);
        otr.appendChild(otd5); 
        otr.appendChild(otd7);
        otr.appendChild(otd8);
        otr.appendChild(otd9); 
        otr.appendChild(otd11);
        otr.appendChild(otd6); 
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);			
	}     
	
	
	function openWin(id){
		var destination = "selBxfhProcCkd.html?openerId="+id;
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
		
		var url = "bxfhimportSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
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
		dwrService.getZyProductObjBySerialNum(serialNum,setProductInfo);		
	}
	
	//处理返回商品对象
	function setProductInfo(product){
		if(product != null && product.productId != null)
		{
			var flag = false;
			for(var i=0;i<=allCount;i++)
			{
				var obj = document.getElementById("product_id_" + i);
				if(obj != null)
				{
					if(obj.value == "" || obj.value==product.productId)
					{
						var vl = dwr.util.getValue("qz_serial_num_" + i); //已有的序列号
						var vl2 = dwr.util.getValue("s_nums");    //输入的序列号
						if(vl.indexOf(vl2) != -1){
							alert("商品列表中已存在该序列号，请检查！");
							break;
						}
						
						if(vl == "")
						{
							vl = vl2;
						}
						else
						{
							vl += "," + vl2;
						}
						dwr.util.setValue("qz_serial_num_" + i,vl);
											
						dwr.util.setValue("product_id_" + i,product.productId);
						dwr.util.setValue("product_name_" + i,product.productName);
						dwr.util.setValue("product_xh_" + i,product.productXh);
												
						var serial = document.getElementById("qz_serial_num_" + i).value;
						var arrySerial = serial.split(",");		
						var nums = dwr.util.getValue("nums_" + i);
						if(arrySerial.length==1)
						{
						  dwr.util.setValue("nums_" + i,parseInt(nums));
						}
						else
						{
						   dwr.util.setValue("nums_" + i,parseInt(nums)+1);
						}						
						
						dwr.util.setValue("qz_flag_" + i,product.qz_serial_num);
						
						dwr.util.setValue("s_nums","");
						break;
					}
					if(i==allCount)
					{
						addTr();				
					}
				}
			}
		}
		else
		{
			alert("该序列号不存在，请检查!");
		}
	}	
   function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.bxfhdForm.proc_id.length;i++){
			var o = document.bxfhdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.bxfhdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("price_" + sel).value = "0.00";
		document.getElementById("nums_" + sel).value = "1";
		document.getElementById("xj_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
		document.getElementById("cpfj_" + sel).value = "";
		document.getElementById("qz_serial_num_" + sel).value = "";
		document.getElementById("qz_flag_" + sel).value = "";
	}	
	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	
	function hj(){
		var length = (document.getElementById('bxfhdtable').rows.length-2);
		
		var hjz = 0;
		
		
		for(var i=0;i<=allCount;i++)
		{			
			var price = document.getElementById("price_" + i);
			
			if(price != null)
			{
				if(!InputValid(price,0,"float",0,1,99999999,"维修价格")){
					price.focus();
					return;
				}
			}
			
			var nums = document.getElementById("nums_" + i);
			if(nums != null)
			{
				if(!InputValid(nums,0,"int",0,1,99999999,"数量"))
				{
					nums.focus();
					return;
				}
			}		
					
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null)
			{
				xj.value = (parseFloat(price.value) * parseFloat(nums.value)).toFixed(2);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);				
			}
		}
		var hjje = document.getElementById("hjje");
		var ssje = document.getElementById("ssje");
		
		hjje.value = hjz.toFixed(2);		
		ssje.value = hjz.toFixed(2);	
	}
	

</script>

</head>
<body onload="initFzrTip();initClientTip();">
<form name="bxfhdForm" action="savebxfhd.html" method="post">
<input type="hidden" name="bxfhd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">报修返还单信息</td>
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
		<td class="a2" width="35%"><input type="text" name="bxfhd.id" id="id" value="<%=StringUtils.nullToStr(bxfhd.getId()) %>" readonly></td>	
		 
		<td class="a1">报修返还日期</td>
		<td class="a2"><input type="text" name="bxfhd.fh_date" id="fh_date" value="<%=DateComFunc.getToday() %>" readonly>
		</td>		
	</tr>
	<tr>		 
		<td class="a1" width="20%">报修单位</td>
		<td class="a2">
		<input type="text" name="bxfhd.bxcs_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxfhd.getBxcs())) %>" size="40" maxlength="60"  onblur="setClientValue();">
		<input type="hidden" name="bxfhd.bxcs" id="client_id" value="<%=StringUtils.nullToStr(bxfhd.getBxcs()) %>">
		<!--<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openProvider();" style="cursor:hand">
			--><div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<font color="red">*</font>
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input id="brand" type="text" ength="20" onblur="setValue();" value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getJsr())) %>" > 
         <!--<img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          --><div   id="brandTip"  style="position:absolute;left:610px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		  <input type="hidden" name="bxfhd.jsr" id="fzr" value="<%=bxfhd.getJsr()%>"> <font color="red">*</font>	
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
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			注：输入商品序列号回车，可自动提取商品信息到商品列表中
		</td>
	</tr>	
</table>
<table width="100%"  align="center" id="bxfhdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
	    <td width="2%">选择</td>
	    <td width="17%">商品名称</td>	
		<td width="13%">商品规格</td>
		<td width="3%">仓库</td>
		<td width="7%">单价</td>
		<td width="6%">数量</td>	
		<td width="8%">小计</td>
		<td width="15%">强制序列号</td>
		<td width="14%">商品附件</td>
		<td width="15%">备注</td>		
	</tr>
	</thead>
<%
 if(bxfhdProducts!=null&&bxfhdProducts.size()>0)
 {
      for(int i=0;i<bxfhdProducts.size();i++)
      {
           BxfhdProduct bxfhdProduct= (BxfhdProduct)bxfhdProducts.get(i);
           String flag2 = "否";
		   if(!StringUtils.nullToStr(bxfhdProduct.getQz_serial_num()).equals("")){
			 flag2 = "是";
		   }
 %>
	<tr>
	            <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
				<td class="a2">
					<input type="text" id="product_name_<%=i %>" name="bxfhdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_name()) %>" style="width:90%" readonly>
					<input type="hidden" id="product_id_<%=i %>" name="bxfhdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_id()) %>">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="bxfhdProducts[<%=i %>].product_xh"  value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_xh()) %>"  style="width:90%" readonly></td>	
				<td class="a2">	
				<% String productname=StringUtils.nullToStr(bxfhdProduct.getProduct_name());
	               String kf="";
	              if(productname.equals(""))
	              {
	                kf="";
	              }
	              else
	              {
	                kf="好件库";
	               }
	            %>			    
					<input type="text" id="store_id" name="store_id" value="<%=kf %>" size="5"  style="width:90%" readonly>					 
				</td>
				<td class="a2"><input type="text" id="price_<%=i %>" name="bxfhdProducts[<%=i %>].price" value="<%=JMath.round(bxfhdProduct.getPrice()) %>" size="10" style="width:90%" onblur="hj();"  ></td>
				<td class="a2"><input type="text" id="nums_<%=i %>" name="bxfhdProducts[<%=i %>].nums" value="<%=bxfhdProduct.getNums() %>" size="5" onblur="hj();"  style="width:90%"  ></td>		 
				<td class="a2"><input type="text" id="xj_<%=i %>" name="bxfhdProducts[<%=i %>].totalmoney" value="<%=JMath.round(bxfhdProduct.getTotalmoney()) %>" size="7" style="width:90%" readonly></td>
				<td class="a2">
			        <input type="text" id="qz_serial_num_<%=i %>" name="bxfhdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(bxfhdProduct.getQz_serial_num()) %>" size="10" readonly>
			        <input type="hidden" id="qz_flag_<%=i %>" name="bxfhdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				
				<td class="a2"><input type="text" id="cpfj_<%=i %>" name="bxfhdProducts[<%=i %>].cpfj"  value="<%=StringUtils.nullToStr(bxfhdProduct.getCpfj()) %>" style="width:90%"></td>	
				<td class="a2"><input type="text" id="remark_<%=i %>" name="bxfhdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(bxfhdProduct.getRemark()) %>" style="width:90%"></td>				
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
					<input type="text" id="product_name_<%=i %>" name="bxfhdProducts[<%=i %>].product_name"  >
					<input type="hidden" id="product_id_<%=i %>" name="bxfhdProducts[<%=i %>].product_id">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="bxfhdProducts[<%=i %>].product_xh" size="15"  ></td>	
				<td class="a2">					
					<input type="text" id="store_id" name="store_id" value="好件库" size="7" readonly>				 
				</td>
				<td class="a2"><input type="text" id="price_<%=i %>" name="bxfhdProducts[<%=i %>].price" value="0.00" size="7" style="width:100% " onblur="hj();">
				<td class="a2"><input type="text" id="nums_<%=i %>" name="bxfhdProducts[<%=i %>].nums" value="1" size="5" onblur="hj();"  style="width:90%" ></td>		 
				<td class="a2"><input type="text" id="xj_<%=i %>" name="bxfhdProducts[<%=i %>].totalmoney" value="0.00" size="7" style="width:90%" readonly></td>	 
				
				<td class="a2">
			        <input type="text" id="qz_serial_num_<%=i %>" name="bxfhdProducts[<%=i %>].qz_serial_num"  size="10" readonly>
			        <input type="hidden" id="qz_flag_<%=i %>" name="bxfhdProducts[<%=i %>].qz_flag"> <a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				
				<td class="a2"><input type="text" id="cpfj_<%=i %>" name="bxfhdProducts[<%=i %>].cpfj" size="15"  style="width:90%"></td>	
				<td class="a2"><input type="text" id="remark_<%=i %>" name="bxfhdProducts[<%=i %>].remark"  style="width:90%"></td>				
			</tr>
		<%
	}
}
%>	

</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
  <tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>	 
	</tr>
    <tr height="35">	
		<td class="a1">合计金额</td>
		<td class="a2">
			<input type="text" name="bxfhd.hjje" id="hjje" value="<%=JMath.round(bxfhd.getHjje()) %>" readonly>
		</td>
		<td class="a1">本次实付金额</td>
		<td class="a2">
			<input type="text" name="bxfhd.ssje" id="ssje" value="<%=JMath.round(bxfhd.getSsje()) %>" >
		</td>		
	</tr>
			
	<tr>
		<td class="a1" widht="20%">付款账户</td>
		<td class="a2" ><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(bxfhd.getFkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="bxfhd.fkzh" value="<%=StringUtils.nullToStr(bxfhd.getFkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>	
</table> 
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="bxfhd.remark" id="remark" style="width:75%"><%=StringUtils.nullToStr(bxfhd.getRemark()) %> </textarea>
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
<font color="red">注：“草稿”指报修返还单暂存，可修改；“提交”后报修返还单不可修改。</font>
<BR><BR>
</form>
</BODY>
</HTML>