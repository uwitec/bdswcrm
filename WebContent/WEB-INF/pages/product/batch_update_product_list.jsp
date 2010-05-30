<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>商品信息批量调整</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/reportJust.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validateform.js"></script>
<script type="text/javascript">
	function tj(){
		if(doValidate(reportForm)){
			if(window.confirm("确认提交吗，提交后所有商品信息将更新!")){
				document.reportForm.submit();
			}
		}
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>商品信息批量调整</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<form name="reportForm" action="batchUpdateProduct.html" method="post">
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="3%" nowrap="nowrap">序号</TD>
			<TD class=ReportHead width="8%" nowrap="nowrap">商品编码</TD>
			<TD class=ReportHead width="15%">商品名称</TD>
			<TD class=ReportHead width="11%">规格</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">考核成本价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">预估成本价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">零售报价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">零售限价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">分销报价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">分销限价</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">比例点杀</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">金额点杀</TD>
			<TD class=ReportHead width="7%" nowrap="nowrap">是否参与提成</TD>
		</TR>
	</THEAD>
	<TBODY>
	<ww:iterator value="%{products}" status="li">
	<TR>
		<TD class=ReportItemXh nowrap="nowrap"><ww:property value="%{#li.count}"></ww:property></TD>
		<TD class=ReportItemXh nowrap="nowrap"><ww:hidden name='products[%{#li.count-1}].productId' id='productId_%{#li.count-1}' value="%{productId}" theme="simple"></ww:hidden><ww:property value="%{productId}"/></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].productName' id='productName_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{productName}"/>" class="cssTextLeft" maxlength="100" notNull='true' vtype='string' vdisp='商品名称' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].productXh' id='productXh_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{productXh}"/>" class="cssTextLeft" maxlength="100" notNull='false' vtype='string' vdisp='商品规格' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].khcbj' id='khcbj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{khcbj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='考核成本价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].ygcbj' id='ygcbj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{ygcbj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='预估成本价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].lsbj' id='lsbj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{lsbj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='零售报价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].lsxj' id='lsxj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{lsxj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='零售限价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].fxbj' id='fxbj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{fxbj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='分销报价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].fxxj' id='fxxj_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{fxxj})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='分销限价' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].gf' id='gf_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{gf})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='比例点杀' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<TD class=ReportItemXh><input type="text" name='products[<ww:property value="%{#li.count-1}"/>].dss' id='dss_<ww:property value="%{#li.count-1}"/>' value="<ww:property value="%{getText('global.format.double',{dss})}"/>" class="cssTextRight" maxlength="10" notNull='true' vtype='float' vdisp='金额点杀' onfocus="this.style.backgroundColor='#DCDCDC'" onblur="this.style.backgroundColor='#FFFFFF'"></TD>
		<td class=ReportItemXh><ww:select list="#{\"1\":'是',\"0\":'否'}" name="products[%{#li.count-1}].sfcytc" id="sfcytc_%{#li.count-1}" value="%{sfcytc}" theme="simple"></ww:select></td>
	</TR>
	</ww:iterator>
	</TBODY>
</TABLE><BR>
<center class="Noprint">
	<input type="button" name="button_print" value=" 保存 " onclick="tj();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='batchUpdateProductCon.html';"> 
</center>
</form>
</body>
</html>