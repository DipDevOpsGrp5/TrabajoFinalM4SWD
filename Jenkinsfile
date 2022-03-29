 pipeline {
    agent any
    stages {
        stage("Paso 1: Testing unit tests"){
            steps {
                script {
                    sh "mvn package"
                }
            }
        }
        stage("Paso 2: Run spring-boot app"){
            steps {
                script {
                    sh "echo 'run spring-boot app'"
                    //sh "JENKINS_NODE_COOKIE=dontKillMe nohup ./mvnw spring-boot:run &"
                    sh "nohup java -jar target/devops-0.0.1-SNAPSHOT.jar & >/dev/null"
                }
            }
        }
        stage("Paso 3: Run integration tests (Postman-newman)"){
            steps {
               checkout(
                        [$class: 'GitSCM',
                        branches: [[name: "main" ]],
                        userRemoteConfigs: [[url: 'https://github.com/DipDevOpsGrp5/TrabajoFinalM4SWD-postman-integration-tests.git']]])
                script {
                    sh "sleep 15"
                    sh "newman run integration_tests.postman_collection.json"
                }
            }
        }
        stage("Paso 4: Download and checkout front project"){
            steps {
               checkout(
                        [$class: 'GitSCM',
                        branches: [[name: "main" ]],
                        userRemoteConfigs: [[url: 'https://github.com/DipDevOpsGrp5/TrabajoFinalM4SWD-front.git']]])
            }
        }
        stage("Paso 5: Run front app"){
            steps {
                script {
                    sh "echo 'run spring-boot app'"
                    //sh "cd TrabajoFinalM4SWD-front"
                    sh "yarn install"
                    sh "nohup yarn serve --port 3000 &"
                }
            }
        }
        stage("Paso 6: Download and checkout Selenium project"){
            steps {
                script {
                    sh "echo 'Download and checkout Selenium project'"
                    sh "sleep 60"
                }
            }
        }
        stage("Paso 7: Run functional tests (Selenium)"){
            steps {
                script {
                    sh "echo 'Run Selenium tests'"
                }
            }
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        success {
            sh "echo 'fase success'"
        }
        failure {
            sh "echo 'fase failure'"
        }
    }
}