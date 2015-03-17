<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=delete" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/delete.png" /></span>
				<span class="Title">Usuń komunikat</span>
			</td>
		</tr>
		<tr>
			<td>Id:</td>
			<td><c:out value="${user_message.id}" /><input type="hidden" name="id" value="<c:out value="${user_message.id}" />"></td>
		</tr>
		<tr>
			<td>Message:</td>
			<td><c:out value="${user_message.message}" /></td>
		</tr>
		<tr>
			<td>Visible:</td>
			<td>
				<c:if test="${user_message.visible == 'true'}">Yes (normal)</c:if>
				<c:if test="${user_message.visible == 'false'}">No (locked)</c:if>
			</td>
		</tr>
		<tr>
			<td>Author:</td>
			<td><c:out value="${user_message.nick}" /> ~ <a href="mailto:<c:out value="${user_message.email}" />"><c:out value="${user_message.email}" /></a></td>
		</tr>
		<tr>
			<td>Modified:</td>
			<td><c:out value="${user_message.modified_short}" /></td>
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
