pipeline {
    agent any
    stages {
        stage("Clone the project") {
            git branch: 'main', url: 'https://github.com/Chakir-Aicha/testJenkins.git'
          }
        stage("Compilation") {
            sh "./mvnw clean install -DskipTests"
          }
        stage("Runing unit tests") {
            sh "./mvnw test -Punit"
          }
    }
}
