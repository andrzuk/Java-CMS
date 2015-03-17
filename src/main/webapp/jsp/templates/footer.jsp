<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<footer>
  <div class="footer"><c:out value="${page.main_footer}" escapeXml="false" /></div>
</footer>

<c:if test="${not empty page.highlight_theme}">
  <script src="assets/js/rainbow.min.js"></script>
  <script src="assets/js/language/generic.js"></script>
  <script src="assets/js/language/java.js"></script>
</c:if>
