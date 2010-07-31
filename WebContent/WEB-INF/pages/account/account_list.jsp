<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List accountList = (List)VS.findValue("accountList");

%>

<html>
<head>
<title>账户资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewAccount.html?id="+id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要停用吗！")){
			location.href = "delAccount.html?id=" + id;
		}
	}

	function restore(id){
		if(confirm("确定要启用吗！")){
			location.href = "restoreAccount.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addAccount.html";
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加账户资料',fea);	
	}
	
	function edit(id){
		var destination = "editAccount.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改账户资料',fea);		
	}	
	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}	
	
	function refreshPage(){
		document.myform.action = "listAccount.html";
		document.myform.submit();
	}		
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listAccount.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>账户资料</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>名称</td>
		<td>账户类型</td>
		<td>开户行</td>
		<td>账号</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = accountList.iterator();
	
	while(its.hasNext()){
		Map map = (Map)its.next();
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("type")) %></td>
		<td><%=StringUtils.nullToStr(map.get("bank")) %></td>
		<td><%=StringUtils.nullToStr(map.get("bank_count")) %></td>
		<td><%=StringUtils.nullToStr(map.get("flag")).equals("1")?"正常":"停用" %></td>
		<td>
			<a href="#" class="xxlb" onclick="edit('<%=StringUtils.nullToStr(map.get("id")) %>');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="xxlb" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;	
			
			<%if(StringUtils.nullToStr(map.get("flag")).equals("1")){ %>		
			<a href="#" class="xxlb" onclick="del('<%=StringUtils.nullToStr(map.get("id")) %>');">停用</a>
			<%}else{ %>
			<a href="#" class="xxlb" onclick="restore('<%=StringUtils.nullToStr(map.get("id")) %>');">重新启用</a>
			<%} %>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
</body>
</html>
