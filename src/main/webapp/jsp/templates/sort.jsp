<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:choose>
		<c:when test="${sorting.sort_order == 'desc'}">
			<c:set var="order" value="asc" scope="session" />
			<c:set var="image" value="images/sort_descending.png" scope="session" />
			<c:set var="title" value="Malejąco" scope="session" />
		</c:when>
		<c:otherwise>
			<c:set var="order" value="desc" scope="session" />
			<c:set var="image" value="images/sort_ascending.png" scope="session" />
			<c:set var="title" value="Rosnąco" scope="session" />
		</c:otherwise>
	</c:choose>
