<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] arryNums = (String[])VS.findValue("arryNums");
List serialNumList = (List)VS.findValue("serialNumList");
List tempSerialNumList = new ArrayList();

session.setAttribute("arryPdNums", arryNums);
session.setAttribute("serialPdNumList", tempSerialNumList);

String store_id = (String)VS.findValue("store_id");
String cdate =  DateComFunc.getCurTime();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序列号盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function save(){
		document.myform.submit();
	}
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="insertSerialNumPd.html" method="post" target="hiddenFrame">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>序列号盘点</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="listSerialNumPd.html"> 查看历史盘点记录 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="location.href='pdSerialNumCon.html';"> 返 回 </a>	</td>
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">
					&nbsp;&nbsp;盘点库房：&nbsp;&nbsp;<input type="hidden" name="serialNumPd.store_id" id="store_id" value="<%=store_id %>" ><%=StaticParamDo.getStoreNameById(store_id) %>&nbsp;&nbsp;&nbsp;&nbsp;
					盘点时间：&nbsp;&nbsp;<input type="hidden" name="serialNumPd.cdate" id="cdate" value="<%=cdate %>" /><%=cdate %>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" name="buttonCx" value=" 保存盘点结果" class="css_button3" onclick="save();">
		</td>
	</tr>		
</table>
</form>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="10%">序号</td>
		<td width="30%">实际</td>
		<td width="60%">账面</td>
	</tr>
	</thead>
	<%
	int i=0;
	if(arryNums != null && arryNums.length > 0){
		for(;i<arryNums.length;i++){
			
			String zmdy = "无";  //账面对应情况
			
			if(serialNumList != null && serialNumList.size() > 0){
				for(int k=0;k<serialNumList.size();k++){
					SerialNumMng serialNumMng = (SerialNumMng)serialNumList.get(k);
					
					//如果账面存在该序列号
					if((arryNums[i].trim()).equals(serialNumMng.getSerial_num())){
						
						//账面对应情况取值
						zmdy =serialNumMng.getSerial_num() + "/" + serialNumMng.getProduct_id() + "/" + serialNumMng.getProduct_name() + "/" + serialNumMng.getProduct_xh();
						
						//账面队列中只对应后出列，队列中剩余均为没有对应上的
						tempSerialNumList.add(serialNumMng);
						serialNumList.remove(k);
						break;
					}
				}
			}
			if(zmdy.equals("无")){
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><font color="red"><%=i+1 %></font></td>
		<td><font color="red"><%=arryNums[i] %></font></td>
		<td align="left"><font color="red"><%=zmdy %></font></td>
	</tr>
	<%
			}else{
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=i+1 %></td>
		<td><%=arryNums[i] %></td>
		<td align="left"><%=zmdy %></td>
	</tr>
	<%
			}
		}
	}
	
	if(serialNumList != null && serialNumList.size() > 0){
		for(int k=0;k<serialNumList.size();k++){
			SerialNumMng serialNumMng = (SerialNumMng)serialNumList.get(k);
			tempSerialNumList.add(serialNumMng);
			String zmdy = serialNumMng.getSerial_num() + "/" + serialNumMng.getProduct_id() + "/" + serialNumMng.getProduct_name() + "/" + serialNumMng.getProduct_xh();
			String sjxlh = "无";
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><font color="red"><%=i+1+k %></font></td>
		<td><font color="red"><%=sjxlh %></font></td>
		<td align="left"><font color="red"><%=zmdy %></font></td>
	</tr>			
	<%
		}
	}
	%>
</table>
<BR>
<iframe name="hiddenFrame" id="hiddenFrame" width="0" height="0" style="display: none;"></iframe>
</div>
</body>
</html>
