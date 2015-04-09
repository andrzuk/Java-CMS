<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=password&id=<c:out value="${user.id}" />"><img alt="password" src="images/access.png" title="Zmień hasło"></a> &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj użytkownika</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${user.id}" /><input type="hidden" name="id" value="<c:out value="${user.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>First Name:</td>
			<td><input type="text" name="first_name" value="<c:out value="${user.first_name}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Last Name:</td>
			<td><input type="text" name="last_name" value="<c:out value="${user.last_name}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>E-mail:</td>
			<td><input type="text" name="email" value="<c:out value="${user.email}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Login:</td>
			<td><input type="text" name="login" value="<c:out value="${user.login}" />"></td>
		</tr>
		<c:choose>
			<c:when test="${operator == 1}">
				<tr class="FormRow">
					<td>Status:</td>
					<td>
						<select name="status">
							<option value="1" <c:if test="${user.status == 1}">selected="selected"</c:if>>Administrator</option>
							<option value="2" <c:if test="${user.status == 2}">selected="selected"</c:if>>Operator</option>
							<option value="3" <c:if test="${user.status == 3}">selected="selected"</c:if>>User</option>
						</select>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="status" value="<c:out value="${user.status}" />" />
			</c:otherwise>
		</c:choose>
		<tr class="FormRow">
			<td>Active:</td>
			<td>
				<c:choose>
					<c:when test="${user.active == true}">
						<input type="radio" id="option_1" name="active" value="active" checked="checked"> <label for="option_1">Tak</label>
						&nbsp;
						<input type="radio" id="option_2" name="active" value="passive"> <label for="option_2">Nie</label>
					</c:when>
					<c:otherwise>
						<input type="radio" id="option_1" name="active" value="active"> <label for="option_1">Tak</label>
						&nbsp;
						<input type="radio" id="option_2" name="active" value="passive" checked="checked"> <label for="option_2">Nie</label>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${user.modified_short}" /></td>
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
