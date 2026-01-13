#!/bin/bash

# EC2 초기 설정 스크립트
# EC2 인스턴스에 처음 접속했을 때 한 번만 실행

echo "=== EC2 초기 설정 시작 ==="

# 시스템 업데이트
echo "1. 시스템 업데이트 중..."
sudo yum update -y

# Docker 설치
echo "2. Docker 설치 중..."
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

# Docker Compose 설치
echo "3. Docker Compose 설치 중..."
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Git 설치
echo "4. Git 설치 중..."
sudo yum install -y git

# Java 설치 확인 (Docker 이미지에 포함되므로 선택사항)
echo "5. 기본 도구 설치 완료"

echo ""
echo "=== 설정 완료 ==="
echo "다음 단계:"
echo "1. 로그아웃 후 다시 로그인 (Docker 그룹 적용)"
echo "2. 프로젝트 클론: git clone <your-repo-url>"
echo "3. 배포 스크립트 실행: ./scripts/deploy.sh"
