import groovy.io.FileType

def call(directoryPath) {
    def reader = new x.BDConfigReader()
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        //def workspacePath = pwd()
        //def dir = new File("${workspacePath}" + "${directoryPath}")
        def dir = new File("${directoryPath}")
       // def config = BDConfigReader.readConfigFile("../config.json")

        reader.readConfigFile(this,"../config.json")
       // echo("CONFIG FILE: ${config.skipExecution}")
        dir.eachFileRecurse (FileType.FILES) { file ->
            sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
        }
    }
}