pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mandrindraesperant/spring-graphql'
        IMAGE_TAG  = "${BUILD_NUMBER}"
    }

    stages {

        // ─── Checkout Git ────────────────────────────────
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        // ─── Build + Tests en une seule étape ───────────
        stage('Build & Test') {
            steps {
                sh '''
                    chmod +x mvnw
                    ./mvnw clean verify -B
                '''
            }
        }

        // ─── Build Docker Image ─────────────────────────
        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build \
                    -t $IMAGE_NAME:$IMAGE_TAG \
                    -t $IMAGE_NAME:latest .
                '''
            }
        }

        // ─── Push DockerHub ─────────────────────────────
        stage('Push DockerHub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {

                    sh '''
                        echo "$DOCKER_TOKEN" | docker login -u "mandrindraesperant" --password-stdin

                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest

                        docker logout
                    '''
                }
            }
        }

        // ─── Nettoyage ──────────────────────────────────
        stage('Cleanup') {
            steps {
                sh '''
                    docker image prune -f
                '''
            }
        }
    }
}