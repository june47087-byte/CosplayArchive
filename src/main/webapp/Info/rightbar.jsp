<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attribute, set when a card is clicked):
    info - InfoDTO: getInfo_id, getInfo_div, getInfo_name, getInfo_contents,
           getInfo_url, getComments (List<InfoChatDTO> - getInfo_chat_comment)
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
		$('form[name=chat_modify] [name=info_chat_comment]').val($a.data('comment'));
		$('form[name=chat_modify] [name=info_chat_url]').val($a.data('url'));
		$('form[name=chat_modify]').show();
	});
	$("#btn_chat_save2").click(function(){
		if($('form[name=chat_modify] [name=info_chat_comment]').val() == ''){
			alert("내용을 입력하세요");
			$('form[name=chat_modify] [name=info_chat_comment]').focus();
			return;
		}
		var url2 = $('form[name=chat_modify] [name=info_chat_url]').val();
		if(url2 != '' && !isValidUrl(url2)){
			alert("URL은 http:// 또는 https:// 로 시작해야 합니다");
			$('form[name=chat_modify] [name=info_chat_url]').focus();
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
<form class="entry-form" name="chat_modify" method="post" action="${pageContext.request.contextPath}/Info?cmd=infoChatModifyPro">
      	<b style="font-size:14px">댓글 수정</b>
		<input type="hidden" name="chat_id" value="">
		<input type="hidden" name="info_id" value="${info.info_id}">
        <input class="form-input" type="text" name="info_chat_comment" placeholder="메모" required>
        <input class="form-input" type="text" name="info_chat_url" placeholder="URL (https://...)">
        <div class="form-actions">
          	<a class="btn-cancel" id="btn_chat_cancel2">취소</a>
          	<button type="button" class="btn-save" id="btn_chat_save2">수정</button>
        </div>
      	</form>
  <c:choose>
    <c:when test="${not empty info}">
      <div class="detail-head"><span class="badge">${info.info_div}</span></div>
      <h1 style="font-size:24px;font-weight:900;margin:0 0 10px">${info.info_name}</h1>
      <p style="font-size:16px;line-height:1.8;color:var(--text);margin:0 0 16px;white-space:pre-line">${info.info_contents}</p>
      <c:if test="${not empty info.info_url}">
        <a href="<c:out value='${info.info_url}'/>" target="_blank" rel="noopener noreferrer" style="font-size:13px;word-break:break-all">🔗 <c:out value="${info.info_url}"/></a>
      </c:if>
      <div class="comment-list">
        <c:forEach var="chat" items="${info.comments}">
          <div class="comment-item">
            <c:out value="${chat.info_chat_comment}" />
            <c:if test="${not empty sessionScope.loginId}">
            <a class="icon-btn" href="javascript:void(0)" title="수정" name="btn_chat_modify"
                			data-id="${chat.info_chat_id}"
                			data-comment="${chat.info_chat_comment}" data-url="${chat.info_chat_url}">✏️</a>
              <a class="icon-btn" href="${pageContext.request.contextPath}/Info?cmd=infoChatDelete&info_chat_id=${chat.info_chat_id}&info_id=${info.info_id}" title="삭제">🗑️</a>
            </c:if>
          </div>
          <c:if test="${not empty chat.info_chat_url}">
            <div class="comment-item">
              <a href="<c:out value='${chat.info_chat_url}'/>" target="_blank" rel="noopener noreferrer" style="word-break:break-all"><c:out value="${chat.info_chat_url}"/></a>
            </div>
          </c:if>
        </c:forEach>
      </div>
    </c:when>

    <c:otherwise>
      <div class="detail-empty">게시물을 클릭하세요</div>
    </c:otherwise>
  </c:choose>
</aside>
