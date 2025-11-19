pipeline {
  agent any

  triggers {
    // runs every 2 minutes
    cron('*/2 * * * *')
  }

  options {
    disableConcurrentBuilds()   // avoid overlapping runs
    timeout(time: 10, unit: 'MINUTES')
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  stages {

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Setup Chrome') {
      steps {
        sh '''
          # Install Chrome if needed (Linux agent)
          if ! command -v google-chrome &> /dev/null
          then
            echo "Installing Chrome..."
            wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
            sudo apt install -y ./google-chrome-stable_current_amd64.deb
          fi
        '''
      }
    }

    stage('Run Tests') {
      steps {
        sh 'mvn -B clean test -DbrowserMode=headless'
      }
    }

    stage('Archive Reports') {
      steps {
        archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
    }
  }
}
