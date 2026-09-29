<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  wish.jsp와 같은 구조입니다 (샵/채팅 같은 자식 테이블이 없어서 그 부분만 뺐습니다).
  카드엔 plan_name(제목, 클릭하면 rightbar에 상세)과 plan_comment만 보이고, 수정/삭제는
  카드에서 data-* + JS로 처리합니다. plan_switch(완료/미완료)는 tab-bar 필터로 쓰고,
  plan_day(토/일/양일)는 select로 고정 3개 옵션입니다.

  expects (request attributes):
    planList       - List<PlanDTO>: getPlan_id, getPlan_name, getPlan_file, getPlan_comment,
                      getPlan_switch, getPlan_day
    selectedStatus - String, 전체/완료/미완료
    plan           - (for rightbar.jsp) 클릭해서 선택한 plan, 없으면 안내 문구
  request attribute "activeMenu"는 "plan"으로 설정하고 진입시켜주세요

  expected URLs (컨트롤러에서 구현):
    GET  Plan?cmd=planList                      - 전체 목록
    GET  Plan?cmd=planList&status=완료           - 상태 필터
    GET  Plan?cmd=planList&planId=5              - 플랜 클릭 → rightbar에 plan 채움
    POST Plan?cmd=planWritePro                   - 새 플랜 등록 (multipart)
    POST Plan?cmd=planModifyPro                  - 플랜 수정 (multipart)
    GET  Plan?cmd=planDelete&id=5                - 플랜 삭제
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>플랜 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('form[name=entry]').hide();
		$('form[name=modify]').hide();

		$('#btn_entry').click(function(){
			$('form[name=entry]').show();
		});
		$(document).on('click', '[name=btn_modify]', function(){
			var $a = $(this);
			$('form[name=modify] [name=id]').val($a.data('id'));
			$('form[name=modify] [name=plan_name]').val($a.data('name'));
			$('form[name=modify] [name=plan_comment]').val($a.data('comment'));
			$('form[name=modify] [name=plan_switch]').val($a.data('switch'));
			$('form[name=modify] [name=plan_day]').val($a.data('day'));
			$('form[name=modify]').show();
		});

		$("#btn_save1").click(function(){
			if($('form[name=entry] [name=plan_name]').val() == ''){
				$('form[name=entry] [name=plan_name]').focus();
				return;
			}
			$('form[name=entry]').submit();
		});
		$("#btn_save2").click(function(){
			if($('form[name=modify] [name=plan_name]').val() == ''){
				$('form[name=modify] [name=plan_name]').focus();
				return;
			}
			$('form[name=modify]').submit();
		});

		$("#btn_cancel1").click(function(){
			$('form[name=entry]')[0].reset();
			$('form[name=entry]').hide();
		});
		$("#btn_cancel2").click(function(){
			$('form[name=modify]')[0].reset();
			$('form[name=modify]').hide();
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
        <h2 class="page-title">📝 플랜</h2>
      </div>
      <div class="tab-bar">
        <a class="tab-btn${empty selectedStatus || selectedStatus == '전체' ? ' active' : ''}" href="${pageContext.request.contextPath}/Plan?cmd=planList">전체</a>
        <a class="tab-btn${selectedStatus == '완료' ? ' active' : ''}" href="${pageContext.request.contextPath}/Plan?cmd=planList&status=완료">완료</a>
        <a class="tab-btn${selectedStatus == '미완료' ? ' active' : ''}" href="${pageContext.request.contextPath}/Plan?cmd=planList&status=미완료">미완료</a>
      </div>
    </div>

    <form class="entry-form" name="entry" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Plan?cmd=planWritePro">
      <b style="font-size:14px">새 플랜 등록</b>
      <input class="form-input" type="text" name="plan_name" placeholder="플랜 이름" required>
      <input class="form-input" type="file" name="plan_file" size="30">
      <textarea class="form-textarea" name="plan_comment" rows="3" placeholder="뭐 할건지"></textarea>
      <select class="form-select" name="plan_day">
        <option value="토">토</option>
        <option value="일">일</option>
        <option value="양일">양일</option>
      </select>
      <select class="form-select" name="plan_switch">
        <option value="미완료">미완료</option>
      </select>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel1">취소</a>
        <button type="button" class="btn-save" id="btn_save1">저장</button>
      </div>
    </form>

    <form class="entry-form" name="modify" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Plan?cmd=planModifyPro">
      <b style="font-size:14px">플랜 수정</b>
      <input type="hidden" name="id">
      <input class="form-input" type="text" name="plan_name" placeholder="플랜 이름" required>
      <input class="form-input" type="file" name="plan_file" size="30">
      <div style="font-size:12px;color:var(--muted);margin-top:-4px">비워두면 기존 파일 유지</div>
      <textarea class="form-textarea" name="plan_comment" rows="3" placeholder="뭐 할건지"></textarea><br>
      <select class="form-select" name="plan_day">
        <option value="토">토</option>
        <option value="일">일</option>
        <option value="양일">양일</option>
      </select>
      <select class="form-select" name="plan_switch">
        <option value="미완료">미완료</option>
        <option value="완료">완료</option>
      </select>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel2">취소</a>
        <button type="button" class="btn-save" id="btn_save2">수정</button>
      </div>
    </form>

    <div style="padding:20px;display:flex;flex-direction:column;gap:14px">
      <c:forEach var="p" items="${planList}">
        <div class="card" style="padding:16px">
          <div class="card-head" style="display:flex;align-items:center;justify-content:space-between;gap:10px">
            <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
              <a href="${pageContext.request.contextPath}/Plan?cmd=planList&planId=${p.plan_id}" style="color:inherit;text-decoration:none">${p.plan_name}</a>
            </div>
            <c:if test="${not empty sessionScope.loginId}">
              <div style="display:flex;gap:6px;flex-shrink:0">
                <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_modify"
                   data-id="${p.plan_id}" data-name="${p.plan_name}" data-comment="${p.plan_comment}"
                   data-switch="${p.plan_switch}" data-day="${p.plan_day}">✏️</a>
                <a class="icon-btn" href="${pageContext.request.contextPath}/Plan?cmd=planDelete&id=${p.plan_id}" title="삭제" onclick="return confirm('삭제할까요?')">🗑️</a>
              </div>
            </c:if>
          </div>
          <div style="font-size:14px;color:var(--muted)">${p.plan_comment}</div>
        </div>
      </c:forEach>
    </div>
    <c:if test="${empty planList}">
      <p class="empty-msg">등록된 플랜이 없습니다</p>
    </c:if>
  </main>

  <jsp:include page="rightbar.jsp" />
</div>
</body>
</html>
