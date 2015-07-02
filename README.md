# takeoff-web-spring
Web 서비스를 만들 때 spring 으로 쉽게 시작할 수 있는 뼈대를 만드는 프로젝트. 

# 프로젝트 목표
- 프로젝트 초반 세팅을 Skip 할 수 있게 한다.
- Production 레벨의 Skeleton 을 제공한다.
- Clean Code, Best Practice 를 지향한다.
- 기술 스택의 Stable 한 버전 중 최신 버전을 지원한다.

# 서비스 구현 기능
- 회원가입(Form), 회원가입(Social)
- 로그인(Form), 로그인(Social), Remember-me
- 메일 인증, 비밀번호 찾기
- 글 리스트
- 댓글 리스트
- 글 & 댓글 좋아요
- 태그
- 노티피케이션(실시간 알람)
- 관리자 페이지


# 기술 스택
**Server Side**
- spring-boot
- spring-security
- spring-jpa

**Client Side**
bootstrap

# Required
- JDK 1.8
- lombok plugin

# 실행하기
mvn spring-boot:run

# IntelliJ 에서 실행하기
Edit Configurations -> '+' 버튼 -> Spring boot -> Main Class : com.sungmook.Application