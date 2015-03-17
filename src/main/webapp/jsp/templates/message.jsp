<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${param.msg_type == 'ERROR'}">
		<c:set var="message_color" scope="session" value="#c00" />
		<c:set var="background_color" scope="session" value="#fcc" />
		<c:set var="border" scope="session" value="1px solid #f99" />
		<c:set var="present" scope="session" value="true" />
	</c:when>
	<c:when test="${param.msg_type == 'WARNING'}">
		<c:set var="message_color" scope="session" value="#930" />
		<c:set var="background_color" scope="session" value="#ffc" />
		<c:set var="border" scope="session" value="1px solid #fc0" />
		<c:set var="present" scope="session" value="true" />
	</c:when>
	<c:when test="${param.msg_type == 'QUESTION'}">
		<c:set var="message_color" scope="session" value="#060" />
		<c:set var="background_color" scope="session" value="#cec" />
		<c:set var="border" scope="session" value="1px solid #aca" />
		<c:set var="present" scope="session" value="true" />
	</c:when>
	<c:when test="${param.msg_type == 'INFORMATION'}">
		<c:set var="message_color" scope="session" value="#00c" />
		<c:set var="background_color" scope="session" value="#ccd" />
		<c:set var="border" scope="session" value="1px solid #99c" />
		<c:set var="present" scope="session" value="true" />
	</c:when>
	<c:otherwise>
		<c:set var="message_color" scope="session" value="inherit" />
		<c:set var="background_color" scope="session" value="inherit" />
		<c:set var="border" scope="session" value="0px solid #ccc" />
		<c:set var="present" scope="session" value="false" />
    </c:otherwise>
</c:choose>

<c:if test="${present == true}">
	<p class="Message" style="color: <c:out value="${message_color}" />; border: <c:out value="${border}" />; background-color: <c:out value="${background_color}" />;">
		<c:out value="${param.msg_text}" />
	</p>
</c:if>
