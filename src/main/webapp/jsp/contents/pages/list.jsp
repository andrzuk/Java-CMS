<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=new"><img alt="new" src="images/add.png" title="Dodaj nową pozycję"></a> &nbsp; <a href="/admin"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="95%" cellpadding="5" cellspacing="0">

	<c:set var="columns_count" value="8" />
	<c:set var="actions_count" value="2" />
	
	<jsp:include page="/jsp/templates/sort.jsp">
		<jsp:param name="sorting" value="${sorting}" />
	</jsp:include>

	<jsp:include page="/jsp/templates/titlebar.jsp">
		<jsp:param name="title_image" value="images/page.png" />
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
	
	<c:forEach items="${data}" var="site" varStatus="iterator">
		<c:choose>
			<c:when test="${site.visible == 'true'}">
				<c:set var="style_name" value="active" />
			</c:when>
			<c:otherwise>
				<c:set var="style_name" value="passive" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${fn:toLowerCase(site.caption) == 'index'}">
				<c:set var="add_style" value="index" />
				<c:set var="item_icon" value="<img src='images/home.png' title='Strona główna' />" />
			</c:when>
			<c:when test="${fn:toLowerCase(site.caption) == 'contact'}">
				<c:set var="add_style" value="contact" />
				<c:set var="item_icon" value="<img src='images/contact.png' title='Strona kontaktowa' />" />
			</c:when>
			<c:otherwise>
				<c:set var="add_style" value="" />
				<c:set var="item_icon" value="" />
			</c:otherwise>
		</c:choose>

		<tr class="${iterator.index % 2 == 0 ? 'even' : 'odd'} <c:out value="${style_name} ${add_style}" />">
			<td style="text-align: <c:out value="${sorting.fields_aligns[0]}" />;"><c:out value="${site.id}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[1]}" />;"><c:out value="${site.caption}" /><div><c:out value="${item_icon}" escapeXml="false" /></div></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[2]}" />;"><c:out value="${site.title}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[3]}" />;"><c:out value="${site.description}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[4]}" />;"><c:out value="${site.login}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[5]}" />;"><c:out value="${site.modified_short}" /></td>
			<td class="Action"><a href="/<c:out value="${page.module}" />?action=edit&id=<c:out value="${site.id}"/>"><img alt="edit" src="images/edit.png" title="Edytuj"></a></td>
			<td class="Action"><a href="/<c:out value="${page.module}" />?action=delete&id=<c:out value="${site.id}"/>"><img alt="delete" src="images/trash.png" title="Usuń"></a></td>
		</tr>
	</c:forEach>
	
	<jsp:include page="/jsp/templates/noresults.jsp">
		<jsp:param name="columns_count" value="${columns_count}" />
	</jsp:include>

</table>

</p>

<jsp:include page="/jsp/templates/paginator.jsp" />
