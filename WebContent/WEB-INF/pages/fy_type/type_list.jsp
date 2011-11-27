<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String strTree = (String)VS.findValue("strTree");

String msg = (String)VS.findValue("msg");
if(msg != null && !msg.equals("")){
	out.print("<script>alert(\"" + msg + "\");</script>");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>费用类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="tree/menu.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script language="JavaScript">
treedata = new Array();
path = "";

function add_item(tree_id,tree_prarent,tree_name){
	var	tree_close_ico="tree/close.gif";
	var	tree_open_ico="tree/open.gif";
	treedata[treedata.length]	= new Array(tree_id,tree_prarent,tree_name,"",tree_close_ico,tree_open_ico);
}


function menu(id){
	var currdata	= new Array();
	var i			= 0;
	var printstr	= "<table border='0' cellspacing='0' cellpadding='0'>\n";
	var listtype	= "";
	var menutype	= "";
	
	for(; i<treedata.length; i++){					//判断有无此节点
		if(treedata[i][1]==id)	currdata[currdata.length] = treedata[i];
	}
	var onmouseup = null;
	for(var i=0; i<currdata.length; i++){					//遍历数组，执行判断
		if(itemExists(currdata[i][0])){						//判断是否有子节点
			if(i==currdata.length-1){			// 最后一个
				menutype	= "menu3";
				listtype	= "list1";
			}else{
				menutype	= "menu1";
				listtype	= "list";
			}
			onmouseup	= "chengstate('"+currdata[i][0]+"')";
		}else{					//没有子节点
			if(i==currdata.length-1){			// 最后一个
				menutype	= "file1";
			}else{
				menutype	= "file";
			}
			onmouseup	= "";
		}

		menuname	= "<a oncontextmenu='document.getElementById(\"parent_id\").value=\"" + currdata[i][0] + "\";mlay.style.display=\"\";mlay.style.pixelTop=getScrollTop() + event.clientY+document.body.scrollTop; mlay.style.pixelLeft=event.clientX; return false;'>" + currdata[i][2] + "</a>";

		ico			= "<img src='"+path+currdata[i][4]+"' id='ico"+currdata[i][0]+"' align=middle border=0>";
		printstr	+= "<tr><td id='pr"+currdata[i][0]+"' valign=middle class="+menutype+" onclick="+onmouseup+">"+ico+"<span onMouseOver='over_str(this)' valign='bottom' onMouseOut='out_str(this)' class='item'> "+menuname+" </span> </td></tr>\n";
		printstr	+= "<tr id='item"+currdata[i][0]+"' ondblclick='chengstatedbl(\""+currdata[i][0]+"\")' style='display:none'><td class="+listtype+">"+menu(currdata[i][0])+"</td></tr>\n";

	}
	printstr	+= "</table>\n";
	
	return printstr;
}

function getScrollTop(){
	var y;
	if(document.documentElement && document.documentElement.scrollTop) {    
		// IE 6 Strict    
		y = document.documentElement.scrollTop;    
	} else if(document.body) { 
		// all other IE    
		y = document.body.scrollTop;    
	} else {
		y = 0;
	}
	return y;
}

function itemExists(id){
	for(var i=0;i<treedata.length;i++){
		if(treedata[i][1]==id)return true;
	}
	return false;
}

function closeAll(){											
	// 所有节点全部折叠
	var len	= treedata.length;
	for(var i=0; i<len; i++){					
		//遍历数组，执行判断
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu2" || obj.className == "menu4"){						
			//判断是否有子节点
			chengstate(treedata[i][0]);
		}
	}
	
}

function openAll(){											
    // 所有节点全部展开
	var len	= treedata.length;
	for(var i=0; i<len; i++){//遍历数组，执行判断
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu1" || obj.className == "menu3"){//判断是否有子节点
			chengstate(treedata[i][0]);
		}
	}
}

function over_str(obj){
	obj.style.background	= "#EEEEEE";
	obj.style.border		= "1px solid #999999";
}

function out_str(obj){
	obj.style.background	= "";
	obj.style.border		= "1px solid #FFFFFF";
}

function chengstatedbl(id){
	chengstate(id,true);
	event.cancelBubble = true;
}

function chengstate(menuid,save){
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid);
	ico		= eval("ico"+menuid);
	var len	= treedata.length;
	for(var i=0; i<len; i++){
		if(treedata[i][0]==menuid)
		{
			break;
		}
	}
	
	if(menuobj.style.display == ''){
		menuobj.style.display	= 'none';
		ico.src					= path+treedata[i][4];
	}else{
		menuobj.style.display	= '';
		ico.src					= path+treedata[i][5];
	}switch (obj.className){
		case "menu1":
			obj.className	= "menu2";
			break;
		case "menu2":
			obj.className	= "menu1";
			break;
		case "menu3":
			obj.className	= "menu4";
			break;
		case "menu4":
			obj.className	= "menu3";
			break;
	}
}

function window_onload(){
	fresh();
}

function openAdd(){
	var destination = "editFyType.html?parent_id=" + document.myform.parent_id.value;
	var fea ='width=400,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'',fea);	
}

function openEdit(){
	var destination = "editFyType.html?id=" + document.myform.parent_id.value;
	var fea ='width=400,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'',fea);		
}

function del(){
	if(confirm("确认要删除该类别吗？")){
		location.href = "delFyType.html?id=" + document.myform.parent_id.value;
	}
}


var mname=new Array( 
"新　建", 
"修　改", 
"删　除"
); 

//mname是菜单对应的名称，数组的个数必须与下面murl对应 
var murl=new Array( 
"openAdd();", 
"openEdit();", 
"del();"
); 
//murl是菜单对应的操作，可以是任意javascript代码但是要注意不要在里面输入\"，只能用' 
//如果要实现跳转可以这样window.location='url';  
var ph=22,mwidth=100;//每条选项的高度,菜单的总宽度 
var bgc="#EEEEEE",txc="black";//菜单没有选中的背景色和文字色 
var cbgc="#888888",ctxc="white";//菜单选中的选项背景色和文字色 


/****************以下代码请不要修改******************/ 
var mover="this.style.background='"+cbgc+"';this.style.color='"+ctxc+"';" 
var mout="this.style.background='"+bgc+"';this.style.color='"+txc+"';" 


function showoff(){ 
 mlay.style.display="none"; 
} 

function fresh() { 
	mlay.style.background=bgc; 
	mlay.style.color=txc; 
	mlay.style.width=mwidth; 
	mlay.style.height=mname.length*ph; 
	var h="<table width=100% height="+mname.length*ph+"px cellpadding=0  cellspacing=0 border=0>"; 
	var i=0; 
	for(i=0;i<mname.length;i++){ 
		h+="<tr align=center height="+ph+"><td width=\"20\" style='background:blue'></td><td width=\"80\" style='font-size:9pt;'onclick=\""+murl[i]+"\" onMouseover=\""+mover+"\" onMouseout=\""+mout+"\">"+mname[i]+"</td></tr>"; 
	} 
	h+="</table>"; 
	mlay.innerHTML=h; 
} 

</script>

</head>

<body onLoad="window_onload();" onClick="showoff();">
<div class="rightContentDiv" id="divContent">
<div id="mlay" style="position:absolute;display:none;cursor:default;" onClick="return false;"></div>
<table width="100%" border="0" align="center" class="chart_list"
	cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>费用类别维护</b></td>
	</tr>
<form name="myform" action="listFyType.html" method="post">
<input type="hidden" name="parent_id" id="parent_id" value="">
</form>	
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="1%">&nbsp;</td>
		<td width="98%" valign="top" style="text-align: left"><span class="a1" height="30">费用类别
		<script language="javascript" type="text/javascript">
			<%=strTree %>
			document.write(menu('00'));
			openAll();
		</script></td>
		<td width="1%">&nbsp;</td>
	</tr>
</table>
<BR><BR><BR><BR><BR><BR>
</div>
</body>
</html>
