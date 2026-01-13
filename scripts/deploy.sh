#!/bin/bash

# 배포 스크립트
# EC2 서버에서 실행

set -e  # 에러 발생 시 스크립트 중단

echo "=== 배포 시작 ==="

# 프로젝트 디렉토리로 이동
cd "$(dirname "$0")/.." || exit

# Git 최신 코드 가져오기
echo "1. Git 최신 코드 가져오기..."
git pull origin main || git pull origin master

# Docker Compose로 빌드 및 실행
echo "2. Docker 이미지 빌드 및 컨테이너 실행..."
docker-compose down
docker-compose build --no-cache
docker-compose up -d

# 컨테이너 상태 확인
echo "3. 컨테이너 상태 확인..."
sleep 5
docker-compose ps

# 로그 확인
echo "4. 애플리케이션 로그 확인..."
docker-compose logs --tail=50 app

echo ""
echo "=== 배포 완료 ==="
echo "애플리케이션 로그 확인: docker-compose logs -f app"
echo "컨테이너 상태 확인: docker-compose ps"
