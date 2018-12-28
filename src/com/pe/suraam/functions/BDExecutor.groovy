package com.pe.suraam.functions

import groovy.io.FileType

import static com.pe.suraam.functions.BDConfigReader.*

class BDExecutor {

    private def script
    private def scriptsPath
    private def configFilePath
    private def username
    private def password
    private def host
    private def port

    BDExecutor(script, scriptsPath, configFilePath, username, password, dbHost, dbPort) {
        this.script = script
        this.scriptsPath = scriptsPath
        this.configFilePath = configFilePath
        this.username = username
        this.password = password
        this.host = dbHost
        this.port = dbPort
    }

    void executeScripts() {
        def workspacePath = script.pwd()
        def config = readConfigFile("${workspacePath}${configFilePath}")
        def dir
        try {
            dir = new File("${workspacePath}${scriptsPath}")
        } catch (FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
        }
        script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if (!config.skipExecution) {
            def list = []
            dir.eachFileRecurse(FileType.FILES) { file ->
                list << file
            }
            list.sort{file -> file.getName()}.each{ file ->
                def filePath = file.toPath().toString().replace("\\", "\\\\")
                script.sh "cat ${filePath} | sqlcmd -s $host -o $port -u $username -p $password"
            }
        }
    }
}