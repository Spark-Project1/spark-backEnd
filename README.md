<br />
<p align="center">
  <a href="https://github.com/your_username/spark-backend">
    <img src="https://github.com/user-attachments/assets/f8159db0-937d-4b0e-a426-a4d299aadf31" alt="Logo" width="80" height="80">
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

- [프로젝트 소개]
- [사용 기술]
- [협업 및 작업 방식]
- [시작하기]
  - [사전 준비]
  - [설치 방법]
- [개발 로드맵]
- [연락처]
- [참고 자료]

---

## 🧠 프로젝트 소개

Spark는 관심사와 성향 기반의 **맞춤형 소개팅 추천** 서비스를 제공합니다. 실시간 채팅, 피드 공유, 좋아요/차단 기능 등으로 소통 기반의 만남을 제공합니다.

**주요 기능:**

- JWT 인증 기반 회원가입 / 로그인
- 관심사 기반 프로필 추천 알고리즘
- 실시간 채팅 (STOMP + WebSocket)



---

## 🛠️ 사용 기술

- [Spring Boot]
- [Java 17]
- [MySQL]
- [Redis]
- [JWT]
- [MyBatis]
- [Docker]
- [GitHub Actions]

---


## 👥 협업 및 작업 방식

- GitHub 저장소의 Push / Pull Request / Issue Comment 등의 이벤트 발생 시,  
  **Webhook을 이용하여 Discord 채널로 실시간 알림**을 전송
- 모든 팀원이 코드 변경 사항과 PR 흐름을 빠르게 공유하고 확인
- 작업 흐름 자동화 및 커뮤니케이션 강화를 위해 해당 방식으로 운영


🔒 브랜치 보호 규칙

프로젝트의 안정성을 위해 다음과 같은 브랜치 보호 규칙을 설정했습니다

main 브랜치에는 직접 푸시(Push)가 금지되어 있습니다.

모든 변경 사항은 Pull Request(PR) 를 통해 병합해야 합니다.

PR은 반드시 아래 조건을 만족해야 병합할 수 있습니다:

✅ 최소 1명 이상의 승인 필요

✅ CODEOWNERS에 지정된 사용자(@kimyeongeuk)의 리뷰 승인 필수

✅ PR에 새로운 커밋이 추가되면 기존 승인은 무효화됨

✅ PR에 남긴 모든 코드 리뷰 코멘트가 해결되어야 함

✅ 관리자도 보호 규칙을 우회할 수 없음

CODEOWNERS 설정을 통해 @kimyeongeuk 계정만이 최종 승인 권한을 가집니다.



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




## 📍 개발 로드맵

- [x] JWT 로그인/로그아웃 구현
- [x] 관심사 기반 추천 로직
- [ ] 피드 업로드 / 상세 조회
- [ ] 실시간 채팅 기능
- [ ] 관리자 기능 강화
- [ ] S3 이미지 업로드 연동
- [ ] CI/CD 자동화 (GitHub Actions + AWS)
- [ ] 테스트 코드 작성 및 테스트 환경 분리




## 📬 연락처

**김영욱** - tklr0731@naver.com  
**박시우** - frontend@example.com



---

## 🙏 참고 자료

- [Best README Template](https://github.com/othneildrew/Best-README-Template)


