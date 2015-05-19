<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=edit&id=<c:out value="${site.id}" />"><img alt="edit" src="images/page_edit.png" title="Edytuj"></a> &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=restore" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/restore.png" /></span>
				<span class="Title">Przywróć stronę</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${site.id}" /><input type="hidden" name="id" value="<c:out value="${site.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Title:</td>
			<td><c:out value="${site.title}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Description:</td>
			<td><c:out value="${site.description}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Wersje:</td>
			<td>
				<c:forEach items="${data}" var="archive">
					<div>
						<label>
							<input type="radio" name="archive_id" value="<c:out value="${archive.id}" />" />
							<b><c:out value="${archive.modified_short}" /></b>
						</label>
					</div>
				</c:forEach>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Author:</td>
			<td><c:out value="${site.login}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${site.modified_short}" /></td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<c:choose>
					<c:when test="${empty data}">
						<c:set var="active_button" value="disabled" />
					</c:when>
					<c:otherwise>
						<c:set var="active_button" value="" />
					</c:otherwise>
				</c:choose>
				<input type="submit" name="confirm" value="Restore" <c:out value="${active_button}" />>
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>
	
</table>

</p>

