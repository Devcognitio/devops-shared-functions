package main.groovy.com.pe.suraam.functions

import groovy.io.FileType
import main.groovy.com.pe.suraam.functions.BDConfigReader

class BDExecutor {

    private def script
    private def scriptsPath
    private def configFilePath
    private def username
    private def password

    BDExecutor(script, scriptsPath, configFilePath, username, password) {
        this.script = script
        this.scriptsPath = scriptsPath
        this.configFilePath = configFilePath
        this.username = username
        this.password = password
    }

    void executeScripts() {
        def workspacePath = pwd()
        def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        def dir
        try {
            dir = new File("${workspacePath}" + "${scriptsPath}")
        } catch (FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
        }
        echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if (!config.skipExecution) {
            dir.eachFileRecurse(FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $username -p $password"
            }
        }
    }
}