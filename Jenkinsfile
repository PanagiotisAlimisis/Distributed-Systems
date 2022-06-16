pipeline {
    agent any

    stages {
        stage ("Compile") {
            steps {
                sh "mvn compile"
            }
        }

        stage ("Unit test") {
            steps {
                sh "mvn test"
            }
        }

        stage("Install package") {
            steps {
                sh "mvn clean install package -DskipTests=true"
            }
        }

        stage ("Deploy to ansible vm") {
            steps {
                sh "ansible-playbook playbooks/deploy.yaml"
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