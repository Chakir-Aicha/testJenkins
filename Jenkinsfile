pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // RÃ©cupÃ©rer le code source de la branche main
                git branch: 'main', url: 'https://github.com/Chakir-Aicha/testJenkins.git'
            }
        }
        
        stage('Build') {
            steps {
                // Construire l'application Spring Boot (par exemple, avec Maven)
                bat './mvnw clean install -DskipTests=true'
            }
        }

        stage('Run Tests') {
            steps {
                // ExÃ©cuter les tests unitaires avec Maven
                bat './mvnw test -Punit'
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
