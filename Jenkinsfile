pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source de la branche main
                git branch: 'main', url: 'https://github.com/votre-repo'
            }
        }
        
        stage('Build') {
            steps {
                // Construire l'application Spring Boot (par exemple, avec Maven)
                sh './mvnw clean install -DskipTests=true'
            }
        }

        stage('Run Tests') {
            steps {
                // Exécuter les tests unitaires avec Maven
                sh './mvnw test'
            }
        }
    }
    
    post {
        success {
            echo 'Tests passed. The build was successful!'
        }

        failure {
            echo 'Tests failed. The build failed!'
        }
    }
}
