# Review

# 설명

모든 것을 리뷰하는 커뮤니티.

어떤 주제든 큰 분류 설정 후 자유롭게 리뷰할 수 있음(맛집, 여행지, 전자기기, 음식 등 모든 것)

# 기능

- 회원 관리
    - 회원가입 기능(아이디(중복 불가), 닉네임(중복 불가), 비밀번호)
    - 로그인
        - 아이디, 비밀번호를 통해 로그인
            - 로그인 시 회원 전용 기능(게시물, 댓글 관리) 사용 가능
        - 로그아웃
    - 회원정보 조회 기능
        - 아이디(변경 불가)
        - 닉네임 확인 및 변경(중복 불가)
        - 비밀번호 변경
        - 작성한 게시물, 댓글 개수 확인
- 게시물
    - 전체 게시물 조회 기능
        - 게시물 정렬 기능(최신순, 오래된 순, 댓글 많은 순, 댓글 적은 순)
        - 페이징 처리
    - 게시물 검색 기능(제목, 분류, 닉네임, 작성일 & 회원번호(회원 페이지에서 본인이 작성한 게시글 조회를 위해))
    - 로그인 시
        - 본인이 작성한 게시물 조회 기능(회원페이지에서 사용, 위에서 개발한 게시물 검색 기능 이용)
        - 게시물 작성, 수정, 삭제 기능
        - 회원별 게시글 개수 확인 api
- 댓글
    - 게시물 조회 시 전체 댓글 조회 기능
        - 페이징 처리
    - 로그인 시
        - 본인이 작성한 댓글 조회 기능(회원페이지에서)
        - 댓글 작성, 수정, 삭제 기능
        - 회원별 댓글 개수 확인 api
- 추후 추가 예정
    - 회원 등급 : 웰컴, 패밀리
        - 웰컴 : 회원가입 시 등급
        - 패밀리 : 게시물 10개, 댓글 10개 작성 시 자동으로 승급
        - 등급에 따른 혜택은 추후 고민 예정
        - 회원 등급 심사 기능
            - 심사 버튼을 누르면 회원의 게시물, 댓글 개수가 승급 기준에 맞는 지 확인 후 승급되는 기능
    - 추천, 비추천 : 게시물, 댓글에 대해 추천 및 비추천을 할 수 있는 기능
    - 삭제 시 30일이 지나면 DB에서도 삭제되는 기능
    - 회원 프로필 사진 기능
    - 게시물에 사진 첨부 기능
    - 카테고리 소분류 추가

# 페이지 구성 예상도 (실제 구현은 추후에)

- 홈 화면
    - 전체 게시물 조회(비회원도 가능, 게시물 클릭 시 게시물 화면으로 이동)
    - 로그인 버튼(로그인 페이지로 이동)
    - 로그인 시
        - 회원 관리 버튼(로그인 시 회원 페이지로 이동하는 기능)
        - 게시물 작성 버튼
        - 로그아웃 버튼
- 로그인 화면
    - 아이디, 비밀번호 입력란
    - 회원가입 버튼
- 회원가입 화면
    - 아이디, 닉네임, 비밀번호 입력
- 회원관리 화면
    - 아이디, 닉네임 확인 가능
    - 닉네임 변경
    - 비밀번호 변경
    - 본인이 작성한 게시물 목록 확인
    - 본인이 작성한 댓글 목록 확인
- 게시글 화면
    - 제목, 분류, 작성일, 작성자, 내용 표시
    - 댓글 목록 표시(내용, 작성자, 작성일)
    - 전체 목록 버튼(전체 목록으로 돌아가는 기능)
    - 게시물 검색 기능
    - 로그인 시
        - (본인 게시물일 경우)게시물 수정, 삭제 기능
        - 댓글 작성, 수정, 삭제 기능

# ERD
![Review.png](doc%2FReview.png)

## Trouble Shooting
추후 문제 발생 시 추가 예정

### Tech Stack

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">

<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">