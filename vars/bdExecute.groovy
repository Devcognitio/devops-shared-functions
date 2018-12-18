import groovy.io.FileType

import com.pe.suraam.functions.BDConfigReader
import com.pe.suraam.exceptions.*

def call(scriptsPath, configFilePath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def workspacePath = pwd()
        try {
            def dir = new File("${workspacePath}" + "${scriptsPath}")
            def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        } catch(FileNotFoundException exception) {
            throw new BDScriptsExecutionException("BD Scripts path, configuration file path or both are incorrect, please verify that exists and are not null", exception.getMessage())
        }
        echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if(!config.skipExecution) {
            dir.eachFileRecurse (FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
            }
        }
    }
}