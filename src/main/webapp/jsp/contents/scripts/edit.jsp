<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/admin"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="90%" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/modify.png" /></span>
				<span class="Title">Edytuj skrypty</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>File:</td>
			<td><input type="hidden" name="filename" value="<c:out value="${filename}" />"><c:out value="${shortname}" /></td>
		</tr>
		<tr class="FormRow">
			<td width="10%">Contents:</td>
			<td width="90%"><textarea name="contents" rows="30"><c:out value="${script}" /></textarea></td>
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
