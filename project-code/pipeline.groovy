pipeline{
    agent {label 'node1'}
    
    tools{
        jdk 'JDK 11'
        maven 'Maven 3.x'
    }
    
    stages{
        stage('pull code from github'){
            steps{
                script{
                    git "https://github.com/AdhmAbdein/java-project.git"
                }
            }
        }
        stage('Build'){
            steps{
                script{
                    dir('project-code'){
                        sh 'mvn clean install'
                    }
                }
            }
        }
        stage('Test'){
            steps{
                script{\
                    dir('project-code'){
                        sh 'mvn test'
                    }
                }
            }
        }
        stage('Archive'){
            steps{
                script{
                    dir('project-code') {
                        archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
                    }
                }
            }
        }
        stage('Run'){
            steps{
                script{
                    dir('project-code') {
                        sh 'java -jar target/*.jar'               
                    }
                }
            }
        }
    }
    post{
        always{
            echo "pipeline execution complete"
        }
        success{
            echo "pipeline executed successfully"
        }
        failure{
            echo "pipeline failed"
        }
    }
}
