<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page linkmanPage = (Page)VS.findValue("linkmanPage");
String[] lxrlx=(String[])VS.findValue("lxrlx");

String linkmanyddh = (String)VS.findValue("linkmanyddh");
String linkmanname = (String)VS.findValue("linkmanname");
String lxid=(String)VS.findValue("lxid");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发送短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	

	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "deletelinkman.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addlinkman.html";
		var fea = 'width=400,height=300,left=' + (screen.availWidth-450)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加联系人',fea);	
	}
	
	function edit(id){
		var destination = "editlinkman.html?id=" + id;
		var fea = 'width=400,height=300,left=' + (screen.availWidth-450)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改联系人',fea);		
	}		
	
	function clearAll(){
		document.myform.linkmanname.value = "";
		document.myform.linkmanyddh.value = "";
		document.myform.lxid.value = "";
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
		document.myform.action = "listlinkman.html";
		document.myform.submit();
	}	
	
	function check_all(obj,cName)
   {
    var checkboxs = document.getElementsByName(cName);
    for(var i=0;i<checkboxs.length;i++){checkboxs[i].checked = obj.checked;}
   }
   function sel()
   {
			var k = 0;
			var obj = document.getElementsByName("sendLinkman");
			for(var i=0;i<obj.length;i++){
				var o = obj[i];
				if(o.checked){
					k = k + 1;
				}
			}
			if(k == 0){
				alert("请选择发送联系人!");
				return;
			}
			var destination = "addSMS.html?sendLinkman=";
			var linkmanvalue="";
			for(var j=0;j<obj.length;j++)
			{
			   if(obj[j].checked)
			   {
		 
			      linkmanvalue=linkmanvalue+obj[j].value+",";
			   }
			   
			}
			destination=destination+linkmanvalue;
		    var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		     window.open(destination,'发送短信',fea);	
			
		}
		
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listlinkman.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>发送短信</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添加联系人 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
		   联系人：<input type="text" name="linkmanname" value="<%=linkmanname %>"/>&nbsp;&nbsp;&nbsp;&nbsp;
		   手机：  <input type="text" name="linkmanyddh" value="<%=linkmanyddh %>"/>&nbsp;&nbsp;&nbsp;&nbsp;
		   联系人类型:<select name="lxid">
		   <option value=""></option>
	 	<%
	    if(lxrlx != null && lxrlx.length > 0) { 
			for(int i=0;i<lxrlx.length;i++){
		%>
	    <option value="<%=lxrlx[i]%>" <%if(lxrlx[i].equals(lxid)) out.print("selected");%>><%=lxrlx[i] %></option>
		<%
			}
		} 
		%>
		   </select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
	    <td><input type="checkbox" name="all" value="" onclick="check_all(this,'sendLinkman')"></td>
		<td onclick="doSort('name');">联系人<%if(orderName.equals("name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('yddh');">手机<%if(orderName.equals("yddh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxid');">类型<%if(orderName.equals("lxid")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = linkmanPage.getResults();
	Iterator its = list.iterator();
	
	while(its.hasNext()){
		Map linkman = (Map)its.next();
		
	%>
	<tr class="a1"  >
	    <td class="a1"><input type="checkbox" name="sendLinkman" id="sendLinkman" value="<%=StringUtils.nullToStr(linkman.get("id")) %>"></td>
		<td class="a1"><%=StringUtils.nullToStr(linkman.get("name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(linkman.get("yddh")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(linkman.get("lxid")) %></td>
		
		<td class="a1">
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(linkman.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(linkman.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
    <tr>
		<td class="a2"><input type="button" class="css_button3" name="buttonx" value="发短信" onclick=" sel();"></td>
	</tr>
	<tr>
		<td class="page"><%=linkmanPage.getPageScript() %></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
