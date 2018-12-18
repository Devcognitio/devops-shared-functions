#!/usr/bin/env groovy
package com.pe.suraam.functions

import groovy.json.JsonSlurper

class BDConfigReader {

    static def readConfigFile(jsonPath) {
        def inputFile = new File("${jsonPath}")
       // return new JsonSlurper().parseText(inputFile.text) as ConfigFile
        return new JsonSlurper().parseText(inputFile.text)
    }

}
