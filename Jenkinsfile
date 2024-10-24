pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                // Construire l'application Spring Boot (par exemple, avec Maven)
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                // ExÃ©cuter les tests unitaires avec Maven
                sh './mvnw test -Punit'
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
