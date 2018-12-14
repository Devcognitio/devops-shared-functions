def call(script) {
    node {
        sh(script: libraryResource("bdshell.sh"), ${script})
    }
}