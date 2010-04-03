<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productKcPage");

String product_xh = (String)VS.findValue("product_xh");
String product_name = (String)VS.findValue("product_name");
String store_id = (String)VS.findValue("store_id");
String product_kind = (String)VS.findValue("product_kind");

String iscs_flag = (String)VS.findValue("iscs_flag");
%>

<html>
<head>
<title>库存列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	//打开设置存初始数量窗口
	function openWin(id,qz_flag){
		if(document.myform.store_id.value == ""){
			alert("请先保存页面顶部的初始基本信息，再开始初始商品明细库存！");
			return;
		}
		var destination = "";
		var fea = "";
		
		if(qz_flag == "是"){
			destination = "addProductKcSerial.html?product_id=" + id + "&store_id=" + document.myform.store_id.value;
			fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizeable=no';
		}else{
			destination = "addProductKc.html?product_id=" + id + "&store_id=" + document.myform.store_id.value;
			fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		}

		window.open(destination,'设置库存数量',fea);	
	}
	
	//删除记录
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delProductKc.html?kc_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
	}
	
	function add(){
		lcation.href = "addJhd.html";
	}
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body >
<form name="myform" action="listInitProductKc.html" method="post">
<input type="hidden" name="store_id" value="<%=store_id %>">
<input type="hidden" name="product_kind" value="<%=product_kind %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			商品名称：<input type="text" name="product_name" value="<%=product_name %>">&nbsp;&nbsp;
			规格：<input type="text" name="product_xh" value="<%=product_xh %>">&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>计量单位</td>
		<td>启用日期</td>
		<td>初始数量</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		String initNums = StringUtils.nullToStr(map.get("init_nums"));
		
		String cssStyle = "";
		if(!initNums.equals("")){
			cssStyle = "style=\"color:red\"";
		}
	%>
	<tr class="a1"  onmousedown="trSelectChangeCss()" <%=cssStyle %> <%if(iscs_flag.equals("0")){ %> title="双击设置商品库存" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>');" <%}else{ %> title="库存初始已完成"<%} %>>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>		
		<td><%=StringUtils.nullToStr(map.get("dw")) %></td>
		<td><%=StringUtils.nullToStr(map.get("init_date")) %></td>
		<td><%=initNums %></td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
<BR>
商品库存初始分为两种情况，<BR>一、强制序列号商品，需要输入序列号来初始库存数量；<BR>二、不需要强制序列号商品，只需输入初始数量即可。<BR>红色字体代表，商品库存已初始化，
系统初始工作结束后商品库存初始数据只可查看，不能修改。
</form>
</body>
</html>
