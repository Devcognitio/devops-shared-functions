def call(script) {
    node {
        sh bdshell.sh ${script}
    }
}