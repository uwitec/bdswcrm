<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List lxList = (List)VS.findValue("lxlist");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>联系人类型</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
 
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "deletelx.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addlx.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加联系人类型',fea);	
	}
	
	function edit(id){
		var destination = "editlx.html?id=" + id;
		var fea ='width=400,height=300,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改联系人类型',fea);		
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
		document.myform.action = "listlx.html";
		document.myform.submit();
	}		
	
</script>
</head>
<body oncontextmenu="return false;" >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listlx.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>联系人类型</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>编号</td>
		<td>联系人类型</td>	 
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = lxList.iterator();
	
	while(its.hasNext())
	{
		Map map = (Map)its.next();
	%>
	<tr class="a1"   onmousedown="trSelectChangeCss()" >
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("name")) %></td>
	 
		<td>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	 }
	%>
</table>
</form>
</div>
</body>
</html>