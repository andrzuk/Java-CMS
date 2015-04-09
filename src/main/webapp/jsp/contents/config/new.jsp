<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=new" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/new.png" /></span>
				<span class="Title">Dodaj ustawienie</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Key Name:</td>
			<td><input type="text" name="key_name"></td>
		</tr>
		<tr class="FormRow">
			<td>Key Value:</td>
			<td><textarea name="key_value" rows="10"></textarea></td>
		</tr>
		<tr class="FormRow">
			<td>Meaning:</td>
			<td><input type="text" name="meaning"></td>
		</tr>
		<tr class="FormRow">
			<td>Field Type:</td>
			<td>
				<select name="field_type">
					<option value="1">Short Text</option>
					<option value="2" selected="selected">Long Text</option>
					<option value="3">Boolean</option>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Active:</td>
			<td>
				<input type="radio" id="option_1" name="active" value="active" checked="checked"> <label for="option_1">Tak</label>
				&nbsp;
				<input type="radio" id="option_2" name="active" value="passive"> <label for="option_2">Nie</label>
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
