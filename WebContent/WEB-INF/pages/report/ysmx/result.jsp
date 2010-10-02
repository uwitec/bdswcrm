<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientWlStatService clientWlStatService = (ClientWlStatService)VS.findValue("clientWlStatService");
List clientList = (List)VS.findValue("clientList");

String q_client_name = StringUtils.nullToStr(request.getParameter("client_name"));   //开始时间

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));   //开始时间
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));       //结束时间
String flag = StringUtils.nullToStr(request.getParameter("flag"));               //不显示0往来单位
String flag2 = StringUtils.nullToStr(request.getParameter("flag2"));             //不显示余额为0往来单位
%>

<html>
<head>
<title>客户应收汇总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(client_name){
		var fea ='width=' + (screen.availWidth-4) + ',height=' + (screen.availHeight-30) + ',left=0,top=0,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		var url = "getYsmxMxResult.html?start_date=<%=start_date%>&end_date=<%=end_date%>&client_name=" + client_name;
		window.open(url,"客户销售汇总",fea);	
	}	
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户应收汇总表</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<form name="myform" method="post" action="getYsmxMxResult.html">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
	<input type="hidden" name="client_name" value="">
</form>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="18%">客户名称</TD>
			<TD class=ReportHead width="10%">客户经理</TD>
			<TD class=ReportHead width="8%">期初数</TD>
			<TD class=ReportHead width="8%">本期发生数</TD>
			<TD class=ReportHead width="8%">本期收款</TD>
			<TD class=ReportHead width="8%">当前应收款</TD>
			<TD class=ReportHead width="8%">未到期应收款</TD>
			<TD class=ReportHead width="8%">超期应收款</TD>
			<TD class=ReportHead width="8%">其它应收</TD>
			<TD class=ReportHead width="8%">预收款</TD>
			<TD class=ReportHead width="8%">最长超期天数</TD>
		</TR>
	</THEAD>
	
	<TBODY>
<%

double hj_qcs = 0;
double hj_bqfs = 0;
double hj_ysje = 0;
double hj_dqys = 0;
double hj_wdqysk = 0;
double hj_cqysk = 0;
double hj_ysk = 0;
double hj_qtysk = 0;

Map qcMap = clientWlStatService.getClientQc(start_date);
Map infoMap = clientWlStatService.getClientWlInfo(start_date,end_date,q_client_name);

Map maxCqtsMap = clientWlStatService.getClientMaxCqts(q_client_name);  //最长超期天数
Map wdqyskMap = clientWlStatService.getClientWdqysk(q_client_name);    //未到期应收款
Map cqyskMap = clientWlStatService.getClientCqysk(q_client_name);      //超期应收款 

Map yskMap = clientWlStatService.getClientYushouk(q_client_name);      //预收款
Map qcjsMap = clientWlStatService.getClientQcjsye(q_client_name);      //期初结算
Map tzjsMap = clientWlStatService.getClientTzjsye(q_client_name);      //调账（应收）结算

if(clientList != null && clientList.size() > 0){
	for(int i=0;i<clientList.size();i++){
		Map map = (Map)clientList.get(i);
		
		String client_id = StringUtils.nullToStr(map.get("id"));
		String client_name = StringUtils.nullToStr(map.get("name"));
		String khjl = StringUtils.nullToStr(map.get("khjl"));
		
		double qcs = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //期初数		
		double bqfs = infoMap.get(client_id+"应收发生")==null?0:((Double)infoMap.get(client_id+"应收发生")).doubleValue();  //本期发生
		double ysje = infoMap.get(client_id+"已收发生")==null?0:((Double)infoMap.get(client_id+"已收发生")).doubleValue();  //本期已收
		
		String strCqts = StringUtils.nullToStr(maxCqtsMap.get(client_id));
		int cqts = 0; //最长超期天数
		if(!strCqts.equals("")){
			cqts = new Integer(strCqts).intValue();
		}
		double wdqysk = wdqyskMap.get(client_id)==null?0:((Double)wdqyskMap.get(client_id)).doubleValue();  //未到期应收款 
		double cqysk = cqyskMap.get(client_id)==null?0:((Double)cqyskMap.get(client_id)).doubleValue();  //超期应收款
		
		double ysk = yskMap.get(client_id)==null?0:((Double)yskMap.get(client_id)).doubleValue();  //预收款
		double qtysk = (qcjsMap.get(client_id)==null?0:((Double)qcjsMap.get(client_id)).doubleValue()) + (tzjsMap.get(client_id)==null?0:((Double)tzjsMap.get(client_id)).doubleValue()); //其它应收款 

		double dqys = qcs + bqfs - ysje;  //当前应收
		
		//不显示0往来单位
		boolean bl = false;		
		if(flag.equals("是")){
			if(bqfs!=0 || ysje !=0){
				bl = true;
			}
		}else{
			bl = true;
		}
		
		//不显示余额为0往来单位
		boolean bl2 = false;		
		if(flag2.equals("是")){
			if(dqys !=0){
				bl2 = true;
			}
		}else{
			bl2 = true;
		}
		
		
		if(bl && bl2){
			hj_qcs += qcs;
			hj_bqfs += bqfs;
			hj_ysje += ysje;
			hj_dqys += dqys;
			hj_wdqysk += wdqysk;
			hj_cqysk += cqysk;
			hj_ysk += ysk;
			hj_qtysk += qtysk;
			
%>	
		<TR>
			<TD class=ReportItem><a href="getYsmxMxResult.html?start_date=<%=start_date%>&end_date=<%=end_date%>&client_name=<%=client_id %>"><%=client_name %></a></TD>
			<TD class=ReportItemXH><%=khjl %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="期初数"><%=JMath.round(qcs,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期发生数"><%=JMath.round(bqfs,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="本期收款"><%=JMath.round(ysje,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="当前应收款"><%=JMath.round(dqys,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="未到期应收款"><%=JMath.round(wdqysk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="超期应收款"><font color="red"><%=JMath.round(cqysk,2) %></font></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="其它应收"><%=JMath.round(qtysk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="预收款"><%=JMath.round(ysk,2) %></TD>
			<TD class=ReportItemMoney nowrap="nowrap" title="最长超期天数"><%=cqts %></TD>
		</TR>

<%		
		}
	}
	
}

%>
	<TR>
		<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>
		<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_qcs,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_bqfs,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_ysje,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_dqys,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_wdqysk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><font color="red"><%=JMath.round(hj_cqysk,2) %></font></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_qtysk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold" nowrap="nowrap"><%=JMath.round(hj_ysk,2) %></TD>
		<TD class=ReportItemMoney style="font-weight:bold">--</TD>
	</TR>
	</TBODY>
	
</TABLE>
<br>
<table width="99%">
		<tr>
		<td width="70%" height="30">&nbsp;说明：点击客户编号或客户名称打开应收对账单
		</td>
		<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>