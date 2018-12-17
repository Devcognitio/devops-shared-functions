import groovy.io.FileType

def call(script) {
    node {
        withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD')]) {
            def dir = new File("/Users/jenniferperezbedoya/Documents/proyectos/ICProjects/jenkins/ServicioBase/bd")
            dir.eachFileRecurse (FileType.FILES) { file ->
                sh "cat ${file} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD -o 1433"
            }
        }
    }
}