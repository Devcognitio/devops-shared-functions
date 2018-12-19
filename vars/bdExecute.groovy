import main.groovy.com.pe.suraam.functions.BDExecutor

def call(scriptsPath, configFilePath) {
    def bdExecutor = new BDExecutor(this, scriptsPath, configFilePath)
    bdExecutor.executeScripts()
}