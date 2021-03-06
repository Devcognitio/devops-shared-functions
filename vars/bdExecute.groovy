import com.pe.suraam.functions.BDExecutor

def call(scriptsPath, configFilePath, dbHost, dbPort) {
    withCredentials([usernamePassword(credentialsId: 'SQL_SERVER_CREDENTIALS',
                                      usernameVariable: 'USERNAME',
                                      passwordVariable: 'PASSWORD')]) {
        def bdExecutor = new BDExecutor(this, scriptsPath, configFilePath, USERNAME, PASSWORD, dbHost, dbPort)
        bdExecutor.executeScripts()
    }

}