<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageShkc");

String qz_serial_num = (String)VS.findValue("qz_serial_num");
String product_name = (String)VS.findValue("product_name");
String state = (String)VS.findValue("state");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>售后库存</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewShkc.html?id="+id;
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delQtsr.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.qz_serial_num.value = "";
		document.myform.product_name.value = "";
		document.myform.state.value="";
	}	
	
	function add(){
		var destination = "addQtsr.html";
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他收入',fea);	
	}
	
	function edit(id){
		var destination = "editQtsr.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他收入',fea);		
	}	
	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}	
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}	
	
	function refreshPage(){
		document.myform.action = "listShkc.html";
		document.myform.submit();
	}	
	
</script>
</head>
<body>
<form name="myform" action="listShkc.html" method="post">
 
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>售后库存</b></td>	
		<td class="csstitle" width="20%" align="center">&nbsp;
			 
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			序列号：<input type="text" name="qz_serial_num" value="<%=qz_serial_num%>" size="20" >
		 
			 
			商品名称:<input type="text" name="product_name" value="<%=product_name%>" size="30" >
			 
		    库存状态:
		    <select name="state">
		       <option></option>
		       <option value="1" <%if(state.equals("1"))out.print("selected");%>>坏件库</option>
		       <option value="2" <%if(state.equals("2"))out.print("selected");%>>在外库</option>
		       <option value="3" <%if(state.equals("3"))out.print("selected");%>>好件库</option>
		    </select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>
	 				
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>序列号</td>
		<td>商品名称</td>	
		<td>商品规格</td>
		<td>状态</td>
		<td>数量</td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = (results.getResults()).iterator();
	
	while(its.hasNext()){
		Map shkc = (Map)its.next();
		 
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(shkc.get("id"))%>');">
		<td><%=StringUtils.nullToStr(shkc.get("qz_serial_num"))%></td>
		<td><%=StringUtils.nullToStr(shkc.get("product_name"))%></td>
		<td><%=StringUtils.nullToStr(shkc.get("product_xh"))%></td>
		<td><% String str=StringUtils.nullToStr(shkc.get("state")); if(str.equals("1"))out.println("坏件库");if(str.equals("2"))out.println("在外库");if(str.equals("3"))out.println("好件库");%></td>
		<td><%=StringUtils.nullToStr(shkc.get("nums"))%></td>		
		<td>	 
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(shkc.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>		 
		</td>
	</tr>	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
