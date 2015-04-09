<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=new" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/new.png" /></span>
				<span class="Title">Dodaj kategorię</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Parent:</td>
			<td>
				<select name="parent_id">
					<option value="0">None (Root)</option>
					<c:forEach items="${imported}" var="category">
						<option value="<c:out value="${category.id}" />"><c:out value="${category.caption}" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Caption:</td>
			<td><input type="text" name="caption"></td>
		</tr>
		<tr class="FormRow">
			<td>Link:</td>
			<td><input type="text" name="link" value="/category?id=(auto)"></td>
		</tr>
		<tr class="FormRow">
			<td>Order:</td>
			<td>
				<select name="item_order">
					<option value="0">0</option>
					<c:forEach items="${imported}" var="category" varStatus="iterator">
						<option value="<c:out value="${iterator.index + 1}" />"><c:out value="${iterator.index + 1}" /></option>
					</c:forEach>
				</select>
			</td>
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
