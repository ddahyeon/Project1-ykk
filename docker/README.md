# Docker 실행 

- Docker 이미지 빌드

```bash
docker build -f 파일 -t 이미지/폴더
```
ex) docker build -f docker/Dockerfile.backend -t backend:v1 ./backend
* `-f docker/Dockerfile.backend` : 사용할 Dockerfile 위치
* `-t backend:v1` : 생성할 이미지 이름
* `./backend` : 백엔드 프로젝트 폴더

- 이미지 생성 확인

```bash
docker images
```


- 컨테이너 테스트

```bash
docker run -p 8080:8080 이미지명
```

http://localhost:8080


- Docker Compose로 MySQL + Backend 실행

```bash
docker compose -f docker/docker-compose.yml up -d
```

- 컨테이너 확인

```bash
docker ps
```

- 로그 확인

```bash
docker logs project-backend
docker logs project-mysql
```

- 종료

```bash
docker compose -f docker/docker-compose.yml down
```

- Docker Hub에 업로드

Docker Hub 로그인:

```bash
docker login
```

- Docker Hub용 이미지 이름 붙이기

```bash
docker tag 이미지명 dahyeoni/이미지명
```

Docker Hub에 push:

```bash
docker push dahyeoni/이미지명
```

- 이미지 pull 받기

```bash
docker pull dahyeoni/이미지명
```

- 실행

```bash
docker run -p 8080:8080 dahyeoni/이미지명
```

- Kubernetes에서 사용할 이미지 

```yaml
image: dahyeoni/이미지명
```
