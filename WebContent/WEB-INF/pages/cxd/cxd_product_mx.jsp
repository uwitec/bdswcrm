<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function clearAll(){
		document.myform.product_con.value = "";
	}

	function selPro(product_id,product_name,product_xh,product_dw,price){
		window.opener.document.getElementById("product_id").value = product_id;
		window.opener.document.getElementById("product_name").value = product_name;
		window.opener.document.getElementById("product_xh").value = product_xh;
		window.opener.document.getElementById("product_dw").value = product_dw;
		window.opener.document.getElementById("price").value = price;

		window.close();
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
					var dw = window.opener.document.getElementById("product_dw_"+k);
					var pc = window.opener.document.getElementById("price_"+k);
					var qz_flag = window.opener.document.getElementById("qz_flag_"+k);
				
				
					id.value = arryStr[0];
					xh.value = arryStr[1];
					name.value = arryStr[2];
					dw.value = arryStr[3];
					pc.value = arryStr[4];
					qz_flag.value = arryStr[5];
					
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
<form name="myform" action="selCxProductMx.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择商品</b></td>	
	</tr>
	<tr>
		<td class="search" align="left">&nbsp;&nbsp;
			商品信息：<input type="text" name="product_con" value="<ww:property value="product_con"/>" size="30">
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%" align="center" border="1" class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td width="10%"><input type="checkbox" name="allCheck" onclick="selectAll();"></td>
		<td width="15%">商品编号</td>
		<td width="30%">商品名称</td>
		<td width="25%">商品规格</td>
		<td width="10%">单位</td>
		<td width="10%">成本</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageProduct.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><input type="checkbox" name="chk_id" value="<ww:property value="productId"/>|<ww:property value="productName"/>|<ww:property value="productXh"/>|<ww:property value="dw"/>|<ww:property value="%{getText('global.format.double',{price})}"/>|<ww:property value="qz_serial_num"/>"></td>
			<td><ww:property value="%{productId}" /></td>
			<td align="left"><ww:property value="%{productName}" /></td>
			<td align="left"><ww:property value="%{productXh}" /></td>
			<td><ww:property value="%{dw}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{price})}" /></td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{pageProduct}" formname="myform"/>
		</td>
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