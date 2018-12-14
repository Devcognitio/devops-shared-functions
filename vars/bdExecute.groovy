def call(script) {
    node {
        sh "cat ${script} | sqlcmd -s localhost -u sa -p Admin2018 -o 1433"
    }
}