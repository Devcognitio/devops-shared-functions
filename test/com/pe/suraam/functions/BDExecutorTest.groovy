package com.pe.suraam.functions

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Ignore
import org.junit.rules.ExpectedException

import static org.hamcrest.Matchers.containsString


class BDExecutorTest extends BasePipelineTest {

    //TODO: Finalizar test unitarios

    def script
    def bDExecutor
    def scriptsPath 
    def configFilePath 
    def username
    def password
    def host
    def port

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Override
    @Before
    void setUp() {
        super.setUp()
        helper.registerAllowedMethod("pwd", []) {new File("").getAbsolutePath()}
        script = loadScript('vars/bdExecute.groovy')
        configFilePath = "/test/resources/config.json"
        username = "username"
        password = "password"
        host = 'localhost'
        port = '1433'
    }

    @Ignore
    @Test
    void mustThrowFileNotFoundExceptionIfScriptsPathDoesNotExist() {
        scriptsPath = "/non/existing/path"
        bDExecutor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)
        thrown.expect(FileNotFoundException.class)
        thrown.expectMessage(containsString("/non/existing/path"))
        bDExecutor.executeScripts()
    }

    @Ignore
    @Test
    void mustExecuteAnDBScriptIfConfigSkipExecutionIsFalse(){
        helper.registerAllowedMethod("sh", []) {"OK"}
        scriptsPath = "/test/resources/test.sql"
        bDExecutor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)
        bDExecutor.executeScripts()
    }

    @Ignore
    @Test
    void mustDoNotingIfConfigSkipExecutionIsTrue(){
        Script.metaClass.sh << 
        helper.registerAllowedMethod("sh", []) {"OK"}
        scriptsPath = "/test/resources/test.sql"
        mock.use {
            bDExecutor = new BDExecutor(scriptMock, scriptsPath, configFilePath, username, password, host, port)
            bDExecutor.executeScripts()
        }
    }

}