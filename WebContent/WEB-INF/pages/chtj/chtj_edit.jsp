<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
Chtj chtj = (Chtj)VS.findValue("chtj");
List chtjDescs = (List)VS.findValue("chtjDesc");

int count = 2;
if(chtjDescs!=null && chtjDescs.size()>0){
	count = chtjDescs.size()-1;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>存货调价</title>
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
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(document.getElementById("id").value == ""){
			alert("编号不能为空，请填写！");
			return;
		}
		if(document.getElementById("tj_date").value == ""){
			alert("调价时间不能为空，请填写！");
			return;
		}		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请填写！");
			return;
		}
		
		if(vl == "1"){
			document.chtjForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				document.chtjForm.submit();
			}else{
				return;
			}
		}
		
		document.chtjForm.btnSub.disabled = true;
		document.chtjForm.btnSave.disabled = true;
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
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="chtjDesc['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="chtjDesc['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="chtjDesc['+curId+'].product_xh" readonly>';
        
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="ysjg_'+curId+'" name="chtjDesc['+curId+'].ysjg" value="0.00">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="tzjg_'+curId+'" name="chtjDesc['+curId+'].tzjg" value="0.00">';        
            
        
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="chtjDesc['+curId+'].remark">';                       
		
		otr.appendChild(otd9); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3);
        otr.appendChild(otd5);     
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(){
		
		var destination = "selChProc.html";
		var fea ='width=850,height=500,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择库存商品',fea);	
	}	
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}		
	
	
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.chtjForm.proc_id.length;i++){
			var o = document.chtjForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.chtjForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("ysjg_" + sel).value = "0.00";
		document.getElementById("tzjg_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
	}		
	
</script>
</head>
<body onload="initFzrTip();">
<form name="chtjForm" action="updateChtj.html" method="post">
<input type="hidden" name="chtj.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">存货调价</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="chtj.id" id="id" value="<%=StringUtils.nullToStr(chtj.getId()) %>" maxlength="25">
		</td>	
		<%
		String rq = StringUtils.nullToStr(chtj.getTj_date());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">日期</td>
		<td class="a2"><input type="text" name="chtj.tj_date" id="tj_date" value="<%=rq %>"  class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>
		</td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" colspan="3">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(chtj.getJsr()) %>"/>  
            <div   id="brandTip"  style="position:absolute;left:125px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="chtj.jsr" id="fzr"  value="<%=chtj.getJsr() %>" /> <font color="red">*</font>	
		</td>	
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="kcpdDescTable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>选择</td>
		<td>商品名称</td>
		<td>规格</td>
		<td>原价格</td>
		<td>调整后价格</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(chtjDescs!=null && chtjDescs.size()>0){
	for(int i=0;i<chtjDescs.size();i++){
		ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="chtjDesc[<%=i %>].product_name" value="<%=StringUtils.nullToStr(chtjDesc.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="chtjDesc[<%=i %>].product_id" value="<%=StringUtils.nullToStr(chtjDesc.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="chtjDesc[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(chtjDesc.getProduct_xh()) %>" readonly></td>
		<td class="a2"><input type="text" id="ysjg_<%=i %>" name="chtjDesc[<%=i %>].ysjg" value="<%=JMath.round(chtjDesc.getYsjg()) %>"></td>
		<td class="a2"><input type="text" id="tzjg_<%=i %>" name="chtjDesc[<%=i %>].tzjg" value="<%=JMath.round(chtjDesc.getTzjg()) %>"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="chtjDesc[<%=i %>].remark" value="<%=StringUtils.nullToStr(chtjDesc.getRemark()) %>"></td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="chtjDesc[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="chtjDesc[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="chtjDesc[<%=i %>].product_xh" readonly></td>
		<td class="a2"><input type="text" id="ysjg_<%=i %>" name="chtjDesc[<%=i %>].ysjg" value="0.00"></td>
		<td class="a2"><input type="text" id="tzjg_<%=i %>" name="chtjDesc[<%=i %>].tzjg" value="0.00"></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="chtjDesc[<%=i %>].remark"></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加明细商品" class="css_button3" onclick="openWin();">
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
			<textarea rows="3" name="kcpd.remark" id="remark" style="width:75%" maxlength="500"><%=StringUtils.nullToStr(chtj.getRemark()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
