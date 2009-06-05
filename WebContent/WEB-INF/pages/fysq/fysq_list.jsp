<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>费用申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editFysq.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'费用申请',fea);		
	}
	
	function view(id){
		var destination = "viewFysq.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'费用申请',fea);		
	}	
	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			location.href = "delFysq.html?id=" + id;
			return;
		}
	}
	
	function clearAll(){
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
		document.myform.state.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listFysq.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="listFysq.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>费用申请</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			日期：
			<ww:textfield theme="simple" name="creatdate1" id="creatdate1" value="%{creatdate1}" readonly="true" size="10"/> 
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate1); return false;">&nbsp;至&nbsp;
			<ww:textfield theme="simple" name="creatdate2" id="creatdate2" value="%{creatdate2}" readonly="true" size="10"/> 
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">
			&nbsp;&nbsp;		
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'保存':'保存','提交':'提交','审批通过':'审批通过','审批不通过':'审批不通过','已支付':'已支付'}"  emptyOption="true" ></ww:select>&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>申请日期</td>	
		<td>业务员</td>
		<td>费用类型</td>
		<td>金额</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{fysqPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{creatdate}" /></td>
			<td><ww:property value="%{ywy_name}" /></td>
			<td><ww:property value="%{fy_type}" /></td>
			<td><ww:property value="%{strJe}" /></td>
			<td><ww:property value="%{state}" /></td>		
			<td>
				<ww:if test="state=='保存'">
					<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
					<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除该" border="0" style="cursor:hand"></a>							
				</ww:if>
				<ww:else>
					<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
				</ww:else>
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{fysqPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>