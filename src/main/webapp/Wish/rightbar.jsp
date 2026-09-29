<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attributes):
    wish         - WishDTO: getWish_id, getWish_name, getWish_file, getWish_comment, getWish_switch
                   (없으면 안내 문구)
    wishChatList - List<WishChatDTO> (getWish_chat_contents=샵 이름, getWish_chat_url), wish 있을 때만

  순서: wish_name → wish_comment → wish_file(이미지) → wish_chat 목록.
  wish 게시물 자체의 수정/삭제는 wish.jsp 카드에서 합니다. 여기서는 wish_chat(샵) 항목
  하나하나의 수정/삭제만 처리합니다(info의 rightbar.jsp와 동일 패턴).
--%>
<script>
function isValidUrl(url){
	return /^https?:\/\/.+/i.test(url);
}
$(function(){
	$('form[name=chat_modify]').hide();
	$(document).on('click', '[name=btn_chat_modify]', function(){
		var $a = $(this);
		$('form[name=chat_modify] [name=chat_id]').val($a.data('id'));
		$('form[name=chat_modify] [name=wish_chat_contents]').val($a.data('contents'));
		$('form[name=chat_modify] [name=wish_chat_url]').val($a.data('url'));
		$('form[name=chat_modify]').show();
	});
	$("#btn_chat_save2").click(function(){
		if($('form[name=chat_modify] [name=wish_chat_contents]').val() == ''){
			alert("샵 이름을 입력하세요");
			$('form[name=chat_modify] [name=wish_chat_contents]').focus();
			return;
		}
		var url2 = $('form[name=chat_modify] [name=wish_chat_url]').val();
		if(url2 != '' && !isValidUrl(url2)){
			alert("URL은 http:// 또는 https:// 로 시작해야 합니다");
			$('form[name=chat_modify] [name=wish_chat_url]').focus();
			return;
		}
		$('form[name=chat_modify]').submit();
	});
	$("#btn_chat_cancel2").click(function(){
		$('form[name=chat_modify]')[0].reset();
		$('form[name=chat_modify]').hide();
	});
});
</script>
<aside class="aside-col">
<c:if test="${not empty wish && not empty sessionScope.loginId}">
<form class="entry-form" name="chat_modify" method="post" action="${pageContext.request.contextPath}/Wish?cmd=wishChatModifyPro">
  <b style="font-size:14px">샵 수정</b>
  <input type="hidden" name="chat_id" value="">
  <input type="hidden" name="wish_id" value="${wish.wish_id}">
  <input class="form-input" type="text" name="wish_chat_contents" placeholder="샵 이름" required>
  <input class="form-input" type="text" name="wish_chat_url" placeholder="샵 url">
  <div class="form-actions">
    <a class="btn-cancel" id="btn_chat_cancel2">취소</a>
    <button type="button" class="btn-save" id="btn_chat_save2">수정</button>
  </div>
</form>
</c:if>
  <c:choose>
    <c:when test="${not empty wish}">
      <h1 style="font-size:24px;font-weight:900;margin:0 0 10px">${wish.wish_name}</h1>
      <p style="font-size:16px;line-height:1.8;color:var(--text);margin:0 0 16px;white-space:pre-line">${wish.wish_comment}</p>
      <c:if test="${not empty wish.wish_file}">
        <div class="detail-photo">
          <img src="${pageContext.request.contextPath}/Wish/upload/${wish.wish_file}" alt="${wish.wish_name}" style="width:100%;height:100%;object-fit:cover;display:block">
        </div>
      </c:if>
      <div class="comment-list">
        <c:forEach var="shop" items="${wishChatList}">
          <div class="comment-item">
            <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:6px">
              <div style="font-weight:700">${shop.wish_chat_contents}</div>
              <c:if test="${not empty sessionScope.loginId}">
                <div style="display:flex;gap:6px;flex-shrink:0">
                  <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_chat_modify"
                     data-id="${shop.wish_chat_id}" data-contents="${shop.wish_chat_contents}" data-url="${shop.wish_chat_url}">✏️</a>
                  <a class="icon-btn" href="${pageContext.request.contextPath}/Wish?cmd=wishChatDelete&wish_chat_id=${shop.wish_chat_id}&wish_id=${wish.wish_id}" title="삭제" onclick="return confirm('삭제할까요?')">🗑️</a>
                </div>
              </c:if>
            </div>
            <c:if test="${not empty shop.wish_chat_url}">
              <a href="${shop.wish_chat_url}" target="_blank" style="font-size:12px;word-break:break-all">🔗 ${shop.wish_chat_url}</a>
            </c:if>
          </div>
        </c:forEach>
      </div>
    </c:when>

    <c:otherwise>
      <div class="detail-empty">게시물을 클릭하세요</div>
    </c:otherwise>
  </c:choose>
</aside>
