<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<script>
	alert("操作成功！");
	window.close();
	opener.document.myform.submit();
</script>