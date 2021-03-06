<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String dj = StringUtils.nullToStr(request.getParameter("dj"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String xsry = StringUtils.nullToStr(request.getParameter("xsry"));
String dept = StringUtils.nullToStr(request.getParameter("dept"));


String con = "日期：" + start_date + "至" + end_date;

if(!dept.equals("")){
	con += "&nbsp; 部门：" + StaticParamDo.getDeptNameById(dept);
}
if(!xsry.equals("")){
	con += "&nbsp; 销售人员：" + StaticParamDo.getRealNameById(xsry);
}
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品毛利分类汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">

<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script language="javascript" src="print/LodopFuncs.js"></script>
<script language="javascript" src="print/print.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function pageRefresh(){
		document.refreshFormMain.submit();
	}
	function openMx(product_kind,start_date,end_date,client_name,xsry,dept){
		location.href="getHpmlflMxResult.html?product_kind=" + product_kind + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name + "&xsry=" + xsry + "&dept=" + dept;
	}
	
	function onloadTotal(){
		var objs = document.getElementsByName("fz_vl");

		var hjml_vl = parseFloat(document.getElementById("fm").value);
		
		for(var i=0;i<objs.length;i++){
			var objId = objs[i].id;
			var arryTemp = objId.split("_");
			
			var zbId = "zb_" + arryTemp[1];
			try{
				document.getElementById(zbId).innerText = ((parseFloat(document.getElementById(objId).value)/hjml_vl*100).toFixed(2)) + "%";
			}catch(e){
				document.getElementById(zbId).innerText = "0.00%";
			}
		}
	}
	function isIE(){ //ie?
		  if (window.navigator.userAgent.toLowerCase().indexOf("msie") >= 1)
		  	return true;
		  else
		  	return false;
	}

	if(!isIE()){ //firefox innerText define
		  HTMLElement.prototype.__defineGetter__( "innerText",
			  function(){
				  var anyString = "";
				  var childS = this.childNodes;
				  for(var i=0; i <childS.length; i++) {
					  if(childS[i].nodeType==1)
					  	anyString += childS[i].tagName=="BR" ? '\n' : childS[i].innerText;
					  else if(childS[i].nodeType==3)
					  	anyString += childS[i].nodeValue;
				  }
				  return anyString;
			  }
		  );
		  HTMLElement.prototype.__defineSetter__( "innerText",
			  function(sText){
			  	this.textContent=sText;
			  }
		  );
	}		
</script>
</head>
<body align="center" onload="onloadTotal();">
<div class="rightContentDiv" id="divContent">
<form name="refreshFormMain" action="getHpmlflHzResult.html" method="post">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="xsry" value="<%=xsry %>">
<input type="hidden" name="dj" value="<%=dj %>">
</form>
<div id="printDIV">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品毛利分类汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>商品类别名称</TD>
			<TD class=ReportHead>数量</TD>			
			<TD class=ReportHead>销售金额</TD>
			<TD class=ReportHead>不含税金额</TD>
			<TD class=ReportHead>成本</TD>
			<TD class=ReportHead>毛利</TD>
			<TD class=ReportHead>毛利率</TD>	
			<TD class=ReportHead>占比</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;    //合计数量
double hj_je = 0;   //合计金额
double hj_ml = 0;   //合计毛利
double hj_cb = 0;   //合计成本
double hj_bhsje = 0; //合计不含税金额
if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("name"));
		
		String strBlank = "";
		for(int k=0;k<id.length()/2;k++){
			strBlank += "　　　";
		}
		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		double cb = map.get("hjcb")==null?0:((Double)map.get("hjcb")).doubleValue(); //成本
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue(); //成本
				
			
		double ml = bhsje - cb;
		
		String strNums = StringUtils.nullToStr(map.get("nums"));		
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}
		
		if(id.length() == 2){
			hj_nums += nums;
			hj_je += je;
			hj_cb += cb;	
			hj_ml += ml;
			hj_bhsje += bhsje;
		}
		
		String stl = "BACKGROUND-COLOR: #FFFFFF";
		if(id.length() == 2){
			stl = "BACKGROUND-COLOR: #E8E8E8";
		}else if(id.length() == 4){
			stl = "BACKGROUND-COLOR: #FBFBFB";
		}
		
		if(nums != 0 || je != 0){
%>
		<TR style="<%=stl %>">
			<TD class=ReportItem><%=strBlank %><a href="javascript:openMx('<%=id %>','<%=start_date %>','<%=end_date %>','<%=client_name %>','<%=xsry %>','<%=dept %>');"><%=name %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><input type="hidden" name="fz_vl" id="fz_<%=id %>" value="<%=ml %>"><%=JMath.round(ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.percent(ml,bhsje) %>&nbsp;</TD>	
			<TD class=ReportItemMoney><span id="zb_<%=id %>"></span>&nbsp;</TD>	
		</TR>
	
<%
		}
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_cb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><input type="hidden" name="fm" id="fm" value="<%=hj_ml %>"><%=JMath.round(hj_ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(hj_ml,hj_bhsje) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">--&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：毛利 = 不含税金额 - 成本；毛利率 = 毛利 / 不含税金额 * 100%；点击类别名称可查看明细信息。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
</div>
<center class="Noprint">
	<input type="button" name="btnRes" value=" 刷新"  onclick="pageRefresh();"> &nbsp;&nbsp;
    <input type="button" name="button_printsetup" value=" 打印预览 "  onclick="printPre('2','A4','printDIV');"> &nbsp;&nbsp;
	<input type="button" name="button_print" value=" 打 印 " onclick="printReport('货品毛利分类汇总表','2','A4','printDIV');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showHpmlflHzCon.html';"> 
</center>
</div>
</body>
</html>