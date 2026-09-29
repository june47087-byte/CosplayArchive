<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  info.jsp 구조를 참조해서 다시 짰습니다. 탭바 없이 지역 카드 4개(서울/부산/전라/기타)만
  있고, 카드는 아코디언처럼 펼쳐지지 않습니다 — 지역 이름을 클릭하면 페이지가 다시
  로드되면서 오른쪽 rightbar.jsp에 그 지역의 행사 목록이 뜹니다(수정/삭제도 거기서).
  카드 안의 "행사 추가"는 info의 btn_chat_toggle이랑 완전히 같은 패턴 — 로컬 토글로
  인라인 등록폼을 보여줍니다. event_region은 폼에 넣지 않고 등록 action URL의
  ®ion= 파라미터로 넘어갑니다(그 카드 소속이니까 자동).

  expects (request attributes):
    region    - (for rightbar.jsp) 현재 선택된 지역, 없으면 안내 문구만
    eventList - (for rightbar.jsp) List<EventDTO>, region이 있을 때만
  request attribute "activeMenu"는 "event"로 설정하고 진입시켜주세요

  expected URLs (컨트롤러에서 구현):
    GET  Event?cmd=eventList&region=서울           - 그 지역 목록을 rightbar에 채워서 진입
    POST Event?cmd=eventWritePro&region=서울        - 새 행사 등록 (event_name/event_day/event_place)
    POST Event?cmd=eventModifyPro                   - 행사 수정 (rightbar.jsp에서, id/region 포함)
    GET  Event?cmd=eventDelete&id=5&region=서울      - 행사 삭제
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>행사 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('form[name=chat_entry]').hide();
		$(document).on('click', '[name=btn_chat_toggle]', function(){
			$(this).closest('.card').find('form[name=chat_entry]').toggle();
		});
		$(document).on('click', '[name=btn_save3]', function(){
			var $form = $(this).closest('form[name=chat_entry]');
			if($form.find('[name=event_name]').val() == ''){
				$form.find('[name=event_name]').focus();
				return;
			}
			if($form.find('[name=event_day]').val() == ''){
				$form.find('[name=event_day]').focus();
				return;
			}
			if($form.find('[name=event_place]').val() == ''){
				$form.find('[name=event_place]').focus();
				return;
			}
			$form.submit();
		});
		$(document).on('click', '[name=btn_cancel3]', function(){
			var $form = $(this).closest('form[name=chat_entry]');
			$form[0].reset();
			$form.hide();
		});
	});
</script>
</head>
<body>
<div class="app-shell">
  <jsp:include page="/common/sidebar.jsp" />

  <main class="main-col">
    <div class="page-header">
      <div class="page-header-row">
        <h2 class="page-title">🎪 행사</h2>
      </div>
    </div>

    <%-- 지역 4개는 고정값이라 EL 배열 리터럴(JSTL에 없음) 대신 그냥 4번 반복해서 적었습니다. --%>
    <div style="padding:20px;display:flex;flex-direction:column;gap:14px">
      <div class="card" style="padding:16px">
        <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
          <a href="${pageContext.request.contextPath}/Event?cmd=eventList&region=서울" style="color:inherit;text-decoration:none">서울</a>
        </div>
        <c:choose>
	      	<c:when test="${not empty sessionScope.loginId}">
	      		<div style="margin-top:8px">
	            	<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">행사 추가</a>
	          	</div>
	     	</c:when>
	  	  </c:choose>
        <div class="comment-list" style="margin-top:12px">
          <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Event?cmd=eventWritePro&region=서울">
            <input class="comment-input" type="text" name="event_name" placeholder="행사이름" required>
            <input class="comment-input" type="text" name="event_day" placeholder="행사 날짜 (예: 2026-09-12)" required><br>
            <input class="comment-input" type="text" name="event_place" placeholder="행사장소" required>
            <a class="btn-cancel" name="btn_cancel3">취소</a>
            <button type="submit" class="comment-submit" name="btn_save3">등록</button>
          </form>
        </div>
      </div>

      <div class="card" style="padding:16px">
        <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
          <a href="${pageContext.request.contextPath}/Event?cmd=eventList&region=부산" style="color:inherit;text-decoration:none">부산</a>
        </div>
        <c:choose>
	      	<c:when test="${not empty sessionScope.loginId}">
	      		<div style="margin-top:8px">
	            	<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">행사 추가</a>
	          	</div>
	     	</c:when>
	  	  </c:choose>
        <div class="comment-list" style="margin-top:12px">
          <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Event?cmd=eventWritePro&region=부산">
            <input class="comment-input" type="text" name="event_name" placeholder="행사이름" required>
            <input class="comment-input" type="text" name="event_day" placeholder="행사 날짜 (예: 2026-09-12)" required><br>
            <input class="comment-input" type="text" name="event_place" placeholder="행사장소" required>
            <a class="btn-cancel" name="btn_cancel3">취소</a>
            <button type="submit" class="comment-submit" name="btn_save3">등록</button>
          </form>
        </div>
      </div>

      <div class="card" style="padding:16px">
        <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
          <a href="${pageContext.request.contextPath}/Event?cmd=eventList&region=전라" style="color:inherit;text-decoration:none">전라</a>
        </div>
         <c:choose>
	      	<c:when test="${not empty sessionScope.loginId}">
	      		<div style="margin-top:8px">
	            	<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">행사 추가</a>
	          	</div>
	     	</c:when>
	  	  </c:choose>
        <div class="comment-list" style="margin-top:12px">
          <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Event?cmd=eventWritePro&region=전라">
            <input class="comment-input" type="text" name="event_name" placeholder="행사이름" required>
            <input class="comment-input" type="text" name="event_day" placeholder="행사 날짜 (예: 2026-09-12)" required><br>
            <input class="comment-input" type="text" name="event_place" placeholder="행사장소" required>
            <a class="btn-cancel" name="btn_cancel3">취소</a>
            <button type="submit" class="comment-submit" name="btn_save3">등록</button>
          </form>
        </div>
      </div>

      <div class="card" style="padding:16px">
        <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
          <a href="${pageContext.request.contextPath}/Event?cmd=eventList&region=기타" style="color:inherit;text-decoration:none">기타</a>
        </div>
    	<c:choose>
	      	<c:when test="${not empty sessionScope.loginId}">
	      		<div style="margin-top:8px">
	            	<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">행사 추가</a>
	          	</div>
	     	</c:when>
	  	  </c:choose>
        <div class="comment-list" style="margin-top:12px">
          <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Event?cmd=eventWritePro&region=기타">
            <input class="comment-input" type="text" name="event_name" placeholder="행사이름" required>
            <input class="comment-input" type="text" name="event_day" placeholder="행사 날짜 (예: 2026-09-12)" required><br>
            <input class="comment-input" type="text" name="event_place" placeholder="행사장소" required>
            <a class="btn-cancel" name="btn_cancel3">취소</a>
            <button type="submit" class="comment-submit" name="btn_save3">등록</button>
          </form>
        </div>
      </div>
    </div>
  </main>

  <jsp:include page="rightbar.jsp" />
</div>
</body>
</html>
