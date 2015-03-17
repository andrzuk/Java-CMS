<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>		
	<table align="center" width="300" cellpadding="15" cellspacing="0">
		<form action="/admin" method="post">
			<tr class="ColumnHeaders">
				<td colspan="2">
					<span class="Icon"><img src="images/lock.png" /></span>
					<span class="Title">Dostęp zabroniony</span>
				</td>
			</tr>
			<tr class="Dialog">
				<td><img src="images/no_entry.png" alt="" /></td>
				<td>Próbujesz uzyskać dostęp do zasobów, które wymagają posiadania odpowiednich uprawnień.</td>
			</tr>
			<tr>
				<td class="ActionBar" colspan="2">
					<input type="submit" name="cancel" value="Zaloguj">
				</td>
			</tr>
		</form>
	</table>
</p>
