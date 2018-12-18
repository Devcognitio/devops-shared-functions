import groovy.io.FileType
import com.pe.suraam.functions.*

def call(directoryPath) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        //def workspacePath = pwd()
        //def dir = new File("${workspacePath}" + "${directoryPath}")
        def dir = new File("${directoryPath}")
        def config = readConfigFile("../config.json")
        echo("CONFIG FILE: ${config.skipExecution}")
        dir.eachFileRecurse (FileType.FILES) { file ->
            sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
        }
    }
}