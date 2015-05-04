<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>		
	<table align="center" width="300" cellpadding="15" cellspacing="0">
		<form action="/" method="post">
			<tr class="ColumnHeaders">
				<td colspan="2">
					<span class="Icon"><img src="images/not_found.png" /></span>
					<span class="Title">Zasób nie znaleziony</span>
				</td>
			</tr>
			<tr class="Dialog">
				<td><img src="images/no_entry.png" alt="" /></td>
				<td>Strona lub kategoria, do której się odwołujesz, nie została znaleziona.</td>
			</tr>
			<tr>
				<td class="ActionBar" colspan="2">
					<input type="submit" name="cancel" value="Zamknij">
				</td>
			</tr>
		</form>
	</table>
</p>
