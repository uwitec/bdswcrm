<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Cgfk cgfk = (Cgfk)VS.findValue("cgfk");
List cgfkDescs = (List)VS.findValue("cgfkDescs");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审批付款申请单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function doSp(vl){
	var msg = "";
	if(vl == "1"){
		document.getElementById("state").value = "通过";
		msg = "确认审批通过吗？";
	}else{
		document.getElementById("state").value = "不通过";
		msg = "确认审批不通过吗？";
	}
	if(window.confirm(msg)){
		document.cgfkForm.submit();
	}
}

function openWin(id){
	var destination = "viewJhd.html?id="+id;
	var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'详细信息',fea);	
}
</script>
</head>
<body>
<form name="cgfkForm" action="doSpCgfk.html" method="post">
<input type="hidden" name="state" id="state" value="">
<input type="hidden" name="id" id="id" value="<%=StringUtils.nullToStr(cgfk.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付款申请单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getId()) %></td>
		<td class="a1" width="15%">是否预付款</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getIs_yfk()) %></td>			
	</tr>
	<tr>	
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getFk_date()) %></td>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfk.getGysbh())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">单位全称</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getClient_all_name()) %></td>
		<td class="a1" width="15%">开户行账号</td>
		<td class="a2"><%=StringUtils.nullToStr(cgfk.getBank_no()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">客户联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(cgfk.getKh_lxr()) %></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(cgfk.getTel()) %></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">传真</td>
		<td class="a2"><%=StringUtils.nullToStr(cgfk.getFax()) %></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(cgfk.getJsr())) %></td>	
	</tr>			
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">付款明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="20%">进货单编号</td>
		<td width="15%">发生日期</td>
		<td width="15%">发生金额</td>
		<td width="15%">应付金额</td>
		<td width="15%">本次付款</td>
		<td width="20%">备注</td>
	</tr>
	</thead>
<%
if(cgfkDescs != null && cgfkDescs.size()>0){
	for(int i=0;i<cgfkDescs.size();i++){
		Map map = (Map)cgfkDescs.get(i);
		
		double fsje = map.get("fsje")==null?0:((Double)map.get("fsje")).doubleValue();
		double yfje = map.get("yfje")==null?0:((Double)map.get("yfje")).doubleValue();
		double bcfk = map.get("bcfk")==null?0:((Double)map.get("bcfk")).doubleValue();
		
		String jhd_id = StringUtils.nullToStr(map.get("jhd_id"));
%>
	<tr>
		<%if(jhd_id.indexOf("JH") != -1){ %>
		<td class="a2"><a href="javascript:openWin('<%=jhd_id %>');" title="点击查看进货单详情"><%=jhd_id %></a></td>
		<%}else{ %>
		<td class="a2"><%=jhd_id %></td>
		<%} %>
		<td class="a2"><%=StringUtils.nullToStr(map.get("fsrq")) %></td>
		<td class="a2"><%=JMath.round(fsje,2) %></td>
		<td class="a2"><%=JMath.round(yfje,2) %></td>
		<td class="a2"><%=JMath.round(bcfk,2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(map.get("remark")) %></td>	
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr>			
		<td class="a1" width="15%">本次付款总额</td>
		<td class="a2" width="35%"><%=JMath.round(cgfk.getFkje(),2) %></td>	
		<td class="a1" width="15%">付款账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(cgfk.getFkzh())) %></td>
	</tr>
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><input type="text" name="remark" id="remark" style="width:90%" value="<%=StringUtils.nullToStr(cgfk.getRemark()) %>" maxlength="500"></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="审批通过" class="css_button3" onclick="doSp('1');">&nbsp;&nbsp;
			<input type="button" name="button1" value="审批不通过" class="css_button3" onclick="doSp('2');">&nbsp;&nbsp;
			<input type="button" name="button1" value=" 关 闭 " class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
