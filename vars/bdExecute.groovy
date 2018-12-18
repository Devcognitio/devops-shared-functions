import groovy.io.FileType

def call(directoryPath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        //def workspacePath = pwd()
        //def dir = new File("${workspacePath}" + "${directoryPath}")
        def dir = new File("${directoryPath}")
       // def config = BDConfigReader.readConfigFile("../config.json")
        def reader = new com.pe.suraam.functions.BDConfigReader()
        reader.readConfigFile(this,"../config.json")
        echo("CONFIG FILE: ${config.skipExecution}")
        dir.eachFileRecurse (FileType.FILES) { file ->
            sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
        }
    }
}


com.pe.suraam.functions.BDConfigReader
com.pe.suraam.functions/BDConfigReader.groovy
com.pe.suraam.functions
