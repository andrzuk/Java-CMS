<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=new"><img alt="new" src="images/add.png" title="Dodaj nową pozycję"></a> &nbsp; <a href="/admin"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="95%" cellpadding="5" cellspacing="0">

	<c:set var="columns_count" value="11" />
	<c:set var="actions_count" value="3" />
	
	<jsp:include page="/jsp/templates/sort.jsp">
		<jsp:param name="sorting" value="${sorting}" />
	</jsp:include>

	<jsp:include page="/jsp/templates/titlebar.jsp">
		<jsp:param name="title_image" value="images/picture.png" />
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
	
	<c:forEach items="${data}" var="image" varStatus="iterator">

		<fmt:parseNumber var="file_size_int" integerOnly="true" type="number" value="${image.file_size / 1024}" />
		<tr class="${iterator.index % 2 == 0 ? 'even' : 'odd'}">
			<td style="text-align: <c:out value="${sorting.fields_aligns[0]}" />;"><c:out value="${image.id}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[1]}" />;"><a href="/<c:out value="${page.module}" />?action=preview&id=<c:out value="${image.id}"/>"><div class="dc"><img class="dynamic" src="upload/<c:out value="${image.file_name}" />" width="${preview.width}" height="${preview.height}" onload="showImage(this);" title="Podgląd" alt="" /></div></a></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[2]}" />;"><c:out value="${image.file_name}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[3]}" />;"><c:out value="${file_size_int} KB" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[4]}" />;"><c:out value="${image.width} px" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[5]}" />;"><c:out value="${image.height} px" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[6]}" />;"><c:out value="${image.login}" /></td>
			<td style="text-align: <c:out value="${sorting.fields_aligns[7]}" />;"><c:out value="${image.modified_short}" /></td>
			<td class="Action"><a href="/upload/<c:out value="${image.file_name}"/>"><img alt="download" src="images/save.png" title="Pobierz"></a></td>
			<td class="Action"><a href="/<c:out value="${page.module}" />?action=import&id=<c:out value="${image.id}"/>"><img alt="import" src="images/import.png" title="Importuj"></a></td>
			<td class="Action"><a href="/<c:out value="${page.module}" />?action=delete&id=<c:out value="${image.id}"/>"><img alt="delete" src="images/trash.png" title="Usuń"></a></td>
		</tr>
	</c:forEach>
	
	<jsp:include page="/jsp/templates/noresults.jsp">
		<jsp:param name="columns_count" value="${columns_count}" />
	</jsp:include>

</table>

</p>

<jsp:include page="/jsp/templates/paginator.jsp" />
