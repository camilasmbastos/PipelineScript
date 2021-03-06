
// Definicao dos Stages
def checkoutStage() {
    stage ('Checkout') {
        checkout scm
        getProjectName()
    }
}

def buildStage() {
    stage ('Build') {
        build()
    }
}

def testStage() {
    docker.image("$PROJECT_NAME").inside { 
        stage ('Install Dependencies') {
            sh 'npm install'
        }
        stage ('Test') {
           //sh 'npm run test'
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
        pushImage("snapshot")
    }
}

def deployHMGStage() {
    stage ('Deploy HMG') {
        deploy("snapshot")
    }  
}

def releaseStage() {
    stage ('Release / Promover') {
        def releaseType
        try {
            timeout(time: 1, unit: 'HOURS') {
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
        deploy("$PROJECT_VERSION")
    }  
}
// Fim da definição dos Stages

def build() {
    docker.build("$PROJECT_NAME")
}

def pushImage(def version) {
    withDockerRegistry(credentialsId: 'gcr:bitprojecttw', url: 'https://gcr.io/bitprojecttw/v1/') {
            sh "docker tag $PROJECT_NAME gcr.io/bitprojecttw/$PROJECT_NAME:$version"
            sh "docker push gcr.io/bitprojecttw/$PROJECT_NAME:$version"
        }
} 

def getProjectName() {
    env.PROJECT_NAME = sh (script: 'PACKAGE_NAME=$(cat package.json | grep name | head -1 | awk -F: \'{ print $2 }\' | sed \'s/[\\",]//g\' | tr -d \'[[:space:]]\') && echo $PACKAGE_NAME', returnStdout: true).trim().toLowerCase()
}

def getVersion() {
    env.PROJECT_VERSION = sh (script: 'PACKAGE_VERSION=$(cat package.json | grep version | head -1 | awk -F: \'{ print $2 }\' | sed \'s/[\\",]//g\' | tr -d \'[[:space:]]\') && echo $PACKAGE_VERSION', returnStdout: true).trim()
}

def release(rType) {
    println "Tipo de release ---> " +rType
    
    //sh "npm version ${rType} -f"
    //git_branch = sh (script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
    //sh "git push origin HEAD:$git_branch"
    //sh "git push origin --tags"
    build()
    getVersion()
    pushImage(version)
    
    //Apenas uma perfumaria para saber qual é a versao da release
    addBadge icon: 'package.gif', id: '', link: '', text: 'Versão: ${PROJECT_VERSION}'
    createSummary("package.gif").appendText("<b>Versão:</b> ${PROJECT_VERSION}", false, false, false, "black")
}

def deploy(version) {
    println "docker pull gcr.io/bitprojecttw/$PROJECT_NAME:$version"
}

return this;
