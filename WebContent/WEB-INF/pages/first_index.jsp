<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List nbggList = (List)VS.findValue("nbggList");

XxfbNbggService xxfbNbggService = (XxfbNbggService)VS.findValue("xxfbNbggService");

List bwlList = (List)VS.findValue("bwlList");

ClientWlStatService clientWlStatService = (ClientWlStatService)VS.findValue("clientWlStatService");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript" src="fusionCharts/FusionCharts.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style type="text/css">
.inner{
	width:99%;
	margin:2px; 
	border:1px solid #B8B8B8;
	text-align:left;
	display:inline ;
	float:left;	
	height: 240px;
	overflow-y: auto;
	overflow-x: none; 
}
.innerImage{
	width:99%;
	margin:2px; 
	border:1px solid #B8B8B8;
	text-align:left;
	display:inline ;
	float:left;	
	height: 290px;
	overflow-y: none;
	overflow-x: none; 
}
</style>
<script type="text/javascript">	
	function openNbggWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}

   function openBwlWin(id){
		var destination = "viewBwl.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}

	function refreshPage(){
		document.myform.action = "listMain.html";
		document.myform.submit();
	}

	function queryUndoWorkNums(){
		//个人工作台数据
		$("#dckSpan").html('<img src="images/indicator.gif"></img>');
		$("#drkSpan").html('<img src="images/indicator.gif"></img>');
		$("#dspLsdSpan").html('<img src="images/indicator.gif"></img>');
		$("#dspXsdSpan").html('<img src="images/indicator.gif"></img>');
		$("#dspFksqSpan").html('<img src="images/indicator.gif"></img>');
		$("#dspFysqSpan").html('<img src="images/indicator.gif"></img>');
		$("#dqrKfdbSpan").html('<img src="images/indicator.gif"></img>');
		$.ajax({
			cache: false,
			url:"getUndoWorkNums.html",
			dataType:"json",
			type: "POST",
			success: function(json) {
				$("#dckSpan").html(json.dckListNums);
				$("#drkSpan").html(json.drkListNums);
				$("#dspLsdSpan").html(json.dspLsdNums);
				$("#dspXsdSpan").html(json.dspXsdNums);
				$("#dspFksqSpan").html(json.dspCgfkNums);
				$("#dspFysqSpan").html(json.dspFysqNums);
				$("#dqrKfdbSpan").html(json.confirmKfdbNums);
			}
		});
	}

	function queryHpflhzChart(start_date,end_date){
		$("#chartdiv_bing").html('<BR><BR><BR><BR><BR><img src="images/indicator.gif"></img>');
		//货品销售分类汇总图
		$.ajax({
			cache: false,
			url:"getResultsForChart.html",
			type: "POST",
			data:{c_start_date:start_date,c_end_date:end_date},
			success: function(result) {
				var myChart_bing_1 = new FusionCharts("fusionCharts/Pie3D.swf?PBarLoadingText=Loading&XMLLoadingText=Loading&ParsingDataText=Loading", "myChartId_bing_", ($(window).width()-60)/2, "240", "0", "0");
				myChart_bing_1.addParam('wmode','transparent');
				var chartDataXml = "<chart baseFontSize='12' showAboutMenuItem='0' decimals='2' chartTopMargin='0' chartBottomMargin='0' chartLeftMargin='0' chartRightMargin='0' caption='' showValues='0' formatNumberScale='2' bgAlpha='0' startingAngle='0' showZeroPies='0'>";
				chartDataXml = chartDataXml + result;
				chartDataXml = chartDataXml + "</chart>";
				myChart_bing_1.setDataXML(chartDataXml);
				myChart_bing_1.render("chartdiv_bing");
			}
		});	
	}

	function queryHpmlflhzChart(start_date,end_date){
		$("#chartdiv_hpmlflhz").html('<BR><BR><BR><BR><BR><img src="images/indicator.gif"></img>');
		//货品毛利分类汇总图
		$.ajax({
			cache: false,
			url:"queryHpmlflhz.html",
			type: "POST",
			data:{start_date:start_date,end_date:end_date},
			success: function(result) {
				var myChart_bing_1 = new FusionCharts("fusionCharts/Pie3D.swf?PBarLoadingText=Loading&XMLLoadingText=Loading&ParsingDataText=Loading", "myChartId_bing_", ($(window).width()-60)/2, "240", "0", "0");
				myChart_bing_1.addParam('wmode','transparent');
				var chartDataXml = "<chart baseFontSize='12' showAboutMenuItem='0' decimals='2' chartTopMargin='0' chartBottomMargin='0' chartLeftMargin='0' chartRightMargin='0' caption='' showValues='0' formatNumberScale='2' bgAlpha='0' startingAngle='0' showZeroPies='0'>";
				chartDataXml = chartDataXml + result;
				chartDataXml = chartDataXml + "</chart>";
				myChart_bing_1.setDataXML(chartDataXml);
				myChart_bing_1.render("chartdiv_hpmlflhz");
			}
		});	
	}	

	function queryClientsYshz(){
		//客户应收汇总
		$("#clientYshzDiv").html('<BR><BR><BR><BR><BR><img src="images/indicator.gif"></img>');
		$.ajax({
			cache: false,
			url:"queryClientsYshz.html",
			type: "POST",
			success: function(result) {
				$("#clientYshzDiv").html(result);
			}
		});
	}

	function queryHpflhzCharByCon(){
		var start_date =  document.getElementById("creatdate").value;
		var end_date =  document.getElementById("creatdate2").value;
		if(start_date == "" || end_date == ""){
			alert("时间不能为空！");
			return;
		}else{
			queryHpflhzChart(start_date,end_date);
		}
	}

	function queryHpmlflhzCharByCon(){
		var start_date =  document.getElementById("hpml_start_date").value;
		var end_date =  document.getElementById("hpml_end_date").value;
		if(start_date == "" || end_date == ""){
			alert("时间不能为空！");
			return;
		}else{
			queryHpmlflhzChart(start_date,end_date);
		}
	}
	
	function initPage(){
		var end_date = "<%=DateComFunc.getToday()%>";
		var start_date = "<%=DateComFunc.getMonthFirstDay(DateComFunc.getToday())%>";

		queryHpflhzChart(start_date,end_date);
		queryHpmlflhzChart(start_date,end_date);
		queryUndoWorkNums();
		queryClientsYshz();
	}
</script>
</head>
<body onload="initPage();">
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listMain.html" method="post">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="50%" align="center">
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>个人工作台</b></td>
					</tr>
					<tr><td height="3"></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待出库单：&nbsp;&nbsp; <span id="dckSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待出库单','queryDckList.html','dckList');" class="xxlb">点击查看</a></td></tr>
					<tr><td height="23">&nbsp;&nbsp;待入库单： &nbsp;&nbsp;<span id="drkSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待入库单','queryDrkList.html','drkList');" class="xxlb">点击查看</a></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待审批零售单：&nbsp;&nbsp; <span id="dspLsdSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待审批零售单','queryDspLsdList.html','dspLsdList');" class="xxlb">点击查看</a></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待审批销售单： &nbsp;&nbsp;<span id="dspXsdSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待审批销售单','queryDspXsdList.html','dspXsdList');" class="xxlb">点击查看</a></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待审批付款申请：&nbsp;&nbsp; <span id="dspFksqSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待审批付款申请','queryDspFksqList.html','dspFksqList');" class="xxlb">点击查看</a></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待审批费用申请：&nbsp;&nbsp; <span id="dspFysqSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待审批费用申请','queryDspFysqList.html','dspFysqList');" class="xxlb">点击查看</a></td></tr>		
					<tr><td height="23">&nbsp;&nbsp;待确认库房调拨：&nbsp;&nbsp; <span id="dqrKfdbSpan"></span> 件&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.addtabFmMenu('待确认库房调拨','queryDqrDfdbList.html','dqrKfdbList');" class="xxlb">点击查看</a></td></tr>		
				</table>
			</div>	
		</td>
		<td align="center">	
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" colspan="3" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
						<td height="27" align="center" style="background-image:url(images/head_top2bg.gif)"><img src="images/import.gif" align="absmiddle" border="0"> <a href="#" class="xxlb" onclick="refreshPage();">刷新首页</a></td>
					</tr>
					<tr><td height="3" colspan="4"></td></tr>
					<%
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Iterator itNbgg = nbggList.iterator();
					while(itNbgg.hasNext()){
						XxfbNbgg info = (XxfbNbgg)itNbgg.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>20){
							subTitle = title.substring(0,20) + "...";
						}
						String pub_date = StringUtils.nullToStr(info.getPub_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
						String cz_date=StringUtils.nullToStr(sdf.format(info.getCz_date()));						
						String finalhfr=StaticParamDo.getRealNameById(xxfbNbggService.getFinalhfr(id));
					%>				
					<tr>
						<td width="55% height="23">&nbsp;<A class=xxlb href="#" onclick="openNbggWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="15%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="15%" nowrap="nowrap" height="23"><%=finalhfr %></td>
						<td width="15%" nowrap="nowrap" height="23">【<%=pub_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="4"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('公告列表','listGdNbgg.html','gbnbgg');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
			</div>
		</td>	
	</tr>
	<tr>
		<td  align="center">		
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" colspan="3" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>备忘录</b></td>
					</tr>
					<tr><td height="3" colspan="3"></td></tr>
					<%
					Iterator itBwl = bwlList.iterator();
					while(itBwl.hasNext()){
						Bwl info = (Bwl)itBwl.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>18){
							subTitle = title.substring(0,18) + "...";
						}
						String cz_date = StringUtils.nullToStr(info.getCz_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
					%>				
					<tr>
						<td width="60% height="23">&nbsp;<A class=xxlb href="#" onclick="openBwlWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="15%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="15%" nowrap="nowrap" height="23">【<%=cz_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="3"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('备忘录列表','listSyBwl.html','sybwl');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
			</div>
		</td>
		<td align="center">	
			<div class="inner" align="center" id="clientYshzDiv" style="vertical-align: middle;text-align: center;"></div>	
		</td>		
	</tr>
	<tr>
		<td  align="center">		
			<div class="innerImage">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>货品销售分类汇总</b></td>								
					</tr>
                    <TR>
                    	<TD height="23">&nbsp;<input type="text" name="creatdate" id="creatdate" value="<%=DateComFunc.getMonthFirstDay(DateComFunc.getToday()) %>" size="12" class="Wdate" onFocus="WdatePicker()"/>&nbsp;至&nbsp;
						<input type="text" name="creatdate2" id="creatdate2" value="<%=DateComFunc.getToday() %>"  class="Wdate" size="12" onFocus="WdatePicker()"/>&nbsp;&nbsp;
						<input type="button" name="buttonCx" value="汇总" onclick="queryHpflhzCharByCon();" class="css_button">
                    	</TD>
                    </TR>
					<tr><td height="240"><div id="chartdiv_bing" align="center" style="width:100%;height:240px;display:inline;float:left;overflow-x:none;overflow-y:none;border:0px;vertical-align: middle;text-align: center;"></div></td></tr>
				</table>			
			</div>		
		</td>
		<td  align="center">		
			<div class="innerImage">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>货品毛利分类汇总</b></td>								
					</tr>
                    <TR>
                    	<TD height="23">&nbsp;<input type="text" name="hpml_start_date" id="hpml_start_date" value="<%=DateComFunc.getMonthFirstDay(DateComFunc.getToday()) %>" size="12" class="Wdate" onFocus="WdatePicker()"/>&nbsp;至&nbsp;
						<input type="text" name="hpml_end_date" id="hpml_end_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" size="12" onFocus="WdatePicker()"/>&nbsp;&nbsp;
						<input type="button" name="buttonCx" value="汇总" onclick="queryHpmlflhzCharByCon();" class="css_button">
                    	</TD>
                    </TR>
					<tr><td height="240"><div id="chartdiv_hpmlflhz" align="center" style="width:100%;height:240px;display:inline;float:left;overflow-x:none;overflow-y:none;border:0px;vertical-align: middle;text-align: center;"></div></td></tr>
				</table>			
			</div>		
		</td>		
	</tr>
</table>
<BR>
</form>
</div>
</body>
</html>