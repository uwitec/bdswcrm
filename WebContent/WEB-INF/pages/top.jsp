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
%>
<html>
<head>
<title>top</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="js/dropdowntabs.js"></script>
<script type='text/javascript' src='dwr/interface/msgService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

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
			var fea ='width=900,height=600,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
			
			var newWin =window.open(destination,'库存查询',fea);
			newWin.focus();
		}else{
			//var destination = "client.htm";
			//var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
			
			//window.open(destination,'客户查询',fea);	
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
	
	//调用DWR服务检查用户是否有新消息
	function getHasMsg(){
		msgService.isHasMsgByUser('<%=user_id %>',chkReturn);
		setTimeout("getHasMsg()",120*1000);
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
			MM_controlSound('play','document.MM_controlSound1');
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
		parent.addtabFmMenu('已读消息','listReadedMsg.html','');		
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
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'发送消息',fea);	
	}
	
	function MM_controlSound(sndAction,sndObj){ 　
		if(eval(sndObj) != null){ 　　
			if(navigator.appName=='Netscape'){
				eval(sndObj+((sndAction=='stop')?'.stop()':'.play(false)')); 　　
			}else if(eval(sndObj+".FileName")){
				eval(sndObj+((sndAction=='stop')?'.stop()':'.play()')); 　
			}
		}
	}	
	
	//打开未读消息窗口
	function sendMail(){
		var destination = "addMail.html";
		var fea ='width=850,height=650,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'发送邮件',fea);			
	}			
</script>
</head>
<body onload="init();getHasMsg();">
<form name="myform" action="queryKcMx.html" method="post" target="_blank">
<div id="dropmenu1_a" class="menu_down">
<a href="javascript:showWdMsg();"><span id="wdMsgTxt">未读消息</span></a>
<a href="javascript:shoYdMsg();"><span>已读消息</span></a>
<a href="javascript:send();"><span>发送消息</span></a>
</div>
<TABLE cellSpacing=0 cellPadding=0 width=100% height=100% align=center border=0 bgColor=#FBFBFB>
	<TBODY>
	<tr>
		<td width="200">
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
			<font style="font-size: 12px;">库存查询：</font><input type="text" name="kc_con"  onkeypress="f_enter('1');"  size="12">
			<input type="button" name="button1" value=" 查询 " class="css_button" onclick="openWin('1');">
			&nbsp;&nbsp;&nbsp;
			<%
			}
			if(funcList.contains("FC9998")){
			%>			
			<font style="font-size: 12px;">客户查询：</font><input type="text" name="client_con"  onkeypress="f_enter('2');"  size="12">
			<input type="button" name="button1" value=" 查询 " class="css_button" onclick="openWin('2');">	
			<%
			}
			%>		
		</td>
		<td vAlign=top align=right >
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           		<TR>
					<TD>&nbsp;</TD>
					<TD align=middle width=10><IMG src="index_images/head_right.gif"></TD>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/index_home.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="parent.location='thk_main.html';">首 页</A></TD>		
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD noWrap align=middle vlign=middle width=85 bgColor=#bcc2d4><div id="colortab" class="ddcolortabs">
						<IMG id="msgImage" src="images/msg_1.gif" align=absMiddle>
						<A class=TitleMenu  href="#" rel="dropmenu1_a">系统消息</A></div>
					</TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/stock.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="sendMail();">发送邮件</A></TD>			
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>					
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/WORDPAD.gif" width=15 align=absMiddle> <A class=TitleMenu href="http://hi.baidu.com/bdthinking" target="_bland">系统帮助</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/out_system.gif" width=15 align=absMiddle> <A class=TitleMenu href="#" onclick="logout();">安全退出</A></TD>
				</TR>
				</TBODY>
			</TABLE>
			<font style="font-size: 12px;">欢迎<%=real_name %>光临系统&nbsp;</font>
		</td>
	</tr>
	</TBODY>
</TABLE>
</form>
<embed name='MM_controlSound1' src='css/1.wav' loop=false autostart=false mastersound hidden=true width=0 height=0></embed>
<script type="text/javascript">
	tabdropdown.init("colortab", 3)
</script>
</body>
</html>
