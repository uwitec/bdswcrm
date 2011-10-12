<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="fusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="fusionCharts/charts.js"></script>
<script type="text/javascript">
	function initChart(){
		createPie3DChart("商品分类统计","<set label='标签一' value='5.6'/><set label='标签二' value='5.6'/><set label='标签三' value='15.6'/><set label='标签四' value='9.6'/><set label='标签五' value='3'/>","chartdiv_bing");
		createColumn3DChart("商品统计","","","<set label='标签一' value='5.6'/><set label='标签二' value='5.6'/><set label='标签三' value='15.6'/><set label='标签四' value='9.6'/><set label='标签五' value='3'/>","chartdiv_zhu");
		createMSlineChart("毛利统计","年度","数值","<categories><category label='2001'/><category label='2002'/><category label='2003'/><category label='2004'/></categories><dataset seriesName='aaa'><set value='15' /><set value='18' /><set value='16' /><set value='15' /></dataset><dataset seriesName='bbbb'><set value='25' /><set value='12' /><set value='11' /><set value='25' /></dataset>","chartdiv_zhe");
	}
</script>
</head>
<body onload="initChart();">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" align="center">
			<div id="chartdiv_bing" align="center" style="padding-top:5px;width:48%;height:240px;display:inline;float:left;border: 1px solid"></div>
			<div id="chartdiv_zhu" align="center" style="padding-top:5px;width:48%;height:240px;display:inline;float:left;border: 1px solid"></div>
			<div id="chartdiv_zhe" align="center" style="padding-top:5px;width:48%;height:240px;display:inline;float:left;border: 1px solid"></div>
		</td>
</tr>
</table>
</body>
</html>