<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attribute, set when a card is clicked):
    plan - PlanDTO: getPlan_id, getPlan_file, getPlan_name, getPlan_comment,
           getPlan_switch, getPlan_day
  수정/삭제는 여기가 아니라 plan.jsp의 카드에서 합니다(wish와 동일한 위치).
--%>
<aside class="aside-col">
  <c:choose>
    <c:when test="${not empty plan}">
      <c:if test="${not empty plan.plan_file}">
        <div class="detail-photo">
          <img src="${pageContext.request.contextPath}/Plan/upload/${plan.plan_file}" alt="${plan.plan_name}" style="width:100%;height:100%;object-fit:cover;display:block">
        </div>
      </c:if>
      <h1 style="font-size:24px;font-weight:900;margin:0 0 10px">${plan.plan_name}</h1>
      <p style="font-size:16px;line-height:1.8;color:var(--text);margin:0 0 16px;white-space:pre-line">${plan.plan_comment}</p>
      <div class="detail-meta">
        <span>요일</span><span>${plan.plan_day}</span>
        <span>상태</span><span>${plan.plan_switch}</span>
      </div>
    </c:when>

    <c:otherwise>
      <div class="detail-empty">게시물을 클릭하세요</div>
    </c:otherwise>
  </c:choose>
</aside>
