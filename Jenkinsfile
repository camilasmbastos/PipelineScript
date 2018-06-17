properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
                                      artifactNumToKeepStr: '', 
                                      daysToKeepStr: '', 
                                      numToKeepStr: '5')), 
            pipelineTriggers([bitbucketPush()])])
            
timestamps {
    node() {
        checkoutStage()
        buildStage()
        testStage()
    }
}


// Definicao dos Stages
def checkoutStage() {
    stage ('Checkout') {
        git 'https://bitbucket.org/edunesic/bit/src/master/'
    }
}

def buildStage() {
    stage ('Build') {
        env.PROJECT_NAME=getProjectName()
        docker.build("$PROJECT_NAME")
    }
}

def testStage() {
    docker.image("$PROJECT_NAME").inside { 
        stage ('Install Dependencies') {
            sh 'npm install'
            sh 'npm audit fix'
        }
        stage ('Test') {
            
            sh 'npm test:docker'
        }
        stage ('Code Quality') {
            def scannerHome = tool 'sonarQube'
            
            withSonarQubeEnv('sonarcloud') {
                sh "${scannerHome}/bin/sonar-scanner"
            }
        }
    }
}

def getProjectName() {
    def name = sh (script: 'PACKAGE_NAME=$(cat package.json | grep name | head -1 | awk -F: \'{ print $2 }\' | sed \'s/[\\",]//g\' | tr -d \'[[:space:]]\') && echo $PACKAGE_NAME', returnStdout: true).trim()
    return name
}



