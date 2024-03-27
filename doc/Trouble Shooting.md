# Trouble Shooting

### 고민

- 게시물 카테고리를 어떻게 관리할 것인가?
    - 문제
        - Enum을 이용하여 정해진 범위 안에서 카테고리 관리하길 원하는데 이걸 사용자에게 어떻게 목록을 보여주고 선택하게 할지, 받아왔을 때 어떻게 처리할 지 고민 필요
            - 목록을 보여주고 사용자가 선택하는 건 프론트 단에서 구현 예정
            - 사용자로부터 어떻게 받아올 것인지 고민이 필요
    - 해결
        - 프론트에서 String값으로 받아오는 것으로 결정
        - 해당 값을 Service 계층에서 Enum으로 변환(일치하는 값이 없을 때에는 예외처리)
        - DB에 저장할 때는 `@Enumerated(EnumType.*STRING*)`을 이용해 Enum → String으로 변환하여 저장
- 클라이언트에게서 받아온 DTO를 어느 레이어까지 전달하고 DTO-Entity 변환 작업은 어떤 레이어에서 해야하는 가?
  - DTO-Entity의 변환 작업을 Controller단에서 하게 될 경우 문제점
    1. View에 반환할 필요가 없는 데이터까지 Domain 객체에 포함되어 Controller(표현 계층)까지 넘어올 수 있음
    2. Controller가 여러 Domain 객체들의 정보를 조합해서 DTO를 생성해야 하는 경우, Service 로직이 Controller에 포함될 수 있음
    3. 또한 여러 Domain 객체들을 조회해야 하기 때문에 하나의 Controller가 의존하는 Service의 개수가 많아짐
    - Service 계층에서 DTO-Entity 변환 작업을 하게 되면 위 단점을 보완할 수 있다.
  - Service Layer의 역할
    - 마틴 파울러는 Service 레이어란 어플리케이션의 경계를 정의하고 비즈니스 로직 등 도메인을 캡슐화하는 역할이라고 정의했다.
    - 즉, 도메인을 보호하기 위함이므로 표현 계층인 Controller보다 Service 계층에서 변환 작업을 하는 것이 적합하다.

### 에러

- Web server failed to start. Port 8080 was already in use.
    - 인텔리제이가 비정상적으로 종료되었는데 어플리케이션 재실행 시 위와 같은 오류 발생.
    - 8080포트를 종료해줘야 함.
    - **해결 방법**
      - `lsof -i:8080` 명령어를 사용하여 8080 포트를 사용 중인 프로세스 번호(PID)를 확인
      - `kill -9 {PID}` 명령어로 해당 프로세스 종료
      - 이후 재실행하면 정상적으로 실행됨