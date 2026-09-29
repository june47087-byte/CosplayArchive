<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attributes):
    region    - 현재 선택된 지역 (event.jsp에서 지역 이름 클릭 시 넘어옴), 없으면 안내 문구
    eventList - List<EventDTO> (getEvent_id/getEvent_name/getEvent_day/getEvent_place), region 있을 때만

  수정은 archive/info랑 같은 data-* + JS 패턴 — 서버 왕복 없이 목록에 이미 있는 값을
  수정 폼에 채워넣습니다. event_region은 수정 폼에서 안 바꿉니다(카드 소속을 옮기는
  기능은 없음) — hidden region 값은 저장 후 이 지역 목록으로 다시 돌아오기 위한 용도입니다.
--%>
<aside class="aside-col">
  <c:choose>
    <c:when test="${not empty eventList}">
      <h1 style="font-size:20px;font-weight:900;margin:0 0 10px">${region} 행사</h1>
      <div class="comment-list">
        <c:forEach var="ev" items="${eventList}">
          <div class="comment-item" style="display:flex;align-items:center;gap:8px">
            <div style="flex:1;min-width:0">
              <div style="font-weight:700;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">${ev.event_name}</div>
              <div style="color:var(--muted)">${ev.event_day} · ${ev.event_place}</div>
            </div>
            <c:if test="${not empty sessionScope.loginId}">
              <div style="display:flex;gap:4px;flex:none">
                <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_modify"
                   data-id="${ev.event_id}" data-region="${region}"
                   data-name="${ev.event_name}" data-day="${ev.event_day}" data-place="${ev.event_place}">✏️</a>
                <a class="icon-btn" href="${pageContext.request.contextPath}/Event?cmd=eventDelete&id=${ev.event_id}&region=${region}" title="삭제" onclick="return confirm('삭제할까요?')">🗑️</a>
              </div>
            </c:if>
          </div>
        </c:forEach>
      </div>
    </c:when>
    <c:when test="${not empty region}">
      <div class="detail-empty">${region}에 등록된 행사가 없습니다</div>
    </c:when>
    <c:otherwise>
      <div class="detail-empty">지역을 클릭하세요</div>
    </c:otherwise>
  </c:choose>

  <form class="entry-form" name="modify" method="post" action="${pageContext.request.contextPath}/Event?cmd=eventModifyPro" style="margin-top:16px">
    <b style="font-size:14px">행사 수정</b>
    <input type="hidden" name="id">
    <input type="hidden" name="region">
    <input class="form-input" type="text" name="event_name" placeholder="행사이름" required>
    <input class="form-input" type="text" name="event_day" placeholder="행사 날짜 (예: 2026-09-12)" required>
    <input class="form-input" type="text" name="event_place" placeholder="행사장소" required>
    <div class="form-actions">
      <a class="btn-cancel" id="btn_cancel2">취소</a>
      <button type="button" class="btn-save" id="btn_save2">수정</button>
    </div>
  </form>
</aside>

<script type="text/javascript">
	$(function(){
		$('form[name=modify]').hide();
		$(document).on('click', '[name=btn_modify]', function(){
			var $a = $(this);
			$('form[name=modify] [name=id]').val($a.data('id'));
			$('form[name=modify] [name=region]').val($a.data('region'));
			$('form[name=modify] [name=event_name]').val($a.data('name'));
			$('form[name=modify] [name=event_day]').val($a.data('day'));
			$('form[name=modify] [name=event_place]').val($a.data('place'));
			$('form[name=modify]').show();
		});
		$("#btn_cancel2").click(function(){
			$('form[name=modify]')[0].reset();
			$('form[name=modify]').hide();
		});
		$("#btn_save2").click(function(){
			if($('form[name=modify] [name=event_name]').val() == ''){
				$('form[name=modify] [name=event_name]').focus();
				return;
			}
			$('form[name=modify]').submit();
		});
	});
</script>
