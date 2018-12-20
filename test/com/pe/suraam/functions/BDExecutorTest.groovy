package com.pe.suraam.functions

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.hamcrest.Matchers.containsString


class BDExecutorTest extends BasePipelineTest {

    def script
    def bDExecutor
    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Override
    @Before
    void setUp() {
        super.setUp()
        helper.registerAllowedMethod("pwd", []) {new File("").getAbsolutePath()}
        script = loadScript('vars/bdExecute.groovy')
        def scriptsPath = "/non/existing/path"
        def configFilePath = "/test/resources/config.json"
        def username = "username"
        def password = "password"
        bDExecutor = new BDExecutor(script, scriptsPath, configFilePath, username, password)
    }

    @Test
    void mustThrowFileNotFoundExceptionIfScriptsPathDoesNotExist() {
        thrown.expect(FileNotFoundException.class)
        thrown.expectMessage(containsString("/non/existing/path"))
        bDExecutor.executeScripts()
    }

}