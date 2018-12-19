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
        def workspacePath = script.pwd()
        script.sh "pwd"
        //   def p5 = Paths.get(System.getProperty("user.dir"))
        // script.echo(p5.toString())
        //script.echo(p5.toUri().toString())
       // def pf = context.getAbsolutePathFile(".")
        //script.echo(pf.getAbsolutePath())
        def scriptDir = getClass().protectionDomain.codeSource.location.path
        script.echo(scriptDir)
        def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        def dir
        try {
            dir = new File("${workspacePath}" + "${scriptsPath}")
        } catch (FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path is incorrect, please provide a correct path. ", exc.getMessage())
        }
        script.echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if (!config.skipExecution) {
            dir.eachFileRecurse(FileType.FILES) { file ->
                if (Files.exists(file.toPath())) {
                    script.sh "cat ${file} | sqlcmd -s localhost -u $username -p $password"
                } else {
                    script.echo("SCRIPT FILE -> BD SCRIPT DOES NOT EXIST" + ${file.toPath()})
                }
            }
        }
        script.echo("WORKSPACE ${env.WORKSPACE}")
    }
}