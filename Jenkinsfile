properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', 
                                      artifactNumToKeepStr: '', 
                                      daysToKeepStr: '', 
                                      numToKeepStr: '5'))])

            
timestamps {
    node() { 
       def stages = load 'deploy.groovy'
        
        stages.checkoutStage()
        stages.buildStage()
        stages.testStage()
        stages.archiveStage()
        stages.deployHMGStage()
        stages.releaseStage()
        stages.deployPRDStage()
    }
}
