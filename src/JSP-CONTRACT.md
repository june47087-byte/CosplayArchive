# View 레이어 연동 가이드

`WebContent/index.jsp`와 `WebContent/WEB-INF/views/*.jsp`만 만들어뒀습니다.
DTO, Service(DAO), Controller(Servlet)는 직접 구현하시면 되고, 아래는 각 JSP가
기대하는 request 속성 이름 / getter 이름 / URL 규칙입니다. Controller와 DTO를
이 이름에 맞춰 만들면 JSP 수정 없이 바로 연동됩니다.

공통
- 모든 JSP는 `WEB-INF/views/common/sidebar.jsp`, `common/rightbar.jsp`를 include합니다.
- sidebar는 `session` 속성 `loginId`(String)로 로그인 여부를 판단합니다. 로그인 성공 시
  `session.setAttribute("loginId", 아이디)`, 로그아웃 시 `session.invalidate()` 또는 해당 속성 제거.
- sidebar의 메뉴 활성화 표시는 request 속성 `activeMenu`(문자열: home/archive/event/wish/plan/info)를 봅니다.
- rightbar(활동 요약)는 request 속성 `yearStats` — `getYear()`, `getCount()`를 가진 리스트.
- WEB-INF 안의 JSP는 브라우저에서 직접 열 수 없습니다(의도된 것). 반드시 Servlet에서
  `request.getRequestDispatcher("/WEB-INF/views/xxx.jsp").forward(request, response)`로 진입해야 합니다.
- JSTL(core) 태그를 쓰므로 `WEB-INF/lib`에 jstl 라이브러리가 필요합니다.

## index.jsp (공개, WebContent 바로 아래)
- 선택적 request 속성: `totalPosts`, `totalYears`, `totalCosplays` (없으면 0으로 표시)
- "입장하기" 버튼 → `/home` (GET)

## home.jsp — activeMenu = "home"
- `postList`: `getId, getYear, getDate, getCategory, getTitle, getSummary, getImageColor, getLikes, getReposts, getCommentCount`
- `yearList`: `List<Integer>` (postList에 존재하는 연도 목록, 중복제거·내림차순 권장)
- `selectedYear`: 현재 선택된 연도 필터(문자열) 또는 비어있으면 "전체"
- URL: `GET /home`, `GET /home?year=2026`

## archive.jsp — activeMenu = "archive"
- `archiveGroups`: `getYear()`("2026년" 형태 문자열), `getCount()`, `getPosts()`(`getId, getTitle, getDate, getCategory, getImageColor`)
- `query`: 검색어(문자열, 없으면 빈 문자열)
- URL: `GET /archive`, `GET /archive?query=검색어`

## postDetail.jsp (사이드바 활성 메뉴 없음)
- `post`: `getId, getTitle, getBody, getCategory, getDate, getYear, getSubject, getSeries, getPlace, getLikes, getReposts, getCommentCount, getImageColor`
- URL: `GET /post?id=1`

## event.jsp — activeMenu = "event"
- `eventList`: `getId, getName, getRegion, getPeriod, getPlace`
- `selectedRegion`: 전체/경기/전라/경상
- `showForm`(boolean), `showForm=true`일 때 `formEvent`: `getId(빈값=신규), getName, getRegion, getPeriod, getPlace`
- URL:
  - `GET /event`, `GET /event?region=경기`
  - `GET /event?action=write` — 빈 폼 오픈
  - `GET /event?action=edit&id=3` — 수정 폼 오픈(값 채워서)
  - `POST /event?action=save` — id 파라미터가 비어있으면 등록, 있으면 수정 → 처리 후 `/event`로 redirect
  - `GET /event?action=delete&id=3` — 삭제 → 처리 후 `/event`로 redirect

## wish.jsp — activeMenu = "wish"
- `wishList`: `getId, getCharacterName, getStatus, getPhotoColor(CSS background 문자열), getComments()`(각 원소 `getContent()`, `getUrl()`)
- `selectedStatus`: 전체/미완/완료
- `showForm`(boolean), `formWish`: `getId, getCharacterName, getStatus, getPhotoColor`
- URL: event.jsp와 동일 패턴(`/wish`) + 댓글: `POST /wish?action=comment&id=3` (파라미터 `content`, `url` — `url`은 선택값이라 비어있을 수 있음)

## plan.jsp — activeMenu = "plan"
- `planList`: `getId, getCharacterName, getEventName, getRegion, getPeriod, getStatus, getPhotoColor`
- `selectedStatus`: 전체/미완/완료
- `showForm`(boolean), `formPlan`: `getId, getCharacterName, getEventName, getRegion, getPeriod, getStatus, getPhotoColor`
- URL: event.jsp와 동일 패턴(`/plan`)

## info.jsp — activeMenu = "info"
- `infoList`: `getId, getCategory, getTitle, getBody, getUrl, getComments()`(각 원소 `getContent()`)
- `selectedCategory`: 세팅/제작/자세/샵 (전체 탭 없음)
- `showForm`(boolean), `formInfo`: `getId, getCategory, getTitle, getBody, getUrl`
- URL: event.jsp와 동일 패턴(`/info`) + 댓글: `POST /info?action=comment&id=3` (파라미터 `content`)

## login.jsp (사이드바 없음)
- `error`: 선택적, 로그인 실패 메시지
- 폼 파라미터: `loginId`, `loginPw`, `redirect`(로그인 후 돌아갈 경로, 없으면 `/home`으로)
- URL: `GET /login` (폼 표시), `POST /login` (인증 처리)
- 로그아웃: `POST /logout`

## 색상 값 규칙 (photoColor / imageColor)
JSP는 이 값을 그대로 `style="background:..."`에 꽂아 넣습니다. 즉 DB에는
`linear-gradient(135deg,#845ef7,#5f3dc4)` 같은 완성된 CSS 값을 저장하거나,
Service 단에서 조합해서 넘겨주면 됩니다.
