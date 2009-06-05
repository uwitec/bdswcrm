<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String msg = StringUtils.nullToStr(VS.findValue("msg"));

List storeList = (List)VS.findValue("storeList");

String ckd_id = (String)VS.findValue("ckd_id");

List userList = (List)VS.findValue("userList");
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));
%>

<html>
<head>
<title>出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	<%if(!msg.equals("")){ %>
		alert("<%=msg%>");
		window.close();
		opener.document.myform.submit();
	<%}%>
	
	var allCount = 2;
	var iscs_flag = '<%=iscs_flag %>';
	
	//保存、提交等校验
	function saveInfo(){
	
		if(document.getElementById("ckd_id").value == ""){
			alert("出库单编号不能为空，请填写！");
			return;
		}
		if(document.getElementById("client_name").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
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

		if(iscs_flag == "1"){  //初始化完成后再做强制序列号校验
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

		document.ckdForm.submit();
	}
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}
    
    //添加一行
	function addTr(){
        var otr = document.getElementById("cktable").insertRow(-1);
		var curId = allCount + 1;   //curId一直加下去，防止重复
		allCount = allCount + 1;
		
		var otd9=document.createElement("td");
		otd9.className = "a2";
		otd9.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';        
		
		var otd0=document.createElement("td");
		otd0.className = "a2";
		otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" size="20" name="ckdProducts['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="ckdProducts['+curId+'].product_id">';
		
		var otd1 = document.createElement("td");
		otd1.className = "a2";
		otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'" size="20"  name="ckdProducts['+curId+'].product_xh" size="10" readonly>';
		
		
		var otd5 = document.createElement("td");
		otd5.className = "a2";
		otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="ckdProducts['+curId+'].nums" value="0" size="10">';
		
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="ckdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="ckdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		
		
		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="text" id="remark_'+curId+'" size="20" name="ckdProducts['+curId+'].remark">';                       
		
		otr.appendChild(otd9);
		otr.appendChild(otd0); 
		otr.appendChild(otd1); 
		otr.appendChild(otd5);
		otr.appendChild(otd7);
		otr.appendChild(otd6);   
                 
	}	
     
    //打开输入序列号窗口
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
    
    //删除一行
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	//打开选择出库单窗口
	function openWin(){
		var destination = "selCkdProc.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	//打开选择往来客户窗口
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}

	
	//清除产品明细信息
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
<body >
<form name="ckdForm" action="saveCkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出库单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">出库单编号</td>
		<td class="a2" width="35%"><input type="text" name="ckd.ckd_id" id="ckd_id" value="<%=ckd_id %>" readonly size="30">
		</td>	
		<td class="a1">创建时间</td>
		<td class="a2"><input type="text" name="ckd.creatdate" id="creatdate" value="<%=DateComFunc.getToday() %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('creatdate')); return false;">
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">		
		<input type="text" name="ckd.client_id" id="client_name" value="" readonly size="30" maxlength="50">
		<input type="hidden" name="ckd.client_name" id="client_id" value="">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		</td>		
		<td class="a1" width="15%">经手人</td>
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
				<option value="<%=id %>" <%if(user_id.equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>			
		</td>
	</tr>
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
				<option value="<%=id %>"><%=name %></option>
			<%
				}
			}
			%>
			</select>			
		</td>			
		<td class="a1" width="15%">物流状态</td>
		<td class="a2" width="35%">
			<select name="ckd.state" id="state">
				<option value="待出库">待出库</option>
				<option value="已出库">已出库</option>
			</select>		
		</td>		
	</tr>	
	<tr>
		<td class="a1">出库时间</td>
		<td class="a2"><input type="text" name="ckd.ck_date" id="ck_date" value="<%=DateComFunc.getToday() %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('ck_date')); return false;">
		</td>		
		<td class="a1" width="15%">销售单编号</td>
		<td class="a2"><input type="text" name="ckd.xsd_id" id="xsd_id" value="" size="30" maxlength="20"></td>		
	</tr>
	
	<tr>		
		<td class="a1" width="15%">销售负责人</td>
		<td class="a2" colspan="3">
			<select name="ckd.xsry" id="xsry">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String id = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=id %>" <%if(user_id.equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>
			<input type="hidden" name="ckd.skzt" id="skzt" value="">	
		</td>		
	</tr>		
</table>
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
		<td>选择</td>
		<td>产品名称</td>
		<td>规格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>备注</td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" size="20" name="ckdProducts[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="ckdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" size="20" name="ckdProducts[<%=i %>].product_xh" size="10" readonly></td>	
		<td class="a2"><input type="text" id="nums_<%=i %>" name="ckdProducts[<%=i %>].nums" value="0" size="10"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="ckdProducts[<%=i %>].qz_serial_num" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="ckdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
		<td class="a2"><input type="text" id="remark_<%=i %>" size="20" name="ckdProducts[<%=i %>].remark"></td>
	</tr>
<%
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;
			<input type="button" name="button1" value="添加产品明细" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除明细信息" class="css_button3" onclick="delDesc();">
			&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			<font color="red">注：输入产品序列号回车，自动提取产品信息。</font>
		</td>
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">描述信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="ckd.ms" id="ms" style="width:75%" maxlength="500"></textarea>
		</td>
	</tr>	
</table>
<br>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
