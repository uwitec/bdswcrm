<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
String msg = StringUtils.nullToStr(VS.findValue("msg"));
List userList = (List)VS.findValue("userList");
String id = (String)VS.findValue("id");

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加库存盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
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
			document.getElementById("state").value = "已提交";
		}
		
		if(document.getElementById("store_id").value == ""){
			alert("仓库名称不能为空，请选择！");
			return;
		}			
		if(document.getElementById("pdrq").value == ""){
			alert("盘点日期不能为空，请填写！");
			return;
		}	
		if(document.getElementById("id").value == ""){
			alert("名称不能为空，请填写！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("盘点人不能为空，请填写！");
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
						var nms = document.getElementById("yk_" + i).value;		
						if(nms<0)
		                {
		                   nms=-nms;
		                }				
						if(parseInt(nms) != arrySerial.length){
							alert("商品" + pn.value + "输入序列号数量与商品数量不符，请检查！");
							qzserialnum.focus();
							return;
						}						
					}
				}
			}
		}
		
		pdAll();

        
		if(vl == "1"){
			document.kcpdForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				document.kcpdForm.submit();
			}else{
				return;
			}
		}
		
		document.kcpdForm.btnSub.disabled = true;
		document.kcpdForm.btnSave.disabled = true;
	}
      	
    function addTr(){
        var otr = document.getElementById("kcpdDescTable").insertRow(-1);

        //var curId = ($('kcpdDescTable').rows.length-2);
		var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;        
        
		var otd9=document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';        
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="kcpdDesc['+curId+'].product_name" style="width:90%" readonly><input type="hidden" id="product_id_'+curId+'" name="kcpdDesc['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="kcpdDesc['+curId+'].product_xh" style="width:90%" readonly>';
        
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="kc_nums_'+curId+'" name="kcpdDesc['+curId+'].kc_nums" style="width:90%" value="0" size="5" onblur="pdAll();" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="sj_nums_'+curId+'" name="kcpdDesc['+curId+'].sj_nums" style="width:90%" value="0" size="5" onblur="pdAll();">';        
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="yk_'+curId+'" name="kcpdDesc['+curId+'].yk" value="0" style="width:90%" size="5" readonly>';         
        
        var otd5 = document.createElement("td");
		otd5.className = "a2";
		otd5.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="kcpdDesc['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="kcpdDesc['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		
		
		otr.appendChild(otd9); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd4);
        otr.appendChild(otd5);
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	//打开输入序列号窗口
	function openSerialWin(vl){
		var pn = document.getElementById("product_id_" + vl).value;
		var nm = document.getElementById("yk_" + vl).value;
		
		if(nm<0)
		{
		  nm=-nm;
		}
		
		if(pn == ""){
			alert("请选择商品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置商品数量，再输入序列号！");
			return;
		}
		
		var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
		
		var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
	
	
	function openWin(){
	
		if(document.getElementById("store_id").value == ""){
			alert("请先填写仓库名称！");
			return;
		}
		
		var destination = "selKcProc.html?store_id=" + document.getElementById("store_id").value;
		var fea ='width=850,height=500,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择库存商品',fea);	
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		
	

	function pdAll(){
		var length = (document.getElementById('kcpdDescTable').rows.length-2);
		
		for(var i=0;i<=length;i++){
			
			var kcnums = document.getElementById("kc_nums_"+i);
			if(!InputValid(kcnums,0,"int",0,1,999999,"库库数")){
				kcnums.focus();
				return;
			}			
			var sjnums = document.getElementById("sj_nums_"+i);
			if(!InputValid(sjnums,0,"int",0,1,999999,"实际数")){
				sjnums.focus();
				return;
			}	
			var yk = document.getElementById("yk_"+i);			
			
			yk.value = parseInt(sjnums.value) - parseInt(kcnums.value);
		}
	}
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.kcpdForm.proc_id.length;i++){
			var o = document.kcpdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.kcpdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("kc_nums_" + sel).value = "0";
		document.getElementById("sj_nums_" + sel).value = "0";
		document.getElementById("yk_" + sel).value = "0";
		document.getElementById("qz_serial_num_" + sel).value = "";
		document.getElementById("qz_flag_" + sel).value = "";
		document.getElementById("remark_" + sel).value = "";
	}		
	
	//触发点击回车事件
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
						
						var nums = dwr.util.getValue("yk_" + i);
						if(nums<0)
						{
						  nums=-nums;
						}
						dwr.util.setValue("yk_" + i,parseInt(nums)+1);					
						
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
<body onload="initFzrTip();">
<form name="kcpdForm" action="saveKcpd.html" method="post">
<input type="hidden" name="kcpd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">库存盘点</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="kcpd.id" id="id" value="<%=id %>" maxlength="25" style="width:230px"> <font color="red">*</font>	
		</td>	
		<td class="a1">盘点日期</td>
		<td class="a2"><input type="text" name="kcpd.pdrq" id="pdrq" value="<%=DateComFunc.getToday() %>" style="width:230px" class="Wdate" onFocus="WdatePicker()"> <font color="red">*</font>	
		</td>			
	</tr>	
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%">			
			<select name="kcpd.store_id" id="store_id" style="width:230px">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
					String strid = StringUtils.nullToStr(storeHouse.getId());
					String name = StringUtils.nullToStr(storeHouse.getName());
			%>
				<option value="<%=strid %>"><%=name %></option>
			<%
				}
			}
			%>
			</select> <font color="red">*</font>		
		</td>		
		<td class="a1" width="15%">盘点人</td>
		<td class="a2" width="35%">
		    <input id="brand" type="text"   length="20"  onblur="setValue()"  style="width:230px"/> <font color="red">*</font>	 
            <div   id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="kcpd.pdr" id="fzr"  />
		</td>		
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">盘点详情</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="kcpdDescTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="10%">选择</td>
		<td width="20%">商品名称</td>
		<td width="20%">规格</td>
		<td width="12%">账面数量</td>
		<td width="12%">实际数量</td>
		<td width="8%">盈亏</td>
		<td width="18%">强制序列号</td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="kcpdDesc[<%=i %>].product_name" style="width:90%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="kcpdDesc[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="kcpdDesc[<%=i %>].product_xh" style="width:90%" readonly></td>
		<td class="a2"><input type="text" id="kc_nums_<%=i %>" name="kcpdDesc[<%=i %>].kc_nums" style="width:90%" size="5" value="0" onblur="pdAll();" readonly></td>
		<td class="a2"><input type="text" id="sj_nums_<%=i %>" name="kcpdDesc[<%=i %>].sj_nums" style="width:90%" size="5" value="0" onblur="pdAll();"></td>
		<td class="a2"><input type="text" id="yk_<%=i %>" name="kcpdDesc[<%=i %>].yk" size="5" style="width:90%" value="0" readonly></td>
	    <td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="kcpdDesc[<%=i %>].qz_serial_num" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="kcpdDesc[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
	</tr>
<%
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加库存商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除明细信息" class="css_button4" onclick="delDesc();">
		</td>
	</tr>
</table>
<br>	
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">其它</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="4" name="kcpd.remark" id="remark" style="width:75%" maxlength="500"></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>