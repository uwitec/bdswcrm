<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Kfdb kfdb = (Kfdb)VS.findValue("kfdb");

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

String msg = StringUtils.nullToStr(VS.findValue("msg"));

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库房调拨</title>
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
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">

	<%if(!msg.equals("")){ %>
		alert("<%=msg%>");
		window.close();
		opener.document.myform.submit();
	<%}%>

	var allCount = 2;
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已出库";
		}
		
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
			
		if(document.getElementById("ck_date").value == ""){
			alert("日期不能为空，请选择！");
			return;
		}		
		if(document.getElementById("ck_store_id").value == ""){
			alert("调出仓库不能为空，请选择！");
			return;
		}
		if(document.getElementById("rk_store_id").value == ""){
			alert("调入仓库不能为空，请选择！");
			return;
		}
		if(document.getElementById("rk_store_id").value == document.getElementById("ck_store_id").value){
			alert("调出仓库与调入仓库不能相同，请确认！");
			return;		
		}
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		//判断是否存在强制输入序列号的商品没有输入序列号
		for(var i=0;i<allCount;i++){
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			
			if(qzflag != null){
				if(qzflag.value == "是"){
					if(qzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
						alert("商品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}else{
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

		if(vl == "1"){
			document.kfdbForm.submit();
		}else{
			if(window.confirm("确认出库吗？！")){
				document.kfdbForm.submit();
			}else{
				return;
			}
		}
		
		document.kfdbForm.btnSub.disabled = true;
		document.kfdbForm.btnSave.disabled = true;
	}

      	
    function addTr(){
        var otr = document.getElementById("kfdbTable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;

		var otd0 = document.createElement("td");
		otd0.className = "a2";
		otd0.innerHTML = '<input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '">';
        
        var otd1=document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_name_'+curId+'" name="kfdbProducts['+curId+'].product_name" style="width:90%" readonly><input type="hidden" id="product_id_'+curId+'" name="kfdbProducts['+curId+'].product_id">';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="kfdbProducts['+curId+'].product_xh" style="width:90%" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="kfdbProducts['+curId+'].nums" style="width:90%;text-align:center" value="0">';
        
		var otd4 = document.createElement("td");
		otd4.className = "a2";
		otd4.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="kfdbProducts['+curId+'].qz_serial_num" size="25" readonly onclick="openSerialWin('+ curId +');"><input type="hidden" id="qz_flag_'+curId+'" name="kfdbProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           

		otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3);
        otr.appendChild(otd4);
     }	


	function delDesc(){
		var k = 0;
		var sel = "0"; 
		var arryProcId = document.getElementsByName("proc_id");
		for(var i=0;i<arryProcId.length;i++){
			var o = arryProcId[i];
			if(o.checked){
				k = k + 1;
				sel = arryProcId[i].value;
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
		document.getElementById("qz_flag_" + sel).value = "";
	}
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	//选择调拨商品
	function openWin(id){
		var destination = "selKfdbProc.html";
		var fea ='width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
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
	
	function openSerialWin(vl){
		var pn = document.getElementById("product_id_" + vl).value;
		var nm = document.getElementById("nums_" + vl).value;
		
		if(pn == ""){
			alert("请选择商品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置商品数量，再输入序列号！");
			return;
		}
		var storeId = document.getElementById("ck_store_id").value;
		if(storeId == ""){
			alert("调出仓库不能为空，请选择！");
			return;
		}
		var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
		
		var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn + "&store_id=" + storeId;
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
		var storeId = dwr.util.getValue("ck_store_id");
		if(serialNum == ""){
			return;
		}
		if(storeId == ""){
			alert("调出仓库不能为空，请选择！");
			return;
		}
		dwrService.getProductObjBySerialNumAndStoreId(serialNum,storeId,setProductInfo);				
	}
	
	//处理返回商品对象
	function setProductInfo(product){
		if(product != null && product.productId != null){
			var flag = false;
			for(var i=0;i<=allCount;i++){
				var obj = document.getElementById("product_id_" + i);
								
				if(obj != null){
					if(obj.value == "" || obj.value==product.productId){
						if(product.img == "1"){
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
						}
											
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

	
</script>
</head>
<body onload="initFzrTip();initSqrTip();">
<form name="kfdbForm" action="saveKfdb.html" method="post">
<input type="hidden"  name="kfdb.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">库房调拨</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2">
		<input type="text" name="kfdb.id" id="id" value="<%=StringUtils.nullToStr(kfdb.getId()) %>" style="width:230px" maxlength="50" readonly><font color="red">*</font>
		</td>	
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="kfdb.ck_date" id="ck_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" style="width:230px" onFocus="WdatePicker()"><font color="red">*</font>
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">调出仓库</td>
		<td class="a2">
			<select name="kfdb.ck_store_id" id="ck_store_id" style="width:230px">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>"><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>		
		</td>
		<td class="a1" width="15%">调入仓库</td>
		<td class="a2">
			<select name="kfdb.rk_store_id" id="rk_store_id" style="width:230px">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>"><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>		
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">调拨申请单编号</td>
		<td class="a2" width="35%"><input type="text" name="kfdb.dbsq_id" id="dbsq_id" value="" style="width:230px" maxlength="20"></td>
		<td class="a1" width="15%">申请人</td>
		<td class="a2" width="35%">
		    <input id="sqr_text" type="text" maxlength="20" style="width:230px" onblur="setSqrValue();" />
            <div id="sqr_tips"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="kfdb.sqr" id="sqr"/> 	
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">经手人</td>
		<td class="a2" colspan="3">
		    <input  id="brand"    type="text"length="20"  onblur="setValue();"  style="width:230px"/> <font color="red">*</font>
            <div   id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="kfdb.jsr" id="fzr" />
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
		<td class="a2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
		注：输入商品序列号或条形码回车，可自动提取商品信息到商品列表中
		</td>
	</tr>		
</table>
<table width="100%"  align="center" id="kfdbTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="6%">选择</td>
		<td width="30%">商品名称</td>
		<td width="30%">规格</td>
		<td width="10%">数量</td>
		<td width="24%">强制序列号</td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="kfdbProducts[<%=i %>].product_name" style="width:90%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="kfdbProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="kfdbProducts[<%=i %>].product_xh" style="width:90%" readonly></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="kfdbProducts[<%=i %>].nums" style="width:90%;text-align:center" value="0"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="kfdbProducts[<%=i %>].qz_serial_num" value="" size="25" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="kfdbProducts[<%=i %>].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
	</tr>
<%
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
</table>
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
			<textarea rows="4" name="kfdb.remark" id="remark" style="width:75%"></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="出 库" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
