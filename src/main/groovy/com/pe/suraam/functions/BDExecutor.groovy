package main.groovy.com.pe.suraam.functions

import groovy.io.FileType
import groovy.json.JsonSlurper
import main.groovy.com.pe.suraam.functions.BDConfigReader

class BDExecutor {

    private def script
    private def scriptsPath
    private def configFilePath

    BDExecutor(script, scriptsPath, configFilePath) {
        this.script = script
        this.scriptsPath = scriptsPath
        this.configFilePath = configFilePath
    }

    void executeScripts() {
        this.script.withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                usernameVariable: 'USERNAME',
                passwordVariable: 'PASSWORD')]) {
            def workspacePath = script.pwd()
            def dir
            def config
            try {
                dir = new File("${workspacePath}" + "${scriptsPath}")
                config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
            } catch(FileNotFoundException exc) {
                throw new FileNotFoundException("BD Scripts path, configuration file path or both are incorrect, please verify that exists and are not null", exc.getMessage())
            }
            script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
            if(!config.skipExecution) {
                dir.eachFileRecurse (FileType.FILES) { file ->
                    script.sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
                }
            }
        }
    }
}