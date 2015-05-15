<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />?action=restore&id=<c:out value="${site.id}" />"><img alt="close" src="images/archives.png" title="Przywróć"></a> &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="90%" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj stronę</span>
			</td>
		</tr>
		<input type="hidden" name="type" value="<c:out value="${site.type}" />">
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${site.id}" /><input type="hidden" name="id" value="<c:out value="${site.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Title:</td>
			<td><input type="text" name="title" value="<c:out value="${site.title}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Category:</td>
			<td>
				<select name="category_id">
					<c:forEach items="${imported}" var="category">
						<c:choose>
							<c:when test="${category.id == site.category_id}">
								<c:set var="selected" value="selected='selected'" />
							</c:when>
							<c:otherwise>
								<c:set var="selected" value="" />
							</c:otherwise>
						</c:choose>
						<option value="<c:out value="${category.id}" />" <c:out value="${selected}" />><c:out value="${category.caption}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td width="10%">Contents:</td>
			<td width="90%"><textarea name="contents" rows="20"><c:out value="${site.contents}" /></textarea></td>
		</tr>
		<tr class="FormRow">
			<td>Description:</td>
			<td><input type="text" name="description" value="<c:out value="${site.description}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Visible:</td>
			<td>
				<c:choose>
					<c:when test="${site.visible == true}">
						<input type="radio" id="option_1" name="visible" value="active" checked="checked"> <label for="option_1">Tak</label>
						&nbsp;
						<input type="radio" id="option_2" name="visible" value="passive"> <label for="option_2">Nie</label>
					</c:when>
					<c:otherwise>
						<input type="radio" id="option_1" name="visible" value="active"> <label for="option_1">Tak</label>
						&nbsp;
						<input type="radio" id="option_2" name="visible" value="passive" checked="checked"> <label for="option_2">Nie</label>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Author:</td>
			<td><c:out value="${site.login}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${site.modified_short}" /></td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<input type="submit" name="confirm" value="Update">
				&nbsp;
				<input type="submit" name="confirm" value="Archive">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>
	
</table>

</p>
