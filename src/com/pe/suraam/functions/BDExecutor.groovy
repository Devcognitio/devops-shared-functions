package com.pe.suraam.functions

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
            List<String>  filesPathList = getFilesList("${workspacePath}${scriptsPath}")
            script.echo "filesPathList : ${filesPathList}, class: ${filesPathList.getClass()}"
            if (filesPathList.isEmpty()){
                throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ")
            }
            for(String filePath : filesPathList.sort()){
                script.echo "Path: >>>${filePath}<<<"
                script.sh "cat ${filePath} | sqlcmd -s $host -o $port -u $username -p $password"
            }
        }
    }

    List<String> getFilesList(path){
        List<String>  filesList = []
        if (script.isUnix()) {
            filesList = script.sh (script: "ls -d -1 $path/**.sql", returnStdout: true).trim().split("\\r?\\n")
        }else{
            script.dir(path){
                filesList = script.bat(returnStdout: true, script: "dir /s /b *.sql").trim().tokenize('\r\n')
            }
        }
        return filesList
    }
}
