package com.pe.suraam.functions

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import static com.lesfurets.jenkins.unit.MethodCall.callArgsToString
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.containsString
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue

class BDExecutorTest extends BasePipelineTest {

    //TODO: Finalizar test unitarios

    def script
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
        username = "usernameTest"
        password = "passwordTest"
        host = 'localhost'
        port = '1433'
    }

    @Test
    void mustThrowFileNotFoundExceptionIfScriptsPathDoesNotExist() {
        BDConfigReader.metaClass.static.readConfigFile = {str -> return [skipExecution:false]}
        scriptsPath = "/non/existing/path"
        thrown.expect(FileNotFoundException.class)
        thrown.expectMessage(containsString("BD Scripts path is incorrect, please provide a correct path."))
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)
        executor.metaClass.getFilesList{ str -> [] }
        executor.executeScripts()
    }

    @Test
    void mustExecuteAnDBScriptIfConfigSkipExecutionIsFalse(){
        BDConfigReader.metaClass.static.readConfigFile = {str -> return [skipExecution:false]}
        helper.registerAllowedMethod("sh", [String.class]) {str -> println("Script OK")}
        helper.registerAllowedMethod("echo", [String.class]) {str -> println(str)}
        scriptsPath = "/test/resources"
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)
        executor.metaClass.getFilesList{ str -> ["test.sql"] }

        executor.executeScripts()

        assertTrue(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.any { call ->
            callArgsToString(call).contains("sqlcmd -s localhost -o 1433 -u usernameTest -p passwordTest")
        })
        assertJobStatusSuccess()
    }

    @Test
    void mustDoNotingIfConfigSkipExecutionIsTrue(){
        BDConfigReader.metaClass.static.readConfigFile = {str -> return [skipExecution:true]}
        helper.registerAllowedMethod("echo", [String.class]) {str -> println(str)}
        scriptsPath = "/test/resources"
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)
        executor.metaClass.getFilesList{ str -> ["test.sql"] }

        executor.executeScripts()

        assertTrue(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.isEmpty())

        assertFalse(helper.callStack.findAll { call ->
            call.methodName == "echo"
        }.any { call ->
            callArgsToString(call).contains("filesList")
        })

        assertJobStatusSuccess()
    }

    @Test
    void mustGetEmptyFilesListIfDoNotFindFiles(){
        helper.registerAllowedMethod("isUnix", []) {return true}
        helper.registerAllowedMethod("sh", [Map.class]) {c -> return ""}
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)

        def resultList = executor.getFilesList("/non/existing/path")

        assertThat(resultList, contains(""))
    }

    @Test
    void mustCallShInGetFilesListIfSOIsUnix(){
        helper.registerAllowedMethod("isUnix", []) {return true}
        helper.registerAllowedMethod("sh", [Map.class]) { c -> return "test.sql"}
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)

        def resultList = executor.getFilesList("/test/resources")

        assertTrue(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.any { call ->
            callArgsToString(call).contains("ls")
        })

        assertThat(resultList, contains("test.sql"))
    }

    @Test
    void mustCallBatInGetFilesListIfSOIsNotUnix(){
        helper.registerAllowedMethod("isUnix", []) {return false}
        helper.registerAllowedMethod("bat", [Map.class]) {c -> return "test.sql"}
        def executor = new BDExecutor(script, scriptsPath, configFilePath, username, password, host, port)

        def resultList = executor.getFilesList("/test/resources")

        assertTrue(helper.callStack.findAll { call ->
            call.methodName == "bat"
        }.any { call ->
            callArgsToString(call).contains("dir")
        })

        assertThat(resultList, contains("test.sql"))
    }



}