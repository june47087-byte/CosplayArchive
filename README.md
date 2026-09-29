# 코스프레 기록 아카이브용 블로그

"예전에 했던 코스프레, 하고싶은 계획, 정보글, 코스프레 이벤트의 일자를 모으기 위한 블로그"
<br>

## 목차
- [프로젝트 정보](#프로젝트-정보)
- [기획 배경](#기획-배경)
- [주요 기능](#주요-기능-features)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#시스템-아키텍처)
- [ERD](#erd)
- [프로젝트 구조](#프로젝트-구조)
- [실행 방법](#실행-방법)
- [회고](#회고)

<br>

## 프로젝트 정보

| 항목 | 내용 |
|---|---|
| **개발 기간** | 2026.08.25 ~ 2026.09.01 |
| **개발 인원** | 1인 (개인 프로젝트) |
| **담당** | 기획 · DB 설계 · Backend · Frontend 전체 |
| **배포** | [cosplay-archive.herokuapp.com](https://cosplay-archive-f8903be177b6.herokuapp.com/) (Heroku) |
| **GitHub** | [@june47087-byte](https://github.com/june47087-byte) |

<br>

## 기획 배경
- 코스프레 기록은 SNS 피드에 올리면 시간순으로 묻히고 코스프레 전용 계정으로 사용하고 있지 않기에 코스프레 사진만 모아보기 힘들고 정보도 모이기 어렵고 또한 트위터 검색 알고리즘의 한계로 인해 정보를 찾기도 힘듭니다.<br>
그래서 코스프레 전용 아카이브 블로그를 만들기로 했습니다.


<br>

## 주요 기능 (Features)

### 🏠 홈 (연도별 아카이브)
<br>
<img width="1901" height="1032" alt="Image" src="https://github.com/user-attachments/assets/5ebc348b-3953-4006-a962-54d14f2772f8" />
<br>
- 그동안의 코스프레 기록(사진 · 사진사 · 참여 행사)을 연도별 아코디언으로 묶어서 표시
- 연도별 페이지네이션, 게시물 클릭 시 오른쪽 패널에 상세 표시
- 등록 · 수정 · 삭제 (이미지 업로드 포함)

<!-- <img src="docs/images/archive.png" width="700"> -->

### 🎪 행사
<br>
<img width="1901" height="1032" alt="Image" src="https://github.com/user-attachments/assets/663f4dff-e940-4032-8372-63aa56b94648" />  
<br>
- 지역(서울 · 부산 · 전라 · 기타) 카드별 행사 목록 관리
- 지역 클릭 시 오른쪽 패널에 해당 지역 행사 상세 표시
- 등록 · 수정 · 삭제

<!-- <img src="docs/images/event.png" width="700"> -->

### ⭐ 위시
<br>
<img width="1901" height="1032" alt="Image" src="https://github.com/user-attachments/assets/129b4dca-f448-403d-a436-03d35fca5495" />
<br>
- 하고 싶은 코스프레를 완료 / 미완료 상태로 관리
- 위시 하나당 참고할 샵(구매처) 목록을 별도로 첨부 · 수정 · 삭제
- 탭바로 상태별 필터링

<!-- <img src="docs/images/wish.png" width="700"> -->

### 📝 플랜

<img width="1920" height="988" alt="Image" src="https://github.com/user-attachments/assets/570ddcab-76fe-47bd-b353-2096a7f0d986" />

- 확정된 코스프레 계획을 완료 / 미완료 + 요일(토 · 일 · 양일)로 관리
- 이미지 업로드 지원

<!-- <img src="docs/images/plan.png" width="700"> -->

### ℹ️ 정보
<br>
<img width="1901" height="1032" alt="Image" src="https://github.com/user-attachments/assets/5ba8ad45-eaf4-4fbe-99b9-8e4f72fb8187" />
<br>
- 세팅 · 제작 · 자세 · 샵 · 기타 카테고리별 노하우 아카이브
- 게시글마다 댓글식으로 추가 메모(+URL) 기록 · 수정 · 삭제

<!-- <img src="docs/images/info.png" width="700"> -->

<br>

## 🛠 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![JSP](https://img.shields.io/badge/JSP%20%2F%20Servlet-F8981D?style=for-the-badge&logo=java&logoColor=white)
![JSTL](https://img.shields.io/badge/JSTL-5382A1?style=for-the-badge)

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
![AJAX](https://img.shields.io/badge/AJAX-4B8BBE?style=for-the-badge)

### DB & WAS
![Oracle](https://img.shields.io/badge/Oracle%2021c-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Tomcat](https://img.shields.io/badge/Tomcat%209.0-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)

### Tools
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white)
![SQL Developer](https://img.shields.io/badge/SQL%20Developer-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

<br>

## 시스템 아키텍처

<img width="799" height="598" alt="Image" src="https://github.com/user-attachments/assets/67e5a771-eb39-4e5a-b7b4-2fad316656c0" />

<br>

## ERD

<img width="1282" height="742" alt="Image" src="https://github.com/user-attachments/assets/d2b8478f-e05c-4c36-a718-5f3fd1c72acb" />

<br>
| 테이블 | 설명 | 컬럼 |
|---|---|---|
| `picture` | 홈에 올라가는 코스프레 기록 | `pic_id`(PK), `pic_name`, `pic_file`, `photographer`, `pic_event`, `pic_year` |
| `event` | 지역별 행사 | `event_id`(PK), `event_name`, `event_region`, `event_day`, `event_place` |
| `wish` | 하고 싶은 코스프레 | `wish_id`(PK), `wish_name`, `wish_file`, `wish_comment`, `wish_switch` |
| `wish_chat` | 위시 하나에 딸린 샵(구매처) 목록, `wish` 1 : N | `wish_chat_id`(PK), `wish_id`(FK), `wish_chat_url`, `wish_chat_contents` |
| `plan` | 확정된 코스프레 계획 | `plan_id`(PK), `plan_name`, `plan_file`, `plan_comment`, `plan_switch`, `plan_day` |
| `info` | 카테고리별 노하우 게시글 | `info_id`(PK), `info_div`, `info_name`, `info_contents`, `info_url` |
| `info_chat` | 정보 게시글에 딸린 댓글식 메모, `info` 1 : N | `info_chat_id`(PK), `info_id`(FK), `info_chat_comment`, `info_chat_url` |

> `picture` · `event` · `plan`은 서로 FK 없이 완전히 독립된 테이블입니다.

<br>

## 프로젝트 구조

```
CospalyArchive/
├── src/main/
│   ├── java/com/cosplay/archive/
│   │   ├── controller/         # 메뉴별 Servlet (Archive, Cosplay, Event, Info, Plan, Wish)
│   │   ├── service/
│   │   │   ├── Archive/        # 홈(사진) 등록·수정·삭제
│   │   │   ├── DetailIndex/    # 랜딩·로그인·로그아웃
│   │   │   ├── Event/          # 행사 등록·수정·삭제
│   │   │   ├── Plan/           # 플랜 등록·수정·삭제
│   │   │   └── Wish/           # 위시 · 샵 등록·수정·삭제
│   │   ├── servie/Info/        # 정보 목록·상세·등록·수정·삭제·댓글
│   │   ├── model/              # DAO / DTO
│   │   └── util/               # DBManager (DB 연결)
│   └── webapp/
│       ├── common/             # 공통 사이드바
│       ├── css/                # 스타일시트
│       ├── Archive/ Event/ Info/ Plan/ Wish/  # 메뉴별 목록 + rightbar(상세) JSP
│       ├── DetailIndex/        # 랜딩·로그인 화면
│       └── WEB-INF/lib/        # ojdbc8, commons-fileupload, commons-io, JSTL(taglibs-standard)
└── README.md
```

<br>

## 실행 방법

### 요구 사항
- JDK 8 이상, Apache Tomcat 9.0, Oracle Database (XE 등), Eclipse (Dynamic Web Project)
- 필요한 라이브러리(ojdbc8, commons-fileupload, commons-io, JSTL)는 `src/main/webapp/WEB-INF/lib/`에 포함되어 있습니다.

### 1. DB 준비
`util/DBManager.java`에서 접속 정보를 본인 환경에 맞게 수정합니다.

```java
String myURL  = "jdbc:oracle:thin:@localhost:1521:xe";
String myID   = "<계정>";
String myPass = "<비밀번호>";
```

아래 7개 테이블과, 각 테이블의 PK 채번용 시퀀스가 필요합니다. (테이블 컬럼은 [ERD](#erd) 참고)

| 테이블 | 시퀀스 |
|---|---|
| `picture` | `seq_picture` |
| `event` | `seq_event` |
| `wish` / `wish_chat` | `seq_wish` / `seq_wish_chat` |
| `plan` | `seq_plan` |
| `info` / `info_chat` | `seq_info` / `seq_info_chat` |

### 2. 프로젝트 실행
1. 이 저장소를 clone 한 뒤 Eclipse에서 **Existing Projects into Workspace**로 가져옵니다.
2. Tomcat 9.0 서버에 프로젝트를 추가하고 실행합니다.
3. 브라우저에서 `http://localhost:8080/<컨텍스트 경로>/` 로 접속하면 `index.jsp`가 `/Cos?cmd=index`로 forward 되어 랜딩 페이지가 열립니다.

> 업로드한 이미지는 각 메뉴의 `webapp/<메뉴>/upload/` 폴더에 저장됩니다.

### 3. 웹 배포
https://cosplay-archive-f8903be177b6.herokuapp.com/로 배포 하였습니다.
<br>

## 회고

- **레이아웃 언어 통일의 힘** : 메뉴마다 다르게 짜지 않고 "사이드바 20% · 목록 30% · 상세 50%" 하나의 3단 컬럼 구조를 정해두고 5개 메뉴 전부에 반복 적용하니, 기능이 늘어나도 화면이 낯설지 않고 유지보수도 쉬웠습니다.
- **기준 패턴을 먼저 완성하고 이식하기** : 정보(Info) 메뉴를 가장 먼저 끝까지 완성해 패턴으로 삼고, 그다음 홈 · 행사 · 위시 · 플랜에 같은 패턴(등록/수정/삭제 + data-속성 기반 인라인 수정폼)을 이식하는 방식으로 진행하니 반복 작업의 속도가 빨라졌습니다.
- **1:N 관계 정리 경험** : 위시-샵(wish_chat), 정보-댓글(info_chat)처럼 부모를 지우면 자식도 같이 지워야 하는 관계를 직접 처리하며 DAO 설계와 삭제 순서에 대한 감을 잡을 수 있었습니다.
- **아쉬운 점** : 로그인 여부를 화면(JSP)에서만 판단하고 서버(Servlet) 쪽에서는 별도로 검증하지 않아, 다음 버전에서는 서버 측 인가 체크를 보완하고 싶습니다.
                  하드코딩된 id&pw는 com\cosplay\archive\service\DetailIndex\LoginProService에 있습니다. 

<br>

---
Copyright © 2026 june47087-byte. All rights reserved.
