package test.com.pe.suraam.functions

import groovy.json.JsonSlurper
import org.junit.Test
import main.com.pe.suraam.functions.BDConfigReader

import static org.hamcrest.MatcherAssert.assertThat

class BDConfigReaderTest {

    @Test(expected = FileNotFoundException.class)
    void mustThrowFileNotFoundExceptionIfConfigFilePathDoesNotExist() {
        BDConfigReader.readConfigFile("non/existing/path")
    }


}