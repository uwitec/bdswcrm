<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");

Ykck ykck = (Ykck)VS.findValue("ykck");
List ykckProducts = (List)VS.findValue("ykckProducts");

String msg = StringUtils.nullToStr(VS.findValue("msg"));

int count = 2;
if(ykckProducts!=null && ykckProducts.size()>0){
	count = ykckProducts.size() - 1;
}

%>

<html>
<head>
<title>移库出库</title>
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
	
	function saveInfo(){
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
		 		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		for(var i=0;i<=allCount;i++){
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //产品名称
			
			if(qzflag != null){
				if(qzflag.value == "是"){
					if(qzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
						alert("产品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}else{
						//校验输入数量与产品数是否相同
						var serial = document.getElementById("qz_serial_num_" + i).value;
						var arrySerial = serial.split(",");
						
						var nms = document.getElementById("nums_" + i).value;
						
						if(parseInt(nms) != arrySerial.length){
							alert("产品" + pn.value + "输入序列号数量与产品数量不符，请检查！");
							qzserialnum.focus();
							return;
						}
					
					}
				}
			}
		}								
	
		document.ykckForm.submit();
	}
	

    function addTr(){
        var otr = document.getElementById("kfdbTable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="ykckProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="ykckProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="ykckProducts['+curId+'].product_xh" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="ykckProducts['+curId+'].nums" value="0">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="ykckProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="ykckProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		       
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="product_remark_'+curId+'" name="ykckProducts['+curId+'].product_remark"  maxlength="50">';                       

		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7); 
        otr.appendChild(otd5);
        otr.appendChild(otd6);               
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	
	//选择调拨产品
	function openWin(id){
		var destination = "selKfdbProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openSerialWin(vl){
		var pn = document.getElementById("product_id_" + vl).value;
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
		
		var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	} 		


	function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
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
	
</script>
</head>
<body onload="initFzrTip();">
<form name="ykckForm" action="updateYkck.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">移库出库</td>
	</tr>
	</thead>
<%
//如果有信息则显示
if(!msg.equals("")){
%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=msg %></font></td>
	</tr>	
<%
}
%>	
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2">
		<input type="text" name="ykck.id" id="id" value="<%=StringUtils.nullToStr(ykck.getId()) %>" size="30" maxlength="50" readonly><font color="red">*</font>	
		</td>	
		<%
		String date = StringUtils.nullToStr(ykck.getCk_date());
		if(date.equals("")){
			date = DateComFunc.getToday();
		}
		%>
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="ykck.ck_date" id="ck_date" value="<%=date %>"  class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>	
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">调出仓库</td>
		<td class="a2">
			<select name="ykck.ck_store_id" id="ck_store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(ykck.getCk_store_id()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>			
		</td>
		 <td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(ykck.getJsr()) %>"/>   
            <div   id="brandTip"  style="height:12px;position:absolute;left:146px; top:141px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="ykck.jsr" id="fzr" value="<%=ykck.getJsr()%>"/> <font color="red">*</font>	
		</td>
	</tr>
	 
	<tr>			
		
		<td class="a1">状态</td>
		<td class="a2" colspan="3">
			<select name="ykck.state" id="state">
				<option value="已保存"  <%if(StringUtils.nullToStr(ykck.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(ykck.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>			
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
	<tr height="35">
		<td class="a2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			注：输入产品序列号回车，自动提取产品信息
		</td>
	</tr>		
</table>
<table width="100%"  align="center" id="kfdbTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
if(ykckProducts!=null && ykckProducts.size()>0){
	for(int i=0;i<ykckProducts.size();i++){
		Map ykckProduct = (Map)ykckProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="ykckProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(ykckProduct.get("product_name")) %>" readonly>
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="ykckProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(ykckProduct.get("product_id")) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="ykckProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(ykckProduct.get("product_xh")) %>" readonly></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="ykckProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(ykckProduct.get("nums")) %>"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="ykckProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(ykckProduct.get("qz_serial_num")) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="ykckProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(ykckProduct.get("qz_flag")) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="product_remark_<%=i %>" name="ykckProducts[<%=i %>].product_remark" value="<%=StringUtils.nullToStr(ykckProduct.get("product_remark")) %>" maxlength="50"></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="ykckProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="ykckProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="ykckProducts[<%=i %>].product_xh" readonly></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="ykckProducts[<%=i %>].nums" value="0"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="ykckProducts[<%=i %>].qz_serial_num" value="" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="ykckProducts[<%=i %>].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="product_remark_<%=i %>" name="ykckProducts[<%=i %>].product_remark" maxlength="50"></td>
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
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
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
			<textarea rows="6" name="ykck.remark" id="remark" style="width:75%" maxlength="500"></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>