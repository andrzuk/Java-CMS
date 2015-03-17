<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:if test="${not empty filter}">
		<tr class="Showfilter">
			<td colspan="<c:out value="${param.columns_count}" />">
				<form action="/<c:out value="${page.module}" />?action=clear" method="post">
					Filtr: <a class="Filter"><c:out value="${filter}" /></a> 
					<input type="hidden" name="clear_search" value="true" />
					<input type="image" src="images/stop.png" title="Wyłącz filtr" class="StopFilter" />
				</form>
			</td>
		</tr>
	</c:if>
