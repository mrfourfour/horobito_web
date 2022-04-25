# 웹소설 서비스 구현 프로젝트

### 내용 및 기능

- 현재 서비스중인 웹소설 사이트의 기능을 구현하고 싶어서 만든 토이 프로젝트입니다.
- 여러 도메인들의 기능들이 서로 연동되는 것을 목표로 한 프로젝트입니다.

### 사용 스킬 혹은 지식

- Java
- Spring Boot
- JPA
- MySQL

### 진행기간

- 2021.09.20~2021.02.18

### 기여

- 서버 전반의 개발을 맡았습니다.

### 프로젝트 시 특이사항

- 세부 기능
 1) 메인, 랭킹, 카테고리별 작품 조회 
 2) 조회 시 좋아요, 작성 시간 순으로 조회 가능하도록 구현 

- 직면했던 문제 및 해결 방법
 1) N+1 문제
   - 문제
     - 기간 별 랭킹 조회 시 N+1 발생
   - 해결 방안
     - 조회 전용 도메인 생성
     - 테이블 간 join 사용하여 조회 시 단번에 결과 조회    
