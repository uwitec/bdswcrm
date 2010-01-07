<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String strTree = (String)VS.findValue("strTree");
%>

<html>
<head>
<title>产品类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="tree/menu.css" rel="stylesheet" type="text/css">
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
<!--
treedata = new Array();
path = "";
function treemenu(tree_path,tree_name,tree_ico,url,target){
	path	= tree_path;
	name	= tree_name;
	ico	= tree_ico;
	
	if(url!="" && url!=null)
		document.write("<div><img src='"+path+ico+"' align=top border=0><a style='font-size:15px;font-weight:bold' href='"+url+"' target='"+target+"'>"+name+"</a></div>");
	else
		document.write("<div style='font-size:15px;font-weight:bold'><img src='"+path+ico+"' align=top border=0>"+name+"</div>");

}//end function treemenu

//                 0 id      1 父id      2  名称    3  地址   4  关闭图标    5 打开图标     6 指向窗口
function add_item(tree_id,tree_prarent,tree_name,tree_close_ico,tree_open_ico,tree_url,tree_target)
{
	if(tree_close_ico=="")
		tree_close_ico="tree/close.gif";
	if(tree_open_ico=="")
		tree_open_ico="tree/open.gif";
	//                                       0 id      1 父id      2  名称    3  地址   4  关闭图标    5 打开图标     6 指向窗口
	treedata[treedata.length]	= new Array(tree_id,tree_prarent,tree_name,tree_url,tree_close_ico,tree_open_ico,tree_target);
}//end function add_item

function print_arr(){
	var i;
	var j;
	var n	= treedata.length;
	var m	= treedata[0].length;
	
	for(var i=0; i<n; i++)
	{
		for(j=0; j<m;j++)
		{
			//"  treedata["+i+"]["+j+"]="
			document.write(" "+j+":"+treedata[i][j]);
		}//end for
		
		document.write(" <br>\n");
	}//end for
	
}//end function print_arr

function menu(id){						//画菜单
	var currdata	= new Array();
	var i			= 0;
	var printstr	= "<table border='0' cellspacing='0' cellpadding='0'>\n";
	var listtype	= "";
	var menutype	= "";
	
	for(; i<treedata.length; i++){					
		//判断有无此节点
		if(treedata[i][1]==id)	currdata[currdata.length] = treedata[i];
	}//end for	
	
	
	for(var i=0; i<currdata.length; i++){					
		//遍历数组，执行判断
		if(itemExists(currdata[i][0])){						
			//判断是否有子节点
			if(i==currdata.length-1) {			
				// 最后一个
				menutype	= "menu3";
				listtype	= "list1";
			}else{
				menutype	= "menu1";
				listtype	= "list";
			}//end if
			onmouseup	= "chengstate('"+currdata[i][0]+"')";
		}else{					
			//没有子节点
			if(i==currdata.length-1){			
				// 最后一个
				menutype	= "file1";
			}else{
				menutype	= "file";
			}//end if
			onmouseup	= "";
		}//end if

		if(currdata[i][3]!="" && currdata[i][3]!=null){
			menuname	= "<input type='checkbox' name='chkKind' value='" + currdata[i][0] + "|" + currdata[i][2] + "' style='height:16px;width:16px;'><span onMouseOver='over_str(this)' valign='bottom' onMouseOut='out_str(this)' class='item' onclick=" + onmouseup + ">" + currdata[i][2] + "</span>";
		}else{
			menuname	= currdata[i][2];
		}//end if

		ico			= "<img src='"+path+currdata[i][4]+"' id='ico"+currdata[i][0]+"' align=middle border=0 onclick=\""+onmouseup+"\">";
		printstr	+= "<tr><td id='pr"+currdata[i][0]+"' class="+menutype+" onclick=\""+onmouseup+"\"></td><td valign=middle class=textItem>"+ico + menuname+"</td></tr>\n";
		printstr	+= "<tr id='item"+currdata[i][0]+"' style='display:none'><td class="+listtype+" onclick=\""+onmouseup+"\"></td><td class=textItem>"+menu(currdata[i][0])+"</td></tr>\n";

	}//end for
	printstr	+= "</table>\n";
	
	return printstr;
}//end function menu

function itemExists(id){
	for(var i=0;i<treedata.length;i++)
	{
		if(treedata[i][1]==id)return true;
	}//end for
	return false;
}//end function itemExists

function over_str(obj){
	obj.style.background	= "#EEEEEE";
	obj.style.border		= "1px solid #999999"; 
}//end function over

function out_str(obj){
	obj.style.background	= "";
	obj.style.border		= "1px solid #FFFFFF";
}//end function out

function chengstatedbl(id){
	chengstate(id,true);
	event.cancelBubble = true;
}//end function chengstatedbl

function chengstate(menuid,save){											
	//切换节点的开放/关闭
	//alert("menuid:"+menuid+"save:"+save);
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid);
	ico		= eval("ico"+menuid);
	var len	= treedata.length;
	for(var i=0; i<len; i++){
		if(treedata[i][0]==menuid)
		{
			break;
		}//end if
	}//end for
	
	if(menuobj.style.display == ''){
		menuobj.style.display	= 'none';
		ico.src					= path+treedata[i][4];
	}else{
		menuobj.style.display	= '';
		ico.src					= path+treedata[i][5];
	}//end if
	switch (obj.className){
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
	}//end switch
	
}//end funciton chengstaut

function selOk(){
	var objs = document.getElementsByName("chkKind");
	
	var kind_name = "";     //名称组
	var product_kind = "";  //编号组
	
	if(objs != null && objs.length > 0){
		for(var i=0;i<objs.length;i++){
			if(objs[i].checked){
				var arryItems = objs[i].value.split("|");
				
				if(product_kind == ""){
					product_kind = arryItems[0];
				}else{
					product_kind += "," + arryItems[0];
				}
				
				if(kind_name == ""){
					kind_name = arryItems[1];
				}else{
					kind_name += "," + arryItems[1];
				}
			}
		}
	}
	
	window.opener.document.getElementById("product_kind").value = product_kind;
	window.opener.document.getElementById("kind_name").value = kind_name;	
	
	window.close();
}

</SCRIPT>
</head>

<body align="center">
<table width="100%" border="0" align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择产品类别</b></td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" valign="top">
				<script language="javascript" type="text/javascript">
					<%=strTree %>
					document.write(menu(0));
				</script></td>
	</tr>
</table>
<BR>
<table width="100%" border="0" align="center">
	<tr>
		<td width="100%" align="center">
			<input type="button" name="btnOk" class="css_button" onclick="selOk();" value=" 确定 ">
			<input type="button" name="btnCls" class="css_button" onclick="window.close();" value=" 关闭 ">
		</td>
	</tr>
</table>
</body>
</html>
