import groovy.io.FileType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.pe.suraam.functions.BDConfigReader

final Logger logger = LoggerFactory.getLogger("devops-shared-functions-log")

def call(scriptsPath, configFilePath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def workspacePath = pwd()
        try {
            def dir = new File("${workspacePath}" + "${scriptsPath}")
            def config = BDConfigReader.readConfigFile("${workspacePath}" + "${configFilePath}")
            echo("CONFIG FILE -> SKIP BD SCRIPTS EXECUTION: ${config.skipExecution}")
            if(!config.skipExecution) {
                dir.eachFileRecurse (FileType.FILES) { file ->
                    sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
                }
            }
        } catch(FileNotFoundException exc) {
            logger.error("BD Scripts path, configuration file path or both are incorrect, please verify that exists and are not null")
        }
    }
}