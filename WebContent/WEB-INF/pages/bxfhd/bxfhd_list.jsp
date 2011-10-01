<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
 
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("bxfhdPage");

String bxcs_name = (String)VS.findValue("bxcs_name");
String fh_date1 = (String)VS.findValue("fh_date1");
String fh_date2 = (String)VS.findValue("fh_date2");

 
String state = (String)VS.findValue("state");
 

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");  

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报修返还单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewBxfhd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delBxfhd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.bxcs.value = "";		
		document.myform.fh_date1.value = "";
		document.myform.fh_date2.value = "";
		document.myform.state.value = "";
		
	}
	
	function add(){
		var destination = "addBxfhd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';	
		window.open(destination,'添加报修返还单',fea);	
	}
	
	function edit(id){
		var destination = "editBxfhd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改报修返还单',fea);		
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
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();		
	}
	
	function refreshPage(){
		document.myform.action = "listBxfhd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listBxfhd.html" method="post">
<div class="rightContentDiv" id="divContent">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>报修返还单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="add();"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;			
			报修单位：<input type="text" name="bxcs" value="<%=bxcs_name%>" size="25" maxlength="50" >&nbsp;&nbsp;
			返还时间：<input type="text" name="fh_date1" value="<%=fh_date1%> " size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="fh_date2" value="<%=fh_date2%> " size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			状态：<select name="state">
				<option value=""></option>
				<option value="已保存"  <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交"  <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>&nbsp;&nbsp;
			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">报修返还单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('bxcs_name');">报修单位<%if(orderName.equals("bxcs_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('productNums');">报修数量<%if(orderName.equals("productNums")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('ssje');">报修金额<%if(orderName.equals("ssje")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('fh_date');">返还时间<%if(orderName.equals("fh_date")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>	
		<td onclick="doSort('cjr');">操作员<%if(orderName.equals("cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %>  </td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map bxfhd = (Map)it.next();
		String ssje = JMath.round((bxfhd.get("ssje")==null?0:((Double)bxfhd.get("ssje")).doubleValue()),2);
		%> 
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');"  onDblClick="openWin('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(bxfhd.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxfhd.get("bxcs")))%></td>
		<td> <%=StringUtils.nullToStr(bxfhd.get("productNums")) %></td>
		<td align="right" nowrap="nowrap"><%=ssje %>&nbsp;&nbsp;</td>
		<td> <%=StringUtils.nullToStr(bxfhd.get("fh_date")) %></td>
		<td> <%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.get("jsr"))) %></td>			
		<td><%=StringUtils.nullToStr(bxfhd.get("state")) %></td>		
		<td> <%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.get("cjr"))) %></td>		 
		<td>
		    <%
		     if(bxfhd.get("state").equals("已保存"))
		     {
		     %>
	        <a href="#" onclick="edit('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该报修单" border="0" style="cursor:hand"></a>				 
		    <%}
		     else
		     {
		      %>
		      	<a href="#" onclick="openWin('<%=StringUtils.nullToStr(bxfhd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <%} %> 
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

<form name="descForm" action="descBxfhd.html" method="post" target="bxfhddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="bxfhddesc" name="bxfhddesc" onload="dyniframesize('bxfhddesc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>