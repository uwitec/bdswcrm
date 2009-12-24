<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Ykrk ykrk = (Ykrk)VS.findValue("ykrk");

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

String msg = StringUtils.nullToStr(VS.findValue("msg"));

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
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">

	<%if(!msg.equals("")){ %>
		alert("<%=msg%>");
		window.close();
		
	<%}%>

	var allCount = 2;
	
	function saveInfo(){
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
			
		if(document.getElementById("rk_date").value == ""){
			alert("日期不能为空，请选择！");
			return;
		}		
		if(document.getElementById("rk_store_id").value == ""){
			alert("调出仓库不能为空，请选择！");
			return;
		}
		  if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		var oq="";
		var nq="";
		var oflag=true;
		var nflag=true; 
		//判断是否存在强制输入序列号的产品没有输入序列号
		for(var i=0;i<allCount;i++){				  
			var oqzserialnum = document.getElementById("oqz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //产品名称			 
			var nqzserialnum= document.getElementById("nqz_serial_num_" + i); //序列号
			if(pn.value!="")
			{
			   if(oq=="")
			   {
			     oq=document.getElementById("oqz_serial_num_" + i);
			   }
			   else
			   {
			     oq+=","+document.getElementById("oqz_serial_num_" + i);
			   }
			   if(nq=="")
			   {
			     nq=document.getElementById("nqz_serial_num_" + i);
			   }
			   else
			   {
			     nq+=","+document.getElementById("nqz_serial_num_" + i);
			   }
			   				 
			   if(nqzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
				alert("产品" + pn.value + "强制序列号1，请先输入序列号！");
				nqzserialnum.focus();
				return;
				}					
				else if(nqzserialnum.value ==oqzserialnum.value)  {											 
				alert("产品" + pn.value + "输入新序列号重复 ，请检查！");
				nqzserialnum.focus();
				return;
				}
					
			}
		}
			 					
			document.YkrkForm.submit(); 
		  
		}
		


 							
	    
		
	 

      	
    function addTr(){
        var otr = document.getElementById("kfdbTable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="ykrkProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="ykrkProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="ykrkProducts['+curId+'].product_xh" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="oqz_serial_num_'+curId+'" name="ykrkProducts['+curId+'].oqz_serial_num" size="15" readonly>';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="nqz_serial_num_'+curId+'" name="ykrkProducts['+curId+'].nqz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="ykrkProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		         
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="product_remark_'+curId+'" name="ykrkProducts['+curId+'].product_remark"  maxlength="50">';                       

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
		var destination = "selShkcByzy.html?openerId="+id;
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
		var url = "importYkrkSerial.html?openerId="+vl;
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

	
</script>
</head>
<body onload="initFzrTip();">
<form name="YkrkForm" action="saveYkrk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">移库入库</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2">
		<input type="text" name="ykrk.id" id="id" value="<%=StringUtils.nullToStr(ykrk.getId()) %>" size="30" maxlength="50" readonly><font color="red">*</font>
		</td>	
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="ykrk.rk_date" id="rk_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">调入仓库</td>
		<td class="a2">
			<select name="ykrk.rk_store_id" id="rk_store_id">
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
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"length="20"  onblur="setValue();" />
            <div   id="brandTip"  style="height:12px;position:absolute;left:146px; top:141px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="ykrk.jsr" id="fzr" /><font color="red">*</font>
		</td>
		 
	</tr>
	 
	<tr>			
		
		<td class="a1">状态</td>
		<td class="a2" colspan="3">
			<select name="ykrk.state" id="state">
				<option value="已保存">已保存</option>
				<option value="已提交">已提交</option>
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
	<!-- 
	<tr height="35">
		<td class="a2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
		注：输入产品序列号回车，自动提取产品信息
		</td>
	</tr> -->		
</table>
<table width="100%"  align="center" id="kfdbTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>旧序列号</td>
		<td>新序列号</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="ykrkProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="ykrkProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="ykrkProducts[<%=i %>].product_xh" readonly></td>
		<td class="a2"> <input type="text" id="oqz_serial_num_<%=i %>" name="ykrkProducts[<%=i %>].oqz_serial_num" value="" size="15" readonly></td>
		<td class="a2">
			<input type="text" id="nqz_serial_num_<%=i %>" name="ykrkProducts[<%=i %>].nqz_serial_num" value="" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="ykrkProducts[<%=i %>].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="product_remark_<%=i %>" name="ykrkProducts[<%=i %>].product_remark" maxlength="50"></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
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
			<textarea rows="6" name="ykrk.remark" id="remark" style="width:75%" maxlength="500"></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
