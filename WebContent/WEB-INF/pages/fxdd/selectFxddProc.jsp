<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择订单商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function clearAll(){
		document.myform.product_name.value = "";
		document.myform.product_xh.value = "";
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
					var pc = window.opener.document.getElementById("price_"+k);				
				
					id.value = arryStr[0];
					xh.value = arryStr[1];
					name.value = arryStr[2];
					pc.value = arryStr[3];

					k++;	
				}	
			}

		}
		
		window.close();
	}
	
</script>
</head>
<body>
<ww:form name="myform" action="selFxddProc.html" method="post">
<ww:hidden name="chk_id" value="" />
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择订单商品</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			产品名称：
			<ww:textfield theme="simple" name="product_name" id="product_name" value="%{product_name}"/> 
			&nbsp;&nbsp;
			规格：<ww:textfield theme="simple" name="product_xh" id="product_xh" value="%{product_xh}"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>序号</td>
		<td><input type="checkbox" name="allCheck" onclick="selectAll();"></td>	
		<td>产品名称</td>
		<td>规格</td>
		<td>分销限价</td>
		<td>单位</td>
	</tr>
	</thead>
	<ww:iterator id="productKc" value="fxddProductPage.results" status="rowstatus">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><ww:property value="#rowstatus.index + 1"/></td>
			<td><input type="checkbox" name="chk_id" value='<ww:property value="#productKc.product_id"/>|<ww:property value="#productKc.product_xh"/>|<ww:property value="#productKc.product_name"/>|<ww:property value="%{getText('global.format.double',{#productKc.fxxj})}"/>'></td>
			<td><ww:property value="#productKc.product_name" /></td>
			<td><ww:property value="#productKc.product_xh" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{#productKc.fxxj})}"/>&nbsp;&nbsp;</td>			
			<td><ww:property value="#productKc.dw" /></td>
		</tr>
	</ww:iterator>
		<tr>
			<td colspan="7" class="page"><crm:page value="%{fxddProductPage}" formname="myform"/></td>
		</tr>	
		<tr>
			<td colspan="7" height="25"><input type="button" name="buttonQd" value=" 确定 " onclick="sel();" class="css_button2"></td>
		</tr>
</table>
</ww:form>
</body>
</html>
