<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="templates/head.jsp" />
	</head>
	<body>
		<jsp:include page="templates/header.jsp" />
		
		<c:choose>
			<c:when test="${not empty page['action']}">
		      <jsp:include page="contents/${page['module']}/${page['action']}.jsp" />
			</c:when>
			<c:otherwise>
		      <jsp:include page="contents/${page['module']}.jsp" />
			</c:otherwise>
		</c:choose>
		
		<jsp:include page="templates/message.jsp">
			<jsp:param name="msg_type" value="${message['type']}" />
			<jsp:param name="msg_text" value="${message['text']}" />
		</jsp:include>
		
		<jsp:include page="templates/footer.jsp" />
	</body>
</html>
