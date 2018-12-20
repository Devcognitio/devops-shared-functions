package com.pe.suraam.functions

import groovy.io.FileType

import static com.pe.suraam.functions.BDConfigReader.*

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
        def workspacePath = script.pwd()
        //def workspacePath = script.env.WORKSPACE
        def config = readConfigFile("${workspacePath}${configFilePath}")
        def dir
        try {
            dir = new File("${workspacePath}${scriptsPath}")
        } catch (FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
        }
        script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if (!config.skipExecution) {
            dir.eachFileRecurse(FileType.FILES) { file ->
                def filePath = file.toPath().toString().replace("\\", "\\\\")
                script.sh "cat ${filePath} | sqlcmd -s localhost -u $username -p $password"
            }
        }
    }
}