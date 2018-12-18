import groovy.io.FileType

def call(directoryPath) {
        withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD')]) {
            sh "pwd"
            sh "ls"
            def workspacePath = sh 'pwd'
            def dir = new File("bd/scripts")
            dir.eachFileRecurse (FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD"
            }
        }
}