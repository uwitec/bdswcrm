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
<html>
<head>
<title>费用类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="tree/menu.css" rel="stylesheet" type="text/css">

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

		menuname	= "<a onDblClick='selType(\"" + currdata[i][0] + "\",\"" + currdata[i][2] + "\");'>" + currdata[i][2] + "</a>";

		ico			= "<img src='"+path+currdata[i][4]+"' id='ico"+currdata[i][0]+"' align=middle border=0>";
		printstr	+= "<tr><td id='pr"+currdata[i][0]+"' valign=middle class="+menutype+" onclick="+onmouseup+">"+ico+"<span onMouseOver='over_str(this)' valign='bottom' onMouseOut='out_str(this)' class='item'> "+menuname+" </span> </td></tr>\n";
		printstr	+= "<tr id='item"+currdata[i][0]+"' ondblclick='chengstatedbl(\""+currdata[i][0]+"\")' style='display:none'><td class="+listtype+">"+menu(currdata[i][0])+"</td></tr>\n";

	}
	printstr	+= "</table>\n";
	
	return printstr;
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

function selType(id,name){
	var objId = window.opener.document.getElementById("fy_type");
	var objName = window.opener.document.getElementById("fy_type_show");
	
	if(objId != null) objId.value = id;
	if(objName != null) objName.value = name;
	
	window.close();
}
</script>

</head>

<body align="center">
<table width="100%" border="0" align="center" class="chart_list"
	cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择费用类别</b></td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="1%">&nbsp;</td>
		<td width="98%" valign="top"><span class="a1" height="30">费用类别
		<script language="javascript" type="text/javascript">
			<%=strTree %>
			document.write(menu('00'));
		</script>
		<BR>
		<font color="red">注：双击鼠标左键选择</font>
		</td>
		<td width="1%">&nbsp;</td>
	</tr>
</table>

</body>
</html>
