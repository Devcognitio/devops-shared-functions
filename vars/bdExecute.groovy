def call(script) {
    node {
        sh libraryResource("bdshell.sh") ${script}
    }
}