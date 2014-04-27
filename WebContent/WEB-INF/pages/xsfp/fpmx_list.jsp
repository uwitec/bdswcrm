<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发票明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
	
	function openWin(id){
		var destination = "";
		var fea ='width=980,height=650,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		if(id.indexOf("XS") != -1){
			destination = "viewXsd.html?id="+id;
		}else{
			destination = "viewLsd.html?id="+id;
		}
		window.open(destination,'详细信息',fea);	
	}		
</script>
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">发票明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="25%">单位名称</td>
		<td width="15%">销售单号</td>
		<td width="15%">销售日期</td>
		<td width="10%">应开票金额</td>
		<td width="10%">已开票金额</td>
		<td width="10%">本次开票金额</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	<ww:if test="xsfpFpxx.fpje_bdd >0">
		<tr class="a1" style="color: red" onmousedown="trSelectChangeCss()">
			<td>&nbsp;</td>
			<td>不对单</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align="right"><ww:property value="%{getText('global.format.money',{xsfpFpxx.fpje_bdd})}" /></td>
			<td><ww:property value="%{xsfpFpxx.remark}" /></td>
		</tr>
	</ww:if>
	<ww:iterator value="%{xsfpFpmxList}" status="li">
		<tr class="a1" onmousedown="trSelectChangeCss()">
			<td><ww:property value="%{khmc}" /></td>
			<td><a href="javascript:openWin('<ww:property value="%{yw_id}" />');"><ww:property value="%{yw_id}" /></a></td>
			<td><ww:property value="%{cdate}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{kpje_ying})}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{kpje_yi})}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{kpje_bc})}" /></td>
			<td><ww:property value="%{remark}" /></td>
		</tr>
	</ww:iterator>
</table>
注：点击业务单据编号可以查看原始单据。
</body>
</html>