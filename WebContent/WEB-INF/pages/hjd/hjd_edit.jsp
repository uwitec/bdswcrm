<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Hjd hjd = (Hjd)VS.findValue("hjd");
List hjdProducts = (List)VS.findValue("hjdProducts");
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

int count = 2;
if(hjdProducts!=null && hjdProducts.size()>0){
	count = hjdProducts.size() - 1;
}

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>

<html>
<head>
<title>换件单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selSqr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=count %>;

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
			
		if(document.getElementById("hj_date").value == ""){
			alert("日期不能为空，请选择！");
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
		  alert("换件产品不能为空，请填写！");
		  return;
		}
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		for(var i=0;i<allCount;i++){				  
			var oqzserialnum = document.getElementById("oqz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //产品名称			 
			var nqzserialnum= document.getElementById("nqz_serial_num_" + i); //序列号
			if(pn.value!="")
			{
			    if(oqzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
				alert("产品" + pn.value + "强制旧序列号，请先输入序列号！");
				oqzserialnum.focus();
				return;
				}
			   				 
			   if(nqzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
				alert("产品" + pn.value + "强制新序列号，请先输入序列号！");
				nqzserialnum.focus();
				return;
				}					
				else if(nqzserialnum.value ==oqzserialnum.value)  {											 
				alert("产品" + pn.value + "输入的新序列号与旧序列号重复 ，请检查！");
				nqzserialnum.focus();
				return;
				}
			}
		}			 					
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交换件单吗，提交后将无法修改！"))
			{		 					
			  document.HjdForm.submit(); 
			 }
 	     }
	     else
	     { 	     
	         document.HjdForm.submit();	
	     }
	     document.bfdForm.btnSave.disabled = true;
		 document.bfdForm.btnSub.disabled = true;
		}
		
    function addTr(){
        var otr = document.getElementById("hjdTable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="hjdProducts['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="hjdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="hjdProducts['+curId+'].product_xh" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="oqz_serial_num_'+curId+'" name="hjdProducts['+curId+'].oqz_serial_num" >';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="nqz_serial_num_'+curId+'" name="hjdProducts['+curId+'].nqz_serial_num" size="10" readonly><input type="hidden" id="qz_flag_'+curId+'" name="hjdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		         
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="product_remark_'+curId+'" name="hjdProducts['+curId+'].product_remark" >';                       

				
		otr.appendChild(otd);
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7);
        otr.appendChild(otd5);
     }	
     
     
	function delTr(i)
	{
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
	}     

	//选择好件库产品
	function openWin(id)
	{
		var destination = "selHjdProcKc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	
	function  opensqrWin()
	{
	   var destination = "selSqr.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	function openSerialWin(vl)
	{	 		
		
		var pn = document.getElementById("product_name_"+ vl).value;
		 
		
		if(pn == ""){
			alert("请选择产品，再输入序列号！");
			return;
		} 		
		var url = "importHjSerial.html?openerId="+vl;
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
		}else{
			alert("该序列号不存在，请检查!");
		}
	}			

   function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.bfdForm.proc_id.length;i++){
			var o = document.bfdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.bfdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择产品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";		
		document.getElementById("product_remark_" + sel).value = "";		
		document.getElementById("oqz_serial_num_" + sel).value = "";
		document.getElementById("nqz_serial_num_" + sel).value = "";
		document.getElementById("qz_flag_" + sel).value = "";
	}	
	
</script>
</head>
<body onload="initFzrTip();">
<form name="HjdForm" action="updateHjd.html" method="post">
<input type="hidden" name="hjd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">换件单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2">
		<input type="text" name="hjd.id" id="id" value="<%=StringUtils.nullToStr(hjd.getId()) %>" size="30" maxlength="50" readonly><font color="red">*</font>	
		</td>	
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="hjd.hj_date" id="hj_date" value="<%=StringUtils.nullToStr(hjd.getHj_date())  %>"  class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>	
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(hjd.getJsr()) %>"/>   
            <div   id="brandTip"  style="height:12px;position:absolute;left:146px; top:141px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="hjd.jsr" id="fzr" value="<%=hjd.getJsr()%>"/> <font color="red">*</font>	
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
<table width="100%"  align="center" id="hjdTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="20%">产品名称</td>
		<td width="20%">规格</td>
		<td width="15%">旧序列号</td>
		<td width="20%">新序列号</td>
		<td width="25%">备注</td>
		<td></td>
	</tr>
	</thead>
<%
 if(hjdProducts!=null&&hjdProducts.size()>0)
 {
      for(int i=0;i<hjdProducts.size();i++)
      {
           HjdProduct hjdProduct= (HjdProduct)hjdProducts.get(i);
           String flag2 = "否";
		   if(!StringUtils.nullToStr(hjdProduct.getOqz_serial_num()).equals("")){
			 flag2 = "是";
		   }
 %>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="hjdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(hjdProduct.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="hjdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(hjdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="hjdProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(hjdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2"><input type="text" id="oqz_serial_num_<%=i %>" name="hjdProducts[<%=i %>].oqz_serial_num" value="<%=StringUtils.nullToStr(hjdProduct.getOqz_serial_num()) %>"  readonly></td>
		<td class="a2">
			<input type="text" id="nqz_serial_num_<%=i %>" name="hjdProducts[<%=i %>].nqz_serial_num" value="<%=StringUtils.nullToStr(hjdProduct.getNqz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="hjdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="product_remark_<%=i %>" name="hjdProducts[<%=i %>].product_remark" value="<%=StringUtils.nullToStr(hjdProduct.getProduct_remark()) %>"></td>
		
	</tr>
<%
	}
}
%>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">备注</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="6" name="hjd.remark" id="remark" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(hjd.getRemark()) %></textarea>
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
<font color="red">注：“草稿”指换件单暂存，可修改；“提交”后换件单不可修改，如需审批则直接提交审批。</font>
<BR><BR>

</body>
</html>