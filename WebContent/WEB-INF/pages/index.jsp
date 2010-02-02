<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.service.MenuService" %>
<%@ page import="com.sw.cms.model.LoginInfo" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
MenuService menuService = (MenuService)VS.findValue("menuService");

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

String cpy_name = (String)VS.findValue("cpy_name");

int menu_index = 0;
%>
<HTML>
<HEAD>
<TITLE><%=cpy_name %></TITLE>
<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/Desktop.css" type=text/css rel=stylesheet>
<SCRIPT language=javascript src="script/Main.js"></SCRIPT>
<SCRIPT language=javascript src="script/RollScript.js"></SCRIPT>
<SCRIPT language=javascript src="script/eTab.js"></SCRIPT>
<SCRIPT language=javascript src="script/Menu.js"></SCRIPT>
<script type="text/javascript">
	function hidMenu(){
		if(tdService.style.display=='none'){
			tdService2.style.display='inline';
			tdService.style.display='inline';
			splitImg.src = "index_images/p_1.gif";
		} else {
			tdService.style.display='none';
			tdService2.style.display='none';
			splitImg.src = "index_images/p_2.gif";
		}
	}
</script>
</HEAD>
<BODY id=Body bottomMargin=0 leftMargin=0 topMargin=0 onload="MenusInit();" align="left">
<TABLE cellSpacing=0 cellPadding=0 width="100%" height="100%" background="" border=0>
	<TBODY>
	<!-- 头开始 -->
	<TR vAlign=top height="60">
		<TD colspan=3><IFRAME id=ifrIndex2 style="WIDTH: 100%; height:100%; POSITION: relative" border=0  src="thk_main_top.html" frameBorder=0 scrolling=no></IFRAME></TD>
	<TR>
	<!-- 头结束 -->	
	
	<!-- 导航部分开始 -->
	<TR vAlign=top height=30>
		<TD id=tdService2 class=HeadTab_Bottom  vAlign=center align=left>
		<SPAN class=NormalTextBox id=lblDate></SPAN><SCRIPT>setInterval("lblDate.innerHTML=new Date().toLocaleString();",1000);</SCRIPT>
		</td>
		<td class=HeadTab_Bottom  vAlign=center align=left></td>
		<td class=HeadTab_Bottom  vAlign=center align=left>
			<table id="tabTable" summary="" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td nowrap>
						<div class="tabHeader_div">
						<ul id="tabHeader">	
							<li id="first">首页</li>
							<li id="second">待办工作</li>
						</ul>
						</div>
					</td>
				</tr>
			</table>
		</TD>
	<TR>
	<!-- 导航部分结束 -->
  	
	<TR>
		<!-- 左侧菜单区开始 -->
    	<TD id=tdService vAlign=top noWrap align=left width="156">

				
				
			<%
			List list = menuService.getColumnList(user_id,"0","1");
			if(list != null && list.size() > 0){
				
			%>
			<TABLE cellSpacing=0 cellPadding=0 width=156 align=center border=0>
				<TBODY>			
				<TR>
					<TD class=Menutitle>业务功能</TD>
				</TR>
				<TR>
					<TD vAlign=center align=left>
					<UL id=menus>			
			<%
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					String menuName = StringUtils.nullToStr(map.get("name"));
					String menuId = StringUtils.nullToStr(map.get("id"));
					String menuImg = StringUtils.nullToStr(map.get("img"));
					
					List childList = menuService.getColumnList(user_id,menuId,"1");
					
					if(childList != null && childList.size() > 0){
						menu_index++;
			%>
				<!-- 子菜单开始 -->

					<LI class=MenuItem id=menu<%=menu_index %>><SPAN class=MenuItemImg><IMG src="index_images/<%=menuImg %>" align=absMiddle border=0><%=menuName %></SPAN>
						
						<!-- 隐藏菜单部分开始 -->
						<DIV class=hidden id=menu<%=menu_index %>_cont  onmouseover=showMenuCont(this);  onmouseout=hiddenMenuCont(this);>
							<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TBODY>
								<TR>
									<TD class=MenuBackGround>&nbsp;</TD>
									<TD class=MenuBackGround>
										<TABLE cellSpacing=5 border=0>
											<TBODY>
											<TR>
											<%
											
												for(int k=0;k<childList.size();k++){
													Map childMap = (Map)childList.get(k);
													String childMenuName = StringUtils.nullToStr(childMap.get("name"));
													String childMenuId = StringUtils.nullToStr(childMap.get("id"));
													
													List funcList = menuService.getUserYwgzFunc(user_id,childMenuId);
													
													
													if(funcList != null && funcList.size() > 0){	
													
											%>
												<!-- 隐藏菜单列开始 -->
												<TD>
													<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
														<TBODY>
														
														<!-- 菜单项头开始 -->
														<TR vAlign=top>
															<TD class=Menutitle><A href="javascript:void(0);">&nbsp;&nbsp;<%=childMenuName %>&nbsp;</A></TD>
														</TR>
														<!-- 菜单项头结束 -->
														
													<%
													
														for(int l=0;l<funcList.size();l++){
															Map funcMap = (Map)funcList.get(l);
															String funcName = StringUtils.nullToStr(funcMap.get("func_name"));
															String funcUrl = StringUtils.nullToStr(funcMap.get("url"));
															String funcImg = StringUtils.nullToStr(funcMap.get("img"));
													%>
														<!-- 菜单项开始 -->
														<TR vAlign=top>
															<TD>
																<TABLE style="BORDER-COLLAPSE: collapse; HEIGHT: 100%" cellSpacing=0 cellPadding=0 border=0>
																	<TBODY>
																	<TR>
																		<TD><A class=Menuitem href="javascript:void(0);" onclick="addtabFmMenu('<%=funcName %>','<%=funcUrl %>','');"><IMG src="index_images/<%=funcImg %>" align=absMiddle border=0>&nbsp;&nbsp;<%=funcName %>&nbsp;</A></TD>
																	</TR>
																	</TBODY>
																</TABLE>
															</TD>
														</TR>
														<!-- 菜单项结束 -->													
													<%
														}
													%>			
																					
														</TBODY>
													</TABLE>
												</TD>
												<!-- 隐藏菜单列结束  -->													
											<%		
													}
												}
											
											%>
												
											</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD class=MenuBackGround>&nbsp;</TD>
								</TR>								
								</TBODY>
							</TABLE>
						</DIV>
						<!-- 隐藏菜单部分结束 -->
						
					</LI>

				<!-- 子菜单结束 -->			
			<%
					}
				}
			%>
					</UL>
					</TD>
				</TR>				
				</TBODY>
			</TABLE>			
			<%
			}
			%>
				
				
				
			<br>
			
			
			<%
			list = menuService.getColumnList(user_id,"0","0");
			if(list != null && list.size() > 0){
				
			%>
			<TABLE cellSpacing=0 cellPadding=0 width=156 align=center border=0>
				<TBODY>			
				<TR>
					<TD class=Menutitle>系统管理</TD>
				</TR>
				<TR>
					<TD vAlign=center align=left>
					<UL id=menus2>			
			<%
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					String menuName = StringUtils.nullToStr(map.get("name"));
					String menuId = StringUtils.nullToStr(map.get("id"));
					String menuImg = StringUtils.nullToStr(map.get("img"));
					
					List childList = menuService.getColumnList(user_id,menuId,"0");
					
					if(childList != null && childList.size() > 0){
						menu_index++;
			%>
				<!-- 子菜单开始 -->

					<LI class=MenuItem id=menu<%=menu_index %>><SPAN class=MenuItemImg><IMG src="index_images/<%=menuImg %>" align=absMiddle border=0><%=menuName %></SPAN>
						
						<!-- 隐藏菜单部分开始 -->
						<DIV class=hidden id=menu<%=menu_index %>_cont  onmouseover=showMenuCont(this);  onmouseout=hiddenMenuCont(this);>
							<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TBODY>
								<TR>
									<TD class=MenuBackGround>&nbsp;</TD>
									<TD class=MenuBackGround>
										<TABLE cellSpacing=5 border=0>
											<TBODY>
											<TR>
											<%
											
												for(int k=0;k<childList.size();k++){
													Map childMap = (Map)childList.get(k);
													String childMenuName = StringUtils.nullToStr(childMap.get("name"));
													String childMenuId = StringUtils.nullToStr(childMap.get("id"));
													
													List funcList = menuService.getUserYwgzFunc(user_id,childMenuId);
													
													
													if(funcList != null && funcList.size() > 0){	
													
											%>
												<!-- 隐藏菜单列开始 -->
												<TD>
													<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
														<TBODY>
														
														<!-- 菜单项头开始 -->
														<TR vAlign=top>
															<TD class=Menutitle><A href="javascript:void(0);">&nbsp;&nbsp;<%=childMenuName %>&nbsp;</A></TD>
														</TR>
														<!-- 菜单项头结束 -->
														
													<%
													
														for(int l=0;l<funcList.size();l++){
															Map funcMap = (Map)funcList.get(l);
															String funcName = StringUtils.nullToStr(funcMap.get("func_name"));
															String funcUrl = StringUtils.nullToStr(funcMap.get("url"));
															String funcImg = StringUtils.nullToStr(funcMap.get("img"));
													%>
														<!-- 菜单项开始 -->
														<TR vAlign=top>
															<TD>
																<TABLE style="BORDER-COLLAPSE: collapse; HEIGHT: 100%" cellSpacing=0 cellPadding=0 border=0>
																	<TBODY>
																	<TR>
																		<TD><A class=Menuitem href="javascript:void(0);" onclick="addtabFmMenu('<%=funcName %>','<%=funcUrl %>','');"><IMG src="index_images/<%=funcImg %>" align=absMiddle border=0>&nbsp;&nbsp;<%=funcName %>&nbsp;</A></TD>
																	</TR>
																	</TBODY>
																</TABLE>
															</TD>
														</TR>
														<!-- 菜单项结束 -->													
													<%
														}
													%>			
																					
														</TBODY>
													</TABLE>
												</TD>
												<!-- 隐藏菜单列结束  -->													
											<%		
													}
												}
											
											%>
												
											</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD class=MenuBackGround>&nbsp;</TD>
								</TR>								
								</TBODY>
							</TABLE>
						</DIV>
						<!-- 隐藏菜单部分结束 -->
						
					</LI>

				<!-- 子菜单结束 -->			
			<%
					}
				}
			%>
					</UL>
					</TD>
				</TR>				
				</TBODY>
			</TABLE>			
			<%
			}
			%>			
		
	
						
		</TD>
		<!-- 左侧菜单区结束 -->
		
		<TD class=Admin_BackGround vAlign=center align=middle width=5><IMG id="splitImg" src="index_images/p_1.gif"></TD>
		
		<!-- 右侧主工作区 -->
		<TD  valign="top" align="left" width="100%" id="tdContent" height="100%" >
			<div class="tabcontent_div">
			<ul id="tabContent">							
				<li><iframe id="ifrIndex1" src="listMain.html" scrolling="auto" border="0" style="width:100%;" onload="FrameChange('ifrIndex1');" frameborder="0"></iframe></li>
				<li><iframe id="ifrIndex2" src="undoWork.html" scrolling="auto" border="0" style="width:100%;" onload="FrameChange('ifrIndex2');" frameborder="0"></iframe></li>
			</ul>
			</div>
		</TD>
		<!-- 右侧主工作区 -->
	</TR>
	
	<!-- 尾开始 --><!--
	<TR vAlign=top height="25">
		<TD colspan=3>
			<TABLE cellSpacing=0 cellPadding=0 width=100% height=100% align=center border=0 bgColor=#DDDDDD>
				<tr>
					<td width="100%" align="center"><font style="font-size: 12px;">保定思维 ©2007-2008</font> </td>
				</tr>
			</TABLE>
		</TD>
	<TR>
	--><!-- 尾结束 -->	
		
</TBODY>					
<script language="javascript">
		document.getElementById("tabTable").width = screen.availWidth-200;
		DesktopInit();
</script>
</BODY>
</HTML>
                              