package com.pe.suraam.functions

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

class BDConfigReaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void mustThrowFileNotFoundExceptionIfConfigFilePathDoesNotExist() {
        thrown.expect(FileNotFoundException.class)
        thrown.expectMessage(containsString("non/existing/path (No"))
        BDConfigReader.readConfigFile("non/existing/path")
    }

    @Test
    void mustReturnConfigFileObjectWithAtLeastOneFieldIfJsonExists() {
        def filePath = new File(getClass().getResource('/config.json').toURI())
        def configFile = BDConfigReader.readConfigFile(filePath)
        assertThat((boolean)configFile.skipExecution, is(false))
    }


}