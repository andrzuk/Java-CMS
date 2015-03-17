<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=delete" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/delete.png" /></span>
				<span class="Title">Usuń użytkownika</span>
			</td>
		</tr>
		<tr>
			<td>Id:</td>
			<td><c:out value="${user.id}" /><input type="hidden" name="id" value="<c:out value="${user.id}" />"></td>
		</tr>
		<tr>
			<td>First Name:</td>
			<td><c:out value="${user.first_name}" /></td>
		</tr>
		<tr>
			<td>Last Name:</td>
			<td><c:out value="${user.last_name}" /></td>
		</tr>
		<tr>
			<td>E-mail:</td>
			<td><c:out value="${user.email}" /></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td><c:out value="${user.login}" /></td>
		</tr>
		<tr>
			<td>Status:</td>
			<td>
				<c:if test="${user.status == 1}">Administrator</c:if>
				<c:if test="${user.status == 2}">Operator</c:if>
				<c:if test="${user.status == 3}">User</c:if>
			</td>
		</tr>
		<tr>
			<td>Active:</td>
			<td>
				<c:if test="${user.active == 'true'}">Yes (normal)</c:if>
				<c:if test="${user.active == 'false'}">No (locked)</c:if>
			</td>
		</tr>
		<tr>
			<td>Modified:</td>
			<td><c:out value="${user.modified_short}" /></td>
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
