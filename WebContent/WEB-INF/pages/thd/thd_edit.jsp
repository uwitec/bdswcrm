<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Thd thd = (Thd)VS.findValue("thd");
List thdProducts = (List)VS.findValue("thdProducts");

int counts = 2;
if(thdProducts != null && thdProducts.size() > 0){
	counts = thdProducts.size();
}
%>

<html>
<head>
<title>退货单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript">

	var allCount = <%=counts %>;
	
	function saveInfo(){
		if(document.getElementById("thd_id").value == ""){
			alert("退货单编号不能为空！");
			return;
		}
		if(document.getElementById("th_date").value == ""){
			alert("退货日期不能为空！");
			return;
		}					
		if(document.getElementById("client_name").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		if(document.getElementById("th_fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}		
		if(document.getElementById("skzh").value == ""){
			alert("退款账号不能为空，请选择！");
			return;
		}
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		//只有在状态为已入库时才进行强制序列号及入库库房的判断
		if(document.getElementById("state").value == "已入库"){
		
			if(document.getElementById("store_id").value == ""){
				alert("入库库房不能为空，请选择！");
				return;
			}		
		
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
		document.thdForm.submit();
	}
	
	
	function chgState(vl){
		var obj = document.getElementById("store_id");
		if(vl == "已入库"){
			obj.style.display = "";
		}else{
			obj.style.display = "none";
			obj.style.value = "";
		}
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
      	

	
	function openWin(id){
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}		

	
	function hj(){
		var length = ($('thtable').rows.length-2);
		
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
				xj.value = parseFloat(price.value)  * parseFloat(nums.value);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}	
		
		var thdje = document.getElementById("thdje");
		thdje.value = hjz;
		
	}	
	
</script>
</head>
<body >
<form name="thdForm" action="updateThd.html" method="post">
<input type="hidden" name="thd.xsd_id" id="xsd_id" value="<%=StringUtils.nullToStr(thd.getXsd_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">退货单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2">
		<input type="text" name="thd.thd_id" id="thd_id" value="<%=StringUtils.nullToStr(thd.getThd_id()) %>" size="30" maxlength="50" readonly>
		</td>	
		<td class="a1">退货日期</td>
		<td class="a2"><input type="text" name="thd.th_date" id="th_date" value="<%=StringUtils.nullToStr(thd.getTh_date()) %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('th_date')); return false;">
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<input type="text" name="thd.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(thd.getClient_name())) %>" size="30" maxlength="50" readonly>
		<input type="hidden" name="thd.client_name" id="client_id" value="<%=StringUtils.nullToStr(thd.getClient_name()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
			<select name="thd.th_fzr" id="th_fzr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String id = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(thd.getTh_fzr()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="a1">退款方式</td>
		<td class="a2">
			<select name="thd.type" id="type">
				<option value="现金" <%if(StringUtils.nullToStr(thd.getType()).equals("现金")) out.print("selected"); %>>现金</option>
				<option value="冲抵往来" <%if(StringUtils.nullToStr(thd.getType()).equals("冲抵往来")) out.print("selected"); %>>冲抵往来</option>
			</select>			
		</td>
		<td class="a1">状&nbsp;&nbsp;态</td>
		<td class="a2">
			<select name="thd.state" id="state" onchange="chgState(this.value);">
				<option value="已保存" <%if(StringUtils.nullToStr(thd.getState()).equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(thd.getState()).equals("已提交")) out.print("selected"); %>>提交</option>
				<option value="已入库" <%if(StringUtils.nullToStr(thd.getState()).equals("已入库")) out.print("selected"); %>>入库</option>
			</select>
			
			<%
			String cssStyle = "display:none";
			if(StringUtils.nullToStr(thd.getState()).equals("已入库")){
				cssStyle = "";
			}
			%>
			<!-- 选择库房 -->
			<select name="thd.store_id" id="store_id" style="<%=cssStyle %>" title="入库库房">
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
			</select>						
		</td>
	</tr>
	<tr>			
		
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>退货价格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>小计</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
if(thdProducts!=null && thdProducts.size()>0){
	for(int i=0;i<thdProducts.size();i++){
		ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(thdProduct.getProduct_name()) %>" readonly>
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(thdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" size="10" value="<%=StringUtils.nullToStr(thdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" size="10" value="<%=JMath.round(thdProduct.getTh_price()) %>" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj" value="<%=JMath.round(thdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(thdProduct.getKh_cbj()) %>">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums"  size="5" value="<%=StringUtils.nullToStr(thdProduct.getNums()) %>" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="thdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(thdProduct.getQz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="thdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(thdProduct.getQz_flag()) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj"  size="10" value="<%=JMath.round(thdProduct.getXj()) %>" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="thdProducts[<%=i %>].remark" maxlength="50" value="<%=StringUtils.nullToStr(thdProduct.getRemark()) %>"></td>
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
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" size="10" value="0.00" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj"  value="0.00">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj"  value="0.00">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj" value="0.00" size="10" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="thdProducts[<%=i %>].remark" maxlength="50"></td>
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
	<tr>
		<td class="a1" widht="20%">退款账户</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(thd.getTkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="thd.tkzh" value="<%=StringUtils.nullToStr(thd.getTkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1">合计退款金额</td>
		<td class="a2"><input type="text" id="thdje"  name="thd.thdje" value="<%=JMath.round(thd.getThdje()) %>" readonly></td>
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
			<textarea rows="3" name="thd.remark" id="remark" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(thd.getRemark()) %></textarea>
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
