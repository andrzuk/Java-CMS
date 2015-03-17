<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=edit&id=<c:out value="${user.id}" />"><img alt="edit" src="images/edit.png" title="Edytuj"></a> &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="300" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=password" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/password.png" /></span>
				<span class="Title">Ustaw hasło</span>
			</td>
		</tr>
		<tr>
			<td>Id:</td>
			<td><c:out value="${user.id}" /><input type="hidden" name="id" value="<c:out value="${user.id}" />"></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td><c:out value="${user.login}" /><input type="hidden" name="login" value="<c:out value="${user.login}" />"></td>
		</tr>
		<tr>
			<td>New password:</td>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<td>Repeat password:</td>
			<td><input type="password" name="repeat" value="" /></td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<input type="submit" name="confirm" value="Update">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>
	
</table>

</p>
