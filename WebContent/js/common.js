/**
 * 打印DIV中的内容
 * @param reportDiv
 * @return
 */
function printDiv(reportDiv){
	var newstr = document.getElementById(reportDiv).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = newstr;
	window.print(); 
	document.body.innerHTML = oldstr;
	return false;
}