/**
 * �����ӡ
 * @param taskName  ��ӡ��������
 * @param intOrient    ��ӡ�������  1������2������
 * @param pageName  ֽ������ ���硰A4��
 * @param strHtmlObj ��ӡ����dom����
 */
function printReport(taskName,intOrient,pageName,strHtmlObj) {
		var LODOP=getLodop();  
		LODOP.PRINT_INIT(taskName);
		LODOP. SET_PRINT_PAGESIZE (intOrient, 0, 0,pageName);
		var strStyle='<link href="css/report.css" rel="stylesheet" type="text/css" />';  //ϵͳ������ʽ
		LODOP.ADD_PRINT_HTM("20mm","20mm","RightMargin:20mm","BottomMargin:20mm",strStyle + document.getElementById(strHtmlObj).innerHTML);
		LODOP.PRINT();
}


/**
 * ��ӡԤ��
 * @param intOrient ��ӡ�������  1������2������
 * @param pageName ֽ������ ���硰A4��
 * @param strHtmlObj ��ӡ����dom����
 */
function printPre(intOrient,pageName,strHtmlObj) {
	var LODOP=getLodop();  
	LODOP. SET_PRINT_PAGESIZE (intOrient, 0, 0,pageName);
	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true);
	var strStyle='<link href="css/report.css" rel="stylesheet" type="text/css" />';
	LODOP.ADD_PRINT_HTM("20mm","20mm","RightMargin:20mm","BottomMargin:20mm",strStyle + document.getElementById(strHtmlObj).innerHTML);		
	LODOP.PREVIEW();
}