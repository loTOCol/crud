# AWS EC2 배포 가이드

## 1. EC2 인스턴스 생성

### 1.1 AWS 콘솔에서 EC2 인스턴스 생성
1. AWS 콘솔 → EC2 → 인스턴스 시작
2. Amazon Linux 2023 선택 (또는 Ubuntu 22.04)
3. 인스턴스 타입: t2.micro (프리티어) 또는 t3.small
4. 키 페어 생성/선택 (SSH 접속용)
5. 보안 그룹 설정:
   - SSH (22): 내 IP
   - HTTP (80): 0.0.0.0/0
   - HTTPS (443): 0.0.0.0/0
   - 커스텀 TCP (8080): 0.0.0.0/0 (애플리케이션 포트)
6. 인스턴스 시작

### 1.2 Elastic IP 할당 (선택사항)
- EC2 → Elastic IP → 할당 → 인스턴스에 연결
- 고정 IP 주소 사용 시 필요

## 2. EC2 초기 설정

### 2.1 SSH 접속
```bash
# Windows (PowerShell)
ssh -i "your-key.pem" ec2-user@your-ec2-ip

# Mac/Linux
chmod 400 your-key.pem
ssh -i your-key.pem ec2-user@your-ec2-ip
```

### 2.2 초기 설정 스크립트 실행
```bash
# 스크립트에 실행 권한 부여
chmod +x scripts/setup-ec2.sh

# 실행
./scripts/setup-ec2.sh
```

### 2.3 로그아웃 후 재접속
```bash
exit
ssh -i your-key.pem ec2-user@your-ec2-ip
```

## 3. 프로젝트 배포

### 3.1 Git 저장소 클론
```bash
cd ~
git clone https://github.com/your-username/your-repo.git
cd your-repo
```

또는 프로젝트 파일을 직접 업로드:
```bash
# SCP로 파일 전송 (로컬에서 실행)
scp -i your-key.pem -r . ec2-user@your-ec2-ip:~/demo
```

### 3.2 환경변수 설정 (필요시)
```bash
# .env 파일 생성 (선택사항)
nano .env

# 또는 환경변수 직접 설정
export DB_URL=jdbc:mysql://mysql:3306/spring_crud
export DB_USERNAME=demo_user
export DB_PASSWORD=your_secure_password
export REDIS_HOST=redis
export REDIS_PORT=6379
```

### 3.3 배포 실행
```bash
chmod +x scripts/deploy.sh
./scripts/deploy.sh
```

## 4. 배포 확인

### 4.1 컨테이너 상태 확인
```bash
docker-compose ps
```

### 4.2 로그 확인
```bash
# 애플리케이션 로그
docker-compose logs -f app

# 모든 서비스 로그
docker-compose logs -f
```

### 4.3 헬스 체크
```bash
# 로컬에서 테스트
curl http://your-ec2-ip:8080/posts

# 또는 브라우저에서 접속
http://your-ec2-ip:8080/posts
```

## 5. 업데이트 배포

코드 변경 후 재배포:
```bash
cd ~/your-repo
./scripts/deploy.sh
```

## 6. 문제 해결

### 컨테이너가 시작되지 않는 경우
```bash
# 로그 확인
docker-compose logs app

# 컨테이너 재시작
docker-compose restart app

# 완전히 재시작
docker-compose down
docker-compose up -d
```

### 포트 충돌
```bash
# 포트 사용 확인
sudo netstat -tulpn | grep 8080

# 다른 포트 사용 시 docker-compose.yml 수정
```

### 메모리 부족
```bash
# 메모리 확인
free -h

# Docker 리소스 정리
docker system prune -a
```

## 7. 보안 설정 (프로덕션)

### 7.1 방화벽 설정 (UFW - Ubuntu)
```bash
sudo ufw allow 22/tcp
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw allow 8080/tcp
sudo ufw enable
```

### 7.2 Nginx 리버스 프록시 (선택사항)
- 80/443 포트로 접속 → Nginx → 8080 포트로 프록시
- SSL 인증서 설정 가능

### 7.3 데이터베이스 보안
- 프로덕션에서는 RDS 사용 권장
- 또는 외부 접속 차단, 백업 설정

## 8. 자동 배포 (CI/CD) - 선택사항

### GitHub Actions 예시
```yaml
# .github/workflows/deploy.yml
name: Deploy to EC2
on:
  push:
    branches: [main]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Deploy to EC2
        uses: appleboy/ssh-action@master
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ec2-user
          key: ${{ secrets.EC2_SSH_KEY }}
          script: |
            cd ~/your-repo
            ./scripts/deploy.sh
```
