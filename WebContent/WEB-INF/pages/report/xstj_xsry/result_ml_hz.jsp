<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));
String isShowZ = StringUtils.nullToStr(request.getParameter("isShowZ"));         //是否显示销售额为零客户

String[] xwTypes = request.getParameterValues("xwType");   //查看的业务类型

//销售人员列表
List userList = (List)VS.findValue("userList");


%>

<html>
<head>
<title>业务员销售毛利汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function subForm(vl){
		document.reportForm.xsry_id.value = vl;		
		document.reportForm.submit();
	}
</script>
</head>
<body align="center" >
<form action="getXsmlMxXsryResult.html" name="reportForm" method="post">
	<input type="hidden" name="start_date"value="<%=start_date %>">
	<input type="hidden" name="end_date"value="<%=end_date %>">
	<input type="hidden" name="client_name"value="<%=client_name %>">
	<input type="hidden" name="dj_id"value="<%=dj_id %>">
	<input type="hidden" name="isShowZ"value="<%=isShowZ %>">
	<input type="hidden" name="xsry_id" value="">
	<%
	if(xwTypes != null && xwTypes.length>0){
		for(int i=0;i<xwTypes.length;i++){
	%>
		<input type="hidden" name="xwType" value="<%=xwTypes[i] %>">
	<%
		}
	}
	%>
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员销售毛利汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>业务员编号</TD>
			<TD class=ReportHead>业务员姓名</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>销售收入</TD>
			<TD class=ReportHead>成本</TD>
			<TD class=ReportHead>毛利</TD>	
			<TD class=ReportHead>毛利率</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;
double hj_xssr =0;
double hj_cb = 0;
double hj_ml = 0;
if(userList != null && userList.size()>0){
	
	for(int i=0;i<userList.size();i++){
		
		Map map = (Map)userList.get(i);
		
		String user_id = StringUtils.nullToStr(map.get("user_id"));
		String real_name = StringUtils.nullToStr(map.get("real_name"));
		
		//销售单列表
		List xsdList = null;

		if(StringUtils.isStrInArray(xwTypes,"1")){
			xsdList = xstjXsryService.getXsdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		//零售单列表
		List lsdList = null;
		if(StringUtils.isStrInArray(xwTypes,"2")){
			lsdList = xstjXsryService.getLsdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		//退货单列表
		List thdList = null;
		if(StringUtils.isStrInArray(xwTypes,"3")){
			thdList = xstjXsryService.getThdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		boolean hasXs = false;
		boolean hasLs = false;
		boolean hasTh = false;
		
		if(xsdList != null && xsdList.size()>0){
			hasXs = true;
		}
		if(lsdList != null && lsdList.size()>0){
			hasLs = true;
		}
		if(thdList != null && thdList.size()>0){
			hasTh = true;
		}
		
		if(hasXs || hasLs || hasTh || isShowZ.equals("是")){
			
			int xs_nums_zj = 0;  //数量总计
			double xssr_zj = 0; //销售收入总计
			double cb_zj = 0;  //成本总计
			double ml_zj = 0;  //毛利总计

			
			//销售单列表及明细部分		
			if(xsdList != null && xsdList.size()>0){
				for(int k=0;k<xsdList.size();k++){
					Map xsdMap = (Map)xsdList.get(k);
					
					String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
	
					List mxList = xstjXsryService.getXsdMxList(xsd_id);
					
			
					if(mxList != null && mxList.size()>0){
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue(); //单价
							double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
							price = price + jgtz;  //实际售价等于 单价+价格调整
							
							double dwcbj = mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue(); //单位成本
							
							int nums = new Integer(StringUtils.nullToStr(mxMap.get("sjcj_nums"))).intValue(); //实际成交数量
							double cb = dwcbj * nums;  //成本
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
							}
							
							double xj = mxMap.get("sjcj_xj")==null?0:((Double)mxMap.get("sjcj_xj")).doubleValue();   //实际金额
							double ml = xj - cb;   //毛利
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;							
						}
						
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
					
				}
			}

	
			//零售单列表及明细部分			
			if(lsdList != null && lsdList.size()>0){
				for(int k=0;k<lsdList.size();k++){
					Map lsdMap = (Map)lsdList.get(k);
									
					String id = StringUtils.nullToStr(lsdMap.get("id"));
	
					List mxList = xstjXsryService.getLsdMxList(id);
					
			
					if(mxList != null && mxList.size()>0){
						
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();   //数量
							double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();  //销售收入
							double dwcb = mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue();  //单位成本
							double cb = dwcb * nums;  //成本
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
							}
							
							double ml = xj - cb;      //毛利
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;

						}
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
				}
			}


			//退货单列表及明细部分
			//退货单退货成本目前取的是退货时库存
			//退货不计算毛利和毛利率,但影响最终的毛利及毛利率
			if(thdList != null && thdList.size()>0){
				for(int k=0;k<thdList.size();k++){
					Map thdMap = (Map)thdList.get(k);
					
					String thd_id = StringUtils.nullToStr(thdMap.get("thd_id"));

					List mxList = xstjXsryService.getThdMxList(thd_id);
					
			
					if(mxList != null && mxList.size()>0){
						
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							int nums = 0 - new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();  //数量
							double xj = 0 - (mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue());	 //销售收入	
							double dwcb = 0 - (mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue());	 //单位成本
							double cb = 0 -( dwcb * nums);
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
							}
							
							double ml = xj - cb;
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;
						}
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
				}
			}
%>		
			<TR>
				<TD class=ReportItemXh><%=user_id %></TD>
				<TD class=ReportItem><a href="#" onclick="subForm('<%=user_id %>');"><%=real_name %></a></TD>
				<TD class=ReportItemMoney><%=xs_nums_zj %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(xssr_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(cb_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(ml_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.percent(ml_zj,xssr_zj) %>&nbsp;</TD>
			</TR>
<%
			hj_nums += xs_nums_zj;
			hj_xssr += xssr_zj;
			hj_cb += cb_zj;
			hj_ml += ml_zj;
		}
	}
}
%>

			<TR>
				<TD class=ReportItemXh><b>合计</b></TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItemMoney><%=hj_nums %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(hj_xssr,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(hj_cb,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.round(hj_ml,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney><%=JMath.percent(hj_ml,hj_xssr) %>&nbsp;</TD>
			</TR>

	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击业务员姓名可以查看明细。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>