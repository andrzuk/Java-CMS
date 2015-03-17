<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/admin"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="95%" cellpadding="5" cellspacing="0">

	<c:set var="columns_count" value="6" />
	<c:set var="actions_count" value="1" />
	
	<jsp:include page="/jsp/templates/sort.jsp">
		<jsp:param name="sorting" value="${sorting}" />
	</jsp:include>

	<jsp:include page="/jsp/templates/titlebar.jsp">
		<jsp:param name="title_image" value="images/user.png" />
		<jsp:param name="columns_count" value="${columns_count}" />
	</jsp:include>
	
	<jsp:include page="/jsp/templates/filter.jsp">
		<jsp:param name="columns_count" value="${columns_count}" />
	</jsp:include>
	
	<tr class="ColumnHeaders">
		<c:forEach var="i" begin="0" end="${columns_count - actions_count - 1}">
			<td width="<c:out value="${sorting.fields_widths[i]}" />" style="text-align: <c:out value="${sorting.fields_aligns[i]}" />;"><a href="/<c:out value="${page.module}" />?sort=<c:out value="${sorting.fields_names[i]}" />&order=<c:out value="${order}" />" class="<c:if test="${sorting.sort_field == sorting.fields_names[i]}">Ordered</c:if>"><c:out value="${sorting.fields_names[i]}" /></a><c:if test="${sorting.sort_field == sorting.fields_names[i]}">&nbsp;<img src="<c:out value="${image}" />" title="<c:out value="${title}" />" /></c:if></td>
		</c:forEach>
		<td class="Action" colspan="${actions_count}">Actions</td>
	</tr>
	
	<c:forEach items="${data}" var="user" varStatus="iterator">
		<c:choose>
			<c:when test="${user.active == 'true'}">
				<c:set var="style_name" value="active" />
			</c:when>
			<c:otherwise>
				<c:set var="style_name" value="passive" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${user.status == 1}">
				<c:set var="status_name" value="Adm" />
			</c:when>
			<c:when test="${user.status == 2}">
				<c:set var="status_name" value="Opr" />
			</c:when>
			<c:when test="${user.status == 3}">
				<c:set var="status_name" value="Usr" />
			</c:when>
			<c:otherwise>
				<c:set var="status_name" value="None" />
			</c:otherwise>
		</c:choose>
			
		<tr class="${iterator.index % 2 == 0 ? 'even' : 'odd'} <c:out value="${style_name}" />">
			<td style="text-align: <c:out value="${sorting.fields_aligns[0]}" />;"><c:out value="${user.id}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[1]}" />;"><c:out value="${user.login}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[2]}" />;"><c:out value="${user.email}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[3]}" />;"><c:out value="${status_name}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[4]}" />;"><c:out value="${user.available_functions}" /></td>
			<td class="Action"><a href="/<c:out value="${page.module}" />?action=edit&id=<c:out value="${user.id}"/>"><img alt="edit" src="images/edit.png" title="Edytuj"></a></td>
		</tr>
	</c:forEach>
	
	<jsp:include page="/jsp/templates/noresults.jsp">
		<jsp:param name="columns_count" value="${columns_count}" />
	</jsp:include>

</table>

</p>

<jsp:include page="/jsp/templates/paginator.jsp" />
