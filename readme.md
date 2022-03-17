# 공모주토피아 백엔드 서버 개발

### 백엔드 개요

- 기준 서비스 인원 : 10만명
- 개발환경
    - Window 10
    - IntelliJ 2021.2.3 (Ultimate Edition) (SpringToolSuite4)
    - HeidiSQL
    - putty / winscp
    - Tomcat10
    - postman
- Code Convention
    - 변수명은 camelCase로 작성한다. (Database 제외)
    - 시작 중괄호는 "{" 따로 한줄을 차지하지 않는다.
- 백엔드 측 분류
    - RESTful API Server (Spring, GCP)
    - 비동기성 Web Cralwer (Python3, Rpi)
    - Database (Maraia DB)

### 백엔드 측 분류 (기술)

- RESTful API Server
    - Spring Boot 2.6.2 (Gradle)
    - lombok
    - Thymeleaf
    - Mybatis
    - Hikari (connection pool)
    - Swagger 2.9.2
    - AOP (TransactionAspect, LoggerAspect)
    - WebClient (HttpConnection)
- Database
    - MariaDB (JDBC)
    - Mysql / HeidiSQL
- 비동기성 외부 로직
    - Python3.9
    - 동작 시스템 : GCP or Rpi
    - Selenium (Web Cralwer)
    - pyMySql
- Google Compute Engine
    - CentOS Linux release 8.4.2105
- 배포 시스템
    - Docker
    - Jenkins

