def call(script) {
    node {
        withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD')]) {
            sh "cat ${script} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD -o 1433"
        }
    }
}