<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.util.DateComFunc" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List cdateList = (List)VS.findValue("cdateList");

//默认取当前日期
Calendar calendar = Calendar.getInstance();
int nYear = calendar.get(calendar.YEAR);//取当前年份       
int nMonth = calendar.get(calendar.MONTH) + 1 ;//取当前月份

//如果参数年不为空
String strYear = request.getParameter("year");
if(strYear != null && !strYear.equals("")){
	nYear = Integer.parseInt(strYear);
}

//如果参数月不为空
String strMonth = request.getParameter("month");
if(strMonth != null && !strMonth.equals("")){
	nMonth = Integer.parseInt(strMonth);
}


calendar.set(nYear,nMonth - 1 ,1);
int nDaySum = calendar.getActualMaximum(calendar.DAY_OF_MONTH); //获得该月一共多少天
int nFirstDayIsWeek = calendar.get(calendar.DAY_OF_WEEK); //获得该月第一天是周几  

//月份和日格式标准化
String sMonth = nMonth + "";
if(sMonth.length() == 1){
	sMonth = "0" + sMonth;
}

%>

<html>
<head>
<title>日程安排</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style type="text/css">
.td_css1{background-color:#DDDDDD;}
.td_css2{background-color:#FFFFFF;}
.td_css3{background-color:#FFDDDD;}
</style>
<script type="text/javascript">

//年份和月份的翻页
function do_Refresh(){	
    //翻页只做读取操作，获取年月
    document.myform.action="showCalendar.html";
	document.myform.submit();		
}

//选择日期
function dateSel(vl){
	if (event.srcElement.tagName=='TD'){
		var t = document.getElementById("calendarTable").childNodes.item(0);
		for(var i=0;i<t.childNodes.length;i++){
			for(var j=0;j<t.childNodes(i).childNodes.length;j++){
				if(t.childNodes(i).childNodes(j).className != "td_css3")	t.childNodes(i).childNodes(j).className = "td_css2";
			}
		}

		if(event.srcElement.className != "td_css3")	event.srcElement.className='td_css1';

		document.descForm.cdate.value = vl;
		document.descForm.submit();	
	}
}

function addActive(cdate){
	var destination = "editPlan.html?cdate="+cdate;
	var fea = 'width=650,height=400,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'',fea);	
}

function descMx(){
	document.descForm.submit();		
}	

function loadMx(){
	document.descForm.cdate.value = "<%=DateComFunc.getToday() %>";
	document.descForm.submit();		
}
</script>
</head>
<body onload="loadMx();">
<form name="myform" action="showCalendar.html" method="post">
	<table cellspacing="0" cellpadding="0" border="0" width="99%" align="center" style="border-bottom: 1 solid #B8B8B8;border-left: 1 solid #B8B8B8;border-right: 1 solid #B8B8B8;border-top: 1 solid #B8B8B8">
		<tr height="35">
			<td align="center" style="background-color: #DDDDDD;border-bottom: 1 solid #B8B8B8;">
				<select name="year" onchange="do_Refresh();">
					<%
					for(int i=nYear-5;i<nYear+5;i++){
					%>
					<option value="<%=i %>" <%if(i==nYear) out.print("selected"); %>><%=i %></option>
					<%	
					}
					%>
				</select>&nbsp;年&nbsp;
				<select name="month" onchange="do_Refresh();">
					<%
					for(int i=1;i<=12;i++){
					%>
					<option value="<%=i %>" <%if(i==nMonth) out.print("selected"); %>><%=i %></option>
					<%	
					}
					%>
				</select>&nbsp;月
			</td>
		</tr>
		
		<tr> 
			<td style="border-bottom: 1 solid #B8B8B8;">
				<table width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr >
						<td width="14%" align="center" style="font-size: 20px"><b>日</b></td>                      
						<td width="14%" align="center" style="font-size: 20px"><b>一</b></td>
						<td width="14%" align="center" style="font-size: 20px"><b>二</b></td>
						<td width="14%" align="center" style="font-size: 20px"><b>三</b></td>
						<td width="14%" align="center" style="font-size: 20px"><b>四</b></td>
						<td width="14%" align="center" style="font-size: 20px"><b>五</b></td>
						<td width="14%" align="center" style="font-size: 20px"><b>六</b></td>
                    </tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<table width="70%" border="0" align="center" cellpadding="0" cellspacing="5" id="calendarTable">
				<%
				int day  = 1 ;
				int i = 1 ;
				int n = 7 ;	
				 
				while ( i <= 42 ){					    
				    if ( i >= nDaySum + nFirstDayIsWeek ){
					   break ;
					} 
				%>                    
					<tr height="40">
                      
				<%
					n = i + 7 ; 
					for( ; i < n ; i ++){
						if ( i <  nFirstDayIsWeek || i >= nDaySum + nFirstDayIsWeek ){
				%>                      
						<td width="14%" >&nbsp;</td>
				<%   		
						}else{//显示日期
							String strMonth_ = nMonth + "";										 
							 if(strMonth_.length()==1){
									strMonth_ = "0" + strMonth_;
							 }
							 
							 String strDay_ =  day + "";
							 if(strDay_.length() == 1){
									strDay_ = "0" + strDay_;
							 }	
							 							 					 
							String curDate = nYear+"-"+strMonth_+"-"+strDay_+"";
							
							if(cdateList.contains(curDate)){
								if(curDate.equals(DateComFunc.getToday())){
				%>
									<td width="14%" align="center" style="cursor: hand;font-size: 28px;color:red;" class="td_css3" onclick="dateSel('<%=curDate %>');" ondblclick="addActive('<%=curDate %>');"><%=day%></td>
				<%					
								}else{
				%>
									<td width="14%" align="center" style="cursor: hand;font-size: 28px;color:red;" onclick="dateSel('<%=curDate %>');" ondblclick="addActive('<%=curDate %>');"><%=day%></td>
				<%					
								}
							}else{
								if(curDate.equals(DateComFunc.getToday())){
				%>
									<td width="14%" align="center" style="cursor: hand;font-size: 28px;" class="td_css3" onclick="dateSel('<%=curDate %>');" ondblclick="addActive('<%=curDate %>');"><%=day%></td>
				<%										
								}else{
				%>
									<td width="14%" align="center" style="cursor: hand;font-size: 28px;" onclick="dateSel('<%=curDate %>');" ondblclick="addActive('<%=curDate %>');"><%=day%></td>
				<%									
								}							
							}
							day ++ ;								
						}
					}
				%>	
					</tr>
				<%	
				} 
				%>	
				</table>
			</td>
		</tr>
	</table>		
	<BR><font color="red">&nbsp;注：红色字体标志当天有活动安排，单击鼠标左键查看活动，双击鼠标左键添加活动</font>
</form>
<form name="descForm" action="listPlan.html" method="post" target="desc">
	<input type="hidden" name="cdate" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>