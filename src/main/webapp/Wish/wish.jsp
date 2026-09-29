<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  info.jsp 구조를 거의 그대로 따랐습니다. 카드엔 wish_name(제목, 클릭하면 rightbar에 상세+샵
  목록)과 wish_comment만 보이고, wish_switch(상태)는 tab-bar 필터로만 쓰고 카드엔 안 보여줍니다.
  "샵 추가"는 info의 btn_chat_toggle과 같은 패턴 — wish_chat_contents(샵 이름)/wish_chat_url만
  입력받고 wish_id는 action URL로 넘어갑니다. 수정/삭제는 info와 동일하게 카드에서
  data-* + JS로 처리합니다(rightbar가 아니라 여기서).

  expects (request attributes):
    wishList       - List<WishDTO>: getWish_id, getWish_name, getWish_file, getWish_comment, getWish_switch
    selectedStatus - String, 전체/완료/미완료
    wish           - (for rightbar.jsp) 클릭해서 선택한 wish, 없으면 안내 문구
    wishChatList   - (for rightbar.jsp) List<WishChatDTO>, wish 있을 때만
  request attribute "activeMenu"는 "wish"로 설정하고 진입시켜주세요

  expected URLs (컨트롤러에서 구현):
    GET  Wish?cmd=wishList                      - 전체 목록
    GET  Wish?cmd=wishList&status=완료           - 상태 필터
    GET  Wish?cmd=wishList&wishId=5              - 위시 클릭 → rightbar에 wish+wishChatList 채움
    POST Wish?cmd=wishWritePro                   - 새 위시 등록 (multipart)
    POST Wish?cmd=wishModifyPro                  - 위시 수정 (multipart)
    GET  Wish?cmd=wishDelete&id=5                - 위시 삭제 (딸린 wish_chat도 같이 삭제)
    POST Wish?cmd=wishChatWrite&id=5             - 샵 추가
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>위시 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('form[name=entry]').hide();
		$('form[name=modify]').hide();
		$('form[name=chat_entry]').hide();

		$('#btn_entry').click(function(){
			$('form[name=entry]').show();
		});
		$(document).on('click', '[name=btn_chat_toggle]', function(){
			$(this).closest('.card').find('form[name=chat_entry]').toggle();
		});
		$(document).on('click', '[name=btn_modify]', function(){
			var $a = $(this);
			$('form[name=modify] [name=id]').val($a.data('id'));
			$('form[name=modify] [name=wish_name]').val($a.data('name'));
			$('form[name=modify] [name=wish_comment]').val($a.data('comment'));
			$('form[name=modify] [name=wish_switch]').val($a.data('switch'));
			$('form[name=modify]').show();
		});

		$("#btn_save1").click(function(){
			if($('form[name=entry] [name=wish_name]').val() == ''){
				$('form[name=entry] [name=wish_name]').focus();
				return;
			}
			$('form[name=entry]').submit();
		});
		$("#btn_save2").click(function(){
			if($('form[name=modify] [name=wish_name]').val() == ''){
				$('form[name=modify] [name=wish_name]').focus();
				return;
			}
			$('form[name=modify]').submit();
		});
		$(document).on('click', '[name=btn_save3]', function(){
			var $form = $(this).closest('form[name=chat_entry]');
			if($form.find('[name=wish_chat_contents]').val() == ''){
				$form.find('[name=wish_chat_contents]').focus();
				return;
			}
			$form.submit();
		});

		$("#btn_cancel1").click(function(){
			$('form[name=entry]')[0].reset();
			$('form[name=entry]').hide();
		});
		$("#btn_cancel2").click(function(){
			$('form[name=modify]')[0].reset();
			$('form[name=modify]').hide();
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
        <h2 class="page-title">⭐ 위시</h2>
      </div>
      <div class="tab-bar">
        <a class="tab-btn${empty selectedStatus || selectedStatus == '전체' ? ' active' : ''}" href="${pageContext.request.contextPath}/Wish?cmd=wishList">전체</a>
        <a class="tab-btn${selectedStatus == '완료' ? ' active' : ''}" href="${pageContext.request.contextPath}/Wish?cmd=wishList&status=완료">완료</a>
        <a class="tab-btn${selectedStatus == '미완료' ? ' active' : ''}" href="${pageContext.request.contextPath}/Wish?cmd=wishList&status=미완료">미완료</a>
      </div>
    </div>

    <form class="entry-form" name="entry" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Wish?cmd=wishWritePro">
      <b style="font-size:14px">새 위시 등록</b>
      <input class="form-input" type="text" name="wish_name" placeholder="캐릭터 이름" required>
      <input class="form-input" type="file" name="wish_file" size="30">
      <textarea class="form-textarea" name="wish_comment" rows="3" placeholder="하고 싶은 이유"></textarea>
      <select class="form-select" name="wish_switch">
        <option value="미완료">미완료</option>
      </select>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel1">취소</a>
        <button type="button" class="btn-save" id="btn_save1">저장</button>
      </div>
    </form>

    <form class="entry-form" name="modify" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Wish?cmd=wishModifyPro">
      <b style="font-size:14px">위시 수정</b>
      <input type="hidden" name="id">
      <input class="form-input" type="text" name="wish_name" placeholder="캐릭터 이름" required>
      <input class="form-input" type="file" name="wish_file" size="30">
      <div style="font-size:12px;color:var(--muted);margin-top:-4px">비워두면 기존 파일 유지</div>
      <textarea class="form-textarea" name="wish_comment" rows="3" placeholder="하고 싶은 이유"></textarea>
      <select class="form-select" name="wish_switch">
        <option value="미완료">미완료</option>
        <option value="완료">완료</option>
      </select>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel2">취소</a>
        <button type="button" class="btn-save" id="btn_save2">수정</button>
      </div>
    </form>

    <div style="padding:20px;display:flex;flex-direction:column;gap:14px">
      <c:forEach var="w" items="${wishList}">
        <div class="card" style="padding:16px">
          <c:if test="${not empty sessionScope.loginId}">
            <div class="card-head" style="justify-content:flex-end">
              <div style="display:flex;gap:6px">
                <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_modify"
                   data-id="${w.wish_id}" data-name="${w.wish_name}" data-comment="${w.wish_comment}" data-switch="${w.wish_switch}">✏️</a>
                <a class="icon-btn" href="${pageContext.request.contextPath}/Wish?cmd=wishDelete&id=${w.wish_id}" title="삭제" onclick="return confirm('삭제할까요?')">🗑️</a>
              </div>
            </div>
          </c:if>
          <div style="font-size:16px;font-weight:800;margin:10px 0 6px">
            <a href="${pageContext.request.contextPath}/Wish?cmd=wishList&wishId=${w.wish_id}" style="color:inherit;text-decoration:none">${w.wish_name}</a>
          </div>
          <div style="font-size:14px;color:var(--muted);margin-bottom:6px">${w.wish_comment}</div>
          <br>
	      <c:choose>
	      	<c:when test="${not empty sessionScope.loginId}">
	      		<div style="margin-top:8px">
	            	<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">샵 추가</a>
	          	</div>
	     	</c:when>
	  	  </c:choose>
	         

          <div class="comment-list" style="margin-top:12px">
            <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Wish?cmd=wishChatWrite&id=${w.wish_id}">
              <input class="comment-input" type="text" name="wish_chat_contents" placeholder="샵 이름" required>
              <input class="comment-input" type="text" name="wish_chat_url" placeholder="샵 url">
              <a class="btn-cancel" name="btn_cancel3">취소</a>
              <button type="submit" class="comment-submit" name="btn_save3">등록</button>
            </form>
          </div>
        </div>
      </c:forEach>
    </div>
    <c:if test="${empty wishList}">
      <p class="empty-msg">등록된 위시가 없습니다</p>
    </c:if>
  </main>

  <jsp:include page="rightbar.jsp" />
</div>
</body>
</html>
