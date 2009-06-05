<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String msg = StringUtils.nullToStr(VS.findValue("msg"));
Clients client = (Clients)VS.findValue("client");

List storeList = (List)VS.findValue("storeList");
Ckd ckd = (Ckd)VS.findValue("ckd");
List ckdProducts = (List)VS.findValue("ckdProducts");

String[] ysfsArry = (String[])VS.findValue("ysfs");

List userList = (List)VS.findValue("userList");

int count = 2;
if(ckdProducts!=null && ckdProducts.size()>0){
	count = ckdProducts.size();
}

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

String lxr = "",address = "";
if(client != null){
	lxr = StringUtils.nullToStr(client.getLxr());
	address = StringUtils.nullToStr(client.getAddress());
}

%>

<html>
<head>
<title>出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript">

	<%if(!msg.equals("")){ %>
		alert("<%=msg%>");
		window.close();
		opener.document.myform.submit();
	<%}%>

	var allCount = <%=count %>;
	var iscs_flag = '<%=iscs_flag %>';
	
	function saveInfo(){	
		if(document.getElementById("ckd_id").value == ""){
			alert("出库单编号不能为空，请填写！");
			return;
		}
		if(document.getElementById("client_name").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}

		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}	
		
		if(document.getElementById("fzr").value == ""){
			alert("出库经手人不能为空，请选择！");
			return;
		}								
		
		if(document.getElementById("state").value == "已出库"){
			if(document.getElementById("ck_date").value == ""){
				alert("出库时间不能为空，请选择！");
				return;
			}
		}
		if(document.getElementById("xsd_id").value == ""){
			alert("销售单编号不能为空，请输入！");
			return;
		}
		if(document.getElementById("ysfs").value == ""){
			alert("运输方式不能为空，请选择！");
			return;
		}
		if(document.getElementById("job_no").value == ""){
			alert("货单号不能为空，请填写！");
			return;
		}
		if(document.getElementById("cx_tel").value == ""){
			alert("查询电话不能为空，请填写！");
			return;
		}		
		if(document.getElementById("send_time").value == ""){
			alert("发货时间不能为空，请填写！");
			return;
		}						

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
		document.ckdForm.btnSub.disabled = true;
		document.ckdForm.submit();
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
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(){
		var destination = "selCkdProc.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}

	
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.ckdForm.proc_id.length;i++){
			var o = document.ckdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.ckdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择产品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("remark_" + sel).value = "";
	}
	
	//退回订单
	function doTh(){
		if(window.confirm("确认要退回订单吗？")){
			document.ckdForm.action = "doCkdTh.html";
			document.ckdForm.btnTh.disabled = true;
			document.ckdForm.submit();
		}
	}
</script>
</head>
<body >
<form name="ckdForm" action="updateCkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出库单基本信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">出库单编号</td>
		<td class="a2" width="35%"><input type="text" name="ckd.ckd_id" id="ckd_id" value="<%=StringUtils.nullToStr(ckd.getCkd_id()) %>" readonly size="30">
		</td>
		<%
		String cjsj = StringUtils.nullToStr(ckd.getCreatdate());
		if(cjsj.equals("")){
			cjsj = DateComFunc.getToday();
		}
		%>
		<td class="a1">创建日期</td>
		<td class="a2"><input type="text" name="ckd.creatdate" id="creatdate" value="<%=cjsj %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('creatdate')); return false;">
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">		
		<input type="text" name="ckd.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.getClient_name())) %>" readonly size="30" maxlength="50">
		<input type="hidden" name="ckd.client_name" id="client_id" value="<%=StringUtils.nullToStr(ckd.getClient_name()) %>">
		</td>
		<td class="a1">联系人</td>
		<td class="a2"><input type="text" name="client_lxr" id="client_lxr" value="<%=lxr %>" readonly>				
	</tr>
	<tr>
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="ckd.tel" id="tel" value="<%=StringUtils.nullToStr(ckd.getTel()) %>" readonly>	
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="client_address" id="client_address" value="<%=address %>" readonly>			
	</tr>
	<tr>	
		<td class="a1" width="15%">销售订单编号</td>
		<td class="a2"><input type="text" name="ckd.xsd_id" id="xsd_id" value="<%=StringUtils.nullToStr(ckd.getXsd_id()) %>" size="30" readonly maxlength="20"></td>				
		<td class="a1" width="15%">销售负责人</td>
		<td class="a2">
			<input type="text" name="xsry_name" id="xsry_name" value="<%=StaticParamDo.getRealNameById(ckd.getXsry()) %>" readonly maxlength="20">
			<input type="hidden" name="ckd.xsry" id="xsry" value="<%=StringUtils.nullToStr(ckd.getXsry()) %>" size="30" readonly maxlength="20">	
			<input type="hidden" name="ckd.skzt" id="skzt" value="<%=StringUtils.nullToStr(ckd.getSkzt()) %>">	
		</td>			
	</tr>	
</table>	
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">物流信息</td>
	</tr>
	</thead>
	<tr>	
		<td class="a1" width="15%">出货库房</td>
		<td class="a2" width="35%">
			<select name="ckd.store_id" id="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(ckd.getStore_id()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">出库经手人</td>
		<td class="a2" width="35%">
			<select name="ckd.fzr" id="fzr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String id = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(ckd.getFzr()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>	<font color="red">*</font>		
		</td>						
	</tr>		
	<tr>
		<td class="a1" width="15%">物流状态</td>
		<td class="a2" width="35%">
			<select name="ckd.state" id="state">
				<option value="待出库" <%if(StringUtils.nullToStr(ckd.getState()).equals("待出库")) out.print("selected"); %>>待出库</option>
				<option value="已出库" <%if(StringUtils.nullToStr(ckd.getState()).equals("已出库")) out.print("selected"); %>>已出库</option>
			</select>	<font color="red">*</font>		
		</td>
		<%
		String cksj = StringUtils.nullToStr(ckd.getCk_date());
		if(cksj.equals("")){
			cksj = DateComFunc.getToday();
		}
		%>	
		<td class="a1">出库日期</td>
		<td class="a2"><input type="text" name="ckd.ck_date" id="ck_date" value="<%=cksj %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('ck_date')); return false;">
		</td>		
	</tr>	
	<tr>	
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%">
			<select name="ckd.ysfs" id="ysfs">
				<option value=""></option>
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(ysfsArry[i].equals(ckd.getYsfs())) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	<font color="red">*</font>	
		</td>
		<td class="a1" width="15%">货单号</td>
		<td class="a2"><input type="text" name="ckd.job_no" id="job_no" value="<%=StringUtils.nullToStr(ckd.getJob_no()) %>" size="30"  maxlength="20"><font color="red">*</font>	</td>										
	</tr>
	<tr>
		<td class="a1" width="15%">查询电话</td>
		<td class="a2"><input type="text" name="ckd.cx_tel" id="cx_tel" value="<%=StringUtils.nullToStr(ckd.getCx_tel()) %>" size="30"  maxlength="20"><font color="red">*</font>	</td>						
		<td class="a1" width="15%">发货时间</td>
		<td class="a2"><input type="text" name="ckd.send_time" id="send_time" value="<%=StringUtils.nullToStr(ckd.getSend_time()) %>" size="30" maxlength="20"><font color="red">*</font>	</td>										
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
<table width="100%"  align="center" id="cktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(ckdProducts!=null && ckdProducts.size()>0){
	for(int i=0;i<ckdProducts.size();i++){
		Map ckdProduct = (Map)ckdProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" size="20" name="ckdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(ckdProduct.get("product_name")) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="ckdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(ckdProduct.get("product_id")) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" size="20" name="ckdProducts[<%=i %>].product_xh" size="10" value="<%=StringUtils.nullToStr(ckdProduct.get("product_xh")) %>" readonly></td>	
		<td class="a2"><input type="text" id="nums_<%=i %>" name="ckdProducts[<%=i %>].nums" value="<%=StringUtils.nullToStr(ckdProduct.get("nums")) %>" readonly size="10"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="ckdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(ckdProduct.get("qz_serial_num")) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="ckdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(ckdProduct.get("qz_flag")) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="remark_<%=i %>" size="20" name="ckdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(ckdProduct.get("remark")) %>"></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="ckd.ms" id="ms" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(ckd.getMs()) %></textarea>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnTh" value="退回订单" class="css_button3" onclick="doTh();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
