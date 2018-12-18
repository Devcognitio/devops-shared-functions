
package com.pe

//import groovy.json.JsonSlurper

//class BDConfigReader {

def readConfigFile(script, jsonPath) {
       // def inputFile = new File("${jsonPath}")
    script.echo("Desde BDConfigReader")
        script.sh "pwd"
       // echo "${inputFile}"
       // return new JsonSlurper().parseText(inputFile.text) as ConfigFile
        //return new JsonSlurper().parseText(inputFile.text)
    }

return this

//}
