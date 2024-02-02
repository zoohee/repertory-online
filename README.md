# Project : Repertory (Bboy repertory making tool)

### team : Lucky Turkey(A707) // 브랜치별로 나눠서 개발중입니다. 브랜치 살펴봐주시면 감사하겠습니다

---

# 업무 분담

### 안준현 : Team Leader, BE

#### 담당업무 (update at 2024.02.02)

- 기획, 팀 운영
- 멤버 서버 (유저 관련 서비스)


#### 업무 상세 - 기획

- **완료**
    - 요구사항 정의
    - 요구사항 명세서 작성
    - api 명세서 작성
    - 화면 기능 정의서 작성

#### 업무 상세 - 멤버 서버

- **완료**
    - 자체 회원가입 구현
    - 자체 로그인 구현
    - 로그인 성공시 아이디와 권한이 담긴 토큰 부여
    - 소셜 로그인 시 db에 존재하지 않는 사용자면 회원가입 구현
    - 소셜 로그인 구현
- **진행중**
    - 소셜로그인 테스트
- **해야할 일**
    - 기타 유저 api 구현
    - 서버간 요청 api 구현

### 이주희 : CI/CD, BE(Community Service)

#### ~2024.02.02 한 일

- CI/CD 파이프라인 구축 완료
    - Back-end 프로젝트 젠킨스 연동
    - 파이프라인 설명
        1. GitHub에서 푸쉬 감지하여 젠킨스로 Webhook 전송
        2. 젠킨스에서 Gradle 빌드
        2. Git에서 변동된 microservice를 감지해서 microservice 별로 이미지화
        3. 해당 서비스 이름으로 dockerhub에 push
        4. 이미지 pull 받기 전에 서비스 이름으로 생긴 image와 container 전부 삭제
        5. 불필요한 이름을 가진 image 전부 삭제
        6. 변동된 microservice docker container 새로 생성 및 실행
    - 젠킨스 파이프라인 첨부 (리팩토링 필요)
```
pipeline {
    agent any

    environment {
        releaseServerAccount = 'ubuntu'
        releaseServerUri = 'i10a707.p.ssafy.io'
        imageName = "zoohee/a707-backend"
    }

    stages {
        stage("SCM"){
            steps{
                git branch: 'dev/BE',
                        credentialsId: 'gitlab',
                        url: 'https://lab.ssafy.com/s10-webmobile1-sub2/S10P12A707.git'
            }
        }
        stage('Build with Gradle') {
            steps {
                script {
                    try {
                        def gitDiffResult = sh(script: "git diff --name-only HEAD^ HEAD", returnStdout: true).trim()
                        println("gitDiffResult: " + gitDiffResult)

                        def egrepResult = sh(script: "echo '${gitDiffResult}' | egrep '(\\.java|\\.gradle|\\.properties|\\.yml|Dockerfile)\$' || true", returnStdout: true).trim()
                        println("egrepResult: " + egrepResult)

                        def services = sh(script: "echo '${egrepResult}' | cut -d/ -f2 | uniq", returnStdout: true).trim().split("\n")
                        println("services: " + services)

                        for (service in services) {
                            dir('BE') {
                                dir(service) {
                                    sh "chmod +x gradlew" // 실행 권한 추가
                                    sh "./gradlew build -x test"
                                }
                            }
                        }
                    } catch (Exception e) {
                        println("Error: " + e.getMessage())
                    }
                }
            }
        }
        stage('Build and Push Docker Images') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com/', 'docker-hub-credentials') {
                        try {
                            def gitDiffResult = sh(script: "git diff --name-only HEAD^ HEAD", returnStdout: true).trim()
                            println("gitDiffResult: " + gitDiffResult)

                            def egrepResult = sh(script: "echo '${gitDiffResult}' | egrep '(\\.java|\\.gradle|\\.properties|\\.yml|Dockerfile)\$' || true", returnStdout: true).trim()
                            println("egrepResult: " + egrepResult)

                            def services = sh(script: "echo '${egrepResult}' | cut -d/ -f2 | uniq", returnStdout: true).trim().split("\n")
                            println("services: " + services)

                            for (service in services) {
                                dir('BE') {
                                    dir(service) {
                                        sh "docker buildx build -f Dockerfile -t zoohee/a707-backend:${service} . --load"
                                        sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
                                        sh "docker push zoohee/a707-backend:${service}"
                                    }
                                }

                            }
                            sh "docker image prune --force"

                        }
                        catch (Exception e) {
                            println("Error: " + e.getMessage())
                        }
                    }
                }
            }
        }

        stage('Before Service Stop') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com/', 'docker-hub-credentials') {
                        try {
                            def gitDiffResult = sh(script: "git diff --name-only HEAD^ HEAD", returnStdout: true).trim()
                            println("gitDiffResult: " + gitDiffResult)

                            def egrepResult = sh(script: "echo '${gitDiffResult}' | egrep '(\\.java|\\.gradle|\\.properties|\\.yml|Dockerfile)\$' || true", returnStdout: true).trim()
                            println("egrepResult: " + egrepResult)

                            def services = sh(script: "echo '${egrepResult}' | cut -d/ -f2 | uniq", returnStdout: true).trim().split("\n")
                            println("services: " + services)

                            for (service in services) {
                                    sshagent(credentials: ['ubuntu']) {
                                        def serviceName = "${service}" // 변수 선언
                                        sh """
                                            targetHost=$releaseServerAccount@$releaseServerUri
                                            imageFullName=$imageName:$serviceName
                                            
                                            CONTAINER_ID=\$(ssh -o StrictHostKeyChecking=no \$targetHost "sudo docker ps -aq --filter ancestor=\$imageFullName")
                                            IMAGE_ID=\$(ssh -o StrictHostKeyChecking=no \$targetHost "sudo docker images -q \$imageFullName")
                                            
                                            if [ "\$CONTAINER_ID" != "" ]; then
                                                ssh -o StrictHostKeyChecking=no \$targetHost "sudo docker stop \$CONTAINER_ID"
                                                ssh -o StrictHostKeyChecking=no \$targetHost "sudo docker rm -f \$CONTAINER_ID"
                                            fi
                                            if [ "\$IMAGE_ID" != "" ]; then
                                                ssh -o StrictHostKeyChecking=no \$targetHost "sudo docker rmi \$IMAGE_ID"
                                            fi
                                        """
                                }
                            }
                            sh "docker image prune --force"

                        }

                        catch (Exception e) {
                            println("Error: " + e.getMessage())
                        }
                    }
                }
            }
        }

        stage('DockerHub Pull') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com/', 'docker-hub-credentials') {
                        try {
                            def gitDiffResult = sh(script: "git diff --name-only HEAD^ HEAD", returnStdout: true).trim()
                            println("gitDiffResult: " + gitDiffResult)

                            def egrepResult = sh(script: "echo '${gitDiffResult}' | egrep '(\\.java|\\.gradle|\\.properties|\\.yml|Dockerfile)\$' || true", returnStdout: true).trim()
                            println("egrepResult: " + egrepResult)

                            def services = sh(script: "echo '${egrepResult}' | cut -d/ -f2 | uniq", returnStdout: true).trim().split("\n")
                            println("services: " + services)

                            for (service in services) {
                                dir('BE') {
                                    dir(service) {
                                        sshagent(credentials: ['ubuntu']) {
                                            sh "ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri 'sudo docker pull $imageName:${service}'"
                                        }
                                    }
                                }
                            }
                        }

                        catch (Exception e) {
                            println("Error: " + e.getMessage())
                        }
                    }
                }
            }
        }

        stage('Service Start') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com/', 'docker-hub-credentials') {
                        try {
                            def gitDiffResult = sh(script: "git diff --name-only HEAD^ HEAD", returnStdout: true).trim()
                            println("gitDiffResult: " + gitDiffResult)

                            def egrepResult = sh(script: "echo '${gitDiffResult}' | egrep '(\\.java|\\.gradle|\\.properties|\\.yml|Dockerfile)\$' || true", returnStdout: true).trim()
                            println("egrepResult: " + egrepResult)

                            def services = sh(script: "echo '${egrepResult}' | cut -d/ -f2 | uniq", returnStdout: true).trim().split("\n")
                            println("services: " + services)

                            for (service in services) {
                                dir('BE') {
                                    dir(service) {
                                        def serviceName
                                        sshagent(credentials: ['ubuntu']) {
                                            serviceName = "${service}"
                                            sh """
                                                ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri 'sudo docker run -i -e TZ=Asia/Seoul -e SPRING_PROFILES_ACTIVE=prod --name $serviceName -d $imageName:$serviceName'
                                            """
                                        }
                                    }
                                }
                            }

                        }

                        catch (Exception e) {
                            println("Error: " + e.getMessage())
                        }
                    }
                }
            }
        }

    }
}
```
- 커뮤니티 서비스 개발
    - 총 9개의 API 개발
        - [GET] /community/subscribers
        - [POST] /community/subscribe
        - [DELETE] /community/subscribe
        - [PATCH] /community/source/{feedId}/like
        - [DELETE] /community/source/{feedId}/like
        - [GET] /community/feed/subscribe/{page}/{pageSize}
        - [GET] /community/feed/{page}/{pageSize}
        - [GET] /community/feed/{feedId}/detail
        - [POST] /community/feed
