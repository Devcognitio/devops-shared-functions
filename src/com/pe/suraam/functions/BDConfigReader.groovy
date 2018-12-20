package com.pe.suraam.functions

import com.pe.suraam.model.ConfigFile
import groovy.json.JsonSlurper


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
