package test.groovy.com.pe.suraam.functions

import org.junit.Test
import main.groovy.com.pe.suraam.functions.BDConfigReader

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

class BDConfigReaderTest {

    @Test(expected = FileNotFoundException.class)
    void mustThrowFileNotFoundExceptionIfConfigFilePathDoesNotExist() {
        BDConfigReader.readConfigFile("non/existing/path")
    }

    @Test
    void mustReturnConfigFileObjectWithAtLeastOneFieldIfJsonExists() {
        def filePath = new File(getClass().getResource('/config.json').toURI())
        def configFile = BDConfigReader.readConfigFile(filePath)
        assertThat(configFile.skipExecution, is(false))
    }


}