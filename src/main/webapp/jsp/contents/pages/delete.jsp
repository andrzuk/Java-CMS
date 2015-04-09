<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=delete" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/delete.png" /></span>
				<span class="Title">Usuń stronę</span>
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
			<td>Visible:</td>
			<td>
				<c:if test="${site.visible == 'true'}">Yes (normal)</c:if>
				<c:if test="${site.visible == 'false'}">No (locked)</c:if>
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
				<input type="submit" name="confirm" value="Delete">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>
	
</table>

</p>
