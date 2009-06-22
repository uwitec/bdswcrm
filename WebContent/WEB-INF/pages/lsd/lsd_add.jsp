<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");
List posTypeList = (List)VS.findValue("posTypeList");
String[] ysfsArry = (String[])VS.findValue("ysfs");

Lsd lsd = (Lsd)VS.findValue("lsd");
List lsdProducts = (List)VS.findValue("lsdProducts");

int counts = 2;
if(lsdProducts != null && lsdProducts.size()>0){
	counts = lsdProducts.size() - 1;
}

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");


String sp_state = StringUtils.nullToStr(lsd.getSp_state());
%>
<html>
<head>
<title>零售单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>

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

	var allCount = <%=counts %>;
	var iscs_flag = '<%=iscs_flag %>';
		
	function saveInfo(){
	 
		if(document.getElementById("xsry").value == ""){                                     <!-- -->
			alert("经手人不能为空，请选择！");
			return;
		}	
		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}			
		if(document.getElementById("client_name").value == ""){
			alert("客户姓名不能为空，请填写！");
			return;
		}
		if(document.getElementById("has_yushk").value=="是"){
			if(document.getElementById("yushk_id").value == ""){
				alert("预收款编号不能为空，请选择！");
				return;
			}
		}
		
		if(document.getElementById("fkfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}
				
		if(!InputValid(document.getElementById("skzh"),1,"string",0,1,100,"收款账户")){return;}	
		
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

		hj();
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交零售单吗，提交后将无法修改！")){
				document.lsdForm.btnSub.disabled = true;
				document.lsdForm.submit();		
			}			
		}else{
			document.lsdForm.btnSub.disabled = true;
			document.lsdForm.submit();	
		}
	}
	
	function submitInfo(){
		if(document.getElementById("xsry").value == ""){                                         <!-- -->
			alert("经手人不能为空，请选择！");
			return;
		}	
		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}
		if(document.getElementById("state").value == "已提交"){
			alert("零售单状态必须为已保存！");
			return;
		}					
		if(document.getElementById("client_name").value == ""){
			alert("客户姓名不能为空，请填写！");
			return;
		}
		if(document.getElementById("has_yushk").value=="是"){
			if(document.getElementById("yushk_id").value == ""){
				alert("预收款编号不能为空，请选择！");
				return;
			}
		}
		
		if(document.getElementById("fkfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}
		
		if(!InputValid(document.getElementById("skzh"),1,"string",0,1,100,"收款账户")){return;}	
			
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

		hj();

		document.lsdForm.action = "submitLsd.html";
		document.lsdForm.btnSp.disabled = true;
		document.lsdForm.submit();	

	}	
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}
      	
    function addTr(){
        var otr = document.getElementById("lsdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="lsdProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="lsdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="lsdProducts['+curId+'].product_xh" size="15" readonly>';

        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="price_'+curId+'" name="lsdProducts['+curId+'].price" value="0.00" size="7" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="lsdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="lsdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="gf_'+curId+'" name="lsdProducts['+curId+'].gf" value="0">';      
        
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="lsdProducts['+curId+'].nums" value="0" size="5" onblur="hj();">';
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="lsdProducts['+curId+'].xj" value="0.00" size="7" readonly>';        
        
        var otd9 = document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="lsdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="lsdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" id="remark_'+curId+'" name="lsdProducts['+curId+'].remark">';                       

		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3); 
        otr.appendChild(otd5);
        otr.appendChild(otd8); 
        otr.appendChild(otd9);
        otr.appendChild(otd6);
        otr.appendChild(otd7);        
                 
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selLsProcCkd.html?openerId="+id;
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
	
	function hj(){
		var length = ($('lsdtable').rows.length-2);
		
		var hjz = 0;
		var cbjhj = 0;
		var khcbjhj = 0;
		
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"销售价格")){
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
			
			var cbjz = document.getElementById("cbj_" + i);
			var khcbjz = document.getElementById("kh_cbj_" + i);
			
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null){
				xj.value = parseFloat(price.value) * parseFloat(nums.value);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
				
				cbjhj = parseFloat(cbjhj) + parseFloat(cbjz.value) * parseFloat(nums.value);
				khcbjhj = parseFloat(khcbjhj) + parseFloat(khcbjz.value) * parseFloat(nums.value)
			}
		}	
		var yhje = document.getElementById("yhje");
		if(!InputValid(yhje,0,"float",0,1,99999999,"优惠金额")){
			yhje.focus();
			return;
		}		
		
		var lsdje = document.getElementById("lsdje");
		var lsdcbj = document.getElementById("lsdcbj");
		var lsdkhcbj = document.getElementById("lsdkhcb");
		
		var skje = document.getElementById("skje");

		var yushkje = document.getElementById("yushkje");

		lsdje.value = parseFloat(hjz) - parseFloat(yhje.value);

		lsdcbj.value = cbjhj;
		lsdkhcbj.value = khcbjhj;
		skje.value = parseFloat(hjz) - parseFloat(yhje.value) - parseFloat(yushkje.value);

		
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
	
	function chgKpTyle(vD){
		var obj_mc1 = document.getElementById("mc1");
		var obj_mc2 = document.getElementById("mc2");
		
		var obj_dz1 = document.getElementById("dz1");
		var obj_dz2 = document.getElementById("dz2");
		
		var obj_dh1 = document.getElementById("dh1");
		var obj_dh2 = document.getElementById("dh2");
		
		var obj_zh1 = document.getElementById("zh1");
		var obj_zh2 = document.getElementById("zh2");
		
		var obj_sh1 = document.getElementById("sh1");
		var obj_sh2 = document.getElementById("sh2");
		
		if(vD == "出库单"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
			
			obj_dh1.style.display = "none";
			obj_dh2.style.display = "none";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "普通发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "增值发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "";
			obj_zh2.style.display = "";
			
			obj_sh1.style.display = "";
			obj_sh2.style.display = "";			
		}
		
		if(document.getElementById("fkfs").value == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
		}		
	}
	
	function selFkfs(vD){
		if(vD == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
	}	
</script>

</head>
<body  onload="chgKpTyle('<%=StringUtils.nullToStr(lsd.getFplx()) %>');">
<FORM  name="lsdForm" action="saveLsd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">零售单信息</td>
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
	
	<%
	if(sp_state.equals("1")){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red">零售单商品超出最低限价，需相关人员审批方可出库，提交审批请点击“提交审批”按钮，或修改价格后再次提交。</font></td>
	</tr>	
	<%	
	}
	%>		
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><input type="text" name="lsd.id" id="id" value="<%=StringUtils.nullToStr(lsd.getId()) %>" readonly></td>	
		<%
		String rq = StringUtils.nullToStr(lsd.getCreatdate());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">销售日期</td>
		<td class="a2"><input type="text" name="lsd.creatdate" id="creatdate" value="<%=rq %>" readonly>
		</td>		
	</tr>
	<tr>
		
		<td class="a1" width="15%">出货库房</td>
		<td class="a2">
			<select name="lsd.store_id" id="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(storeHouse.getId()) %>" <%if(StringUtils.nullToStr(storeHouse.getId()).equals(StringUtils.nullToStr(lsd.getStore_id()))) out.print("selected"); %>><%=StringUtils.nullToStr(storeHouse.getName()) %></option>
			<%
				}
			}
			%>
			</select>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		<!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"  type="text"   length="20"  onblur="setValue()"/> 
         <img id="Loadingimg" src="images/indicator.gif" style="display:none"/>
       
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div  id="brandTip" style=" height:12px; position:absolute;left:612px;top:85px; width:132px;border:1px solid menu;background-Color:#fff;display:none;"  >
          </div>
		    <input type="hidden" name="lsd.xsry" id="xsry" value=""/> 
		<!--修改 --------------------------------------------------------------------------------------  -->
		</td>					
	</tr>
	<tr>
		<td class="a1" width="15%">零售单状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="lsd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(lsd.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(lsd.getState()).equals("已提交")) out.print("selected"); %>>已成交</option>
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
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>销售价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>强制序列号</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
if(lsdProducts != null && lsdProducts.size()>0){
	for(int i=0;i<lsdProducts.size();i++){
		LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
		String flag2 = "否";
		if(!StringUtils.nullToStr(lsdProduct.getQz_serial_num()).equals("")){
			flag2 = "是";
		}
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="lsdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_name()) %>" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="lsdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="lsdProducts[<%=i %>].product_xh" size="15" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_xh()) %>" readonly></td>	
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="lsdProducts[<%=i %>].price" value="<%=JMath.round(lsdProduct.getPrice()) %>" size="7" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="lsdProducts[<%=i %>].cbj" value="<%=JMath.round(lsdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="lsdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(lsdProduct.getKh_cbj()) %>">
			<input type="hidden" id="gf_<%=i %>" name="lsdProducts[<%=i %>].gf" value="<%=lsdProduct.getGf() %>">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="lsdProducts[<%=i %>].nums" value="<%=lsdProduct.getNums() %>" size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="lsdProducts[<%=i %>].xj" value="<%=JMath.round(lsdProduct.getXj()) %>" size="7" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="lsdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(lsdProduct.getQz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="lsdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="remark_<%=i %>" name="lsdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(lsdProduct.getRemark()) %>"></td>
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
					<input type="text" id="product_name_<%=i %>" name="lsdProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
					<input type="hidden" id="product_id_<%=i %>" name="lsdProducts[<%=i %>].product_id">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="lsdProducts[<%=i %>].product_xh" size="15" readonly></td>	
				<td class="a2">
					<input type="text" id="price_<%=i %>" name="lsdProducts[<%=i %>].price" value="0.00" size="7" onblur="hj();">
					<input type="hidden" id="cbj_<%=i %>" name="lsdProducts[<%=i %>].cbj" value="0.00">
					<input type="hidden" id="kh_cbj_<%=i %>" name="lsdProducts[<%=i %>].kh_cbj" value="0.00">
					<input type="hidden" id="gf_<%=i %>" name="lsdProducts[<%=i %>].gf" value="0">
				</td>
				<td class="a2"><input type="text" id="nums_<%=i %>" name="lsdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
				<td class="a2"><input type="text" id="xj_<%=i %>" name="lsdProducts[<%=i %>].xj" value="0.00" size="7" readonly></td>
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="lsdProducts[<%=i %>].qz_serial_num" size="15" readonly>
					<input type="hidden" id="qz_flag_<%=i %>" name="lsdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
				<td class="a2"><input type="text" id="remark_<%=i %>" name="lsdProducts[<%=i %>].remark"></td>
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
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			<font color="red">注：输入产品序列号回车，可自动提取产品信息到产品列表中。</font>
		</td>
	</tr>
	<tr height="35">	
		<td class="a1">合计金额</td>
		<td class="a2" colspan="3">
			<input type="text" name="lsd.lsdje" id="lsdje" value="<%=JMath.round(lsd.getLsdje()) %>" readonly>
			<input type="hidden" name="lsd.lsdcbj" id="lsdcbj" value="<%=JMath.round(lsd.getLsdcbj()) %>" readonly>
			<input type="hidden" name="lsd.lsdkhcb" id="lsdkhcb" value="<%=JMath.round(lsd.getLsdkhcb()) %>" readonly>
			<input type="hidden" name="lsd.yhje" id="yhje" value="0.00">
		</td>
	</tr>
	<%
	String cssStyle = "display:none";
	if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("是")){
		cssStyle = "";
	}
	%>
	<tr height="35">
		<td class="a1">本次实收金额</td>
		<td class="a2">
			<input type="text" name="lsd.skje" id="skje" value="<%=JMath.round(lsd.getSkje()) %>" readonly>
		</td>	
		<td class="a1">是否存在预收款</td>
		<td class="a2">
			<select name="lsd.has_yushk" id="has_yushk" onchange="chgSel(this.value);">
				<option value="否" <%if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("否")) out.print("selected"); %>>否</option>
				<option value="是" <%if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("是")) out.print("selected"); %>>是</option>
			</select>
		</td>	
	</tr>
	<tr height="35" id="ysStyle" style="<%=cssStyle %>">	
		<td class="a1">预收款编号</td>
		<td class="a2">
			<input type="text" name="lsd.yushk_id" id="yushk_id" value="<%=StringUtils.nullToStr(lsd.getYushk_id()) %>" readonly>
			<input type="button" name="btnSel" value="选择" class="css_button2" onclick="openYushk();">
		</td>
		<td class="a1">预收金额</td>
		<td class="a2">
			<input type="text" name="lsd.yushkje" id="yushkje" value="<%=JMath.round(lsd.getYushkje()) %>" readonly>
		</td>		
	</tr>		
	<tr>
		<td class="a1" widht="20%">客户付款方式</td>
		<td class="a2">
			<select name="lsd.fkfs" id="fkfs" onchange="selFkfs(this.value);">
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(StringUtils.nullToStr(lsd.getFkfs()).equals(ysfsArry[i])) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	
			
			<select name="lsd.pos_id" id="pos_id" style="display:none">
				<option value="">选择刷卡POS机</option>
			<%
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(StringUtils.nullToStr(lsd.getFkfs()).equals(posType.getId())) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>
				
			</select>							
		</td>	
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsd.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="lsd.skzh" value="<%=StringUtils.nullToStr(lsd.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><font color="red">*</font>
		</td>
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
		<td class="a2" width="35%"><input type="text" name="lsd.client_name" id="client_name" value="<%=StringUtils.nullToStr(lsd.getClient_name()) %>" size="30"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="lsd.lxr" id="lxr" value="<%=StringUtils.nullToStr(lsd.getLxr()) %>"></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="lsd.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(lsd.getLxdh()) %>" maxlength="20"></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2"><input type="text" name="lsd.mobile" id="mobile" value="<%=StringUtils.nullToStr(lsd.getMobile()) %>" maxlength="20"></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2"><input type="text" name="lsd.address" id="address" value="<%=StringUtils.nullToStr(lsd.getAddress()) %>" size="30" maxlength="100"></td>			
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"><input type="text" name="lsd.mail" id="mail" value="<%=StringUtils.nullToStr(lsd.getMail()) %>" maxlength="50" size="30"></td>		
	</tr>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">发票类型</td>
		<td class="a2">
			<select name="lsd.fplx" id="fplx" onchange="chgKpTyle(this.value);">
				<option value="出库单" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("出库单")) out.print("selected"); %>>出库单</option>
				<option value="普通发票" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("普通发票")) out.print("selected"); %>>普通发票</option>
				<option value="增值发票" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("增值发票")) out.print("selected"); %>>增值发票</option>
			</select>
		</td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2"><input type="text" name="lsd.kp_mc" id="kp_mc" value="<%=StringUtils.nullToStr(lsd.getKp_mc()) %>" maxlength="50"></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><input type="text" name="lsd.kp_address" id="kp_address" value="<%=StringUtils.nullToStr(lsd.getKp_address()) %>" maxlength="50"></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><input type="text" name="lsd.kp_dh" id="kp_dh" value="<%=StringUtils.nullToStr(lsd.getKp_dh()) %>" maxlength="20"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><input type="text" name="lsd.khhzh" id="khhzh" value="<%=StringUtils.nullToStr(lsd.getKhhzh()) %>" maxlength="50"></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><input type="text" name="lsd.sh" id="sh" value="<%=StringUtils.nullToStr(lsd.getSh()) %>" maxlength="50"></td>		
	</tr>	
	
	<tr>
		<td class="a1" width="15%">发票信息摘要</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="lsd.fpxx" id="fpxx" style="width:75%"><%=StringUtils.nullToStr(lsd.getFpxx()) %></textarea>
		</td>	
				
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="2" name="lsd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(lsd.getMs()) %></textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;	
			<%
			if(sp_state.equals("1")){
			%>	
			<input type="button" name="btnSp" value="提交审批" class="css_button3" onclick="submitInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<%
			}
			%>
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>

</FORM>

</BODY>
</HTML>
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
      document.getElementById("xsry").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("xsry").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("xsry").value="";
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
 

