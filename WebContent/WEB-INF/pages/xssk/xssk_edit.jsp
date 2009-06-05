<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Xssk xssk = (Xssk)VS.findValue("xssk");

List userList = (List)VS.findValue("userList");

List xsskDescs = (List)VS.findValue("xsskDescs");

int allCount = 3;
if(xsskDescs != null && xsskDescs.size()>0){
	allCount = xsskDescs.size();
}
%>

<html>
<head>
<title>销售收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	var allCount = <%=allCount %>;

	function saveInfo(){
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("sk_date"),1,"string",1,1,20,"收款日期")){	 return; }
		if(!InputValid(document.getElementById("client_id"),1,"string",1,1,50,"往来单位")){	 return; }
		
		if(document.getElementById("jsr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(!InputValid(document.getElementById("fkzh"),1,"string",1,1,50,"收款账户")){	 return; }
		
		hj();
		
		if(isNaN(document.getElementById("skje").value) || parseFloat(document.getElementById("skje").value) == 0){
			alert("本次收款金额必须为数字并且不等于0，请检查！");
			return;
		}

		document.XsskForm.btnSub.disabled = true;
		document.XsskForm.submit();
	}
	
	function openWin(){
		var destination = "selectJhd.html";
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function openClientWin(){
		var destination = "selXsskClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function hj(){
		if(document.getElementById("is_ysk").checked){
			return;
		}
		
		var hjz = 0;

		for(var i=0;i<allCount;i++){
				
			var sk = document.getElementById("bcsk_" + i);
			var ysk = document.getElementById("ysk_" + i);
			
			if(sk != null){
				if(!InputValid(sk,1,"float",1,0,99999999,"本次收款")){
					sk.focus();
					return;
				}
				if(parseFloat(sk.value) > parseFloat(ysk.value)){
					alert("本次收款金额大于应收金额，请检查！");
					sk.focus();
					return;
				}
			}				
			
			hjz = parseFloat(hjz) + parseFloat(sk.value);
		}

		
		var skje = document.getElementById("skje");
		var hj_bcsk = document.getElementById("hj_bcsk");
		hj_bcsk.value = hjz
		skje.value = hjz;
		
	}	
	
	function chkYsk(){
		if(document.getElementById("is_ysk").checked){
			if(document.getElementById("client_id").value != ""){
				alert("先确认预收款，再选择客户！");
				document.getElementById("is_ysk").checked = false;
				return;
			}
			
			document.getElementById("skje").readOnly = false;
			document.getElementById("hj_bcsk").readOnly = true;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcsk_" + i).readOnly = true;
			}
			
		}else{
			document.getElementById("skje").readOnly = true;
			document.getElementById("hj_bcsk").readOnly = false;
			
			for(var i=0;i<allCount;i++){
				document.getElementById("bcsk_" + i).readOnly = false;
			}
		}
	}
				
</script>
</head>
<body >
<form name="XsskForm" action="updateXssk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售收款信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="xssk.id" id="id" value="<%=StringUtils.nullToStr(xssk.getId()) %>" readonly>
		</td>
		<td class="a1" width="15%">是否预收款</td>
		<td class="a2"><input type="checkbox" name="xssk.is_ysk" id="is_ysk" value="是" onclick="chkYsk();" <%if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")) out.print("checked"); %>>预收款</td>				
	</tr>
	<tr>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="clientName" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xssk.getClient_name())) %>" size="35" readonly>
		<input type="hidden" name="xssk.client_name" id="client_id" value="<%=StringUtils.nullToStr(xssk.getClient_name()) %>">
		</td>
		<td class="a1" width="15%">收款日期</td>
		<td class="a2" width="35%"><input type="text" name="xssk.sk_date" id="sk_date" value="<%=StringUtils.nullToStr(xssk.getSk_date()).equals("")?DateComFunc.getToday():StringUtils.nullToStr(xssk.getSk_date()) %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('fk_date')); return false;">
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
			<select name="xssk.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String id = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(xssk.getJsr()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>				
		</td>	
		<td class="a1" widht="20%">收款账户</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xssk.getSkzh())) %>" readonly>
		<input type="hidden" id="fkzh"  name="xssk.skzh" value="<%=StringUtils.nullToStr(xssk.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>

	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2">
			<select name="xssk.state">
				<option value="已保存" <%if(StringUtils.nullToStr(xssk.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(xssk.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>			
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
		<td>发生日期</td>
		<td>发生金额</td>
		<td>应收金额</td>
		<td>本次收款</td>
		<td>备注</td>
	</tr>
	</thead>
<%
double hj_fsje = 0;
double hj_ysk = 0;
double hj_bcsk = 0;

if(xsskDescs != null && xsskDescs.size()>0){
	for(int i=0;i<xsskDescs.size();i++){
		Map map = (Map)xsskDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		hj_fsje += fsje;
		double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
		hj_ysk += ysk;
		
		double bcsk = map.get("bcsk")==null?0:((Double)map.get("bcsk")).doubleValue();
		hj_bcsk += bcsk;
%>
	<tr>
		<td class="a2"><input type="text" id="xsd_id_<%=i %>" name="xsskDescs[<%=i %>].xsd_id" value="<%=StringUtils.nullToStr(map.get("xsd_id")) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="xsskDescs[<%=i %>].fsrq" value="<%=StringUtils.nullToStr(map.get("fsrq")) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="xsskDescs[<%=i %>].fsje" value="<%=JMath.round(fsje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="ysk_<%=i %>" name="xsskDescs[<%=i %>].ysk"  value="<%=JMath.round(ysk) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcsk_<%=i %>" name="xsskDescs[<%=i %>].bcsk" value="<%=JMath.round(bcsk) %>"  onblur="hj();" <%if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")) out.print("readonly"); %>></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="xsskDescs[<%=i %>].remark" value="<%=StringUtils.nullToStr(map.get("remark")) %>"></td>	
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="text" id="xsd_id_<%=i %>" name="xsskDescs[<%=i %>].xsd_id" value="" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsrq_<%=i %>" name="xsskDescs[<%=i %>].fsrq" value="" readonly></td>
		<td class="a2"><input type="text" size="10" id="fsje_<%=i %>" name="xsskDescs[<%=i %>].fsje" value="0.00" readonly></td>
		<td class="a2"><input type="text" size="10" id="ysk_<%=i %>" name="xsskDescs[<%=i %>].yfje"  value="0.00" readonly></td>
		<td class="a2"><input type="text" size="10" id="bcsk_<%=i %>" name="xsskDescs[<%=i %>].bcsk" value="0.00" onblur="hj();" <%if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")) out.print("readonly"); %>></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="xsskDescs[<%=i %>].remark" value=""></td>	
	</tr>
<%
	}
}
%>	
	<tr>
		<td class="a2">合  计</td>
		<td class="a2"></td>
		<td class="a2"><input type="text" size="10" id="hj_fsje" name="hj_fsje" value="<%=JMath.round(hj_fsje) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_ysk" name="hj_ysk"  value="<%=JMath.round(hj_ysk) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="hj_bcsk" name="hj_bcsk" value="<%=JMath.round(hj_bcsk) %>" <%if(StringUtils.nullToStr(xssk.getIs_ysk()).equals("是")) out.print("readonly"); %>></td>
		<td class="a2"></td>	
	</tr>
	<tr>
		<td class="a2">本次收款总金额</td>
		<td class="a2" colspan="5"><input type="text" size="10" id="skje" name="xssk.skje" value="<%=JMath.round(xssk.getSkje()) %>"></td>
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
			<textarea rows="3" cols="50" name="xssk.remark" id="remark" style="width:80%"><%=StringUtils.nullToStr(xssk.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
