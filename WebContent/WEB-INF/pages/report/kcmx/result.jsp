<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

KcMxReportService kcMxReportService = (KcMxReportService)VS.findValue("kcMxReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String store_id = StringUtils.nullToStr(request.getParameter("store_id"));

String isKc0 = StringUtils.nullToStr(request.getParameter("isKc0")); //是否显示0库存商品
String isFse0 = StringUtils.nullToStr(request.getParameter("isFse0")); //是否显示0发生额商品
String isDbd0 = StringUtils.nullToStr(request.getParameter("isDbd0")); //是否包括调拨单
String isDbd1;
if(isDbd0.equals("是"))
{
  isDbd1="1";
}
else
{
  isDbd1="0";
}

String conStr = "";

conStr += "<b>日期：</b>" + start_date + "至" + end_date;

if(!store_id.equals("")){
	conStr += "&nbsp;&nbsp;<b>库房：</b>" + StaticParamDo.getStoreNameById(store_id);
}
if(!kind_name.equals("")){
	conStr += "&nbsp;&nbsp;<b>商品类别：</b>" + kind_name;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出入库汇总——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>出入库汇总</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="5%">序号</TD>
			<TD class=ReportHead width="10%">商品编码</TD>
			<TD class=ReportHead width="20%">商品名称</TD>
			<TD class=ReportHead width="20%">规格</TD>
			<TD class=ReportHead width="5%">单位</TD>
			<TD class=ReportHead width="10%">期初数量</TD>
			<TD class=ReportHead width="10%">收入数量</TD>
			<TD class=ReportHead width="10%">发出数量</TD>
			<TD class=ReportHead width="10%">期末结存</TD>
		</TR>
	</THEAD>
	<TBODY>
<%
List productKcList = kcMxReportService.getKcProductList(product_kind,product_name,store_id);	
Map qcMap = kcMxReportService.getKcqcMap(start_date,store_id);   //期初结果集
Map rkMap = kcMxReportService.getRkNums(product_kind,product_name,start_date,end_date,store_id,isDbd0);//入库数结果集
Map ckMap = kcMxReportService.getCkNums(product_kind,product_name,start_date,end_date,store_id,isDbd0);//出库结果集


int hj_qc_nums = 0; //期初数量合计
int hj_ck_nums = 0; //出库数量合计
int hj_rk_nums = 0; //入库数量合计
int hj_jc_nums = 0; //合计结存数量（库存总商品数）

if(productKcList != null && productKcList.size()>0){
	
	int xh = 0;
	for(int i=0;i<productKcList.size();i++){
		Map map = (Map)productKcList.get(i);

		String product_id = StringUtils.nullToStr(map.get("product_id"));
		String product_name2 = StringUtils.nullToStr(map.get("product_name"));
		String product_xh = StringUtils.nullToStr(map.get("product_xh"));
		String dw = StringUtils.nullToStr(map.get("dw"));
		
		String strNums = StringUtils.nullToStr(qcMap.get(product_id));
		if(strNums.equals("")){
			strNums = "0";
		}
		int qc_nums = new Integer(strNums).intValue();  //期初数
		
		strNums = StringUtils.nullToStr(rkMap.get(product_id));
		if(strNums.equals("")){
			strNums = "0";
		}
		int rk_nums = new Integer(strNums).intValue();  //收入数量
		
		strNums = StringUtils.nullToStr(ckMap.get(product_id));
		if(strNums.equals("")){
			strNums = "0";
		}
		int ck_nums = new Integer(strNums).intValue();  //发出数量
		
		int jc_nums = qc_nums + rk_nums - ck_nums;
		
		boolean is_kc = true;		
		if(isKc0.equals("否")){ //显示0库存商品
			if(jc_nums == 0){
				is_kc = false;
			}
		}
		
		boolean is_fs = true;
		if(isFse0.equals("否")){ //显示0发生额商品
			if(rk_nums == 0 && ck_nums == 0){
				is_fs = false;
			}
		}
		
		if(is_kc && is_fs){
		
			hj_qc_nums += qc_nums;
			hj_rk_nums += rk_nums;
			hj_ck_nums += ck_nums;		
			hj_jc_nums += jc_nums;
			
			xh++;
%>
			<TR>
				<TD class=ReportItemXh><%=xh %></TD>
				<TD class=ReportItem><%=product_id %>&nbsp;</TD>
				<TD class=ReportItem><a href="getKcMxListResult.html?product_id=<%=product_id %>&start_date=<%=start_date %>&end_date=<%=end_date %>&store_id=<%=store_id %>&isDbd1=<%=isDbd1 %>"><%=product_name2 %></a>&nbsp;</TD>
				<TD class=ReportItem><%=product_xh %>&nbsp;</TD>
				<TD class=ReportItemXh><%=dw %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=qc_nums %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=rk_nums %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=ck_nums %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=jc_nums %>&nbsp;</TD>
			</TR>
<%
		}
	}
}
%>	

		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合 计</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_qc_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_rk_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_ck_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_jc_nums %>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<table width="99%">
		<tr>
			<td width="70%" height="30">&nbsp;注：点击查看明细信息。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
	<!--<input type="button" name="button_print" value=" 导 出 " onclick="document.reportForm.submit();">--> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showKcMxCondition.html';"> 
</center>
<form name="reportForm" action="DownLoadXlsServlet" method="post">
<input type="hidden" name="action" value="exportProductKcOutInResult">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<input type="hidden" name="product_name" value="<%=product_name %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
<input type="hidden" name="isKc0" value="<%=isKc0 %>">
<input type="hidden" name="isFse0" value="<%=isFse0 %>">
<input type="hidden" name="isDbd1" value="<%=isDbd1 %>">
</form>
</div>
</body>
</html>