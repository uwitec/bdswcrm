<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

YushouToYingshou yushouToYingshou = (YushouToYingshou)VS.findValue("yushouToYingshou");

List userList = (List)VS.findValue("userList");
double clientHjYushouK = (Double)VS.findValue("clientHjYushouK");
List clientsList=(List)VS.findValue("clientsList");

List yushouToYingshouDescs = (List)VS.findValue("yushouToYingshouDescs");

int allCount = 3;
if(yushouToYingshouDescs != null && yushouToYingshouDescs.size()>0){
	allCount = yushouToYingshouDescs.size();
}
%>

<html>
<head>
<title>预收冲应收</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	var allCount = <%=allCount %>;

	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("create_date"),1,"string",1,1,20,"日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		
		hj();

		document.XsskForm.submit();
	}	
	
	function openClientWin(){
		var destination = "selYinshoukClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	
	function hj(){
		var bcjs_hj = 0;

		for(var i=0;i<allCount;i++){
				
			var bcjs = document.getElementById("bcjs_" + i);  //本次结算金额
			var xsdysje = document.getElementById("yingshouje_" + i);  //销售单应收金额
			
			if(!InputValid(bcjs,0,"float",0,1,99999999,"本次结算")){
				bcjs.focus();
				return;
			}
			if(parseFloat(bcjs.value) > parseFloat(xsdysje.value)){
				alert("本次结算金额大于应付金额，请检查！");
				bcjs.focus();
				return;
			}
			
			bcjs_hj = parseFloat(bcjs_hj) + parseFloat(bcjs.value);
			
			if(parseFloat(bcjs_hj) > parseFloat(document.getElementById("yushouzje").value)){
				alert("结算合计金额大于客户预收总金额，请检查！");
				bcjs.focus();
				return;
			}
		}

		
		var hjjs = document.getElementById("total");
		hjjs.value = bcjs_hj;
		
	}
	
	function queryYszd(){
		setClientValue();
		document.XsskForm.action = "addYushouToYingshou.html";
		document.XsskForm.submit();
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		
</script>
</head>
<body  onload="initFzrTip();initClientTip();">
<form name="XsskForm" action="saveYushouToYingshou.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">预收冲应收</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="yushouToYingshou.id" id="id" value="<%=StringUtils.nullToStr(yushouToYingshou.getId()) %>" readonly>
		</td>
		<td class="a1" width="15%">结算日期</td>
		<td class="a2" width="35%"><input type="text" name="yushouToYingshou.create_date" id="create_date" value="<%=StringUtils.nullToStr(yushouToYingshou.getCreate_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(yushouToYingshou.getCreate_date()) %>" class="Wdate" onFocus="WdatePicker()">
		</td>		
	</tr>
	<tr>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="clientName" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(yushouToYingshou.getClient_name())) %>" size="35"  onblur="queryYszd();">
		<input type="hidden" name="yushouToYingshou.client_name" id="client_id" value="<%=StringUtils.nullToStr(yushouToYingshou.getClient_name()) %>">
		<div id="clientsTip" style="height:12px;position:absolute;left:125px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" />
          <div   id="brandTip"  style="height:12px;position:absolute;left:515px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="yushouToYingshou.jsr" id="fzr"  /> 
		</td>				
	</tr>
	<tr>		
		<td class="a1" width="15%">状态</td>
		<td class="a2">
			<select name="yushouToYingshou.state">
				<option value="已保存" <%if(StringUtils.nullToStr(yushouToYingshou.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(yushouToYingshou.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>
		<td class="a1" width="15%">客户预收总金额</td>
		<td class="a2"><%=clientHjYushouK %><input type="hidden" name="yushouzje" id="yushouzje" value="<%=clientHjYushouK %>"></td>					
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">收款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>销售单编号</td>
		<td>应收金额</td>
		<td>本次结算</td>
		<td>备注</td>
	</tr>
	</thead>
<%
double hj_yingshouje = 0;
double hj_bcjs = 0;

if(yushouToYingshouDescs != null && yushouToYingshouDescs.size()>0){
	for(int i=0;i<yushouToYingshouDescs.size();i++){
		YushouToYingshouDesc info = (YushouToYingshouDesc)yushouToYingshouDescs.get(i);

		hj_yingshouje += info.getYingshouje();
		hj_bcjs += info.getBcjs();
%>
	<tr>
		<td class="a2"><input type="text" id="xsd_id_<%=i %>" name="yushouToYingshouDescs[<%=i %>].xsd_id" value="<%=StringUtils.nullToStr(info.getXsd_id()) %>" readonly></td>
		<td class="a2"><input type="text" size="15" id="yingshouje_<%=i %>" name="yushouToYingshouDescs[<%=i %>].yingshouje" value="<%=JMath.round(info.getYingshouje()) %>" readonly></td>
		<td class="a2"><input type="text" size="15" id="bcjs_<%=i %>" name="yushouToYingshouDescs[<%=i %>].bcjs" value="<%=JMath.round(info.getBcjs()) %>" onblur="hj();"></td>
		<td class="a2"><input type="text" size="25" id="remark_<%=i %>" name="yushouToYingshouDescs[<%=i %>].remark" value="<%=StringUtils.nullToStr(info.getRemark()) %>"></td>	
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" id="xsd_id_<%=i %>" name="yushouToYingshouDescs[<%=i %>].xsd_id" value="" readonly></td>
		<td class="a2"><input type="text" size="15" id="yingshouje_<%=i %>" name="yushouToYingshouDescs[<%=i %>].yingshouje" value="0.00" readonly></td>
		<td class="a2"><input type="text" size="15" id="bcjs_<%=i %>" name="yushouToYingshouDescs[<%=i %>].bcjs" value="0.00" onblur="hj();"></td>
		<td class="a2"><input type="text" size="25" id="remark_<%=i %>" name="yushouToYingshouDescs[<%=i %>].remark"  value="" readonly></td>
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"><input type="text" size="15" id="hj_yingshouje" name="hj_yingshouje" value="<%=JMath.round(hj_yingshouje) %>" readonly></td>
		<td class="a2"><input type="text" size="15" id="total" name="yushouToYingshou.total" value="<%=JMath.round(hj_bcjs) %>" readonly></td>
		<td class="a2"></td>	
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备  注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="yushouToYingshou.remark" id="remark" style="width:80%"><%=StringUtils.nullToStr(yushouToYingshou.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>