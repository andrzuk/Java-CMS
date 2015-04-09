<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />?action=new" method="post" enctype="multipart/form-data">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/new.png" /></span>
				<span class="Title">Dodaj obrazek</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Get Picture:</td>
			<td><input type="file" name="uploadFile" /></td>
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
