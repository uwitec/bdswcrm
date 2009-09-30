<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");

List userList = (List)VS.findValue("userList");

Kcpd kcpd = (Kcpd)VS.findValue("kcpd");
List kcpdDescs = (List)VS.findValue("kcpdDesc");

int count = 2;
if(kcpdDescs!=null && kcpdDescs.size()>0){
	count = kcpdDescs.size()-1;
}
%>

<html>
<head>
<title>添加库存盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	var allCount = <%=count %>;
	
	function saveInfo(){
		if(document.getElementById("store_id").value == ""){
			alert("仓库名称不能为空，请选择！");
			return;
		}		
		if(document.getElementById("pdrq").value == ""){
			alert("盘点日期不能为空，请填写！");
			return;
		}	
		if(document.getElementById("id").value == ""){
			alert("名称不能为空，请填写！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("盘点人不能为空，请填写！");
			return;
		}		
		
		pdAll();
		
		document.kcpdForm.submit();
	}
	

    function addTr(){
        var otr = document.getElementById("kcpdDescTable").insertRow(-1);

        //var curId = ($('kcpdDescTable').rows.length-2);
		var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;        
        
		var otd9=document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';        
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="kcpdDesc['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="kcpdDesc['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="kcpdDesc['+curId+'].product_xh" readonly>';
        
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="kc_nums_'+curId+'" name="kcpdDesc['+curId+'].kc_nums" value="0" size="5" onblur="pdAll();">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="sj_nums_'+curId+'" name="kcpdDesc['+curId+'].sj_nums" value="0" size="5" onblur="pdAll();">';        
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="yk_'+curId+'" name="kcpdDesc['+curId+'].yk" value="0" size="5">';         
        
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="kcpdDesc['+curId+'].remark">';                       
		
		otr.appendChild(otd9); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd4);
        otr.appendChild(otd5);     
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(){
	
		if(document.getElementById("store_id").value == ""){
			alert("请先填写仓库名称！");
			return;
		}
		
		var destination = "selKcProc.html?store_id=" + document.getElementById("store_id").value;
		var fea ='width=850,height=500,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择库存产品',fea);	
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
			
	

	function pdAll(){
		var length = (document.getElementById('kcpdDescTable').rows.length-2);
		
		for(var i=0;i<=length;i++){
			
			var kcnums = document.getElementById("kc_nums_"+i);
			if(!InputValid(kcnums,0,"int",0,1,999999,"库库数")){
				kcnums.focus();
				return;
			}			
			var sjnums = document.getElementById("sj_nums_"+i);
			if(!InputValid(sjnums,0,"int",0,1,999999,"实际数")){
				sjnums.focus();
				return;
			}	
			var yk = document.getElementById("yk_"+i);			
			
			yk.value = parseInt(sjnums.value) - parseInt(kcnums.value);
		}
	}
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.kcpdForm.proc_id.length;i++){
			var o = document.kcpdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.kcpdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择产品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("kc_nums_" + sel).value = "0";
		document.getElementById("sj_nums_" + sel).value = "0";
		document.getElementById("yk_" + sel).value = "0";
		document.getElementById("remark_" + sel).value = "";
	}		
	
</script>
</head>
<body onload="initFzrTip();">
<form name="kcpdForm" action="updateKcpd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">库存盘点</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="kcpd.id" id="id" value="<%=StringUtils.nullToStr(kcpd.getId()) %>" maxlength="25"><body onload="initFzrTip();">
		</td>	
		<%
		String rq = StringUtils.nullToStr(kcpd.getPdrq());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">盘点日期</td>
		<td class="a2"><input type="text" name="kcpd.pdrq" id="pdrq" value="<%=rq %>"  class="Wdate" onFocus="WdatePicker()"> <font color="red">*</font>	
		</td>			
	</tr>	
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%">			
			<select name="kcpd.store_id" id="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
					String strid = StringUtils.nullToStr(storeHouse.getId());
					String name = StringUtils.nullToStr(storeHouse.getName());
			%>
				<option value="<%=strid %>" <%if(StringUtils.nullToStr(kcpd.getStore_id()).equals(strid)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select> <font color="red">*</font>	
		</td>		
		<td class="a1" width="15%">盘点人</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()"  value="<%=StaticParamDo.getRealNameById(kcpd.getPdr())%>"/> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:515px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="kcpd.pdr" id="fzr" value="<%= kcpd.getPdr()%>" /> <font color="red">*</font>	
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="kcpd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(kcpd.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(kcpd.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>			
		</td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">盘点详情</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="kcpdDescTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>选择</td>
		<td>产品名称</td>
		<td>规格</td>
		<td>库存数量</td>
		<td>实际数量</td>
		<td>盈亏</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(kcpdDescs!=null && kcpdDescs.size()>0){
	for(int i=0;i<kcpdDescs.size();i++){
		KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="kcpdDesc[<%=i %>].product_name" value="<%=StringUtils.nullToStr(kcpdDesc.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="kcpdDesc[<%=i %>].product_id" value="<%=StringUtils.nullToStr(kcpdDesc.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="kcpdDesc[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(kcpdDesc.getProduct_xh()) %>" readonly></td>
		<td class="a2"><input type="text" id="kc_nums_<%=i %>" name="kcpdDesc[<%=i %>].kc_nums" size="5" value="<%=StringUtils.nullToStr(kcpdDesc.getKc_nums()) %>" onblur="pdAll();"></td>
		<td class="a2"><input type="text" id="sj_nums_<%=i %>" name="kcpdDesc[<%=i %>].sj_nums" size="5" value="<%=StringUtils.nullToStr(kcpdDesc.getSj_nums()) %>" onblur="pdAll();"></td>
		<td class="a2"><input type="text" id="yk_<%=i %>" name="kcpdDesc[<%=i %>].yk" size="5" value="<%=StringUtils.nullToStr(kcpdDesc.getYk()) %>"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="kcpdDesc[<%=i %>].remark" value="<%=StringUtils.nullToStr(kcpdDesc.getRemark()) %>"></td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="kcpdDesc[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="kcpdDesc[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="kcpdDesc[<%=i %>].product_xh" readonly></td>
		<td class="a2"><input type="text" id="kc_nums_<%=i %>" name="kcpdDesc[<%=i %>].kc_nums" size="5" value="0" onblur="pdAll();"></td>
		<td class="a2"><input type="text" id="sj_nums_<%=i %>" name="kcpdDesc[<%=i %>].sj_nums" size="5" value="0" onblur="pdAll();"></td>
		<td class="a2"><input type="text" id="yk_<%=i %>" name="kcpdDesc[<%=i %>].yk" size="5" value="0"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="kcpdDesc[<%=i %>].remark"></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加库存产品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除明细信息" class="css_button4" onclick="delDesc();">
		</td>
	</tr>
</table>
<br>	
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">其它</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="6" name="kcpd.remark" id="remark" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(kcpd.getRemark()) %></textarea>
		</td>
	</tr>	
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