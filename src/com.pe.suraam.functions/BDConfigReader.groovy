#!/usr/bin/env groovy
package com.pe.suraam.functions

import groovy.json.JsonSlurper

class BDConfigReader {

    static def readConfigFile(script, jsonPath) {
       // def inputFile = new File("${jsonPath}")
        script.sh "pwd"
       // echo "${inputFile}"
       // return new JsonSlurper().parseText(inputFile.text) as ConfigFile
        //return new JsonSlurper().parseText(inputFile.text)
    }

}
