<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj ustawienie</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${config.id}" /><input type="hidden" name="id" value="<c:out value="${config.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Key Name:</td>
			<td><input type="text" name="key_name" value="<c:out value="${config.key_name}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Key Value:</td>
			<td>
				<c:choose>
					<c:when test="${config.field_type == 1}">
						<input type="text" name="key_value" value="<c:out value="${config.key_value}" />">
					</c:when>
					<c:when test="${config.field_type == 2}">
						<textarea name="key_value" rows="10"><c:out value="${config.key_value}" /></textarea>
					</c:when>
					<c:when test="${config.field_type == 3}">
						<c:choose>
							<c:when test="${config.key_value == true}">
								<input type="radio" id="key_value_1" name="key_value" value="true" checked="checked"> <label for="key_value_1">True</label>
								&nbsp;
								<input type="radio" id="key_value_2" name="key_value" value="false"> <label for="key_value_2">False</label>
							</c:when>
							<c:otherwise>
								<input type="radio" id="key_value_1" name="key_value" value="true"> <label for="key_value_1">True</label>
								&nbsp;
								<input type="radio" id="key_value_2" name="key_value" value="false" checked="checked"> <label for="key_value_2">False</label>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:out value="${config.key_value}" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Meaning:</td>
			<td><input type="text" name="meaning" value="<c:out value="${config.meaning}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Field Type:</td>
			<td>
				<select name="field_type">
					<option value="1" <c:if test="${config.field_type == 1}">selected="selected"</c:if>>Short Text</option>
					<option value="2" <c:if test="${config.field_type == 2}">selected="selected"</c:if>>Long Text</option>
					<option value="3" <c:if test="${config.field_type == 3}">selected="selected"</c:if>>Boolean</option>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Active:</td>
			<td>
				<c:choose>
					<c:when test="${config.active == true}">
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
			<td><c:out value="${config.modified_short}" /></td>
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
