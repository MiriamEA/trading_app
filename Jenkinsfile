pipeline {
    agent any
    tools {
        maven "M3"
    }

    environment {
        app_name = 'trading-app'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
                echo "app name is ${env.app_name}"
                archiveArtifacts 'target/*zip'
            }
        }
        stage('Deploy-dev') {
            when { branch 'develop' }
            steps {
                echo "Current branch is ${env.GIT_BRANCH}"
                sh "./scripts/eb_deploy.sh TradingApp-dev2"
            }
        }
        stage('Deploy-prod') {
            when {branch 'master' }
            steps {
                echo "Current branch is ${env.GIT_BRANCH}"
                sh "./scripts/eb_deploy.sh TradingApp-prod"
            }
        }
    }
}
