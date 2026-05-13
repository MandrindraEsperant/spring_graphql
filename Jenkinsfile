pipeline {
    agent any

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm
                }
        }

        stage('Build Application') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests -B'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t mandrindraesperant/spring-graphql:1.0.0 .'
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {
                    sh '''
                        echo $DOCKER_TOKEN | docker login -u mandrindraesperant --password-stdin
                        docker push mandrindraesperant/spring-graphql:1.0.0
                    '''
                }
            }
        }
    }
}