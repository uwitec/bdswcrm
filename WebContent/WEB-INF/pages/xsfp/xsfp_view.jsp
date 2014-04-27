<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售发票开票</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售发票开票</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><ww:property value="%{xsfpFpxx.id}"/></td>
		<td class="a1" width="15%">开票日期</td>
		<td class="a2" width="35%"><ww:property value="%{xsfpFpxx.kprq}"/></td>				
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><ww:property value="%{xsfpFpxx.khmc}"/></td>				
		<td class="a1" width="15%">发票类型</td>
		<td class="a2" width="35%"><ww:property value="%{xsfpFpxx.fplx}"/></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">发票号</td>
		<td class="a2" width="35%"><ww:property value="%{xsfpFpxx.fph}"/></td>		
		<td class="a1" width="15%">开票人</td>
		<td class="a2" width="35%"><ww:property value="%{getUserRealName(xsfpFpxx.getKpr())}"/></td>
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2"  colspan="3"><ww:property value="%{xsfpFpxx.remark}"/></td>		
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发票明细内容</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="fpmxTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">序号</td>
		<td width="25%">客户名称</td>
		<td width="15%">销售单号</td>
		<td width="10%">销售日期</td>
		<td width="10%">应开票金额</td>
		<td width="10%">已开票金额</td>
		<td width="10%">本次开票金额</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	<tr>
		<td class="a2">1</td>
		<td class="a2">&nbsp;</td>
		<td class="a2">不对单</td>
		<td class="a2">&nbsp;</td>
		<td class="a2"><ww:textfield name='bdd_ykpje' id='bdd_ykpje' value="0.00" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='bdd_yikpje' id='bdd_yikpje' value="0.00" theme="simple" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.fpje_bdd' id='fpje_bdd' value="%{getText('global.format.double',{xsfpFpxx.fpje_bdd})}" theme="simple" readonly="true"   cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name='xsfpFpxx.remark_bdd' id='remark_bdd' value="%{xsfpFpxx.remark_bdd}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
	</tr>		
	<ww:iterator value="%{fpmxDescs}" status="li">
	<tr>
		<td class="a2"><ww:property value="#li.count+1"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].khmc" id="khmc_%{#li.count-1}" value="%{khmc}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].yw_id" id="yw_id_%{#li.count-1}" value="%{yw_id}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].cdate" id="cdate_%{#li.count-1}" value="%{cdate}" theme="simple"  readonly="true" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_ying" id="kpje_ying_%{#li.count-1}" value="%{getText('global.format.double',{kpje_ying})}" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_yi" id="kpje_yi_%{#li.count-1}" value="%{getText('global.format.double',{kpje_yi})}" theme="simple"  readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].kpje_bc" id="kpje_bc_%{#li.count-1}" value="%{getText('global.format.double',{kpje_bc})}" theme="simple"   readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2"><ww:textfield name="fpmxDescs[%{#li.count-1}].remark" id="remark_%{#li.count-1}" value="%{remark}" theme="simple" cssStyle="width:90%"  readonly="true" /></td>		
	</tr>	
	</ww:iterator>
</table>
<table width="100%"  align="center" id="fpmxTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a2" width="5%"><b>合计</b></td>
		<td class="a2" width="25%">&nbsp;</td>
		<td class="a2" width="15%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%">&nbsp;</td>
		<td class="a2" width="10%"><ww:textfield name='xsfpFpxx.fpje' id='fpje'  value="%{getText('global.format.double',{xsfpFpxx.fpje})}" theme="simple" readonly="true" cssStyle="width:90%;text-align:right"/></td>
		<td class="a2" width="15%">&nbsp;</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();" />
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>