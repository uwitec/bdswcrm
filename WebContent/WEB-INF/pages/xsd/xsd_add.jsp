<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
List posTypeList = (List)VS.findValue("posTypeList");

String[] ysfsArry = (String[])VS.findValue("ysfs");
String[] fkfsArry = (String[])VS.findValue("fkfs");

List xsdProducts = (List)VS.findValue("xsdProducts");
Xsd xsd = (Xsd)VS.findValue("xsd");


//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

int allCount = 2;
if(xsdProducts != null && xsdProducts.size()>0){
	allCount = xsdProducts.size() -1;
}

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");

String sp_state = StringUtils.nullToStr(xsd.getSp_state());

String sptg_flag = "";
if(sp_state.equals("3")){
	sptg_flag = "readonly";
}

String sp_type = StringUtils.nullToStr(xsd.getSp_type());

String spMsg = "";
if(sp_state.equals("1")){
	if(sp_type.equals("1")){
		spMsg = "客户存在超期应付款，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";
	}else if(sp_type.equals("2")){
		spMsg = "超出客户限额并且订单商品价格低于最低限价，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";	
	}else if(sp_type.equals("3")){
		spMsg = "超出客户限额，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";	
	}else if(sp_type.equals("4")){
		spMsg = "订单商品价格低于最低限价，需相关人员审批方可出库，提交审批请点击“提交审批”按钮，或修改价格后再次提交。";
	}
}
%>
<html>
<head>
<title>销售订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/xsd.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=allCount %>;
	var iscs_flag = '<%=iscs_flag %>';	
	
	//客户对应联系人信息
	var arryLxrObj = new Array();
	
	//客户联系人对象
	function LinkMan(id,name,tel){
	    this.id = id;
	    this.name = name;
	    this.tel = tel;
	}	
	
	//查询客户相关信息
	function setClientRegInfo(){		
		//填充值
		setClientValue(); 
		
		if($F('client_id') == ""){
			return;
		}
		
		//根据填充后的值选择客户地址联系人等信息
		var url = 'queryClientsRegInfo.html';
		var params = "clients_id=" + $F('client_id');
		var myAjax = new Ajax.Request(
		url,
		{
			method:'post',
			parameters: params,
			onComplete: fillClientRegInfo,
			asynchronous:true
		});
	}
	
	//处理客户地址联系人等信息
	function fillClientRegInfo(originalRequest){  

		var resText = originalRequest.responseText.trim(); 
		if(resText == ""){
			return false;
		}

		var arryText = resText.split("%");

		//客户地址填充
		if(arryText != null && arryText.length>0){
		
			var arryClientInfo = arryText[0].split("#");			
			document.getElementById("kh_address").value = arryClientInfo[0];

			if(document.getElementById("kp_mc").value == "") document.getElementById("kp_mc").value = arryClientInfo[1];
			if(document.getElementById("kp_address").value == "") document.getElementById("kp_address").value = arryClientInfo[2];
			if(document.getElementById("kp_dh").value == "") document.getElementById("kp_dh").value = arryClientInfo[3];
			if(document.getElementById("khhzh").value == "") document.getElementById("khhzh").value = arryClientInfo[4];
			if(document.getElementById("sh").value == "") document.getElementById("sh").value = arryClientInfo[5];
		}
		
		if(arryText != null && arryText.length>1){
			var linkMantext = arryText[1];
			
			//联系人填充
			var objLxr = document.getElementById("kh_lxr"); 
			objLxr.options.length = 0;
			
			var arryLinkMan = linkMantext.split("$");
			if(arryLinkMan.length > 0){
				for(var i=0;i<arryLinkMan.length;i++){
					var arryInfo = arryLinkMan[i].split("#");		
					var manObj = new LinkMan(arryInfo[0],arryInfo[1],arryInfo[2]);
					arryLxrObj[i] = manObj;
					objLxr.add(new Option(arryInfo[1],arryInfo[1]));
				}
				if(arryLxrObj[0].tel != undefined)
					document.getElementById("kh_lxdh").value = arryLxrObj[0].tel;
			}		
		}
	}
	
	
	//联系人与电话联动
	function chgLxr(vl){
		if(vl == ""){
			return;
		}
		if(arryLxrObj != null && arryLxrObj.length > 0){
			for(var i=0;i<arryLxrObj.length;i++){
				if(vl == arryLxrObj[i].name){
					document.getElementById("kh_lxdh").value = arryLxrObj[i].tel;
					break;
				}
			}
		}
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();chgKpTyle('<%=StringUtils.nullToStr(xsd.getFplx()) %>');">
<form name="xsdForm" action="updateXsd.html" method="post">
<input type="hidden" name="xsd.sp_type" id="sp_type" value="<%=StringUtils.nullToStr(xsd.getSp_type()) %>">
<input type="hidden" name="xsd.sp_state" id="sp_state" value="<%=StringUtils.nullToStr(xsd.getSp_state()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售订单信息</td>
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
if(!spMsg.equals("")){
%>	
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=spMsg %></font></td>
	</tr>
<%				
}
%>		
	<tr>
		<td class="a1" width="15%">销售订单编号</td>
		<td class="a2" width="35%">
			<input type="text" name="xsd.id" id="id" value="<%=StringUtils.nullToStr(xsd.getId()) %>" size="30" readonly><font color="red">*</font>
		</td>
		<%
		String creatDate = StringUtils.nullToStr(xsd.getCreatdate());
		if(creatDate.equals("")){
			creatDate = DateComFunc.getToday();
		}
		%>		
		<td class="a1">创建时间</td>
		<td class="a2"><input type="text" name="xsd.creatdate" id="creatdate" value="<%=creatDate %>"  class="Wdate" onFocus="WdatePicker()">
		</td>						 
	</tr>
	<tr>	
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<input type="text" name="xsd.client_id" onblur="setClientRegInfo();" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xsd.getClient_name())) %>" size="30" maxlength="50" >
		<input type="hidden" name="xsd.client_name" id="client_id" value="<%=StringUtils.nullToStr(xsd.getClient_name()) %>"><!--
		<%
		if(!sp_state.equals("3")){
		%>
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		<%
		}
		%>
		--><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="xsd.kh_address" id="kh_address" value="<%=StringUtils.nullToStr(xsd.getKh_address()) %>"></td>	
	</tr>
	
	<tr>
		<td class="a1">客户联系人</td>
		<td class="a2">
			<select name="xsd.kh_lxr" id="kh_lxr" onchange="chgLxr(this.value);" style="width:90px">
				<%
				if(!StringUtils.nullToStr(xsd.getKh_lxr()).equals("")){
				%>
					<option value="<%=StringUtils.nullToStr(xsd.getKh_lxr()) %>" selected><%=StringUtils.nullToStr(xsd.getKh_lxr()) %></option>
				<%
				} 
				%>
			</select>
		</td>	
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="xsd.kh_lxdh" id="kh_lxdh" value="<%=StringUtils.nullToStr(xsd.getKh_lxdh()) %>"></td>
	</tr>
	<tr>
		
		
		<td class="a1" width="15%">客户付款类型</td>
		<td class="a2">
			<select name="xsd.sklx" id="sklx" onchange="changeSklx(this.value);">
				<option value=""></option>
				<option value="现结" <%if(StringUtils.nullToStr(xsd.getSklx()).equals("现结")) out.print("selected"); %>>现结</option>
				<option value="账期" <%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print("selected"); %>>账期</option>
			</select>&nbsp;&nbsp;
			<b id="lb_zq" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print(""); else out.print("display:none"); %>">设置账期(天)：</b>
			<input type="text" name="xsd.zq" id="zq" value="<%=xsd.getZq() %>" size="5" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print(""); else out.print("display:none"); %>" <%=sptg_flag %>>
			<b id="lb_xjd" style="display:none">现金点：</b>
			<input type="text" name="xsd.xjd" id="xjd" value="<%=xsd.getXjd() %>" size="5" style="display:none">
			<font color="red">*</font>
		</td>
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%"> 
			<%
		String[] ysfss = ysfsArry; 
		%>
			<select name="xsd.ysfs" id="ysfs">
				<option value=""></option>
			<%
			if(ysfss != null && ysfss.length > 0){
				for(int i =0;i<ysfss.length;i++){
			%>
				<option value="<%=ysfss[i] %>" <%if(ysfss[i].equals(StringUtils.nullToStr(xsd.getYsfs()))) out.print("selected"); %>><%=ysfss[i] %></option>
			<%
				}
			}
			%>
				
			</select>	
		</td>	
	</tr>
	<tr>
		<td class="a1"  width="15%">经手人</td>
		<td class="a2">
			<input  id="brand" type="text" length="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(xsd.getFzr()) %>"/> 
			<!--<img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
			--><div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<input type="hidden" name="xsd.fzr" id="fzr" value="<%=xsd.getFzr()%>"/><font color="red">*</font>		
		</td>
		<%
		String yfzfType = StringUtils.nullToStr(xsd.getYfzf_type());
		if(yfzfType.equals("")){
			yfzfType = "寄方付";
		}
		%>
		<td class="a1"  width="15%">运费支付</td>
		<td class="a2">
			<input type="radio" name="xsd.yfzf_type" value="寄方付" <%if(yfzfType.equals("寄方付")) out.print("checked"); %>>	寄方付
			<input type="radio" name="xsd.yfzf_type" value="收方付" <%if(yfzfType.equals("收方付")) out.print("checked"); %>>	收方付
		</td>							
	</tr>
	<tr>
		<td class="a1" width="15%">订单状态</td>
		<td class="a2" colspan="3">
			<select name="xsd.state" id="state" onchange="chgState(this.value);">
				<option value="已保存" <%if(StringUtils.nullToStr(xsd.getState()).equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(xsd.getState()).equals("已提交")) out.print("selected"); %>>提交</option>
				<!-- <option value="已出库" <%if(StringUtils.nullToStr(xsd.getState()).equals("已出库")) out.print("selected"); %>>出库</option> -->
			</select>
			
			<%
			String store_id = xsd.getStore_id();
			String diplayFlag = "display:none";
			if(StringUtils.nullToStr(xsd.getState()).equals("已出库")){
				diplayFlag = "";
			}
			%>
			<b id="lb_store_id" style="<%=diplayFlag %>">出货仓库：</b>
			<select name="xsd.store_id" id="store_id" style="<%=diplayFlag %>">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse storeHouse = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=storeHouse.getId() %>" <%if(storeHouse.getId().equals(store_id)) out.print("selected"); %>><%=storeHouse.getName() %></option>
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
<table width="100%"  align="center" id="xsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="35%">产品名称</td>
		<td width="30%">规格</td>
		<td width="10%">销售价格</td>
		<td width="10%">数量</td>
		<td width="10%">小计</td>
	</tr>
	</thead>
<%
if(xsdProducts!=null && xsdProducts.size()>0){
	for(int i=0;i<xsdProducts.size();i++){
		XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="xsdProducts[<%=i %>].product_name" style="width:100%" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="xsdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="xsdProducts[<%=i %>].product_xh" style="width:100%" size="10" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="<%=JMath.round(xsdProduct.getPrice()) %>" size="10" onblur="hj();" <%=sptg_flag %>>
			<input type="hidden" id="cbj_<%=i %>" name="xsdProducts[<%=i %>].cbj" value="<%=JMath.round(xsdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="xsdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(xsdProduct.getKh_cbj()) %>">
			<input type="hidden" id="jgtz_<%=i %>" name="xsdProducts[<%=i %>].jgtz" value="<%=JMath.round(xsdProduct.getJgtz()) %>" size="10" onblur="hj();">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="<%=xsdProduct.getNums() %>" <%=sptg_flag %> size="5" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="<%=JMath.round(xsdProduct.getXj()) %>" size="10" readonly>
			<input type="hidden" id="qz_serial_num_<%=i %>" name="xsdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(xsdProduct.getQz_serial_num()) %>" size="15" readonly>	
		</td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" style="width:100%" name="xsdProducts[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="xsdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" style="width:100%" name="xsdProducts[<%=i %>].product_xh" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="0.00" size="10" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="xsdProducts[<%=i %>].cbj" value="0.00">
			<input type="hidden" id="kh_cbj_<%=i %>" name="xsdProducts[<%=i %>].kh_cbj" value="0.00">
			<input type="hidden" id="jgtz_<%=i %>" name="xsdProducts[<%=i %>].jgtz" value="0.00" size="10" onblur="hj();">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="0.00" size="10" readonly>
			<input type="hidden" id="qz_serial_num_<%=i %>" name="xsdProducts[<%=i %>].qz_serial_num" value="" size="15" readonly>	
		</td>		
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<%
	if(!sp_state.equals("3")){
	%>
	<tr height="35">
		<td class="a2" colspan="4" width="100%">&nbsp;
			<input type="button" name="button1" value="添加产品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除产品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
	<%
	}
	%>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<%
	String skxxStyle = "";
	if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")){
		skxxStyle = "display:none";
	}
	%>	
	<tr height="35">	
		<td class="a1" width="15%">订单合计金额</td>
		<td class="a2">
			<input type="text" name="xsd.xsdje" id="xsdje" value="<%=JMath.round(xsd.getXsdje()) %>" readonly>
			<input type="hidden" name="xsd.xsdcbj" id="xsdcbj" value="<%=JMath.round(xsd.getXsdcbj()) %>">
			<input type="hidden" name="xsd.xsdkhcb" id="xsdkhcb" value="<%=JMath.round(xsd.getXsdkhcb()) %>">
			<input type="hidden" name="xsd.yhje" id="yhje" value="0.00">
			<input type="hidden" id="ysje"  name="xsd.ysje" value="0.00">元
		</td>
		<td class="a1" widht="20%" id="td_skzh_label" style="<%=skxxStyle %>">收款账户</td>				
		<td class="a2" id="td_skzh_value" style="<%=skxxStyle %>"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xsd.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="xsd.skzh" value="<%=StringUtils.nullToStr(xsd.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>		
	</tr>
	<tr id="tr_skxx" style="<%=skxxStyle %>">
		<td class="a1">本次收款金额</td>
		<td class="a2"><input type="text" id="skje"  name="xsd.skje" value="<%=JMath.round(xsd.getSkje()) %>">元</td>
		
		<td class="a1" widht="20%">收款方式</td>
		<td class="a2">
			<select name="xsd.skfs" id="skfs"  onchange="selSkfs(this.value);">
			<%
			if(fkfsArry != null && fkfsArry.length > 0){
				for(int i =0;i<fkfsArry.length;i++){
			%>
				<option value="<%=fkfsArry[i] %>" <%if(fkfsArry[i].equals(StringUtils.nullToStr(xsd.getSkfs()))) out.print("selected"); %>><%=fkfsArry[i] %></option>
			<%
				}
			}
			
			String khskfs = StringUtils.nullToStr(xsd.getSkfs());
			String cssStyle = "display:none";
			if(khskfs.equals("刷卡")){
				cssStyle = "";
			}
			%>
			</select>
			
			<select name="xsd.pos_id" id="pos_id" style="<%=cssStyle %>">
				<option value="">选择刷卡POS机</option>
			<%
			String pos_id = StringUtils.nullToStr(xsd.getPos_id());
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(pos_id.equals(posType.getId()+"")) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>				
			</select><font color="red">*</font>				
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
		<td class="a2">
			<select name="xsd.fplx" id="fplx" onchange="chgKpTyle(this.value);">
				<option value="出库单" <%if(StringUtils.nullToStr(xsd.getFplx()).equals("出库单")) out.print("selected"); %>>出库单</option>
				<option value="普通发票" <%if(StringUtils.nullToStr(xsd.getFplx()).equals("普通发票")) out.print("selected"); %>>普通发票</option>
				<option value="增值发票" <%if(StringUtils.nullToStr(xsd.getFplx()).equals("增值发票")) out.print("selected"); %>>增值发票</option>
			</select>
		</td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2"><input type="text" name="xsd.kp_mc" id="kp_mc" value="<%=StringUtils.nullToStr(xsd.getKp_mc()) %>" maxlength="50"></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><input type="text" name="xsd.kp_address" id="kp_address" value="<%=StringUtils.nullToStr(xsd.getKp_address()) %>" maxlength="50"></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><input type="text" name="xsd.kp_dh" id="kp_dh" value="<%=StringUtils.nullToStr(xsd.getKp_dh()) %>" maxlength="20"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><input type="text" name="xsd.khhzh" id="khhzh" value="<%=StringUtils.nullToStr(xsd.getKhhzh()) %>" maxlength="50"></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><input type="text" name="xsd.sh" id="sh" value="<%=StringUtils.nullToStr(xsd.getSh()) %>" maxlength="50"></td>		
	</tr>	
	
	<tr>
		<td class="a1" width="15%" id="fpxx1">发票信息摘要</td>
		<td class="a2" colspan="3" id="fpxx2">
			<input type="text" name="xsd.fpxx" id="fpxx" value="<%=StringUtils.nullToStr(xsd.getFpxx()) %>" style="width:75%" maxlength="100">
		</td>	
				
	</tr>
	<tr>
		<td class="a1" width="15%">备&nbsp;&nbsp;注</td>
		<td class="a2" width="85%" colspan="3">
			<input type="text" name="xsd.ms" id="ms" value="<%=StringUtils.nullToStr(xsd.getMs()) %>" style="width:75%" maxlength="100">
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<%
			if(!spMsg.equals("")){
			%>
			<input type="button" name="btnTjsp" value="提交审批" class="css_button3" onclick="submitInfo();">&nbsp;&nbsp;
			<%
			}
			%>
			<input type="reset" name="btnCz" value="重 置" class="css_button2">&nbsp;&nbsp;
			<input type="reset" name="btnClose" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();;">
		</td>
	</tr>
</table>
</form>
</body>
</html>