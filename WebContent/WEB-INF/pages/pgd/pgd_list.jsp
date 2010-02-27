<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pgdPage");

String p_cj_date1 = (String)VS.findValue("p_cj_date1");
String p_cj_date2 = (String)VS.findValue("p_cj_date2");
 
String p_state = (String)VS.findValue("p_state");
String linkman = (String)VS.findValue("linkman");
String p_wx_state=(String)VS.findValue("p_wx_state");
 

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>派工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewPgd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delSfd.html?id="+id;
		}
	}
	
	function clearAll(){
		document.myform.linkman.value = "";
		document.myform.cj_date1.value = "";
		document.myform.cj_date2.value = "";
		document.myform.wx_state.value = "";
		document.myform.state.value = "";
		document.myform.jxr.value = "";
	}
	
	function add(){
		var destination = "addSfd.html";
			var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'售后服务单',fea);	
	}	
	function edit(id){
		var destination ="editPgd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息');			
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
		document.myform.action = "listPgd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listPgd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>派工单</b></td>
		<td class="csstitle" width="25%">
		 
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			联系人：<input type="text" name="linkman" value="<%=linkman %>">&nbsp;&nbsp;
			时间：<input type="text" name="p_cj_date1" value="<%=p_cj_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()" >
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="p_cj_date2" value="<%=p_cj_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()" >
			&nbsp;&nbsp;
			状态：<select name="p_state">
				<option value=""></option>
				<option value="已保存" <%if(p_state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(p_state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>&nbsp;&nbsp;
			维修状态：<select name="p_wx_state">
				<option value=""></option>
				<option value="待处理" <%if(p_wx_state.equals("待处理")) out.print("selected"); %>>待处理</option>
				<option value="已处理" <%if(p_wx_state.equals("已处理")) out.print("selected"); %>>已处理</option>
			</select>&nbsp;&nbsp;
			 
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('p_id');">派工单编号<%if(orderName.equals("p_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">往来单位<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('linkman');">联系人<%if(orderName.equals("linkman")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">联系电话<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('p_yy_date');">派工时间<%if(orderName.equals("p_yy_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('p_state');">状态<%if(orderName.equals("p_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('p_wx_state');">维修状态<%if(orderName.equals("p_wx_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('p_pgr');">派工人<%if(orderName.equals("p_pgr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>				
		<td onclick="doSort('p_wxr');">维修人<%if(orderName.equals("p_wxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>						
		<td onclick="doSort('p_cjr');">操作员<%if(orderName.equals("p_cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map pgd = (Map)it.next();		
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(pgd.get("p_sfd_id"))%>')">
		<td><%=StringUtils.nullToStr(pgd.get("p_id")) %></td>
		<td><%=StringUtils.nullToStr(StaticParamDo.getClientNameById((String)pgd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("linkman")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("mobile")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("p_yy_date")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("p_state")) %></td>	
		<td><%=StringUtils.nullToStr(pgd.get("p_wx_state")) %></td>	 		 
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("p_pgr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("p_wxr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("p_cjr"))) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(pgd.get("p_state")).equals("已提交")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(pgd.get("p_sfd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看派工单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(pgd.get("p_sfd_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改派工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(pgd.get("p_sfd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看派工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			 
		<%	
		}		
		%>
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
