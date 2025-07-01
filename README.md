<br />
<p align="center">
  <a href="https://github.com/your_username/spark-backend">
    <img src="https://user-images.githubusercontent.com/00000000/000000000-logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Spark 💖</h3>

  <p align="center">
    관심사 기반 소개팅 추천 플랫폼 - 백엔드
    <br />
    <a href="https://github.com/your_username/spark-backend"><strong>문서 보기 »</strong></a>
    <br />
    <br />
    <a href="#">데모 보기</a>
    ·
    <a href="https://github.com/your_username/spark-backend/issues">버그 제보</a>
    ·
    <a href="https://github.com/your_username/spark-backend/issues">기능 요청</a>
  </p>
</p>

---

## 📌 목차

- [프로젝트 소개](#프로젝트-소개)
- [사용 기술](#사용-기술)
- [시작하기](#시작하기)
  - [사전 준비](#사전-준비)
  - [설치 방법](#설치-방법)
- [개발 로드맵](#개발-로드맵)
- [연락처](#연락처)
- [참고 자료](#참고-자료)

---

## 🧠 프로젝트 소개

Spark는 관심사와 성향 기반의 **맞춤형 소개팅 추천** 서비스를 제공합니다. 실시간 채팅, 피드 공유, 좋아요/차단 기능 등으로 소통 기반의 만남을 제공합니다.

**주요 기능:**

- JWT 인증 기반 회원가입 / 로그인
- 관심사 기반 프로필 추천 알고리즘
- 실시간 채팅 (STOMP + WebSocket)

<p align="right">(<a href="#top">맨 위로</a>)</p>

---

## 🛠️ 사용 기술

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [MySQL](https://www.mysql.com/)
- [Redis](https://redis.io/)
- [JWT](https://jwt.io/)
- [MyBatis](https://mybatis.org/)
- [Docker](https://www.docker.com/)
- [GitHub Actions](https://github.com/features/actions)

<p align="right">(<a href="#top">맨 위로</a>)</p>

---

## 🚀 시작하기

### 사전 준비

- Java 17
- Maven
- Docker
- MySQL 8
- Redis

### 설치 방법
### Docker 실행 예시

```bash
# MySQL 실행
docker run -d \
  --name spark-db \
  -e MYSQL_ROOT_PASSWORD=yourpassword \
  -e MYSQL_DATABASE=spark \
  -p 3306:3306 \
  mysql:8.0

# Redis 실행
docker run -d --name spark-redis -p 6379:6379 redis:6

# 백엔드 이미지 빌드 및 실행
docker build -t spark-backend .
docker run -d -p 8080:8080 --name spark-app spark-backend
```

<p align="right">(<a href="#top">맨 위로</a>)</p>


## 📍 개발 로드맵

- [x] JWT 로그인/로그아웃 구현
- [x] 관심사 기반 추천 로직
- [ ] 피드 업로드 / 상세 조회
- [ ] 실시간 채팅 기능
- [ ] 관리자 기능 강화
- [ ] S3 이미지 업로드 연동
- [ ] CI/CD 자동화 (GitHub Actions + AWS)
- [ ] 테스트 코드 작성 및 테스트 환경 분리

<p align="right">(<a href="#top">맨 위로</a>)</p>


## 📬 연락처

**김영욱** - backend@example.com  
**박시우** - frontend@example.com

<p align="right">(<a href="#top">맨 위로</a>)</p>

---

## 🙏 참고 자료

- [Best README Template](https://github.com/othneildrew/Best-README-Template)


<p align="right">(<a href="#top">맨 위로</a>)</p>
