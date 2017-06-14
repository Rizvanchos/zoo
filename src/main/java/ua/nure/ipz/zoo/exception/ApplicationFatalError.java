package ua.nure.ipz.zoo.exception;

public class ApplicationFatalError extends RuntimeException {
    public ApplicationFatalError() {
        super("Application fatal error. Please contact your administrator.");
    }
}