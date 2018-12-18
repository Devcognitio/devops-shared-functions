import groovy.io.FileType
import com.pe.suraam.functions.BDConfigReader

def call(scriptsPath, configFilePath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def workspacePath = pwd()
        def dir
        def config
        try {
            dir = new File("${workspacePath}" + "${scriptsPath}")
            config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
        } catch(FileNotFoundException exc) {
            throw new FileNotFoundException("BD Scripts path, configuration file path or both are incorrect, please verify that exists and are not null", exc.getMessage())
        }
        echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
        if(!config.skipExecution) {
            dir.eachFileRecurse (FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
            }
        }
    }
}