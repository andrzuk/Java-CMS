<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<tr class="ListHeader">
		<td colspan="<c:out value="${param.columns_count}" />">
			<table width="100%" cellpadding="0" cellspacing="0" style="border: 0px dotted #fff;">
				<tr>
					<td class="Icon"><img src="<c:out value="${param.title_image}" />" /></td>
					<td class="Title">${page['title']}</td>
					<td class="Search">
						<form action="/<c:out value="${page.module}" />?action=search" method="post">
							<input type="text" name="search_value" />
							<input type="submit" name="search_button" value="Szukaj" />
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	