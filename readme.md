# 공모주토피아 백엔드 서버 개발

## 📙 프로젝트 개요

**프로젝트 설명**

- 크롤링된 공모주 정보를 Client에게 Restful API로 전달 및 관리
- 공모주 관련 플랫폼을 구축하는 것이 목표인 백엔드 프로젝트

**개발 배경**
- _

**개발 인원**
- RestAPI Backend 1인
- Android App 1인
- Web Frontend 1인
- 크롤러 공통 3인


### 💻 프로젝트 내용

**담당 업무 - SpringBoot Backend 개발**

- 개발 환경 : Window10, Chrome Browser, Whale Broswer, Edge Browser
- 사용 기술 : html, css, js, chrome-extension

## 개발 환경
### 개발 환경
- 개발환경
    - Window & Mac
    - IntelliJ 2021.2.3 (Ultimate Edition)
    - HeidiSQL & DataGrip
    - putty / winscp
    - postman
- Code Convention
    - 변수명은 camelCase로 작성한다. (Database 제외)
    - 시작 중괄호는 "{" 따로 한줄을 차지하지 않는다.
- 백엔드 측
    - RESTful API Server (Spring, GCP)
    - 비동기성 Web Cralwer로 부터 데이터 수집 (Python3, Linux)
    - Database (Maraia DB)


### 기술 스택
- RESTful API Server
    - Spring Boot 2.6.2 (Gradle)
    - lombok
    - Mybatis
    - Hikari (connection pool)
    - Swagger 2.9.2
    - AOP (TransactionAspect, LoggerAspect)
    - WebClient (HttpConnection)
- Database
    - MariaDB (JDBC)
- 크롤러 (기업 공시 데이터 수집)
    - Python3.9
    - OS : Linux Debian
    - BS4 (Web Cralwer)
    - pyMySql
- ServerComputer : Google Compute Engine
    - CentOS Linux release 8.4.2105



## 🎈 기타
- _
