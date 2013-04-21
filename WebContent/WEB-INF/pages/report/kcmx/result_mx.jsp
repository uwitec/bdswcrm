<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

KcMxReportService kcMxReportService = (KcMxReportService)VS.findValue("kcMxReportService");

Product product = (Product)VS.findValue("product");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String product_id = StringUtils.nullToStr(request.getParameter("product_id"));
String store_id = StringUtils.nullToStr(request.getParameter("store_id"));

String conStr = "";
conStr += "<b>商品编号：</b>" + product.getProductId();
conStr += "&nbsp;&nbsp;<b>商品名称：</b>" + product.getProductName();
conStr += "&nbsp;&nbsp;<b>规格：</b>" + product.getProductXh();
conStr += "&nbsp;&nbsp;<b>日期：</b>" + start_date + "至" + end_date;

if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出入库汇总——明细信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		if(url == ""){
			return;
		}
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>出入库汇总——明细</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>开单日期</TD>
			<TD class=ReportHead>单据编号</TD>
			<TD class=ReportHead>往来单位</TD>
			<TD class=ReportHead>收入数量</TD>
			<TD class=ReportHead>发出数量</TD>
			<TD class=ReportHead>结存数</TD>
		</TR>
	</THEAD>
	<TBODY>
<%		
	//根据商品编号、开始时间、库房编号取库存期初情况
	Map qcMap = kcMxReportService.getKcqcMap(start_date,store_id);
	
	String strNums = "0";   //期初数
	if(qcMap != null){
		strNums = StringUtils.nullToStr(qcMap.get(product_id));
		if(strNums.equals("")){
			strNums = "0";
		}
	}		
	int qc_nums = new Integer(strNums).intValue();  //期初数
%>
	<TR>
		<TD class=ReportItem>期初结存&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItem>&nbsp;</TD>
		<TD class=ReportItemMoney><%=qc_nums %>&nbsp;</TD>
	</TR>
		
		
<%

	List list =  kcMxReportService.getKcbhMxList(product_id,start_date,end_date,store_id);

	int jc_nums = qc_nums;  //合计结存数
	
	if(list != null && list.size()>0){
		for(int k=0;k<list.size();k++){
			Map fsMap = (Map)list.get(k);
			
			String fsrq = StringUtils.nullToStr(fsMap.get("fsrq"));
			String dj_id = StringUtils.nullToStr(fsMap.get("dj_id"));				
			String fsFlag = StringUtils.nullToStr(fsMap.get("flag"));				
			String strFsNums = StringUtils.nullToStr(fsMap.get("nums"));
			String client_name = StaticParamDo.getClientNameById(StringUtils.nullToStr(fsMap.get("client_name")));
			
			if(strFsNums.equals("")){
				strFsNums = "0";
			}
			
			int fs_nums = new Integer(strFsNums).intValue();
			
			String type= "";  //业务类型
			String url = "";  //原始单据链接
			if(dj_id.indexOf("JH") != -1){
				//进货单（采购入库）
				type = "采购入库";
				url = "viewJhd.html?id=";
			}else if(dj_id.indexOf("CGTH") != -1){
				//采购退货出库
				type = "采购退货出库";
				url = "viewCgthd.html?id=";
			}else if(dj_id.indexOf("LS") != -1){
				//零售出库
				type = "零售出库";
				url = "viewLsd.html?id=";
			}else if(dj_id.indexOf("XS") != -1){
				//销售出库
				type = "销售出库";
				url = "viewXsd.html?id=";
			}else if(dj_id.indexOf("TH") != -1){
				//销售退货入库
				type = "销售退货入库";
				url = "viewThd.html?thd_id=";
			}else if(dj_id.indexOf("DB") != -1){
				if(fsFlag.equals("出库")){
					type = "库房调拨（出库）";
				}else if(fsFlag.equals("入库")){
					type = "库房调拨（入库）";
				}
				url = "viewKfdb.html?id=";
			}else if(dj_id.indexOf("PD") != -1){
				if(fs_nums > 0){
					fsFlag = "入库";
					type = "盘点报溢";
				}else{
					fsFlag = "出库";
					type = "盘点报损";
					fs_nums = 0 - fs_nums;
				}
				url = "viewKcpd.html?id=";
			}else if(dj_id.indexOf("CX") != -1){
				type = "拆卸单";
				url = "viewCxd.html?id=";
			}else if(dj_id.indexOf("ZZ") != -1){
				type = "组装单";
				url = "viewZzd.html?id=";
			}else if(dj_id.indexOf("YR") != -1){
				type = "移库入库";
				url = "viewYkrk.html?id=";
			}else if(dj_id.indexOf("YC") != -1){
				type = "移库出库";
				url = "viewYkck.html?id=";
			}
			
%>
			<TR>
				<TD class=ReportItem><%=type %>&nbsp;</TD>
				<TD class=ReportItem><%=fsrq %>&nbsp;</TD>			
				<TD class=ReportItem><a href="#" onclick="openWin('<%=url+dj_id %>');"><%=dj_id %></a>&nbsp;</TD>
				<TD class=ReportItem><%=client_name %>&nbsp;</TD>	
				<%
				if(fsFlag.equals("入库")){	
					jc_nums += fs_nums;
				%>	
				<TD class=ReportItemMoney><%=fs_nums %>&nbsp;</TD>				
				<TD class=ReportItemMoney>&nbsp;</TD>				
				<TD class=ReportItemMoney>&nbsp;</TD>					
				<%
				}else{
					jc_nums -= fs_nums;
				%>
				<TD class=ReportItemMoney>&nbsp;</TD>				
				<TD class=ReportItemMoney><%=fs_nums %>&nbsp;</TD>				
				<TD class=ReportItemMoney>&nbsp;</TD>					
				<%
				}
				%>			
			</TR>			
<%
		}
	}
		
%>
		<TR>			
			<TD class=ReportItem>期末结存</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>				
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItemMoney>&nbsp;</TD>			
			<TD class=ReportItemMoney>&nbsp;</TD>			
			<TD class=ReportItemMoney><%=jc_nums %>&nbsp;</TD>				
		</TR>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30">&nbsp;注：点击单据编号可以查看原始单据。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>