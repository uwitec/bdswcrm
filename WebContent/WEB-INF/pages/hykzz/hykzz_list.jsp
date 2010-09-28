<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("hykzzPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType"); 

String hykh = (String)VS.findValue("hykh");
String state = (String)VS.findValue("state");
%>

<html>
<head>
<title>会员卡制作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(id){
		var destination = "viewHykzz.html?id="+id;
		var fea = 'width=500,height=500,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(hykh){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delHykzz.html?hykh=" + hykh;
		}
	}
	
	function add(){
		var destination = "addHykzz.html";
		var fea = 'width=500,height=500,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加会员卡',fea);	
	}
	
	function edit(id){
		var destination = "editHykzz.html?id=" + id;
		var fea = 'width=500,height=500,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改会员卡',fea);		
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
	
	function refreshPage(){
		document.myform.action = "listHykzz.html";
		document.myform.submit();
	}
	
	function clearAll(){
		document.myform.hykh.value = "";
		document.myform.state.value = "";		
	}
</script>
</head>
<body >
<form name="myform" action="listHykzz.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>会员卡制作</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			会员卡号：<input type="text" name="hykh" value="<%=hykh %>" size="25" >	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			状态:<select name="state">
							<option value=""></option>
							<option value="已使用" <%if(state.equals("已使用")) out.print("selected"); %>>已使用</option>
							<option value="未使用" <%if(state.equals("未使用")) out.print("selected"); %>>未使用</option>
						</select>		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%" onclick="doSort('hykh');">会员卡号<%if(orderName.equals("hykh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('dept');">制卡机构<%if(orderName.equals("dept")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="10%" onclick="doSort('csjf');">初始积分<%if(orderName.equals("csjf")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td width="15%" onclick="doSort('ssfl');">所属分类<%if(orderName.equals("ssfl")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td width="10%" onclick="doSort('card_type');">卡类型<%if(orderName.equals("card_type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="9%" onclick="doSort('cz_date');">创建日期<%if(orderName.equals("cz_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="8%" onclick="doSort('czr');">创建人<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>	
		<td width="8%">状态</td>		
		<td width="10%">操作</td>		
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Hykzz info = (Hykzz)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(info.getHykh()) %></td>
		<td class="a1"  align="left"><%=StringUtils.nullToStr(info.getDept()) %></td>
		<td class="a1"  align="right"><%=info.getCsjf() %></td>
		<td class="a1"  align="left"><%=StaticParamDo.getHykflNameById(StringUtils.nullToStr(info.getSsfl())) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getCard_type()) %></td>
		<td class="a1"><%=DateComFunc.formatDate(info.getCz_date()) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getState()) %></td>
		<td class="a1">
			<a href="javascript:void(0);" onclick="edit('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="del('<%=StringUtils.nullToStr(info.getHykh()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
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
