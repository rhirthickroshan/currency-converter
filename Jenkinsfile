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


    }

}