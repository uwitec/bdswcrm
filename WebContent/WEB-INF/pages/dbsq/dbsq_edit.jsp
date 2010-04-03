<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

Dbsq dbsq = (Dbsq)VS.findValue("dbsq");
List dbsqProducts = (List)VS.findValue("dbsqProducts");

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");

%>

<html>
<head>
<title>调拨申请</title>
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

	var allCount = 2;
	
	function saveInfo(vl){
		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
			
		if(document.getElementById("creatdate").value == ""){
			alert("日期不能为空，请选择！");
			return;
		}		
		if(document.getElementById("store_id").value == ""){
			alert("仓库不能为空，请选择！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}					
	
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("提交后将不能修改，确认提交吗？")){
				document.dbsqForm.submit();
			}
		}else{
			document.dbsqForm.submit();
		}
		
	}
      	
    function addTr(){
        var otr = document.getElementById("dbsqTable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="dbsqProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="dbsqProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="dbsqProducts['+curId+'].product_xh" readonly>';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="dbsqProducts['+curId+'].nums" value="0">';
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="dbsqProducts['+curId+'].remark"  maxlength="50">';                       

		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3); 
        otr.appendChild(otd5);
        otr.appendChild(otd6);               
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	
	function openWin(id){  //与退货单使用一个商品选择
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	

     function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
</script>
</head>
<body onload="initFzrTip();">
<form name="dbsqForm" action="updateDbsq.html" method="post">
<input type="hidden" name="dbsq.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">调拨申请</td>
	</tr>
	</thead>
<%
//如果有信息则显示
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
		<td class="a1" width="15%">编号</td>
		<td class="a2">
		<input type="text" name="dbsq.id" id="id" value="<%=StringUtils.nullToStr(dbsq.getId()) %>" size="30" maxlength="50" readonly><font color="red">*</font>
		</td>	
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="dbsq.creatdate" id="creatdate" value="<%=StringUtils.nullToStr(dbsq.getCreatdate()) %>" class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">仓库</td>
		<td class="a2">
			<select name="dbsq.store_id" id="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(dbsq.getStore_id()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>		
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		    <input id="brand" type="text" length="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(dbsq.getJsr()) %>"/> 
            <div id="brandTip" style="height:12px;position:absolute;left:513px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="dbsq.jsr" id="fzr" value="<%=dbsq.getJsr() %>"/><font color="red">*</font>	
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
<table width="100%"  align="center" id="dbsqTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>数量</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
if(dbsqProducts!=null && dbsqProducts.size()>0){
	for(int i=0;i<dbsqProducts.size();i++){
		DbsqProduct dbsqProduct = (DbsqProduct)dbsqProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="dbsqProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(dbsqProduct.getProduct_name()) %>" readonly>
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="dbsqProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(dbsqProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="dbsqProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(dbsqProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="dbsqProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(dbsqProduct.getNums()) %>"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="dbsqProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(dbsqProduct.getRemark()) %>" maxlength="50"></td>
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
			<input type="text" id="product_name_<%=i %>" name="dbsqProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="dbsqProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="dbsqProducts[<%=i %>].product_xh" readonly></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="dbsqProducts[<%=i %>].nums" value="0"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="dbsqProducts[<%=i %>].remark" maxlength="50"></td>
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
			<textarea rows="3" name="dbsq.remark" id="remark" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(dbsq.getRemark()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>