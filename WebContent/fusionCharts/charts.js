/**
 * 创建饼图
 * @param title 标题
 * @param dataXml 数据串（格式如：<set label='标签一' value='5.6'/><set label='标签二' value='5.6'/>）
 * @param divContainer 图形显示容器DIV的ID
 * @return
 */
function createPie3DChart(title,dataXml,divContainer){
	var myChart_bing_1 = new FusionCharts("./fusionCharts/Pie3D.swf?PBarLoadingText=Loading&XMLLoadingText=Loading&ParsingDataText=Loading", "myChartId_bing_", "100%", "100%", "0", "0");
	myChart_bing_1.addParam('wmode','transparent');
	var chartDataXml = "<chart baseFontSize='12' showAboutMenuItem='0' decimals='2' chartTopMargin='0' chartBottomMargin='0' caption='" +title + "' showValues='0' formatNumberScale='2' bgAlpha='0' startingAngle='10'>";
	chartDataXml = chartDataXml + dataXml;
	chartDataXml = chartDataXml + "</chart>";
	myChart_bing_1.setDataXML(chartDataXml);
	myChart_bing_1.render(divContainer);
}

/**
 * 创建柱状图
 * @param title 标题
 * @param xName X轴名称，空不显示
 * @param yName y轴名称，空不显示 
 * @param dataXml 数据串（格式如：<set label='标签一' value='5.6'/><set label='标签二' value='5.6'/>）
 * @param divContainer 图形显示容器DIV的ID
 * @return
 */
function createColumn3DChart(title,xName,yName,dataXml,divContainer){
	var myChart_zhu_1 = new FusionCharts("./fusionCharts/Column3D.swf?PBarLoadingText=Loading&XMLLoadingText=Loading&ParsingDataText=Loading", "myChartId_zhu_1", "100%", "100%", "0", "0");
	myChart_zhu_1.addParam('wmode','transparent');
	var chartDataXml = "<chart caption='" +title + "' xAxisName='" + xName + "' yAxisName='" + yName + "'  baseFontSize='12' outCnvBaseFontSize='12' showAboutMenuItem='0' decimals='2' palette='2' enableRotation='1' canvasBgColor='ffffff' canvasBgAlpha='40' canvasBgDepth='3' canvasBaseColor='ffffff' divLineColor='d8d8d8' numDivLines='4' bgAlpha='0'  divLineColor='d8d8d8' canvasPadding='8' chartLeftMargin='15' chartRightMargin='15' chartTopMargin='5' chartBottomMargin='5' maxColWidth='70' showValues='0'>";
	chartDataXml = chartDataXml + dataXml;
	chartDataXml = chartDataXml + "</chart>";
	myChart_zhu_1.setDataXML(chartDataXml);
	myChart_zhu_1.render(divContainer);
}

/**
 * 创建折线图
 * @param title 标题
 * @param xName X轴名称，空不显示
 * @param yName y轴名称，空不显示 
 * @param dataXml 数据串 格式如下
 * <categories>
 * 	<category label='2001'/>
 * 	<category label='2002'/>
 * 	<category label='2003'/>
 * 	<category label='2004'/>
 * </categories>
 * <dataset seriesName='aaa'>
 * 	<set value='15' />
 * 	<set value='18' />
 * 	<set value='16' />
 * 	<set value='15' />
 * </dataset>
 * <dataset seriesName='bbbb'>
 * 	<set value='25' />
 * 	<set value='12' />
 * 	<set value='11' />
 * 	<set value='25' />
 * </dataset>
 * @param divContainer 图形显示容器DIV的ID
 * @return
 */
function createMSlineChart(title,xName,yName,dataXml,divContainer){
	var myChart_line_1 = new FusionCharts("./fusionCharts/MSLine.swf?PBarLoadingText=Loading&XMLLoadingText=Loading&ParsingDataText=Loading", "myChartId_line_1", "100%", "100%", "0", "0");
	myChart_line_1.addParam('wmode','transparent');
	var chartDataXml = "<chart xAxisName='" + xName + "' yAxisName='" + yName + "' caption='" + title + "' showValues='1' baseFontSize='13' lineThickness='2' formatNumber = '0' formatNumberScale='0' rotateYAxisName='1' connectNullData='1' lineDashGap='7' showPrintMenuItem='0' showAboutMenuItem='0' showBorder='0' chartTopMargin='5' chartBottomMargin='5' chartLeftMargin='25' chartRightMargin='25'>";
	chartDataXml = chartDataXml + dataXml;
	chartDataXml = chartDataXml + "</chart>";
	myChart_line_1.setDataXML(chartDataXml);
	myChart_line_1.render(divContainer);	
}