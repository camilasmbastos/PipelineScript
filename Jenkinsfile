properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
                                      artifactNumToKeepStr: '', 
                                      daysToKeepStr: '', 
                                      numToKeepStr: '2')), 
            pipelineTriggers([bitbucketPush()])])
            
timestamps {
    node() {
        checkoutStage()
        buildStage()
        testStage()
        archiveStage()
        deployHMGStage()
    }
    releaseStage()
    node() {
        deployPRDStage()
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
            //sh 'npm test:docker'
        }
        stage ('Code Quality') {
            def scannerHome = tool 'sonarQube'
            
            withSonarQubeEnv('sonarcloud') {
                //sh "${scannerHome}/bin/sonar-scanner"
            }
        }
    }
}

def archiveStage() {
    stage('Archive Artifacts') {
        
    }
}

def deployHMGStage() {
    stage ('Deploy HMG') {
        
    }  
}

def releaseStage() {
    stage ('Release / Promover') {
        def releaseType
        try {
            timeout(time: 12, unit: 'HOURS') {
                releaseType = input message: 'Fechar versão e promover para produção?', parameters: [[$class: 'hudson.model.ChoiceParameterDefinition', choices: 'major\nminor\npatch', description: 'Tipos de release', name: 'release']]
            }
        } catch (err) {
            println "Abortada"
            throw err
        }
        release(releaseType)
    }
}

def deployPRDStage() {
    stage ('Deploy PRD') {
        
    }  
}

// Fim da definição dos Stages

def getProjectName() {
    def name = sh (script: 'PACKAGE_NAME=$(cat package.json | grep name | head -1 | awk -F: \'{ print $2 }\' | sed \'s/[\\",]//g\' | tr -d \'[[:space:]]\') && echo $PACKAGE_NAME', returnStdout: true).trim()
    return name
}

def release(rType) {
    println "Release    " +rType
    node() {
        //sh "npm version ${rType} -f --verbose"
        
        //git_branch = sh (script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
        
        //sh "git push origin HEAD:$git_branch"
        //sh "git push origin --tags"
    }
}



