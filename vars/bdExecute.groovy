import groovy.io.FileType

import com.pe.suraam.functions.BDConfigReader

def call(scriptsPath, configFilePath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def workspacePath = pwd()
        def dir = new File("${workspacePath}" + "${scriptsPath}")
        //def dir = new File("${directoryPath}")
        def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")

        //BDConfigReader.readConfigFile(this,"../config.json")
        echo("CONFIG FILE: ${config.skipExecution}")
        dir.eachFileRecurse (FileType.FILES) { file ->
            sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
        }
    }
}