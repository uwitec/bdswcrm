<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page productPage = (Page)VS.findValue("productPage");

String product_xh = ParameterUtility.getStringParameter(request,"product_xh", "");
String product_name = ParameterUtility.getStringParameter(request,"product_name", "");

%>

<html>
<head>
<title>产品列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
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


	function sel(){
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
					var ysjg = window.opener.document.getElementById("ysjg_"+k);
				
				
					id.value = arryStr[0];
					xh.value = arryStr[1];
					name.value = arryStr[2];
					ysjg.value = arryStr[3];
					
					k++;	
				}	
			}

		}
		
		window.close();
	}
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selChProc.html" method="post">
<input type="hidden" name="chk_id" value="">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择产品</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			产品名称：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;
			规格：<input type="text" name="product_xh" value="<%=product_xh %>" size="20">&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><input type="checkbox" name="allCheck" onclick="selectAll();"></td>	
		<td>产品名称</td>
		<td>规格</td>		
		<td>库存数量</td>
		<td>成本价</td>
		<td>分销限价</td>
		<td>零售限价</td>
	</tr>
	</thead>
	<%
	List productKcs = productPage.getResults();
	if(productKcs != null && productKcs.size()>0){
		Iterator it = productKcs.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			String state = StringUtils.nullToStr(map.get("flag"));
			if(state.equals("1")){
				state = "正常";
			}else{
				state = "停售";
			}
			
			double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
			double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
			double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
			
			String vl = StringUtils.nullToStr(map.get("product_id")) + "|" + StringUtils.nullToStr(map.get("product_xh")) + "|" + StringUtils.nullToStr(map.get("product_name")) + "|" + JMath.round(price);
	%>
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><input type="checkbox" name="chk_id" value="<%=vl %>"></td>
			<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
			<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
			<td><%=StringUtils.nullToStr(map.get("nums")) %></td>
			<td><%=JMath.round(price,2) %></td>
			<td><%=JMath.round(fxxj,2) %></td>
			<td><%=JMath.round(lsxj,2) %></td>
		</tr>
	
	<%
		}
	}
	%>
		<tr>
			<td colspan="7" class="page"><%=productPage.getPageScript() %></td>
		</tr>	
		<tr>
			<td colspan="7"><input type="button" name="buttonQd" value=" 确定 " onclick="sel();" class="css_button2"></td>
		</tr>
</table>
</form>
</body>
</html>
