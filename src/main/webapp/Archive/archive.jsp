<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  home.jsp를 여기로 합쳤습니다 (사이드바에서 홈 메뉴 제거, 이 페이지가 이제 새 "홈").
  트위터식 피드 대신 연도별 아코디언 카드 구조입니다. 아코디언 토글은 info.jsp의
  jQuery 패턴(name 속성 + closest('.card').find(...).toggle())을 그대로 따랐습니다.
  수정 폼도 info.jsp 패턴 그대로 — 목록에 이미 뿌려진 값을 data-* 속성으로 들고 있다가
  ✏️ 클릭하면 JS로 수정 폼에 채워넣는 방식이라 서버 왕복이 없습니다. (단, 파일 input은
  브라우저 보안상 JS로 값을 채울 수 없어서 수정 폼의 pic_file은 항상 비어있는 채로
  시작합니다 — 비워두면 기존 파일 유지, 새로 고르면 그걸로 교체.)
  사이드바의 "게시하기"(id=btn_entry) 버튼을 누르면 새 게시물(picture) 등록 폼이 열립니다.
  연도는 picture.pic_year를 그룹핑해서 만드는 것뿐이라 연도 자체를 따로 등록/수정/삭제하는
  기능은 없습니다.

  expects (request attributes):
    archiveGroups - List, picture를 pic_year로 묶은 연도별 그룹. 이 모양을 담을 DTO가
                    아직 없어서 새로 만드셔야 합니다:
                      getYear()       - int, 예: 2026
                      getCount()      - int, 그 해 전체 게시물 수 (페이지 무관)
                      getPictures()   - List<PictureDTO> (현재 페이지분만, getPic_id/getPic_name/
                                        getPhotographer/getPic_event/getPic_year)
                      getPage()       - int, 현재 페이지(1부터)
                      getTotalPages() - int
  request attribute "activeMenu"는 "home"으로 설정하고 진입시켜주세요 (이 페이지가 새 홈입니다)

  expected URLs (컨트롤러에서 구현):
    GET  Archive?cmd=archiveList                       - 목록, 전부 접힌 상태로 시작
    GET  Archive?cmd=archiveList&year=2026&page=2       - 특정 연도의 다음 페이지만 다시 렌더
    GET  Archive?cmd=archiveList&pictureId=5            - 게시물 클릭 → rightbar.jsp의 picture 채움
    POST Archive?cmd=archiveWritePro                    - 새 게시물 저장 (multipart, PictureWriteProService)
    POST Archive?cmd=archiveModifyPro                   - 게시물 수정 (multipart, PictureModifyProService)
    GET  Archive?cmd=archiveDelete&id=5                 - 게시물 삭제 (PictureDeleteService)
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>홈 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('form[name=entry]').hide();
		$('form[name=modify]').hide();
		$('.year-posts').hide();

		$('#btn_entry').click(function(){
			$('form[name=entry]').show();
		});
		$("#btn_cancel1").click(function(){
			$('form[name=entry]')[0].reset();
			$('form[name=entry]').hide();
		});
		$("#btn_save1").click(function(){
			if($('form[name=entry] [name=pic_name]').val() == ''){
				$('form[name=entry] [name=pic_name]').focus();
				return;
			}
			$('form[name=entry]').submit();
		});

		$(document).on('click', '[name=btn_modify]', function(){
			var $a = $(this);
			$('form[name=modify] [name=id]').val($a.data('id'));
			$('form[name=modify] [name=pic_name]').val($a.data('name'));
			$('form[name=modify] [name=photographer]').val($a.data('photographer'));
			$('form[name=modify] [name=pic_event]').val($a.data('event'));
			$('form[name=modify] [name=pic_year]').val($a.data('year'));
			$('form[name=modify]').show();
		});
		$("#btn_cancel2").click(function(){
			$('form[name=modify]')[0].reset();
			$('form[name=modify]').hide();
		});
		$("#btn_save2").click(function(){
			if($('form[name=modify] [name=pic_name]').val() == ''){
				$('form[name=modify] [name=pic_name]').focus();
				return;
			}
			$('form[name=modify]').submit();
		});

		$(document).on('click', '[name=btn_year_toggle]', function(){
			$(this).closest('.card').find('.year-posts').toggle();
			$(this).find('.year-chevron').toggleClass('open');
		});
	});
</script>
</head>
<body>
<div class="app-shell">
  <jsp:include page="/common/sidebar.jsp"/>

  <main class="main-col">
    <div class="page-header">
      <div class="page-header-row">
        <h2 class="page-title">홈</h2>
      </div>
    </div>

    <form class="entry-form" name="entry" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Archive?cmd=archiveWritePro">
      <b style="font-size:14px">새 게시물 등록</b>
      <input class="form-input" type="text" name="pic_name" placeholder="캐릭터 이름" required>
      <input class="form-input" type="file" name="pic_file" size="30">
      <input class="form-input" type="text" name="photographer" placeholder="사진사 (필수아님)">
      <input class="form-input" type="text" name="pic_event" placeholder="행사 (필수아님)">
      <input class="form-input" type="text" name="pic_year" placeholder="연도 (예: 2026)" required>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel1">취소</a>
        <button type="button" class="btn-save" id="btn_save1">저장</button>
      </div>
    </form>

    <form class="entry-form" name="modify" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Archive?cmd=archiveModifyPro">
      <b style="font-size:14px">게시물 수정</b>
      <input type="hidden" name="id">
      <input class="form-input" type="text" name="pic_name" placeholder="캐릭터 이름" required>
      <input class="form-input" type="file" name="pic_file" size="30">
      <div style="font-size:12px;color:var(--muted);margin-top:-4px">비워두면 기존 파일 유지</div>
      <input class="form-input" type="text" name="photographer" placeholder="사진사 (필수아님)">
      <input class="form-input" type="text" name="pic_event" placeholder="행사 (필수아님)">
      <input class="form-input" type="text" name="pic_year" placeholder="연도 (예: 2026)" required>
      <div class="form-actions">
        <a class="btn-cancel" id="btn_cancel2">취소</a>
        <button type="button" class="btn-save" id="btn_save2">수정</button>
      </div>
    </form>

    <div style="padding:20px 20px 0;display:flex;flex-direction:column;gap:14px">
      <c:forEach var="group" items="${archiveGroups}">
        <div class="card" style="padding:16px">
          <div class="year-title" name="btn_year_toggle">
            <svg class="year-chevron" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="6 9 12 15 18 9"></polyline>
            </svg>
            ${group.year}년
          </div>
          <span style="font-size:12px;color:var(--blue);font-weight:700">${group.count}개의 게시물</span>

          <div class="year-posts comment-list">
            <c:forEach var="pic" items="${group.pictures}">
              <div class="comment-item" style="display:flex;align-items:center;gap:8px">
                <a href="${pageContext.request.contextPath}/Archive?cmd=archiveList&year=${group.year}&pictureId=${pic.pic_id}"
                   style="flex:1;min-width:0;display:flex;justify-content:space-between;gap:8px;text-decoration:none;color:inherit;overflow:hidden">
                  <span style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap">${pic.pic_name} · ${pic.pic_event}</span>
                  <span style="color:var(--muted);flex:none">${pic.pic_year}</span>
                </a>
                <c:if test="${not empty sessionScope.loginId}">
                  <div style="display:flex;gap:4px;flex:none">
                    <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_modify"
                       data-id="${pic.pic_id}" data-name="${pic.pic_name}"
                       data-photographer="${pic.photographer}" data-event="${pic.pic_event}" data-year="${pic.pic_year}">✏️</a>
                    <a class="icon-btn" href="${pageContext.request.contextPath}/Archive?cmd=archiveDelete&id=${pic.pic_id}" title="삭제" onclick="return confirm('삭제할까요?')">🗑️</a>
                  </div>
                </c:if>
              </div>
            </c:forEach>

            <c:if test="${group.totalPages > 1}">
              <div class="pagination">
                <c:forEach var="p" begin="1" end="${group.totalPages}">
                  <a class="page-btn${p == group.page ? ' active' : ''}"
                     href="${pageContext.request.contextPath}/Archive?cmd=archiveList&year=${group.year}&page=${p}">${p}</a>
                </c:forEach>
              </div>
            </c:if>
          </div>
        </div>
      </c:forEach>
    </div>
    <c:if test="${empty archiveGroups}">
      <p class="empty-msg">기록이 없습니다</p>
    </c:if>
  </main>

  <jsp:include page="rightbar.jsp" />
</div>
</body>
</html>
