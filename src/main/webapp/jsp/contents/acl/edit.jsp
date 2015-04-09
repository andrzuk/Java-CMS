<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/acl"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<p>

<table align="center" width="400" cellpadding="5" cellspacing="0">
	
	<tr class="ColumnHeaders">
		<td colspan="3">
			<span class="Icon"><img src="images/modify.png" /></span>
			<span class="Title">Edytuj ACL</span>
		</td>
	</tr>
	<tr class="FormRow">
		<td>Login:</td>
		<td colspan="2"><c:out value="${user.login}" /></td>
	</tr>
	<tr class="FormRow">
		<td>Name:</td>
		<td colspan="2"><c:out value="${user.first_name}" /> <c:out value="${user.last_name}" /></td>
	</tr>
	<tr class="FormRow">
		<td>Status:</td>
		<td colspan="2">
			<c:if test="${user.status == 1}">Admin</c:if>
			<c:if test="${user.status == 2}">Operator</c:if>
			<c:if test="${user.status == 3}">User</c:if>
		</td>
	</tr>
	<tr class="ColumnHeaders">
		<td class="Line">Module</td>
		<td class="Line">Function</td>
		<td class="Line">Access</td>
	</tr>
	
	<form action="/<c:out value="${page.module}" />?action=edit" method="post">
		<c:forEach items="${data}" var="acl" varStatus="iterator">
			<tr class="${iterator.index % 2 == 0 ? 'even' : 'odd'}">
				<td><input type="hidden" name="user_<c:out value="${acl.function_id}" />" value="${user_id}" /><c:out value="${acl.module}" /></td>
				<td><input type="hidden" name="func_<c:out value="${acl.function_id}" />" value="<c:out value="${acl.function_id}" />" /><c:out value="${acl.function_name}" /></td>
				<td>
					<c:choose>
						<c:when test="${acl.access == true}">
							<input type="radio" id="option_1_<c:out value="${acl.id}" />" name="accs_<c:out value="${acl.function_id}" />" value="1" checked="checked"> <label for="option_1_<c:out value="${acl.id}" />">Tak</label>
							&nbsp;
							<input type="radio" id="option_2_<c:out value="${acl.id}" />" name="accs_<c:out value="${acl.function_id}" />" value="0"> <label for="option_2_<c:out value="${acl.id}" />">Nie</label>
						</c:when>
						<c:otherwise>
							<input type="radio" id="option_1_<c:out value="${acl.id}" />" name="accs_<c:out value="${acl.function_id}" />" value="1"> <label for="option_1_<c:out value="${acl.id}" />">Tak</label>
							&nbsp;
							<input type="radio" id="option_2_<c:out value="${acl.id}" />" name="accs_<c:out value="${acl.function_id}" />" value="0" checked="checked"> <label for="option_2_<c:out value="${acl.id}" />">Nie</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td class="ActionBar" colspan="3" style="text-align: right;">
				<input type="submit" name="confirm" value="Update">
				&nbsp;
				<input type="submit" name="cancel" value="Cancel">
			</td>
		</tr>
	</form>

</table>

</p>
