<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存初始化</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function edit(store_id){
		var destination = "initMain.html?store_id=" + store_id;
		var fea ='width=820,height=520,left=' + (screen.availWidth-820)/2 + ',top=' + (screen.availHeight-520)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		window.open(destination,'库存初始',fea);		
	}

	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listInit.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存初始化</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 开始一个新的初始化 </a>
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>库房编号</td>
		<td>库房名称</td>	
		<td>初始日期</td>
		<td>经手人</td>
	</tr>
	</thead>
	<ww:iterator value="%{productKcInitList}">
		<tr class="a1" onmousedown="trSelectChangeCss()" onDblClick="edit('<ww:property value="%{store_id}" />');";>
			<td><ww:property value="%{store_id}" /></td>
			<td><ww:property value="%{store_name}" /></td>
			<td><ww:property value="%{create_date}" /></td>
			<td><ww:property value="%{jsr_name}" /></td>
		</tr>
	</ww:iterator>
</table>
</form>
说明：库存的初始化工作按库房分别进行，点击“开始一个新的初始化”，开始一个新的初始化，双击列表中的库房信息可以继续原有的初始化工作。
</div>
</body>
</html>