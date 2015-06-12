<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/<c:out value="${page.module}" />"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">

	<form action="/<c:out value="${page.module}" />" method="post">
		<tr class="ColumnHeaders">
			<td colspan="2">
				<span class="Icon"><img src="images/list_checked.png" /></span>
				<span class="Title">Szczegóły odwiedzin</span>
			</td>
		</tr>
		<tr class="FormRow">
			<td>Id:</td>
			<td><c:out value="${visitor.id}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Visitor IP:</td>
			<td><c:out value="${visitor.visitor_ip}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Host Name:</td>
			<td><c:out value="${visitor.host_name}" /></td>
		</tr>
		<tr class="FormRow">
			<td>HTTP Referer:</td>
			<td><a href="<c:out value="${visitor.http_referer}" />" target="_blank"><c:out value="${visitor.http_referer_split}" /></a></td>
		</tr>
		<tr class="FormRow">
			<td>Request URI:</td>
			<td><c:out value="${visitor.request_uri}" /></td>
		</tr>
		<tr class="FormRow">
			<td>Visited:</td>
			<td><c:out value="${visitor.visited_short}" /></td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2">
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>
	
</table>

</p>
