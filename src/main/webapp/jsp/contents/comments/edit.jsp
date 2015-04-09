<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj komentarz</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${comment.id}" /><input type="hidden" name="id" value="<c:out value="${comment.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Title:</td>
			<td><c:out value="${comment.title}" /><input type="hidden" name="page_id" value="<c:out value="${comment.page_id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Comment:</td>
			<td><textarea name="comment" rows="10"><c:out value="${comment.comment}" /></textarea></td>
		</tr>
		<tr class="FormRow">
			<td>Accepted:</td>
			<td>
				<c:choose>
					<c:when test="${comment.visible == true}">
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
			<td>Address IP:</td>
			<td><c:out value="${comment.ip}" /><input type="hidden" name="ip" value="<c:out value="${comment.ip}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Author:</td>
			<td>
				<c:out value="${comment.nick}" /> ~ <a href="mailto:<c:out value="${comment.email}" />"><c:out value="${comment.email}" /></a>
				<input type="hidden" name="nick" value="<c:out value="${comment.nick}" />">
				<input type="hidden" name="email" value="<c:out value="${comment.email}" />">
			</td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${comment.modified_short}" /></td>
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
