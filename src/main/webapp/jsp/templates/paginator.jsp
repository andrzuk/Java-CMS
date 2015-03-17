<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>

<table align="center" width="100%" cellpadding="5" cellspacing="0" style="border: 0px dotted #fff;">
	<tr>
		<td style="text-align: center;">
			<form action="/<c:out value="${page.module}" />" method="post">
				<input type="submit" name="first" value="&lt;&lt; First" <c:if test="${page_index == 0}">disabled="disabled"</c:if> />
				&nbsp;
				<input type="submit" name="prev" value="&lt; Prev" <c:if test="${page_index == 0}">disabled="disabled"</c:if> />
				&nbsp;
				<c:forEach items="${page_buttons}" var="idx">
					<c:choose>
    					<c:when test="${idx == page_index + 1}">
       						<input type="button" name="page_<c:out value="${idx}" />" value="<c:out value="${idx}" />" class="Current" />
    					</c:when>
    					<c:otherwise>
        					<input type="submit" name="page_<c:out value="${idx}" />" value="<c:out value="${idx}" />" />
    					</c:otherwise>
					</c:choose>
					&nbsp;
				</c:forEach>
				<input type="submit" name="next" value="Next &gt;" <c:if test="${page_index >= pages_count - 1}">disabled="disabled"</c:if> />
				&nbsp;
				<input type="submit" name="last" value="Last &gt;&gt;" <c:if test="${page_index >= pages_count - 1}">disabled="disabled"</c:if> />
			</form>
		</td>
	</tr>
	<tr>
		<td style="text-align: center;">
			<form action="/<c:out value="${page.module}" />" method="post">
				Wierszy:
				<select name="lines_on_page" class="PageLines" onchange="submit()">
					<option value="10" <c:if test="${rows_per_page == 10}">selected="selected"</c:if> >10</option>
					<option value="15" <c:if test="${rows_per_page == 15}">selected="selected"</c:if> >15</option>
					<option value="20" <c:if test="${rows_per_page == 20}">selected="selected"</c:if> >20</option>
					<option value="50" <c:if test="${rows_per_page == 50}">selected="selected"</c:if> >50</option>
					<option value="100" <c:if test="${rows_per_page == 100}">selected="selected"</c:if> >100</option>
				</select>
				&nbsp; &nbsp;
				Pozycji: <b><c:out value="${rows_count_str}" /></b>
				&nbsp; &nbsp;
				Stron: <b><c:out value="${pages_count_str}" /></b>
				&nbsp; &nbsp;
				Strona: <input type="text" name="goto_page" class="Goto" /><input type="submit" name="goto_button" value="Idź" />
			</form>
		</td>
	</tr>
</table>

</p>

