<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

List jhdProducts = (List)VS.findValue("jhdProducts");
Jhd jhd = (Jhd)VS.findValue("Jhd"); 

int counts = 2;
if(jhdProducts != null && jhdProducts.size()>0){
	counts = jhdProducts.size();
}
%>

<html>
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	
	var allCount = <%=counts %>;
	
	function saveInfo(){
		if(document.getElementById("client_id").value == ""){
			alert("供货单位不能为空，请选择！");
			return;
		}

		if(document.getElementById("fzr").value == ""){
			alert("采购负责人不能为空，请选择！");
			return;
		}

		if(document.getElementById("fkfs").value == ""){
			alert("付款方式不能为空，请选择！");
			return;
		}else if(document.getElementById("fkfs").value == "账期"){
			if(!InputValid(document.getElementById("zq"),1,"int",1,1,999,"账期限天数")){
				return;
			}
		}

		if(document.getElementById("state").value == "已入库"){
			if(document.getElementById("store_id").value == ""){
				alert("入库库房不能为空，请选择！");
				return;
			}
		}
		
		if(!InputValid(document.getElementById("fkje"),0,"float",0,1,99999999,"本次付款金额")){
			return;
		}
		
		if(document.getElementById("fkfs").value == "现金"){
			if(parseFloat(document.getElementById("fkje").value) != parseFloat(document.getElementById("total").value)){
				alert("本次付款金额不等于订单总金额，请检查！");
				return;
			}	
		
			if(document.getElementById("fkzh").value == ""){
				alert("付款账户不能为空！");
				return;
			}
		}else{
			if(parseFloat(document.getElementById("fkje").value) > parseFloat(document.getElementById("total").value)){
				alert("本次付款金额大于订单金额，请检查!");
				return;
			}
		}	
		
		if(document.getElementById("state").value == "已入库"){
			//判断是否存在强制输入序列号的产品没有输入序列号
			for(var i=0;i<allCount;i++){
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
		}
		
		hj();
		
		document.jhdForm.submit();
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
		
		var url = "importRkSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}	
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}
      	
     function addTr(){
        var otr = document.getElementById("jhtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="jhdProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="jhdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" size="10" id="product_xh_'+curId+'"  name="jhdProducts['+curId+'].product_xh" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" size="10" id="price_'+curId+'" name="jhdProducts['+curId+'].price" value="0.00" onblur="hj();">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" size="5" id="nums_'+curId+'" name="jhdProducts['+curId+'].nums" value="0" onblur="hj();">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="jhdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="jhdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
         
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" size="10" id="xj_'+curId+'" name="jhdProducts['+curId+'].xj" value="0.00" readonly>';  
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="jhdProducts['+curId+'].remark">';                       

		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7); 
        otr.appendChild(otd4); 
        otr.appendChild(otd5);
        otr.appendChild(otd6);               
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selectProduct.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openProvider(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择往来单位',fea);	
	}
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}		
	
		function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	function hj(){
		var length = ($('jhtable').rows.length-2);
		
		var hjz = 0;
		var cbjhj = 0;
		
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"采购价格")){
					price.focus();
					return;
				}
			}
			
			var nums = document.getElementById("nums_" + i);
			if(nums != null){
				if(!InputValid(nums,0,"int",0,1,99999999,"数量")){
					nums.focus();
					return;
				}
			}		
			
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null){
				xj.value = parseFloat(price.value) * parseFloat(nums.value);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}		
		
		var total = document.getElementById("total");
		
		total.value = hjz;
		
	}
	
	function chkFkfs(vl){
		var obj = document.getElementById("zq");
		if(vl == "账期"){
			obj.style.display = "";
		}else{
			obj.style.display = "none";
			obj.value = "0";
		}
	}

	function chkState(vl){
		var obj = document.getElementById("store_id");
		if(vl == "已入库"){
			obj.style.display = "";
		}else{
			obj.style.display = "none";
			obj.value = "";
		}
	}	
	
</script>
</head>
<body>
<form name="jhdForm" action="updateJhd.html" method="post" target="submitFram">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.id" id="id" value="<%=StringUtils.nullToStr(jhd.getId()) %>" readonly>	
		<td class="a1" width="15%">采购日期</td>
		<td class="a2" width="35%"><input type="text" name="jhd.cg_date" id="cg_date" value="<%=StringUtils.nullToStr(jhd.getCg_date()) %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('cg_date')); return false;">
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.gysmc" id="client_name" value="<%=StringUtils.nullToStr(jhd.getGysmc()) %>" size="35" readonly>
		<input type="hidden" name="jhd.gysbh" id="client_id" value="<%=StringUtils.nullToStr(jhd.getGysbh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openProvider();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2" width="35%">
			  <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%for(int i=0;i<userList.size();i++){Map map=(Map)userList.get(i); if(map.get("user_id").toString().equals(jhd.getFzr())) out.print(map.get("real_name"));} %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:612px; top:85px; width:132px;border:1px solid menu;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="jhd.fzr" id="fzr" value="<%=jhd.getFzr()%>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2">
		<%
		String fkfs = StringUtils.nullToStr(jhd.getFkfs());
		
		String fkfsStyle = "display:none";
		if(fkfs.equals("账期")){
			fkfsStyle = "";
		}
		%>		
			<select name="jhd.fkfs" id="fkfs" onchange="chkFkfs(this.value);">
				<option value=""></option>
				<option value="现金" <%if(fkfs.equals("现金")) out.print("selected"); %>>现金</option>
				<option value="账期" <%if(fkfs.equals("账期")) out.print("selected"); %>>账期</option>
			</select>
			<input type="text" name="jhd.zq" id="zq" value="<%=StringUtils.nullToStr(jhd.getZq()) %>" size="3" style="<%=fkfsStyle %>" title="账期天数"> 注：选择账期，请输入账期天数
		</td>
			
		<td class="a1" width="15%">进货单状态</td>
		<td class="a2" width="35%">
		<%
		String state = StringUtils.nullToStr(jhd.getState());
		String stateStyle = "display:none";
		if(stateStyle.equals("已入库")){
			stateStyle = "";
		}
		
		String store_id = StringUtils.nullToStr(jhd.getStore_id());
		%>
			<select name="jhd.state" id="state" onchange="chkState(this.value);">
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>提交</option>
				<option value="已入库" <%if(state.equals("已入库")) out.print("selected"); %>>入库</option>
			</select>
		
			<!-- 选择库房 -->
			<select name="jhd.store_id" id="store_id" style="<%=stateStyle %>" title="入库库房">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse storeHouse = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=storeHouse.getId() %>" <%if(store_id.equals(storeHouse.getId())) out.print("selected"); %>><%=storeHouse.getName() %></option>
			<%
				}
			}
			%>
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
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>采购价格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>小计</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
if(jhdProducts != null && jhdProducts.size()>0){
	for(int i=0;i<jhdProducts.size();i++){
		JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="jhdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_name()) %>" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="jhdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" name="jhdProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="price_<%=i %>" name="jhdProducts[<%=i %>].price" value="<%=JMath.round(jhdProduct.getPrice()) %>" onblur="hj();"></td>
		<td class="a2"><input type="text" size="5" id="nums_<%=i %>" name="jhdProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(jhdProduct.getNums()) %>" onblur="hj();"></td>
		
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="jhdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(jhdProduct.getQz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="jhdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(jhdProduct.getQz_flag()) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		
		<td class="a2"><input type="text" size="10" id="xj_<%=i %>" name="jhdProducts[<%=i %>].xj" value="<%=JMath.round(jhdProduct.getPrice() * jhdProduct.getNums()) %>" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="jhdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(jhdProduct.getRemark()) %>"></td>	
		<%
		if(i != 0){
		%>
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%
		}else{
		%>
		<td class="a2"></td>
		<%
		}
		%>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
		</td>
	</tr>
	
	<tr>
		<td class="a1">合计金额</td>
		<td class="a2">
			<input type="text" id="total"  name="jhd.total" value="<%=JMath.round(jhd.getTotal()) %>" readonly>
			<input type="hidden" id="yfje"  name="jhd.yfje" value="0.00">
		</td>
		<td class="a1">付款金额</td>
		<td class="a2"><input type="text" id="fkje"  name="jhd.fkje" value="<%=JMath.round(jhd.getFkje()) %>"></td>	
	</tr>
	<tr>
		<td class="a1" widht="20%">付款账户</td>
		<td class="a2" colspan="3"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(jhd.getFkzh()) %>" readonly>
		<input type="hidden" id="fkzh"  name="jhd.fkzh" value="<%=StringUtils.nullToStr(jhd.getFkzh()) %>" >
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>		
	<tr>
		<td class="a1" width="20%">描述信息</td>
		<td class="a2" width="80%" colspan="3">
			<textarea rows="3" name="jhd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(jhd.getMs()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<iframe name="submitFram" border="0" width="0" height="0"></iframe>
</form>
</body>
</html>

<!-- 修改 ----------------------------------------------------------------------------------------------------------------------->
<!-- searchBrand  查询相近的经手人-->
<!-- showResponse 展示-->
<!-- move 上下事件-->
<!-- down 鼠标按下事件-->
<!-- setValue 鼠标离开事件-->
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">
var tip = "";
function searchBrand()
{
    
	var url = 'getBrands.do';
	var params = "prefix=" + $F('brand');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponse,
		asynchronous:true
	});
}
var list;
function showResponse(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	list=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("brandTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tip != $("brand").value)
		{
			Element.show('brandTip');
		}
	}
	else
	{
		var bt = $("brandTip");
		bt.innerHTML = "";
		Element.hide('brandTip');
	}
}
function move(event)
{
	 var srcEl = Event.element(event);
	 var tipEl = $(srcEl.id + "Tip");
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
					 Element.hide(tipEl);
					 return ;
				}
			}
		}
		
	}
}
function  down(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("brandTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("brand").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var lists=new Array();
<%
  for(int i=0;i<userList.size();i++)
  {   
     Map map=(Map)userList.get(i); 
%>
   lists["<%=map.get("real_name")%>"]="<%=map.get("user_id")%>";
<%}%>
function setValue()
{
  if(document.getElementById("brand").value!="")
  {
    var brand =document.getElementById("brand").value;
    brand=brand.trim();
    if(brand in lists)
    {
      document.getElementById("fzr").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("fzr").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("fzr").value="";
  }
 
  Element.hide('brandTip')
}
String.prototype.trim = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
 

new Form.Element.Observer("brand",1, searchBrand);
Event.observe("brand", "keydown", move, false);
Event.observe("brandTip","mousedown",down,true);
 

</script>
<!-- 修改 ----------------------------------------------------------------------------------------------------------------------->
