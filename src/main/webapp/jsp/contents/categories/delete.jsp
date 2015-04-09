<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=delete" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/delete.png" /></span>
				<span class="Title">Usuń kategorię</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${category.id}" /><input type="hidden" name="id" value="<c:out value="${category.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Caption:</td>
			<td><c:out value="${category.caption}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Link:</td>
			<td><c:out value="${category.link}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Visible:</td>
			<td>
				<c:if test="${category.visible == 'true'}">Yes (normal)</c:if>
				<c:if test="${category.visible == 'false'}">No (locked)</c:if>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Author:</td>
			<td><c:out value="${category.login}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${category.modified_short}" /></td>
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
