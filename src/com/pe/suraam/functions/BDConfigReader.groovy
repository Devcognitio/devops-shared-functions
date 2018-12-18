
package com.pe.suraam.functions

import groovy.json.JsonSlurper

class BDConfigReader {

  static ConfigFile readConfigFile() {
        def stream = getClass().getResourceAsStream("/config.json")
        print(stream.text)
        //def inputFile = new File("${jsonPath}")
        //script.echo("Desde BDConfigReader")
        //script.sh "pwd"
        print "${inputFile}"
        return new JsonSlurper().parseText(inputFile.text) as ConfigFile
        //return new JsonSlurper().parseText(inputFile.text)
  }

//return this

}
