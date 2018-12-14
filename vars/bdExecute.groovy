def call(script) {
    node {
        def function = libraryResource("bdshell.sh")
        writeFile file: 'bdshell.sh', text: function
        sh "bdshell.sh ${script}"
    }
}