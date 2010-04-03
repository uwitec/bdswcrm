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
String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));

String conStr = "";
conStr += "<b>日期：</b>" + start_date + "至" + end_date;
if(!product_kind.equals("")){
	conStr += "&nbsp;&nbsp;<b>商品类别：</b>" + kind_name;
}
%>

<html>
<head>
<title>库存成本变化——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>库存成本变化</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead rowspan="2" width="15%">商品名称</TD>
			<TD class=ReportHead rowspan="2" width="15%">商品规格</TD>
			<TD class=ReportHead rowspan="2">日期</TD>
			<TD class=ReportHead rowspan="2">业务类型</TD>
			<TD class=ReportHead rowspan="2">单据编号</TD>
			<TD class=ReportHead colspan="3">入库</TD>
			<TD class=ReportHead colspan="3">出库</TD>
			<TD class=ReportHead colspan="3">结存</TD>
		</TR>
		<TR>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>单位成本</TD>
			<TD class=ReportHead>成本</TD>						
		</TR>		
	</THEAD>
	<TBODY>
<%

double kczz = 0 ; //库存总值


List productKcList = kcMxReportService.getProductKcList(product_kind,product_name,"");

if(productKcList != null && productKcList.size()>0){
	
	int jc_nums = 0; //结存数量
	double jc_cb = 0;//结存成本
	
	for(int i=0;i<productKcList.size();i++){
		Map map = (Map)productKcList.get(i);

		String product_id = StringUtils.nullToStr(map.get("product_id"));
		String product_name2 = StringUtils.nullToStr(map.get("product_name"));
		String product_xh = StringUtils.nullToStr(map.get("product_xh"));
		String store_id = StringUtils.nullToStr(map.get("store_id"));
		
		//根据商品编号、开始时间、库房编号取库存期初情况
		Map qcMap = kcMxReportService.getKcqcMxMap(product_id,start_date,store_id);
		
		String strNums = "0";   //期初数
		String qc_date = "";    //期初时间(一般情况为用户选择的开始时间，如果开始时间小于系统的启用时间，则取系统启用时间)		
		double price = 0;       //期初金额
		
		if(qcMap != null){
			strNums = StringUtils.nullToStr(qcMap.get("nums"));
			if(strNums.equals("")){
				strNums = "0";
			}
			qc_date = StringUtils.nullToStr(qcMap.get("qc_date"));
			price = qcMap.get("price")==null?0:((Double)qcMap.get("price")).doubleValue();
		}
		
		int nums = new Integer(strNums).intValue();
		
		
		
		double qccb = price * nums;
		
		jc_nums = nums;
		jc_cb = qccb;
%>
		<TR>
			<TD class=ReportItem><%=product_name2 %>&nbsp;</TD>
			<TD class=ReportItem><%=product_xh %>&nbsp;</TD>			
			<TD class=ReportItemXh><%=qc_date %>&nbsp;</TD>
			<TD class=ReportItemXh>库存期初&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(qccb,2) %>&nbsp;</TD>						
		</TR>
		
		
<%

		List list =  kcMxReportService.getKcCbBhMxList(product_id,start_date,end_date);
		

		int rk_nums = 0; //入库数量
		double rk_cbhj = 0; //入库成合计
		int ck_nums = 0;  //出库数量
		double ck_cbhj = 0; //出库成本合计
		double jc_dwcb = price; //结存单位成本
		
		
		if(list != null && list.size()>0){
			for(int k=0;k<list.size();k++){
				Map fsMap = (Map)list.get(k);
				
				String fsrq = StringUtils.nullToStr(fsMap.get("fsrq"));
				String dj_id = StringUtils.nullToStr(fsMap.get("dj_id"));
				
				String fsFlag = StringUtils.nullToStr(fsMap.get("flag"));
				
				int fs_nums = new Integer(StringUtils.nullToStr(fsMap.get("nums"))).intValue();
				double fs_price = fsMap.get("price")==null?0:((Double)fsMap.get("price")).doubleValue();
				double fs_cb = Amount.cheng(fs_price , fs_nums);
				
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
				}
%>
				<TR>
					<TD class=ReportItem>&nbsp;</TD>
					<TD class=ReportItem>&nbsp;</TD>			
					<TD class=ReportItemXh><%=fsrq %></TD>
					<TD class=ReportItemXh><%=type %></TD>			
					<TD class=ReportItemXh><a href="#" onclick="openWin('<%=url+dj_id %>');"><%=dj_id %></a></TD>
				<%
				if(fsFlag.equals("入库")){
					rk_nums += fs_nums;
					if(!type.equals("盘点报溢")){
						rk_cbhj += fs_cb;
						
						jc_nums += fs_nums;
						jc_cb += fs_cb;
						
						jc_dwcb = jc_cb/jc_nums;
					}else{						
						fs_price = jc_dwcb;  //发生成本等于上一次结存时的成本，不变化
						fs_cb = Amount.cheng(fs_price , fs_nums);
						
						rk_cbhj += fs_cb;
						
						jc_nums += fs_nums;
						jc_cb += fs_cb;
						
						jc_dwcb = jc_cb/jc_nums;
					}

				%>	
					<TD class=ReportItemMoney><%=fs_nums %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(fs_price,2) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(fs_cb,2) %>&nbsp;</TD>
					
					<TD class=ReportItemMoney>&nbsp;</TD>
					<TD class=ReportItemMoney>&nbsp;</TD>
					<TD class=ReportItemMoney>&nbsp;</TD>
					
					<TD class=ReportItemMoney><%=jc_nums %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(jc_dwcb,2) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(jc_cb,2) %>&nbsp;</TD>						
				<%
				}else{
					ck_nums += fs_nums;
					ck_cbhj += fs_cb;
					
					jc_nums -= fs_nums;
					jc_cb = jc_dwcb * jc_nums;
				%>
					<TD class=ReportItemMoney>&nbsp;</TD>
					<TD class=ReportItemMoney>&nbsp;</TD>
					<TD class=ReportItemMoney>&nbsp;</TD>
					
					<TD class=ReportItemMoney><%=fs_nums %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(fs_price,2) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(fs_cb,2) %>&nbsp;</TD>		
					
					<TD class=ReportItemMoney><%=jc_nums %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(jc_dwcb,2) %>&nbsp;</TD>
					<TD class=ReportItemMoney><%=JMath.round(jc_cb,2) %>&nbsp;</TD>							
				<%
				}
				%>			
				</TR>			
<%
			}
		}
		
		kczz += jc_cb;
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItemXh style="font-weight:bold">小计&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=rk_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(rk_cbhj,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=ck_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ck_cbhj,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>						
		</TR>
<%
	}
}
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItemXh style="font-weight:bold">合计&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(kczz,2) %>&nbsp;</TD>						
		</TR>
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击单据编号可以查看原始单据。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showKcJeHzCondition.html';"> 
</center>
</body>
</html>