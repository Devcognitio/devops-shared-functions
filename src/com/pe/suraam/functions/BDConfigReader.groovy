
package com.pe.suraam.functions

import groovy.json.JsonSlurper

class BDConfigReader {

 def static ConfigFile readConfigFile(jsonPath) {
        def inputFile = new File("${jsonPath}")
        //script.echo("Desde BDConfigReader")
        //script.sh "pwd"
        echo "${inputFile}"
       // return new JsonSlurper().parseText(inputFile.text) as ConfigFile
        //return new JsonSlurper().parseText(inputFile.text)
    }

//return this

}
