<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=new" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/new.png" /></span>
				<span class="Title">Dodaj użytkownika</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>First Name:</td>
			<td><input type="text" name="first_name"></td>
		</tr>
		<tr class="FormRow">
			<td>Last Name:</td>
			<td><input type="text" name="last_name"></td>
		</tr>
		<tr class="FormRow">
			<td>E-mail:</td>
			<td><input type="text" name="email"></td>
		</tr>
		<tr class="FormRow">
			<td>Login:</td>
			<td><input type="text" name="login"></td>
		</tr>
		<c:choose>
			<c:when test="${operator == 1}">
				<tr class="FormRow">
					<td>Status:</td>
					<td>
						<select name="status">
							<option value="1">Administrator</option>
							<option value="2">Operator</option>
							<option value="3" selected="selected">User</option>
						</select>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="status" value="3" />
			</c:otherwise>
		</c:choose>
		<tr class="FormRow">
			<td>Active:</td>
			<td>
				<input type="radio" id="option_1" name="active" value="active" checked="checked"> <label for="option_1">Tak</label>
				&nbsp;
				<input type="radio" id="option_2" name="activee" value="passive"> <label for="option_2">Nie</label>
			</td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<input type="submit" name="confirm" value="Register">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>

</table>

</p>
