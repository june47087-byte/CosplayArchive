<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attributes):
    infoList         - List: getId, getCategory, getTitle, getBody, getUrl,
                        getComments() (list of items exposing getContent())
    selectedCategory - String, one of 세팅/제작/자세/샵
    showForm         - boolean
    formInfo         - getId, getCategory, getTitle, getBody, getUrl (only needed when showForm)
  request attribute "activeMenu" should be set to "info" before forwarding here

  expected URLs (implement in the controller):
    GET  info                          - list, requires ?category= (no "전체" tab for this menu)
    GET  info?action=write             - open blank add form
    GET  info?action=edit&id=N         - open edit form prefilled for id N
    POST info?action=save              - insert (empty id) or update (id present)
    GET  info?action=delete&id=N       - delete id N
    POST info?action=comment&id=N      - add a comment (param "content") to info N
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>정보 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script type="text/javascript">
	function isValidUrl(url){
		return /^https?:\/\/.+/i.test(url);
	}

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
			$('form[name=modify] [name=info_div]').val($a.data('div'));
			$('form[name=modify] [name=info_name]').val($a.data('name'));
			$('form[name=modify] [name=info_contents]').val($a.data('contents'));
			$('form[name=modify] [name=info_url]').val($a.data('url'));
			$('form[name=modify]').show();
		});
		$("#btn_save1").click(function(){
			// 목록 구분
			if($('form[name=entry] [name=info_div]').val() == ''){
				$('form[name=entry] [name=info_div]').focus();
				return;
			}
			// 제목 검사
			if($('form[name=entry] [name=info_name]').val() == ''){
				$('form[name=entry] [name=info_name]').focus();
				return;
			}
			// 내용 검사
			if($('form[name=entry] [name=info_contents]').val() == ''){
				$('form[name=entry] [name=info_contents]').focus();
				return;
			}
			// URL 검사
			var url1 = $('form[name=entry] [name=info_url]').val();
			if(url1 != '' && !isValidUrl(url1)){
				alert("URL은 http:// 또는 https:// 로 시작해야 합니다");
				$('form[name=entry] [name=info_url]').focus();
				return;
			}
			$('form[name=entry]').submit();
		});
		$("#btn_save2").click(function(){
			// 목록 구분
			if($('form[name=modify] [name=info_div]').val() == ''){
				alert("정보구분을 선택하세요!");
				$('form[name=modify] [name=info_div]').focus();
				return;
			}
			// 제목 검사
			if($('form[name=modify] [name=info_name]').val() == ''){
				alert("제목를 입력하세요");
				$('form[name=modify] [name=info_name]').focus();
				return;
			}
			// 내용 검사
			if($('form[name=modify] [name=info_contents]').val() == ''){
				alert("내용을 입력하세요");
				$('form[name=modify] [name=info_contents]').focus();
				return;
			}
			// URL 검사
			var url2 = $('form[name=modify] [name=info_url]').val();
			if(url2 != '' && !isValidUrl(url2)){
				alert("URL은 http:// 또는 https:// 로 시작해야 합니다");
				$('form[name=modify] [name=info_url]').focus();
				return;
			}
			$('form[name=modify]').submit();
		});
		$(document).on('click', '[name=btn_save3]', function(){
			var $form = $(this).closest('form[name=chat_entry]');
			// 내용 검사
			if($form.find('[name=info_chat_comment]').val() == ''){
				$form.find('[name=info_chat_comment]').focus();
				return;
			}
			// URL 검사
			var url3 = $form.find('[name=info_chat_url]').val();
			if(url3 != '' && !isValidUrl(url3)){
				alert("URL은 http:// 또는 https:// 로 시작해야 합니다");
				$form.find('[name=info_chat_url]').focus();
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
	<jsp:include page="/common/sidebar.jsp"/>

	<main class="main-col">
    <div class="page-header">
		<div class="page-header-row">
        <h2 class="page-title">ℹ️ 정보</h2>
      	</div>
      	<div class="tab-bar">
        	<a class="tab-btn${empty selectedCategory ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoList">전체</a>
        	<a class="tab-btn${selectedCategory == '세팅' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoListSetting">세팅</a>
        	<a class="tab-btn${selectedCategory == '제작' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoListMake">제작</a>
        	<a class="tab-btn${selectedCategory == '자세' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoListPose">자세</a>
        	<a class="tab-btn${selectedCategory == '샵' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoListShop">샵</a>
        	<a class="tab-btn${selectedCategory == '기타' ? ' active' : ''}" href="${pageContext.request.contextPath}/Info?cmd=infoListElse">기타</a>
      	</div>
    	</div>

		<form class="entry-form" name="entry" method="post" action="${pageContext.request.contextPath}/Info?cmd=infoWritePro">
			<b style="font-size:14px">새 글 등록</b>
        	<select class="form-select" name="info_div">
          		<option value="세팅" ${selectedCategory == '세팅' ? 'selected' : ''}>세팅</option>
          		<option value="제작" ${selectedCategory == '제작' ? 'selected' : ''}>제작</option>
          		<option value="자세" ${selectedCategory == '자세' ? 'selected' : ''}>자세</option>
          		<option value="샵" ${selectedCategory == '샵' ? 'selected' : ''}>샵</option>
          		<option value="기타" ${selectedCategory == '기타' ? 'selected' : ''}>기타</option>
        	</select>
        	<input class="form-input" type="text" name="info_name" placeholder="제목" required>
        	<textarea class="form-textarea" name="info_contents" rows="3" placeholder="게시글 내용" required></textarea> 
        	<input class="form-input" type="text" name="info_url" placeholder="URL (https://...)">
        	<div class="form-actions">
          	<a class="btn-cancel" id="btn_cancel1">취소</a>
          	<button type="button" class="btn-save" id="btn_save1">저장</button>
        	</div>
		</form>
      
		<form class="entry-form" name="modify" method="post" action="${pageContext.request.contextPath}/Info?cmd=infoModifyPro">
      	<b style="font-size:14px">글 수정</b>
		<input type="hidden" name="id" value="${formInfo.info_id}">
        <select class="form-select" name="info_div">
			<option value="세팅" ${formInfo.info_div == '세팅' ? 'selected' : ''}>세팅</option>
        	<option value="제작" ${formInfo.info_div == '제작' ? 'selected' : ''}>제작</option>
          	<option value="자세" ${formInfo.info_div == '자세' ? 'selected' : ''}>자세</option>
          	<option value="샵" ${formInfo.info_div == '샵' ? 'selected' : ''}>샵</option>
          	<option value="기타" ${formInfo.info_div == '기타' ? 'selected' : ''}>기타</option>
        </select>
        <input class="form-input" type="text" name="info_name" value="${formInfo.info_name}" placeholder="제목" required>
        <textarea class="form-textarea" name="info_contents" rows="3" placeholder="게시글 내용" required>${formInfo.info_contents}</textarea>
        <input class="form-input" type="text" name="info_url" value="${formInfo.info_url}" placeholder="URL (https://...)">
        <div class="form-actions">
          	<a class="btn-cancel" id="btn_cancel2">취소</a>
          	<button type="button" class="btn-save" id="btn_save2">수정</button>
        </div>
      	</form>
      
    <div style="padding:20px;display:flex;flex-direction:column;gap:14px">
      	<c:forEach var="row" items="${infoList}">
        <div class="card" style="padding:16px">
          	<div class="card-head">
            	<span class="badge">${row.info_div}</span>
            	<c:if test="${not empty sessionScope.loginId}">
              		<div style="display:flex;gap:6px">
                		<a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_modify"
                			data-id="${row.info_id}" data-div="${row.info_div}"
                			data-name="${row.info_name}" data-contents="${row.info_contents}" data-url="${row.info_url}">✏️</a>
                		<a class="icon-btn" href="${pageContext.request.contextPath}/Info?cmd=infoDelete&id=${row.info_id}" title="삭제" id="btn_delete">🗑️</a>
             		</div>
            	</c:if>
          	</div>
		<div style="font-size:16px;font-weight:800;margin:10px 0 6px"><a href="${pageContext.request.contextPath}/Info?cmd=infoDetail&id=${row.info_id}" style="color:inherit;text-decoration:none">${row.info_name}</a></div>
          	<div style="font-size:14px;color:var(--muted);margin-bottom:6px">${row.info_contents}</div>
          	<a href="${row.info_url}" target="_blank" style="font-size:13px;word-break:break-all"> ${row.info_url}</a><br>
          	<c:choose>
	      		<c:when test="${not empty sessionScope.loginId}">
	      			<div style="margin-top:8px">
	            		<a class="btn-cancel" name="btn_chat_toggle" style="cursor:pointer">정보 추가</a>
	          		</div>
	     		</c:when>
	  	  </c:choose>

		<div class="comment-list" style="margin-top:12px">
          <form class="comment-form-row" name="chat_entry" method="post" action="${pageContext.request.contextPath}/Info?cmd=infoChatWrite&id=${row.info_id}">
            <input class="comment-input" type="text" name="info_chat_comment" placeholder="메모 추가..." required>
            <input class="comment-input" type="text" name="info_chat_url" placeholder="url 추가...">
            <a class="btn-cancel" name="btn_cancel3">취소</a>
            <button type="button" class="comment-submit" name="btn_save3">등록</button>
          </form>
        </div>
		</div>
		</c:forEach>
		</div>
    <c:if test="${empty infoList}">
      <p class="empty-msg">등록된 글이 없습니다</p>
    </c:if>
  </main>

  <jsp:include page="rightbar.jsp" />
</div>
</body>
</html>
