package com.pe.suraam.functions

import groovy.json.JsonSlurper
import org.junit.Test


import static org.hamcrest.MatcherAssert.assertThat

class BDConfigReaderTest {

    @Test(expected = FileNotFoundException.class)
    void mustThrowFileNotFoundExceptionIfConfigFilePathDoesNotExist() {
        readConfigFile("non/existing/path")
    }


}