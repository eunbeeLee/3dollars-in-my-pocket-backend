# 가슴속 삼천원 백엔드

![Generic badge](https://img.shields.io/badge/version-2.0.0-green.svg)
[![codecov](https://codecov.io/gh/depromeet/3dollars-in-my-pocket-backend/branch/develop/graph/badge.svg?token=QZPVF6VGHA)](https://codecov.io/gh/depromeet/3dollars-in-my-pocket-backend)

## Installation

### with gradlew

```bash
./gradlew clean build
java -jar threedollar-api/build/libs/threedollar-api.jar 
```

### with docker-compose

```bash
docker-compose up --build
```

## Tech Stacks
### Language & Framework
- Java 11, Kotlin 1.5
- Spring Boot 2.3 + Spring MVC
- Spring Data JPA + QueryDSL 4.3
- Gradle 7.0
- Junit 5

## Infra
### Production Environment
> 차후 구성할 예정입니다.

- AWS RDS (MariaDB 10.4)
- flyway
- AWS ElasticCache Redis
- Github Actions CI/CD


### Development Environment
> AWS 프리티어 내에서 비용 없이 구성하였습니다.
- AWS EC2
- AWS RDS (MariaDB 10.4)
- flyway
- Nginx
- Docker compose
- Redis
- Github Actions CI/CD
