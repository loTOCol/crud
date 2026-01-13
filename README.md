# Demo Project

Spring Boot 기반 Post CRUD API 프로젝트

## 환경 설정

### 개발 환경 (로컬)
```bash
# 기본값으로 dev 프로파일 사용
./gradlew bootRun
```

### 프로덕션 환경
환경변수를 설정하여 실행:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://your-db-host:3306/spring_crud?serverTimezone=Asia/Seoul
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

./gradlew bootRun
```

또는 JAR 실행:
```bash
java -jar -Dspring.profiles.active=prod \
  -DDB_URL=jdbc:mysql://your-db-host:3306/spring_crud \
  -DDB_USERNAME=your_username \
  -DDB_PASSWORD=your_password \
  build/libs/demo-0.0.1-SNAPSHOT.jar
```

## Docker 사용법

### 로컬 개발용 (DB와 Redis만 실행)
```bash
# MySQL과 Redis만 Docker로 실행 (애플리케이션은 로컬에서 실행)
docker-compose -f docker-compose.dev.yml up -d

# 중지
docker-compose -f docker-compose.dev.yml down
```

### 전체 Docker 실행 (애플리케이션 포함)
```bash
# 빌드 및 실행
docker-compose up -d --build

# 로그 확인
docker-compose logs -f app

# 중지
docker-compose down

# 볼륨까지 삭제
docker-compose down -v
```

### Docker 이미지 빌드만
```bash
docker build -t demo-app:latest .
```

## AWS EC2 배포

자세한 배포 가이드는 [DEPLOY.md](DEPLOY.md)를 참고하세요.

### 빠른 배포
```bash
# 1. EC2 초기 설정 (최초 1회)
./scripts/setup-ec2.sh

# 2. 프로젝트 클론 후 배포
git clone <your-repo>
cd <your-repo>
./scripts/deploy.sh
```

## API 엔드포인트

- `POST /posts` - 게시글 생성
- `GET /posts` - 게시글 목록 조회
- `GET /posts/{id}` - 게시글 단건 조회
- `PUT /posts/{id}` - 게시글 수정
- `DELETE /posts/{id}` - 게시글 삭제
