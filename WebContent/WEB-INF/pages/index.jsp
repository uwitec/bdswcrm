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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=cpy_name %></title>
<link rel="stylesheet" type="text/css" media="all" href="extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" media="all" href="extjs/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<style>
.menu_icon_001 { background-image: url(index_images/merchandise_sales.gif);}
.menu_icon_002 { background-image: url(index_images/stock_m.gif);}
.menu_icon_003 { background-image: url(index_images/stock.gif);}
.menu_icon_004 { background-image: url(index_images/funds.gif);}
.menu_icon_005 { background-image: url(index_images/WORDPAD.gif);}
.menu_icon_006 { background-image: url(index_images/buy.gif);}
.menu_icon_007 { background-image: url(index_images/buy.gif);}
.menu_icon_008 { background-image: url(index_images/AddDocument.gif);}
.menu_icon_009 { background-image: url(index_images/set.gif);}
.menu_icon_010 { background-image: url(index_images/itinerary.gif);}
.menu_icon_011 { background-image: url(index_images/funds.gif);}
.menu_icon_012 { background-image: url(index_images/buy.gif);}
.menu_icon_013 { background-image: url(index_images/AddDocument.gif);}
</style>
<script type="text/javascript">
//右边具体功能面板区
var contentPanel = new Ext.TabPanel({
	region:'center',
	enableTabScroll:true,
	activeTab:0,
	margins: '0 0 0 0',
	border:false,
	items:[{
		id:'homePage',
		title:' 首　页 ',
		autoScroll:true,
		html:'<iframe scrolling="no" name="pageIndex" id="pageIndex" frameborder="0" width="100%" height="100%" src="listMain.html"></iframe>'
	},{
		id:'undoWord',
		title:'待办工作',
		autoScroll:true,
		html:'<iframe scrolling="no" name="pageUndowork" id="pageUndowork" frameborder="0" width="100%" height="100%" src="undoWork.html"></iframe>'
	}]
});

<%
String tpArray = "";
List list = menuService.getColumnList(user_id,"0","");
if(list != null && list.size() > 0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		String menuName = StringUtils.nullToStr(map.get("name"));
		String menuId = StringUtils.nullToStr(map.get("id"));
		String menuImg = StringUtils.nullToStr(map.get("img"));
		
		List childList = menuService.getColumnList(user_id,menuId,"");
		
		if(tpArray.equals("")){
			tpArray = "tp_" + menuId;
		}else{
			tpArray = tpArray + ",tp_" + menuId;
		}
%>		
		//定义treePanel
		var tp_<%=menuId%> = new Ext.tree.TreePanel({
		     animate:true,//以动画形式伸展,收缩子节点
		     title:"<%=menuName%>",
		     rootVisible:false,//是否显示根节点
		     autoScroll:true,
		     autoWidth:true,
		     border:false,
		     useArrows:true,//是否显示小箭头
		     trackMouseOver:true,//鼠标放上去效果
		     lines:true,//节点之间虚线
		     loader:new Ext.tree.TreeLoader(),//加载节点数据
		     iconCls:"menu_icon_<%=menuId%>",
		     root:new Ext.tree.AsyncTreeNode({
		         id:"root",
		         text:"功能列表",//节点名称
		         singleClickExpand:true,//是否单击展开，否则双击展开
		         expanded:true,//展开
		         checked:false,//是否显示checkbox
		         leaf:false
<%
		if(childList != null && childList.size() > 0){
%>
				,
				children:[
<%
			menu_index++;
			for(int k=0;k<childList.size();k++){
				Map childMap = (Map)childList.get(k);
				String childMenuName = StringUtils.nullToStr(childMap.get("name"));
				String childMenuId = StringUtils.nullToStr(childMap.get("id"));
				
				List funcList = menuService.getUserYwgzFunc(user_id,childMenuId);
				if(k==childList.size()-1){
					%>
		  		    {id:"fck_<%=childMenuId%>",expanded:true,
		             text:"<%=childMenuName%>"
<%
						if(funcList != null && funcList.size() > 0){
							%>
							,
							children:[
			<%							
							for(int l=0;l<funcList.size();l++){
								Map funcMap = (Map)funcList.get(l);
								String funcName = StringUtils.nullToStr(funcMap.get("func_name"));
								String funcUrl = StringUtils.nullToStr(funcMap.get("url"));
								String funcImg = StringUtils.nullToStr(funcMap.get("img"));
								if(l == funcList.size() -1){
%>
								  {id:"fc_<%=funcName%>",
								      text:"<%=funcName%>",leaf:true,expanded:true,
							           listeners:{
											'click':function(node, event) {
												event.stopEvent();
												var n = contentPanel.getComponent(node.id);
												if (!n) { //判断是否已经打开该面板
													n = contentPanel.add({
														'id':node.id,
														'title':node.text,
														closable:true,  //通过html载入目标页
														html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="<%=funcUrl%>"></iframe>'
													});
												}
												contentPanel.setActiveTab(n);
											}}}
<%
								}else{
%>
									{id:"fc_<%=funcName%>",
									    text:"<%=funcName%>",leaf:true,expanded:true,
							           listeners:{
											'click':function(node, event) {
												event.stopEvent();
												var n = contentPanel.getComponent(node.id);
												if (!n) { //判断是否已经打开该面板
													n = contentPanel.add({
														'id':node.id,
														'title':node.text,
														closable:true,  //通过html载入目标页
														html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="<%=funcUrl%>"></iframe>'
													});
												}
												contentPanel.setActiveTab(n);
											}}},
<%
								}
							}
%>
						]
<%
						}
%>
		  		  }
<%
				}else{
					%>
		  		    {id:"fck_<%=childMenuId%>",expanded:true,
		             text:"<%=childMenuName%>"
<%
						if(funcList != null && funcList.size() > 0){
							%>
							,
							children:[
			<%							
							for(int l=0;l<funcList.size();l++){
								Map funcMap = (Map)funcList.get(l);
								String funcName = StringUtils.nullToStr(funcMap.get("func_name"));
								String funcUrl = StringUtils.nullToStr(funcMap.get("url"));
								String funcImg = StringUtils.nullToStr(funcMap.get("img"));
								if(l == funcList.size() -1){
%>
								  {id:"fc_<%=funcName%>",
								      text:"<%=funcName%>",leaf:true,expanded:true,
							           listeners:{
											'click':function(node, event) {
												event.stopEvent();
												var n = contentPanel.getComponent(node.id);
												if (!n) { //判断是否已经打开该面板
													n = contentPanel.add({
														'id':node.id,
														'title':node.text,
														closable:true,  //通过html载入目标页
														html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="<%=funcUrl%>"></iframe>'
													});
												}
												contentPanel.setActiveTab(n);
											}}}
<%
								}else{
%>
									{id:"fc_<%=funcName%>",
									    text:"<%=funcName%>",leaf:true,expanded:true,
							           listeners:{
											'click':function(node, event) {
												event.stopEvent();
												var n = contentPanel.getComponent(node.id);
												if (!n) { //判断是否已经打开该面板
													n = contentPanel.add({
														'id':node.id,
														'title':node.text,
														closable:true,  //通过html载入目标页
														html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="<%=funcUrl%>"></iframe>'
													});
												}
												contentPanel.setActiveTab(n);
											}}},
<%
								}
							}
%>
						]
<%
						}
%>
		  		  },
<%
				}
						
			}
%>
			]
<%
		}
%>
				
		     })
		})
<%
	}
}	
%>						

		 





//左侧功能导航区域
var leftNav = new Ext.Panel({
		title:"功能导航",
		width:185,
		region:'west',
		layout:"accordion",
		layoutConfig:{animate:true},
		split:true,
		collapsible: true,
		margins: '0 0 0 5',
		collapseMode: 'mini',
		items:[
			<%=tpArray%>
		]
});

//顶部导航区域
var topNav = new Ext.Panel({
		region:"north",
		split:true,
		collapsible: false,
		collapseMode: 'mini',
		height:62,
		margins:'0 0 0 0',
		autoScroll:false,
		html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="thk_main_top.html?flag=2"></iframe>'
	});

var bottomNav = new Ext.Panel({
	region:"south",
	height:25,
	margins:'0 0 0 0',
	autoScroll:false,
	html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="bottom.jsp"></iframe>'
})

Ext.onReady(function(){
	new Ext.Viewport({
		layout:'border', //使用border布局
		defaults:{activeItem:0},
		items:[
			topNav,
			leftNav,
			contentPanel,
			bottomNav
		]
	});
});

function addtabFmMenu(strTitle,strUrl,id){
	var n = contentPanel.getComponent("readedMsg");
	if (!n) { //判断是否已经打开该面板
		n = contentPanel.add({
			'id':id,
			'title':strTitle,
			closable:true,  //通过html载入目标页
			html:'<iframe scrolling="no" frameborder="0" width="100%" height="100%" src="' + strUrl + '"></iframe>'
		});
	}
	contentPanel.setActiveTab(n);
}
</script>
</head>
<body>
</body>
</html>