<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>

<table align="center" width="300" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/login.png" /></span>
				<span class="Title">Logowanie do serwisu</span>
			</td>
		</tr>
		<tr class="Up">
			<td>Login (e-mail):</td>
			<td><input type="text" name="login" required></td>
		</tr>
		<tr class="Down">
			<td>Hasło:</td>
			<td><input type="password" name="password" required></td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<input type="submit" name="login" value="Zaloguj">
			</td>
		</tr>
	</form>

</table>

</p>
