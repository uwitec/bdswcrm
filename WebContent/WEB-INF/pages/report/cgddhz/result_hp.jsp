<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
CgddhzService cgddhzService = (CgddhzService)VS.findValue("cgddhzService");
String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String productKind = StringUtils.nullToStr(request.getParameter("productKind"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String product_xh = StringUtils.nullToStr(request.getParameter("product_xh"));
String con = "";
con = "日期：" + start_date + "至" + end_date;
if(!kind_name.equals("")){
	con += "&nbsp; 商品类别：" + kind_name;
}
if(!product_name.equals("")){
	con += "&nbsp; 商品名称：" + product_name;
}
if(!product_xh.equals("")){
	con += "&nbsp; 商品规格：" + product_xh;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品采购执行汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />

<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品采购执行汇总</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center"  class="stripe"  cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
		    <TD class=ReportHead rowspan="2">商品编码</TD>
		    <TD class=ReportHead rowspan="2">条形码</TD>
			<TD class=ReportHead rowspan="2">商品名称</TD>
			<TD class=ReportHead rowspan="2">商品规格</TD>
			<TD class=ReportHead colspan="2">未入库</TD>
			<TD class=ReportHead colspan="2">已入库</TD>	
		</TR>
		<TR>
			<TD class=ReportHead width="95">数量</TD>
			<TD class=ReportHead width="95">金额</TD>
			<TD class=ReportHead width="95">数量</TD>
			<TD class=ReportHead width="95">金额</TD>
		</TR>
	</THEAD>
	<TBODY>

	<%
		List list = cgddhzService.getHpcgList(productKind, start_date, end_date, product_name, product_xh);
			
		int hj_wrk_sl = 0;
		double hj_wrk_je = 0;
		
		int hj_yrk_sl = 0;
		double hj_yrk_je = 0;
		
		Map<String,Map> productMaps = new HashMap<String,Map>();
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String id = StringUtils.nullToStr(map.get("product_id"));   //商品编号
				String txm =  StringUtils.nullToStr(map.get("sp_txm")); //商品规格
				String name = StringUtils.nullToStr(map.get("product_name")); //商品名称
				String xh =  StringUtils.nullToStr(map.get("product_xh")); //商品规格
				
				double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();  //金额
				String strNums = StringUtils.nullToStr(map.get("nums"));
				String state = StringUtils.nullToStr(map.get("state"));   //状态 未入库  已入库
				int nums = 0;
				if(!strNums.equals("")){
					nums = new Integer(strNums).intValue();    //数量
				}
				
				Map hzMap = (Map)productMaps.get(id);
				if(hzMap != null){
					if(state.equals("未入库")){
						hzMap.put("wrk_sl", nums);
						hzMap.put("wrk_je", je);
					}else{
						hzMap.put("yrk_sl", nums);
						hzMap.put("yrk_je", je);
					}
				}else{
					hzMap = new HashMap();
					hzMap.put("product_id", id);
					hzMap.put("sp_txm", txm);
					hzMap.put("product_name", name);
					hzMap.put("product_xh", xh);
					if(state.equals("未入库")){
						hzMap.put("wrk_sl", nums);
						hzMap.put("wrk_je", je);
					}else{
						hzMap.put("yrk_sl", nums);
						hzMap.put("yrk_je", je);
					}
					productMaps.put(id, hzMap);
				}				
			}
		}
		
		for (Map.Entry<String, Map> entry : productMaps.entrySet()) {
			   Map map =  entry.getValue();
				String id = StringUtils.nullToStr(map.get("product_id"));   //商品编号
				String txm =  StringUtils.nullToStr(map.get("sp_txm")); //商品规格
				String name = StringUtils.nullToStr(map.get("product_name")); //商品名称
				String xh =  StringUtils.nullToStr(map.get("product_xh")); //商品规格
				
				int yrk_sl = map.get("yrk_sl")==null?0:new Integer(StringUtils.nullToStr(map.get("yrk_sl"))).intValue();  //已入库数量
				double yrk_je = map.get("yrk_je")==null?0:((Double)map.get("yrk_je")).doubleValue();  //已入库金额
				
				hj_yrk_sl += yrk_sl;
				hj_yrk_je += yrk_je;
				
				int wrk_sl = map.get("wrk_sl")==null?0:new Integer(StringUtils.nullToStr(map.get("wrk_sl"))).intValue();//未入库数量
				double wrk_je = map.get("wrk_je")==null?0:((Double)map.get("wrk_je")).doubleValue();  //未入库金额
				
				hj_wrk_sl += wrk_sl;
				hj_wrk_je += wrk_je;
	%>
		<TR>
			<TD class=ReportItemXh nowrap="nowrap"><%=id %>&nbsp;</TD>
			<TD class=ReportItemXh nowrap="nowrap"><%=txm %>&nbsp;</TD>
			<TD class=ReportItem><a href="getHpcgddMxCondition.html?product_id=<%=id %>&start_date=<%=start_date %>&end_date=<%=end_date %>"><%=name %></a>&nbsp;</TD>			
			<TD class=ReportItem><%=xh %>&nbsp;</TD>
		    <TD class=ReportItemMoney nowrap="nowrap"><%=wrk_sl==0?"":wrk_sl %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=wrk_je==0?(wrk_sl==0?"":"0.0"): JMath.round(wrk_je,2) %>&nbsp;</TD>	
		    <TD class=ReportItemMoney nowrap="nowrap"><%=yrk_sl==0?"":yrk_sl %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap"><%=yrk_je==0?(yrk_sl==0?"":"0.0"): JMath.round(yrk_je,2) %>&nbsp;</TD>	
		</TR>	
	<%
			  }
	%>
	
		<TR>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItem>&nbsp;</TD>				
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=hj_wrk_sl %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_wrk_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=hj_yrk_sl %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_yrk_je,2) %>&nbsp;</TD>
		</TR>
 	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击商品编号可查看原始单据列表。</td>
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