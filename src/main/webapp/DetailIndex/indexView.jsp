<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- request attributes: totalPosts, totalYears, totalCosplays (set by IndexPageService, default to 0 when not set) --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="landing">
    <div class="landing-mark">衣</div>
    <h1>코스 아카이브</h1>
    <p>지금까지 해온 코스프레와 취미 활동을<br>연도별로 기록하고 관리하는 개인 블로그</p>
    <div class="landing-stats">
      <div><b><c:out value="${totalPosts}" default="0"/></b>기록</div>
      <div><b><c:out value="${totalYears}" default="0"/></b>활동 연도</div>
      <div><b><c:out value="${totalCosplays}" default="0"/></b>코스프레</div>
    </div>
    <a class="btn-enter" href="${pageContext.request.contextPath}/Archive?cmd=archiveList">입장하기</a>
  </div>
</body>
</html>
