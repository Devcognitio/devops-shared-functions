def call(script) {
    node {
        withCredentials([usernamePassword(credentialsId: 'bd-credenciales-desa',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD')]) {
            sh "cat ${script} | sqlcmd -s localhost -u $USERNAME -p $PASSWORD -o 1433"
        }
    }
}