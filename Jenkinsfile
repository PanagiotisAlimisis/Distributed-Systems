pipeline {
    agent any

    stages {
        stage ("Compile") {
            steps {
                sh "mvn compile"
            }
        }

        stage("Install package") {
            steps {
                sh "mvn clean install package -DskipTests=true"
            }
        }

        stage("Build Docker Images") {
            steps {
                sh '''
                docker build -t db -f DockerfileDb .
                docker build -t spring-app -f Dockerfile .
                '''
            }
        }

        stage("Tag Docker Images") {
            steps {
                sh '''
                docker tag db panagiotishua/db
                docker tag spring-app panagiotishua/spring-app
                '''
            }
        }

        stage("Push Docker Images to Dockerhub") {
            steps {
                sh '''
                docker push panagiotishua/db
                docker push panagiotishua/spring-app
                '''
            }
        }

        stage ("Deploy to ansible vm") {
            steps {
                sh "ansible-playbook playbooks/deploy.yaml"
            }
        }

        stage ("Deploy to docker vm") {
            steps {
                sh "ansible-playbook playbooks/deploy.yaml --vault-password-file $HOME/db-password.txt"
            }
        }

    }

    post {
        success {
            mail to: "${my_email}",
            subject: 'Completed Pipeline',
            body: 'Build completed successfully.'
        }
        failure {
            mail to: "${my_email}",
            subject: 'Completed Pipeline',
            body: 'Build has failed.'
        }
    }

}