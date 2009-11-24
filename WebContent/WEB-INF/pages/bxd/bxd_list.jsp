<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
 
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("bxdPage");
String lxr=(String)VS.findValue("lxr");
String product_serial_num=(String)VS.findValue("product_serial_num");
String jx_date1 = (String)VS.findValue("jx_date1");
String jx_date2 = (String)VS.findValue("jx_date2");
String gcs=(String)VS.findValue("gcs");
 
String state = (String)VS.findValue("state");
 

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");  

%>

<html>
<head>
<title>报修单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewBxd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delBxd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.lxr.value = "";
		document.myform.product_serial_num.value = "";
		document.myform.jx_date1.value = "";
		document.myform.jx_date2.value = "";
		document.myform.state.value = "";
		document.myform.gcs.value="";
	}
	
	function add(){
		var destination = "addBxd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';	
		window.open(destination,'添加报修单',fea);	
	}
	
	function edit(id){
		var destination = "editBxd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改报修单',fea);		
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
		document.myform.action = "listBxd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listBxd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>报修单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="add();"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			联系人：<input type="text" name="lxr" value="<%=lxr%>"size="10">&nbsp;&nbsp;
			序列号：<input type="text" name="product_serial_num" value="<%=product_serial_num%>" size="10">&nbsp;&nbsp;
			时间：<input type="text" name="jx_date1" value="<%=jx_date1%> " size="8" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.jx_date1); return false;">&nbsp;至&nbsp;
			<input type="text" name="jx_date2" value="<%=jx_date2%> " size="8" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.jx_date2); return false;">			
			&nbsp;&nbsp;
			报修单状态：<select name="state">
				<option value=""></option>
				<option value="已保存"  <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交"  <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>&nbsp;&nbsp;
			工程师：<input type="text" name="gcs" value="<%=gcs%>" size="10">
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">报修单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr');">联系人<%if(orderName.equals("lxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jxdate');">报修时间<%if(orderName.equals("jxdate")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>	
		<td onclick="doSort('jxr');">报修人<%if(orderName.equals("jxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>					 
		<td onclick="doSort('gcs');">工程师<%if(orderName.equals("gcs")) out.print("<img src='images/" + orderType + ".gif'>"); %>  </td>	
		<td onclick="doSort('cjr');">操作员<%if(orderName.equals("cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %>  </td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map bxd = (Map)it.next();
		%> 
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(bxd.get("id")) %>');"  onDblClick="openWin('<%=StringUtils.nullToStr(bxd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(bxd.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxd.get("client_name")))%></td>
		<td> <%=StringUtils.nullToStr(bxd.get("lxr")) %></td>
		<td  ><%=StringUtils.nullToStr(bxd.get("lxdh")) %> </td>
		<td> <%=StringUtils.nullToStr(bxd.get("jxdate")) %></td>
		<td><%=StringUtils.nullToStr(bxd.get("state")) %></td>
		<td> <%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxd.get("jxr")))%></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxd.get("gcs"))) %> </td>
		<td> <%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxd.get("cjr"))) %></td>		
	 
		<td>
		    <%
		     if(bxd.get("state").equals("已保存"))
		     {
		     %>
	        <a href="#" onclick="edit('<%=StringUtils.nullToStr(bxd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(bxd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(bxd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该报修单" border="0" style="cursor:hand"></a>				 
		    <%}
		     else
		     {
		      %>
		      	<a href="#" onclick="openWin('<%=StringUtils.nullToStr(bxd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看报修单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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

<form name="descForm" action="descBxd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" onload="dyniframesize('desc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
 

</body>
</html>