<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page productPage = (Page)VS.findValue("productPage");

String product_name = StringUtils.nullToStr((String)VS.findValue("product_name"));
String product_kind = StringUtils.nullToStr((String)VS.findValue("product_kind"));
List kindList = (List)VS.findValue("kindList");

String openerId = ParameterUtility.getStringParameter(request, "openerId","");
%>

<html>
<head>
<title>商品列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.product_kind.value = "";
		document.myform.product_name.value = "";
	}

	function openWin(id){
		var destination = "viewKcye.html?product_id="+id;
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'设置库存数量',fea);	
	}

	function selectAll(){
		var flag = false;
		if(document.myform.allCheck.checked){
			flag = true;
		}
		for(var i=0;i<document.myform.elements.length;i++){
			var o = document.myform.elements[i];
			if(o.name == "chk_id"){
				if(flag == true){
					o.checked = true;
				}else{
					o.checked = false;
				}
			}
		}
	}	

	function sel(sel_flag){
		var k = 0;
		var allCount = window.opener.allCount;  //当前存在的最大行序号
		//alert("当前存在的最大行序号" + allCount);
		var startCount = 0;
		for(var x=0;x<=allCount;x++){
			var id = window.opener.document.getElementById("product_id_"+x);
			var name = window.opener.document.getElementById("product_name_"+x);
	
			startCount = x;
			
			if(id.value=="" && name.value==""){  //如果该行无值则跳出
				break;
			}
			
			if(startCount == allCount){
				startCount = startCount + 1;
			}			
		}
		
		k = startCount;  //从此位置开始添加
		//alert("从此位置开始添加" + k);
		
		for(var i=1;i<document.myform.chk_id.length;i++){
					
			var o = document.myform.chk_id[i];
			if(o.checked){
			
				var vl = document.myform.chk_id[i].value;
				var arryStr = vl.split("|");
				
				var flag = false;
				for(var y=0;y<=allCount;y++){
					var s_id = window.opener.document.getElementById("product_id_"+y);
					
					if(s_id.value == arryStr[0]){  //该值已存在
						flag = true;
					}
				}			
			
				if(flag == false){
					var id = window.opener.document.getElementById("product_id_"+k);
					
					if(id == null){
						window.opener.addTr();
					}
					
					id = window.opener.document.getElementById("product_id_"+k);
					
					var xh = window.opener.document.getElementById("product_xh_"+k);
					var name = window.opener.document.getElementById("product_name_"+k);
					var qz_flag = window.opener.document.getElementById("qz_flag_"+k);
				
					id.value = arryStr[0];
					xh.value = arryStr[1];
					name.value = arryStr[2];
					qz_flag.value = arryStr[3];
					k++;	
				}	
			}
		}
		
		if(sel_flag == "2"){
			window.close();
		}
	}	
	
</script>
</head>
<body>
<form name="myform" action="selKfdbProc.html" method="post">
<input type="hidden" name="chk_id" value="">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择库存商品</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			商品：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;&nbsp;&nbsp;
			类别：
			<select name="product_kind">
				<option value=""></option>
				<%
				if(kindList != null &&  kindList.size()>0){
					for(int i=0;i<kindList.size();i++){
						Map map = (Map)kindList.get(i);
						String id = StringUtils.nullToStr(map.get("id"));
						String name = StringUtils.nullToStr(map.get("name"));
						for(int k=0;k<id.length()-3;k++){
							name = "　" + name;
						}
				%>
				<option value="<%=id %>" <%if(product_kind.equals(id)) out.print("selected"); %>><%=name %></option>
				<%
					}
				}
				%>
			</select>			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><input type="checkbox" name="allCheck" onclick="selectAll();"></td>
		<td>商品编号</td>
		<td>商品名称</td>
		<td>规格</td>
		<td>库存数量</td>
		<td>强制序列号</td>
	</tr>
	</thead>
	<%
	List productKcs = productPage.getResults();
	if(productKcs != null && productKcs.size()>0){
		Iterator it = productKcs.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			String vl = StringUtils.nullToStr(map.get("product_id")) + "|" + StringUtils.nullToStr(map.get("product_xh")) + "|" + StringUtils.nullToStr(map.get("product_name")) + "|" + StringUtils.nullToStr(map.get("qz_serial_num"));
	%>
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">		
			<td><input type="checkbox" name="chk_id" value="<%=vl %>"></td>
			<td><%=StringUtils.nullToStr(map.get("product_id")) %></td>
			<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
			<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
			<td><%=StringUtils.nullToStr(map.get("kc_nums")) %></td>
			<td><%=StringUtils.nullToStr(map.get("qz_serial_num")) %></td>
		</tr>
	
	<%
		}
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=productPage.getPageScript() %></td>
	</tr>
</table>
<center><BR>
	<input type="button" name="buttonQd" value="确认并继续选择" onclick="sel('1');" class="css_button4">
	<input type="button" name="buttonQd" value="确认选择并关闭" onclick="sel('2');" class="css_button4">
	<input type="button" name="buttonQd" value=" 关闭 " onclick="window.close();" class="css_button2">
</center>
</form>
</body>
</html>
