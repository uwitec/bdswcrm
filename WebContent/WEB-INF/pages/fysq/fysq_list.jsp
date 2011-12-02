<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>费用申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editFysq.html?id=" + id;
		var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'费用申请',fea);		
	}
	
	function view(id){
		var destination = "viewFysq.html?id=" + id;
		var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'费用申请',fea);		
	}	
	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			//location.href = "delFysq.html?id=" + id;
			document.myform.action = "delFysq.html?id=" + id;
			document.myform.submit();
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
			<input type="text" name="creatdate1" id="creatdate1" value="<ww:property value="%{creatdate1}" />" class="Wdate" onFocus="WdatePicker()"/>&nbsp;至&nbsp;
			<input type="text" name="creatdate2" id="creatdate2" value="<ww:property value="%{creatdate2}" />" class="Wdate" onFocus="WdatePicker()"/> 
			&nbsp;&nbsp;		
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'保存':'保存','待审批':'待审批','审批不通过':'审批不通过','待支付':'待支付','已支付':'已支付','出纳退回':'出纳退回'}"  emptyOption="true" ></ww:select>&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%" align="center" border="1" class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>申请日期</td>	
		<td>申请人</td>
		<td>使用部门</td>
		<td>费用类型</td>
		<td>金额</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{fysqPage.results}">
		<tr class="a1" onmousedown="trSelectChangeCss()" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{creatdate}" /></td>
			<td><ww:property value="%{getUserRealName(sqr)}" /></td>
			<td><ww:property value="%{getDeptName(ywy_dept)}" /></td>
			<td><ww:property value="%{getFyTypeName(fy_type)}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{je})}" />&nbsp;</td>
			<td><ww:property value="%{state}" /></td>
			<td>
				<ww:if test="state=='保存' or state=='审批不通过' or state=='出纳退回'">
					<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
					<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>							
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
</div>
</body>
</html>