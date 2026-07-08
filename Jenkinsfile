pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rhirthickroshan/currency-converter.git'
            }

        }
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package'
            }
        }
        stage('test') {
            steps{
                bat 'mvnw.cmd test'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t currency-converter .'
            }
        }
        stage('Run Docker Container') {
             steps {
                bat 'docker compose up -d'
             }
        }

    }

}