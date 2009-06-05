<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 系统消息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/Desktop.css" type=text/css rel=stylesheet>
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script language='JavaScript' src="js/lightBox.js"></script>
<script type='text/javascript' src='dwr/interface/msgService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	var box = null;
	
	function refreshPage(){
		document.myform.action = "listReadedMsg.html";
		document.myform.submit();
	}
	
	function delMsg(id){
		if(window.confirm("确认要删除该消息吗？")){
			document.myform.action = "delReadedMsg.html?msg_id=" + id;
			document.myform.submit();		
		}
	}
	
	//关闭回复消息框
	function hiddenDiv(){
		box.Close();
	}
	
	//打开回复消息框
	function showDiv(vl){
		document.getElementById("reciever_id").value = vl;
		document.getElementById("msg_body").value = "";
		box.Show();
	}
	
	//保存回复消息
	function subMsg(){
		var strMsg = dwr.util.getValue("msg_body");
		if(strMsg.length > 250){
			alert("消息内容超出长度！");
			return;
		}
		
		var strReciever = dwr.util.getValue("reciever_id");
		var sender = '<ww:property value="#session['LOGINUSER'].user_id"/>';
		
		msgService.saveFwMsg(sender,strReciever,strMsg,chkReturn);			
	}
	
	//对返回结果进行处理
	function chkReturn(flag){
		if(flag > 0){
			alert("消息发送成功！");
			hiddenDiv();
		}else{
			alert("消息发送失败！");
		}
	}
	
		//初始层
	function init(){
		box = new LightBox("back_div");
	}				
</script>
</head>
<BODY id=Body bottomMargin=0 leftMargin=0 topMargin=0 align="center" onload="init();">
<div name="back_div" id="back_div" class="msg_back">
	<input type="hidden" name="reciever_id"  id="reciever_id" value="">
	<table width="100%" style="font-size: 12px;" border="0"  cellspacing="5" height="100%">
		<tr><td align="right" colspan="2"><img src="index_images/tabClose.gif" border="0" style="cursor:hand" onclick="hiddenDiv();" title="关闭"></td></tr>
		<tr>
			<td width="20%" align="center">消息内容:</td>
			<td width="80%"><textarea rows="4" style="width:88%" name="msg_body" id="msg_body"></textarea> </td>
		</tr>
		<tr height="30">
			<td colspan="2" align="center">
				<input type="button" name="button1" value="发送" class="css_button2" onclick="subMsg();">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="button1" value="关闭" class="css_button2" onclick="hiddenDiv();">
			</td>
		</tr>
	</table>
</div>
<form name="myform" action="listReadedMsg.html" method="post">
<TABLE cellSpacing=0 cellPadding=10 width="100%" border=0>
	<TBODY>
	
	<TR>
		<TD vAlign=top width="100%" align="center">
		
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
				<TR>
					<TD vAlign=bottom>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
							<TR>
								<TD width=10 background="index_images/subTab_topleftL2.gif">&nbsp;</TD>
								<TD class="SubTab_top " align=right>&nbsp;</TD>
								<TD vAlign=bottom align=right width=10><IMG src="index_images/subTab_topright.gif"></TD>
							</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD class=SubTab_LR vAlign=top align=middle>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
							
							<TR>
								<TD vAlign=top align=middle colSpan=3>
									<TABLE id=_ctl1_News_lstAnnouncements style="WIDTH: 95%; BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=2 border=0>
										<TBODY>
										<TR>
											<TD>
												<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
													<TBODY>
					                                <TR height="30">
					                                	<TD class=Bottom_dotted align=left colspan="2" valign="middel">&nbsp;&nbsp;<b>系统消息</b></TD>	
					                                	<TD class=Bottom_dotted align=center width="20%">&nbsp;&nbsp;&nbsp;&nbsp;
					                                		<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="javascript:refreshPage();">刷&nbsp;新</a>
					                                	</TD>																	
													</TR>													
													<ww:iterator value="%{msgPage.results}">
					                                <TR height="30">
					                                	<TD class=Bottom_no_dotted align=left width="25%">发 送 人：<ww:property value="%{sender_name}"/></TD>	
					                                	<TD class=Bottom_dotted align=left valign="top" rowspan="3">内　容：<ww:property value="%{msg_body}"/></TD>
					                                	<TD class=Bottom_no_dotted align=center width="20%"></TD>																				
													</TR>
					                                <TR height="25">
					                                	<TD class=Bottom_no_dotted align=left width="25%">发送时间：<ww:property value="%{send_time}"/></TD>
					                                																		
													</TR>
					                                <TR height="25">
					                                	<TD class=Bottom_dotted align=left width="25%">读取时间：<ww:property value="%{read_time}"/></TD>
					                                	<TD class=Bottom_dotted align=center width="20%">
					                                		<a href="javascript:delMsg('<ww:property value="%{msg_id}"/>');">删除</a>&nbsp;&nbsp;
					                                		<a href="javascript:showDiv('<ww:property value="%{sender_id}"/>');">回复</a>
					                                	</TD>																			
													</TR>																										
													</ww:iterator>																																																																										
													</TBODY>
													<TR height="35">
														<TD class=Bottom_no_dotted align=right colspan="3">
															<crm:page value="%{msgPage}" formname="myform"/>
														</TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
										</TBODY>
									</TABLE>
								</TD>
							</TR>			
							
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				
				<TR>
					<TD vAlign=top align=left height=10>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" background=index_images/subTab_footcenter.gif border=0>
							<TBODY>
							<TR>
								<TD vAlign=bottom align=left width=10><IMG src="index_images/subTab_footleft.gif"></TD>
								<TD class=SubTab_bottom width="100%" height=10></TD>
								<TD vAlign=bottom align=right width=10><IMG src="index_images/subTab_footright.gif"></TD>
							</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>					
				
				</TBODY>
			</TABLE>
			
		</TD>
	</TR>	
	
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>