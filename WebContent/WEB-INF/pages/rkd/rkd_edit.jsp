<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
Rkd rkd = (Rkd)VS.findValue("rkd");
List rkdProducts = (List)VS.findValue("rkdProducts");

int counts = 0;
if(rkdProducts != null && rkdProducts.size()>0){
	counts = rkdProducts.size();
}
List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>

<html>
<head>
<title>编辑入库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var allCount = <%=counts %>;
	
	function saveInfo(){
	    
		if(document.getElementById("rk_date").value == ""){
			alert("入库时间不能为空，选选择！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("仓管员不能为空，请选择！");
			return;
		}
		if(document.getElementById("state").value == "已入库"){
			if(document.getElementById("store_id").value == ""){
				alert("仓库不能为空，请选择！");
				return;
			}
			if(document.getElementById("rk_date").value == ""){
				alert("入库时间不能为空，请选择！");
				return;
			}			
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
		
		document.rkdForm.btnSub.disabled = true;
		document.rkdForm.submit();
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
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selectProduct.html?openerId="+id;
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	function opencgfzrWin()
	{
	    var destination = "selCgfzr.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'采购负责人',fea);	
	}	
	
	//退回订单
	function doTh(){
		if(window.confirm("确认要退回订单吗？")){
			document.rkdForm.action = "doRkdTh.html";
			document.rkdForm.btnTh.disabled = true;
			document.rkdForm.submit();
		}
	}		
	
</script>
</head>
<body onload="initFzrTip();">
<form name="rkdForm" action="updateRkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">入库单信息</td>
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
	<tr>
		<td class="a1" width="15%">入库单编号</td>
		<td class="a2" width="35%"><input type="text" name="rkd.rkd_id" id="rkd_id" value="<%=StringUtils.nullToStr(rkd.getRkd_id()) %>" readonly>
		</td>	
		<%
		String rksj = StringUtils.nullToStr(rkd.getRk_date());
		if(rksj.equals("")){
			rksj = DateComFunc.getToday();
		}
		%>
		<td class="a1">入库时间</td>
		<td class="a2"><input type="text" name="rkd.rk_date" id="rk_date" value="<%=rksj %>"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">仓库</td>
		<td class="a2" width="35%">
			<select name="rkd.store_id" id="store_id">
				<option value=""></option>
			<%
			String store_id = StringUtils.nullToStr(rkd.getStore_id());
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
			%>
				<option value="<%=id %>" <%if(store_id.equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>	<font color="red">*</font>	
		</td>
		<td class="a1" width="15%">仓管员</td>
		<td class="a2" width="35%">
		 <input id="brand" type="text" length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(rkd.getFzr()) %>"/> 
         <!--<img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          --><div id="brandTip" style="height:12px;position:absolute;left:580px; top:86px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="rkd.fzr" id="fzr" value="<%=rkd.getFzr()%>"/><font color="red">*</font>	
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">状态</td>
		<td class="a2" colspan="3">
			<select name="rkd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(rkd.getState()).equals("已保存")) out.print("selected");%>>已保存</option>
				<option value="已入库" <%if(StringUtils.nullToStr(rkd.getState()).equals("已入库")) out.print("selected");%>>已入库</option>
			</select>		
		</td>				
	</tr>
</table>
<br>	
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">相应订单信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">进货单编号</td>
		<td class="a2"><input type="text" name="rkd.jhd_id" id="jhd_id" value="<%=StringUtils.nullToStr(rkd.getJhd_id()) %>" size="30" maxlength="20" readonly></td>	
		<%
		String cjsj = StringUtils.nullToStr(rkd.getCreatdate());
		if(cjsj.equals("")){
			cjsj = DateComFunc.getToday();
		}
		%>		
		<td class="a1">创建时间</td>
		<td class="a2"><input type="text" name="rkd.creatdate" id="creatdate" value="<%=cjsj %>" readonly></td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2">
		<input type="text" name="rkd.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(rkd.getClient_name())) %>" size="30" maxlength="50" readonly>
		<input type="hidden" name="rkd.client_name" id="client_id" value="<%=StringUtils.nullToStr(rkd.getClient_name()) %>">
		</td>
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2">
		 	<input name="cgfzr_name" id="cgfzr_name" type="text" length="20" value="<%=StaticParamDo.getRealNameById(rkd.getCgfzr()) %>" readonly/> 
		    <input type="hidden" name="rkd.cgfzr" id="cgfzr" value="<%=rkd.getCgfzr()%>"/>
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
<table width="100%"  align="center" id="rktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="31%">产品名称</td>
		<td width="31%">产品规格</td>
		<td width="8%">数量</td>
		<td width="15%">强制序列号</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
<%
if(rkdProducts != null && rkdProducts.size()>0){
	for(int i=0;i<rkdProducts.size();i++){
		Map rkdProduct = (Map)rkdProducts.get(i);
		
		double price = rkdProduct.get("price")==null?0:((Double)rkdProduct.get("price")).doubleValue();
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="rkdProducts[<%=i %>].product_name" style="width:100%" readonly value="<%=StringUtils.nullToStr(rkdProduct.get("product_name")) %>">
			<input type="hidden" id="product_id_<%=i %>" name="rkdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(rkdProduct.get("product_id")) %>">
			<input type="hidden" id="price_<%=i %>"  name="rkdProducts[<%=i %>].price" value="<%=JMath.round(price) %>" readonly>
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" style="width:100%" name="rkdProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(rkdProduct.get("product_xh")) %>" readonly></td>
		<td class="a2"><input type="text" size="5" id="nums_<%=i %>" style="width:100%" name="rkdProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(rkdProduct.get("nums")) %>" readonly><input type="hidden" id="xj_<%=i %>" name="rkdProducts[<%=i %>].xj" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="rkdProducts[<%=i %>].qz_serial_num" style="width:85%"  value="<%=StringUtils.nullToStr(rkdProduct.get("qz_serial_num")) %>" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="rkdProducts[<%=i %>].qz_flag"  value="<%=StringUtils.nullToStr(rkdProduct.get("qz_flag")) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" size="10" id="remark_<%=i %>" style="width:100%" name="rkdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(rkdProduct.get("remark")) %>"></td>
	</tr>
<%
	}
}
%>	
</table>	
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="rkd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(rkd.getMs()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnTh" value="退回订单" class="css_button3" onclick="doTh();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>