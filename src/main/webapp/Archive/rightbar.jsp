<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attribute, set when a card is clicked):
    picture - PictureDTO: getPic_id, getPic_name, getPic_file, getPhotographer,
              getPic_event, getPic_year

  참고: pic_file은 PictureWriteProService/PictureModifyProService가 저장하는 실제 경로
  (context.getRealPath("Archive/upload/"))에 맞춰서 contextPath + "/Archive/upload/" +
  파일명 으로 걸어뒀습니다.
--%>
<aside class="aside-col">
  <c:choose>
    <c:when test="${not empty picture}">
      <div class="detail-photo">
        <img src="${pageContext.request.contextPath}/Archive/upload/${picture.pic_file}" alt="${picture.pic_name}" style="width:100%;height:100%;object-fit:cover;display:block">
      </div>
      <h1 style="font-size:24px;font-weight:900;margin:0 0 10px">${picture.pic_name}</h1>
      <div class="detail-meta">
        <span>촬영자</span><span>${picture.photographer}</span>
        <span>행사</span><span>${picture.pic_event}</span>
        <span>연도</span><span>${picture.pic_year}년</span>
      </div>
    </c:when>

    <c:otherwise>
      <div class="detail-empty">게시물을 클릭하세요</div>
    </c:otherwise>
  </c:choose>
</aside>
