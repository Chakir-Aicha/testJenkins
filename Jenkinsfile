pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                // Utiliser Maven pour construire l'application
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Exécuter les tests unitaires
                bat 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Par exemple, pour déployer l'application
                bat 'mvn spring-boot:run'
            }
        }
    }
}
