package main.groovy.com.pe.suraam.functions

import groovy.io.FileType
import java.nio.file.*
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
        //def workspacePath = script.pwd()
        def workspacePath = script.env.WORKSPACE
        def path = "${workspacePath}${scriptsPath}"
        script.echo("PATH ${path}")
        def path2 = path.replace("\\", "/")
        script.echo("PATH2 ${path2}")
        def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        def dir
        try {
            dir = new File(path)
        } catch (FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
        }
        script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if (!config.skipExecution) {
            dir.eachFileRecurse(FileType.FILES) { file ->
                script.echo("SCRIPT FILE -> ${file.toPath()}")
                def pa = file.toPath().toString().replace("\\", "\\\\")
                script.sh "cat ${file.toPath()} | sqlcmd -s localhost -u $username -p $password"
            }
        }
    }
}