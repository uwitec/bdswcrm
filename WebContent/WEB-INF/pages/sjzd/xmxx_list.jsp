<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统字典维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function edit(zd_id,xm_id){
		var destination = "editZdxx.html?zd_id=" + zd_id + "&xm_id=" + xm_id;
		var fea ='width=400,height=300,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		window.open(destination,'字典维护',fea);		
	}
	
	function del(zd_id,xm_id){
		if(window.confirm("确认要删除该行数据吗？")){
			location.href = "delZdxx.html?zd_id=" + zd_id + "&xm_id=" + xm_id;
		}
	}
</script>
</head>
<body>
<form name="myform" action="listZdxx.html" method="post">
<ww:hidden name="zd_id" id="zd_id" value="%{zd_id}" />
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="70%">&nbsp;&nbsp;&nbsp;&nbsp;<b>字典子项维护（<ww:property value="%{sjzdJbxx.zd_name}" />）</b></td>
		<td class="csstitle" width="25%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="#" onclick="edit('<ww:property value="%{zd_id}" />','');" class="xxlb"> 添 加 </a> &nbsp;&nbsp;	
			<img src="index_images/out_system.gif" align="absmiddle" border="0"><a href="listJbxx.html" class="xxlb"> 返 回 </a> &nbsp;&nbsp;	
		</td>					
	</tr>	
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>子项名称</td>	
		<td>子项描述</td>
		<td>序号</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{sjzdXmxxList}" status="rowstatus">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><ww:property value="%{xm_name}" /></td>
			<td><ww:property value="%{xm_ms}" /></td>
			<td><ww:property value="%{xh}" /></td>
			<td>
				<a href="javascript:void(0);" onclick="edit('<ww:property value="%{zd_id}" />','<ww:property value="%{xm_id}" />');">修改</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" onclick="del('<ww:property value="%{zd_id}" />','<ww:property value="%{xm_id}" />');">删除</a>
			</td>
		</tr>
	</ww:iterator>
</table>
</form>
</body>
</html>