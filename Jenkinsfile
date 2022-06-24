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
                docker build -t springapp -f Dockerfile .
                docker build -t mailhog -f DockerfileMailhog .
                '''
            }
        }

        stage("Tag Docker Images") {
            steps {
                sh '''
                docker tag db panagiotishua/db
                docker tag springapp panagiotishua/springapp
                docker tag mailhog panagiotishua/mailhog
                '''
            }
        }

        stage("Push Docker Images to Dockerhub") {
            steps {
                sh '''
                docker push panagiotishua/db
                docker push panagiotishua/springapp
                docker push panagiotishua/mailhog
                '''
            }
        }

        stage ("Deploy to ansible vm") {
            steps {
                sh "ansible-playbook playbooks/deploy-to-ansible-vm.yaml"
            }
        }

        stage ("Deploy to docker vm") {
            steps {
                sh "ansible-playbook playbooks/deploy-to-docker-vm.yaml --vault-password-file $HOME/db-password.txt"
            }
        }

        stage ("Deploy to kubernetes vm") {
            steps {
                sh "ansible-playbook playbooks/deploy-to-kubernetes-vm.yaml"
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