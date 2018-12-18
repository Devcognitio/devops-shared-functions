import groovy.io.FileType

import com.pe.suraam.functions.BDConfigReader

def call(scriptsPath, configFilePath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def workspacePath = pwd()
        def dir = new File("${workspacePath}" + "${scriptsPath}")
        def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        echo("CONFIG FILE SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        echo(getClass().getResourceAsStream("/config.json"))
        if(!config.skipExecution) {
            dir.eachFileRecurse (FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
            }
        }
    }
}