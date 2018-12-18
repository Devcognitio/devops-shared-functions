package com.pe.suraam.exceptions

class BDScriptExecutionException extends Exception {

    BDScriptExecutionException() { }

    BDScriptExecutionException(String message) {
        super(message)
    }

    BDScriptExecutionException(String message, Throwable cause) {
        super(message, cause)
    }

}