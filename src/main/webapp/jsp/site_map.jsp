<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<?xml version="1.0" encoding="UTF-8"?>
<urlset
      xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9
            http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
<!-- created by Auto Sitemap Generator of Java CMS Application http://<c:out value="${base_url}" /> -->

	<url>
		<loc>http://cms-jfrmwrk.rhcloud.com</loc>
	</url>
	<url>
		<loc>http://cms-jfrmwrk.rhcloud.com/login</loc>
	</url>
	<url>
		<loc>http://cms-jfrmwrk.rhcloud.com/contact</loc>
	</url>

<c:forEach items="${categories}" var="category">
	<url>
		<loc>http://<c:out value="${base_url}" />/category?id=<c:out value="${category.id}" /></loc>
	</url>
</c:forEach>

<c:forEach items="${sites}" var="site">
	<url>
		<loc>http://<c:out value="${base_url}" />/page?id=<c:out value="${site.id}" /></loc>
	</url>
</c:forEach>

</urlset>
