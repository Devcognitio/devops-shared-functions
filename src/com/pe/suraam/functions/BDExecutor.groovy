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
        
        script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        script.echo("Rute: ${workspacePath}${scriptsPath}")
        if (!config.skipExecution) {
            def  filesList = getFilesList("${workspacePath}${scriptsPath}")
            script.echo "filesList : ${filesList}"
            if (filesList.isEmpty()){
                throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
            }
            for(String file : filesList.sort()){ 
                script.echo "Path: >>>${workspacePath}${scriptsPath}/${file}<<<" 
                script.sh "cat ${workspacePath}${scriptsPath}/${file} | sqlcmd -s $host -o $port -u $username -p $password"    
            }
        }
    }

    def getFilesList(path){
        def  filesList
        if (script.isUnix()) {
            filesList = script.sh (script: "ls '${path}'", returnStdout: true).trim().split("\\r?\\n")
        }else{
            filesList = bat(script: 'dir /s /b /a:-D "'+path+'" 2>nul').trim().tokenize('\r\n')
        }
        return filesList
    }
}