<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.service.MenuService" %>
<%
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String real_name = info.getReal_name();
String user_id = info.getUser_id();

OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String logo_url = (String)VS.findValue("logo_url");

MenuService menuService = (MenuService)VS.findValue("menuService");
List funcList = menuService.getUserYwgnFunc(user_id);

String flag = request.getParameter("flag");
if(flag == null){
	flag = "2";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>top</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="js/dropdowntabs.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>

<script>

	//标题框提示
	var dynamicMsg = null;
	function init() {
		dynamicMsg = new DynamicMessage(parent.window.document.title, "【ＨＩ，消息】", "【　　　　　】");
	}
	
	function begin() {
		dynamicMsg.initIntervalMsg();
	}
	
	function end() {   
		dynamicMsg.clearIntervalMsg();
	}
  
	function DynamicMessage(defaultMsg, msg, hiddenMsg) {
		this.intervalMsg = null;
		this.bMsg = false;
		var _this=this;
		this.initIntervalMsg = function() {
		this.intervalMsg = setInterval(function() {
				if(!_this.bMsg) {
					parent.window.document.title = msg + " - " + defaultMsg;
					_this.bMsg = true;
				} else {
					parent.window.document.title = hiddenMsg + " - " + defaultMsg;     
					_this.bMsg = false;
				}            
			}, 1000);
		};
		    
		this.clearIntervalMsg = function() {
		       if(this.intervalMsg != null) {     
				clearInterval(this.intervalMsg);     
				parent.window.document.title = defaultMsg;     
				_this.bMsg = false;     
			}
		}; 
	}	
	
	
	//退出系统
	function  logout(){
		if(confirm("确认退出管理系统?")){
			window.top.location='login.jsp';
		}
	}
    
    //查询库存
	function openWin(flag){
		if(flag == "1"){
			var destination = "top.htm";
			var fea ='width=' + (screen.availWidth-100) + ',height='+ (screen.availHeight-150) +',left=50,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
			
			var newWin =window.open(destination,'库存查询',fea);
			newWin.focus();

		}else{
			document.myform.action = "queryClients.html";
			document.myform.submit();
		}		
	}
	
	//触发点击回车事件
	function f_enter(flag){
	    if (window.event.keyCode==13){
	        openWin(flag);
	        event.returnValue = false;
	    }
	}
	
	var is = false;
	
	//检查用户是否有新消息
	function getHasMsg(){
		freshUserMsg();
		setTimeout("getHasMsg()",20*1000);
	}
	
	function freshUserMsg(){
		$.ajax({
			cache: false,
			url:"user_msg.jsp",
			dataType:"string",
			type: "POST",
			success: function(vl) {
				chkReturn(vl.trim());
			}
		});
	}
	
	//对返回结果进行处理
	function chkReturn(flag){
		if(flag > 0){
			if(!is){
				begin();
				is = true;
			}
			document.getElementById("wdMsgTxt").innerText = "未读消息[" + flag + "]";
			document.getElementById("msgImage").src = "images/msg_new.gif";
		}else{
			end();
			is = false;
			document.getElementById("msgImage").src = "images/msg_1.gif";
			document.getElementById("wdMsgTxt").innerText = "未读消息";
		}
	}
	
	//打开未读消息窗口
	function showWdMsg(){
		end();
		is = false;
		document.getElementById("msgImage").src = "images/msg_1.gif";
		document.getElementById("wdMsgTxt").innerText = "未读消息";	
		
		var destination = "listNotReadMsg.html";
		var fea ='width=600,height=500,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'未读消息',fea);			
	}
	
	//打开已读消息窗口
	function shoYdMsg(){
		parent.addtabFmMenu('已读消息','listReadedMsg.html','readeMsg');		
	}		
	
	//隐藏菜单
	function hiddenDiv(){
		document.getElementById("dropmenu1_a").style.visibility = "hidden";
	}
	
	//显示菜单
	function showDiv(){
		document.getElementById("dropmenu1_a").style.visibility = "visible";
	}
	
	//发送消息
	function send(){
		var destination = "sendMsg.html";
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'发送消息',fea);	
	}
	
	//打开未读消息窗口
	function sendMail(){
		var destination = "addMail.html";
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'发送邮件',fea);			
	}	

	function switchUi(flag){
		if(flag == "2"){
			parent.location='thk_main.html';
		}else{
			parent.location='thk_main_new.html';
		}
	}		
	function isIE(){ //ie?
		  if (window.navigator.userAgent.toLowerCase().indexOf("msie") >= 1)
		  	return true;
		  else
		  	return false;
	}

	if(!isIE()){ //firefox innerText define
		  HTMLElement.prototype.__defineGetter__( "innerText",
			  function(){
				  var anyString = "";
				  var childS = this.childNodes;
				  for(var i=0; i <childS.length; i++) {
					  if(childS[i].nodeType==1)
					  	anyString += childS[i].tagName=="BR" ? '\n' : childS[i].innerText;
					  else if(childS[i].nodeType==3)
					  	anyString += childS[i].nodeValue;
				  }
				  return anyString;
			  }
		  );
		  HTMLElement.prototype.__defineSetter__( "innerText",
			  function(sText){
			  	this.textContent=sText;
			  }
		  );
	}		
</script>
</head>
<body onload="init();getHasMsg();" bgColor=#FBFBFB>
<form name="myform" action="queryKcMx.html" method="post" target="_blank">
<div id="dropmenu1_a" class="menu_down">
<div class="menu_down_span" ><a href="javascript:showWdMsg();"><span id="wdMsgTxt">未读消息</span></a></div>
<div class="menu_down_span" ><a href="javascript:shoYdMsg();"><span>已读消息</span></a></div>
<div class="menu_down_span" ><a href="javascript:send();"><span>发送消息</span></a></div>
</div>
<TABLE cellSpacing=0 cellPadding=0 width=100% height=100% align=center border=0 bgColor=#FBFBFB>
	<TBODY>
	<tr>
		<td width="160">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           				<TR>
					<TD>&nbsp;&nbsp;<IMG src="logo/<%=logo_url %>" width="140" height="49"></TD>
				</TR>						
				</TBODY>
			</TABLE>					
		</td>
		<td height=100%  vAlign=middle  align=center>
			<%
			if(funcList.contains("FC9999")){
			%>
			<font style="font-size: 12px;">库存查询：</font><input type="text" name="kc_con"  onkeypress="f_enter('1');"  size="14" title="可用空格或逗号分隔关键词">
			<input type="button" name="button1" value="查询" class="css_button" onclick="openWin('1');">
			&nbsp;&nbsp;&nbsp;<BR>
			<%
			}
			if(funcList.contains("FC9998")){
			%>			
			<font style="font-size: 12px;">客户查询：</font><input type="text" name="client_con"  size="14"  onkeypress="f_enter('2');"  size="12">
			<input type="button" name="button1" value="查询" class="css_button" onclick="openWin('2');">	&nbsp;&nbsp;&nbsp;
			<%
			}
			%>		
		</td>
		<td vAlign=top align=right  width="500">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           		<TR>
					<TD>&nbsp;</TD>
					<TD align=middle width=10><IMG src="index_images/head_right.gif"></TD>
					<%if(flag.equals("1")){ %>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/index_home.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="parent.location='thk_main.html';">首 页</A></TD>
					<%}else{ %>		
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/index_home.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="parent.location='thk_main_new.html';">首 页</A></TD>
					<%} %>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD noWrap align=middle vlign=middle width=85 bgColor=#bcc2d4><div id="colortab" class="ddcolortabs">
						<IMG id="msgImage" src="images/msg_1.gif" align=absMiddle>
						<A class=TitleMenu  href="#" rel="dropmenu1_a">系统消息</A></div>
					</TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/stock.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="sendMail();">发送邮件</A></TD>			
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>					
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/WORDPAD.gif" width=15 align=absMiddle> <A class=TitleMenu href="http://221.194.37.139:8080/jforum" target="_bland">服务论坛</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/out_system.gif" width=15 align=absMiddle> <A class=TitleMenu href="#" onclick="logout();">安全退出</A></TD>
				</TR>
				<TR height="20"><TD colspan="11" align="right">&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-size: 12px;">欢迎<%=real_name %>光临系统&nbsp;</font>&nbsp;&nbsp;&nbsp;&nbsp;<!-- <a href="javascript:switchUi('<%=flag %>');">切换界面</a>&nbsp;&nbsp;&nbsp;&nbsp; --></TD></TR>
				</TBODY>
			</TABLE>
		</td>
	</tr>
	</TBODY>
</TABLE>
</form>
<script type="text/javascript">
	tabdropdown.init("colortab", 3)
</script>
</body>
</html>
