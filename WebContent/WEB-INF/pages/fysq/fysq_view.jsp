<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>费用申请单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用申请单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.id}"/></td>
		<td class="a1" width="15%">申请日期</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.creatdate}"/></td>				
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请人</td>
		<td class="a2" width="35%"><ww:property value="%{getUserRealName(fysq.sqr)}"/></td>
		<td class="a1" width="15%">状态</td>
		<td class="a2"><ww:property value="%{fysq.state}"/></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">费用使用部门</td>
		<td class="a2" width="35%"><ww:property value="%{getDeptName(fysq.ywy_dept)}"/></td>	
		<td class="a1" width="15%">费用使用人</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.ywy_name}"/></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><ww:property value="%{getFyTypeName(fysq.fy_type)}"/></td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.strJe}"/></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">对应客户</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.xgkh}"/></td>			
		<td class="a1" width="15%">支付方式</td>
		<td class="a2" width="35%"><ww:property value="%{fysq.fklx}"/></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">支付账户</td>
		<td class="a2" colspan="3"><ww:property value="%{fysq.zfzh_name}"/></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<ww:textarea name="fysq.remark" id="remark"  theme="simple" cssStyle="width:85%;height:70px" readonly="true"/>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
