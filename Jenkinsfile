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
                bat 'mvn clean package'
            }
        }
        stage('test') {
            steps{
                bat 'mvn test'
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