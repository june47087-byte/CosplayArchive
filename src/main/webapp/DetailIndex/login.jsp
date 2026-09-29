<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  expects (request attributes / params):
    error    - optional String, shown when the previous login attempt failed
    redirect - optional request PARAM, where to send the user back after a successful login
               (read it in the controller with request.getParameter("redirect") and pass it through as a hidden field)
  no sidebar/rightbar on this page (matches the design's full-screen login screen)
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인 · 코스 아카이브</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script type="text/javascript">
	$(function(){
		$("#loginCheck").click(function(){
			var loginId = $("#loginId").val(), loginPw = $("#loginPw").val();
			$.ajax({
				url:'${pageContext.request.contextPath}/Cos?cmd=loginPro',
				type:'post',
				data:{'loginId':loginId, 'loginPw':loginPw},
				success:function(result){
					if(result == "true"){
						location.href ='${pageContext.request.contextPath}/Archive?cmd=archiveList';
					}else{
						alertText.innerHTML='아이디 혹은 비밀번호가 틀립니다.';
					}
				}
			});
		});
	});
</script>
</head>
<body>
  <div class="login-wrap">
    <div class="login-box">
      <div class="login-header">
        <div class="login-mark">衣</div>
        <b style="font-size:20px">로그인</b>
        <p style="margin:0;font-size:13px;color:var(--muted);text-align:center" id="alertText">글쓰기·수정·삭제는 로그인 후에만 가능합니다</p>
      </div>
      <form class="login-form" method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="redirect" value="${param.redirect}">
        <input type="text" name="loginId" id="loginId" placeholder="아이디" required>
        <input type="password" name="loginPw" id="loginPw" placeholder="비밀번호" required>
        <button type="button" class="login-submit" name="loginCheck" id="loginCheck">로그인</button>
        <a class="login-back" href="${pageContext.request.contextPath}/Archive?cmd=archiveList">← 돌아가기</a>
      </form>
    </div>
  </div>
</body>
</html>
