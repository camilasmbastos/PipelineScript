properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
                                      artifactNumToKeepStr: '', 
                                      daysToKeepStr: '', 
                                      numToKeepStr: '5'))])

            
timestamps {
    node() { 
        checkoutStage()
        
        def stages = load 'deploy.groovy'
     
        stages.buildStage()
        stages.testStage()
        stages.archiveStage()
        stages.deployHMGStage()
        stages.releaseStage()
        stages.deployPRDStage()
    }
}

def checkoutStage() {
    stage ('Checkout') {
        checkout scm
        getProjectName()
    }
}
