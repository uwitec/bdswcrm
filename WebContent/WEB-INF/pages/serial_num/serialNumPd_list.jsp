<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序列号盘点记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function view(serial_num){
		var destination = "viewSerialNum.html?serial_num=" + serial_num;
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'序列号管理',fea);
	}	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	function clearAll(){
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.jsr.value = "";
	}
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listSerialNumPd.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="70%">&nbsp;&nbsp;&nbsp;&nbsp;<b>序列号盘点记录</b></td>	
		<td class="csstitle" width="30%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="pdSerialNumCon.html" class="xxlb"> 返回盘点 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			盘点时间：<input type="text" name="start_date" value="<ww:property value="%{start_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">	&nbsp;至&nbsp;
			<input type="text" name="end_date" value="<ww:property value="%{end_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			经手人：
			<ww:textfield name="jsr" id="jsr" value="%{jsr}" theme="simple" />		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="25%">盘点时间</td>
		<td width="25%">经手人</td>	
		<td width="25%">库房</td>
		<td width="25%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageSerialNumPd.results}">
		<tr class="a1" onmousedown="trSelectChangeCss()">
			<td><ww:property value="%{cdate}" /></td>
			<td><ww:property value="%{getUserRealName(jsr)}" /></td>
			<td><ww:property value="%{getStoreName(store_id)}" /></td>
			<td>
				<a href="DownLoadFileServlet?file=upload/<ww:property value="%{pd_result}" />">下载</a>
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{pageSerialNumPd}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>