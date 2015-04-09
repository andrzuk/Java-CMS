<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="90%" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=new" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/new.png" /></span>
				<span class="Title">Dodaj stronę</span>
			</td>
		</tr>
		<input type="hidden" name="type" value="standard" />
		<tr class="FormRow">
			<td>Title:</td>
			<td><input type="text" name="title"></td>
		</tr>
		<tr class="FormRow">
			<td>Category:</td>
			<td>
				<select name="category_id">
					<c:forEach items="${imported}" var="category">
						<option value="<c:out value="${category.id}" />"><c:out value="${category.caption}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td width="10%">Contents:</td>
			<td width="90%"><textarea name="contents" rows="20"></textarea></td>
		</tr>
		<tr class="FormRow">
			<td>Description:</td>
			<td><input type="text" name="description"></td>
		</tr>
		<tr class="FormRow">
			<td>Visible:</td>
			<td>
				<input type="radio" id="option_1" name="visible" value="active" checked="checked"> <label for="option_1">Tak</label>
				&nbsp;
				<input type="radio" id="option_2" name="visible" value="passive"> <label for="option_2">Nie</label>
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
