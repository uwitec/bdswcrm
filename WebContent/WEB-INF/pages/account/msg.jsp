<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<script>
	parent.opener.document.myform.submit();
	alert("操作成功！");
	parent.window.close();
</script>