<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
String thd_id = (String)VS.findValue("thd_id");

Thd thd = (Thd)VS.findValue("thd");
List thdProducts = (List)VS.findValue("thdProducts");

String msg = StringUtils.nullToStr(VS.findValue("msg"));

int counts = 2;
if(thdProducts != null && thdProducts.size()>0){
	counts = thdProducts.size() - 1;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售退货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=counts %>;
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已入库";
		}
		
		if(document.getElementById("thd_id").value == ""){
			alert("退货单编号不能为空！");
			return;
		}
		if(document.getElementById("th_date").value == ""){
			alert("退货日期不能为空！");
			return;
		}					

		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		var rdHyks = document.getElementsByName("rd_hyk");
		var vlHyk = "";
		for(var i=0;i<rdHyks.length;i++){
			if(rdHyks[i].checked == true){
				vlHyk = rdHyks[i].value;
				break;
			}
		}
		if(vlHyk == "1" && document.getElementById("hyk_id").value == ""){
			alert("会员卡号不能为空，请输入");
			return;
		}
		
		if(document.getElementById("client_id").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("store_id").value == ""){
			alert("退货库房不能为空，请选择！");
			return;
		}
		if(document.getElementById("remark").value == ""){
			alert("退货原因不能为空！");
			return;
		}	
		if(document.getElementById("type").value == "现金"){
			if(document.getElementById("skzh").value == ""){
				alert("退款账号不能为空，请选择！");
				return;
			}
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
					
		hj();

		if(document.getElementById("state").value == "已入库"){
			if(window.confirm("确认要入库吗，入库后将无法修改！")){
				document.thdForm.submit();		
			}else{
				return;
			}			
		}else{
			document.thdForm.submit();	
		}
		
		document.thdForm.btnSave.disabled = true;
		document.thdForm.btnSub.disabled = true;
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
		
		var url = "importRkSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}		
      	
    function addTr(){
        var otr = document.getElementById("thtable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;

        var otd=document.createElement("td");
		otd.className = "a2";
		otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="thdProducts['+curId+'].product_name" readonly style="width:90%"><input type="hidden" id="product_id_'+curId+'" name="thdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="thdProducts['+curId+'].product_xh" style="width:90%" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="th_price_'+curId+'" name="thdProducts['+curId+'].th_price" style="width:90%;text-align:right" value="0.00" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="thdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="thdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="sd_'+curId+'" name="thdProducts['+curId+'].sd"  value="0.00"><input type="hidden" id="gf_'+curId+'" name="thdProducts['+curId+'].gf"  value="0.00"><input type="hidden" id="ds_'+curId+'" name="thdProducts['+curId+'].ds"  value="0.00"><input type="hidden" id="basic_ratio_'+curId+'" name="thdProducts['+curId+'].basic_ratio"  value="0.00"><input type="hidden" id="out_ratio_'+curId+'" name="thdProducts['+curId+'].out_ratio"  value="0.00"><input type="hidden" id="lsxj_'+curId+'" name="thdProducts['+curId+'].lsxj"  value="0.00"><input type="hidden" id="ygcbj_'+curId+'" name="thdProducts['+curId+'].ygcbj"  value="0.00"><input type="hidden" id="sfcytc_'+curId+'" name="thdProducts['+curId+'].sfcytc"  value="">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="thdProducts['+curId+'].nums" style="width:90%;text-align:center" value="0" onblur="hj();">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="thdProducts['+curId+'].qz_serial_num" style="width:120px" readonly  onclick="openSerialWin('+ curId +');"><input type="hidden" id="qz_flag_'+curId+'" name="thdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';               
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="xj_'+curId+'" name="thdProducts['+curId+'].xj" style="width:90%;text-align:right" value="0.00" readonly>';  
        
        otr.appendChild(otd); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7);
        otr.appendChild(otd4); 
    }	

	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.thdForm.proc_id.length;i++){
			var o = document.thdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.thdForm.proc_id[i].value;
				document.getElementById("product_name_" + sel).value = "";
				document.getElementById("product_id_" + sel).value = "";
				document.getElementById("product_xh_" + sel).value = "";
				document.getElementById("th_price_" + sel).value = "0.00";
				document.getElementById("cbj_" + sel).value = "0.00";
				document.getElementById("kh_cbj_" + sel).value = "0.00";
				document.getElementById("sd_" + sel).value = "0.00";
				document.getElementById("gf_" + sel).value = "0.00";
				document.getElementById("ds_" + sel).value = "0.00";
				document.getElementById("basic_ratio_" + sel).value = "0.00";
				document.getElementById("out_ratio_" + sel).value = "0.00";
				document.getElementById("lsxj_" + sel).value = "0.00";
				document.getElementById("ygcbj_" + sel).value = "0.00";
				document.getElementById("sfcytc_" + sel).value = "0.00";
				document.getElementById("nums_" + sel).value = "0";
				document.getElementById("qz_serial_num_" + sel).value = "";
				document.getElementById("qz_flag_" + sel).value = "";
				document.getElementById("xj_" + sel).value = "0.00";
			}
		}
		if(k == 0){
			alert("请选择商品明细！");
			return;
		}
	}    
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	
	function openWin(id){
		var destination = "selThdProcMx.html";
		var fea ='width=900,height=600,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}

	function selXsd(){
		if(document.getElementById('client_id').value == ""){
			alert("请先选择客户！");
			return;
		}
		
		var destination = "selXsd.html?creatdate1=&creatdate2=&client_name=" + document.getElementById('client_id').value;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联销售单',fea);			
	}
	
	function selLsd(){
		var destination = "selLsd.html";
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联销售单',fea);		
	}
	
	function hj(){
		var length = (document.getElementById('thtable').rows.length-2);
		
		var hjz = 0;
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("th_price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"退货价格")){
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
				xj.value = (parseFloat(price.value)  * parseFloat(nums.value)).toFixed(2);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}	
		
		var thdje = document.getElementById("thdje");
		thdje.value = hjz.toFixed(2);
		
	}
	
	function chgType(vl){
		if(vl == "冲抵往来"){
			document.getElementById("td_zh_label").style.display = "none";
			document.getElementById("td_zh_value").style.display = "none";
			document.getElementById("zhname").value = ""
			document.getElementById("skzh").value = ""			
		}else{
			document.getElementById("td_zh_label").style.display = "";
			document.getElementById("td_zh_value").style.display = "";		
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
	}	

	function initChgYwType(vl){
		var typeObj = document.getElementById("type"); 
		var typeValue = "<%=StringUtils.nullToStr(thd.getType()) %>";
		if(vl == "2"){
			//零售单
			document.getElementById("client_name").style.display = 'none';
			document.getElementById("client_id").style.display = '';
			document.getElementById("btnXsd").style.display = 'none';
			document.getElementById("btnLsd").style.display = '';

			typeObj.options.length = 0;
			var optionObj = new Option("现金","现金");
			if(typeValue == "现金") optionObj.selected = "selected";
			typeObj.options.add(optionObj);
			
			document.getElementById("tr_hyk").style.display = '';
		}else{
			//销售订单
			document.getElementById("client_name").style.display = '';
			document.getElementById("client_id").style.display = 'none';
			document.getElementById("btnXsd").style.display = '';
			document.getElementById("btnLsd").style.display = 'none';

			typeObj.options.length = 0;
			var optionObj = new Option("现金","现金");
			if(typeValue == "现金") optionObj.selected = "selected";
			typeObj.options.add(optionObj);

			optionObj = new Option("冲抵往来","冲抵往来");
			if(typeValue == "冲抵往来") optionObj.selected = "selected";
			typeObj.options.add(optionObj); 
			
			document.getElementById("tr_hyk").style.display = 'none';
			
			document.getElementById("client_id").value = "";
			document.getElementById("hyk_id").value = "";
			
			var rdHyks = document.getElementsByName("rd_hyk");
			var vlHyk = "";
			for(var i=0;i<rdHyks.length;i++){
				if(rdHyks[i].value == "0"){
					rdHyks[i].checked = true;
					break;
				}
			}
			document.getElementById("spanHykId").style.display = "none";
		}
	}	

	function chgYwType(vl){
		var typeObj = document.getElementById("type"); 
		if(vl == "2"){
			//零售单
			document.getElementById("client_name").style.display = 'none';
			document.getElementById("client_id").style.display = '';
			document.getElementById("btnXsd").style.display = 'none';
			document.getElementById("btnLsd").style.display = '';
			
			typeObj.options.length = 0;
			typeObj.options.add(new Option("现金","现金"));
			
			document.getElementById("tr_hyk").style.display = '';
		}else{
			//销售订单
			document.getElementById("client_name").style.display = '';
			document.getElementById("client_id").style.display = 'none';
			document.getElementById("btnXsd").style.display = '';
			document.getElementById("btnLsd").style.display = 'none';
			
			typeObj.options.length = 0;
			typeObj.options.add(new Option("现金","现金"));
			typeObj.options.add(new Option("冲抵往来","冲抵往来"));  
			
			document.getElementById("tr_hyk").style.display = 'none';
			
			document.getElementById("client_id").value = "";
			document.getElementById("hyk_id").value = "";
			
			var rdHyks = document.getElementsByName("rd_hyk");
			var vlHyk = "";
			for(var i=0;i<rdHyks.length;i++){
				if(rdHyks[i].value == "0"){
					rdHyks[i].checked = true;
					break;
				}
			}
			document.getElementById("spanHykId").style.display = "none";
		}
		document.getElementById("client_name").value = "";
		document.getElementById("client_id").value = "";
	}
	
	function chkHyk(vl) {
		if(vl == "1"){
			document.getElementById("spanHykId").style.display = "";
		}else{
			document.getElementById("spanHykId").style.display = "none";
			document.getElementById("hyk_id").value = "";
			document.getElementById("client_id").value = "";
		}
	}	
	
	function getHydainfo(){
		var hyk_id = dwr.util.getValue("hyk_id");
		if(hyk_id == ""){
			return;
		}

		dwrService.getHykdaById(hyk_id,setHydaInfo);		
	}
	
	function setHydaInfo(hykda){
		if(hykda != null && hykda.id != null){
			dwr.util.setValue("client_id",hykda.hymc);
		}else{
			alert("会员卡不存在，请检查");
			document.getElementById("hyk_id").focus();
			dwr.util.setValue("client_id","");
		}
	}	
</script>
</head>
<body onload="initFzrTip();initClientTip();chgKpTyle('<%=StringUtils.nullToStr(thd.getFplx()) %>');initChgYwType('<%=StringUtils.nullToStr(thd.getYw_type()) %>');">
<form name="thdForm" action="saveThd.html" method="post">
<input type="hidden" name="thd.xsd_id" id="xsd_id" value="">
<input type="hidden" name="thd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售退货单</td>
	</tr>
	</thead>
	<%if(!msg.equals("")){ %>
	<tr>
		<td class="a2" colspan="4"><font color="red"><%=msg %></font></td>		
	</tr>	
	<%} %>		
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2">
		<input type="text" name="thd.thd_id" id="thd_id" style="width:232px" value="<%=StringUtils.nullToStr(thd.getThd_id()) %>" size="30" maxlength="50" readonly>
		</td>	
		<td class="a1">退货日期</td>
		<td class="a2"><input type="text" name="thd.th_date" style="width:232px" id="th_date" value="<%=StringUtils.nullToStr(thd.getTh_date()) %>"  class="Wdate" onFocus="WdatePicker()">
		</td>	
	</tr>
	<tr>			
		<td class="a1">退货类型</td>
		<td class="a2">
			<select name="thd.yw_type" id="yw_type" onchange="chgYwType(this.value);" style="width:232px">
				<option value="1" <%if(StringUtils.nullToStr(thd.getYw_type()).equals("1")) out.print("selected"); %>>销售订单</option>
				<option value="2" <%if(StringUtils.nullToStr(thd.getYw_type()).equals("2")) out.print("selected"); %>>零售单</option>
			</select> <span style="color:red">*</span>
		</td>		
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input  id="brand"  type="text" mxlength="20" style="width:232px"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(thd.getTh_fzr() )%>"/> <font color="red">*</font>
         <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;"  ></div>
		    <input type="hidden" name="thd.th_fzr" id="fzr" value="<%=StringUtils.nullToStr(thd.getTh_fzr()) %>"/>	
		</td>		
	</tr>
	<tr id="tr_hyk" style="display: none;">
		<td class="a1" width="15%">会员卡</td>
		<td class="a2"  colspan="3">
			<input type="radio"  name="rd_hyk" id="rd_hyk"  value="1"  onclick="chkHyk(this.value);"/>有&nbsp;&nbsp;
			<input type="radio"  name="rd_hyk" id="rd_hyk"  value="0"  onclick="chkHyk(this.value);" checked="checked"/>无&nbsp;&nbsp;
			<span id="spanHykId" style="display: none;"><input type="text" name="thd.hyk_id" id="hyk_id" value=""  size="24"  title="输入会员卡号" onblur="getHydainfo();"/> <font color="red">*</font></span>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<input type="text" name="thd.client_id" id="client_name" style="width:232px" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(thd.getClient_name())) %>" size="30" maxlength="50" onblur="setClientValue();"><input type="text" name="thd.client_name" id="client_id" style="width:232px;display: none" value="<%=StringUtils.nullToStr(thd.getClient_name()) %>"> <font color="red">*</font>
		<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>		
		<td class="a1">退款方式</td>
		<td class="a2">
			<select name="thd.type" id="type" onchange="chgType(this.value);" style="width:232px">
				<option value="现金" <%if(StringUtils.nullToStr(thd.getType()).equals("现金")) out.print("selected"); %>>现金</option>
				<option value="冲抵往来" <%if(StringUtils.nullToStr(thd.getType()).equals("冲抵往来")) out.print("selected"); %>>冲抵往来</option>
			</select> <font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">退货库房</td>
		<td class="a2">
			<select name="thd.store_id" id="store_id" style="width:232px">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse storeHouse = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=storeHouse.getId() %>" <%if(storeHouse.getId().equals(thd.getStore_id())) out.print("selected"); %>><%=storeHouse.getName() %></option>
			<%
				}
			}
			%>
			</select> <font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">退货原因</td>
		<td class="a2">
			<input type="text" name="thd.remark" id="remark" value="<%=StringUtils.nullToStr(thd.getRemark()) %>" maxlength="100" style="width:232px"> <font color="red">*</font>
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="25%">商品名称</td>
		<td width="20%">规格</td>
		<td width="10%">退货价格</td>
		<td width="10%">数量</td>
		<td width="20%">强制序列号</td>
		<td width="10%">小计</td>
	</tr>
	</thead>
<%
if(thdProducts!=null && thdProducts.size()>0){
	for(int i=0;i<thdProducts.size();i++){
		ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" style="width:90%" value="<%=StringUtils.nullToStr(thdProduct.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(thdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" style="width:90%" value="<%=StringUtils.nullToStr(thdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" style="width:90%;text-align:right" value="<%=JMath.round(thdProduct.getTh_price()) %>" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj" value="<%=JMath.round(thdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(thdProduct.getKh_cbj()) %>">
			
			<input type="hidden" id="sd_<%=i %>" name="thdProducts[<%=i %>].sd"  value="<%=JMath.round(thdProduct.getSd()) %>">
			<input type="hidden" id="gf_<%=i %>" name="thdProducts[<%=i %>].gf"  value="<%=JMath.round(thdProduct.getGf()) %>">
			<input type="hidden" id="ds_<%=i %>" name="thdProducts[<%=i %>].ds"  value="<%=JMath.round(thdProduct.getDs()) %>">
			<input type="hidden" id="basic_ratio_<%=i %>" name="thdProducts[<%=i %>].basic_ratio"  value="<%=JMath.round(thdProduct.getBasic_ratio()) %>">
			<input type="hidden" id="out_ratio_<%=i %>" name="thdProducts[<%=i %>].out_ratio"  value="<%=JMath.round(thdProduct.getOut_ratio()) %>">			
			<input type="hidden" id="lsxj_<%=i %>" name="thdProducts[<%=i %>].lsxj"  value="<%=JMath.round(thdProduct.getLsxj()) %>">
			<input type="hidden" id="ygcbj_<%=i %>" name="thdProducts[<%=i %>].ygcbj"  value="<%=JMath.round(thdProduct.getYgcbj()) %>">
			<input type="hidden" id="sfcytc_<%=i %>" name="thdProducts[<%=i %>].sfcytc"  value="<%=StringUtils.nullToStr(thdProduct.getSfcytc()) %>">				
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums" style="width:90%;text-align:center" value="<%=StringUtils.nullToStr(thdProduct.getNums()) %>" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="thdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(thdProduct.getQz_serial_num()) %>" style="width:120px" readonly onclick="openSerialWin('<%=i %>');">
			<input type="hidden" id="qz_flag_<%=i %>" name="thdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(thdProduct.getQz_flag()) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj" style="width:90%;text-align:right" value="<%=JMath.round(thdProduct.getXj()) %>" readonly></td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" style="width:90%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" style="width:90%" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" style="width:90%;text-align:right" value="0.00" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj"  value="0.00">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj"  value="0.00">
			
			<input type="hidden" id="sd_<%=i %>" name="thdProducts[<%=i %>].sd"  value="0.00">
			<input type="hidden" id="gf_<%=i %>" name="thdProducts[<%=i %>].gf"  value="0.00">
			<input type="hidden" id="ds_<%=i %>" name="thdProducts[<%=i %>].ds"  value="0.00">
			<input type="hidden" id="basic_ratio_<%=i %>" name="thdProducts[<%=i %>].basic_ratio"  value="0.00">
			<input type="hidden" id="out_ratio_<%=i %>" name="thdProducts[<%=i %>].out_ratio"  value="0.00">	
			<input type="hidden" id="lsxj_<%=i %>" name="thdProducts[<%=i %>].lsxj"  value="0.00">
			<input type="hidden" id="ygcbj_<%=i %>" name="thdProducts[<%=i %>].ygcbj"  value="0.00">
			<input type="hidden" id="sfcytc_<%=i %>" name="thdProducts[<%=i %>].sfcytc"  value="">				
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums" value="0" style="width:90%;text-align:center" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="thdProducts[<%=i %>].qz_serial_num" style="width:120px" readonly onclick="openSerialWin('<%=i %>');">
			<input type="hidden" id="qz_flag_<%=i %>" name="thdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>			
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj" value="0.00" style="width:90%;text-align:right" readonly></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button2" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
			<input type="button" name="btnXsd" id="btnXsd" value="关联销售订单" class="css_button3" onclick="selXsd();">
			<input type="button" name="btnLsd" id="btnLsd" value="关联零售单" style="display:none" class="css_button3" onclick="selLsd();">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="15%">合计退款金额</td>
		<td class="a2"><input type="text" id="thdje"  name="thd.thdje" value="<%=JMath.round(thd.getThdje()) %>" style="width:232px" readonly></td>	
		
		<td class="a1" id="td_zh_label" width="15%">退款账户</td>
		<td class="a2" id="td_zh_value" width="35%"><input type="text" id="zhname" onclick="openAccount();" name="zhname" style="width:232px" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(thd.getTkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="thd.tkzh" value="<%=StringUtils.nullToStr(thd.getTkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
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
		<td class="a2" width="35%">
			<select name="thd.fplx" id="fplx" onchange="chgKpTyle(this.value);" style="width:232px">
				<option value="出库单" <%if(StringUtils.nullToStr(thd.getFplx()).equals("出库单")) out.print("selected"); %>>出库单</option>
				<option value="普通发票" <%if(StringUtils.nullToStr(thd.getFplx()).equals("普通发票")) out.print("selected"); %>>普通发票</option>
				<option value="增值发票" <%if(StringUtils.nullToStr(thd.getFplx()).equals("增值发票")) out.print("selected"); %>>增值发票</option>
			</select>
		</td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2" width="35%"><input type="text" name="lsd.kp_mc" id="kp_mc" value="<%=StringUtils.nullToStr(thd.getKp_mc()) %>" style="width:232px" maxlength="50"></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><input type="text" name="thd.kp_address" id="kp_address" value="<%=StringUtils.nullToStr(thd.getKp_address()) %>" style="width:232px" maxlength="50"></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><input type="text" name="thd.kp_dh" id="kp_dh" value="<%=StringUtils.nullToStr(thd.getKp_dh()) %>" style="width:232px" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><input type="text" name="thd.khhzh" id="khhzh" value="<%=StringUtils.nullToStr(thd.getKhhzh()) %>" style="width:232px" maxlength="50"></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><input type="text" name="thd.sh" id="sh" value="<%=StringUtils.nullToStr(thd.getSh()) %>" style="width:232px" maxlength="50"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">发票信息摘要</td>
		<td class="a2" colspan="3">
			<input type="text" name="thd.fpxx" id="fpxx" value="<%=StringUtils.nullToStr(thd.getFpxx()) %>" maxlength="100" style="width:80%">
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="入 库" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>