<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:if test="${empty data}">
		<tr class="Noresults">
			<td colspan="<c:out value="${param.columns_count}" />">
				<img src="images/no_entry.png" alt="No results" title="No results" />
				<br />
				(Brak wyników wyszukiwania)
			</td>
		</tr>
	</c:if>
	