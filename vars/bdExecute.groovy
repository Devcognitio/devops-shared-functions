def call(script) {
    node {
        sh resources/bdshell.sh ${script}
    }
}