<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- expects: request attribute "activeMenu" (one of home/archive/event/wish/plan/info),
     session attribute "loginId" (String, null when logged out) --%>
<scirpt>

</scirpt>
<nav class="sidebar">
  <div class="brand">
    <div class="brand-mark">衣</div>
    <b class="brand-name">코스 아카이브</b>
  </div>
  <a class="nav-btn${activeMenu == 'home' ? ' active' : ''}" href="${pageContext.request.contextPath}/Archive?cmd=archiveList">🏠 홈</a>
  <a class="nav-btn${activeMenu == 'event' ? ' active' : ''}" href="${pageContext.request.contextPath}/Event?cmd=eventList">🎪 행사</a>
  <a class="nav-btn${activeMenu == 'wish' ? ' active' : ''}" href="${pageContext.request.contextPath}/Wish?cmd=wishList">⭐ 위시</a>
  <a class="nav-btn${activeMenu == 'plan' ? ' active' : ''}" href="${pageContext.request.contextPath}/Plan?cmd=planList">📝 플랜</a>
  <a class="nav-btn${activeMenu == 'info' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoList">ℹ️ 정보</a>

	<c:choose>
      <c:when test="${not empty sessionScope.loginId}">
           <a class="btn-auth" name="btn_entry" id="btn_entry">✏️ 게시하기</a>
      </c:when>
      <c:otherwise>
        <a class="btn-auth" href="${pageContext.request.contextPath}/Cos?cmd=login">🔐 로그인</a>
      </c:otherwise>
    </c:choose>

  <div class="profile-row">
    <div class="avatar-sm"></div>
    <div style="flex:1;min-width:0">
      <div class="profile-name"><b>나의 기록</b></div>
      <div class="profile-handle">@cos_archive</div>
    </div>
    <c:if test="${not empty sessionScope.loginId}">
      <form method="post" action="${pageContext.request.contextPath}/Cos?cmd=logout" style="margin:0">
        <button type="submit" class="logout-link">로그아웃</button>
      </form>
    </c:if>
  </div>
</nav>
