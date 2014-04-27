/**
 * 报表打印
 * @param taskName  打印任务名称
 * @param intOrient    打印方向控制  1：纵向；2：横向
 * @param pageName  纸张类型 例如“A4”
 * @param strHtmlObj 打印容器dom对象
 */
function printReport(taskName,intOrient,pageName,strHtmlObj) {
		var LODOP=getLodop();  
		LODOP.PRINT_INIT(taskName);
		LODOP. SET_PRINT_PAGESIZE (intOrient, 0, 0,pageName);
		var strStyle='<link href="css/report.css" rel="stylesheet" type="text/css" />';  //系统报表样式
		LODOP.ADD_PRINT_HTM("20mm","20mm","RightMargin:20mm","BottomMargin:20mm",strStyle + document.getElementById(strHtmlObj).innerHTML);
		LODOP.PRINT();
}


/**
 * 打印预览
 * @param intOrient 打印方向控制  1：纵向；2：横向
 * @param pageName 纸张类型 例如“A4”
 * @param strHtmlObj 打印容器dom对象
 */
function printPre(intOrient,pageName,strHtmlObj) {
	var LODOP=getLodop();  
	LODOP. SET_PRINT_PAGESIZE (intOrient, 0, 0,pageName);
	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true);
	var strStyle='<link href="css/report.css" rel="stylesheet" type="text/css" />';
	LODOP.ADD_PRINT_HTM("20mm","20mm","RightMargin:20mm","BottomMargin:20mm",strStyle + document.getElementById(strHtmlObj).innerHTML);		
	LODOP.PREVIEW();
}