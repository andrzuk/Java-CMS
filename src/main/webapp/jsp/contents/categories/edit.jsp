<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj kategorię</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${category.id}" /><input type="hidden" name="id" value="<c:out value="${category.id}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Parent:</td>
			<td>
				<select name="parent_id">
					<option value="0">None (Root)</option>
					<c:forEach items="${imported}" var="parent">
						<c:choose>
							<c:when test="${parent.id == category.parent_id}">
								<c:set var="selected" value="selected='selected'" />
							</c:when>
							<c:otherwise>
								<c:set var="selected" value="" />
							</c:otherwise>
						</c:choose>
						<option value="<c:out value="${parent.id}" />" <c:out value="${selected}" />><c:out value="${parent.caption}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Caption:</td>
			<td><input type="text" name="caption" value="<c:out value="${category.caption}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Link:</td>
			<td><input type="text" name="link" value="<c:out value="${category.link}" />"></td>
		</tr>
		<tr class="FormRow">
			<td>Order:</td>
			<td>
				<select name="item_order">
					<option value="0">0</option>
					<c:forEach items="${imported}" var="idx" varStatus="iterator">
						<c:choose>
							<c:when test="${iterator.index == category.item_order - 1}">
								<c:set var="selected" value="selected='selected'" />
							</c:when>
							<c:otherwise>
								<c:set var="selected" value="" />
							</c:otherwise>
						</c:choose>
						<option value="<c:out value="${iterator.index + 1}" />" <c:out value="${selected}" />><c:out value="${iterator.index + 1}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Visible:</td>
			<td>
				<c:choose>
					<c:when test="${category.visible == true}">
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
			<td><c:out value="${category.login}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Modified:</td>
			<td><c:out value="${category.modified_short}" /></td>
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
