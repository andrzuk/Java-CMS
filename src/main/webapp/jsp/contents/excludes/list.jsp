<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/visitors"><img alt="visitors" src="images/page_edit.png" title="Odwiedziny"></a> &nbsp; <a href="/admin"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">
	
	<tr class="ColumnHeaders">
		<td colspan="2">
			<span class="Icon"><img src="images/modify.png" /></span>
			<span class="Title">Edytuj wykluczenia</span>
		</td>
	</tr>
	<tr class="ColumnHeaders">
		<td>Visitor IP</td>
		<td>Active</td>
	</tr>
	
	<form action="/excludes" method="post">
		<c:forEach items="${data}" var="exclude" varStatus="iterator">
			<tr class="${iterator.index % 2 == 0 ? 'even' : 'odd'}">
				<td><input type="text" value="<c:out value="${exclude.visitor_ip}" />" name="ip_<c:out value="${exclude.id}" />" /></td>
				<td>
					<c:choose>
						<c:when test="${exclude.active == true}">
							<input type="radio" id="option_1_<c:out value="${exclude.id}" />" name="ac_<c:out value="${exclude.id}" />" value="1" checked="checked"> <label for="option_1_<c:out value="${exclude.id}" />">Tak</label>
							&nbsp;
							<input type="radio" id="option_2_<c:out value="${exclude.id}" />" name="ac_<c:out value="${exclude.id}" />" value="0"> <label for="option_2_<c:out value="${exclude.id}" />">Nie</label>
						</c:when>
						<c:otherwise>
							<input type="radio" id="option_1_<c:out value="${exclude.id}" />" name="ac_<c:out value="${exclude.id}" />" value="1"> <label for="option_1_<c:out value="${exclude.id}" />">Tak</label>
							&nbsp;
							<input type="radio" id="option_2_<c:out value="${exclude.id}" />" name="ac_<c:out value="${exclude.id}" />" value="0" checked="checked"> <label for="option_2_<c:out value="${exclude.id}" />">Nie</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td><input type="text" value="" name="ip_new" /></td>
			<td>
				<input type="radio" id="option_new_1" name="ac_new" value="1" checked="checked"> <label for="option_new_1">Tak</label>
				&nbsp;
				<input type="radio" id="option_new_2" name="ac_new" value="0"> <label for="option_new_2">Nie</label>
			</td>
		</tr>
		<tr>
			<td class="ActionBar" colspan="2" style="text-align: right;">
				<input type="submit" name="confirm" value="Update">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>

</table>

</p>
