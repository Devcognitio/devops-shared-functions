package com.pe.suraam.functions

import groovy.json.JsonSlurper
import com.pe.suraam.model.*

class BDConfigReader {

  static ConfigFile readConfigFile(jsonPath) {
      def inputFile
      try {
          inputFile = new File("${jsonPath}")
      } catch(FileNotFoundException exc) {
         throw new FileNotFoundException("BD Scripts execution config file was not found, please provide a correct path. ", exc.getMessage())
      }
      return new JsonSlurper().parseText(inputFile.text) as ConfigFile
  }

}
