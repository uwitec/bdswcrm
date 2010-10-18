<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bxd bxd = (Bxd)VS.findValue("bxd");
List bxdProducts = (List)VS.findValue("bxdProducts");

int counts = 2;
if(bxdProducts != null && bxdProducts.size()>0){
	counts = bxdProducts.size() - 1;
}
 
List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>
<html>
<head>
<title>报修单修改</title>
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
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' type='text/javascript' src='datepicker/WdatePicker.js'></script>
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
		  alert("报修商品不能为空，请填写！");
		  return;
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
			if(window.confirm("确认要提交报修单吗，提交后将无法修改！")){				
				document.bxdForm.submit();		
			}
 	     }
	     else
	     { 
	         document.bxdForm.submit();	
	     }
	     document.bxdForm.btnSub.disabled = true;
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
    //增加设置送修天数
 	function setSxts(i)
	{
	  var sxts = document.getElementById("sxts_" + i);
			if(sxts != null){
				if(!InputValid(sxts,0,"int",0,1,99999999,"送修天数")){
					sxts.focus();
					return;
				}
			}		
	}    
      	
    function addTr(){
        var otr = document.getElementById("bxdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
		otd.className = "a2";
		otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
		
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="bxdProducts['+curId+'].product_name" size="14" style="width:100%" readonly><input type="hidden" id="product_id_'+curId+'" name="lsdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="bxdProducts['+curId+'].product_xh" size="14" style="width:100%" readonly>';

        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="bxdProducts['+curId+'].nums" value="1" size="3" style="width:100%" readonly onblur="setNum('+curId+')">';
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="store_id_'+curId+'" name="bxdProducts['+curId+'].store_id" value="坏件库" size="5" style="width:100%"  readonly>';
               
        var otd9 = document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="bxdProducts['+curId+'].qz_serial_num" size="10" ><input type="hidden" id="qz_flag_'+curId+'" name="bxdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="左键输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
        var otd10 = document.createElement("td");
        otd10.className = "a2";
        otd10.innerHTML = '<input type="text" id="sxts_'+curId+'" name="bxdProducts['+curId+'].sxts" value="" size="5" style="width:100%"  onblur="setSxts('+curId+')" >';
        
        var otd11 = document.createElement("td");
        otd11.className = "a2";
        otd11.innerHTML = '<input type="text" id="cpfj_'+curId+'"  name="bxdProducts['+curId+'].cpfj" size="15"  style="width:100%" readonly>';

        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" id="remark_'+curId+'" name="bxdProducts['+curId+'].remark" size="15" style="width:100%"  readonly>';                       
	
		otr.appendChild(otd); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd5);
        otr.appendChild(otd4); 
        otr.appendChild(otd9);
        otr.appendChild(otd10); 
        otr.appendChild(otd11);
        otr.appendChild(otd6); 
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);			
	}     
	
	
	function openWin(id){
		var destination = "selBxProcCkd.html?openerId="+id;
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
		
		var url = "bximportSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}	

	function f_enter()
	{
	 if (window.event.keyCode==13)
	 {
	   sendSerialNum();
	 }
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("s_nums");
		if(serialNum == ""){
			return;
		}
		dwrService.getBadProductObjBySerialNum(serialNum,setProductInfo);		
	}
	
	//处理返回商品对象
	function setProductInfo(product)
	{
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
												
						dwr.util.setValue("qz_flag_" + i,product.qz_flag);
						
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
		for(var i=0;i<document.bxdForm.proc_id.length;i++){
			var o = document.bxdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.bxdForm.proc_id[i].value;
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
		document.getElementById("remark_" + sel).value = "";
		document.getElementById("sxts_" + sel).value = "";
		document.getElementById("cpfj_" + sel).value = "";
		document.getElementById("qz_serial_num_" + sel).value = "";
		document.getElementById("qz_flag_" + sel).value = "";
	}		
 
</script>

</head>
<body onload="initFzrTip();initClientTip();">
<form name="bxdForm" action="updateBxd.html" method="post">
<input type="hidden" name="bxd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">报修单信息</td>
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
		<td class="a2" width="35%"><input type="text" name="bxd.id" id="id" value="<%=StringUtils.nullToStr(bxd.getId()) %>" readonly></td>	
		 
		<td class="a1">报修日期</td>
		<td class="a2"><input type="text" name="bxd.bxdate" id="bxdate" value="<%=StringUtils.nullToStr(bxd.getBxdate()) %>" readonly>
		</td>		
	</tr>
	<tr>		 
		<td class="a1" width="15%">报修单位</td>
		<td class="a2">
		<input type="text" name="bxd.bxcs_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxd.getBxcs())) %>" size="30" maxlength="50"  onblur="setClientValue();">
		<input type="hidden" name="bxd.bxcs" id="client_id" value="<%=StringUtils.nullToStr(bxd.getBxcs()) %>">
		<!--<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openProvider();" style="cursor:hand">
			--><div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<font color="red">*</font>
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input id="brand" type="text" ength="20" onblur="setValue();" value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxd.getJsr())) %>"/> 
         <!--<img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          --><div   id="brandTip"  style="height:12px;position:absolute;left:610px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		  <input type="hidden" name="bxd.jsr" id="fzr" value="<%=bxd.getJsr()%>"/> <font color="red">*</font>	
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
<table width="100%"  align="center" id="bxdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>	    
	    <td width="20%">商品名称</td>	
		<td width="15%">规格</td>
		<td width="5%">数量</td>	
		<td width="8%">仓库</td>
		<td width="15%">强制序列号</td>
		<td width="7%">送修天数</td>
		<td width="15%">商品附件</td>
		<td width="15%">备注</td>		
	</tr>
	</thead>
<%
 if(bxdProducts!=null&&bxdProducts.size()>0)
 {
      for(int i=0;i<bxdProducts.size();i++)
      {
           BxdProduct bxdProduct= (BxdProduct)bxdProducts.get(i);
           String flag2 = "否";
		   if(!StringUtils.nullToStr(bxdProduct.getQz_serial_num()).equals("")){
			 flag2 = "是";
		   }
 %>
	<tr>
	            <td class="a2">
					<input type="text" id="product_name_<%=i %>" name="bxdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_name()) %>" style="width:100%" readonly>
					<input type="hidden" id="product_id_<%=i %>" name="bxdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_id()) %>">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="bxdProducts[<%=i %>].product_xh"  value="<%=StringUtils.nullToStr(bxdProduct.getProduct_xh()) %>"  style="width:100%" readonly></td>	
				
				<td class="a2"><input type="text" id="nums_<%=i %>" name="bxdProducts[<%=i %>].nums" value="<%=StringUtils.nullToStr(bxdProduct.getNums()) %>" size="5" onblur="setNum(<%=i %>)"  style="width:100%" readonly></td>		 
				<td class="a2">	
				<% String productname=StringUtils.nullToStr(bxdProduct.getProduct_name());
	               String kf="";
	              if(productname.equals(""))
	              {
	                kf="";
	              }
	              else
	              {
	                kf="坏件库";
	               }
	            %>			    
					<input type="text" id="store_id" name="store_id" value="<%=kf %>" size="8"  style="width:100%" readonly>					 
				</td>
				<td class="a2">
			        <input type="text" id="qz_serial_num_<%=i %>" name="bxdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(bxdProduct.getQz_serial_num()) %>" size="10" readonly>
			        <input type="hidden" id="qz_flag_<%=i %>" name="bxdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				<td class="a2"><input type="text" id="sxts_<%=i %>" name="bxdProducts[<%=i %>].sxts" value="<%=StringUtils.nullToStr(bxdProduct.getSxts()) %>" size="5" onblur="setSxts(<%=i %>)"  style="width:100%"></td>
				<td class="a2"><input type="text" id="cpfj_<%=i %>" name="bxdProducts[<%=i %>].cpfj"  value="<%=StringUtils.nullToStr(bxdProduct.getCpfj()) %>" style="width:100%"></td>	
				
				<td class="a2"><input type="text" id="product_remark_<%=i %>" name="bxdProducts[<%=i %>].product_remark" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_remark()) %>" style="width:100%"></td>				
			</tr>
<%
}
 }
%>
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">		 
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="bxd.remark" id="remark" style="width:75%"><%=StringUtils.nullToStr(bxd.getRemark()) %> </textarea>
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
<font color="red">注：“草稿”指报修单暂存，可修改；“提交”后报修单不可修改。</font>
<BR><BR>
</form>
</BODY>
</HTML>